 package model;
 
 import entite.Depense;
 import entite.ReglementFournisseur;
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
 
 
 
 
 
 public class ReglementModel
 {
   public void saveReglement(SessionFactory factory, ReglementFournisseur reg) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(reg);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateReglement(SessionFactory factory, ReglementFournisseur reg) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(reg);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void deleteReglement(SessionFactory factory, ReglementFournisseur reg) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(reg);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public ReglementFournisseur getReglementById(int id, SessionFactory factory) {
     ReglementFournisseur reg = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       reg = (ReglementFournisseur)ss.get(ReglementFournisseur.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return reg;
   }
   
   public List<ReglementFournisseur> getListReglement(SessionFactory factory, TypeEcriture type, Depense dep, int idExercice, Date deb, Date fin) {
     List<ReglementFournisseur> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       
       CriteriaBuilder cb = session.getCriteriaBuilder();
       CriteriaQuery<ReglementFournisseur> qr = cb.createQuery(ReglementFournisseur.class);
       Root<ReglementFournisseur> ec = qr.from(ReglementFournisseur.class);
       List<Predicate> predicates = new ArrayList<>();
       
       predicates.add(cb.equal((Expression)ec.get("idExercise"), Integer.valueOf(idExercice)));
       
       if (deb != null) {
         predicates.add(cb.greaterThanOrEqualTo((Expression)ec.get("dateReglement"), deb));
       }
       if (fin != null) {
         predicates.add(cb.lessThanOrEqualTo((Expression)ec.get("dateReglement"), fin));
       }
       if (dep != null) {
         predicates.add(cb.equal((Expression)ec.get("depense"), dep));
       }
       predicates.add(cb.equal((Expression)ec.get("typeOperation"), type));
       
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

