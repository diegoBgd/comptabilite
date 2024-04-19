 package model;
 
 import entite.Depense;
 import entite.TypeCharge;
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
 
 
 
 
 
 public class DepenseModel
 {
   public void saveDepense(SessionFactory factory, Depense dep) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(dep);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateDepense(SessionFactory factory, Depense dep) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(dep);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void deleteDepense(SessionFactory factory, Depense dep) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(dep);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public Depense getDepenseById(int id, SessionFactory factory) {
     Depense dep = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       dep = (Depense)ss.get(Depense.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return dep;
   }
   
   public List<Depense> getListDepense(SessionFactory factory, int idExercice, TypeCharge type, Date deb, Date fin) {
     List<Depense> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       
       CriteriaBuilder cb = session.getCriteriaBuilder();
       CriteriaQuery<Depense> qr = cb.createQuery(Depense.class);
       Root<Depense> ec = qr.from(Depense.class);
       List<Predicate> predicates = new ArrayList<>();
       
       predicates.add(cb.equal((Expression)ec.get("idExercise"), Integer.valueOf(idExercice)));
       
       if (type != null) {
         predicates.add(cb.equal((Expression)ec.get("charge"), type));
       }
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


