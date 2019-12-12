/*
 * Copyright 2007 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
 */
package com.sun.bookstore2.dispatcher;

import com.sun.bookstore2.database.BookDBAO;
import javax.servlet.http.*;
import com.sun.bookstore.cart.ShoppingCart;
import com.sun.bookstore.database.Account;
import com.sun.bookstore.database.Book;
import com.sun.bookstore.exception.BookNotFoundException;
import com.sun.bookstore2.account.AccountManager;
import com.yec.bookstore2.record.RecordManager;
import javax.annotation.Resource;
import javax.transaction.UserTransaction;
import javax.ejb.EJB;
import scut.bank.CreditCardEJBRemote;

public class Dispatcher extends HttpServlet {

    @EJB(name = "helloejb")
    private CreditCardEJBRemote creditCardEJB;
    @Resource
    private UserTransaction utx;

    public void doRequest(HttpServletRequest request,
            HttpServletResponse response) {

        String bookId = null;
        String clear = null;
        Book book = null;
        BookDBAO bookDBAO = (BookDBAO) getServletContext()
                .getAttribute("bookDBAO");
        HttpSession session = request.getSession();
        String selectedScreen = request.getServletPath();
        String parameters = new String();
        Account account = (Account) session.getAttribute("account");
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        RecordManager m = (RecordManager) this.getServletContext().getAttribute("recordManager");

        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }

        System.out.println("dispatcher.aaa..!" + selectedScreen);

        if (selectedScreen.equals("/books/bookcatalog")) {
            bookId = request.getParameter("Add");

            if (!bookId.equals("")) {
                try {
                    book = bookDBAO.getBook(bookId);
                    cart.add(bookId, book);
                } catch (BookNotFoundException ex) {
                    // not possible
                }
            }
        } else if (selectedScreen.equals("/books/bookshowcart")) {
            bookId = request.getParameter("Remove");

            if (bookId != null) {
                cart.remove(bookId);
            }

            clear = request.getParameter("Clear");

            if ((clear != null) && clear.equals("clear")) {
                cart.clear();
            }
        } else if (selectedScreen.equals("/books/bookreceipt")) {

            if (account == null) {
                selectedScreen = "/books/login";
            } else {// Update the inventory
                ShoppingCart c = (ShoppingCart) session.getAttribute("cart");
                try {
                    utx.begin();
                    bookDBAO.buyBooks(cart);
                    String cardname = request.getParameter("cardname");
                    String cardnum = request.getParameter("cardnum");
                    double money = cart.getTotal();

                    System.out.println(cardname + " $$$$$$$$ " + cardnum);
                    if (!creditCardEJB.withdraw(cardnum, cardname, money)) {
                        parameters = "?error=2";
                        throw new Exception("credit card withdraw error!");
                    }
                    utx.commit();
                } catch (Exception ex) {
                    try {
                        utx.rollback();
                    } catch (Exception exe) {
                        System.out.println("Rollback failed: " + exe.getMessage());
                    }
                    if (parameters.length() == 0) {
                        parameters = "?error=1";
                    }
                    selectedScreen = "/books/bookordererror";

                }
                try {
                    utx.begin();
                    m.insertCart(account.getName(), c);
                    utx.commit();
                } catch (Exception ex) {
                    try {
                        utx.rollback();
                    } catch (Exception exe) {
                        System.out.println("Rollback failed: " + exe.getMessage());
                    }
                }
            }
        } //login
        else if (selectedScreen.equals("/books/login")) {
            String str1 = request.getParameter("login");

            if (str1.equalsIgnoreCase("register")) {
                selectedScreen = "/books/register";
            } else if (str1.equalsIgnoreCase("login")) {
                selectedScreen = "/books/login";
            }
        }
        else if(selectedScreen.equals("/books/records")){
            try{
                m.records=m.getRecords(account.getName());
            }catch(Exception e){
                System.err.println("Could not get records: "+e.getMessage());
            }
            session.setAttribute("recordManager", m);
        }


        String screen = selectedScreen + ".jsp" + parameters;

        try {
            request.getRequestDispatcher(screen)
                    .forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void doGet(HttpServletRequest request,
            HttpServletResponse response) {
        // doRequest?request,response);'

        this.doRequest(request, response);
    }

    public void doPost(
            HttpServletRequest request,
            HttpServletResponse response) {

        this.doRequest(request, response);
    }
}
