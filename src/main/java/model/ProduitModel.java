 package model;
 
 import entite.Produit;
 import entite.SoufamilleProd;
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
 import org.hibernate.criterion.MatchMode;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.hibernate.criterion.SimpleExpression;
 
  
 public class ProduitModel
 {
   public void saveProduit(SessionFactory factory, Produit prd) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(prd);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateProduit(SessionFactory factory, Produit prd) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(prd);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void deleteProduit(SessionFactory factory, Produit prd) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(prd);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public Produit getProduitById(int id, SessionFactory factory) {
     Produit prd = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       prd = (Produit)ss.get(Produit.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return prd;
   }
 
   
   public Produit getProduitByCode(SessionFactory factory, String code) {
     Produit prd = null;
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     Criteria cr = ss.createCriteria(Produit.class);
     SimpleExpression simpleExpression = Restrictions.eq("codePrd", code);
     cr.add((Criterion)simpleExpression);
     prd = (Produit)cr.uniqueResult();
     ss.getTransaction().commit();
     ss.close();
     
     return prd;
   }
 
   
   public List<Produit> getListProduit(SessionFactory factory, String libelle) {
     List<Produit> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(Produit.class);
       if (libelle != null && !libelle.equals(""))
         cr.add((Criterion)Restrictions.like("libelle", libelle, MatchMode.ANYWHERE)); 
       cr.addOrder(Order.asc("codePrd"));
       
       list = cr.list();
       session.getTransaction().commit();
       session.close();
     }
     catch (Exception e) {
       System.out.println(e.toString());
     } 
     return list;
   }
   
   public List<Produit> getListProduitSfamille(SessionFactory factory, SoufamilleProd sf) {
     List<Produit> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       
       CriteriaBuilder cb = session.getCriteriaBuilder();
       CriteriaQuery<Produit> qr = cb.createQuery(Produit.class);
       Root<Produit> ec = qr.from(Produit.class);
       List<Predicate> predicates = new ArrayList<>();
       predicates.add(cb.equal((Expression)ec.get("sfamille"), sf));
       
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


