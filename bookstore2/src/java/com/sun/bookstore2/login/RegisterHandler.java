/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sun.bookstore2.login;

import com.sun.bookstore2.account.AccountManager;
import java.io.*;
import java.net.*;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.transaction.UserTransaction;

/**
 *
 * @author Yecfly
 */
public class RegisterHandler extends HttpServlet {

    @Resource
    private UserTransaction utx;

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            if (!checkEmail(email)) {
                request.setAttribute("error", "true");
                request.setAttribute("infoR", "email");
                request.getRequestDispatcher("/books/register.jsp")
                        .forward(request, response);
            }
            if (!checkName(username)) {
                request.setAttribute("error", "true");
                request.setAttribute("infoR", "name");
                request.getRequestDispatcher("/books/register.jsp")
                        .forward(request, response);
            }
            if (!checkPassword(password)) {
                request.setAttribute("error", "true");
                request.setAttribute("infoR", "password");
                request.getRequestDispatcher("/books/register.jsp")
                        .forward(request, response);
            } else {
                AccountManager manager = (AccountManager) this.getServletContext().getAttribute("accountManager");
                utx.begin();
                boolean success = manager.register(username, password);
                utx.commit();
                if (success) {
                    request.getRequestDispatcher("/books/registerresult.jsp?success=1")
                            .forward(request, response);
                } else {
                    request.getRequestDispatcher("/books/registerresult.jsp?success=0")
                            .forward(request, response);
                }
            }
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception exe) {
                System.out.println("Rollback failed: " + exe.getMessage());
            }
            System.out.println(ex.toString());
            throw new IOException(
                    "Couldn't open execute insert sql when login: " + ex.getMessage());
        }

    }

    //check the sytax of the email address string
    private boolean checkEmail(String email) {
        if (email.contains("@")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkName(String name) {
        if (name.length() > 0 && name.length() < 21) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkPassword(String p) {
        if (p.length() > 5 && p.length() < 21) {
            return true;
        } else {
            return false;
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
