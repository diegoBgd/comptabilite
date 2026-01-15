 package model;
 
 import entite.Compte;
import entite.TypeCharge;
 import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.criterion.Criterion;
 import org.hibernate.criterion.MatchMode;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.hibernate.criterion.SimpleExpression;
 
  
 public class TypeChargeModel
 {
   public void saveCharge(SessionFactory factory, TypeCharge tchg) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(tchg);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateCharge(SessionFactory factory, TypeCharge tchg) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(tchg);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void deleteCharge(SessionFactory factory, TypeCharge tchg) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(tchg);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public TypeCharge getChargeById(int id, SessionFactory factory) {
     TypeCharge tchg = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       tchg = (TypeCharge)ss.get(TypeCharge.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return tchg;
   }
 
   
   public TypeCharge getChargeByCode(SessionFactory factory, String code) {
     TypeCharge tchg = null;
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     Criteria cr = ss.createCriteria(TypeCharge.class);
     SimpleExpression simpleExpression = Restrictions.eq("codeChg", code);
     cr.add((Criterion)simpleExpression);
     tchg = (TypeCharge)cr.uniqueResult();
     ss.getTransaction().commit();
     ss.close();
     
     return tchg;
   }
 
   @SuppressWarnings("unchecked")
	public List<TypeCharge> getListCharge(SessionFactory factory,String libelle) {
	     List<TypeCharge> listChrg = null;
	 
	     try { 
	       Session session = factory.openSession();
	       session.beginTransaction();
	       Query query = session.createQuery("SELECT C from TypeCharge C ORDER BY C.codeChg ASC");
	       
	       listChrg = query.getResultList();
	       session.getTransaction().commit();
	       session.close();
	     }
	     catch (Exception e) {
	       System.out.println(e.toString());
	     } 
	     return listChrg;
		}
   
 }


