package vewBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.primefaces.PrimeFaces;

import entite.Compte;
import entite.Exercice;
import entite.Partenaire;
import entite.TypeEcriture;
import entite.TypePartener;
import entite.User;
import model.CompteModel;
import model.ExerciceModel;
import model.PartenerModel;
import model.UserModel;
import persistances.DBConfiguration;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class PartenaireVew implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SessionFactory factory = DBConfiguration.getSessionFactory();

	private Partenaire selectedPertener;
	private List<Partenaire> listPartenaire;
	private List<Compte> listCpte;

	private boolean disableMsg;
	private String libelleCpt;
	private String rechLblCpt;
	private String rechCodCpt;
	private String compteCpbl;
	private String nif;
	private String tel;
	private String email;

	private String codePartenaire;
	private String nomPartenaire;
	private Compte selectedCpt;
	PartenerModel model;
	Exercice selecetdExercice;
	TypePartener type;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;
	int idPartener = 0;

	public PartenaireVew() {

	}

	public Partenaire getSelectedPertener() {
		return selectedPertener;
	}

	public void setSelectedPertener(Partenaire selectedPertener) {
		this.selectedPertener = selectedPertener;
	}

	public List<Partenaire> getListPartenaire() {
		return listPartenaire;
	}

	public void setListPartenaire(List<Partenaire> listPartenaire) {
		this.listPartenaire = listPartenaire;
	}

	public List<Compte> getListCpte() {
		return listCpte;
	}

	public void setListCpte(List<Compte> listCpte) {
		this.listCpte = listCpte;
	}

	public boolean isDisableMsg() {
		return disableMsg;
	}

	public void setDisableMsg(boolean disableMsg) {
		this.disableMsg = disableMsg;
	}

	public String getLibelleCpt() {
		return libelleCpt;
	}

	public void setLibelleCpt(String libelleCpt) {
		this.libelleCpt = libelleCpt;
	}

	public String getRechLblCpt() {
		return rechLblCpt;
	}

	public void setRechLblCpt(String rechLblCpt) {
		this.rechLblCpt = rechLblCpt;
	}

	public String getRechCodCpt() {
		return rechCodCpt;
	}

	public void setRechCodCpt(String rechCodCpt) {
		this.rechCodCpt = rechCodCpt;
	}

	public String getCompteCpbl() {
		return compteCpbl;
	}

	public void setCompteCpbl(String compteCpbl) {
		this.compteCpbl = compteCpbl;
	}

	public String getCodePartenaire() {
		return codePartenaire;
	}

	public void setCodePartenaire(String codePartenaire) {
		this.codePartenaire = codePartenaire;
	}

	public String getNomPartenaire() {
		return nomPartenaire;
	}

	public void setNomPartenaire(String nomPartenaire) {
		this.nomPartenaire = nomPartenaire;
	}

	public Compte getSelectedCpt() {
		return selectedCpt;
	}

	public void setSelectedCpt(Compte selectedCpt) {
		this.selectedCpt = selectedCpt;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@PostConstruct
	public void initialize() {
		this.model = new PartenerModel();
		this.disableMsg = true;
		chargementSession();
		chargerPartenaire();
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
				FacesContext context = FacesContext.getCurrentInstance();

				UIComponent frmClt = null;
				frmClt = context.getViewRoot().findComponent("frmClt");

				UIComponent frmFrn = null;
				frmFrn = context.getViewRoot().findComponent("frmFrn");

				if (frmClt != null) {
					type = TypePartener.CLIENT;
				}
				if (frmFrn != null) {
					type = TypePartener.FOURNISSEUR;
				}
				chargerPartenaire();
			}
		}
	}

	public void searchCompte() {
		if (this.compteCpbl != null && this.compteCpbl != null) {

			this.selectedCpt = (new CompteModel()).getCompteByCode(this.factory, this.compteCpbl);
			if (this.selectedCpt != null)
				getCptValues();
		}
	}

	public void getSelecedCptValue() {
		if (this.selectedCpt != null) {

			getCptValues();
			PrimeFaces.current().executeScript("PF('dlgCpt').hide();");
		}
	}

	private void getCptValues() {
		this.compteCpbl = this.selectedCpt.getCompteCod();
		this.libelleCpt = this.selectedCpt.getLibelle();
	}

	public void chargerCompte() {
		this.listCpte = (new CompteModel()).getListCompte(this.factory, this.rechLblCpt, this.rechCodCpt);
	}

	private void chargerPartenaire() {
		listPartenaire = this.model.getAllPartenaire(type, factory);
	}

	public void searchParteners() {
		this.disableMsg = true;
		if (codePartenaire != null && !codePartenaire.equals("")) {

			selectedPertener = this.model.getPartenaireByCode(codePartenaire, type, factory);
			if (selectedPertener != null) {
				setPartenerValues();
			}
		}
	}

	private void setPartenerValues() {
		idPartener = selectedPertener.getId();
		codePartenaire = selectedPertener.getCodePartener();
		nomPartenaire = selectedPertener.getNomination();
		compteCpbl = selectedPertener.getCodeCpbl();
		nif = selectedPertener.getNif();
		email = selectedPertener.getEmail();
		tel = selectedPertener.getTelephone();
		type = selectedPertener.getpType();
		searchCompte();
		this.disableMsg = false;
	}

	public void takeSelectedPartener() {
		if (selectedPertener != null) {
			setPartenerValues();
		}
	}

	public void save() {
		if (codePartenaire != null && !codePartenaire.equals("")) {

			if (selectedPertener == null)
				selectedPertener = new Partenaire();
			selectedPertener.setId(idPartener);
			selectedPertener.setCodeCpbl(compteCpbl);
			selectedPertener.setCodePartener(codePartenaire);
			selectedPertener.setEmail(email);
			selectedPertener.setNif(nif);
			selectedPertener.setNomination(nomPartenaire);
			selectedPertener.setpType(type);
			selectedPertener.setTelephone(tel);

			model.savePartener(factory, selectedPertener);
			chargerPartenaire();
		} else {

			HelperC.afficherAttention("Attention", "Il faut préciser la référence !");
		}
	}

	public void delete() {
		if (selectedPertener != null && selectedPertener.getId() > 0) {

			model.deletePartener(this.factory, selectedPertener);
			initializeControl();
			chargerPartenaire();
		}
	}

	public void initializeControl() {

		idPartener = 0;
		codePartenaire = "";
		nomPartenaire = "";
		compteCpbl = "";
		nif = "";
		email = "";
		tel = "";
		selectedCpt = null;
		compteCpbl = "";
		libelleCpt = "";
		selectedPertener = null;
		disableMsg = true;
	}
}
