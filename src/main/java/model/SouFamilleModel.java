 package model;
 
 import entite.FamilleProduit;
 import entite.SoufamilleProd;
 import java.util.List;
 import org.hibernate.Criteria;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.criterion.Criterion;
 import org.hibernate.criterion.MatchMode;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.hibernate.criterion.SimpleExpression;
 
  
 public class SouFamilleModel
 {
   public void saveSousFamille(SessionFactory factory, SoufamilleProd sfm) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(sfm);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateSousFamille(SessionFactory factory, SoufamilleProd sfm) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(sfm);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void deleteSousFamille(SessionFactory factory, SoufamilleProd sfm) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(sfm);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public SoufamilleProd getSousFamilleById(int id, SessionFactory factory) {
     SoufamilleProd sfm = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       sfm = (SoufamilleProd)ss.get(SoufamilleProd.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return sfm;
   }
 
   
   public SoufamilleProd getSousFamilleByCode(SessionFactory factory, String code) {
     SoufamilleProd sfm = null;
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     Criteria cr = ss.createCriteria(SoufamilleProd.class);
     SimpleExpression simpleExpression = Restrictions.eq("codeSfm", code);
     cr.add((Criterion)simpleExpression);
     sfm = (SoufamilleProd)cr.uniqueResult();
     ss.getTransaction().commit();
     ss.close();
     
     return sfm;
   }
 
   
   public List<SoufamilleProd> getListSousFamille(SessionFactory factory, String libelle) {
     List<SoufamilleProd> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(SoufamilleProd.class);
       if (libelle != null && !libelle.equals(""))
         cr.add((Criterion)Restrictions.like("libelle", libelle, MatchMode.ANYWHERE)); 
       cr.addOrder(Order.asc("codeSfm"));
       
       list = cr.list();
       session.getTransaction().commit();
       session.close();
     }
     catch (Exception e) {
       System.out.println(e.toString());
     } 
     return list;
   }
   
   public List<SoufamilleProd> getListSousFamille(SessionFactory factory, FamilleProduit fml) {
     List<SoufamilleProd> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(FamilleProduit.class);
       if (fml != null)
         cr.add((Criterion)Restrictions.eq("famille", fml)); 
       cr.addOrder(Order.asc("codeSfm"));
       
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


