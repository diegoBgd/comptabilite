package model;

import java.util.List;

import org.hibernate.query.Query;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import entite.Cession;
import entite.Ecriture;
import entite.Immobilise;
import entite.TypeEcriture;


public class CessionModel {

	public CessionModel() {
		
	}

	public void saveCession(SessionFactory factory, Cession ces) {
	     Session ss = null;
	     int id=0;
	     try {
	     ss = factory.openSession();
	     ss.beginTransaction();
	     if(ces.getId()==0)
	     {
	    		 id = ((Integer) ss.save(ces)).intValue();
	     }
	     else
	    	 ss.update(ces);
	     
			for (Ecriture ecr : ces.getListEcriture()) 
			{
				ecr.setSourceOperation(id);
				ss.save(ecr);
			}
			
	     ss.getTransaction().commit();
	     ss.close();
	     }
	     catch (Exception e) {
	    	 e.getMessage();
	    	 ss.close();
		}
	   }
	
	
	 
	 public void deleteCession(SessionFactory factory, String journal, int idCession) {

			try {
				Session session = factory.openSession();
				session.beginTransaction();
				String sql1 = "DELETE  from Cession C where C.id=:idCes";
				String sql2 = "DELETE  from Ecriture E where E.sourceOperation=:idOp AND E.jrnl=:jnl AND E.typeOperation=:tpe";
				Query<?> query1 = session.createQuery(sql1);
				Query<?> query2 = session.createQuery(sql2);

				query1.setParameter("idCes", idCession);			
				query2.setParameter("jnl", journal);
				query2.setParameter("idOp", idCession);
				query2.setParameter("tpe", TypeEcriture.cession);

				query1.executeUpdate();
				query2.executeUpdate();

				session.getTransaction().commit();
				session.close();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	 
	 @SuppressWarnings("unchecked")
		public List<Cession> getListeCession(SessionFactory factory) {
			List<Cession> list = null;
			Session session =null;
			try {
				 session = factory.openSession();
				session.beginTransaction();

				String sql = "SELECT C from Cession C ";
				Query query = session.createQuery(sql);
				list = query.getResultList();

				session.getTransaction().commit();
				session.close();
			} catch (Exception e) {
				System.out.println(e.toString());
				session.close();
			}
			return list;
		}
	 
		public Cession getCessionImmo(SessionFactory factory,Immobilise immo) {
			Cession cess = null;
			Session session =null;
			try {
				 session = factory.openSession();
				session.beginTransaction();

				String sql = "SELECT C from Cession C WHERE C.immo=:im";
				Query<?> query = session.createQuery(sql);
				query.setParameter("im", immo);	
				cess = (Cession)query.getSingleResult();

				session.getTransaction().commit();
				session.close();
			} catch (Exception e) {
				System.out.println(e.toString());
				session.close();
			}
			return cess;
		}
}
