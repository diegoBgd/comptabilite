package vewBean;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;

import entite.Compte;
import entite.Exercice;
import entite.ParametreFinance;
import entite.User;
import model.CompteModel;
import model.ExerciceModel;
import model.ParametreFinanceModel;
import model.UserModel;
import persistances.DBConfiguration;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class ParametreFinanceVew implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1479981783471067995L;

	SessionFactory factory = DBConfiguration.getSessionFactory();

	private ParametreFinance selectedPrm;
	private boolean disableMsg;
	private String cpteChEnc, cptChEm, cptCash, cptVirInt;
	private String libelleCpteChEnc, libelleCptChEm, libelleCptCash, libelleCptVirInt;

	Exercice selecetdExercice;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;
	ParametreFinanceModel model;

	int idParm;

	public ParametreFinanceVew() {

	}

	public ParametreFinance getSelectedPrm() {
		return selectedPrm;
	}

	public void setSelectedPrm(ParametreFinance selectedPrm) {
		this.selectedPrm = selectedPrm;
	}

	public boolean isDisableMsg() {
		return disableMsg;
	}

	public void setDisableMsg(boolean disableMsg) {
		this.disableMsg = disableMsg;
	}

	public String getCpteChEnc() {
		return cpteChEnc;
	}

	public void setCpteChEnc(String cpteChEnc) {
		this.cpteChEnc = cpteChEnc;
	}

	public String getCptChEm() {
		return cptChEm;
	}

	public void setCptChEm(String cptChEm) {
		this.cptChEm = cptChEm;
	}

	public String getCptCash() {
		return cptCash;
	}

	public void setCptCash(String cptCash) {
		this.cptCash = cptCash;
	}

	public String getCptVirInt() {
		return cptVirInt;
	}

	public void setCptVirInt(String cptVirInt) {
		this.cptVirInt = cptVirInt;
	}

	public String getLibelleCpteChEnc() {
		return libelleCpteChEnc;
	}

	public void setLibelleCpteChEnc(String libelleCpteChEnc) {
		this.libelleCpteChEnc = libelleCpteChEnc;
	}

	public String getLibelleCptChEm() {
		return libelleCptChEm;
	}

	public void setLibelleCptChEm(String libelleCptChEm) {
		this.libelleCptChEm = libelleCptChEm;
	}

	public String getLibelleCptCash() {
		return libelleCptCash;
	}

	public void setLibelleCptCash(String libelleCptCash) {
		this.libelleCptCash = libelleCptCash;
	}

	public String getLibelleCptVirInt() {
		return libelleCptVirInt;
	}

	public void setLibelleCptVirInt(String libelleCptVirInt) {
		this.libelleCptVirInt = libelleCptVirInt;
	}

	@PostConstruct
	public void initialize() {
		this.disableMsg = true;
		this.model = new ParametreFinanceModel();
		chargementSession();

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
				chargement();
			}
		}
	}

	private void chargement() {
		disableMsg=false;
		selectedPrm = model.getParameter(factory);
		if (selectedPrm != null) {
			idParm = selectedPrm.getIdParam();
			cptCash = selectedPrm.getCompteCash();
			cptChEm = selectedPrm.getCompteChqEm();
			cpteChEnc = selectedPrm.getCompteChqEnc();
			cptVirInt = selectedPrm.getCompteVirInt();
			disableMsg=true;
			searchCpteCash();
			searchCpteChqEnc();
			searchCpteChqEm();
			searchCpteChqVrm();
		}
	}

	private String searchLibelleCompte(String cpt) {
		String libelle = "";
		Compte cpte = null;
		if (cpt != null && !cpt.equals("")) {
			cpte = new CompteModel().getCompteByCode(factory, cpt);
			if (cpte != null)
				libelle = cpte.getLibelle();
		}
		return libelle;
	}

	public void searchCpteCash() {
		libelleCptCash = searchLibelleCompte(cptCash);
	}

	public void searchCpteChqEnc() {

		libelleCpteChEnc = searchLibelleCompte(cpteChEnc);
	}

	public void searchCpteChqEm() {
		libelleCptChEm = searchLibelleCompte(cptChEm);
	}

	public void searchCpteChqVrm() {
		libelleCptVirInt = searchLibelleCompte(cptVirInt);
	}

	public void save() {
		if (selectedPrm == null)
			selectedPrm = new ParametreFinance();
		selectedPrm.setIdParam(idParm);
		selectedPrm.setCompteCash(cptCash);
		selectedPrm.setCompteChqEm(cptChEm);
		selectedPrm.setCompteChqEnc(cpteChEnc);
		selectedPrm.setCompteVirInt(cptVirInt);
		try {
			model.saveParametreFinance(factory, selectedPrm);
			HelperC.afficherMessage("Succès", "Enregisrement effectué");
			chargement();
		} catch (Exception e) {
			HelperC.afficherErreur("Erreur", "Echec d'enregistrement ");
		}
	}

	public void delete() {
		try {
			if (selectedPrm != null && selectedPrm.getIdParam()>0) {
				model.deleteParametreFinance(factory, selectedPrm);
				HelperC.afficherMessage("Succès", "Suppression effectuée");
				clear();
			}
		} catch (Exception e) {
			HelperC.afficherErreur("Erreur", "Echec de suppression ");
		}
	}

	private void clear() {
		idParm = 0;
		cptCash = "";
		cptChEm = "";
		cpteChEnc = "";
		cptVirInt = "";
		libelleCptCash = "";
		libelleCptChEm = "";
		libelleCpteChEnc = "";
		libelleCptVirInt = "";
		disableMsg=false;
	}
}
