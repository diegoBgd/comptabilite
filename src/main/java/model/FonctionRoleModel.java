 package model;
 
 import entite.FonctionRole;
 import java.io.Serializable;
 import java.util.List;
 import org.hibernate.Criteria;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.criterion.Criterion;
 import org.hibernate.criterion.MatchMode;
 import org.hibernate.criterion.Order;
 import org.hibernate.criterion.Restrictions;
 import org.hibernate.criterion.SimpleExpression;
  
 
 public class FonctionRoleModel
   implements Serializable
 {
   private static final long serialVersionUID = -775178508762053038L;
   
   public void saveRole(SessionFactory factory, FonctionRole fx) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(fx);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateRole(SessionFactory factory, FonctionRole fx) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(fx);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void deleteRole(SessionFactory factory, FonctionRole fx) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(fx);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public FonctionRole getRoleById(int id, SessionFactory factory) {
     FonctionRole fx = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       fx = (FonctionRole)ss.get(FonctionRole.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return fx;
   }
   
   public FonctionRole getRoleByCode(SessionFactory factory, String code) {
     FonctionRole fx = null;
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     Criteria cr = ss.createCriteria(FonctionRole.class);
     SimpleExpression simpleExpression = Restrictions.eq("codeRole", code);
     cr.add((Criterion)simpleExpression);
     fx = (FonctionRole)cr.uniqueResult();
     ss.getTransaction().commit();
     ss.close();
     
     return fx;
   }
 
   
   public List<FonctionRole> getListRole(SessionFactory factory, String libelle) {
     List<FonctionRole> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(FonctionRole.class);
       if (libelle != null && !libelle.equals(""))
         cr.add((Criterion)Restrictions.like("libelleRole", libelle, MatchMode.ANYWHERE)); 
       cr.addOrder(Order.asc("codeRole"));
       
       list = cr.list();
       session.getTransaction().commit();
       session.close();
     } catch (Exception e) {
       System.out.println(e.toString());
     } 
     return list;
   }
 }


