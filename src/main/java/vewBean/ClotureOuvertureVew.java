package vewBean;

import entite.CloseOpen;
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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.ClotureExerciceModel;
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
	private String printTotCrd,printTotDeb,printSoldeCd,printSoldeDb;
	private boolean pagination;
	private boolean disableMsg,disableBtn;
	private double resultatExercice;
	private Journal selectedJrnl;
	private int typeAnnulation;
	EcritureModel model;
	Exercice selecetdExercice,exercicePrcd;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;
	ParametreCompta prmt;
	SessionFactory factory = DBConfiguration.getSessionFactory();
	double solde;
	List<Compte> listeCompte;
	CloseOpen cloture,ouverture,closePrcd;
	
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

	public boolean isDisableBtn() {
		return disableBtn;
	}

	public void setDisableBtn(boolean disableBtn) {
		this.disableBtn = disableBtn;
	}

	public String getPrintTotCrd() {
		return printTotCrd;
	}

	public void setPrintTotCrd(String printTotCrd) {
		this.printTotCrd = printTotCrd;
	}

	public String getPrintTotDeb() {
		return printTotDeb;
	}

	public void setPrintTotDeb(String printTotDeb) {
		this.printTotDeb = printTotDeb;
	}

	public String getPrintSoldeCd() {
		return printSoldeCd;
	}

	public void setPrintSoldeCd(String printSoldeCd) {
		this.printSoldeCd = printSoldeCd;
	}

	public String getPrintSoldeDb() {
		return printSoldeDb;
	}

	public void setPrintSoldeDb(String printSoldeDb) {
		this.printSoldeDb = printSoldeDb;
	}

	public int getTypeAnnulation() {
		return typeAnnulation;
	}

	public void setTypeAnnulation(int typeAnnulation) {
		this.typeAnnulation = typeAnnulation;
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
					{
						exercicePrcd=(new ExerciceModel()).getExercByCode(this.factory,selecetdExercice.getExPrcdCode());
						if(exercicePrcd!=null)
							closePrcd=new ClotureExerciceModel().getExerciceCloture(selecetdExercice.getId(), factory,"C");
					}
					cloture=new ClotureExerciceModel().getExerciceCloture(selecetdExercice.getId(), factory,"C");					
					ouverture=new ClotureExerciceModel().getExerciceCloture(selecetdExercice.getId(), factory,"O");
					
					
					
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
				listEcriture=new ArrayList<Ecriture>();
				this.prmt = (new ParametreComptaModel()).getParameter(this.factory);
				
				UIComponent frmClose = null;
				FacesContext context = FacesContext.getCurrentInstance();
				frmClose = context.getViewRoot().findComponent("frmClo");

				UIComponent frmOpen = null;
				frmOpen = context.getViewRoot().findComponent("frmOpn");
				
				UIComponent frmCancel= null;
				frmCancel = context.getViewRoot().findComponent("frmClo");

				if(frmCancel!=null)
				{
					if(cloture!=null || ouverture!=null)
					disableMsg=false;
				}
				if(prmt!=null)
				{
					if(frmOpen!=null)
					{
						codeJrl=prmt.getJournalAN();
												
						if(ouverture!=null)
						{
							
							disableMsg=true;
							disableBtn=true;
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Attention : ", "L'exercice "+selecetdExercice.getExCode()+" est déjà ouvert"));
							disableMsg=false;	
						}
						else {
							message="";
							if(exercicePrcd!=null)
							{
								if(closePrcd==null)
								{
									disableMsg=true;
									disableBtn=true;
									FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Attention : ", "L'exercice "+exercicePrcd.getExCode()+" n'est pas clôturé"));
									
								}
							}
							
						}
					}
					if(frmClose!=null)
					{
						codeJrl=prmt.getJournalOD();	
						if(ouverture==null)
						{
							disableMsg=true;
							disableBtn=true;
							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Attention : ", "il faut d'abord ouvrir l'exercice "+selecetdExercice.getExCode()));
							
						}
					}
					if(cloture!=null)
					{
						
						disableMsg=true;
						disableBtn=true;
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Attention : ", "L'exercice "+selecetdExercice.getExCode()+" est déjà clôturé"));
							
					}
					else {
						message="";
					}
					selectedJrnl=new JournalModel().getJouralByCode(factory, codeJrl);
					if(selectedJrnl!=null)
						libelleJrnl=selectedJrnl.getLibelle();
				}
			}
		}
	}

	

	public void soldeCompteBilan() {
		message="";
		if(exercicePrcd!=null) {
		List<Object[]> listeSolde = this.model.getListEcriture(this.factory, exercicePrcd.getId(), "", "1",
				"6");
		double deb=0,crd=0,solde=0;
		for (Object[] objects : listeSolde) {

			deb = Double.valueOf(objects[1].toString()).doubleValue();
			crd = Double.valueOf(objects[2].toString()).doubleValue();
			if(deb-crd>0)
			{
				solde=deb-crd;
			createEcriture(codeJrl,objects[0].toString(),solde,0,"SOLDE D'OUVERTURE",selecetdExercice.getDateDebut());
			}
			if(crd>deb)
			{
				solde=crd-deb;
				createEcriture(codeJrl,objects[0].toString(),0,solde,"SOLDE D'OUVERTURE",selecetdExercice.getDateDebut());
			}
			deb=0;crd=0;solde=0;
		}
		calculTotaux();
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
		double deb=0,crd=0;
		if (listeSolde.size() > 0) {

			this.pagination = true;
			this.disableMsg = false;

			for (Object[] objects : listeSolde) {

				if (objects[0].toString().startsWith("6")) {
					deb=Double.valueOf(objects[1].toString()).doubleValue();
					crd=Double.valueOf(objects[2].toString()).doubleValue();
					
					totChg =deb-crd;
				}
				if (objects[0].toString().startsWith("7")) {
					
					deb=Double.valueOf(objects[1].toString()).doubleValue();
					crd=Double.valueOf(objects[2].toString()).doubleValue();
					
					totPrd = crd-deb;
				}
				crd=0;deb=0;
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
			calculTotaux();
			
		}
	}

	private void calculTotaux() {
		double totDeb = 0.0D, totCrd = 0.0D;
		printTotCrd = "0";
		printTotDeb = "0";
		printSoldeCd = "0";
		printSoldeDb = "0";
		for (Ecriture ec : this.listEcriture) {
			totDeb += ec.getDebit();
			totCrd += ec.getCredit();
		}

		solde = totDeb - totCrd;
		this.printTotCrd = HelperC.decimalNumber(totCrd, 0, true);
		this.printTotDeb = HelperC.decimalNumber(totDeb, 0, true);
		
		if (solde > 0.0D)
			this.printSoldeDb = HelperC.decimalNumber(solde, 0, true);
		if (solde < 0.0D)
			this.printSoldeCd = HelperC.decimalNumber(Math.abs(solde), 0, true);
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

	public void saveCloture() {
		if (this.listEcriture != null && this.listEcriture.size() > 0) {
			if (solde != 0) {
				if (cloture == null)
					cloture = new CloseOpen();
				cloture.setDateOperation(new Date());
				cloture.setIdExercice(selecetdExercice.getId());
				cloture.setCodeJournal(codeJrl);
				cloture.setTypeOperation("C");
				this.message = this.model.saveOpenClosePeriod(this.factory, this.listEcriture, cloture);
			} else
				message = "Le journal n'est pas équilibré";
		} else
			message = "La liste des écritures est vide";
	}
	public void saveOuverture() {
		if (this.listEcriture != null && this.listEcriture.size() > 0) {
			
			if (solde != 0) {
			if(ouverture==null)
				ouverture = new CloseOpen();
			ouverture.setDateOperation(new Date());
			ouverture.setIdExercice(selecetdExercice.getId());
			ouverture.setCodeJournal(codeJrl);
			ouverture.setTypeOperation("O");
				this.message = this.model.saveOpenClosePeriod(this.factory, this.listEcriture, ouverture);
			}
			else
				message = "Le journal n'est pas équilibré";
		} else
			message = "La liste des écritures est vide";
	}
	public void annuler() {
	
		switch (typeAnnulation) {
		case 0:
			if(ouverture!=null)
			message =new ClotureExerciceModel().cancelCloseOrOpenPeriod (factory, ouverture);
			break;
			
		case 1:
			if(cloture!=null )
			{				
				message =new ClotureExerciceModel().cancelCloseOrOpenPeriod(factory, cloture);
			}
			break;
		}
		
		
	}
}
