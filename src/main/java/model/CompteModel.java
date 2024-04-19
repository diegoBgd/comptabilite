package model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import entite.Compte;

public class CompteModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7804976354629720345L;

	public void saveCompte(SessionFactory factory, Compte cpt) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		ss.save(cpt);
		ss.getTransaction().commit();
		ss.close();
	}
	public void updateCompte(SessionFactory factory, Compte cpt) {
		Session ss = null;
		ss = factory.openSession();
		ss.beginTransaction();
		ss.update(cpt);
		ss.getTransaction().commit();
		ss.close();
	}
	public void deleteCompte(SessionFactory factory, Compte cpt) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		ss.delete(cpt);
		ss.getTransaction().commit();
		ss.close();
	}
	public Compte getCompteById(int id, SessionFactory factory) {
		Compte cpt = null;
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			cpt = (Compte) ss.get(Compte.class, Integer.valueOf(id));
			ss.getTransaction().commit();
			ss.close();
		} catch (Exception e) {
			e.getMessage();
		}
		return cpt;
	}
	public Compte getCompteByCode(SessionFactory factory, String code) {
		Compte cpt = null;
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		@SuppressWarnings("deprecation")
		Criteria cr = ss.createCriteria(Compte.class);
		SimpleExpression simpleExpression = Restrictions.eq("compteCod", code);
		cr.add((Criterion) simpleExpression);
		cpt = (Compte) cr.uniqueResult();
		ss.getTransaction().commit();
		ss.close();

		return cpt;
	}
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Compte> getListCompte(SessionFactory factory, String libelle, String motCompte) {
		List<Compte> list = null;
		try {
			Session session = factory.openSession();
			session.beginTransaction();

			Criteria cr = session.createCriteria(Compte.class);

			if (libelle != null && !libelle.equals("")) {
				cr.add((Criterion) Restrictions.like("libelle", libelle, MatchMode.ANYWHERE));
			}
			if (motCompte != null && !motCompte.equals("")) {
				cr.add((Criterion) Restrictions.like("compteCod", motCompte, MatchMode.START));
			}
			cr.addOrder(Order.asc("compteCod"));

			list = cr.list();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Compte> getPlanComptable(SessionFactory factory, String compteDeb, String compteFin) {
		List<Compte> list = null;
		try {
			Session session = factory.openSession();
			session.beginTransaction();

			String sql = "SELECT C from Compte C WHERE 1=1 ";

			if (compteDeb != null && !compteDeb.equals(""))
				sql += " AND C.compteCod>=:deb";
			if (compteFin != null && !compteFin.equals(""))
				sql += " AND C.compteCod<=:fin";

			sql = sql + " ORDER BY C.compteCod ASC";

			Query query = session.createQuery(sql);

			if (compteDeb != null && !compteDeb.equals("")) {
				query.setParameter("deb", compteDeb);
			}
			if (compteFin != null && !compteFin.equals("")) {
				query.setParameter("fin", compteFin);

			}
			list = query.getResultList();

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Compte> getListCompte(SessionFactory factory) {
	     List<Compte> listCpt = null;
	 
	     try { 
	       Session session = factory.openSession();
	       session.beginTransaction();
	       Query query = session.createQuery("SELECT C from Compte C ORDER BY C.compteCod ASC");
	     
	 
	       
	       listCpt = query.getResultList();
	       session.getTransaction().commit();
	       session.close();
	     }
	     catch (Exception e) {
	       System.out.println(e.toString());
	     } 
	     return listCpt;
		}
	
	@SuppressWarnings("unchecked")
	public List<Compte> getListCompteCO(SessionFactory factory, int type) {
	     List<Compte> listCpt = null;
	 
	     try { 
	       Session session = factory.openSession();
	       session.beginTransaction();
	       Query query = session.createQuery("SELECT C from Compte C WHERE C.compteCod BETWEEN :a AND :b ORDER BY C.compteCod ASC");
	       switch (type) {
	         case 0:
	           query.setParameter("a", "1");
	           query.setParameter("b", "6");
	           break;
	         case 1:
	           query.setParameter("a", "6");
	           query.setParameter("b", "8");
	           break;
	       } 
	 
	       
	       listCpt = query.getResultList();
	       session.getTransaction().commit();
	       session.close();
	     }
	     catch (Exception e) {
	       System.out.println(e.toString());
	     } 
	     return listCpt;
		}
}
