 package model;
 
 import entite.Amortissement;
 import entite.Ecriture;
 import entite.Exercice;
 import java.util.ArrayList;
 import java.util.List;
 import javax.persistence.criteria.CriteriaBuilder;
 import javax.persistence.criteria.CriteriaQuery;
 import javax.persistence.criteria.Expression;
 import javax.persistence.criteria.Predicate;
 import javax.persistence.criteria.Root;
 import javax.persistence.criteria.Selection;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 
 
 public class AmortisementModel
 {
   public void saveAmortissement(SessionFactory factory, List<Amortissement> listAmrt) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     for (Amortissement amrt : listAmrt) {
       
       int id = ((Integer)ss.save(amrt)).intValue();
       
       for (Ecriture ecr : amrt.getListEcriture()) {
         ecr.setSourceOperation(id);
         saveEcriture(ss, ecr);
       } 
     } 
     
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   private void saveEcriture(Session s, Ecriture e) {
     s.save(e);
   }
   
   public void deleteAmortissemnt(SessionFactory factory, Exercice exe) {
     List<Amortissement> liste = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       
       CriteriaBuilder cb = session.getCriteriaBuilder();
       CriteriaQuery<Amortissement> qr = cb.createQuery(Amortissement.class);
       Root<Amortissement> ec = qr.from(Amortissement.class);
       List<Predicate> predicates = new ArrayList<>();
       predicates.add(cb.equal((Expression)ec.get("exercice"), exe));
       qr.select((Selection)ec).where((Expression)cb.and(predicates.<Predicate>toArray(new Predicate[predicates.size()])));
       liste = session.createQuery(qr).getResultList();
       
       session.getTransaction().commit();
       session.close();
     }
     catch (Exception e) {
       System.out.println(e.toString());
     } 
   }
   public List<Amortissement> getListAccount(SessionFactory factory, Exercice ex) {
     List<Amortissement> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       
       CriteriaBuilder cb = session.getCriteriaBuilder();
       CriteriaQuery<Amortissement> qr = cb.createQuery(Amortissement.class);
       Root<Amortissement> ec = qr.from(Amortissement.class);
       List<Predicate> predicates = new ArrayList<>();
       predicates.add(cb.equal((Expression)ec.get("exercice"), ex));
       
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


