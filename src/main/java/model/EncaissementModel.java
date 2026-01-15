package model;

import entite.BankAccount;
import entite.Banque;

import entite.Encaissement;
import entite.TypeEcriture;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class EncaissementModel {
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
			enc = (Encaissement) ss.get(Encaissement.class, Integer.valueOf(id));
			ss.getTransaction().commit();
			ss.close();
		} catch (Exception e) {
			e.getMessage();
		}
		return enc;
	}

	@SuppressWarnings("unchecked")
	public List<Encaissement> getListEncaissement(SessionFactory factory, int idExercice, Date deb, Date fin,
			 TypeEcriture type) {
		List<Encaissement> list = null;
		Session session = factory.openSession();
		session.beginTransaction();

		try {

			String sql = "Select E from Encaissement E where E.idExercise=:ex";
			if (deb != null)
				sql += " AND E.dateOperation>=:dteDb";
			if (fin != null)
				sql += " AND E.dateOperation<=:dteFn";
			if(type!=null)
				sql+=" AND E.typeEntree=:typIn";
			
			sql += " ORDER BY E.dateOperation";

			Query<?> query = session.createQuery(sql);
			query.setParameter("ex", idExercice);

			if (deb != null) 
				query.setParameter("dteDb", deb);		
			if (fin != null) 
				query.setParameter("dteFn", fin);			
			if(type!=null)
				query.setParameter("typIn", type);
			
			list = (List<Encaissement>) query.getResultList();
			
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
		
			System.out.println(e.toString());
			session.getTransaction().rollback();
			session.close();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Encaissement> getHistoriquEncaissement(SessionFactory factory, int idExercice, Date deb, Date fin,
			Banque bank, BankAccount cpte) {
		List<Encaissement> list = null;
		Session session = factory.openSession();
		session.beginTransaction();

		try {

			String sql = "Select E from Encaissement E where E.idExercise=:ex";
			if (deb != null)
				sql += " AND E.dateOperation>=:dteDb";
			if (fin != null)
				sql += " AND E.dateOperation<=:dteFn";
			if (bank != null)
				sql += " AND E.bank=:bk";
			if (cpte != null)
				sql += " AND E.compteBank=:bkAc";

			sql += " ORDER BY E.dateOperation";

			Query<?> query = session.createQuery(sql);
			query.setParameter("ex", idExercice);

			if (deb != null) 
				query.setParameter("dteDb", deb);		
			if (fin != null) 
				query.setParameter("dteFn", fin);			
			if (bank != null)
				query.setParameter("bk", bank);
			if (cpte != null)
				query.setParameter("bkAc", cpte);
			
			list = (List<Encaissement>) query.getResultList();
			
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
		
			System.out.println(e.toString());
			session.getTransaction().rollback();
			session.close();
		}
		return list;
	}
}
