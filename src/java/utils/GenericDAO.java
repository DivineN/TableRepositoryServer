/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.List;
import org.hibernate.*;

/**
 *
 * @author divine
 */
public class GenericDAO<ClassName> {
    private Class<ClassName> type;

    public GenericDAO(Class<ClassName> type) {
        this.type = type;
    }
    
    private Session session = null;
    
    public ClassName create(ClassName cObj){
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        try {
            session.save(cObj);
            session.getTransaction().commit();
            session.close();
            return  cObj;
        } catch (Exception e) {
            return null;
        }  
    }
    
    public List<ClassName> retrieveAll(){
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
             List<ClassName> list = session.createCriteria(type.getName()).list();
            session.getTransaction().commit();
            session.close();
            return list;
        } catch (Exception e) {
            return null;
        }
    }
    
    public ClassName update(ClassName cObj){
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            session.update(cObj);
            session.getTransaction().commit();
            session.close();
            return cObj;
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean delete(ClassName cObj){
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        try {
            session.delete(cObj);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }
    
    public ClassName findByEmail(String email){
        session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        
        try {
            ClassName  cObj = (ClassName)session.get(type, email);
            session.getTransaction().commit();
            session.close();
            return cObj;
        } catch (Exception e) {
            return null;
        }
    }
    
    public ClassName findByTableNo(int id){
        Query query= HibernateUtil.getSessionFactory().openSession().
        createQuery("from "+ type.getName()+" where table_no=:id");
        query.setParameter("id", id);
        ClassName x = (ClassName) query.uniqueResult();
        return x;
    }
    
    public ClassName findByReservationNo(int id){
        Query query= HibernateUtil.getSessionFactory().openSession().
        createQuery("from "+ type.getName()+" where reservation_no=:id");
        query.setParameter("id", id);
        ClassName x = (ClassName) query.uniqueResult();
        return x;
    }
    
    public List<ClassName> retrieveAvailableTables(){
        Query query= HibernateUtil.getSessionFactory().openSession().
        createQuery("from "+ type.getName()+" where isReserved=false");
        List<ClassName> reservedTables= query.list();
        return reservedTables;
    }
}
