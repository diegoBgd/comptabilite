 package model;
 
 import entite.EtatFinancier;
 import java.util.List;
 import org.hibernate.Criteria;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.criterion.Criterion;
 import org.hibernate.criterion.MatchMode;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.hibernate.criterion.SimpleExpression;
 
 
 
 
 
 
 public class EtatFinancierModel
 {
   public void saveEtatFinancier(SessionFactory factory, EtatFinancier ef) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(ef);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateEtatFinancier(SessionFactory factory, EtatFinancier ef) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(ef);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void deleteEtatFinancier(SessionFactory factory, EtatFinancier ef) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(ef);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public EtatFinancier getEFiById(int id, SessionFactory factory) {
     EtatFinancier efi = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       efi = (EtatFinancier)ss.get(EtatFinancier.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return efi;
   }
 
   
   public EtatFinancier getEFiByCode(SessionFactory factory, String code) {
     EtatFinancier efi = null;
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     Criteria cr = ss.createCriteria(EtatFinancier.class);
     SimpleExpression simpleExpression = Restrictions.eq("code", code);
     cr.add((Criterion)simpleExpression);
     efi = (EtatFinancier)cr.uniqueResult();
     ss.getTransaction().commit();
     ss.close();
     
     return efi;
   }
 
   
   public List<EtatFinancier> getListEFi(SessionFactory factory) {
     List<EtatFinancier> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(EtatFinancier.class);
       
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
 
   
   public List<EtatFinancier> getListEFi(SessionFactory factory, String libelle) {
     List<EtatFinancier> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(EtatFinancier.class);
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


