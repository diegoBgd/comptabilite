package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import entite.Departement;
import entite.Service;

public class ServiceModel {

	public ServiceModel() {
		
	}

	public void saveDirection(SessionFactory factory, Service serv) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		ss.save(serv);
		ss.getTransaction().commit();
		ss.close();
	}

	public void updateDiretcion(SessionFactory factory, Service serv) {
		Session ss = null;
		ss = factory.openSession();
		ss.beginTransaction();
		ss.update(serv);
		ss.getTransaction().commit();
		ss.close();
	}

	public void deleteDirection(SessionFactory factory, Service serv) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		ss.delete(serv);
		ss.getTransaction().commit();
		ss.close();
	}

	@SuppressWarnings("unchecked")
	public List<Service> getListeDirection(SessionFactory factory) {
		Session ss = null;
		List<Service> list = new ArrayList<Service>();
		try {
			ss = factory.openSession();
			ss.beginTransaction();

			String sql = "SELECT S from Service S ";
			Query<?> query = ss.createQuery(sql);
			list = (List<Service>) query.getResultList();

			ss.getTransaction().commit();
			ss.close();
		} catch (Exception e) {
			System.out.println(e.toString());
			ss.close();
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<Service> getListeDirection(SessionFactory factory,Departement dp) {
		Session ss = null;
		List<Service> list = new ArrayList<Service>();
		try {
			ss = factory.openSession();
			ss.beginTransaction();

			String sql = "SELECT S from Service S WHERE S.departement=:dep";
			Query<?> query = ss.createQuery(sql);
			query.setParameter("dep", dp);	
			list = (List<Service>) query.getResultList();

			ss.getTransaction().commit();
			ss.close();
		} catch (Exception e) {
			System.out.println(e.toString());
			ss.close();
		}
		return list;
	}
}
