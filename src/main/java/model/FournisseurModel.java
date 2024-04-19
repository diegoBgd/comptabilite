 package model;
 
 import entite.Fournisseur;
 import java.util.List;
 import org.hibernate.Criteria;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.criterion.Criterion;
 import org.hibernate.criterion.MatchMode;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.hibernate.criterion.SimpleExpression;
 
  
 public class FournisseurModel
 {
   public void saveFournisseur(SessionFactory factory, Fournisseur frn) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(frn);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateFournisseur(SessionFactory factory, Fournisseur frn) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(frn);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void deleteClient(SessionFactory factory, Fournisseur frn) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(frn);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public Fournisseur geFournissuerId(int id, SessionFactory factory) {
     Fournisseur frn = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       frn = (Fournisseur)ss.get(Fournisseur.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return frn;
   }
 
   
   public Fournisseur getFournisseurByCode(SessionFactory factory, String code) {
     Fournisseur frn = null;
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     Criteria cr = ss.createCriteria(Fournisseur.class);
     SimpleExpression simpleExpression = Restrictions.eq("codeFrn", code);
     cr.add((Criterion)simpleExpression);
     frn = (Fournisseur)cr.uniqueResult();
     ss.getTransaction().commit();
     ss.close();
     
     return frn;
   }
 
   
   public List<Fournisseur> getListFournisseur(SessionFactory factory, String libelle) {
     List<Fournisseur> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(Fournisseur.class);
       if (libelle != null && !libelle.equals(""))
         cr.add((Criterion)Restrictions.like("libelle", libelle, MatchMode.ANYWHERE)); 
       cr.addOrder(Order.asc("codeFrn"));
       
       list = cr.list();
       session.getTransaction().commit();
       session.close();
     } catch (Exception e) {
       System.out.println(e.toString());
     } 
     return list;
   }
 }


