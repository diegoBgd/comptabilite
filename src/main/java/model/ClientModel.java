package model;

import entite.Clients;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

public class ClientModel implements Serializable {
	private static final long serialVersionUID = -7578115209663432985L;

	public void saveClient(SessionFactory factory, Clients clt) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		ss.save(clt);
		ss.getTransaction().commit();
		ss.close();
	}

	public void updateClient(SessionFactory factory, Clients clt) {
		Session ss = null;
		ss = factory.openSession();
		ss.beginTransaction();
		ss.update(clt);
		ss.getTransaction().commit();
		ss.close();
	}

	public void deleteClient(SessionFactory factory, Clients clt) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		ss.delete(clt);
		ss.getTransaction().commit();
		ss.close();
	}

	public Clients getClientById(int id, SessionFactory factory) {
		Clients clt = null;
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			clt = (Clients) ss.get(Clients.class, Integer.valueOf(id));
			ss.getTransaction().commit();
			ss.close();
		} catch (Exception e) {
			e.getMessage();
		}
		return clt;
	}

	public Clients getClientByCode(SessionFactory factory, String code) {
		Clients clt = null;
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		Criteria cr = ss.createCriteria(Clients.class);
		SimpleExpression simpleExpression = Restrictions.eq("codeClt", code);
		cr.add((Criterion) simpleExpression);
		clt = (Clients) cr.uniqueResult();
		ss.getTransaction().commit();
		ss.close();

		return clt;
	}

	public List<Clients> getListClients(SessionFactory factory, String libelle) {
		List<Clients> list = null;
		try {
			Session session = factory.openSession();
			session.beginTransaction();
			Criteria cr = session.createCriteria(Clients.class);
			if (libelle != null && !libelle.equals(""))
				cr.add((Criterion) Restrictions.like("libelle", libelle, MatchMode.ANYWHERE));
			cr.addOrder(Order.asc("codeClt"));

			list = cr.list();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}
}
