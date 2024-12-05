package vewBean;

import entite.BankAccount;
import entite.Banque;
import entite.CentreCout;
import entite.Clients;
import entite.Devise;
import entite.Encaissement;
import entite.Exercice;
import entite.Partenaire;
import entite.Taxes;
import entite.TypeEcriture;
import entite.TypePartener;
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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import model.BankAccountModel;
import model.BanqueModel;
import model.CentreCoutModel;
import model.ClientModel;
import model.DeviseModel;
import model.EncaissementModel;
import model.ExerciceModel;
import model.PartenerModel;
import model.TaxeModel;
import model.TypeRecetteModel;
import model.UserModel;
import org.hibernate.SessionFactory;
import org.primefaces.PrimeFaces;
import persistances.DBConfiguration;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class EncaissementVew implements Serializable {
	private static final long serialVersionUID = -6355865421420146388L;
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
	private boolean disableMsg;
	private Date dateDebut;
	private Date dateFin;
	private Date dateOp;
	private Partenaire selectedClient;
	SessionFactory factory = DBConfiguration.getSessionFactory();
	private TypeRecette selectedRecette;
	private String codeClt;
	private String libelleClt;
	private String printDate;
	private String rechLblRec;
	private String infoMsg;
	private String printTot;
	private String codeRec;
	private String libelleRec;
	private String rechLblClt;
	private String rechDateDeb;
	private String rechDateFin;
	private String comment;
	private String pieceOper;
	private String numEnc;
	private double totalRegle;
	private double montantFacture;
	private double tauxTva;
	private double tauxDevise;
	private double montantHt;
	private double montantTvac;
	EncaissementModel model;
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
	int idEnc = 0;

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

	public Date getDateOp() {
		return this.dateOp;
	}

	public void setDateOp(Date dateOp) {
		this.dateOp = dateOp;
	}

	public double getTauxDevise() {
		return this.tauxDevise;
	}

	public void setTauxDevise(double tauxDevise) {
		this.tauxDevise = tauxDevise;
	}

	public String getPieceOper() {
		return this.pieceOper;
	}

	public void setPieceOper(String pieceOper) {
		this.pieceOper = pieceOper;
	}

	public double getTauxTva() {
		return this.tauxTva;
	}

	public void setTauxTva(double tauxTva) {
		this.tauxTva = tauxTva;
	}

	public int getModePmt() {
		return this.modePmt;
	}

	public void setModePmt(int modePmt) {
		this.modePmt = modePmt;
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

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getNumEnc() {
		return this.numEnc;
	}

	public void setNumEnc(String numEnc) {
		this.numEnc = numEnc;
	}

	@PostConstruct
	public void initialize() {
		this.disableMsg = true;
		this.model = new EncaissementModel();
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
			tauxDevise=1;
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
				UIComponent frmEntreFnd = null;
				FacesContext context = FacesContext.getCurrentInstance();
				frmEntreFnd = context.getViewRoot().findComponent("frmEF");

				UIComponent frmFactClt = null;
				frmFactClt = context.getViewRoot().findComponent("frmFcl");

				if (frmEntreFnd != null) {
					this.typeOper = TypeEcriture.entreeFond;
				}
				if (frmFactClt != null) {
					this.typeOper = TypeEcriture.factureClient;
				}
			}
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

	private void deviseValue() {
		if (this.idDevise > 0) {
			this.selectedDev = (new DeviseModel()).getDeviseById(this.idDevise, this.factory);
		}
	}

	public void chargerClient() {
		this.listeClient = new PartenerModel().getAllPartenaire(TypePartener.CLIENT, factory);
	}

	public void searchClient() {
		if (this.codeClt != null && !this.codeClt.equals("")) {

			this.selectedClient = new PartenerModel().getPartenaireByCode(codeClt, TypePartener.CLIENT, factory);
			clientValues();
		}
	}

	public void getSelectedClientValue() {
		clientValues();
		PrimeFaces.current().executeScript("PF('dlgClt').hide();");
	}

	private void clientValues() {
		if (this.selectedClient != null) {

			this.codeClt = this.selectedClient.getCodePartener();
			this.libelleClt = this.selectedClient.getNomination();
		}
	}

	public void searchRecette() {
		if (this.codeRec != null && !this.codeRec.equals("")) {

			this.selectedRecette = (new TypeRecetteModel()).getTypeProduiByCode(this.factory, this.codeRec);
			recetteValues();
		}
	}

	public void chargerRecette() {
		this.listeRecette = (new TypeRecetteModel()).getListTypeRecette(this.factory, this.rechLblRec);
	}

	public void getSelectedRecetteValue() {
		recetteValues();
		PrimeFaces.current().executeScript("PF('dlgRec').hide();");
	}

	private void recetteValues() {
		if (this.selectedRecette != null) {

			this.codeRec = this.selectedRecette.getCodeRec();
			this.libelleRec = this.selectedRecette.getLibelle();
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
		if (this.idCompte > 0) {
			this.selectedCpt = (new BankAccountModel()).getBankAccountById(this.idCompte, this.factory);
		}
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

	public void chargerEncaissement() {
		this.listEncaissement = this.model.getListEncaissement(this.factory, this.selectdExercice.getId(),
				getDateDebut(), getDateFin(), this.typeOper);
	}

	public void getSelectedFactureClient() {
		getEncaissementValue();
		PrimeFaces.current().executeScript("PF('dlgFclt').hide();");
	}
	private void getEncaissementValue() {
		if (this.selectedEncaissement != null) {
			this.selectedCentre = this.selectedEncaissement.getCentre();
			this.selectedDev = this.selectedEncaissement.getDevise();
			this.selectedRecette = this.selectedEncaissement.getRecette();
			this.selectedTaxe = this.selectedEncaissement.getTaxe();
			this.selectedCpt = this.selectedEncaissement.getCompteBank();
			this.selectedCaisse = this.selectedEncaissement.getBank();
			this.selectedClient = this.selectedEncaissement.getPartener();
			this.pieceOper = this.selectedEncaissement.getPiece();
			this.comment = this.selectedEncaissement.getCommentaire();
			this.modePmt = this.selectedEncaissement.getModeReglement();
			this.tauxDevise = this.selectedEncaissement.getCoursDev();
			this.tauxTva = this.selectedEncaissement.getTauxTaxe();
			this.idEnc = this.selectedEncaissement.getId();
			this.numEnc = this.selectedEncaissement.getNumOperation();
			this.montantHt = this.selectedEncaissement.getMontant();
			this.dateOp = this.selectedEncaissement.getDateOperation();
			this.printDate = HelperC.convertDate(this.dateOp);

			if (this.selectedCaisse != null) {
				this.idCaisse = this.selectedCaisse.getId();
			}
			if (this.selectedCpt != null) {
				this.idCompte = this.selectedCpt.getId();
			}
			if (this.selectedDev != null)
				this.idDevise = this.selectedDev.getId();
			if (this.selectedTaxe != null) {
				this.idTaxe = this.selectedTaxe.getId();
			}
			clientValues();
			recetteValues();
			calculerMontantTTC();
		}
	}

	public void getSelectedEntreeFond() {
		getEncaissementValue();
		PrimeFaces.current().executeScript("PF('dlgEf').hide();");
	}

	public void getSelectedReglementValue() {
		getEncaissementValue();
		PrimeFaces.current().executeScript("PF('dlgRglClt').hide();");
	}

	public void save() {
		if (this.dateOp != null) {

			if (this.selectedEncaissement == null)
				this.selectedEncaissement = new Encaissement();
			this.selectedEncaissement.setId(this.idEnc);
			this.selectedEncaissement.setBank(this.selectedCaisse);
			this.selectedEncaissement.setCentre(this.selectedCentre);
			this.selectedEncaissement.setCompteBank(this.selectedCpt);
			this.selectedEncaissement.setCoursDev(this.tauxDevise);
			this.selectedEncaissement.setDateOperation(this.dateOp);
			this.selectedEncaissement.setCommentaire(this.comment);
			this.selectedEncaissement.setIdExercise(this.selectdExercice.getId());
			this.selectedEncaissement.setDevise(this.selectedDev);
			this.selectedEncaissement.setModeReglement(this.modePmt);
			this.selectedEncaissement.setMontant(this.montantHt);
			this.selectedEncaissement.setMontantTTC(this.montantTvac);
			this.selectedEncaissement.setPiece(this.pieceOper);
			this.selectedEncaissement.setRecette(this.selectedRecette);
			this.selectedEncaissement.setTauxTaxe(this.tauxTva);
			this.selectedEncaissement.setTaxe(this.selectedTaxe);
			this.selectedEncaissement.setNumOperation(this.numEnc);
			this.selectedEncaissement.setTypeEntree(this.typeOper);
			this.selectedEncaissement.setPartener(selectedClient);

			if (this.selectedEncaissement.getId() == 0) {
				this.model.saveEncaissement(this.factory, this.selectedEncaissement);
			} else {
				this.model.updateEncaissement(this.factory, this.selectedEncaissement);
			}
			initializeControl();
		}
	}

	public void delete() {
		if (this.selectedEncaissement != null && this.selectedEncaissement.getId() > 0) {

			this.model.deleteEncaissement(this.factory, this.selectedEncaissement);
			initializeControl();
		}
	}

	public void initializeControl() {
		this.selectedCentre = null;
		this.selectedDev = null;
		this.selectedRecette = null;
		this.selectedTaxe = null;
		this.selectedEncaissement = null;
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
		this.idEnc = 0;
		this.montantTvac = 0.0D;
		this.montantHt = 0.0D;
	}
}
