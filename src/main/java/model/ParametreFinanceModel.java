package model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import entite.ParametreFinance;

public class ParametreFinanceModel {

	public ParametreFinanceModel() {

	}

	public void saveParametreFinance(SessionFactory factory, ParametreFinance prm) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		if (prm.getIdParam() == 0) {
			ss.save(prm);
		} else {
			ss.update(prm);
		}
		ss.getTransaction().commit();
		ss.close();
	}

	public void deleteParametreFinance(SessionFactory factory, ParametreFinance prm) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		ss.delete(prm);
		ss.getTransaction().commit();
		ss.close();
	}

	public ParametreFinance getParameter(SessionFactory factory) {
		Session ss = null;
		ParametreFinance prm = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			@SuppressWarnings("rawtypes")
			Query query = ss.createQuery(
					"SELECT p from ParametreFinance p WHERE p.idParam=(SELECT MAX(p.idParam) FROM ParametreFinance p)");
			prm = (ParametreFinance) query.getSingleResult();
			ss.getTransaction().commit();
			ss.close();

		} catch (Exception e) {

			e.getMessage();
		}
		return prm;
	}

}
