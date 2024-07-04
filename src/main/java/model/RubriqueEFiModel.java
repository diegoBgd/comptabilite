 package model;
 
 import entite.Compte;
import entite.EtatFinancier;
 import entite.RubriqueEFi;
 import java.util.ArrayList;
 import java.util.List;
 import javax.persistence.criteria.CriteriaBuilder;
 import javax.persistence.criteria.CriteriaQuery;
 import javax.persistence.criteria.Expression;
import javax.persistence.Query;
import javax.persistence.criteria.*;
 import javax.persistence.criteria.Predicate;
 import javax.persistence.criteria.Root;
 import javax.persistence.criteria.Selection;
 import org.hibernate.Criteria;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.criterion.Criterion;
 import org.hibernate.criterion.MatchMode;
 import org.hibernate.criterion.Restrictions;
 import org.hibernate.criterion.SimpleExpression;
 
  
 public class RubriqueEFiModel
 {
   public void saveRubrique(SessionFactory factory, RubriqueEFi rb) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(rb);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateRubrique(SessionFactory factory, RubriqueEFi rb) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(rb);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void deleteRubrique(SessionFactory factory, RubriqueEFi rb) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(rb);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public RubriqueEFi getRubriqueById(int id, SessionFactory factory) {
     RubriqueEFi rb = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       rb = (RubriqueEFi)ss.get(RubriqueEFi.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return rb;
   }
 
   
   public RubriqueEFi getRubriqueByCode(SessionFactory factory, String code) {
     RubriqueEFi rb = null;
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     Criteria cr = ss.createCriteria(RubriqueEFi.class);
     SimpleExpression simpleExpression = Restrictions.eq("code", code);
     cr.add((Criterion)simpleExpression);
     rb = (RubriqueEFi)cr.uniqueResult();
     ss.getTransaction().commit();
     ss.close();
     
     return rb;
   }
 
   
   public List<RubriqueEFi> getListRubrique(SessionFactory factory) {
     List<RubriqueEFi> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(RubriqueEFi.class);
       
     
        
       list = cr.list();
       session.getTransaction().commit();
       session.close();
     }
     catch (Exception e) {
       System.out.println(e.toString());
     } 
     return list;
   }
 
   
   @SuppressWarnings("unchecked")
			public List<RubriqueEFi> getListRubrique(SessionFactory factory, String libelle) {
     List<RubriqueEFi> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(RubriqueEFi.class);
       if (libelle != null && !libelle.equals(""))
         cr.add((Criterion)Restrictions.like("libelle", libelle, MatchMode.ANYWHERE)); 
    
       
       list = cr.list();
       session.getTransaction().commit();
       session.close();
     }
     catch (Exception e) {
       System.out.println(e.toString());
     } 
     return list;
   }
 
   
   @SuppressWarnings("unchecked")
public List<RubriqueEFi> getListRubrique(SessionFactory factory, EtatFinancier efi) {
     List<RubriqueEFi> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       
       CriteriaBuilder cb = session.getCriteriaBuilder();
       CriteriaQuery<RubriqueEFi> qr = cb.createQuery(RubriqueEFi.class);
       Root<RubriqueEFi> ec = qr.from(RubriqueEFi.class);
       List<Predicate> predicates = new ArrayList<>();
       
       if (efi != null) {
         
         predicates.add(cb.equal((Expression<?>)ec.get("etat"), efi));
         qr.select((Selection)ec).where((Expression)cb.and(predicates.<Predicate>toArray(new Predicate[predicates.size()])));
         qr.orderBy(new Order[] { cb.asc((Expression)ec.get("code")) });
         list = session.createQuery(qr).getResultList();
       } 
       session.getTransaction().commit();
       session.close();
     }
     catch (Exception e) {
       System.out.println(e.toString());
     } 
     return list;
   }
   
	@SuppressWarnings("unchecked")
	public List<RubriqueEFi> getListRubrique(SessionFactory factory, EtatFinancier efi, int typeRb) {
		List<RubriqueEFi> listRb = null;

		try {
			Session session = factory.openSession();
			session.beginTransaction();
			String sql = "SELECT r from RubriqueEFi r WHERE r.etat=:e ";

			if (typeRb > 0)
				sql += " AND r.typeValeur=:t ";

			sql += " ORDER BY r.code ASC";

			Query query = session.createQuery(sql);

			query.setParameter("e", efi);
			if (typeRb > 0)
				query.setParameter("t", typeRb);

			listRb = query.getResultList();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return listRb;
	}
	
 }


