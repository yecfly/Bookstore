/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sun.bookstore2.account;

import com.sun.bookstore.database.Account;
import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Yecfly
 */
public class AccountManager {
    //private Map accounts = new HashMap();

    private EntityManager em;

    public AccountManager(EntityManagerFactory emf) throws IOException {
        try {
            em = emf.createEntityManager();
        } catch (Exception ex) {
            throw new IOException(
                    "Couldn't open connection to database: " + ex.getMessage());
        }
    }

    public boolean login(String user, String password) throws IOException {
        System.err.println("login" + user + ":" + password);
        Account x;

        try {
            Query query = em.createQuery("SELECT ac FROM Account ac WHERE ac.name = :username");
            query.setParameter("username", user);
            x = (Account) query.getSingleResult();
        } catch (Exception ex) {
            System.err.println(ex.toString());
            return false;
        }
        System.err.println("success to login" + user + ":" + x.getPassword());

        return (x != null && x.getPassword().equals(password));
    }

    public boolean logout(String user) {
        return true;
    }

    public boolean register(String user, String password) throws IOException {
        //
        System.err.println("register " + user + ":" + password);
        boolean bSuccess = false;
        try {
            Account ac = new Account();
            try {
                Query query = em.createQuery("SELECT ac FROM Account ac WHERE ac.name = :username");
                query.setParameter("username", user);
                query.getSingleResult();
            } catch (Exception ex) {
                if (ex.getMessage().equalsIgnoreCase("getSingleResult() did not retrieve any entities.")) {
                    ac.setName(user);
                    ac.setPassword(password);
                    em.persist(ac);
                    em.flush();
                    bSuccess = true;
                }
            }

        } catch (Exception ex) {
            throw new IOException("Couldn't open execute insert sql when login: " + ex.getMessage());
        }

        return bSuccess;
    }
}
