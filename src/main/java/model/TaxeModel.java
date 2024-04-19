 package model;
 
 import entite.Taxes;
 import java.util.List;
 import org.hibernate.Criteria;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.criterion.Criterion;
 import org.hibernate.criterion.MatchMode;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.hibernate.criterion.SimpleExpression;
 
  
 public class TaxeModel
 {
   public void saveTaxe(SessionFactory factory, Taxes tx) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(tx);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateTaxe(SessionFactory factory, Taxes tx) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(tx);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void deleteTaxe(SessionFactory factory, Taxes tx) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(tx);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public Taxes getTaxeById(int id, SessionFactory factory) {
     Taxes tx = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       tx = (Taxes)ss.get(Taxes.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return tx;
   }
 
   
   public Taxes getTaxeByCode(SessionFactory factory, String code) {
     Taxes tx = null;
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     Criteria cr = ss.createCriteria(Taxes.class);
     SimpleExpression simpleExpression = Restrictions.eq("codeTaxe", code);
     cr.add((Criterion)simpleExpression);
     tx = (Taxes)cr.uniqueResult();
     ss.getTransaction().commit();
     ss.close();
     
     return tx;
   }
 
   
   public List<Taxes> getListTaxes(SessionFactory factory, String libelle) {
     List<Taxes> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(Taxes.class);
       if (libelle != null && !libelle.equals(""))
         cr.add((Criterion)Restrictions.like("libelle", libelle, MatchMode.ANYWHERE)); 
       cr.addOrder(Order.asc("codeTaxe"));
       
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


