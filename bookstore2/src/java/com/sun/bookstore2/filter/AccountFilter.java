/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sun.bookstore2.filter;

import com.sun.bookstore.database.Account;
import com.yec.bookstore2.record.RecordManager;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Yecfly
 */
public class AccountFilter implements Filter {

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        PrintWriter out = response.getWriter();
        CharResponseWrapper wrapper = new CharResponseWrapper(
                (HttpServletResponse) response);
        chain.doFilter(request, wrapper);
//get the account
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession();
        Account account = (Account) session.getAttribute("account");
        RecordManager m = (RecordManager) session.getAttribute("recordManager");
        session.setAttribute("re", 0);
        if (account != null) {
            out.println("Welcome !" + account.getName());
            session.setAttribute("re", 1);
            try{
                m.od.tm=(List<Date>)m.getOrders(account.getName());
                
            }catch(Exception ex){
                
            }
        }
        out.print(wrapper.toString());
        out.close();
    }

    @Override
    public void destroy() {
    }
}
