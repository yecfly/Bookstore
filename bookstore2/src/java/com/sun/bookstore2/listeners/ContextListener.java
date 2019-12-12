/*
 * Copyright 2007 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
 */
package com.sun.bookstore2.listeners;

import com.sun.bookstore2.database.BookDBAO;
import javax.servlet.*;
import javax.persistence.*;
import com.sun.bookstore2.account.AccountManager;
import com.yec.bookstore2.record.RecordManager;

public final class ContextListener implements ServletContextListener {

    @PersistenceUnit
    private EntityManagerFactory emf;
    private ServletContext context = null;

    public void contextInitialized(ServletContextEvent event) {
        context = event.getServletContext();
        try{
        AccountManager manager = new AccountManager(emf);
        context.setAttribute("accountManager", manager);
        RecordManager rm=new RecordManager(emf);
        context.setAttribute("recordManager", rm);
            BookDBAO bookDBAO = new BookDBAO(emf);
            context.setAttribute("bookDBAO", bookDBAO);
        } catch (Exception ex) {
            System.out.println(
                    "Couldn't create bookstore database bean: "
                    + ex.getMessage());
        }
    }

    public void contextDestroyed(ServletContextEvent event) {
        context = event.getServletContext();

        BookDBAO bookDBAO = (BookDBAO) context.getAttribute("bookDBAO");

        if (bookDBAO != null) {
            bookDBAO.remove();
        }

        context.removeAttribute("bookDBAO");
    }
}
