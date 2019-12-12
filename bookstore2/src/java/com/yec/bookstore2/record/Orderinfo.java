/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yec.bookstore2.record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Yecfly
 */
public class Orderinfo {
    public List<Date> tm; 
    public List amount;
    public int flag;
    public Orderinfo(){
        this.tm=new ArrayList();
        this.amount=new ArrayList();
        flag=0;
    }
}
