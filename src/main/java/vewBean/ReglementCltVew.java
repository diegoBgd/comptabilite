package vewBean;

import entite.BankAccount;
import entite.Banque;
import entite.CentreCout;
import entite.Devise;
import entite.Encaissement;
import entite.Exercice;
import entite.Partenaire;
import entite.ReglementClient;
import entite.Taxes;
import entite.TypeEcriture;
import entite.TypeRecette;
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
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.persistence.criteria.CriteriaBuilder.Case;
import javax.servlet.http.HttpSession;
import model.BankAccountModel;
import model.BanqueModel;
import model.CentreCoutModel;
import model.DeviseModel;
import model.EncaissementModel;
import model.ExerciceModel;
import model.ReglementClientModel;
import model.TaxeModel;
import model.UserModel;
import org.hibernate.SessionFactory;
import org.primefaces.PrimeFaces;
import persistances.DBConfiguration;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class ReglementCltVew implements Serializable {
	private static final long serialVersionUID = 1684979810414140226L;
	SessionFactory factory = DBConfiguration.getSessionFactory();

	private ReglementClient selectedReglement;

	private List<ReglementClient> listReglement;
	private Encaissement selectedEncaissement;
	private List<Encaissement> listEncaissement;
	private List<TypeRecette> listeRecette;
	private List<Partenaire> listeClient;
	private List<SelectItem> listTaxe;
	private List<SelectItem> listDevise;
	private List<SelectItem> listCentre;
	private List<SelectItem> listCaisse;
	private List<SelectItem> listCompte;
	private int idTaxe;
	private int idDevise;
	private int idCentre;
	private int idCompte;
	private int idCaisse;
	private int modePmt;
	private boolean disableMsg,disableComponent;
	private Date dateDebut;
	private Date dateFin;
	private Date dateOp;
	private Partenaire selectedClient;
	private TypeRecette selectedRecette;
	private String codeClt;
	private String libelleClt;
	private String printDate;
	private String rechLblRec;
	int idReg = 0;
	private String infoMsg;
	private String printTot;
	private String codeRec;
	private String libelleRec;
	private String rechLblClt;
	private String rechDateDeb;
	private String rechDateFin;
	private String comment;
	private String pieceOper;
	private String numRegl;
	private double totalRegle;
	private double montantFacture;
	private double tauxTva;
	private double tauxDevise;
	private double montantHt;
	private double montantTvac;
	private double reste;

	ReglementClientModel model;
	Exercice selectdExercice;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;
	Devise selectedDev;
	Taxes selectedTaxe;
	CentreCout selectedCentre;
	BankAccount selectedCpt;
	Banque selectedCaisse;
	TypeEcriture typeOper;

	public Encaissement getSelectedEncaissement() {
		return this.selectedEncaissement;
	}

	public void setSelectedEncaissement(Encaissement selectedEncaissement) {
		this.selectedEncaissement = selectedEncaissement;
	}

	public List<Encaissement> getListEncaissement() {
		return this.listEncaissement;
	}

	public void setListEncaissement(List<Encaissement> listEncaissement) {
		this.listEncaissement = listEncaissement;
	}

	public List<TypeRecette> getListeRecette() {
		return this.listeRecette;
	}

	public void setListeRecette(List<TypeRecette> listeRecette) {
		this.listeRecette = listeRecette;
	}

	public List<Partenaire> getListeClient() {
		return this.listeClient;
	}

	public void setListeClient(List<Partenaire> listeClient) {
		this.listeClient = listeClient;
	}

	public List<SelectItem> getListTaxe() {
		return this.listTaxe;
	}

	public void setListTaxe(List<SelectItem> listTaxe) {
		this.listTaxe = listTaxe;
	}

	public List<SelectItem> getListDevise() {
		return this.listDevise;
	}

	public void setListDevise(List<SelectItem> listDevise) {
		this.listDevise = listDevise;
	}

	public List<SelectItem> getListCentre() {
		return this.listCentre;
	}

	public void setListCentre(List<SelectItem> listCentre) {
		this.listCentre = listCentre;
	}

	public List<SelectItem> getListCaisse() {
		return this.listCaisse;
	}

	public void setListCaisse(List<SelectItem> listCaisse) {
		this.listCaisse = listCaisse;
	}

	public List<SelectItem> getListCompte() {
		return this.listCompte;
	}

	public void setListCompte(List<SelectItem> listCompte) {
		this.listCompte = listCompte;
	}

	public int getIdTaxe() {
		return this.idTaxe;
	}

	public void setIdTaxe(int idTaxe) {
		this.idTaxe = idTaxe;
	}

	public int getIdDevise() {
		return this.idDevise;
	}

	public void setIdDevise(int idDevise) {
		this.idDevise = idDevise;
	}

	public int getIdCentre() {
		return this.idCentre;
	}

	public void setIdCentre(int idCentre) {
		this.idCentre = idCentre;
	}

	public int getIdCompte() {
		return this.idCompte;
	}

	public void setIdCompte(int idCompte) {
		this.idCompte = idCompte;
	}

	public int getIdCaisse() {
		return this.idCaisse;
	}

	public void setIdCaisse(int idCaisse) {
		this.idCaisse = idCaisse;
	}

	public int getModePmt() {
		return this.modePmt;
	}

	public void setModePmt(int modePmt) {
		this.modePmt = modePmt;
	}

	public boolean isDisableMsg() {
		return this.disableMsg;
	}

	public void setDisableMsg(boolean disableMsg) {
		this.disableMsg = disableMsg;
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

	public Date getDateOp() {
		return this.dateOp;
	}

	public void setDateOp(Date dateOp) {
		this.dateOp = dateOp;
	}

	public Partenaire getSelectedClient() {
		return selectedClient;
	}

	public void setSelectedClient(Partenaire selectedClient) {
		this.selectedClient = selectedClient;
	}

	public TypeRecette getSelectedRecette() {
		return this.selectedRecette;
	}

	public void setSelectedRecette(TypeRecette selectedRecette) {
		this.selectedRecette = selectedRecette;
	}

	public String getCodeClt() {
		return this.codeClt;
	}

	public void setCodeClt(String codeClt) {
		this.codeClt = codeClt;
	}

	public String getLibelleClt() {
		return this.libelleClt;
	}

	public void setLibelleClt(String libelleClt) {
		this.libelleClt = libelleClt;
	}

	public String getPrintDate() {
		return this.printDate;
	}

	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	public String getRechLblRec() {
		return this.rechLblRec;
	}

	public void setRechLblRec(String rechLblRec) {
		this.rechLblRec = rechLblRec;
	}

	public String getInfoMsg() {
		return this.infoMsg;
	}

	public void setInfoMsg(String infoMsg) {
		this.infoMsg = infoMsg;
	}

	public String getPrintTot() {
		return this.printTot;
	}

	public void setPrintTot(String printTot) {
		this.printTot = printTot;
	}

	public String getCodeRec() {
		return this.codeRec;
	}

	public void setCodeRec(String codeRec) {
		this.codeRec = codeRec;
	}

	public String getLibelleRec() {
		return this.libelleRec;
	}

	public void setLibelleRec(String libelleRec) {
		this.libelleRec = libelleRec;
	}

	public String getRechLblClt() {
		return this.rechLblClt;
	}

	public void setRechLblClt(String rechLblClt) {
		this.rechLblClt = rechLblClt;
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

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPieceOper() {
		return this.pieceOper;
	}

	public void setPieceOper(String pieceOper) {
		this.pieceOper = pieceOper;
	}

	public String getNumRegl() {
		return this.numRegl;
	}

	public void setNumRegl(String numRegl) {
		this.numRegl = numRegl;
	}

	public double getTotalRegle() {
		return this.totalRegle;
	}

	public void setTotalRegle(double totalRegle) {
		this.totalRegle = totalRegle;
	}

	public double getMontantFacture() {
		return this.montantFacture;
	}

	public void setMontantFacture(double montantFacture) {
		this.montantFacture = montantFacture;
	}

	public double getTauxTva() {
		return this.tauxTva;
	}

	public void setTauxTva(double tauxTva) {
		this.tauxTva = tauxTva;
	}

	public double getTauxDevise() {
		return this.tauxDevise;
	}

	public void setTauxDevise(double tauxDevise) {
		this.tauxDevise = tauxDevise;
	}

	public double getMontantHt() {
		return this.montantHt;
	}

	public void setMontantHt(double montantHt) {
		this.montantHt = montantHt;
	}

	public double getMontantTvac() {
		return this.montantTvac;
	}

	public void setMontantTvac(double montantTvac) {
		this.montantTvac = montantTvac;
	}

	public ReglementClient getSelectedReglement() {
		return this.selectedReglement;
	}

	public void setSelectedReglement(ReglementClient selectedReglement) {
		this.selectedReglement = selectedReglement;
	}

	public List<ReglementClient> getListReglement() {
		return this.listReglement;
	}

	public void setListReglement(List<ReglementClient> listReglement) {
		this.listReglement = listReglement;
	}

	public double getReste() {
		return reste;
	}

	public void setReste(double reste) {
		this.reste = reste;
	}

	public boolean isDisableComponent() {
		return disableComponent;
	}

	public void setDisableComponent(boolean disableComponent) {
		this.disableComponent = disableComponent;
	}

	@PostConstruct
	public void initialize() {
		this.disableMsg = true;
		this.model = new ReglementClientModel();
		chargementSession();
		chargerTaxe();
		chargerDevise();
		chargerCentre();
		chargerBanque();
	}

	private void chargementSession() {
		this.session = HelperC.getSession();
		if (this.session != null) {
			this.exerCode = (String) this.session.getAttribute("exercice");
			this.currUserCode = (String) this.session.getAttribute("operateur");

			if (this.exerCode != null) {
				this.selectdExercice = (new ExerciceModel()).getExercByCode(this.factory, this.exerCode);
			}
			if (this.currUserCode != null) {
				this.currentUser = (new UserModel()).getUserByCode(this.factory, this.currUserCode);
			}

			if (this.currentUser == null || this.selectdExercice == null) {
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("/comptabilite/login.xhtml");
				} catch (IOException e) {

					e.printStackTrace();
				}
			} else {

				this.typeOper = TypeEcriture.reglementClient;
			}
		}
	}

	public void changePaiementMode(ValueChangeEvent event) {
		
		modePmt=((Integer) event.getNewValue()).intValue();
		
		switch (modePmt) {
		case 1:
			disableComponent=true;
			break;
		case 2:
		case 3:
			disableComponent=false;
			break;

		default:
			disableComponent=false;
			break;
		}
	}
	private void chargerBanque() {
		this.listCaisse = new ArrayList<>();

		for (Banque bk : (new BanqueModel()).getListBanque(this.factory, "")) {
			this.listCaisse.add(new SelectItem(Integer.valueOf(bk.getId()), bk.getLibelle()));
		}
	}

	private void chargerCompte() {
		this.listCompte = new ArrayList<>();

		for (BankAccount cpt : (new BankAccountModel()).getListAccount(this.factory, this.selectedCaisse)) {
			this.listCompte.add(new SelectItem(Integer.valueOf(cpt.getId()), cpt.getAccCode()));
		}
	}

	private void chargerTaxe() {
		this.listTaxe = new ArrayList<>();

		for (Taxes tx : (new TaxeModel()).getListTaxes(this.factory, "")) {
			this.listTaxe.add(new SelectItem(Integer.valueOf(tx.getId()), tx.getLibelle()));
		}
	}

	private void chargerDevise() {
		this.listDevise = new ArrayList<>();

		for (Devise dv : (new DeviseModel()).getListDevise(this.factory, "")) {
			this.listDevise.add(new SelectItem(Integer.valueOf(dv.getId()), dv.getLibelle()));
		}
	}

	private void chargerCentre() {
		this.listCentre = new ArrayList<>();

		for (CentreCout c : (new CentreCoutModel()).getListCentreCout(this.factory)) {
			this.listCentre.add(new SelectItem(Integer.valueOf(c.getId()), c.getLibelle()));
		}
	}

	public void changeCentreElement(ValueChangeEvent event) {
		if (event != null && event.getNewValue() != null) {

			this.idCentre = ((Integer) event.getNewValue()).intValue();
			centreValue();
		}
	}

	private void centreValue() {
		if (this.idCentre > 0)
			this.selectedCentre = (new CentreCoutModel()).getCentreCoutById(this.idCentre, this.factory);
	}

	public void changeTaxeElement(ValueChangeEvent event) {
		if (event != null && event.getNewValue() != null) {

			this.idTaxe = ((Integer) event.getNewValue()).intValue();
			taxeValue();
		}
	}

	private void taxeValue() {
		if (this.idTaxe > 0) {

			this.selectedTaxe = (new TaxeModel()).getTaxeById(this.idTaxe, this.factory);
			if (this.selectedTaxe != null) {

				this.tauxTva = this.selectedTaxe.getTaux();
				calculerMontantTTC();
			} else {

				this.tauxTva = 0.0D;
			}
		}
	}

	public void changeDeviseElement(ValueChangeEvent event) {
		if (event != null && event.getNewValue() != null) {

			this.idDevise = ((Integer) event.getNewValue()).intValue();
			deviseValue();
		}
	}

	public void changeBankElement(ValueChangeEvent event) {
		if (event != null && event.getNewValue() != null) {

			this.idCaisse = ((Integer) event.getNewValue()).intValue();
			caisseValue();
		}
	}

	private void caisseValue() {
		if (this.idCaisse > 0) {

			this.selectedCaisse = (new BanqueModel()).getBanqueById(this.idCaisse, this.factory);
			if (this.selectedCaisse != null)
				chargerCompte();
		}
	}

	public void changeCompteElement(ValueChangeEvent event) {
		if (event != null && event.getNewValue() != null) {

			this.idCompte = ((Integer) event.getNewValue()).intValue();
			compteValue();
		}
	}

	private void compteValue() {
		if (this.idCompte > 0)
			this.selectedCpt = (new BankAccountModel()).getBankAccountById(this.idCompte, this.factory);
	}

	private void deviseValue() {
		if (this.idDevise > 0)
			this.selectedDev = (new DeviseModel()).getDeviseById(this.idDevise, this.factory);
	}

	public void calculerMontantTTC() {
		this.montantTvac = 0.0D;
		if (this.tauxTva > 0.0D) {

			this.montantTvac = this.montantHt * (1.0D + this.tauxTva / 100.0D);
		} else {

			this.montantTvac = this.montantHt;
		}
	}

	public void calculerMontantHT() {
		if (this.tauxTva > 0.0D) {

			this.montantHt = this.montantTvac / (1.0D + this.tauxTva / 100.0D);
		} else {

			this.montantHt = this.montantTvac;
		}
	}

	public void changeDate() {
		if (this.printDate.replace("/", "").replace("_", "").trim().equals("")) {

			this.dateOp = null;
		} else {

			this.dateOp = HelperC.validerDate(this.printDate);
			if (this.dateOp == null) {

				HelperC.afficherAttention("Attention", "Date invalide !");
			} else {

				this.printDate = HelperC.convertDate(this.dateOp);
				if (!HelperC.periodeValide(this.dateOp, this.selectdExercice.getDateDebut(),
						this.selectdExercice.getDateFin())) {
					HelperC.afficherAttention("Attention", "La date saisie est en dehors de l'exercice !");
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

	public void takeSelectedFacture() {
		getFactureClient();
		PrimeFaces.current().executeScript("PF('dlgFclt').hide();");
	}

	private void clientValues() {
		if (this.selectedClient != null) {

			this.codeClt = this.selectedClient.getCodeCpbl();
			this.libelleClt = this.selectedClient.getNomination();
		}
	}

	private void recetteValues() {
		if (this.selectedRecette != null) {

			this.codeRec = this.selectedRecette.getCodeRec();
			this.libelleRec = this.selectedRecette.getLibelle();
		}
	}

	public void chargerFactureClient() {
		this.listEncaissement = (new EncaissementModel()).getListEncaissement(this.factory,
				this.selectdExercice.getId(), getDateDebut(), getDateFin(), TypeEcriture.factureClient);
	}

	public void getSelectedFactureClient() {
		getFactureClient();
		PrimeFaces.current().executeScript("PF('dlgFclt').hide();");
	}

	private void getFactureClient() {
		this.pieceOper = this.selectedEncaissement.getNumOperation();
		this.selectedClient = this.selectedEncaissement.getPartener();
		this.selectedRecette = this.selectedEncaissement.getRecette();
		this.selectedTaxe = this.selectedEncaissement.getTaxe();
		this.tauxTva = this.selectedEncaissement.getTauxTaxe();

		this.selectedEncaissement.calculMontantTTC();
		this.montantFacture = this.selectedEncaissement.getMontantTTC();

		recetteValues();
		clientValues();
		if (this.selectedTaxe != null)
			this.idTaxe = this.selectedTaxe.getId();
		
		chargerReglement();
		totalRegle=0;
		for (ReglementClient reg : listReglement) {
			reg.calculMontantTTC();
			totalRegle+=reg.getMontantTTC();
		}
		reste=montantFacture-totalRegle;
	}

	public void chargerReglement() {
		if (this.selectedEncaissement != null) {
			this.listReglement = this.model.getListReglement(this.factory, this.selectdExercice.getId(), this.dateDebut,
					this.dateFin, this.selectedEncaissement);
		} else {
			HelperC.afficherAttention("Attention", "Il faut préciser la facture !");
		}
	}

	public void takeSelectedReglement() {
		reglementValues();

		PrimeFaces.current().executeScript("PF('dlgRglClt').hide();");
	}

	private void reglementValues() {
		
		if (this.selectedReglement != null) {
			this.idReg = this.selectedReglement.getId();
			this.dateOp = this.selectedReglement.getDateReglement();
			this.pieceOper = this.selectedReglement.getPiece();
			this.comment = this.selectedReglement.getComment();
			this.selectedEncaissement = this.selectedReglement.getEncaissement();
			this.printDate = HelperC.convertDate(this.dateOp);
			this.disableMsg = false;
			this.tauxDevise = this.selectedReglement.getTaux();

			this.montantHt = this.selectedReglement.getMontantRegle();
			calculerMontantTTC();
			this.selectedClient = this.selectedEncaissement.getPartener();
			this.selectedTaxe = this.selectedReglement.getTaxeRgl();
			this.selectedCaisse = this.selectedReglement.getCaisse();
			this.selectedDev = this.selectedReglement.getDeviseRgl();
			this.selectedCpt = this.selectedReglement.getAccount();

			if (this.selectedCaisse != null) {
				this.idCaisse = this.selectedCaisse.getId();
				chargerCompte();
			}
			if (this.selectedCpt != null)
				this.idCompte = this.selectedCpt.getId();
			if (this.selectedDev != null)
				this.idDevise = this.selectedDev.getId();
			if (this.selectedTaxe != null)
				this.idTaxe = this.selectedTaxe.getId();
			
			
		}
	}

	public void save() {
		
		if(!testMontant())
		{
			HelperC.afficherAttention("Attention",
					"La montant à  régler ne peut pas dépasser total de la facture!");
			return;
		}
		if (this.selectedEncaissement == null) {
			return;
		}

		if (this.selectedReglement == null)
			this.selectedReglement = new ReglementClient();
		
		
		this.selectedReglement.setId(this.idReg);
		this.selectedReglement.setAccount(this.selectedCpt);
		this.selectedReglement.setCaisse(this.selectedCaisse);
		this.selectedReglement.setComment(this.comment);
		this.selectedReglement.setCoursRgl(Double.valueOf(this.tauxDevise));
		this.selectedReglement.setDateReglement(this.dateOp);
		this.selectedReglement.setDeviseRgl(this.selectedDev);
		this.selectedReglement.setEncaissement(this.selectedEncaissement);
		this.selectedReglement.setIdExercise(this.selectdExercice.getId());
		this.selectedReglement.setModeReglement(this.modePmt);
		this.selectedReglement.setMontantRegle(this.montantHt);
		this.selectedReglement.setMontantTTC(this.montantTvac);
		this.selectedReglement.setPiece(this.pieceOper);
		this.selectedReglement.setTaux(this.tauxTva);
		this.selectedReglement.setTaxeRgl(this.selectedTaxe);

		if (this.selectedReglement.getId() == 0) {
			this.model.saveReglementClt(this.factory, this.selectedReglement);
		} else {
			this.model.updateReglementClt(this.factory, this.selectedReglement);
		}
		initializeControl();
	}

	public void delete() {
		if (this.selectedReglement != null && this.selectedReglement.getId() > 0) {
			this.model.deleteReglementClt(this.factory, this.selectedReglement);
			initializeControl();
		}
	}
	private boolean testMontant() {
		boolean bl = false;
		if (this.totalRegle + getMontantTvac() <= this.montantFacture)
			bl = true;
		return bl;
	}
	public void initializeControl() {
		this.selectedCentre = null;
		this.selectedDev = null;
		this.selectedRecette = null;
		this.selectedTaxe = null;
		this.selectedEncaissement = null;
		this.selectedReglement = null;
		this.codeClt = "";
		this.codeRec = "";
		this.libelleClt = "";
		this.libelleRec = "";
		this.idCaisse = 0;
		this.idCentre = 0;
		this.idCompte = 0;
		this.idDevise = 0;
		this.idTaxe = 0;
		this.pieceOper = "";
		this.comment = "";
		this.modePmt = 0;
		this.tauxDevise = 0.0D;
		this.tauxTva = 0.0D;
		this.idReg = 0;
		this.montantTvac = 0.0D;
		this.montantHt = 0.0D;
		this.disableMsg = true;
	}
}
