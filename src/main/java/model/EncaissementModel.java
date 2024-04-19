 package model;
 
 import entite.Encaissement;
 import entite.TypeEcriture;
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
 
 
 
 
 public class EncaissementModel
 {
   public void saveEncaissement(SessionFactory factory, Encaissement enc) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(enc);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateEncaissement(SessionFactory factory, Encaissement enc) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(enc);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void deleteEncaissement(SessionFactory factory, Encaissement enc) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(enc);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public Encaissement getEncaissementById(int id, SessionFactory factory) {
     Encaissement enc = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       enc = (Encaissement)ss.get(Encaissement.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return enc;
   }
   
   public List<Encaissement> getListEncaissement(SessionFactory factory, int idExercice, Date deb, Date fin, TypeEcriture type) {
     List<Encaissement> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       
       CriteriaBuilder cb = session.getCriteriaBuilder();
       CriteriaQuery<Encaissement> qr = cb.createQuery(Encaissement.class);
       Root<Encaissement> ec = qr.from(Encaissement.class);
       List<Predicate> predicates = new ArrayList<>();
       
       predicates.add(cb.equal((Expression)ec.get("idExercise"), Integer.valueOf(idExercice)));
       predicates.add(cb.equal((Expression)ec.get("typeEntree"), type));
       
       if (deb != null) {
         predicates.add(cb.greaterThanOrEqualTo((Expression)ec.get("dateOperation"), deb));
       }
       if (fin != null) {
         predicates.add(cb.lessThanOrEqualTo((Expression)ec.get("dateOperation"), fin));
       }
       qr.select((Selection)ec).where((Expression)cb.and(predicates.<Predicate>toArray(new Predicate[predicates.size()])));
       qr.orderBy(new Order[] { cb.asc((Expression)ec.get("dateOperation")) });
       
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


