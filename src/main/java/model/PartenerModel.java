package model;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entite.Partenaire;
import entite.TypePartener;

public class PartenerModel {

	public PartenerModel() {

	}

	public void savePartener(SessionFactory factory, Partenaire p) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		if (p.getId() == 0)
			ss.save(p);
		else
			ss.update(p);

		ss.getTransaction().commit();
		ss.close();
	}

	public void deletePartener(SessionFactory factory, Partenaire p) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		ss.delete(p);
		ss.getTransaction().commit();
		ss.close();
	}

	public Partenaire getPartenaireById(int id, SessionFactory factory) {
		Partenaire p = null;
		Session ss = null;
		try {
			
		ss = factory.openSession();
			ss.beginTransaction();
			p = ss.get(Partenaire.class, Integer.valueOf(id));
			ss.getTransaction().commit();
			ss.close();
		} catch (Exception e) {
			e.getMessage();
		}
		return p;
	}

	public Partenaire getPartenaireByCode(String code, TypePartener type, SessionFactory factory) {
		Partenaire p = null;

		try {
			Session session = factory.openSession();
			session.beginTransaction();
			Query query = session.createQuery("SELECT P from Partenaire P WHERE P.codePartener = :a AND P.pType=:b");
			query.setParameter("a", code);
			query.setParameter("b", type);
			p = (Partenaire) query.getSingleResult();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.getMessage();
		}
		return p;
	}

	
	@SuppressWarnings("unchecked")
	public List<Partenaire> getAllPartenaire(TypePartener type, SessionFactory factory) {

		List<Partenaire> list = null;
		try {
			Session session = factory.openSession();
			session.beginTransaction();
			Query query = session.createQuery("SELECT P from Partenaire P WHERE P.pType=:a");

			query.setParameter("a", type);
			list = query.getResultList();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.getMessage();
		}
		return list;
	}
}
