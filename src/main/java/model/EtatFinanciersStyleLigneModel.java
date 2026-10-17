package model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.persistence.Query;
import entite.EtatFinanciersStyleLigne;

public class EtatFinanciersStyleLigneModel {

	public EtatFinanciersStyleLigneModel() {
	}

	public void saveStyle(SessionFactory factory, EtatFinanciersStyleLigne s) {
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			if (s.getIdStyle()==null || s.getIdStyle() == 0)
				ss.save(s);
			else
				ss.update(s);
			ss.getTransaction().commit();
		} catch (Exception ex) {
			if (ss != null)
				ss.getTransaction().rollback();
			ex.getMessage();
			System.out.println(ex.getMessage());
		} finally {
			if (ss != null)
				ss.close();
		}
	}

	public void deleteStyle(SessionFactory factory, EtatFinanciersStyleLigne s) {
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			ss.delete(s);
			ss.getTransaction().commit();
		} catch (Exception ex) {
			if (ss != null)
				ss.getTransaction().rollback();
			ex.getMessage();
		} finally {
			if (ss != null)
				ss.close();
		}
	}

	public EtatFinanciersStyleLigne getStyleById(int id, SessionFactory factory) {
		EtatFinanciersStyleLigne s = null;
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			s = (EtatFinanciersStyleLigne) ss.get(EtatFinanciersStyleLigne.class, Integer.valueOf(id));
			ss.getTransaction().commit();
		} catch (Exception ex) {
			ex.getMessage();
		} finally {
			if (ss != null)
				ss.close();
		}
		return s;
	}

	public EtatFinanciersStyleLigne getStyleByTypeLigne(String typeLigne, SessionFactory factory) {
		EtatFinanciersStyleLigne s = null;
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			Query query = ss.createQuery("SELECT S FROM EtatFinanciersStyleLigne S WHERE S.typeLigne = :type");
			query.setParameter("type", typeLigne);
			s = (EtatFinanciersStyleLigne) query.getSingleResult();
			ss.getTransaction().commit();
		} catch (Exception ex) {
			ex.getMessage();
		} finally {
			if (ss != null)
				ss.close();
		}
		return s;
	}

	@SuppressWarnings("unchecked")
	public List<EtatFinanciersStyleLigne> getAllStyles(SessionFactory factory) {
		List<EtatFinanciersStyleLigne> list = null;
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			Query query = ss.createQuery("SELECT S FROM EtatFinanciersStyleLigne S ORDER BY S.typeLigne");
			list = query.getResultList();
			ss.getTransaction().commit();
		} catch (Exception ex) {
			ex.getMessage();
		} finally {
			if (ss != null)
				ss.close();
		}
		return list;
	}
}