package model;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entite.ParametreJournaux;
import entite.TypeEcriture;

public class ParametreJournauxModel {

	public void saveParmJournaux(SessionFactory factory, ParametreJournaux pJrnl) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		ss.save(pJrnl);
		ss.getTransaction().commit();
		ss.close();
	}

	public void updateParmJournaux(SessionFactory factory, ParametreJournaux pJrnl) {
		Session ss = null;
		ss = factory.openSession();
		ss.beginTransaction();
		ss.update(pJrnl);
		ss.getTransaction().commit();
		ss.close();
	}

	public void deleteParmJournaux(SessionFactory factory, ParametreJournaux pJrnl) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		ss.delete(pJrnl);
		ss.getTransaction().commit();
		ss.close();
	}

	public ParametreJournaux getParmJournaux(String code, TypeEcriture type, SessionFactory factory) {
		ParametreJournaux p = null;

		try {
			Session session = factory.openSession();
			session.beginTransaction();
			Query query = session
					.createQuery("SELECT P from ParametreJournaux P WHERE P.codeJournal = :a AND P.typeOperation=:b");
			query.setParameter("a", code);
			query.setParameter("b", type);
			p = (ParametreJournaux) query.getSingleResult();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.getMessage();
		}
		return p;
	}

	@SuppressWarnings("unchecked")
	public List<ParametreJournaux> getAllParmJournaux(SessionFactory factory) {

		List<ParametreJournaux> list = null;
		try {
			Session session = factory.openSession();
			session.beginTransaction();
			Query query = session.createQuery("SELECT P from ParametreJournaux ");

			list = query.getResultList();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.getMessage();
		}
		return list;
	}
}
