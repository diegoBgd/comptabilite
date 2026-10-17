package model;

import java.util.List;


import javax.persistence.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entite.EtatFinanciers;

public class EtatFinanciersModel {

    public EtatFinanciersModel() {
    }

    public void saveEtatFinanciers(SessionFactory factory, EtatFinanciers e) {
        Session ss = null;
        try {
            ss = factory.openSession();
            ss.beginTransaction();
            if (e.getIdEtat() == 0)
                ss.save(e);
            else
                ss.update(e);
            ss.getTransaction().commit();
        } catch (Exception ex) {
            if (ss != null) ss.getTransaction().rollback();
            ex.getMessage();
        } finally {
            if (ss != null) ss.close();
        }
    }

    public void deleteEtatFinanciers(SessionFactory factory, EtatFinanciers e) {
        Session ss = null;
        try {
            ss = factory.openSession();
            ss.beginTransaction();
            ss.delete(e);
            ss.getTransaction().commit();
        } catch (Exception ex) {
            if (ss != null) ss.getTransaction().rollback();
            ex.getMessage();
        } finally {
            if (ss != null) ss.close();
        }
    }

    public EtatFinanciers getEtatFinanciersById(Long id, SessionFactory factory) {
        EtatFinanciers e = null;
        Session ss = null;
        try {
            ss = factory.openSession();
            ss.beginTransaction();
            e = (EtatFinanciers) ss.get(EtatFinanciers.class, id);
            ss.getTransaction().commit();
        } catch (Exception ex) {
            ex.getMessage();
        } finally {
            if (ss != null) ss.close();
        }
        return e;
    }

    public EtatFinanciers getEtatFinanciersByCode(String code, SessionFactory factory) {
        EtatFinanciers e = null;
        Session ss = null;
        try {
            ss = factory.openSession();
            ss.beginTransaction();
            Query query = ss.createQuery("SELECT E FROM EtatFinanciers E WHERE E.codeEtat = :code");
            query.setParameter("code", code);
            e = (EtatFinanciers) query.getSingleResult();
            ss.getTransaction().commit();
        } catch (Exception ex) {
            ex.getMessage();
        } finally {
            if (ss != null) ss.close();
        }
        return e;
    }

    @SuppressWarnings("unchecked")
    public List<EtatFinanciers> getAllEtatFinanciers(SessionFactory factory) {
        List<EtatFinanciers> list = null;
        Session ss = null;
        try {
            ss = factory.openSession();
            ss.beginTransaction();
            Query query = ss.createQuery("SELECT E FROM EtatFinanciers E ORDER BY E.idEtat");
            list = query.getResultList();
            ss.getTransaction().commit();
        } catch (Exception ex) {
            ex.getMessage();
        } finally {
            if (ss != null) ss.close();
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<EtatFinanciers> getAllEtatFinanciersActifs(SessionFactory factory) {
        List<EtatFinanciers> list = null;
        Session ss = null;
        try {
            ss = factory.openSession();
            ss.beginTransaction();
            Query query = ss.createQuery("SELECT E FROM EtatFinanciers E WHERE E.actif = true ORDER BY E.idEtat");
            list = query.getResultList();
            ss.getTransaction().commit();
        } catch (Exception ex) {
            ex.getMessage();
        } finally {
            if (ss != null) ss.close();
        }
        return list;
    }
}