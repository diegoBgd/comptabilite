package model;

import entite.Amortissement;
import entite.Ecriture;
import entite.Exercice;
import entite.Immobilise;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class AmortisementModel {
	public String saveAmortissement(SessionFactory factory, List<Amortissement> listAmrt) {

		String msg = "";
		Session ss = null;
		try {
			ss = factory.openSession();
			ss.beginTransaction();
			for (Amortissement amrt : listAmrt) {

				int id = ((Integer) ss.save(amrt)).intValue();

				for (Ecriture ecr : amrt.getListEcriture()) {
					ecr.setSourceOperation(id);
					ss.save(ecr);
				}
				ss.save(amrt);
			}

			ss.getTransaction().commit();
			ss.close();
			msg = "Opération terminée";
		} catch (Exception e) {
			System.out.println(e.toString());
			msg = "Operation échouée ";
			ss.getTransaction().rollback();
		}
		return msg;
	}

	public void deleteAmortissemnt(SessionFactory factory, String journal, Exercice exe) {

		try {
			Session session = factory.openSession();
			session.beginTransaction();
			String sql1 = "DELETE  from Amortissement A where A.exercice=:ex";
			String sql2 = "DELETE  from Ecriture E where E.idExercise=:exId AND E.jrnl=:jnl";
			Query<?> query1 = session.createQuery(sql1);
			Query<?> query2 = session.createQuery(sql2);

			query1.setParameter("ex", exe);
			query2.setParameter("exId", exe.getId());
			query2.setParameter("jnl", journal);

			query1.executeUpdate();
			query2.executeUpdate();

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	@SuppressWarnings("unchecked")
	public List<Amortissement> getListAmort(SessionFactory factory, Exercice ex) {
		List<Amortissement> list = null;
		try {
			Session session = factory.openSession();
			session.beginTransaction();

			String sql = "Select A from Amortissement A where A.exercice=:ex";
			Query<?> query = session.createQuery(sql);
			query.setParameter("ex", ex);

			list = (List<Amortissement>) query.getResultList();

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Amortissement> getListAmortImmo(SessionFactory factory, Immobilise immo) {
		List<Amortissement> list = null;
		try {
			Session session = factory.openSession();
			session.beginTransaction();

			String sql = "Select A from Amortissement A where A.immo=:im";
			Query<?> query = session.createQuery(sql);
			query.setParameter("im", immo);

			list = (List<Amortissement>) query.getResultList();

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}
	
	public Amortissement getLastAmortImmo(SessionFactory factory, Immobilise immo) {
		Amortissement amrt = null;
		Session session =null;
		try {
			 session =factory.openSession();
			session.beginTransaction();

			String sql = "SELECT A FROM Amortissement A  WHERE A.immo = :im "+
					     "ORDER BY A.id DESC";
			Query<?> query = session.createQuery(sql);
			query.setParameter("im", immo);
			query.setMaxResults(1); 
			
			amrt = (Amortissement) query.getSingleResult();

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return amrt;
	}
}
