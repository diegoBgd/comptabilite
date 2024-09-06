 package vewBean;
 
 import entite.Compte;
 import entite.Ecriture;
 import entite.Exercice;
 import entite.Journal;
 import entite.User;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
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
 import model.UserModel;

import org.apache.commons.collections4.functors.ForClosure;
import org.apache.poi.ss.usermodel.Cell;
 import org.apache.poi.ss.usermodel.Row;
 import org.apache.poi.ss.usermodel.Sheet;
 import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 import org.hibernate.SessionFactory;
 import org.primefaces.PrimeFaces;
 import persistances.DBConfiguration;
 import utils.HelperC;
 

 @ManagedBean
 @ViewScoped
 public class EcritureVew
   implements Serializable
 {
   private static final long serialVersionUID = -7979230209488713503L;
   private Ecriture selectedEcriture;
   private List<Ecriture> listeEcriture,listeSaisie;
   private Compte selectedCpte;
   private List<Compte> listeCpte;
   private Journal selectedJrnl;
   private List<Journal> listJrnl;
   
   private Date dateDebut;
   private Date dateFin;
   private Date dateOp;
   private boolean disableMsg;
   private boolean disableSave;
   private boolean pagination;
   private boolean keepDate;
   private boolean keepLibelle;
   private boolean keepPice;
   private boolean keepJrnal;
   private boolean autoAdd;
   private String printDate;
   private String rechPiece;
   SessionFactory factory = DBConfiguration.getSessionFactory(); 
   private String rechLblCpt; 
   private String rechLblJrnl; 
   private String rechJrnal;
   private String rechDateDeb; 
   private String rechDateFin; 
   private String rechCpte; 
   private String codeJrnl; 
   private String codeCpt; 
   private String libelleJrnl; 
   private String libelleCpt; 
   private String rechCodCpt; 
   private String printTotDeb; 
   private String printTotCrd; 
   private String printSoldeDb; 
   private String printSoldeCd; 
   private String soldeJrnl,soldeCpte;
   private String infoMsg,deleteMsg; 
   private String libelleOperation; 
   private String piece; 
   private String printCredit,printDebit,printSoldeDeb,printSoldeCrd;
   private double montantDeb; 
   private double montantCrd; 
   EcritureModel model;
   Exercice selecetdExercice;
   double solde = 0.0D;
   boolean selected=false;
   HttpSession session;
   String exerCode;
   String currUserCode;
   User currentUser;
   List<Ecriture>listDelete;
   int idEcr = 0;
   int index=0;
 
 
 
	public String getSoldeJrnl() {
		return soldeJrnl;
	}

	public void setSoldeJrnl(String soldeJrnl) {
		this.soldeJrnl = soldeJrnl;
	}

	public String getSoldeCpte() {
		return soldeCpte;
	}

	public void setSoldeCpte(String soldeCpte) {
		this.soldeCpte = soldeCpte;
	}

	public List<Ecriture> getListeSaisie() {
		return listeSaisie;
	}

	public void setListeSaisie(List<Ecriture> listeSaisie) {
		this.listeSaisie = listeSaisie;
	}

	public Ecriture getSelectedEcriture() {
		return this.selectedEcriture;
	}

	public void setSelectedEcriture(Ecriture selectedEcriture) {
		this.selectedEcriture = selectedEcriture;
	}

	public List<Ecriture> getListeEcriture() {
		return this.listeEcriture;
	}

	public void setListeEcriture(List<Ecriture> listeEcriture) {
		this.listeEcriture = listeEcriture;
	}

	public Compte getSelectedCpte() {
		return this.selectedCpte;
	}

	public void setSelectedCpte(Compte selectedCpte) {
		this.selectedCpte = selectedCpte;
	}

	public List<Compte> getListeCpte() {
		return this.listeCpte;
	}

	public void setListeCpte(List<Compte> listeCpte) {
		this.listeCpte = listeCpte;
	}

	public Journal getSelectedJrnl() {
		return this.selectedJrnl;
	}

	public void setSelectedJrnl(Journal selectedJrnl) {
		this.selectedJrnl = selectedJrnl;
	}

	public List<Journal> getListJrnl() {
		return this.listJrnl;
	}

	public void setListJrnl(List<Journal> listJrnl) {
		this.listJrnl = listJrnl;
	}

	public String getCodeJrnl() {
		return this.codeJrnl;
	}

	public void setCodeJrnl(String codeJrnl) {
		this.codeJrnl = codeJrnl;
	}

	public String getCodeCpt() {
		return this.codeCpt;
	}

	public void setCodeCpt(String codeCpt) {
		this.codeCpt = codeCpt;
	}

	public String getLibelleJrnl() {
		return this.libelleJrnl;
	}

	public void setLibelleJrnl(String libelleJrnl) {
		this.libelleJrnl = libelleJrnl;
	}

	public String getLibelleCpt() {
		return this.libelleCpt;
	}

	public void setLibelleCpt(String libelleCpt) {
		this.libelleCpt = libelleCpt;
	}

	public boolean isDisableMsg() {
		return this.disableMsg;
	}

	public void setDisableMsg(boolean disableMsg) {
		this.disableMsg = disableMsg;
	}

	public String getPrintDate() {
		return this.printDate;
	}

	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	public boolean isDisableSave() {
		return this.disableSave;
	}

	public void setDisableSave(boolean disableSave) {
		this.disableSave = disableSave;
	}

	public boolean isKeepDate() {
		return this.keepDate;
	}

	public void setKeepDate(boolean keepDate) {
		this.keepDate = keepDate;
	}

	public boolean isKeepLibelle() {
		return this.keepLibelle;
	}

	public void setKeepLibelle(boolean keepLibelle) {
		this.keepLibelle = keepLibelle;
	}

	public boolean isKeepPice() {
		return this.keepPice;
	}

	public void setKeepPice(boolean keepPice) {
		this.keepPice = keepPice;
	}

	public boolean isKeepJrnal() {
		return this.keepJrnal;
	}

	public void setKeepJrnal(boolean keepJrnal) {
		this.keepJrnal = keepJrnal;
	}

	public String getRechJrnal() {
		return this.rechJrnal;
	}

	public void setRechJrnal(String rechJrnal) {
		this.rechJrnal = rechJrnal;
	}

	public String getRechDateDeb() {
		return this.rechDateDeb;
	}

	public void setRechDateDeb(String rechDateDeb) {
		this.rechDateDeb = rechDateDeb;
	}

	public String getRechDateFin() {
		return this.rechDateFin;
	}

	public void setRechDateFin(String rechDateFin) {
		this.rechDateFin = rechDateFin;
	}

	public String getRechCpte() {
		return this.rechCpte;
	}

	public void setRechCpte(String rechCpte) {
		this.rechCpte = rechCpte;
	}

	public String getRechPiece() {
		return this.rechPiece;
	}

	public void setRechPiece(String rechPiece) {
		this.rechPiece = rechPiece;
	}

	public Date getDateDebut() {
		return this.dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return this.dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public String getRechLblCpt() {
		return this.rechLblCpt;
	}

	public void setRechLblCpt(String rechLblCpt) {
		this.rechLblCpt = rechLblCpt;
	}

	public String getRechLblJrnl() {
		return this.rechLblJrnl;
	}

	public void setRechLblJrnl(String rechLblJrnl) {
		this.rechLblJrnl = rechLblJrnl;
	}

	public String getPrintTotDeb() {
		return this.printTotDeb;
	}

	public void setPrintTotDeb(String printTotDeb) {
		this.printTotDeb = printTotDeb;
	}

	public String getPrintTotCrd() {
		return this.printTotCrd;
	}

	public void setPrintTotCrd(String printTotCrd) {
		this.printTotCrd = printTotCrd;
	}

	public String getInfoMsg() {
		return this.infoMsg;
	}

	public void setInfoMsg(String infoMsg) {
		this.infoMsg = infoMsg;
	}

	public String getRechCodCpt() {
		return this.rechCodCpt;
	}

	public void setRechCodCpt(String rechCodCpt) {
		this.rechCodCpt = rechCodCpt;
	}

	public Date getDateOp() {
		return this.dateOp;
	}

	public void setDateOp(Date dateOp) {
		this.dateOp = dateOp;
	}

	public String getLibelleOperation() {
		return this.libelleOperation;
	}

	public void setLibelleOperation(String libelleOperation) {
		this.libelleOperation = libelleOperation;
	}

	public String getPiece() {
		return this.piece;
	}

	public void setPiece(String piece) {
		this.piece = piece;
	}

	public double getMontantDeb() {
		return this.montantDeb;
	}

	public void setMontantDeb(double montantDeb) {
		this.montantDeb = montantDeb;
	}

	public double getMontantCrd() {
		return this.montantCrd;
	}

	public void setMontantCrd(double montantCrd) {
		this.montantCrd = montantCrd;
	}

	public boolean isPagination() {
		return this.pagination;
	}

	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}

	public String getPrintSoldeDb() {
		return this.printSoldeDb;
	}

	public void setPrintSoldeDb(String printSoldeDb) {
		this.printSoldeDb = printSoldeDb;
	}

	public String getPrintSoldeCd() {
		return this.printSoldeCd;
	}

	public void setPrintSoldeCd(String printSoldeCd) {
		this.printSoldeCd = printSoldeCd;
	}

	public String getPrintCredit() {
		return printCredit;
	}

	public void setPrintCredit(String printCredit) {
		this.printCredit = printCredit;
	}

	public String getPrintDebit() {
		return printDebit;
	}

	public void setPrintDebit(String printDebit) {
		this.printDebit = printDebit;
	}

	public String getPrintSoldeDeb() {
		return printSoldeDeb;
	}

	public void setPrintSoldeDeb(String printSoldeDeb) {
		this.printSoldeDeb = printSoldeDeb;
	}

	public String getPrintSoldeCrd() {
		return printSoldeCrd;
	}

	public void setPrintSoldeCrd(String printSoldeCrd) {
		this.printSoldeCrd = printSoldeCrd;
	}

	public boolean isAutoAdd() {
		return autoAdd;
	}

	public void setAutoAdd(boolean autoAdd) {
		this.autoAdd = autoAdd;
	}

	public String getDeleteMsg() {
		return deleteMsg;
	}

	public void setDeleteMsg(String deleteMsg) {
		this.deleteMsg = deleteMsg;
	}

	@PostConstruct
	public void initialize() {
		this.model = new EcritureModel();
		this.disableMsg = true;
		chargementSession();
		printTotCrd = "0";
		printTotDeb = "0";
		printSoldeCd = "0";
		printSoldeDb = "0";
		soldeCpte="0";
		soldeJrnl="0";
	}

	private void chargementSession() {
		this.session = HelperC.getSession();
		if (this.session != null) {
			this.exerCode = (String) this.session.getAttribute("exercice");
			this.currUserCode = (String) this.session.getAttribute("operateur");

			if (this.exerCode != null) {
				this.selecetdExercice = (new ExerciceModel()).getExercByCode(this.factory, this.exerCode);
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
				listeSaisie = new ArrayList<Ecriture>();
			}
		}
	}

	public void chargerCompte() {
		this.listeCpte = (new CompteModel()).getListCompte(this.factory, this.rechLblCpt, this.rechCodCpt);
	}

	public void chargerJournal() {
		this.listJrnl = (new JournalModel()).getListJouranl(this.factory, this.rechLblJrnl);
	}

	public void searchJournal() {
		if (this.codeJrnl != null && !this.codeJrnl.equals("")) {

			this.selectedJrnl = (new JournalModel()).getJouralByCode(this.factory, this.codeJrnl);
			if (this.selectedJrnl != null) {
				getJrnlValues();
			} else {
				HelperC.afficherAttention("Attention", "Ce journal n'existe pas !");
				this.disableSave = true;
			}
		}
	}

	public void searchCpt() {
		if (this.codeCpt != null && !this.codeCpt.equals("")) {

			this.selectedCpte = (new CompteModel()).getCompteByCode(this.factory, this.codeCpt);
			if (this.selectedCpte != null) {
				getCptValues();
			} else {
				HelperC.afficherAttention("Attention", "Ce compte n'existe pas !");
				this.disableSave = true;
			}
		}
	}

	public void getSelecedCptValue() {
		if (this.selectedCpte != null) {

			getCptValues();
			PrimeFaces.current().executeScript("PF('dlgCpt').hide();");
		}
	}

	public void getSelecedJrnlValue() {
		if (this.selectedJrnl != null) {

			getJrnlValues();
			PrimeFaces.current().executeScript("PF('dlgJrnl').hide();");
		}
	}

	private void getJrnlValues() {
		double sdJ=0;
		this.codeJrnl = this.selectedJrnl.getCodeJrnl();
		this.libelleJrnl = this.selectedJrnl.getLibelle();
		sdJ=model.getSoldeJournal(factory, selecetdExercice.getId(), codeJrnl);
		soldeJrnl=HelperC.decimalNumber(sdJ, 0, true);
	}

	private void getCptValues() {
		double sdCp=0;
		this.codeCpt = this.selectedCpte.getCompteCod();
		this.libelleCpt = this.selectedCpte.getLibelle();
		sdCp=model.getSoldeCompte(factory, selecetdExercice.getId(), codeCpt);
		soldeCpte=HelperC.decimalNumber(sdCp, 0, true);
	}

	public void chargerEcriture() {
		this.pagination = false;
		this.listeEcriture = this.model.getListEcriture(this.factory, this.selecetdExercice.getId(), this.rechJrnal,
				this.rechCpte, this.rechPiece, this.dateDebut, this.dateFin);
		if (this.listeEcriture != null && this.listeEcriture.size() > 0) {

			calculTotauxEcriture();
			this.pagination = true;
		}
	}

	public void controlDebValue() {
		if (this.montantDeb > 0.0D)
			this.montantCrd = 0.0D;
	}

	public void controlCrdValue() {
		if (this.montantCrd > 0.0D)
			this.montantDeb = 0.0D;
	}

	private void calculTotauxEcriture() {
		double totDeb = 0.0D, totCrd = 0.0D;
		printCredit = "0";
		printDebit = "0";
		printSoldeCrd = "0";
		printSoldeDeb = "0";
		for (Ecriture ec : this.listeEcriture) {
			totDeb += ec.getDebit();
			totCrd += ec.getCredit();
		}

		solde = totDeb - totCrd;
		printCredit = HelperC.decimalNumber(totCrd, 0, true);
		printDebit = HelperC.decimalNumber(totDeb, 0, true);
		if (solde > 0.0D)
			printSoldeDeb = HelperC.decimalNumber(solde, 0, true);
		if (solde < 0.0D)
			printSoldeCrd = HelperC.decimalNumber(Math.abs(solde), 0, true);
	}
	
	private void calculTotaux() {
		double totDeb = 0.0D, totCrd = 0.0D;
		printTotCrd = "0";
		printTotDeb = "0";
		printSoldeCd = "0";
		printSoldeDb = "0";
		for (Ecriture ec : this.listeSaisie) {
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

	public void changeDate() {
		this.disableSave = false;
		if (this.printDate.replace("/", "").replace("_", "").trim().equals("")) {

			this.dateOp = null;
		} else {

			this.dateOp = HelperC.validerDate(this.printDate);
			if (this.dateOp == null) {
				this.disableSave = true;
				HelperC.afficherAttention("Attention", "Date invalide !");
			} else {

				this.printDate = HelperC.convertDate(this.dateOp);
				if (!HelperC.periodeValide(this.dateOp, this.selecetdExercice.getDateDebut(),
						this.selecetdExercice.getDateFin())) {

					HelperC.afficherAttention("Attention", "La date saisie est en dehors de l'exercice !");
					this.disableSave = true;
				}
			}
		}
	}

	public void changeDateDebut() {
		if (this.rechDateDeb.replace("/", "").replace("_", "").trim().equals("")) {

			setDateDebut(null);
		} else {

			setDateDebut(HelperC.validerDate(this.rechDateDeb));
			if (getDateDebut() == null) {
				this.infoMsg = "Date invalide!";
			} else {

				this.rechDateDeb = HelperC.convertDate(getDateDebut());
			}
		}
	}

	public void changeDateFin() {
		if (this.rechDateFin.replace("/", "").replace("_", "").trim().equals("")) {

			setDateFin(null);
		} else {

			setDateFin(HelperC.validerDate(this.rechDateFin));
			if (getDateFin() == null) {

				this.infoMsg = "Date invalide!";
			} else {

				this.rechDateFin = HelperC.convertDate(getDateFin());
			}
		}
	}

	public void getSelectedSaisie() {
		deleteMsg="";disableMsg=true;
		if (selectedEcriture != null) {
			listeSaisie = model.getListEcriture(factory, selecetdExercice.getId(), selectedEcriture.getJrnl(), "",
					selectedEcriture.getPieceCpb(), selectedEcriture.getDateOperation(), selectedEcriture.getDateOperation());
			listDelete=new ArrayList<Ecriture>();
			if(listeSaisie.size()>0)
			{
			disableMsg = false;
			int i = 0;
			for (Ecriture ec : this.listeSaisie) {
				ec.setOrderIndex(i);
				i++;
			}
			calculTotaux();
			PrimeFaces.current().executeScript("PF('dlgEcr').hide();");
			deleteMsg="Voulez-vous supprimer ces "+listeSaisie.size()+" lignes ?";
			}
		}
	}

	public void getSelectedEcrValue() {
		this.idEcr = this.selectedEcriture.getId();
		this.montantCrd = this.selectedEcriture.getCredit();
		this.montantDeb = this.selectedEcriture.getDebit();
		this.dateOp = this.selectedEcriture.getDateOperation();
		this.libelleOperation = this.selectedEcriture.getLibelle();
		this.piece = this.selectedEcriture.getPieceCpb();
		index = listeSaisie.indexOf(selectedEcriture);
		selected = true;

		this.printDate = HelperC.convertDate(this.selectedEcriture.getDateOperation());
		this.codeCpt = this.selectedEcriture.getCpte();
		this.codeJrnl = this.selectedEcriture.getJrnl();
		searchCpt();
		searchJournal();
		
		PrimeFaces.current().executeScript("PF('dlgEcr').hide();");
	}

	public void saveSaisie() {
		if (listeSaisie != null && listeSaisie.size() > 0) {
			if (solde == 0) {
				model.saveEcriture(this.factory, listeSaisie,listDelete);
				initializeAll();
			} else {
				HelperC.afficherAttention("Attention", "Le journal n'est  équilibré !");
			}
		} else {
			HelperC.afficherAttention("Attention", "Il faut ajouter les écritures !");
		}
	}

	public void save() {
		if (this.selectedEcriture == null)
			this.selectedEcriture = new Ecriture();
		this.selectedEcriture.setId(this.idEcr);
		this.selectedEcriture.setCpte(this.codeCpt);
		this.selectedEcriture.setCredit(this.montantCrd);
		this.selectedEcriture.setDateOperation(this.dateOp);
		this.selectedEcriture.setDebit(this.montantDeb);
		this.selectedEcriture.setIdExercise(this.selecetdExercice.getId());
		this.selectedEcriture.setJrnl(this.codeJrnl);
		this.selectedEcriture.setLibelle(this.libelleOperation);
		this.selectedEcriture.setPieceCpb(this.piece);

		if (this.selectedEcriture.getDateOperation() == null) {

			HelperC.afficherAttention("Attention", "Il faut préciser la date de l'opération !");
			return;
		}
		if (this.selectedJrnl == null) {

			HelperC.afficherAttention("Attention", "Il faut préciser le journal !");
			return;
		}
		if (this.selectedCpte == null) {

			HelperC.afficherAttention("Attention", "Il faut préciser le compte comptable !");
			return;
		}
		if (this.selectedEcriture.getId() == 0) {
			this.model.saveEcriture(this.factory, this.selectedEcriture);
		} else {
			this.model.updateEcriture(this.factory, this.selectedEcriture);
		}
		initializeControl();
	}

	public void delete() {
		if (listeSaisie != null && listeSaisie.size()>0) {

			this.model.deleteListEcriture(this.factory,listeSaisie);
			initializeAll();
			calculTotaux();
			calculTotauxEcriture();
		}
	}

	public void initializeControl() {
		this.idEcr = 0;
		this.montantCrd = 0.0D;
		this.montantDeb = 0.0D;
		this.selectedCpte = null;
		this.codeCpt = "";
		this.libelleCpt = "";
		this.selectedEcriture = null;
		selected=false;
		if (!this.keepDate) {

			this.dateOp = null;
			this.printDate = "";
		}

		if (!this.keepPice) {
			this.piece = "";
		}
		if (!this.keepJrnal) {
			this.codeJrnl = "";
			this.selectedJrnl = null;
			this.libelleJrnl = "";
		}

		if (!this.keepLibelle) {
			this.libelleOperation = "";
		}
		this.disableMsg = true;
	}

	public void initializeAll() {
		this.idEcr = 0;
		this.montantCrd = 0.0D;
		this.montantDeb = 0.0D;
		this.selectedCpte = null;
		this.codeCpt = "";
		this.libelleCpt = "";
		this.selectedEcriture = null;
		this.dateOp = null;
		this.printDate = "";
		this.piece = "";
		this.codeJrnl = "";
		this.selectedJrnl = null;
		this.libelleJrnl = "";
		this.libelleOperation = "";
		this.disableMsg = true;
		printCredit = "0";
		printDebit = "0";
		printSoldeCrd = "0";
		printSoldeDeb = "0";
		printTotCrd = "0";
		printTotDeb = "0";
		printSoldeCd = "0";
		printSoldeDb = "0";
		soldeCpte="0";
		soldeJrnl="0";
		keepDate = false;
		keepPice = false;
		keepJrnal = false;
		keepLibelle = false;
		selected=false;
		listeSaisie.clear();
		if(listeEcriture!=null)
		listeEcriture.clear();
		listDelete=null;
	}

	public void addEcriture() {

		if (dateOp == null) {

			HelperC.afficherAttention("Attention", "Il faut préciser la date de l'opération !");
			return;
		}
		if (this.selectedJrnl == null) {

			HelperC.afficherAttention("Attention", "Il faut préciser le journal !");
			return;
		}
		if (this.selectedCpte == null) {

			HelperC.afficherAttention("Attention", "Il faut préciser le compte comptable !");
			return;
		}

		if(!this.selectedCpte.isCompteDetail())
		{
			HelperC.afficherAttention("Attention", "Ce compte ne peut pas être utilisé pour une opération comptable !");
			return;
		}
		if (selectedEcriture == null)
			selectedEcriture = new Ecriture();

		selectedEcriture.setId(idEcr);
		selectedEcriture.setCpte(codeCpt);
		selectedEcriture.setCredit(montantCrd);
		selectedEcriture.setDateOperation(dateOp);
		selectedEcriture.setDebit(montantDeb);
		selectedEcriture.setIdExercise(selecetdExercice.getId());
		selectedEcriture.setJrnl(codeJrnl);
		selectedEcriture.setLibelle(libelleOperation);
		selectedEcriture.setPieceCpb(piece);
		selectedEcriture.setOrderIndex(listeSaisie.size());

		if (!selected)
			listeSaisie.add(selectedEcriture);
		else {
			listeSaisie.remove(index);
			listeSaisie.add(index, selectedEcriture);
		}
		if(autoAdd)
		{
			contrePartie();
		}
		calculTotaux();
		initializeControl();
	}

	private void contrePartie() {
		double montant = 0, db = 0, cd = 0;

		if (selectedJrnl.getContrePartie() != null && !selectedJrnl.getContrePartie().equals("")) {
			for (Ecriture ec : this.listeSaisie) {
				db += ec.getDebit();
				cd += ec.getCredit();
			}
			if (db > cd) {
				montant = db - cd;
				montantCrd = montant;
				montantDeb = 0;
			}
			if (cd > db) {
				montant = cd - db;
				montantCrd = 0;
				montantDeb = montant;
			}

			if (listeSaisie.size() > 1) {
				index = listeSaisie.size() - 1;
				selectedEcriture = listeSaisie.get(index);
			} else
				selectedEcriture = new Ecriture();

			selectedEcriture.setId(idEcr);
			selectedEcriture.setCpte(selectedJrnl.getContrePartie());
			selectedEcriture.setCredit(montantCrd);
			selectedEcriture.setDateOperation(dateOp);
			selectedEcriture.setDebit(montantDeb);
			selectedEcriture.setIdExercise(selecetdExercice.getId());
			selectedEcriture.setJrnl(codeJrnl);
			selectedEcriture.setLibelle(libelleOperation);
			selectedEcriture.setPieceCpb(piece);
			selectedEcriture.setOrderIndex(listeSaisie.size());
			if (listeSaisie.size() > 1) {
				listeSaisie.remove(index);
				listeSaisie.add(index, selectedEcriture);
			} else
				listeSaisie.add(selectedEcriture);
			
			calculTotaux();
			initializeControl();
		}
	}
	
	public void remove() {
		if (selectedEcriture != null) {
			if(selectedEcriture.getId()>0)
				listDelete.add(selectedEcriture);
			listeSaisie.remove(selectedEcriture);
			calculTotaux();
			initializeControl();
		}
	}

	private void getEcriture() {
		File f = new File("D:\\VIRAGO\\COMPTAVIRAGO\\SALAIRE2023.xlsx");

		try {
			FileInputStream file = new FileInputStream(f);
			XSSFWorkbook xSSFWorkbook = new XSSFWorkbook(file);
			Sheet s = xSSFWorkbook.getSheetAt(0);
			int rownumber = s.getLastRowNum() + 1;

			Journal jnl = null;
			String cpte = null, libelle = null, jrnl = null, piece = null;
			int tmp = 0, i = 0;
			Date dateOp = null;
			double deb = 0.0D, crd = 0.0D;

			for (int k = 3; k <= rownumber; k++) {

				Row row = s.getRow(k);

				if (row != null) {
					Cell cell1 = row.getCell(0);
					Cell cell2 = row.getCell(1);
					Cell cell3 = row.getCell(2);
					Cell cell4 = row.getCell(3);
					Cell cell5 = row.getCell(4);
					Cell cell6 = row.getCell(5);
					Cell cell7 = row.getCell(6);

					dateOp = cell1.getDateCellValue();
					jrnl = cell2.getStringCellValue();
					piece = cell3.getStringCellValue();
					cpte = cell4.getStringCellValue();
					libelle = cell5.getStringCellValue();

					deb = cell6.getNumericCellValue();
					crd = cell7.getNumericCellValue();

					this.selectedEcriture = new Ecriture();

					this.selectedEcriture.setCpte(cpte);
					this.selectedEcriture.setCredit(crd);
					this.selectedEcriture.setDateOperation(dateOp);
					this.selectedEcriture.setDebit(deb);
					this.selectedEcriture.setIdExercise(this.selecetdExercice.getId());
					this.selectedEcriture.setJrnl(jrnl);
					this.selectedEcriture.setLibelle(libelle);
					this.selectedEcriture.setPieceCpb(piece);

					this.model.saveEcriture(this.factory, this.selectedEcriture);

					System.out.println(
							dateOp + " - " + jrnl + "-" + piece + "-" + cpte + "-" + libelle + "-" + deb + "-" + crd);
				}

			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
 }


