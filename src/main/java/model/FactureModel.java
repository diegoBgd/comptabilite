 package model;
 
 import entite.Clients;
 import entite.Facture;
 import java.util.ArrayList;
 import java.util.List;
 import javax.persistence.criteria.CriteriaBuilder;
 import javax.persistence.criteria.CriteriaQuery;
 import javax.persistence.criteria.Expression;
 import javax.persistence.criteria.Predicate;
 import javax.persistence.criteria.Root;
 import javax.persistence.criteria.Selection;
 import org.hibernate.Criteria;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.criterion.Criterion;
 import org.hibernate.criterion.Restrictions;
 import org.hibernate.criterion.SimpleExpression;
 
 
 public class FactureModel
 {
   public void saveFacturation(SessionFactory factory, Facture fac) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(fac);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateFacturation(SessionFactory factory, Facture fac) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(fac);
     ss.getTransaction().commit();
     ss.close();
   }
   public Facture getFacturationById(int id, SessionFactory factory) {
     Facture fac = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       fac = (Facture)ss.get(Facture.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return fac;
   }
   
   public Facture getFacturationByNumero(SessionFactory factory, int num) {
     Facture fac = null;
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     Criteria cr = ss.createCriteria(Facture.class);
     SimpleExpression simpleExpression = Restrictions.eq("numeroFact", Integer.valueOf(num));
     cr.add((Criterion)simpleExpression);
     fac = (Facture)cr.uniqueResult();
     ss.getTransaction().commit();
     ss.close();
     
     return fac;
   }
   public List<Facture> getListFacturation(SessionFactory factory, Clients clt) {
     List<Facture> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       
       CriteriaBuilder cb = session.getCriteriaBuilder();
       CriteriaQuery<Facture> qr = cb.createQuery(Facture.class);
       Root<Facture> ec = qr.from(Facture.class);
       List<Predicate> predicates = new ArrayList<>();
       predicates.add(cb.equal((Expression)ec.get("customer"), clt));
       
       qr.select((Selection)ec).where((Expression)cb.and(predicates.<Predicate>toArray(new Predicate[predicates.size()])));
       
       list = session.createQuery(qr).getResultList();
       
       session.getTransaction().commit();
       session.close();
     }
     catch (Exception e) {
       System.out.println(e.toString());
     } 
     return list;
   }
 }


