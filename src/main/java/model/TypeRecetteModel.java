 package model;
 
 import entite.TypeCharge;
 import entite.TypeRecette;
 import java.util.List;
 import org.hibernate.Criteria;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.criterion.Criterion;
 import org.hibernate.criterion.MatchMode;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.hibernate.criterion.SimpleExpression;
  
 
 public class TypeRecetteModel
 {
   public void saveTypeProduit(SessionFactory factory, TypeRecette trc) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(trc);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateTypeProduit(SessionFactory factory, TypeRecette trc) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(trc);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void deleteTypeProduit(SessionFactory factory, TypeRecette trc) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(trc);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public TypeRecette getTypeProduitById(int id, SessionFactory factory) {
     TypeRecette trc = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       trc = (TypeRecette)ss.get(TypeRecette.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return trc;
   }
 
   
   public TypeRecette getTypeProduiByCode(SessionFactory factory, String code) {
     TypeRecette trc = null;
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     Criteria cr = ss.createCriteria(TypeCharge.class);
     SimpleExpression simpleExpression = Restrictions.eq("codeRec", code);
     cr.add((Criterion)simpleExpression);
     trc = (TypeRecette)cr.uniqueResult();
     ss.getTransaction().commit();
     ss.close();
     
     return trc;
   }
 
   
   public List<TypeRecette> getListTypeRecette(SessionFactory factory, String libelle) {
     List<TypeRecette> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(TypeRecette.class);
       if (libelle != null && !libelle.equals(""))
         cr.add((Criterion)Restrictions.like("libelle", libelle, MatchMode.ANYWHERE)); 
       cr.addOrder(Order.asc("codeRec"));
       
       list = cr.list();
       session.getTransaction().commit();
       session.close();
     } catch (Exception e) {
       System.out.println(e.toString());
     } 
     return list;
   }
 }


