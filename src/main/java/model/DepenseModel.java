package model;

import entite.Depense;
import entite.TypeEcriture;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class DepenseModel {
	public void saveDepense(SessionFactory factory, Depense dep) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		ss.save(dep);
		ss.getTransaction().commit();
		ss.close();
	}

	public void updateDepense(SessionFactory factory, Depense dep) {
		Session ss = null;
		ss = factory.openSession();
		ss.beginTransaction();
		ss.update(dep);
		ss.getTransaction().commit();
		ss.close();
	}

	public void deleteDepense(SessionFactory factory, Depense dep) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		ss.delete(dep);
		ss.getTransaction().commit();
		ss.close();
	}

	public Depense getDepenseById(int id, SessionFactory factory) {
		Depense dep = null;
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			dep = (Depense) ss.get(Depense.class, Integer.valueOf(id));
			ss.getTransaction().commit();
			ss.close();
		} catch (Exception e) {
			e.getMessage();
		}
		return dep;
	}

	@SuppressWarnings("unchecked")
	public List<Depense> getListDepense(SessionFactory factory, int idExercice, TypeEcriture type, Date deb, Date fin) {
		List<Depense> list = null;
		try {
			Session session = factory.openSession();
			session.beginTransaction();

			String sql = "Select D from Depense D where D.idExercise=:ex";

			if (type != null) {
				sql += " AND D.typeOperation=:typeOp ";
			}
			if (deb != null) {
				sql += "AND D.dateOperation>=:dteDeb";
			}
			if (fin != null) {
				sql += "AND D.dateOperation<=:dteFin";
			}
			sql += " ORDER BY D.dateOperation";

			Query<?> query = session.createQuery(sql);

			query.setParameter("ex", idExercice);

			if (deb != null)
				query.setParameter("dteDeb", deb);

			if (fin != null)
				query.setParameter("dteFin", fin);

			if (type != null) {
				query.setParameter("typeOp", type);
			}

			list = (List<Depense>) query.getResultList();

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}
}
