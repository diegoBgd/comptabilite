package vewBean;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import entite.EtatFinanciers;
import entite.Exercice;
import entite.User;
import enumeration.ModeCalcul;
import enumeration.TypeEtat;
import model.EtatFinanciersModel;
import model.ExerciceModel;
import model.UserModel;
import persistances.DBConfiguration;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class EtatFinanciersVew implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 4359829324093820848L;

	SessionFactory factory = DBConfiguration.getSessionFactory();

	private EtatFinanciers selectedEtat;
	private List<EtatFinanciers> listEtat;
	private List<TypeEtat> listeTypesEtat;
	private List<ModeCalcul> listeModeCalcul;
    private TypeEtat typeEtatSelectionne;
    private ModeCalcul modeCalculSelectionne;
	private boolean disableMsg;
	private String code;
	private String designation;
	private int type;
	EtatFinanciersModel model;
	Exercice selecetdExercice;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;
	Long idEtat = 0L;

	public EtatFinanciers getSelectedEtat() {
		return selectedEtat;
	}

	public void setSelectedEtat(EtatFinanciers selectedEtat) {
		this.selectedEtat = selectedEtat;
	}

	public List<EtatFinanciers> getListEtat() {
		return listEtat;
	}

	public void setListEtat(List<EtatFinanciers> listEtat) {
		this.listEtat = listEtat;
	}

	public boolean isDisableMsg() {
		return disableMsg;
	}

	public void setDisableMsg(boolean disableMsg) {
		this.disableMsg = disableMsg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<TypeEtat> getListeTypesEtat() {
		return listeTypesEtat;
	}

	public void setListeTypesEtat(List<TypeEtat> listeTypesEtat) {
		this.listeTypesEtat = listeTypesEtat;
	}

	public TypeEtat getTypeEtatSelectionne() {
		return typeEtatSelectionne;
	}

	public void setTypeEtatSelectionne(TypeEtat typeEtatSelectionne) {
		this.typeEtatSelectionne = typeEtatSelectionne;
	}

	public List<ModeCalcul> getListeModeCalcul() {
		return listeModeCalcul;
	}

	public void setListeModeCalcul(List<ModeCalcul> listeModeCalcul) {
		this.listeModeCalcul = listeModeCalcul;
	}

	public ModeCalcul getModeCalculSelectionne() {
		return modeCalculSelectionne;
	}

	public void setModeCalculSelectionne(ModeCalcul modeCalculSelectionne) {
		this.modeCalculSelectionne = modeCalculSelectionne;
	}

	@PostConstruct
	public void initilaze() {
		this.model = new EtatFinanciersModel();
		this.disableMsg = true;
		chargementSession();
		chargerEtatFinancier();
		listeTypesEtat = Arrays.asList(TypeEtat.values());
		listeModeCalcul=Arrays.asList(ModeCalcul.values());
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

	private void chargerEtatFinancier() {
		this.listEtat = this.model.getAllEtatFinanciers(this.factory);
	}

	public void searchEtatFinancier() {
		this.disableMsg = true;

		if (this.code != null && !this.code.equals("")) {

			this.selectedEtat = this.model.getEtatFinanciersByCode(this.code, this.factory);
			if (this.selectedEtat != null) {
				getEFivalues();
			}
		}
	}
	public void getSelectedEFiValue() {
	     if (this.selectedEtat != null) {
	       getEFivalues();
	     }
	   }
	private void getEFivalues() {
		this.code = this.selectedEtat.getCodeEtat();
		this.idEtat = this.selectedEtat.getIdEtat();
		this.designation = this.selectedEtat.getLibelleEtat();
		this.typeEtatSelectionne=selectedEtat.getTypeEtat();
		this.modeCalculSelectionne=selectedEtat.getModeCalcul();
		this.disableMsg = false;
	}

	public void save() {
		if (code == null || code.equals("")) {
			HelperC.afficherAttention("Attention", "Il faut préciser la code !");
			return;
		}
		if (designation == null || designation.equals("")) {
			HelperC.afficherAttention("Attention", "Il faut préciser le nom de l'état !");
			return;
		}
		if (this.selectedEtat == null)
			this.selectedEtat = new EtatFinanciers();
		
		this.selectedEtat.setCodeEtat(code);
		this.selectedEtat.setIdEtat(idEtat);
		this.selectedEtat.setLibelleEtat(designation);
		this.selectedEtat.setTypeEtat(typeEtatSelectionne);
		this.selectedEtat.setModeCalcul(modeCalculSelectionne);
		this.model.saveEtatFinanciers(factory, selectedEtat);

		chargerEtatFinancier();
		initializeControl();
	}
	 public void delete() {
	     if (this.selectedEtat != null && this.selectedEtat.getIdEtat() > 0) {
	       
	       this.model.deleteEtatFinanciers(factory, selectedEtat);
	       initializeControl();
	       chargerEtatFinancier();
	     } 
	   }
	public void initializeControl() {
	     this.idEtat = 0L;
	     this.code = "";
	     this.designation = "";
	     this.disableMsg = true;
	     this.selectedEtat = null;
	     typeEtatSelectionne=null;
	     modeCalculSelectionne=null;
	   }
}