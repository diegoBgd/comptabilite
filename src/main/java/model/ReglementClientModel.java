package model;

import entite.Encaissement;
import entite.ReglementClient;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class ReglementClientModel {
	public void saveReglementClt(SessionFactory factory, ReglementClient rgl) {
		Session ss = null;
		ss = factory.openSession();
		ss.beginTransaction();
		ss.save(rgl);
		ss.getTransaction().commit();
		ss.close();
	}

	public void updateReglementClt(SessionFactory factory, ReglementClient rgl) {
		Session ss = null;
		ss = factory.openSession();
		ss.beginTransaction();
		ss.update(rgl);
		ss.getTransaction().commit();
		ss.close();
	}

	public void deleteReglementClt(SessionFactory factory, ReglementClient rgl) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		ss.delete(rgl);
		ss.getTransaction().commit();
		ss.close();
	}

	public ReglementClient getReglementCltById(int id, SessionFactory factory) {
		ReglementClient rgl = null;
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			rgl = (ReglementClient) ss.get(ReglementClient.class, Integer.valueOf(id));
			ss.getTransaction().commit();
			ss.close();
		} catch (Exception e) {
			e.getMessage();
		}
		return rgl;
	}

	@SuppressWarnings("unchecked")
	public List<ReglementClient> getListReglement(SessionFactory factory, int idExercice, Date deb, Date fin,
			Encaissement encaissement) {
		List<ReglementClient> list = null;
		try {
			Session session = factory.openSession();
			session.beginTransaction();

			String sql = "Select R from ReglementClient R where R.idExercise=:ex";

			if (deb != null) {
				sql += " AND R.dateReglement>=:dteDeb";
			}
			if (fin != null) {
				sql += " AND R.dateReglement<=:dteFin";
			}
			if (encaissement != null)
				sql += " AND R.encaissement=:enc";

			sql += " ORDER BY R.dateReglement";

			Query<?> query = session.createQuery(sql);

			query.setParameter("ex", idExercice);

			if (deb != null)
				query.setParameter("dteDeb", deb);

			if (fin != null)
				query.setParameter("dteFin", fin);

			if (encaissement != null)
				query.setParameter("enc", encaissement);

			list = (List<ReglementClient>) query.getResultList();

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}
}
