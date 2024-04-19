 package model;
 
 import entite.Journal;
 import java.util.List;
 import org.hibernate.Criteria;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.criterion.Criterion;
 import org.hibernate.criterion.MatchMode;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.hibernate.criterion.SimpleExpression;
 
  
 public class JournalModel
 {
   public void saveJournal(SessionFactory factory, Journal jrnl) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(jrnl);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateJournal(SessionFactory factory, Journal jrnl) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(jrnl);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void deleteJournal(SessionFactory factory, Journal jrnl) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(jrnl);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public Journal getJournalById(int id, SessionFactory factory) {
     Journal jrnl = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       jrnl = (Journal)ss.get(Journal.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return jrnl;
   }
 
   
   public Journal getJouralByCode(SessionFactory factory, String code) {
     Journal jrnl = null;
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     Criteria cr = ss.createCriteria(Journal.class);
     SimpleExpression simpleExpression = Restrictions.eq("codeJrnl", code);
     cr.add((Criterion)simpleExpression);
     jrnl = (Journal)cr.uniqueResult();
     ss.getTransaction().commit();
     ss.close();
     
     return jrnl;
   }
 
   
   public List<Journal> getListJouranl(SessionFactory factory) {
     List<Journal> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(Journal.class);
       
       cr.addOrder(Order.asc("codeJrnl"));
       
       list = cr.list();
       session.getTransaction().commit();
       session.close();
     }
     catch (Exception e) {
       System.out.println(e.toString());
     } 
     return list;
   }
 
   
   public List<Journal> getListJouranl(SessionFactory factory, String libelle) {
     List<Journal> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(Journal.class);
       if (libelle != null && !libelle.equals(""))
         cr.add((Criterion)Restrictions.like("libelle", libelle, MatchMode.ANYWHERE)); 
       cr.addOrder(Order.asc("codeJrnl"));
       
       list = cr.list();
       session.getTransaction().commit();
       session.close();
     }
     catch (Exception e) {
       System.out.println(e.toString());
     } 
     return list;
   }
 }


