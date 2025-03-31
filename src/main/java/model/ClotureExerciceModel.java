package model;

import org.hibernate.query.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import entite.CloseOpen;


public class ClotureExerciceModel {

	
	public ClotureExerciceModel() {
		
	}
	public String cancelCloseOrOpenPeriod(SessionFactory factory,CloseOpen close) {
		String msg = "";
		Session ss = null;
		ss = factory.openSession();
		ss.beginTransaction();
		
		ss.delete(close);
		
		String sql = "Delete from Ecriture e where e.idExercise=:ex AND e.jrnl=:jnl";

		Query<?> query = ss.createQuery(sql);

		query.setParameter("ex", close.getIdExercice());
		query.setParameter("jnl", close.getCodeJournal());
		
		query.executeUpdate();

	
		ss.getTransaction().commit();
		ss.close();
		msg = "Opération réussie";
		return msg;
	}
	
	@SuppressWarnings("rawtypes")
	public CloseOpen getExerciceCloture(int idEx,SessionFactory factory,String type) {
		CloseOpen c = null;

		try {
			Session session = factory.openSession();
			session.beginTransaction();
			
			Query query = session.createQuery("SELECT C from CloseOpen C WHERE C.idExercice = :a AND C.typeOperation=:b");
			query.setParameter("a", idEx);
			query.setParameter("b", type);
			
			c = (CloseOpen) query.getSingleResult();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.getMessage();
		}
		return c;
	}
}
