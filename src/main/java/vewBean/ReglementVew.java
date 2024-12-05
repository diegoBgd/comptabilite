package vewBean;

import entite.BankAccount;
import entite.Banque;
import entite.Depense;
import entite.Devise;
import entite.Exercice;
import entite.Fournisseur;
import entite.Partenaire;
import entite.ReglementFournisseur;
import entite.Taxes;
import entite.TypeCharge;
import entite.TypeEcriture;
import entite.TypePartener;
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
import model.DepenseModel;
import model.DeviseModel;
import model.ExerciceModel;
import model.FournisseurModel;
import model.PartenerModel;
import model.ReglementModel;
import model.TaxeModel;
import model.TypeChargeModel;
import model.UserModel;
import org.hibernate.SessionFactory;
import org.primefaces.PrimeFaces;
import persistances.DBConfiguration;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class ReglementVew implements Serializable {
	private static final long serialVersionUID = 5092064261945886451L;
	private Depense selectedDepense;
	private ReglementFournisseur selectedReglement;
	private List<ReglementFournisseur> listReglement;
	private List<SelectItem> listTaxe;
	private List<SelectItem> listDevise;
	private List<SelectItem> listCaisse;
	private List<SelectItem> listCompte;
	private int idTaxe;
	private int idDevise;
	private int idCompte;
	private int idCaisse;
	private int modePmt;
	private Partenaire selectedFourn;
	private List<TypeCharge> listCharge;
	private TypeCharge selectedCharg;
	private String codeFrn;
	private String libelleFrn;
	private String printDate;
	private String rechLblFrn;
	private String commentaire;
	SessionFactory factory = DBConfiguration.getSessionFactory();
	private String rechDateDeb;
	private String rechDateFin;
	private String infoMsg;
	private String numeroDepense;
	private String pieceRegl;
	private String codeChrg;
	private String libelleChrg;
	private String rechCharge;
	private Date dateDebut;
	private Date dateFin;
	private Date dateOperation;
	private boolean disableMsg;
	private boolean disableSave;
	private List<Fournisseur> listFournisseur;
	private List<Depense> listDepense;
	private double montantDepense;
	private double totalRegle;
	private double courDevse;
	private double tauxTaxe;
	private double montantHt;
	private double montantTTC;
	private double reste;
	ReglementModel model;
	Exercice selecetdExercice;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;
	Devise selectedDev;
	Taxes selectedTaxe;
	BankAccount selectedCpt;
	Banque selectedCaisse;
	int idRegl = 0;

	TypeEcriture typeOper;

	public Depense getSelectedDepense() {
		return this.selectedDepense;
	}

	public void setSelectedDepense(Depense selectedDepense) {
		this.selectedDepense = selectedDepense;
	}

	public ReglementFournisseur getSelectedReglement() {
		return this.selectedReglement;
	}

	public void setSelectedReglement(ReglementFournisseur selectedReglement) {
		this.selectedReglement = selectedReglement;
	}

	public List<ReglementFournisseur> getListReglement() {
		return this.listReglement;
	}

	public void setListReglement(List<ReglementFournisseur> listReglement) {
		this.listReglement = listReglement;
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

	public Partenaire getSelectedFourn() {
		return selectedFourn;
	}

	public void setSelectedFourn(Partenaire selectedFourn) {
		this.selectedFourn = selectedFourn;
	}

	public String getCodeFrn() {
		return this.codeFrn;
	}

	public void setCodeFrn(String codeFrn) {
		this.codeFrn = codeFrn;
	}

	public String getLibelleFrn() {
		return this.libelleFrn;
	}

	public void setLibelleFrn(String libelleFrn) {
		this.libelleFrn = libelleFrn;
	}

	public String getPrintDate() {
		return this.printDate;
	}

	public void setPrintDate(String printDate) {
		this.printDate = printDate;
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

	public String getInfoMsg() {
		return this.infoMsg;
	}

	public void setInfoMsg(String infoMsg) {
		this.infoMsg = infoMsg;
	}

	public boolean isDisableMsg() {
		return this.disableMsg;
	}

	public void setDisableMsg(boolean disableMsg) {
		this.disableMsg = disableMsg;
	}

	public List<Fournisseur> getListFournisseur() {
		return this.listFournisseur;
	}

	public void setListFournisseur(List<Fournisseur> listFournisseur) {
		this.listFournisseur = listFournisseur;
	}

	public String getRechLblFrn() {
		return this.rechLblFrn;
	}

	public void setRechLblFrn(String rechLblFrn) {
		this.rechLblFrn = rechLblFrn;
	}

	public List<Depense> getListDepense() {
		return this.listDepense;
	}

	public void setListDepense(List<Depense> listDepense) {
		this.listDepense = listDepense;
	}

	public double getMontantDepense() {
		return this.montantDepense;
	}

	public void setMontantDepense(double montantDepense) {
		this.montantDepense = montantDepense;
	}

	public String getNumeroDepense() {
		return this.numeroDepense;
	}

	public void setNumeroDepense(String numeroDepense) {
		this.numeroDepense = numeroDepense;
	}

	public boolean isDisableSave() {
		return this.disableSave;
	}

	public void setDisableSave(boolean disableSave) {
		this.disableSave = disableSave;
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

	public double getTotalRegle() {
		return this.totalRegle;
	}

	public void setTotalRegle(double totalRegle) {
		this.totalRegle = totalRegle;
	}

	public Date getDateOperation() {
		return this.dateOperation;
	}

	public void setDateOperation(Date dateOperation) {
		this.dateOperation = dateOperation;
	}

	public double getCourDevse() {
		return this.courDevse;
	}

	public void setCourDevse(double courDevse) {
		this.courDevse = courDevse;
	}

	public double getTauxTaxe() {
		return this.tauxTaxe;
	}

	public void setTauxTaxe(double tauxTaxe) {
		this.tauxTaxe = tauxTaxe;
	}

	public double getMontantHt() {
		return this.montantHt;
	}

	public void setMontantHt(double montantHt) {
		this.montantHt = montantHt;
	}

	public double getMontantTTC() {
		return this.montantTTC;
	}

	public void setMontantTTC(double montantTTC) {
		this.montantTTC = montantTTC;
	}

	public String getCommentaire() {
		return this.commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public String getPieceRegl() {
		return this.pieceRegl;
	}

	public void setPieceRegl(String pieceRegl) {
		this.pieceRegl = pieceRegl;
	}

	public int getModePmt() {
		return this.modePmt;
	}

	public void setModePmt(int modePmt) {
		this.modePmt = modePmt;
	}

	public List<TypeCharge> getListCharge() {
		return this.listCharge;
	}

	public void setListCharge(List<TypeCharge> listCharge) {
		this.listCharge = listCharge;
	}

	public TypeCharge getSelectedCharg() {
		return this.selectedCharg;
	}

	public void setSelectedCharg(TypeCharge selectedCharg) {
		this.selectedCharg = selectedCharg;
	}

	public String getCodeChrg() {
		return this.codeChrg;
	}

	public void setCodeChrg(String codeChrg) {
		this.codeChrg = codeChrg;
	}

	public String getLibelleChrg() {
		return this.libelleChrg;
	}

	public void setLibelleChrg(String libelleChrg) {
		this.libelleChrg = libelleChrg;
	}

	public String getRechCharge() {
		return this.rechCharge;
	}

	public void setRechCharge(String rechCharge) {
		this.rechCharge = rechCharge;
	}

	public double getReste() {
		return reste;
	}

	public void setReste(double reste) {
		this.reste = reste;
	}

	@PostConstruct
	public void initialize() {
		this.disableMsg = true;
		this.printDate = HelperC.convertDate(new Date());
		this.model = new ReglementModel();
		this.courDevse = 1.0D;
		this.dateOperation = new Date();
		chargementSession();
		chargerTaxe();
		chargerDevise();
		chargerBanque();
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

				UIComponent frmRegFourn = null;
				FacesContext context = FacesContext.getCurrentInstance();
				frmRegFourn = context.getViewRoot().findComponent("frmRF");

				UIComponent frmAutreDep = null;
				frmAutreDep = context.getViewRoot().findComponent("frmOD");

				if (frmRegFourn != null) {
					this.typeOper = TypeEcriture.reglementFournisseur;
				}
				if (frmAutreDep != null) {
					this.typeOper = TypeEcriture.autreDepense;
				}
			}
		}
	}

	public void changeDate() {
		this.disableSave = false;
		if (this.printDate.replace("/", "").replace("_", "").trim().equals("")) {
			this.dateOperation = null;
		} else {

			this.dateOperation = HelperC.validerDate(this.printDate);
			if (this.dateOperation == null) {
				this.disableSave = true;
				HelperC.afficherAttention("Attention", "Date invalide !");
			} else {

				this.printDate = HelperC.convertDate(this.dateOperation);
				if (!HelperC.periodeValide(this.dateOperation, this.selecetdExercice.getDateDebut(),
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

		for (Devise dv : (new DeviseModel()).getListDevise(this.factory, ""))
			this.listDevise.add(new SelectItem(Integer.valueOf(dv.getId()), dv.getLibelle()));
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

				this.tauxTaxe = this.selectedTaxe.getTaux();
			} else {

				this.tauxTaxe = 0.0D;
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
		if (this.idDevise > 0)
			this.selectedDev = (new DeviseModel()).getDeviseById(this.idDevise, this.factory);
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

	public void searchFournisseur() {
		if (this.codeFrn != null && !this.codeFrn.equals("")) {

			this.selectedFourn = new PartenerModel().getPartenaireByCode(codeFrn, TypePartener.FOURNISSEUR, factory);
					if (this.selectedFourn != null) {
				getFrnValue();
			}
		}
	}

	public void chargerFournisseur() {
		this.listFournisseur = (new FournisseurModel()).getListFournisseur(this.factory, this.rechLblFrn);
	}

	private void getFrnValue() {
		this.codeFrn = this.selectedFourn.getCodePartener();
		this.libelleFrn = this.selectedFourn.getNomination();
	}

	public void getSelectedFrnValue() {
		if (this.selectedFourn != null) {

			getFrnValue();
			PrimeFaces.current().executeScript("PF('dlgFrn').hide();");
		}
	}

	public void chargerCharge() {
		this.listCharge = (new TypeChargeModel()).getListCharge(this.factory, this.rechCharge);
	}

	public void searchCharge() {
		if (this.codeChrg != null && !this.codeChrg.equals("")) {

			this.selectedCharg = (new TypeChargeModel()).getChargeByCode(this.factory, this.codeChrg);
			if (this.selectedCharg != null) {
				getChargeValue();
			}
		}
	}

	private void getChargeValue() {
		this.codeChrg = this.selectedCharg.getCodeChg();
		this.libelleChrg = this.selectedCharg.getLibelle();
	}

	public void getSelectedChrValue() {
		if (this.selectedCharg != null) {

			getChargeValue();
			PrimeFaces.current().executeScript("PF('dlgChg').hide();");
		}
	}

	public void calculerMontantTTC() {
		this.montantTTC = 0.0D;
		if (this.tauxTaxe > 0.0D) {

			this.montantTTC = this.montantHt * (1.0D + this.tauxTaxe / 100.0D);
		} else {

			this.montantTTC = this.montantHt;
		}
	}

	public void calculerMontantHT() {
		this.montantHt = 0.0D;
		if (this.tauxTaxe > 0.0D) {

			this.montantHt = this.montantTTC / (1.0D + this.tauxTaxe / 100.0D);
		} else {

			this.montantHt = this.montantTTC;
		}
	}

	public void chargerDepense() {
		this.listDepense = (new DepenseModel()).getListDepense(this.factory, this.selecetdExercice.getId(), null,
				this.dateDebut, this.dateFin);
	}

	public void getSelectedDepenseValue() {
		if (this.selectedDepense != null) {

			depenseValues();
			PrimeFaces.current().executeScript("PF('dlgDep').hide();");
		}
	}

	private void depenseValues() {
     this.selectedFourn = this.selectedDepense.getPartener();
     getFrnValue();
     this.numeroDepense = this.selectedDepense.getPiece();
     this.selectedDepense.calculMontantTTC();
     this.montantDepense = this.selectedDepense.getMontantTTC();
     
     chargerReglement();
     if (this.listReglement != null && this.listReglement.size() > 0) {
       
       this.totalRegle = 0.0D;
       for (ReglementFournisseur reg : this.listReglement) {
         
         reg.calculMontantTTC();
         this.totalRegle += reg.getMontantTTC();
       } 
       reste=montantDepense-totalRegle;
     } 
   }

	public void chargerReglement() {
		this.listReglement = this.model.getListReglement(this.factory, this.typeOper, this.selectedDepense,
				this.selecetdExercice.getId(), this.dateDebut, this.dateFin);
	}

	public void getSelectedReglValue() {
		if (this.selectedReglement != null) {

			reglementValue();
			PrimeFaces.current().executeScript("PF('dlgRegl').hide();");
		}
	}

	public void getSelectedAutreDepense() {
		if (this.selectedReglement != null) {

			reglementValue();
			PrimeFaces.current().executeScript("PF('dlgDep').hide();");
		}
	}

	private void reglementValue() {
		this.selectedDepense = this.selectedReglement.getDepense();
		this.selectedDev = this.selectedReglement.getDeviseRgl();
		this.selectedTaxe = this.selectedReglement.getTaxeRgl();
		this.selectedCaisse = this.selectedReglement.getCaisse();
		this.selectedCpt = this.selectedReglement.getAccount();
		this.selectedCharg = this.selectedReglement.getTypeDepense();
		this.commentaire = this.selectedReglement.getComment();
		this.courDevse = this.selectedReglement.getCoursRgl().doubleValue();
		this.dateOperation = this.selectedReglement.getDateReglement();
		this.printDate = HelperC.convertDate(this.selectedReglement.getDateReglement());
		this.selectedDev = this.selectedReglement.getDeviseRgl();
		this.idRegl = this.selectedReglement.getId();
		this.pieceRegl = this.selectedReglement.getPiece();

		this.montantHt = this.selectedReglement.getMontantRegle();
		this.modePmt = this.selectedReglement.getModeReglement();

		calculerMontantTTC();

		if (this.selectedDepense != null) {
			depenseValues();
		}

		if (this.selectedDev != null) {
			this.idDevise = this.selectedDev.getId();
		}
		if (this.selectedCharg != null) {
			getChargeValue();
		}

		if (this.selectedTaxe != null) {

			this.idTaxe = this.selectedTaxe.getId();
			taxeValue();
		}
		if (this.selectedCpt != null) {

			this.idCompte = this.selectedCpt.getId();
			compteValue();
		}
		if (this.selectedCaisse != null) {

			this.idCaisse = this.selectedCaisse.getId();
			caisseValue();
		}
		
		this.disableMsg = false;
	}

	public void save() {
		if (this.dateOperation != null) {
			if (testMontant()) {

				if (this.selectedReglement == null)
					this.selectedReglement = new ReglementFournisseur();
				this.selectedReglement.setId(this.idRegl);
				this.selectedReglement.setComment(this.commentaire);
				this.selectedReglement.setCoursRgl(Double.valueOf(this.courDevse));
				this.selectedReglement.setDateReglement(this.dateOperation);
				this.selectedReglement.setDepense(this.selectedDepense);
				this.selectedReglement.setDeviseRgl(this.selectedDev);
				this.selectedReglement.setIdExercise(this.selecetdExercice.getId());
				this.selectedReglement.setModeReglement(this.modePmt);
				this.selectedReglement.setMontantRegle(this.montantHt);
				this.selectedReglement.setPiece(this.pieceRegl);
				this.selectedReglement.setTaux(this.tauxTaxe);
				this.selectedReglement.setTaxeRgl(this.selectedTaxe);
				this.selectedReglement.setAccount(this.selectedCpt);
				this.selectedReglement.setCaisse(this.selectedCaisse);
				this.selectedReglement.setTypeOperation(this.typeOper);

				if (this.selectedReglement.getId() == 0) {
					this.model.saveReglement(this.factory, this.selectedReglement);
				} else {
					this.model.updateReglement(this.factory, this.selectedReglement);
				}
				initializeControl();
			} else {

				HelperC.afficherAttention("Attention",
						"La montant à  régler ne peut pas dépasser total de la facture!");
			}
		}
	}

	public void saveAutreDepense() {
		if (this.dateOperation != null) {

			if (this.selectedReglement == null)
				this.selectedReglement = new ReglementFournisseur();
			this.selectedReglement.setId(this.idRegl);
			this.selectedReglement.setComment(this.commentaire);
			this.selectedReglement.setCoursRgl(Double.valueOf(this.courDevse));
			this.selectedReglement.setDateReglement(this.dateOperation);
			this.selectedReglement.setDepense(this.selectedDepense);
			this.selectedReglement.setDeviseRgl(this.selectedDev);
			this.selectedReglement.setIdExercise(this.selecetdExercice.getId());
			this.selectedReglement.setModeReglement(this.modePmt);
			this.selectedReglement.setMontantRegle(this.montantHt);
			this.selectedReglement.setPiece(this.pieceRegl);
			this.selectedReglement.setTaux(this.tauxTaxe);
			this.selectedReglement.setTaxeRgl(this.selectedTaxe);
			this.selectedReglement.setAccount(this.selectedCpt);
			this.selectedReglement.setCaisse(this.selectedCaisse);
			this.selectedReglement.setTypeOperation(this.typeOper);
			this.selectedReglement.setTypeDepense(this.selectedCharg);

			if (this.selectedReglement.getId() == 0) {
				this.model.saveReglement(this.factory, this.selectedReglement);
			} else {
				this.model.updateReglement(this.factory, this.selectedReglement);
			}
			initializeControl();
		}
	}

	private boolean testMontant() {
		boolean bl = false;
		if (this.totalRegle + getMontantTTC() <= this.montantDepense)
			bl = true;
		return bl;
	}

	public void delete() {
		if (this.selectedReglement != null && this.selectedReglement.getId() > 0) {

			this.model.deleteReglement(this.factory, this.selectedReglement);
			initializeControl();
		}
	}

	public void initializeControl() {
		this.commentaire = "";
		this.courDevse = 1.0D;
		this.dateOperation = new Date();
		this.selectedDev = null;
		this.idDevise = 0;
		this.pieceRegl = "";
		this.selectedTaxe = null;
		this.montantTTC = 0.0D;
		this.tauxTaxe = 0.0D;
		montantHt=0.0;
		this.codeFrn = "";
		this.libelleFrn = "";
		this.idCaisse = 0;
		this.idCompte = 0;
		this.idDevise = 0;
		this.idTaxe = 0;
		this.selectedDepense = null;
		this.selectedDev = null;
		this.selectedFourn = null;
		this.selectedReglement = null;
		this.selectedTaxe = null;
		this.selectedCaisse = null;
		this.selectedCpt = null;
		this.disableMsg = true;
		this.infoMsg = "";
		this.printDate = "";
		this.montantDepense = 0.0D;
		this.numeroDepense = "";
		this.totalRegle = 0.0D;
		this.printDate = HelperC.convertDate(new Date());
	}
}
