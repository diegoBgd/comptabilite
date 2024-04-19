 package model;
 
 import entite.FamilleProduit;
 import java.util.List;
 import org.hibernate.Criteria;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.criterion.Criterion;
 import org.hibernate.criterion.MatchMode;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.hibernate.criterion.SimpleExpression;
  
 
 public class FamilleModel
 {
   public void saveFamille(SessionFactory factory, FamilleProduit fm) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(fm);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateFamille(SessionFactory factory, FamilleProduit fm) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(fm);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void deleteFamille(SessionFactory factory, FamilleProduit fm) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(fm);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public FamilleProduit getFamilleById(int id, SessionFactory factory) {
     FamilleProduit fm = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       fm = (FamilleProduit)ss.get(FamilleProduit.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return fm;
   }
 
   
   public FamilleProduit getFamilleByCode(SessionFactory factory, String code) {
     FamilleProduit fm = null;
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     Criteria cr = ss.createCriteria(FamilleProduit.class);
     SimpleExpression simpleExpression = Restrictions.eq("codeFm", code);
     cr.add((Criterion)simpleExpression);
     fm = (FamilleProduit)cr.uniqueResult();
     ss.getTransaction().commit();
     ss.close();
     
     return fm;
   }
 
   
   public List<FamilleProduit> getListFamille(SessionFactory factory, String libelle) {
     List<FamilleProduit> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(FamilleProduit.class);
       if (libelle != null && !libelle.equals(""))
         cr.add((Criterion)Restrictions.like("libelle", libelle, MatchMode.ANYWHERE)); 
       cr.addOrder(Order.asc("codeFm"));
       
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


