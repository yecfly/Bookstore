/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yec.bookstore2.record;

import com.sun.bookstore.database.Record;
import java.io.IOException;
import java.util.*;
import com.sun.bookstore.cart.ShoppingCart;
import com.sun.bookstore.cart.ShoppingCartItem;
import com.sun.bookstore.database.Book;
import com.sun.bookstore.exception.OrderException;
import com.sun.bookstore.exception.RecordsNotFoundException;
import java.util.Collection;
import java.util.Iterator;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Yecfly
 */
public class RecordManager {
    public List records;
    private EntityManager em;
    public Orderinfo od;
    public RecordManager(EntityManagerFactory emf) throws IOException
    { 
        try {
            em = emf.createEntityManager();
            od=new Orderinfo();
        } catch (Exception ex) {
            throw new IOException(
                    "Couldn't open connection to database: " + ex.getMessage());
        }
    }
    
    private boolean insert(String name, String title, Date time,double price, int quantity) throws IOException
{  
    System.err.println("insert record"+ title+" : "+ time.toString());
    boolean bSuccess = false;
    try {
        Record r = new Record(name,title,time);
        r.setPrice(price);
        r.setQuantity(quantity);
        em.persist(r);
        em.flush();
        bSuccess = true;
    } catch (Exception ex) {
        throw new IOException("Couldn't open execute insert sql when insert record: " + ex.getMessage());
    } 
    return bSuccess;
}
    
    public void insertCart(String name,ShoppingCart cart)throws OrderException {
        Date t=new Date();
        Collection it = cart.getItems();
        Iterator i = it.iterator();
        try {
            while (i.hasNext()) {
                ShoppingCartItem sci = (ShoppingCartItem) i.next();
                Book bd = (Book) sci.getItem();
                String id = bd.getBookId();
                int quantity = sci.getQuantity();
                if(!insert(name,bd.getTitle(),t,bd.getPrice(),quantity)) return;
            }
        } catch (Exception ex) {
            throw new OrderException("Insert record failed: " + ex.getMessage());
        }
    }
    
    public List getRecords(String name) throws RecordsNotFoundException{
        Query q=em.createQuery("SELECT re from Record re where re.recordPK.name = :uname order by re.recordPK.time desc");
        q.setParameter("uname", name);
        try{
            return q.getResultList();
        }catch(Exception ex){
            throw new RecordsNotFoundException("Could not get records: "+ex.getMessage());
        }
    }
    
    public List getRecords(String name, Date time) throws RecordsNotFoundException{
        Query q=em.createQuery("SELECT re from Record re where re.recordPK.name = :uname and re.recordPK.time = :t order by re.recordPK.time desc, re.recordPK.title asc");
        q.setParameter("uname", name);
        q.setParameter("t", time);
        try{
            return q.getResultList();
        }catch(Exception ex){
            throw new RecordsNotFoundException("Could not get records: "+ex.getMessage());
        }
    }
    
    public List getOrders(String name) throws RecordsNotFoundException{
        Query q=em.createQuery("SELECT DISTINCT re.recordPK.time from Record re where re.recordPK.name = :uname order by re.recordPK.time desc");
        q.setParameter("uname", name);
        try{
            return q.getResultList();
        }catch(Exception ex){
            throw new RecordsNotFoundException("Could not get records: "+ex.getMessage());
        }
    }
}
