 package model;
 
 import entite.Exercice;
 import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.criterion.Criterion;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.hibernate.criterion.SimpleExpression;
  
 
 public class ExerciceModel
 {
   public void saveExercice(SessionFactory factory, Exercice ex) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(ex);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateExercice(SessionFactory factory, Exercice ex) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(ex);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void deleteExercice(SessionFactory factory, Exercice ex) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(ex);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public Exercice getExerciceById(int id, SessionFactory factory) {
     Exercice ex = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       ex = (Exercice)ss.get(Exercice.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return ex;
   }
 
   
   public Exercice getExercByCode(SessionFactory factory, String code) {
     Exercice ex = null;
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     Criteria cr = ss.createCriteria(Exercice.class);
     SimpleExpression simpleExpression = Restrictions.eq("exCode", code);
     cr.add((Criterion)simpleExpression);
     ex = (Exercice)cr.uniqueResult();
     ss.getTransaction().commit();
     ss.close();
     
     return ex;
   }
 
   
   @SuppressWarnings("unchecked")
public List<Exercice> getListExercice(SessionFactory factory) {
     List<Exercice> list = null;
     try {
    	 Session session = factory.openSession();
	       session.beginTransaction();
	       Query query = session.createQuery("SELECT E from Exercice E ORDER BY E.exCode ASC");	     	 
	       
	       list = query.getResultList();
	       session.getTransaction().commit();
	       session.close();
     }
     catch (Exception e) {
       System.out.println(e.toString());
     } 
     return list;
   }
 }


