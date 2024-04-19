 package model;
 
 import entite.Encaissement;
 import entite.ReglementClient;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import javax.persistence.criteria.CriteriaBuilder;
 import javax.persistence.criteria.CriteriaQuery;
 import javax.persistence.criteria.Expression;
 import javax.persistence.criteria.Order;
 import javax.persistence.criteria.Predicate;
 import javax.persistence.criteria.Root;
 import javax.persistence.criteria.Selection;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 
 
 
 
 
 public class ReglementClientModel
 {
   public void saveReglementClt(SessionFactory factory, ReglementClient rgl) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(rgl);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void updateReglementClt(SessionFactory factory, ReglementClient rgl) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(rgl);
     ss.getTransaction().commit();
     ss.close();
   }
   public void deleteReglementClt(SessionFactory factory, ReglementClient rgl) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(rgl);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public ReglementClient getReglementCltById(int id, SessionFactory factory) {
     ReglementClient rgl = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       rgl = (ReglementClient)ss.get(ReglementClient.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return rgl;
   }
   public List<ReglementClient> getListReglement(SessionFactory factory, int idExercice, Date deb, Date fin, Encaissement enc) {
     List<ReglementClient> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       
       CriteriaBuilder cb = session.getCriteriaBuilder();
       CriteriaQuery<ReglementClient> qr = cb.createQuery(ReglementClient.class);
       Root<ReglementClient> ec = qr.from(ReglementClient.class);
       List<Predicate> predicates = new ArrayList<>();
       
       predicates.add(cb.equal((Expression)ec.get("idExercise"), Integer.valueOf(idExercice)));
       
       if (enc != null) {
         predicates.add(cb.equal((Expression)ec.get("encaissement"), enc));
       }
       if (deb != null) {
         predicates.add(cb.greaterThanOrEqualTo((Expression)ec.get("dateReglement"), deb));
       }
       if (fin != null) {
         predicates.add(cb.lessThanOrEqualTo((Expression)ec.get("dateReglement"), fin));
       }
       qr.select((Selection)ec).where((Expression)cb.and(predicates.<Predicate>toArray(new Predicate[predicates.size()])));
       qr.orderBy(new Order[] { cb.asc((Expression)ec.get("dateReglement")) });
       
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


