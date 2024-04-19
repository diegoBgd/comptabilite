 package model;
 
 import entite.Immobilise;
 import java.io.Serializable;
 import java.util.List;
 import org.hibernate.Criteria;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.criterion.Criterion;
 import org.hibernate.criterion.MatchMode;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.hibernate.criterion.SimpleExpression;
  
 
 
 public class ImmobiliseModel
   implements Serializable
 {
   private static final long serialVersionUID = -7848369059847197205L;
   
   public void saveImmo(SessionFactory factory, Immobilise immo) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(immo);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void updateImmo(SessionFactory factory, Immobilise immo) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(immo);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void deleteImmo(SessionFactory factory, Immobilise immo) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(immo);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public Immobilise getImmoById(int id, SessionFactory factory) {
     Immobilise immo = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       immo = (Immobilise)ss.get(Immobilise.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return immo;
   }
 
   
   public Immobilise getImmoByCode(SessionFactory factory, String code) {
     Immobilise immo = null;
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     Criteria cr = ss.createCriteria(Immobilise.class);
     SimpleExpression simpleExpression = Restrictions.eq("codeImmo", code);
     cr.add((Criterion)simpleExpression);
     immo = (Immobilise)cr.uniqueResult();
     ss.getTransaction().commit();
     ss.close();
     
     return immo;
   }
 
   
   public List<Immobilise> getListImmo(SessionFactory factory, String libelle) {
     List<Immobilise> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(Immobilise.class);
       if (libelle != null && !libelle.equals(""))
         cr.add((Criterion)Restrictions.like("libelle", libelle, MatchMode.ANYWHERE)); 
       cr.addOrder(Order.asc("codeImmo"));
       
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


