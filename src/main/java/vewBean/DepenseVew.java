package vewBean;

import entite.BankAccount;
import entite.Banque;
import entite.CentreCout;
import entite.Compte;
import entite.Depense;
import entite.Devise;
import entite.Exercice;
import entite.Fournisseur;
import entite.Partenaire;
import entite.Taxes;
import entite.TypeCharge;
import entite.TypeEcriture;
import entite.TypePartener;
import entite.User;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
import model.DepenseModel;
import model.DeviseModel;
import model.ExerciceModel;
import model.FournisseurModel;
import model.PartenerModel;
import model.TaxeModel;
import model.TypeChargeModel;
import model.UserModel;
import org.hibernate.SessionFactory;
import org.primefaces.PrimeFaces;
import persistances.DBConfiguration;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class DepenseVew implements Serializable {
	private static final long serialVersionUID = 7331731613847598014L;
	private List<Depense> listDepense;
	private List<Partenaire> listFournisseur;
	private List<TypeCharge> listCharge;
	private List<SelectItem> listTaxe;
	private List<SelectItem> listDevise;
	private List<SelectItem> listCentre;
	private List<SelectItem> listCaisse;
	private List<SelectItem> listCompte;
	private Depense selectedDepense;
	private int idTaxe;
	private int idDevise;
	private int idCentre;
	private int modePmt;
	private int idCompte;
	private int idCaisse;
	private Partenaire selectedFourn;
	private TypeCharge selectedCharg;
	private boolean disableMsg;
	private String numeroBon;
	private String codeFrn;
	private String libelleFrn;
	private String printDate;
	private String rechLblChg;
	SessionFactory factory = DBConfiguration.getSessionFactory();
	private String infoMsg;
	private String printTot;
	private String printTotRgl;
	private String codeChrg;
	private String libelleChrg;
	private String rechLblFrn;
	private String rechDateDeb;
	private String rechDateFin;
	private String printSolde;
	private String comment;
	private String pieceCmpt;
	private Date dateDebut;
	private Date dateFin;
	private Date dateOp;
	private BigDecimal tauxDevise;
	private BigDecimal tauxTva;
	private BigDecimal montantHt;
	private BigDecimal montantTvac;
	DepenseModel model;
	Compte compteDb;
	Compte compteFn;
	Exercice selecetdExercice;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;
	Devise selectedDev;
	Taxes selectedTaxe;
	CentreCout selectedCentre;
	BankAccount selectedCpt;
	Banque selectedCaisse;
	int idDep = 0;

	TypeEcriture typeOper;

	public List<Depense> getListDepense() {
		return this.listDepense;
	}

	public void setListDepense(List<Depense> listDepense) {
		this.listDepense = listDepense;
	}

	public List<Partenaire> getListFournisseur() {
		return this.listFournisseur;
	}

	public void setListFournisseur(List<Partenaire> listFournisseur) {
		this.listFournisseur = listFournisseur;
	}

	public List<TypeCharge> getListCharge() {
		return this.listCharge;
	}

	public void setListCharge(List<TypeCharge> listCharge) {
		this.listCharge = listCharge;
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

	public Depense getSelectedDepense() {
		return this.selectedDepense;
	}

	public void setSelectedDepense(Depense selectedDepense) {
		this.selectedDepense = selectedDepense;
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

	public Partenaire getSelectedFourn() {
		return selectedFourn;
	}

	public void setSelectedFourn(Partenaire selectedFourn) {
		this.selectedFourn = selectedFourn;
	}

	public TypeCharge getSelectedCharg() {
		return this.selectedCharg;
	}

	public void setSelectedCharg(TypeCharge selectedCharg) {
		this.selectedCharg = selectedCharg;
	}

	public boolean isDisableMsg() {
		return this.disableMsg;
	}

	public void setDisableMsg(boolean disableMsg) {
		this.disableMsg = disableMsg;
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

	public String getPrintDate() {
		return this.printDate;
	}

	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	public String getRechLblFrn() {
		return this.rechLblFrn;
	}

	public void setRechLblFrn(String rechLblFrn) {
		this.rechLblFrn = rechLblFrn;
	}

	public String getRechLblChg() {
		return this.rechLblChg;
	}

	public void setRechLblChg(String rechLblChg) {
		this.rechLblChg = rechLblChg;
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

	public String getPrintTot() {
		return this.printTot;
	}

	public void setPrintTot(String printTot) {
		this.printTot = printTot;
	}

	public String getPrintTotRgl() {
		return this.printTotRgl;
	}

	public void setPrintTotRgl(String printTotRgl) {
		this.printTotRgl = printTotRgl;
	}

	public String getPrintSolde() {
		return this.printSolde;
	}

	public void setPrintSolde(String printSolde) {
		this.printSolde = printSolde;
	}

	public Date getDateOp() {
		return this.dateOp;
	}

	public void setDateOp(Date dateOp) {
		this.dateOp = dateOp;
	}

	public BigDecimal getTauxDevise() {
		return this.tauxDevise;
	}

	public void setTauxDevise(BigDecimal tauxDevise) {
		this.tauxDevise = tauxDevise;
	}

	public BigDecimal getTauxTva() {
		return this.tauxTva;
	}

	public void setTauxTva(BigDecimal tauxTva) {
		this.tauxTva = tauxTva;
	}

	public BigDecimal getMontantHt() {
		return this.montantHt;
	}

	public void setMontantHt(BigDecimal montantHt) {
		this.montantHt = montantHt;
	}

	public BigDecimal getMontantTvac() {
		return this.montantTvac;
	}

	public void setMontantTvac(BigDecimal montantTvac) {
		this.montantTvac = montantTvac;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPieceCmpt() {
		return this.pieceCmpt;
	}

	public void setPieceCmpt(String pieceCmpt) {
		this.pieceCmpt = pieceCmpt;
	}

	public String getNumeroBon() {
		return numeroBon;
	}

	public void setNumeroBon(String numeroBon) {
		this.numeroBon = numeroBon;
	}

	public int getModePmt() {
		return modePmt;
	}

	public void setModePmt(int modePmt) {
		this.modePmt = modePmt;
	}

	public List<SelectItem> getListCaisse() {
		return listCaisse;
	}

	public void setListCaisse(List<SelectItem> listCaisse) {
		this.listCaisse = listCaisse;
	}

	public List<SelectItem> getListCompte() {
		return listCompte;
	}

	public void setListCompte(List<SelectItem> listCompte) {
		this.listCompte = listCompte;
	}

	public int getIdCompte() {
		return idCompte;
	}

	public void setIdCompte(int idCompte) {
		this.idCompte = idCompte;
	}

	public int getIdCaisse() {
		return idCaisse;
	}

	public void setIdCaisse(int idCaisse) {
		this.idCaisse = idCaisse;
	}

	@PostConstruct
	public void initialize() {
		this.disableMsg = true;
		this.model = new DepenseModel();
		tauxDevise=BigDecimal.ONE;
		chargementSession();
		chargerBanque();
		chargerTaxe();
		chargerDevise();
		chargerCentre();
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

				UIComponent frmFactFourn = null;
				FacesContext context = FacesContext.getCurrentInstance();
				frmFactFourn = context.getViewRoot().findComponent("frmFF");

				UIComponent frmAutreDep = null;
				frmAutreDep = context.getViewRoot().findComponent("frmSF");

				if (frmFactFourn != null) {
					this.typeOper = TypeEcriture.factureFournisseur;
				}
				if (frmAutreDep != null) {
					this.typeOper = TypeEcriture.sortieFond;
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
	private void chargerCompte() {
		this.listCompte = new ArrayList<>();

		for (BankAccount cpt : (new BankAccountModel()).getListAccount(this.factory, this.selectedCaisse)) {
			this.listCompte.add(new SelectItem(Integer.valueOf(cpt.getId()), cpt.getAccCode()));
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

	public void chargerDepense() {
		this.listDepense = this.model.getListDepense(this.factory, this.selecetdExercice.getId(), typeOper , this.dateDebut,
				this.dateFin);
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
				if (!HelperC.periodeValide(this.dateOp, this.selecetdExercice.getDateDebut(),
						this.selecetdExercice.getDateFin())) {
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

				this.tauxTva = BigDecimal.ZERO;
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

	public void searchFournisseur() {
		if (this.codeFrn != null && !this.codeFrn.equals("")) {

			this.selectedFourn = new PartenerModel().getPartenaireByCode(codeFrn, TypePartener.FOURNISSEUR, factory);
			if (this.selectedFourn != null) {
				getFrnValue();
			}
		}
	}

	public void chargerFournisseur() {
		this.listFournisseur = new PartenerModel().getAllPartenaire(TypePartener.FOURNISSEUR, factory);
	}

	public void chargerCharge() {
		this.listCharge = (new TypeChargeModel()).getListCharge(this.factory, this.libelleChrg);
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

	public void getSelectedDepenseValue() {
		this.disableMsg = true;
		if (this.selectedDepense != null) {

			getDepenseValue();
			PrimeFaces.current().executeScript("PF('dlgDep').hide();");
		}
	}

	public void calculerMontantTTC() {
	    if (montantHt == null) montantHt = BigDecimal.ZERO;
	    if (tauxTva == null) tauxTva = BigDecimal.ZERO;

	    if (tauxTva.compareTo(BigDecimal.ZERO) > 0) {
	        BigDecimal facteur = tauxTva
	                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
	                .add(BigDecimal.ONE);
	        montantTvac = montantHt.multiply(facteur).setScale(2, RoundingMode.HALF_UP);
	    } else {
	        montantTvac = montantHt.setScale(2, RoundingMode.HALF_UP);
	    }
	}

	public void calculerMontantHT() {
	    if (montantTvac == null) montantTvac = BigDecimal.ZERO;
	    if (tauxTva == null) tauxTva = BigDecimal.ZERO;

	    if (tauxTva.compareTo(BigDecimal.ZERO) > 0) {
	        BigDecimal diviseur = tauxTva
	                .divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
	                .add(BigDecimal.ONE);
	        montantHt = montantTvac.divide(diviseur, 2, RoundingMode.HALF_UP);
	    } else {
	        montantHt = montantTvac.setScale(2, RoundingMode.HALF_UP);
	    }
	}

	private void getDepenseValue() {
		this.idDep = this.selectedDepense.getId();
		this.selectedCentre = this.selectedDepense.getCentre();
		this.selectedCharg = this.selectedDepense.getCharge();
		this.selectedDev = this.selectedDepense.getDevise();
		this.selectedFourn = this.selectedDepense.getPartener();
		this.selectedTaxe = this.selectedDepense.getTaxe();
		this.tauxDevise = this.selectedDepense.getCoursDev();
		this.dateOp = this.selectedDepense.getDateOperation();
		this.comment = this.selectedDepense.getLibelle();
		this.montantHt = this.selectedDepense.getMontant();
		this.tauxTva = this.selectedDepense.getTauxTaxe();
		this.numeroBon = this.selectedDepense.getPiece();
		this.printDate = HelperC.changeDateFormate(this.dateOp);
		if (this.selectedCentre != null) {

			this.idCentre = this.selectedCentre.getId();
			centreValue();
		}
		if (this.selectedDev != null) {

			this.idDevise = this.selectedDev.getId();
			deviseValue();
		}
		if (this.selectedTaxe != null) {

			this.idTaxe = this.selectedTaxe.getId();
			taxeValue();
		}
		if (this.selectedFourn != null) {
			getFrnValue();
		}
		if (this.selectedCharg != null) {
			getChargeValue();
		}
		this.disableMsg = false;
		calculerMontantTTC();
	}

	public void save() {
		if (this.dateOp != null) {

			if (this.selectedDepense == null)
				this.selectedDepense = new Depense();
			this.selectedDepense.setId(this.idDep);
			this.selectedDepense.setCentre(this.selectedCentre);
			this.selectedDepense.setCharge(this.selectedCharg);
			this.selectedDepense.setCoursDev(this.tauxDevise);
			this.selectedDepense.setDateOperation(this.dateOp);
			this.selectedDepense.setDevise(this.selectedDev);
			this.selectedDepense.setPartener(selectedFourn);
			this.selectedDepense.setIdExercise(this.selecetdExercice.getId());
			this.selectedDepense.setLibelle(this.comment);
			this.selectedDepense.setMontant(this.montantHt);
			this.selectedDepense.setPiece(this.numeroBon);
			this.selectedDepense.setTauxTaxe(this.tauxTva);
			this.selectedDepense.setTaxe(this.selectedTaxe);
			this.selectedDepense.setTypeOperation(this.typeOper);

			if (this.selectedDepense.getId() == 0) {
				this.model.saveDepense(this.factory, this.selectedDepense);
			} else {
				this.model.updateDepense(this.factory, this.selectedDepense);
			}
			initializeControl();
		} else {

			HelperC.afficherAttention("Attention", "Il faut préciser la date");
		}
	}

	public void delete() {
		if (this.selectedDepense != null && this.selectedDepense.getId() > 0) {

			this.model.deleteDepense(this.factory, this.selectedDepense);
			initializeControl();
		}
	}

	public void initializeControl() {
		this.idDep = 0;
		this.selectedCentre = null;
		this.selectedCharg = null;
		this.selectedDev = null;
		this.selectedFourn = null;
		this.selectedTaxe = null;
		this.tauxTva = BigDecimal.ZERO;
		this.dateOp = null;
		this.comment = "";
		this.montantTvac = BigDecimal.ZERO;
		this.tauxDevise = BigDecimal.ZERO;
		this.montantHt = BigDecimal.ZERO;
		this.pieceCmpt = "";
		this.codeChrg = "";
		this.codeFrn = "";
		this.libelleChrg = "";
		this.libelleFrn = "";
		this.idCentre = 0;
		this.idDevise = 0;
		this.idTaxe = 0;
		this.disableMsg = true;
		this.selectedDepense = null;
	}
}
