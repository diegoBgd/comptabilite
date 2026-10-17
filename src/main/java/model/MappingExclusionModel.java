package model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.persistence.Query;
import entite.MappingExclusion;

public class MappingExclusionModel {

    public MappingExclusionModel() {
    }

    public void saveExclusion(SessionFactory factory, MappingExclusion e) {
        Session ss = null;
        try {
            ss = factory.openSession();
            ss.beginTransaction();
            if (e.getIdExclusion() == 0)
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

    public void deleteExclusion(SessionFactory factory, MappingExclusion e) {
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

    public MappingExclusion getExclusionById(int id, SessionFactory factory) {
        MappingExclusion e = null;
        Session ss = null;
        try {
            ss = factory.openSession();
            ss.beginTransaction();
            e = (MappingExclusion) ss.get(MappingExclusion.class, Integer.valueOf(id));
            ss.getTransaction().commit();
        } catch (Exception ex) {
            ex.getMessage();
        } finally {
            if (ss != null) ss.close();
        }
        return e;
    }

    @SuppressWarnings("unchecked")
    public List<MappingExclusion> getExclusionsByMapping(Long idMapping, SessionFactory factory) {
        List<MappingExclusion> list = null;
        Session ss = null;
        try {
            ss = factory.openSession();
            ss.beginTransaction();
            Query query = ss.createQuery(
                "SELECT E FROM MappingExclusion E " +
                "WHERE E.mapping.idMapping = :idMapping " +
                "ORDER BY E.compteDebut ASC");
            query.setParameter("idMapping", idMapping);
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

