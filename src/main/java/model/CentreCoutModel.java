 package model;
 
 import entite.CentreCout;
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
 
  
 public class CentreCoutModel
 {
   public void saveCentreCout(SessionFactory factory, CentreCout ctc) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(ctc);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateCentreCout(SessionFactory factory, CentreCout ctc) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(ctc);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void deleteCentreCout(SessionFactory factory, CentreCout ctc) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(ctc);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public CentreCout getCentreCoutById(int id, SessionFactory factory) {
     CentreCout ctc = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       ctc = (CentreCout)ss.get(CentreCout.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return ctc;
   }
 
   
   public CentreCout getCentreCoutByCode(SessionFactory factory, String code) {
     CentreCout ctc = null;
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     Criteria cr = ss.createCriteria(CentreCout.class);
     SimpleExpression simpleExpression = Restrictions.eq("code", code);
     cr.add((Criterion)simpleExpression);
     ctc = (CentreCout)cr.uniqueResult();
     ss.getTransaction().commit();
     ss.close();
     
     return ctc;
   }
 
   
   public List<CentreCout> getListCentreCout(SessionFactory factory) {
     List<CentreCout> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(CentreCout.class);
       
       cr.addOrder(Order.asc("code"));
       
       list = cr.list();
       session.getTransaction().commit();
       session.close();
     }
     catch (Exception e) {
       System.out.println(e.toString());
     } 
     return list;
   }
 
   
   public List<CentreCout> getListCentreCout(SessionFactory factory, String libelle) {
     List<CentreCout> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(Journal.class);
       if (libelle != null && !libelle.equals(""))
         cr.add((Criterion)Restrictions.like("libelle", libelle, MatchMode.ANYWHERE)); 
       cr.addOrder(Order.asc("code"));
       
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


