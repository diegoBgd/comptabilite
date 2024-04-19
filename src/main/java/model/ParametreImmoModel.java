 package model;
 
 import entite.ParametreImmo;
 import org.hibernate.Criteria;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 
 
 
 
 
 
 public class ParametreImmoModel
 {
   public void saveParmImmo(SessionFactory factory, ParametreImmo pIm) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(pIm);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void updateParmImmo(SessionFactory factory, ParametreImmo pIm) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(pIm);
     ss.getTransaction().commit();
     ss.close();
   }
   public void deleteParmImmo(SessionFactory factory, ParametreImmo pIm) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(pIm);
     ss.getTransaction().commit();
     ss.close();
   }
 
 
   
   public ParametreImmo getParmImmo(SessionFactory factory) {
     ParametreImmo pIm = null;
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     Criteria cr = ss.createCriteria(ParametreImmo.class);
     cr.add(Restrictions.isNotNull("idParm"));
     cr.addOrder(Order.desc("idParm"));
     
     pIm = (ParametreImmo)cr.uniqueResult();
     ss.getTransaction().commit();
     ss.close();
     
     return pIm;
   }
 }


