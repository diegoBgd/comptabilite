package model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import entite.Departement;
import entite.Direction;



public class DepartementModel {

	public DepartementModel() {
		
	}
	public void saveDirection(SessionFactory factory, Departement dpt) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		ss.save(dpt);
		ss.getTransaction().commit();
		ss.close();
	}

	public void updateDiretcion(SessionFactory factory, Departement dpt) {
		Session ss = null;
		ss = factory.openSession();
		ss.beginTransaction();
		ss.update(dpt);
		ss.getTransaction().commit();
		ss.close();
	}

	public void deleteDirection(SessionFactory factory, Departement dpt) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		ss.delete(dpt);
		ss.getTransaction().commit();
		ss.close();
	}

	@SuppressWarnings("unchecked")
	public List<Departement> getListeDirection(SessionFactory factory) {
		Session ss = null;
		List<Departement> list = new ArrayList<Departement>();
		try {
			ss = factory.openSession();
			ss.beginTransaction();

			String sql = "SELECT D from Departement D ";
			Query<?> query = ss.createQuery(sql);
			list = (List<Departement>) query.getResultList();

			ss.getTransaction().commit();
			ss.close();
		} catch (Exception e) {
			System.out.println(e.toString());
			ss.close();
		}
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<Departement> getListeDirection(SessionFactory factory,Direction direction) {
		Session ss = null;
		List<Departement> list = new ArrayList<Departement>();
		try {
			ss = factory.openSession();
			ss.beginTransaction();

			String sql = "SELECT D from Departement D WHERE D.direction=:dir";
			Query<?> query = ss.createQuery(sql);
			query.setParameter("dir", direction);	
			list = (List<Departement>) query.getResultList();

			ss.getTransaction().commit();
			ss.close();
		} catch (Exception e) {
			System.out.println(e.toString());
			ss.close();
		}
		return list;
	}
}
