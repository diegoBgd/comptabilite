package vewBean;

import entite.Compte;
import entite.Ecriture;
import entite.Exercice;
import entite.Journal;
import entite.ParametreCompta;
import entite.User;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.CompteModel;
import model.EcritureModel;
import model.ExerciceModel;
import model.JournalModel;
import model.ParametreComptaModel;
import model.UserModel;
import org.hibernate.SessionFactory;
import persistances.DBConfiguration;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class ClotureOuvertureVew implements Serializable {
	private static final long serialVersionUID = 8059489206788600178L;
	private List<Ecriture> listEcriture;
	private String typeResultat;
	private String message, codeJrl, libelleJrnl;
	private boolean pagination;
	private boolean disableMsg;
	private double resultatExercice;
	private Journal selectedJrnl;

	EcritureModel model;
	Exercice selecetdExercice,exercicePrcd;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;
	ParametreCompta prmt;
	SessionFactory factory = DBConfiguration.getSessionFactory();

	List<Compte> listeCompte;

	public List<Ecriture> getListEcriture() {
		return this.listEcriture;
	}

	public void setListEcriture(List<Ecriture> listEcriture) {
		this.listEcriture = listEcriture;
	}

	public String getTypeResultat() {
		return this.typeResultat;
	}

	public void setTypeResultat(String typeResultat) {
		this.typeResultat = typeResultat;
	}

	public boolean isPagination() {
		return this.pagination;
	}

	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isDisableMsg() {
		return this.disableMsg;
	}

	public void setDisableMsg(boolean disableMsg) {
		this.disableMsg = disableMsg;
	}

	public double getResultatExercice() {
		return this.resultatExercice;
	}

	public void setResultatExercice(double resultatExercice) {
		this.resultatExercice = resultatExercice;
	}

	public String getCodeJrl() {
		return codeJrl;
	}

	public void setCodeJrl(String codeJrl) {
		this.codeJrl = codeJrl;
	}

	public String getLibelleJrnl() {
		return libelleJrnl;
	}

	public void setLibelleJrnl(String libelleJrnl) {
		this.libelleJrnl = libelleJrnl;
	}

	public Journal getSelectedJrnl() {
		return selectedJrnl;
	}

	public void setSelectedJrnl(Journal selectedJrnl) {
		this.selectedJrnl = selectedJrnl;
	}

	@PostConstruct
	public void initialize() {
		this.model = new EcritureModel();
		chargementSession();
		this.disableMsg = true;
	}

	private void chargementSession() {
		this.session = HelperC.getSession();
		if (this.session != null) {
			this.exerCode = (String) this.session.getAttribute("exercice");
			this.currUserCode = (String) this.session.getAttribute("operateur");

			if (this.exerCode != null) {
				this.selecetdExercice = (new ExerciceModel()).getExercByCode(this.factory, this.exerCode);
				if(selecetdExercice!=null)
				{
					if(selecetdExercice.getExPrcdCode()!=null && !selecetdExercice.getExPrcdCode().equals(""))
					exercicePrcd=(new ExerciceModel()).getExercByCode(this.factory,selecetdExercice.getExPrcdCode());
				}
			}

			if (this.currUserCode != null) {
				this.currentUser = (new UserModel()).getUserByCode(this.factory, this.currUserCode);
			}

			if (this.currentUser == null || this.selecetdExercice == null) {

				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("/comptabilite/login.xhtml");
				} catch (IOException e) {

					e.printStackTrace();
				}
			} else {

				this.prmt = (new ParametreComptaModel()).getParameter(this.factory);
				if(prmt!=null)
				{
					codeJrl=prmt.getJournalAN();
			
					selectedJrnl=new JournalModel().getJouralByCode(factory, codeJrl);
					if(selectedJrnl!=null)
						libelleJrnl=selectedJrnl.getLibelle();
				}
			}
		}
	}

	private void chargermentCompteGestion() {
		this.listeCompte = (new CompteModel()).getListCompteCO(this.factory, 1);
	}

	private void chargermentCompteBilan() {
		this.listeCompte = (new CompteModel()).getListCompteCO(this.factory, 0);
	}

	private void soldeCompteGestion() {
		this.listEcriture = (new EcritureModel()).getListEcritureCompteBetween(this.factory,
				this.selecetdExercice.getId(), "6", "8", null, null);
	}

	public void soldeCompteBilan() {
		message="";
		if(exercicePrcd!=null) {
		List<Object[]> listeSolde = this.model.getListEcriture(this.factory, exercicePrcd.getId(), "", "1",
				"6");
		double deb=0,crd=0;
		for (Object[] objects : listeSolde) {

			deb = Double.valueOf(objects[1].toString()).doubleValue();
			crd = Double.valueOf(objects[2].toString()).doubleValue();
			createEcriture(codeJrl,objects[0].toString(),deb,crd,"SOLDE D'OUVERTURE",selecetdExercice.getDateFin());
			deb=0;crd=0;
		}
		}
		else {
		message="L'exercice précédent n'est pas parametré";
		}
	}

	public void calculResultat() {
		this.pagination = false;
		List<Object[]> listeSolde = this.model.getListEcriture(this.factory, this.selecetdExercice.getId(), "", "6",
				"8");
		this.listEcriture = new ArrayList<>();
		double solde = 0.0D, totPrd = 0.0D, totChg = 0.0D;
		if (listeSolde.size() > 0) {

			this.pagination = true;
			this.disableMsg = false;

			for (Object[] objects : listeSolde) {

				if (objects[0].toString().startsWith("6")) {
					totChg = Double.valueOf(objects[1].toString()).doubleValue();
				}
				if (objects[0].toString().startsWith("7")) {
					totPrd = Double.valueOf(objects[2].toString()).doubleValue();
				}
				solde += totPrd - totChg;

				if (totPrd > 0.0D) {

					createEcriture(this.prmt.getJournalOD(), objects[0].toString(), totPrd, 0.0D,
							"CLOTURE DE L'EXERCICE",selecetdExercice.getDateFin());
					createEcriture(this.prmt.getJournalOD(), this.prmt.getCompteRs(), 0.0D, totPrd,
							"CLOTURE DE L'EXERCICE",selecetdExercice.getDateFin());
				}

				if (totChg > 0.0D) {

					createEcriture(this.prmt.getJournalOD(), this.prmt.getCompteRs(), totChg, 0.0D,
							"CLOTURE DE L'EXERCICE",selecetdExercice.getDateFin());
					createEcriture(this.prmt.getJournalOD(), objects[0].toString(), 0.0D, totChg,
							"CLOTURE DE L'EXERCICE",selecetdExercice.getDateFin());
				}

				totChg = 0.0D;
				totPrd = 0.0D;
			}

			if (solde >= 0.0D) {

				createEcriture(this.prmt.getJournalOD(), this.prmt.getCompteRs(), solde, 0.0D,
						"AFFECTATION DU RESULTAT",selecetdExercice.getDateFin());
				createEcriture(this.prmt.getJournalOD(), this.prmt.getCompteAND(), 0.0D, solde,
						"AFFECTATION DU RESULTAT",selecetdExercice.getDateFin());

				this.typeResultat = "RESULTAT BENEFICIAIRE";
				this.resultatExercice = solde;
			}

			if (solde < 0.0D) {

				createEcriture(this.prmt.getJournalOD(), this.prmt.getCompteANC(), -solde, 0.0D,
						"AFFECTATION DU RESULTAT",selecetdExercice.getDateFin());
				createEcriture(this.prmt.getJournalOD(), this.prmt.getCompteRs(), 0.0D, -solde,
						"AFFECTATION DU RESULTAT",selecetdExercice.getDateFin());

				this.typeResultat = "RESULTAT DEFICITAIRE";
				this.resultatExercice = -solde;
			}
		}
	}

	private void createEcriture(String jrnal, String cpte, double montantDeb, double montantCrd,
			String libelle,Date dateOper) {
		Ecriture ecr = new Ecriture();
		ecr.setCpte(cpte);
		ecr.setDebit(montantDeb);
		ecr.setCredit(montantCrd);
		ecr.setDateOperation(dateOper);
		ecr.setIdExercise(this.selecetdExercice.getId());
		ecr.setJrnl(jrnal);
		ecr.setLibelle(libelle);
		ecr.setPieceCpb("");

		this.listEcriture.add(ecr);
	}

	public void save() {
		if (this.listEcriture != null && this.listEcriture.size() > 0)
			this.message = this.model.saveEcriture(this.factory, this.listEcriture);
		else
			message="La liste des écritures est vide"	;
	}
}
