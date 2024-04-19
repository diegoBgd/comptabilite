 package model;
 
 import entite.Devise;
 import java.util.List;
 import org.hibernate.Criteria;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.criterion.Criterion;
 import org.hibernate.criterion.MatchMode;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.hibernate.criterion.SimpleExpression;
 
 
 
 
 
 
 public class DeviseModel
 {
   public void saveDevise(SessionFactory factory, Devise dev) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(dev);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateDevise(SessionFactory factory, Devise dev) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(dev);
     ss.getTransaction().commit();
     ss.close();
   }
   public void deleteDevise(SessionFactory factory, Devise dev) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(dev);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public Devise getDeviseById(int id, SessionFactory factory) {
     Devise dev = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       dev = (Devise)ss.get(Devise.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return dev;
   }
 
   
   public Devise getDeviseByCode(SessionFactory factory, String code) {
     Devise dev = null;
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     Criteria cr = ss.createCriteria(Devise.class);
     SimpleExpression simpleExpression = Restrictions.eq("codeDev", code);
     cr.add((Criterion)simpleExpression);
     dev = (Devise)cr.uniqueResult();
     ss.getTransaction().commit();
     ss.close();
     
     return dev;
   }
 
   
   public List<Devise> getListDevise(SessionFactory factory, String libelle) {
     List<Devise> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(Devise.class);
       if (libelle != null && !libelle.equals(""))
         cr.add((Criterion)Restrictions.like("libelle", libelle, MatchMode.ANYWHERE)); 
       cr.addOrder(Order.asc("codeDev"));
       
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


