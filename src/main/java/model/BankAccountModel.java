 package model;
 
 import entite.BankAccount;
 import entite.Banque;
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
 
  
 public class BankAccountModel
 {
   public void saveBankAcount(SessionFactory factory, BankAccount bkAc) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.save(bkAc);
     ss.getTransaction().commit();
     ss.close();
   }
 
   
   public void updateBankAcount(SessionFactory factory, BankAccount bkAc) {
     Session ss = null;
     ss = factory.openSession();
     ss.beginTransaction();
     ss.update(bkAc);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public void deleteBankAcount(SessionFactory factory, BankAccount bkAc) {
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     ss.delete(bkAc);
     ss.getTransaction().commit();
     ss.close();
   }
   
   public BankAccount getBankAccountById(int id, SessionFactory factory) {
     BankAccount bkAc = null;
     Session ss = null;
     try {
       ss = factory.openSession();
       ss.beginTransaction();
       bkAc = (BankAccount)ss.get(BankAccount.class, Integer.valueOf(id));
       ss.getTransaction().commit();
       ss.close();
     } catch (Exception e) {
       e.getMessage();
     } 
     return bkAc;
   }
   
   public BankAccount getBankAccountByCode(SessionFactory factory, String code) {
     BankAccount bk = null;
     Session ss = null;
     
     ss = factory.openSession();
     ss.beginTransaction();
     Criteria cr = ss.createCriteria(BankAccount.class);
     SimpleExpression simpleExpression = Restrictions.eq("accCode", code);
     cr.add((Criterion)simpleExpression);
     bk = (BankAccount)cr.uniqueResult();
     ss.getTransaction().commit();
     ss.close();
     
     return bk;
   }
   
   public List<BankAccount> getListBankAccount(SessionFactory factory, String libelle) {
     List<BankAccount> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       Criteria cr = session.createCriteria(BankAccount.class);
       if (libelle != null && !libelle.equals(""))
         cr.add((Criterion)Restrictions.like("libelle", libelle, MatchMode.ANYWHERE)); 
       cr.addOrder(Order.asc("accCode"));
       
       list = cr.list();
       session.getTransaction().commit();
       session.close();
     }
     catch (Exception e) {
       System.out.println(e.toString());
     } 
     return list;
   }
   
   public List<BankAccount> getListAccount(SessionFactory factory, Banque bk) {
     List<BankAccount> list = null;
     try {
       Session session = factory.openSession();
       session.beginTransaction();
       
       CriteriaBuilder cb = session.getCriteriaBuilder();
       CriteriaQuery<BankAccount> qr = cb.createQuery(BankAccount.class);
       Root<BankAccount> ec = qr.from(BankAccount.class);
       List<Predicate> predicates = new ArrayList<>();
       predicates.add(cb.equal((Expression)ec.get("bank"), bk));
       
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


