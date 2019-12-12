/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sun.bookstore2.filter;

import java.io.CharArrayWriter;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 *
 * @author Yecfly
 */
public class CharResponseWrapper extends HttpServletResponseWrapper {

    private CharArrayWriter output;

    public CharResponseWrapper(HttpServletResponse response) {
        super(response);
        output = new CharArrayWriter();
    }

    public String toString() {
        return output.toString();
    }

    public PrintWriter getWriter() {
        return new PrintWriter(output);
    }
}
