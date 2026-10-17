package model;

import java.util.List;
import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import entite.EtatFinanciersCompte;

public class EtatFinanciersCompteModel {

	public EtatFinanciersCompteModel() {
	}

	public void saveMapping(SessionFactory factory, EtatFinanciersCompte m) {
		Session ss = null;
		 Transaction tx = null;
		try {
			
			ss = factory.openSession();
			tx = ss.beginTransaction();

			if (m.getIdMapping()==null ||  m.getIdMapping() == 0)
				ss.save(m);
			else
				ss.update(m);
			
			 tx.commit();
			 
		} catch (Exception ex) {
			if (tx != null) tx.rollback();
	        ex.printStackTrace();
	       System.out.println("Erreur lors de l'enregistrement de la règle de mapping "+ ex.getMessage());
		} finally {
			if (ss != null)
				ss.close();
		}
	}

	public void deleteMapping(SessionFactory factory, EtatFinanciersCompte m) {
	    Session ss = null;
	    try {
	        ss = factory.openSession();
	        ss.beginTransaction();
	        ss.delete(m);            // ← cascade supprime aussi les inclusions/exclusions
	        ss.getTransaction().commit();
	    } catch (Exception ex) {
	        if (ss != null && ss.getTransaction().isActive()) {
	            ss.getTransaction().rollback();
	        }
	        ex.printStackTrace();
	        throw new RuntimeException("Erreur lors de la suppression", ex);
	    } finally {
	        if (ss != null) ss.close();
	    }
	}
	
	public EtatFinanciersCompte getMappingById(int id, SessionFactory factory) {
		EtatFinanciersCompte m = null;
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			m = (EtatFinanciersCompte) ss.get(EtatFinanciersCompte.class, Integer.valueOf(id));
			ss.getTransaction().commit();
		} catch (Exception ex) {
			ex.getMessage();
		} finally {
			if (ss != null)
				ss.close();
		}
		return m;
	}

	@SuppressWarnings("unchecked")
	public List<EtatFinanciersCompte> getMappingsByRubrique(Long idRubrique, SessionFactory factory) {
		List<EtatFinanciersCompte> list = null;
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			Query query = ss.createQuery("SELECT M FROM EtatFinanciersCompte M "
					+ "WHERE M.rubrique.idRubrique = :idRubrique " + "ORDER BY M.ordre ASC");
			query.setParameter("idRubrique", idRubrique);
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
	public List<EtatFinanciersCompte> getMappingsByRubriqueAndNature(int idRubrique, String natureValeur,
			SessionFactory factory) {
		List<EtatFinanciersCompte> list = null;
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			Query query = ss
					.createQuery("SELECT M FROM EtatFinanciersCompte M " + "WHERE M.rubrique.idRubrique = :idRubrique "
							+ "AND M.natureValeur = :nature " + "ORDER BY M.ordre ASC");
			query.setParameter("idRubrique", idRubrique);
			query.setParameter("nature", natureValeur);
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