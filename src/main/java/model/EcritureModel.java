package model;

import entite.CloseOpen;
import entite.Ecriture;
import utils.HelperC;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class EcritureModel {
	public void saveEcriture(SessionFactory factory, Ecriture ecr) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		ss.save(ecr);
		ss.getTransaction().commit();
		ss.close();
	}

	public String saveEcriture(SessionFactory factory, List<Ecriture> list, List<Ecriture> dList) {
		String msg = "";
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();

		for (Ecriture ecriture : list) {
			if (ecriture.getId() == 0)
				ss.save(ecriture);
			else
				ss.update(ecriture);
		}
		if (dList != null && dList.size() > 0) {
			for (Ecriture ecriture : dList) {
				ss.delete(ecriture);
			}
		}

		ss.getTransaction().commit();
		ss.close();
		msg = "Opération réussie";
		return msg;
	}

	public String saveOpenClosePeriod(SessionFactory factory, List<Ecriture> list, CloseOpen close) {
		String msg = "";
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		for (Ecriture ecriture : list) {
			if (ecriture.getId() == 0)
				ss.save(ecriture);
			else
				ss.update(ecriture);
		}
		ss.save(close);

		ss.getTransaction().commit();
		ss.close();
		msg = "Opération réussie";
		return msg;
	}

	public void updateEcriture(SessionFactory factory, Ecriture ecr) {
		Session ss = null;
		ss = factory.openSession();
		ss.beginTransaction();
		ss.update(ecr);
		ss.getTransaction().commit();
		ss.close();
	}

	public void deleteListEcriture(SessionFactory factory, List<Ecriture> list) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		for (Ecriture ecriture : list) {
			if (ecriture.getId() > 0)
				ss.delete(ecriture);
		}
		ss.getTransaction().commit();
		ss.close();
	}

	public void deletEcriture(SessionFactory factory, Ecriture ecr) {
		Session ss = null;

		ss = factory.openSession();
		ss.beginTransaction();
		ss.delete(ecr);
		ss.getTransaction().commit();
		ss.close();
	}

	public boolean isJournalUsed(SessionFactory factory, String codeJrnl) {
		Session ss = null;
		boolean bl = false;

		ss = factory.openSession();
		ss.beginTransaction();
		String sql = "Select e from Ecriture e where e.jrnl=:jnl";

		Query<?> query = ss.createQuery(sql);
		query.setParameter("jnl", codeJrnl);

		if (query.getResultList().size() > 0)
			bl = true;

		ss.getTransaction().commit();
		ss.close();
		return bl;
	}

	public boolean isCompteUsed(SessionFactory factory, String compte) {
		Session ss = null;
		boolean bl = false;

		ss = factory.openSession();
		ss.beginTransaction();
		String sql = "Select e from Ecriture e where e.cpte=:cpt";

		Query<?> query = ss.createQuery(sql);
		query.setParameter("cpt", compte);

		if (query.getResultList().size() > 0)
			bl = true;

		ss.getTransaction().commit();
		ss.close();
		return bl;
	}

	@SuppressWarnings("unchecked")
	public List<Ecriture> getListEcriture(SessionFactory factory, int idExercice, String codeJrnl, String compte,
			String piece, Date deb, Date fin) {

		List<Ecriture> list = null;
		try {
			Session session = factory.openSession();
			session.beginTransaction();
			if (deb != null) {
				String dteDb = HelperC.convertDate(deb);
				deb = HelperC.stringTodate(dteDb);
			}
			String sql = "Select e from Ecriture e where e.idExercise=:ex";

			if (deb != null)
				sql = sql + " AND e.dateOperation>=:dteDb";
			if (fin != null)
				sql = sql + " AND e.dateOperation<=:dteFn";
			if (compte != null && !compte.equals(""))
				sql = sql + " AND e.cpte=:cpt";
			if (codeJrnl != null && !codeJrnl.equals(""))
				sql = sql + " AND e.jrnl=:jnl";
			if (piece != null && !piece.equals(""))
				sql = sql + " AND e.pieceCpb=:pce";

			// sql = sql + " ORDER BY e.id,e.dateOperation";
			sql = sql + " ORDER BY e.dateOperation";

			Query<?> query = session.createQuery(sql);
			query.setParameter("ex", idExercice);

			if (deb != null) {
				query.setParameter("dteDb", deb);
			}
			if (fin != null) {
				query.setParameter("dteFn", fin);
			}

			if (compte != null && !compte.equals(""))
				query.setParameter("cpt", compte);
			if (codeJrnl != null && !codeJrnl.equals(""))
				query.setParameter("jnl", codeJrnl);
			if (piece != null && !piece.equals(""))
				query.setParameter("pce", piece);

			list = (List<Ecriture>) query.getResultList();

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Ecriture> getListEcritureCompteLIKE(SessionFactory factory, int idExercice, String codeJrnl,
			String compte, String piece, Date deb, Date fin) {

		List<Ecriture> list = null;
		try {
			Session session = factory.openSession();
			session.beginTransaction();
			if (deb != null) {
				String dteDb = HelperC.convertDate(deb);
				deb = HelperC.stringTodate(dteDb);
			}
			String sql = "Select e from Ecriture e where e.idExercise=:ex";

			if (deb != null)
				sql = sql + " AND e.dateOperation>=:dteDb";
			if (fin != null)
				sql = sql + " AND e.dateOperation<=:dteFn";
			if (compte != null && !compte.equals(""))
				sql = sql + " AND e.cpte LIKE :cpt";
			if (codeJrnl != null && !codeJrnl.equals(""))
				sql = sql + " AND e.jrnl=:jnl";
			if (piece != null && !piece.equals(""))
				sql = sql + " AND e.pieceCpb=:pce";

			sql = sql + " ORDER BY e.dateOperation";

			Query<?> query = session.createQuery(sql);
			query.setParameter("ex", idExercice);

			if (deb != null) {
				query.setParameter("dteDb", deb);
			}
			if (fin != null) {
				query.setParameter("dteFn", fin);
			}

			if (compte != null && !compte.equals(""))
				query.setParameter("cpt", compte + "%");
			if (codeJrnl != null && !codeJrnl.equals(""))
				query.setParameter("jnl", codeJrnl);
			if (piece != null && !piece.equals(""))
				query.setParameter("pce", piece);

			list = (List<Ecriture>) query.getResultList();

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public List<Ecriture> getListEcritureCompteBetween(SessionFactory factory, int idExercice, String compteDeb,
			String compteFin, Date deb, Date fin) {
		List<Ecriture> list = null;
		try {

			if (deb != null) {
				String dteDb = HelperC.convertDate(deb);
				deb = HelperC.stringTodate(dteDb);
			}
			Session session = factory.openSession();
			session.beginTransaction();

			String sql = "Select e from Ecriture e where e.idExercise=:ex";

			if (compteDeb != null && !compteDeb.equals("") && compteFin != null && !compteFin.equals("")) {
				sql += " AND e.cpte BETWEEN :cptDb AND :cptFn";
			}
			if (deb != null) {
				sql += " AND e.dateOperation>=:dteDb";
			}
			if (fin != null) {
				sql += " AND e.dateOperation<=:dteFn";
			}
			sql = String.valueOf(sql) + " ORDER BY e.dateOperation";

			Query<?> query = session.createQuery(sql);

			query.setParameter("ex", idExercice);

			if (compteDeb != null && !compteDeb.equals("") && compteFin != null && !compteFin.equals("")) {
				query.setParameter("cptDb", compteDeb);
				query.setParameter("cptFn", compteFin);
			}

			if (deb != null) {
				query.setParameter("dteDb", deb);
			}
			if (fin != null) {
				query.setParameter("dteFn", fin);
			}
			list = (List<Ecriture>) query.getResultList();

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return list;
	}

	public double getSoldeCompte(SessionFactory factory, int idExercice, String compte) {

		double solde = 0.0D;
		try {

			Session session = factory.openSession();
			session.beginTransaction();

			String sql = "Select SUM(e.debit-e.credit) from Ecriture e where e.idExercise=:ex";

			if (compte != null && !compte.equals("")) {
				sql += " AND e.cpte =:cptDb ";
			}

			Query<?> query = session.createQuery(sql);

			query.setParameter("ex", idExercice);

			if (compte != null && !compte.equals("")) {
				query.setParameter("cptDb", compte);

			}

			solde = (double) query.uniqueResult();

			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return solde;
	}

	public double getSoldeJournal(SessionFactory factory, int idExercice, String jrnle) {

		double solde = 0.0D;
		try {

			Session session = factory.openSession();
			session.beginTransaction();

			String sql = "Select SUM(e.debit-e.credit) from Ecriture e where e.idExercise=:ex";

			if (jrnle != null && !jrnle.equals("")) {
				sql += " AND e.jrnl=:jnl ";
			}

			Query<?> query = session.createQuery(sql);

			query.setParameter("ex", idExercice);

			if (jrnle != null && !jrnle.equals("")) {
				query.setParameter("jnl", jrnle);

			}

			solde = (double) query.uniqueResult();

			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return solde;
	}

	public List<Object[]> getListSoldeJournal(SessionFactory factory, int idExercice, String jrnal, Date deb,
			Date fin) {

		Object[] soldeCpt = new Object[3];
		List<Object[]> listSold = new ArrayList<Object[]>();
		List<?> list = null;
		try {

			Session session = factory.openSession();
			session.beginTransaction();

			String sql = "Select e.jrnl,SUM(e.debit),SUM(e.credit) from Ecriture e where e.idExercise=:ex";

			if (jrnal != null && !jrnal.equals("")) {
				sql += " AND e.jrnl =:jrn";
			}
			if (deb != null) {
				sql += " AND e.dateOperation>=:dteDb";
			}
			if (fin != null) {
				sql += " AND e.dateOperation<=:dteFn";
			}

			sql = String.valueOf(sql) + "  group by e.jrnl";

			Query<?> query = session.createQuery(sql);

			query.setParameter("ex", idExercice);

			if (deb != null) {
				query.setParameter("dteDb", deb);
			}
			if (fin != null) {
				query.setParameter("dteFn", fin);
			}

			if (jrnal != null && !jrnal.equals("")) {
				query.setParameter("jrn", jrnal);

			}

			list = query.getResultList();

			session.getTransaction().commit();
			session.close();

			for (Object ob : list) {
				soldeCpt = (Object[]) ob;
				listSold.add(soldeCpt);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return listSold;
	}

	@SuppressWarnings("unchecked")
	public double getDebitCreditCompteBetween(SessionFactory factory, int idExercice, String compteDeb,
			String compteFin, Date deb, Date fin, String value) {
		List<Ecriture> list = null;
		double solde = 0.0D;
		try {

			if (deb != null) {
				String dteDb = HelperC.convertDate(deb);
				deb = HelperC.stringTodate(dteDb);
			}
			Session session = factory.openSession();
			session.beginTransaction();

			String sql = "Select e from Ecriture e where e.idExercise=:ex";

			if (compteDeb != null && !compteDeb.equals("") && compteFin != null && !compteFin.equals("")) {
				sql += " AND e.cpte BETWEEN :cptDb AND :cptFn";
			}
			if (deb != null) {
				sql += " AND e.dateOperation>=:dteDb";
			}
			if (fin != null) {
				sql += " AND e.dateOperation<=:dteFn";
			}
			sql += " ORDER BY e.dateOperation";

			Query<?> query = session.createQuery(sql);

			query.setParameter("ex", idExercice);
			if (compteDeb != null && !compteDeb.equals("") && compteFin != null && !compteFin.equals("")) {
				query.setParameter("cptDb", compteDeb);
				query.setParameter("cptFn", compteFin);
			}

			if (deb != null) {
				query.setParameter("dteDb", deb);
			}
			if (fin != null) {
				query.setParameter("dteFn", fin);
			}
			list = (List<Ecriture>) query.getResultList();

			session.getTransaction().commit();
			session.close();

			if (list.size() > 0) {
				for (Ecriture e : list) {
					if (value.equals("D"))
						solde += e.getDebit();
					if (value.equals("C")) {
						solde += e.getCredit();
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return solde;
	}

	public List<Object[]> getListEcriture(SessionFactory factory, int idExercice, String journal, String compteDeb,
			String compteFin) {
		List<?> list = null;
		Object[] soldeCpt = new Object[3];
		List<Object[]> listSold = new ArrayList<Object[]>();
		try {

			Session session = factory.openSession();
			session.beginTransaction();
			String sql = "Select e.cpte,SUM(e.debit),SUM(e.credit) from Ecriture e where e.idExercise=:ex";

			if (journal != null && !journal.equals("")) {
				sql = String.valueOf(sql) + " AND e.jrnl<>:jnl";
			}
			if (compteDeb != null && !compteDeb.equals("") && compteFin != null && !compteFin.equals("")) {
				sql = String.valueOf(sql) + " AND e.cpte BETWEEN :cptDb AND :cptFn";
			}
			sql = String.valueOf(sql) + "  group by e.cpte";

			Query<?> query = session.createQuery(sql);

			query.setParameter("ex", Integer.valueOf(idExercice));

			if (compteDeb != null && !compteDeb.equals("") && compteFin != null && !compteFin.equals("")) {
				query.setParameter("cptDb", compteDeb);
				query.setParameter("cptFn", compteFin);
			}

			if (journal != null && !journal.equals("")) {
				query.setParameter("jnl", journal);
			}
			list = query.getResultList();

			session.getTransaction().commit();
			session.close();

			for (Object ob : list) {
				soldeCpt = (Object[]) ob;
				listSold.add(soldeCpt);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return listSold;
	}

	public List<Object[]> getBalanceEntree(SessionFactory factory, int idExercice, String journal, String compteDeb,
			String compteFin) {
		List<?> list = null;
		Object[] soldeCpt = new Object[3];
		List<Object[]> listSold = new ArrayList<Object[]>();
		try {
			Session session = factory.openSession();
			session.beginTransaction();
			String sql = "Select e.cpte,SUM(e.debit),SUM(e.credit) from Ecriture e where e.idExercise=:ex";

			if (journal != null && !journal.equals("")) {
				sql = String.valueOf(sql) + " AND e.jrnl=:jnl";
			}
			if (compteDeb != null && !compteDeb.equals("") && compteFin != null && !compteFin.equals("")) {
				sql = String.valueOf(sql) + " AND e.cpte BETWEEN :cptDb AND :cptFn";
			}
			sql = String.valueOf(sql) + "  group by e.cpte";

			Query<?> query = session.createQuery(sql);

			query.setParameter("ex", Integer.valueOf(idExercice));

			if (compteDeb != null && !compteDeb.equals("") && compteFin != null && !compteFin.equals("")) {
				query.setParameter("cptDb", compteDeb);
				query.setParameter("cptFn", compteFin);
			}

			if (journal != null && !journal.equals("")) {
				query.setParameter("jnl", journal);
			}
			list = query.getResultList();

			session.getTransaction().commit();
			session.close();

			for (Object ob : list) {
				soldeCpt = (Object[]) ob;
				listSold.add(soldeCpt);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return listSold;
	}

	public List<Object[]> getBalancePeriode(SessionFactory factory, int idExercice, String journal, String compteDeb,
			String compteFin) {
		List<?> list = null;
		Object[] soldeCpt = new Object[3];
		List<Object[]> listSold = new ArrayList<Object[]>();
		try {
			Session session = factory.openSession();
			session.beginTransaction();
			String sql = "Select e.cpte,SUM(e.debit),SUM(e.credit) from Ecriture e where e.idExercise=:ex";

			if (journal != null && !journal.equals("")) {
				sql = String.valueOf(sql) + " AND e.jrnl NOT LIKE(:jnl)";
			}
			if (compteDeb != null && !compteDeb.equals("") && compteFin != null && !compteFin.equals("")) {
				sql = String.valueOf(sql) + " AND e.cpte BETWEEN :cptDb AND :cptFn";
			}
			sql = String.valueOf(sql) + "  group by e.cpte";

			Query<?> query = session.createQuery(sql);

			query.setParameter("ex", Integer.valueOf(idExercice));

			if (compteDeb != null && !compteDeb.equals("") && compteFin != null && !compteFin.equals("")) {
				query.setParameter("cptDb", compteDeb);
				query.setParameter("cptFn", compteFin);
			}

			if (journal != null && !journal.equals("")) {
				query.setParameter("jnl", journal);
			}
			list = query.getResultList();

			session.getTransaction().commit();
			session.close();

			for (Object ob : list) {
				soldeCpt = (Object[]) ob;
				listSold.add(soldeCpt);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return listSold;
	}

	public List<Object[]> getSoldeCompte(SessionFactory factory, int idExercice, String journal, String cpte, Date deb,
			Date fin) {
		List<?> list = null;
		Object[] soldeCpt = new Object[3];
		List<Object[]> listSold = new ArrayList<Object[]>();
		try {
			Session session = factory.openSession();
			session.beginTransaction();
			String sql = "Select e.cpte,SUM(e.debit),SUM(e.credit) from Ecriture e where e.idExercise=:ex";

			if (journal != null && !journal.equals("")) {
				sql = String.valueOf(sql) + " AND e.jrnl =:jnl";
			}
			if (cpte != null && !cpte.equals("")) {
				sql = String.valueOf(sql) + " AND e.cpte LIKE :cpt";
			}
			if (deb != null) {
				sql += " AND e.dateOperation>=:dteDb";
			}
			if (fin != null) {
				sql += " AND e.dateOperation<=:dteFn";
			}
			sql = String.valueOf(sql) + "  group by e.cpte";

			Query<?> query = session.createQuery(sql);

			query.setParameter("ex", Integer.valueOf(idExercice));

			if (cpte != null && !cpte.equals("")) {
				query.setParameter("cpt", cpte + "%");

			}

			if (journal != null && !journal.equals("")) {
				query.setParameter("jnl", journal);
			}

			if (deb != null) {
				query.setParameter("dteDb", deb);
			}
			if (fin != null) {
				query.setParameter("dteFn", fin);
			}

			list = query.getResultList();

			session.getTransaction().commit();
			session.close();

			for (Object ob : list) {
				soldeCpt = (Object[]) ob;
				/*
				 * if(soldeCpt[0].toString().startsWith("5"))
				 * System.out.println(soldeCpt[0]+" "+soldeCpt[1]+" "+soldeCpt[2]);
				 */
				listSold.add(soldeCpt);
			}

		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return listSold;
	}
}
