package vewBean;

import entite.Compte;
import entite.Exercice;
import entite.Produit;
import entite.ProduitTaxe;
import entite.SoufamilleProd;
import entite.Taxes;
import entite.User;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import model.CompteModel;
import model.ExerciceModel;
import model.ProduitModel;
import model.SouFamilleModel;
import model.TaxeModel;
import model.UserModel;
import org.hibernate.SessionFactory;
import org.primefaces.PrimeFaces;
import persistances.DBConfiguration;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class ProduitVew implements Serializable {
	private static final long serialVersionUID = -7623693398592177188L;
	SessionFactory factory = DBConfiguration.getSessionFactory();

	private List<Produit> listProd;

	private Produit selectedProd;

	private SoufamilleProd sfamille;

	private boolean disableMsg;

	private List<SelectItem> listSfmille;

	private List<SelectItem> listTax;
	private List<ProduitTaxe> listTaxes;
	private int idSfmille;
	private int unitNumber;
	private int idTaxe;
	private BigDecimal tauxTaxe;
	private ProduitTaxe selectedTxe;
	private List<Compte> listCompte;
	private Compte cptStock;
	private Compte cptVente;
	private Compte cptSrt;
	private String lblCptStck;
	private String lblCptVnt;
	private String lblCptSrt;
	private String rechLblCpt;
	private String rechPrd;
	private String rechCodCpt;

	private String code;
	private String designation;
	private String barCode;
	private String compteStock;
	private String compteVente;
	private String compteSortie;
	private String mesureUnit;
	private boolean stockElement;
	private BigDecimal montantUnitaire;
	private BigDecimal montantHT;
	private BigDecimal montantTTC;
	private BigDecimal marge;
	private BigDecimal margePrcent;
	
	Long idPrd = (long) 0;
	Exercice selecetdExercice;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;
	ProduitModel model;
	Taxes txe;
	boolean selected;
	int index;

	public List<SelectItem> getListSfmille() {
		return this.listSfmille;
	}

	public void setListSfmille(List<SelectItem> listSfmille) {
		this.listSfmille = listSfmille;
	}

	public List<Produit> getListProd() {
		return this.listProd;
	}

	public void setListProd(List<Produit> listProd) {
		this.listProd = listProd;
	}

	public Produit getSelectedProd() {
		return this.selectedProd;
	}

	public void setSelectedProd(Produit selectedProd) {
		this.selectedProd = selectedProd;
	}

	public SoufamilleProd getSfamille() {
		return this.sfamille;
	}

	public void setSfamille(SoufamilleProd sfamille) {
		this.sfamille = sfamille;
	}

	public boolean isDisableMsg() {
		return this.disableMsg;
	}

	public void setDisableMsg(boolean disableMsg) {
		this.disableMsg = disableMsg;
	}

	public int getIdSfmille() {
		return this.idSfmille;
	}

	public void setIdSfmille(int idSfmille) {
		this.idSfmille = idSfmille;
	}

	public List<Compte> getListCompte() {
		return this.listCompte;
	}

	public void setListCompte(List<Compte> listCompte) {
		this.listCompte = listCompte;
	}

	public Compte getCptStock() {
		return this.cptStock;
	}

	public void setCptStock(Compte cptStock) {
		this.cptStock = cptStock;
	}

	public Compte getCptVente() {
		return this.cptVente;
	}

	public void setCptVente(Compte cptVente) {
		this.cptVente = cptVente;
	}

	public Compte getCptSrt() {
		return this.cptSrt;
	}

	public void setCptSrt(Compte cptSrt) {
		this.cptSrt = cptSrt;
	}

	public String getLblCptStck() {
		return this.lblCptStck;
	}

	public void setLblCptStck(String lblCptStck) {
		this.lblCptStck = lblCptStck;
	}

	public String getLblCptVnt() {
		return this.lblCptVnt;
	}

	public void setLblCptVnt(String lblCptVnt) {
		this.lblCptVnt = lblCptVnt;
	}

	public String getLblCptSrt() {
		return this.lblCptSrt;
	}

	public void setLblCptSrt(String lblCptSrt) {
		this.lblCptSrt = lblCptSrt;
	}

	public String getRechLblCpt() {
		return this.rechLblCpt;
	}

	public void setRechLblCpt(String rechLblCpt) {
		this.rechLblCpt = rechLblCpt;
	}

	public String getRechPrd() {
		return this.rechPrd;
	}

	public void setRechPrd(String rechPrd) {
		this.rechPrd = rechPrd;
	}

	public String getRechCodCpt() {
		return this.rechCodCpt;
	}

	public void setRechCodCpt(String rechCodCpt) {
		this.rechCodCpt = rechCodCpt;
	}

	public List<SelectItem> getListTax() {
		return this.listTax;
	}

	public void setListTax(List<SelectItem> listTax) {
		this.listTax = listTax;
	}

	public int getIdTaxe() {
		return this.idTaxe;
	}

	public void setIdTaxe(int idTaxe) {
		this.idTaxe = idTaxe;
	}

	public ProduitTaxe getSelectedTxe() {
		return this.selectedTxe;
	}

	public void setSelectedTxe(ProduitTaxe selectedTxe) {
		this.selectedTxe = selectedTxe;
	}

	public BigDecimal getTauxTaxe() {
		return this.tauxTaxe;
	}

	public void setTauxTaxe(BigDecimal tauxTaxe) {
		this.tauxTaxe = tauxTaxe;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesignation() {
		return this.designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getBarCode() {
		return this.barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getCompteStock() {
		return this.compteStock;
	}

	public void setCompteStock(String compteStock) {
		this.compteStock = compteStock;
	}

	public String getCompteVente() {
		return this.compteVente;
	}

	public void setCompteVente(String compteVente) {
		this.compteVente = compteVente;
	}

	public String getCompteSortie() {
		return this.compteSortie;
	}

	public void setCompteSortie(String compteSortie) {
		this.compteSortie = compteSortie;
	}

	public boolean isStockElement() {
		return this.stockElement;
	}

	public void setStockElement(boolean stockElement) {
		this.stockElement = stockElement;
	}

	public BigDecimal getMontantUnitaire() {
		return this.montantUnitaire;
	}

	public void setMontantUnitaire(BigDecimal montantUnitaire) {
		this.montantUnitaire = montantUnitaire;
	}

	public BigDecimal getMontantHT() {
		return this.montantHT;
	}

	public void setMontantHT(BigDecimal montantHT) {
		this.montantHT = montantHT;
	}

	public BigDecimal getMontantTTC() {
		return this.montantTTC;
	}

	public void setMontantTTC(BigDecimal montantTTC) {
		this.montantTTC = montantTTC;
	}

	public BigDecimal getMarge() {
		return this.marge;
	}

	public void setMarge(BigDecimal marge) {
		this.marge = marge;
	}

	public BigDecimal getMargePrcent() {
		return this.margePrcent;
	}

	public void setMargePrcent(BigDecimal margePrcent) {
		this.margePrcent = margePrcent;
	}

	public String getMesureUnit() {
		return this.mesureUnit;
	}

	public void setMesureUnit(String mesureUnit) {
		this.mesureUnit = mesureUnit;
	}

	public int getUnitNumber() {
		return this.unitNumber;
	}

	public void setUnitNumber(int unitNumber) {
		this.unitNumber = unitNumber;
	}

	public List<ProduitTaxe> getListTaxes() {
		return listTaxes;
	}

	public void setListTaxes(List<ProduitTaxe> listTaxes) {
		this.listTaxes = listTaxes;
	}

	@PostConstruct
	public void initialize() {
		this.model = new ProduitModel();
		this.disableMsg = true;
		selectedProd=new Produit();
		chargementSession();
		chargerSFamille();
		chargerTaxe();
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
				listTaxes = new ArrayList<ProduitTaxe>();
			}
		}
	}

	public void chargerProduit() {
		this.listProd = this.model.getListProduit(this.factory, this.rechPrd);
	}

	public void searchProduit() {
		if (this.code != null && !this.code.equals("")) {

			this.selectedProd = this.model.getProduitByCode(this.factory, this.code);
			if (this.selectedProd != null) {
				setProductValue();
			}
		}
	}

	private void setProductValue() {
		this.code = this.selectedProd.getCodePrd();
		this.barCode = this.selectedProd.getCodeBar();
		this.compteSortie = this.selectedProd.getCompteSortie();
		this.compteStock = this.selectedProd.getCompteStock();
		this.compteVente = this.selectedProd.getCompteVente();
		this.designation = this.selectedProd.getLibelle();
		this.stockElement = this.selectedProd.isGererStock();
		this.idPrd = this.selectedProd.getId();

		setMarge(this.selectedProd.getMarge());
		this.unitNumber = this.selectedProd.getNombreUnite();
		this.montantUnitaire = this.selectedProd.getPrixUnitaire();
		this.montantHT = this.selectedProd.getPvHt();
		this.montantTTC = this.selectedProd.getPvTTC();

		this.sfamille = this.selectedProd.getSfamille();
		if (this.sfamille != null)
			this.idSfmille = this.sfamille.getId();
		this.margePrcent = this.selectedProd.getTauxMarge();
		this.mesureUnit = this.selectedProd.getUnite();

		this.listTaxes = selectedProd.getListTaxes();

		this.disableMsg = false;
		getCptStkValues();
		getCptSrtValues();
		getCptVtValues();
	}

	public void getselectedProduct() {
		this.disableMsg = true;
		if (this.selectedProd != null) {

			setProductValue();
			PrimeFaces.current().executeScript("PF('dlgPrd').hide();");
		}
	}

	private void chargerSFamille() {
		this.listSfmille = new ArrayList<>();

		for (SoufamilleProd sf : (new SouFamilleModel()).getListSousFamille(this.factory, "")) {
			this.listSfmille.add(new SelectItem(Integer.valueOf(sf.getId()), sf.getLibelle()));
		}
	}

	public void searchCompteStock() {
		if (getCompteStock() != null && getCompteStock() != null) {

			this.cptStock = (new CompteModel()).getCompteByCode(this.factory, getCompteStock());
			getCptStkValues();
		}
	}

	public void getSelectedCptStkValue() {
		getCptStkValues();
		PrimeFaces.current().executeScript("PF('dlgCptStk').hide();");
	}

	private void getCptStkValues() {
		if (this.cptStock != null) {

			setCompteStock(this.cptStock.getCompteCod());
			this.lblCptStck = this.cptStock.getLibelle();
		}
	}

	private void chargerTaxe() {
		this.listTax = new ArrayList<>();
		this.listTax.add(new SelectItem(Integer.valueOf(0), ""));
		for (Taxes tx : (new TaxeModel()).getListTaxes(this.factory, "")) {
			this.listTax.add(new SelectItem(Integer.valueOf(tx.getId()), tx.getLibelle()+"("+tx.getTaux()+" %)"));
		}
	}

	public void changeTaxElement(ValueChangeEvent event) {
		if (event != null && event.getNewValue() != null) {
			this.idTaxe = ((Integer) event.getNewValue()).intValue();
			if (this.idTaxe > 0) {
				this.txe = (new TaxeModel()).getTaxeById(this.idTaxe, this.factory);
				this.tauxTaxe = this.txe.getTaux();
			}
		}
	}

	public void searchCompteSortie() {
		if (getCompteSortie() != null && getCompteSortie() != null) {

			this.cptSrt = (new CompteModel()).getCompteByCode(this.factory, getCompteSortie());
			if (this.cptSrt != null) {
				getCptSrtValues();
			}
		}
	}

	public void getSelectedCptSrtValue() {
		getCptSrtValues();
		PrimeFaces.current().executeScript("PF('dlgCptSrt').hide();");
	}

	private void getCptSrtValues() {
		if (this.cptSrt != null) {

			setCompteSortie(this.cptSrt.getCompteCod());
			this.lblCptSrt = this.cptSrt.getLibelle();
		}
	}

	public void searchCompteVente() {
		if (getCompteVente() != null && getCompteVente() != null) {

			this.cptVente = (new CompteModel()).getCompteByCode(this.factory, getCompteVente());
			if (this.cptVente != null) {
				getCptVtValues();
			}
		}
	}

	public void getSelecedCptVtValue() {
		getCptVtValues();
		PrimeFaces.current().executeScript("PF('dlgCptVt').hide();");
	}

	private void getCptVtValues() {
		if (this.cptVente != null) {

			setCompteVente(this.cptVente.getCompteCod());
			this.lblCptVnt = this.cptVente.getLibelle();
		}
	}

	public void chargerCompte() {
		this.listCompte = (new CompteModel()).getListCompte(this.factory, this.rechLblCpt, this.rechCodCpt);
	}

	public void changeSfmilElement(ValueChangeEvent event) {
		if (event != null && event.getNewValue() != null) {
			this.idSfmille = ((Integer) event.getNewValue()).intValue();
			if (this.idSfmille > 0) {
				this.sfamille = (new SouFamilleModel()).getSousFamilleById(this.idSfmille, this.factory);
			}
		}
	}

	public void addTaxe() {
		this.selectedTxe = new ProduitTaxe();
		this.selectedTxe.setTaux(this.tauxTaxe);
		this.selectedTxe.setTaxe(this.txe);
		this.selectedTxe.setProduit(selectedProd);
		listTaxes.add(selectedTxe);
		// selectedProd.addTaxe(selectedTxe);

		clearTaxe();
		calculMontant();
	}

	public void takeSelectedTaxe() {
		this.selected = true;
		this.tauxTaxe = this.selectedTxe.getTaux();
		this.idTaxe = this.selectedTxe.getTaxe().getId();
		this.txe = this.selectedTxe.getTaxe();
	}

	public void removeTaxe() {
		if (this.selectedTxe != null) {
			listTaxes.remove(selectedTxe);

			clearTaxe();
			calculMontant();
		}
	}

	private void clearTaxe() {
		this.index = 0;
		this.selected = false;
		this.tauxTaxe = BigDecimal.ZERO;
		this.idTaxe = 0;
		this.selectedTxe = null;
	}

	public void save() {
		if (this.code != null && !this.code.equals("")) {

			if (this.selectedProd == null) {
				this.selectedProd = new Produit();
			}

			this.selectedProd.setCodeBar(this.barCode);
			this.selectedProd.setCodePrd(this.code);
			this.selectedProd.setCompteSortie(this.compteSortie);
			this.selectedProd.setCompteStock(this.compteStock);
			this.selectedProd.setCompteVente(this.compteVente);
			this.selectedProd.setGererStock(this.stockElement);
			this.selectedProd.setId(this.idPrd);
			this.selectedProd.setLibelle(this.designation);
			this.selectedProd.setMarge(getMarge());
			this.selectedProd.setNombreUnite(this.unitNumber);
			this.selectedProd.setPrixUnitaire(this.montantUnitaire);
			this.selectedProd.setPvHt(this.montantHT);
			this.selectedProd.setPvTTC(this.montantTTC);
			this.selectedProd.setSfamille(this.sfamille);
			this.selectedProd.setTauxMarge(this.margePrcent);

			this.selectedProd.setUnite(this.mesureUnit);

			// Liaison des taxes au produit avant sauvegarde
			if (listTaxes != null && !listTaxes.isEmpty()) {
				for (ProduitTaxe pt : listTaxes) {
					pt.setProduit(this.selectedProd); // liaison obligatoire
				}
				this.selectedProd.setListTaxes(listTaxes);
			}

			// Persistance du produit
			if (this.selectedProd.getId() == 0) {

				this.model.saveProduit(this.factory, this.selectedProd);
				initializeControl();
			} else {

				this.model.updateProduit(this.factory, this.selectedProd);
				initializeControl();
			}
		}
	}

	public void calculMontant() {
	}

	public void delete() {
		if (this.selectedProd != null && this.selectedProd.getId() > 0) {

			this.model.deleteProduit(this.factory, this.selectedProd);
			initializeControl();
		}
	}

	public void initializeControl() {
		this.code = "";
		this.barCode = "";
		setCompteSortie("");
		setCompteStock("");
		setCompteVente("");
		this.designation = "";
		this.stockElement = false;
		this.idPrd = 0L;

		// --- Valeurs numériques remplacées par BigDecimal ---
		this.marge = BigDecimal.ZERO;
		this.montantHT = BigDecimal.ZERO;
		this.montantTTC = BigDecimal.ZERO;
		this.margePrcent = BigDecimal.ZERO;
		setTauxTaxe(BigDecimal.ZERO);

		// --- Autres champs ---
		this.unitNumber = 0;
		this.sfamille = null;
		this.mesureUnit = "";
		this.disableMsg = false;
		this.selectedProd = null;
	}

}
