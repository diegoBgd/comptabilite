 package model;
 
 import entite.User;
 import java.util.List;
 import org.hibernate.Session;
 import org.hibernate.SessionFactory;
 import org.hibernate.query.Query;
  
 
 public class UserModel
 {
   public void saveUser(User u, SessionFactory factory) {
     Session ss = null;
     
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       ss.save(u);
       
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
   }
 
   
   public void updateUser(User u, SessionFactory factory) {
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       ss.update(u);
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
   }
   
   public void deleteUser(User u, SessionFactory factory) {
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       ss.delete(u);
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
   }
   
   @SuppressWarnings("rawtypes")
			public User getUserById(int id, SessionFactory factory) {
     User u = null;
 
     
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       
       Query query = session.createQuery("SELECT p from User p WHERE p.id=:a");
       query.setParameter("a", id);
       u = (User) query.getSingleResult();
       session.getTransaction().commit();
       session.close();
     }
     catch (Exception e) {
       e.getMessage();
     } 
     return u;
   }
 
   
   @SuppressWarnings("rawtypes")
			public boolean isUserExist(SessionFactory factory) {
     boolean b = false;
     Session ss = null;
     User u = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       Query  query = ss.createQuery("SELECT p from User p WHERE p.id=(SELECT MAX(p.id) FROM User p)");
       u = (User) query.getSingleResult();
       ss.getTransaction().commit();
       ss.close();
       
       if (u != null) {
         b = true;
       
       }
     }
     catch (Exception e) {
       b = false;
       e.getMessage();
     } 
     return b;
   }
 
   
   @SuppressWarnings("rawtypes")
			public User getUserByCode(SessionFactory factory, String code) {
     User u = null;
     
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       
       Query query = session.createQuery("SELECT p from User p WHERE p.userCod=:a");
       query.setParameter("a", code);
       u = (User) query.getSingleResult();
       session.getTransaction().commit();
       session.close();
     }
     catch (Exception e) {
       
       e.getMessage();
     } 
     return u;
   }
 
   
   @SuppressWarnings("rawtypes")
			public User getUSerByLogin(SessionFactory factory, String code, String pwd) {
     User u = null;
 
     
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       
       Query  query = session.createQuery("SELECT p from User p WHERE p.userCod=:a AND p.userPwd=:b");
       query.setParameter("a", code);
       query.setParameter("b", pwd);
       u = (User) query.getSingleResult();
       session.getTransaction().commit();
       session.close();
     }
     catch (Exception e) {
       System.out.println(e.toString());
     } 
     return u;
   }
 
 
 
   
   @SuppressWarnings("unchecked")
			public List<User> getListActiveUser(SessionFactory factory) {
     List<User> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Query<User> query = session.createQuery("SELECT p from User p WHERE p.actif=1");
       
       list = query.getResultList();
 
       
       session.getTransaction().commit();
       session.close();
     } catch (Exception e) {
       System.out.println(e.toString());
     } 
     return list;
   }
 
   
   public List<User> getListUser(SessionFactory factory, String mot) {
     List<User> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       @SuppressWarnings("unchecked")
				Query<User> query = session.createQuery("SELECT p from User p order by p.userCod");
       
       list = query.getResultList();
       
       session.getTransaction().commit();
       session.close();
     } catch (Exception e) {
       System.out.println(e.toString());
     } 
     return list;
   }
 }


