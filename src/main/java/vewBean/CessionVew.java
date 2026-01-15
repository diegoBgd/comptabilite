package vewBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.print.attribute.standard.Severity;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.primefaces.PrimeFaces;

import entite.Amortissement;
import entite.Cession;
import entite.Ecriture;
import entite.Exercice;
import entite.Immobilise;
import entite.Journal;
import entite.ParametreImmo;
import entite.TypeCession;
import entite.TypeEcriture;
import entite.User;
import model.AmortisementModel;
import model.CessionModel;
import model.ExerciceModel;
import model.ImmobiliseModel;
import model.ParametreImmoModel;
import model.UserModel;
import persistances.DBConfiguration;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class CessionVew implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -900507180117052137L;
	SessionFactory factory = DBConfiguration.getSessionFactory();
	private Immobilise selectedImmo;
	private String codeImmo, libelleImmo, printDateAq, printDateCession, printValeurAq, printTotlAmort, printVnc,
			motifCession, refCession, printDiff, motLibelle, numeroPiece, dureeVie;

	private double montantCession, montantVnc, totAmort;
	private boolean disableMsg, disableSave, vente;
	private List<Immobilise> listImmo;
	private List<Cession> listCession;
	private List<Ecriture> listEcriture;
	private Cession selectedCession;
	private int type;
	CessionModel model;
	Date dateCession;
	Exercice selecetdExercice;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;
	int idCession;
	double montantDif;
	Amortissement amort;
	TypeCession cessionType;
	Journal jrnalAmrt;
	ParametreImmo parametre;
	String codeJrnl="";
	public CessionVew() {

	}

	public Immobilise getSelectedImmo() {
		return selectedImmo;
	}

	public void setSelectedImmo(Immobilise selectedImmo) {
		this.selectedImmo = selectedImmo;
	}

	public String getCodeImmo() {
		return codeImmo;
	}

	public void setCodeImmo(String codeImmo) {
		this.codeImmo = codeImmo;
	}

	public String getLibelleImmo() {
		return libelleImmo;
	}

	public void setLibelleImmo(String libelleImmo) {
		this.libelleImmo = libelleImmo;
	}

	public String getPrintDateAq() {
		return printDateAq;
	}

	public void setPrintDateAq(String printDateAq) {
		this.printDateAq = printDateAq;
	}

	public String getPrintValeurAq() {
		return printValeurAq;
	}

	public void setPrintValeurAq(String printValeurAq) {
		this.printValeurAq = printValeurAq;
	}

	public String getPrintTotlAmort() {
		return printTotlAmort;
	}

	public void setPrintTotlAmort(String printTotlAmort) {
		this.printTotlAmort = printTotlAmort;
	}

	public String getPrintVnc() {
		return printVnc;
	}

	public void setPrintVnc(String printVnc) {
		this.printVnc = printVnc;
	}

	public String getMotifCession() {
		return motifCession;
	}

	public void setMotifCession(String motifCession) {
		this.motifCession = motifCession;
	}

	public String getRefCession() {
		return refCession;
	}

	public void setRefCession(String refCession) {
		this.refCession = refCession;
	}

	public String getPrintDiff() {
		return printDiff;
	}

	public void setPrintDiff(String printDiff) {
		this.printDiff = printDiff;
	}

	public double getMontantCession() {
		return montantCession;
	}

	public void setMontantCession(double montantCession) {
		this.montantCession = montantCession;
	}

	public double getMontantVnc() {
		return montantVnc;
	}

	public void setMontantVnc(double montantVnc) {
		this.montantVnc = montantVnc;
	}

	public double getTotAmort() {
		return totAmort;
	}

	public void setTotAmort(double totAmort) {
		this.totAmort = totAmort;
	}

	public List<Immobilise> getListImmo() {
		return listImmo;
	}

	public void setListImmo(List<Immobilise> listImmo) {
		this.listImmo = listImmo;
	}

	public String getPrintDateCession() {
		return printDateCession;
	}

	public void setPrintDateCession(String printDateCession) {
		this.printDateCession = printDateCession;
	}

	public String getMotLibelle() {
		return motLibelle;
	}

	public void setMotLibelle(String motLibelle) {
		this.motLibelle = motLibelle;
	}

	public boolean isDisableMsg() {
		return disableMsg;
	}

	public void setDisableMsg(boolean disableMsg) {
		this.disableMsg = disableMsg;
	}

	public Cession getSelectedCession() {
		return selectedCession;
	}

	public void setSelectedCession(Cession selectedCession) {
		this.selectedCession = selectedCession;
	}

	public String getNumeroPiece() {
		return numeroPiece;
	}

	public void setNumeroPiece(String numeroPiece) {
		this.numeroPiece = numeroPiece;
	}

	public List<Cession> getListCession() {
		return listCession;
	}

	public void setListCession(List<Cession> listCession) {
		this.listCession = listCession;
	}

	public boolean isDisableSave() {
		return disableSave;
	}

	public void setDisableSave(boolean disableSave) {
		this.disableSave = disableSave;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDureeVie() {
		return dureeVie;
	}

	public void setDureeVie(String dureeVie) {
		this.dureeVie = dureeVie;
	}

	public boolean isVente() {
		return vente;
	}

	public void setVente(boolean vente) {
		this.vente = vente;
	}

	public List<Ecriture> getListEcriture() {
		return listEcriture;
	}

	public void setListEcriture(List<Ecriture> listEcriture) {
		this.listEcriture = listEcriture;
	}

	@PostConstruct
	public void intit() {
		model = new CessionModel();
		chargementImmo();
		chargementSession();
		chargerarametre();
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
			}
		}
	}
	private void chargerarametre() {
		this.parametre = (new ParametreImmoModel()).getParmImmo(this.factory);
		if (this.parametre != null)
		{
			this.jrnalAmrt = this.parametre.getJournalAmort();
			if(jrnalAmrt!=null)
			codeJrnl=jrnalAmrt.getCodeJrnl();
		}
	}

	private void chargementImmo() {
		listImmo = new ImmobiliseModel().getListImmo(factory, motLibelle);
	}

	public void searchImmo() {
		if (codeImmo != null && !codeImmo.equals("")) {
			selectedImmo = new ImmobiliseModel().getImmoByCode(factory, codeImmo);
			getImmoValues();
		}
	}

	public void takeSelectedImmo() {
		getImmoValues();
		PrimeFaces.current().executeScript("PF('dlgImmo').hide();");
	}

	private void getImmoValues() {
		if (selectedImmo != null) {
			codeImmo = selectedImmo.getCodeImmo();
			libelleImmo = selectedImmo.getLibelle();
			printDateAq = HelperC.convertDate(selectedImmo.getDateAcquisition());
			printValeurAq = selectedImmo.getPrintAcqValue();
			amort = new AmortisementModel().getLastAmortImmo(factory, selectedImmo);
			dureeVie = selectedImmo.getNbrAnne() + " ans";
			
			
			if (amort != null) {
				totAmort = amort.getAmrtAnterieur() + amort.getAmortExercice();
				montantVnc = selectedImmo.getMontantAcq() - totAmort;
				
				createEcriture();
			}
		}
	}

	private boolean checkCession() {
		boolean bl=false;
		selectedCession=model.getCessionImmo(factory, selectedImmo);
		if(selectedCession!=null)
		bl=true;
		
		return bl;
	}
	public void checkType() {
		if (type == 1)
			vente = true;
		else
			vente = false;
	}

	public void changeDateCession() {
		disableSave = false;
		if (this.printDateCession.replace("/", "").replace("_", "").trim().equals("")) {
			this.dateCession = null;
		} else {

			this.dateCession = HelperC.validerDate(this.printDateCession);
			if (this.dateCession == null) {
				this.disableSave = true;
				this.printDateCession = "";
				HelperC.afficherMessage("Information", "Date invalide!");
			} else {

				this.printDateCession = HelperC.convertDate(this.dateCession);
				createEcriture();
			}
		}
	}

	public void chargerListCession() {
		listCession = model.getListeCession(factory);
	}

	public void takeSelectedCession() {
		getCessionValues();
		PrimeFaces.current().executeScript("PF('dlgCession').hide();");
	}

	public void calculPlusMoinsValue() {
		if (montantCession > montantVnc) {
			montantDif = montantCession - montantVnc;
			printDiff = "Plus value : " + HelperC.decimalNumber(montantDif, 0, true);
		}

		if (montantCession < montantVnc) {
			montantDif = montantVnc - montantCession;
			printDiff = "Moins value : " + HelperC.decimalNumber(montantDif, 0, true);
		}
	}

	private void getCessionValues() {
		idCession = selectedCession.getId();
		dateCession = selectedCession.getDateCession();
		selectedImmo = selectedCession.getImmo();
		montantCession = selectedCession.getMontantCession();
		numeroPiece = selectedCession.getPiece();
		montantVnc = selectedCession.getVnc();
		cessionType = selectedCession.getTypeCession();
		getImmoValues();
		calculPlusMoinsValue();
		switch (cessionType) {
		case DECLASSEMENT:
			type = 0;
			break;
		case VENTE:
			type = 1;
			checkType();
			break;
		case DON:
			type = 2;
			break;
		case PERTE:
			type = 3;
			break;
		default:
			break;
		}
	}

	public void save() {
		
		if(selectedImmo==null)
		{
			HelperC.afficherMessage("Information", "Il faut préciser l'immo !",FacesMessage.SEVERITY_WARN); 
			return;
		}
		if(checkCession())
		{
			HelperC.afficherMessage("Information", "Cet immo est déjà cédé !",FacesMessage.SEVERITY_WARN);
			return;
		}
		if (selectedCession == null)
			selectedCession = new Cession();
		selectedCession.setId(idCession);
		selectedCession.setCompteAmort(selectedImmo.getCompteDotAmrt());
		selectedCession.setDateCession(dateCession);
		selectedCession.setImmo(selectedImmo);
		selectedCession.setMontantCession(montantCession);
		selectedCession.setPiece(numeroPiece);
		selectedCession.setVnc(montantVnc);
		selectedCession.setMotif(motifCession);
		if(listEcriture!=null && listEcriture.size()>0)
		selectedCession.setListEcriture(listEcriture);
		switch (type) {
		case 0:
			cessionType = TypeCession.DECLASSEMENT;
			break;
		case 1:
			cessionType = TypeCession.VENTE;
			break;
		case 2:
			cessionType = TypeCession.DON;
			break;
		case 3:
			cessionType = TypeCession.PERTE;
			break;
		default:
			break;
		}
		selectedCession.setTypeCession(cessionType);
		model.saveCession(factory, selectedCession);
		initialiseControls();
	}

	public void delete() {
		if (selectedCession != null && selectedCession.getId() > 0) {
			model.deleteCession(factory,jrnalAmrt.getCodeJrnl(), selectedCession.getId());
			initialiseControls();
		}
	}

	public void initialiseControls() {
		idCession = 0;
		dateCession = null;
		selectedImmo = null;
		montantCession = 0;
		numeroPiece = "";
		montantVnc = 0;
		type = 0;
		listEcriture.clear();
	}
	private void createEcriture() {
		 
		if(selectedImmo!=null) {
		 Ecriture ecr = null;
	     this.listEcriture = new ArrayList<>();
	     ecr = new Ecriture();
	     ecr.setCpte(selectedImmo.getCompteAmrt());
	     ecr.setJrnl(jrnalAmrt.getCodeJrnl());
	     ecr.setDateOperation(dateCession);
	     ecr.setDebit(totAmort);
	     ecr.setCredit(0.0D);
	     ecr.setIdExercise(selecetdExercice.getId());
	     ecr.setLibelle("AMORTISSEMENT DES IMMO CEDES");
	     ecr.setTypeOperation(TypeEcriture.cession);
	     ecr.setPieceCpb("");
	     ecr.setSourceOperation(0);
	     this.listEcriture.add(ecr);
	     
	   	    
	     ecr = new Ecriture();
	     ecr.setCpte(selectedImmo.getCompteDotAmrt());
	     ecr.setJrnl(jrnalAmrt.getCodeJrnl());
	     ecr.setDateOperation(dateCession);
	     ecr.setDebit(montantVnc);
	     ecr.setCredit(0.0D);
	     ecr.setIdExercise(selecetdExercice.getId());
	     ecr.setLibelle("AMORTISSEMENT DES IMMO CEDES");
	     ecr.setTypeOperation(TypeEcriture.cession);
	     ecr.setPieceCpb("");
	     ecr.setSourceOperation(0);
	     this.listEcriture.add(ecr);
	     
	     ecr = new Ecriture();
	     ecr.setCpte(selectedImmo.getCompteImmo());
	     ecr.setJrnl(jrnalAmrt.getCodeJrnl());
	     ecr.setDateOperation(dateCession);
	     ecr.setDebit(0.0D);
	     ecr.setCredit(selectedImmo.getMontantAcq());
	     ecr.setIdExercise(selecetdExercice.getId());
	     ecr.setLibelle("AMORTISSEMENT DES IMMO CEDES");
	     ecr.setTypeOperation(TypeEcriture.cession);
	     ecr.setPieceCpb("");
	     ecr.setSourceOperation(0);
	     this.listEcriture.add(ecr);
	     
	     
		}
	}
}
