package model;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.persistence.Query;
import entite.MappingInclusion;

public class MappingInclusionModel {

    public MappingInclusionModel() {
    }

    public void saveInclusion(SessionFactory factory, MappingInclusion i) {
        Session ss = null;
        try {
            ss = factory.openSession();
            ss.beginTransaction();
            if (i.getIdInclusion() == 0)
                ss.save(i);
            else
                ss.update(i);
            ss.getTransaction().commit();
        } catch (Exception ex) {
            if (ss != null) ss.getTransaction().rollback();
            ex.getMessage();
        } finally {
            if (ss != null) ss.close();
        }
    }

    public void deleteInclusion(SessionFactory factory, MappingInclusion i) {
        Session ss = null;
        try {
            ss = factory.openSession();
            ss.beginTransaction();
            ss.delete(i);
            ss.getTransaction().commit();
        } catch (Exception ex) {
            if (ss != null) ss.getTransaction().rollback();
            ex.getMessage();
        } finally {
            if (ss != null) ss.close();
        }
    }

    public MappingInclusion getInclusionById(int id, SessionFactory factory) {
        MappingInclusion i = null;
        Session ss = null;
        try {
            ss = factory.openSession();
            ss.beginTransaction();
            i = (MappingInclusion) ss.get(MappingInclusion.class, Integer.valueOf(id));
            ss.getTransaction().commit();
        } catch (Exception ex) {
            ex.getMessage();
        } finally {
            if (ss != null) ss.close();
        }
        return i;
    }

    @SuppressWarnings("unchecked")
    public List<MappingInclusion> getInclusionsByMapping(Long idMapping, SessionFactory factory) {
        List<MappingInclusion> list = null;
        Session ss = null;
        try {
            ss = factory.openSession();
            ss.beginTransaction();
            Query query = ss.createQuery(
                "SELECT I FROM MappingInclusion I " +
                "WHERE I.mapping.idMapping = :idMapping " +
                "ORDER BY I.compteDebut ASC");
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
