 package model;
 
 import entite.ParametreCompta;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.query.Query;
 
  
 public class ParametreComptaModel
 {
   public void saveParametreCmpta(SessionFactory factory, ParametreCompta prm) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     if (prm.getIdPrm() == 0) {
       ss.save(prm);
     } else {
       ss.update(prm);
     }  ss.getTransaction().commit();
     ss.close();
   }
   public void deleteParametreCmpta(SessionFactory factory, ParametreCompta prm) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(prm);
     ss.getTransaction().commit();
     ss.close();
   }
 
 
   
   public ParametreCompta getParameter(SessionFactory factory) {
     Session ss = null;
     ParametreCompta prm = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       Query query = ss.createQuery("SELECT p from ParametreCompta p WHERE p.idPrm=(SELECT MAX(p.idPrm) FROM ParametreCompta p)");
       prm = (ParametreCompta)query.getSingleResult();
       ss.getTransaction().commit();
       ss.close();
 
     
     }
     catch (Exception e) {
       
       e.getMessage();
     } 
     return prm;
   }
 }


