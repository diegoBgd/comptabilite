package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

import javax.persistence.Query;
import entite.EtatFinanciersRubrique;

public class EtatFinanciersRubriqueModel {

	public EtatFinanciersRubriqueModel() {
	}

	public void saveRubrique(SessionFactory factory, EtatFinanciersRubrique r) {
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			if (r.getIdRubrique() == null || r.getIdRubrique() == 0L)
				ss.save(r);
			else
				ss.update(r);
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

	public void deleteRubrique(SessionFactory factory, EtatFinanciersRubrique r) {
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			ss.delete(r);
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

	public EtatFinanciersRubrique getRubriqueById(Long id, SessionFactory factory) {
		EtatFinanciersRubrique r = null;
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			r = (EtatFinanciersRubrique) ss.get(EtatFinanciersRubrique.class, id);
			ss.getTransaction().commit();
		} catch (Exception ex) {
			ex.getMessage();
		} finally {
			if (ss != null)
				ss.close();
		}
		return r;
	}

	public EtatFinanciersRubrique getRubriqueByCode(int idEtat, String code, SessionFactory factory) {
		EtatFinanciersRubrique r = null;
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			Query query = ss.createQuery("SELECT R FROM EtatFinanciersRubrique R "
					+ "WHERE R.etat.idEtat = :idEtat AND R.codeRubrique = :code");
			query.setParameter("idEtat", idEtat);
			query.setParameter("code", code);
			r = (EtatFinanciersRubrique) query.getSingleResult();
			ss.getTransaction().commit();
		} catch (Exception ex) {
			ex.getMessage();
		} finally {
			if (ss != null)
				ss.close();
		}
		return r;
	}

	@SuppressWarnings("unchecked")
	public List<EtatFinanciersRubrique> getRubriquesByEtat(Long idEtat, SessionFactory factory) {
		List<EtatFinanciersRubrique> list = null;
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			Query query = ss.createQuery("SELECT R FROM EtatFinanciersRubrique R " + "WHERE R.etat.idEtat = :idEtat "
					+ "ORDER BY R.ordreAffichage ASC");
			query.setParameter("idEtat", idEtat);
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

	@SuppressWarnings("unchecked")
	public List<EtatFinanciersRubrique> getRubriquesByEtatAndSection(int idEtat, String section,
			SessionFactory factory) {
		List<EtatFinanciersRubrique> list = null;
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			Query query = ss.createQuery("SELECT R FROM EtatFinanciersRubrique R "
					+ "WHERE R.etat.idEtat = :idEtat AND R.section = :section " + "ORDER BY R.ordreAffichage ASC");
			query.setParameter("idEtat", idEtat);
			query.setParameter("section", section);
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

	@SuppressWarnings("unchecked")
	public List<EtatFinanciersRubrique> getRubriquesByParent(int idParent, SessionFactory factory) {
		List<EtatFinanciersRubrique> list = null;
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			Query query = ss.createQuery("SELECT R FROM EtatFinanciersRubrique R "
					+ "WHERE R.rubriqueParent.idRubrique = :idParent " + "ORDER BY R.ordreAffichage ASC");
			query.setParameter("idParent", idParent);
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