package model;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import entite.Departement;
import entite.Direction;

public class DirectionModel {

	public DirectionModel() {
		
	}

	public void saveDirection(SessionFactory factory, Direction dir) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		ss.save(dir);
		ss.getTransaction().commit();
		ss.close();
	}

	public void updateDiretcion(SessionFactory factory, Direction dir) {
		Session ss = null;
		ss = factory.openSession();
		ss.beginTransaction();
		ss.update(dir);
		ss.getTransaction().commit();
		ss.close();
	}

	public void deleteDirection(SessionFactory factory, Direction dir) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		ss.delete(dir);
		ss.getTransaction().commit();
		ss.close();
	}
	
	public Direction getDirection(SessionFactory factory,String code) {
		Session ss = null;
		Direction dir=null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();

			String sql = "SELECT D from Direction D WHERE D.code=:cde";
			Query<?> query = ss.createQuery(sql);
			query.setParameter("cde", code);	
			dir =  (Direction) query.getSingleResult();

			ss.getTransaction().commit();
			ss.close();
		} catch (Exception e) {
			System.out.println(e.toString());
			ss.close();
		}
		return dir;
	}

	@SuppressWarnings("unchecked")
	public List<Direction> getListeDirection(SessionFactory factory) {
		Session ss = null;
		List<Direction> list = new ArrayList<Direction>();
		try {
			ss = factory.openSession();
			ss.beginTransaction();

			String sql = "SELECT D from Direction D ";
			Query<?> query = ss.createQuery(sql);
			list = (List<Direction>) query.getResultList();

			ss.getTransaction().commit();
			ss.close();
		} catch (Exception e) {
			System.out.println(e.toString());
			ss.close();
		}
		return list;
	}
}
