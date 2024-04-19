 package model;
 
 import entite.Banque;
 import java.util.List;
 import org.hibernate.Criteria;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.criterion.Criterion;
 import org.hibernate.criterion.MatchMode;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.hibernate.criterion.SimpleExpression;
  
 public class BanqueModel
 {
   public void saveBanque(SessionFactory factory, Banque bk) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(bk);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateBanque(SessionFactory factory, Banque bk) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(bk);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void deleteBanque(SessionFactory factory, Banque bk) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(bk);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public Banque getBanqueById(int id, SessionFactory factory) {
     Banque bk = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       bk = (Banque)ss.get(Banque.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return bk;
   }
 
   
   public Banque getBanqueByCode(SessionFactory factory, String code) {
     Banque bk = null;
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     Criteria cr = ss.createCriteria(Banque.class);
     SimpleExpression simpleExpression = Restrictions.eq("codeBk", code);
     cr.add((Criterion)simpleExpression);
     bk = (Banque)cr.uniqueResult();
     ss.getTransaction().commit();
     ss.close();
     
     return bk;
   }
 
   
   public List<Banque> getListBanque(SessionFactory factory, String libelle) {
     List<Banque> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(Banque.class);
       if (libelle != null && !libelle.equals(""))
         cr.add((Criterion)Restrictions.like("libelle", libelle, MatchMode.ANYWHERE)); 
       cr.addOrder(Order.asc("codeBk"));
       
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


