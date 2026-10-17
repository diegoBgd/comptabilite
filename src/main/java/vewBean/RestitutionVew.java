package vewBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;

import dto.ColonneDTO;
import dto.LigneEtatDTO;

import entite.EtatFinanciers;
import entite.EtatFinanciersStyleLigne;
import entite.Exercice;
import entite.User;
import enumeration.TypeLigne;
import model.EtatFinanciersModel;
import model.ExerciceModel;
import model.UserModel;
import persistances.DBConfiguration;
import services.MoteurCalculService;
import services.StyleService;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class RestitutionVew implements Serializable {

	private static final long serialVersionUID = 4359829324093820852L;

	SessionFactory factory = DBConfiguration.getSessionFactory();

	// ============================================================
	// SÉLECTION
	// ============================================================
	private List<EtatFinanciers> listEtat;
	private EtatFinanciers selectedEtat;
	private Long idEtat = 0L;

	private List<Exercice> listeExercices;
	private Integer exerciceSelectionne;
	private List<ColonneDTO> colonnes;
	// ============================================================
	// RÉSULTAT
	// ============================================================
	private List<LigneEtatDTO> lignes;

	// ============================================================
	// ÉTAT INTERFACE
	// ============================================================
	private boolean disableMsg;
	private boolean afficherN1 = true;
	private boolean afficherVariation = false;

	// ============================================================
	// SESSION
	// ============================================================
	Exercice selectedExercice;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;

	// ============================================================
	// SERVICES / MODELS
	// ============================================================
	EtatFinanciersModel model;
	MoteurCalculService moteurService;
	StyleService styleService;

	// ============================================================
	// GETTERS / SETTERS
	// ============================================================
	public List<EtatFinanciers> getListEtat() {
		return listEtat;
	}

	public void setListEtat(List<EtatFinanciers> listEtat) {
		this.listEtat = listEtat;
	}

	public EtatFinanciers getSelectedEtat() {
		return selectedEtat;
	}

	public void setSelectedEtat(EtatFinanciers selectedEtat) {
		this.selectedEtat = selectedEtat;
	}

	public Long getIdEtat() {
		return idEtat;
	}

	public void setIdEtat(Long idEtat) {
		this.idEtat = idEtat;
	}

	public Integer getExerciceSelectionne() {
		return exerciceSelectionne;
	}

	public void setExerciceSelectionne(Integer exerciceSelectionne) {
		this.exerciceSelectionne = exerciceSelectionne;
	}

	public List<LigneEtatDTO> getLignes() {
		return lignes;
	}

	public void setLignes(List<LigneEtatDTO> lignes) {
		this.lignes = lignes;
	}

	public boolean isDisableMsg() {
		return disableMsg;
	}

	public void setDisableMsg(boolean disableMsg) {
		this.disableMsg = disableMsg;
	}

	public boolean isAfficherN1() {
		return afficherN1;
	}

	public void setAfficherN1(boolean afficherN1) {
		this.afficherN1 = afficherN1;
	}

	public boolean isAfficherVariation() {
		return afficherVariation;
	}

	public void setAfficherVariation(boolean afficherVariation) {
		this.afficherVariation = afficherVariation;
	}

	public List<ColonneDTO> getColonnes() {
		return colonnes;
	}

	public void setColonnes(List<ColonneDTO> colonnes) {
		this.colonnes = colonnes;
	}

	public List<Exercice> getListeExercices() {
		return listeExercices;
	}

	public void setListeExercices(List<Exercice> listeExercices) {
		this.listeExercices = listeExercices;
	}

	// ============================================================
	// INITIALISATION
	// ============================================================
	@PostConstruct
	public void initilaze() {
		this.model = new EtatFinanciersModel();
		this.moteurService = new MoteurCalculService(factory);
		this.styleService = new StyleService(factory);

		this.disableMsg = true;
		this.lignes = new ArrayList<LigneEtatDTO>();

		chargementSession();
		chargerEtatFinancier();
		chargerExercices();

		// Présélection
		if (this.listEtat != null && !this.listEtat.isEmpty()) {
			this.selectedEtat = this.listEtat.get(0);
			this.idEtat = this.selectedEtat.getIdEtat();
		}
	

		// Calcul initial
		calculer();
	}

	private void chargementSession() {
		this.session = HelperC.getSession();
		if (this.session != null) {
			this.exerCode = (String) this.session.getAttribute("exercice");
			this.currUserCode = (String) this.session.getAttribute("operateur");

			if (this.exerCode != null) {
				this.selectedExercice = (new ExerciceModel()).getExercByCode(this.factory, this.exerCode);
			}
			if (this.currUserCode != null) {
				this.currentUser = (new UserModel()).getUserByCode(this.factory, this.currUserCode);
			}

			if (this.currentUser == null || this.selectedExercice == null) {
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("/comptabilite/login.xhtml");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void chargerEtatFinancier() {
		this.listEtat = this.model.getAllEtatFinanciersActifs(this.factory);
		if (this.listEtat == null)
			this.listEtat = new ArrayList<EtatFinanciers>();
	}

	private void chargerExercices() {
		listeExercices=new ExerciceModel().getListExercice(factory);
	}

	// ============================================================
	// SÉLECTION
	// ============================================================
	public void getSelectedEFiValue() {
		if (this.selectedEtat != null) {
			this.idEtat = this.selectedEtat.getIdEtat();
			calculer();
		}
	}

	// ============================================================
	// CALCUL
	// ============================================================
	public void calculer() {
		try {
			if (this.idEtat == null || this.idEtat == 0L) {
				this.lignes = new ArrayList<LigneEtatDTO>();
				return;
			}
			

			// 1. Calculer les lignes via le moteur
			this.lignes = this.moteurService.calculerEtat(this.idEtat, selectedExercice.getId());

			if (this.lignes == null)
				this.lignes = new ArrayList<LigneEtatDTO>();

			// 2. Appliquer les styles
			for (LigneEtatDTO l : this.lignes) {
				EtatFinanciersStyleLigne style = this.styleService.getStyle(l.getTypeLigne().getLibelle());
				l.setStyle(style);
			}
			construireColonnes();

			HelperC.afficherInformation("Calcul effectué",
					this.lignes.size() + " ligne(s) pour l'exercice " + this.exerciceSelectionne);

		} catch (Exception e) {
			HelperC.afficherAttention("Erreur de calcul", e.getMessage());
			e.printStackTrace();
		}
	}
	public void recalculerColonnes() {
	    construireColonnes();
	}
	/**
	 * Construit la liste des colonnes selon l'état et les options cochées.
	 */
	private void construireColonnes() {
		colonnes = new ArrayList<ColonneDTO>();

		if (selectedEtat == null)
			return;

		boolean afficheAmort = false;

		// 1. Détecter si l'état contient des rubriques avec affichage amortissements
		if (lignes != null) {
			for (LigneEtatDTO l : lignes) {
				if (l.isAfficheAmortissements()) {
					afficheAmort = true;
					break;
				}
			}
		}

		// 2. Si l'état est un Bilan AVEC immobilisations → 4 colonnes
		if (afficheAmort) {
			colonnes.add(new ColonneDTO("BRUT_N", "Brut N", "BRUT", 12));
			colonnes.add(new ColonneDTO("AMORT_N", "Amort. N", "AMORT", 12));
			colonnes.add(new ColonneDTO("NET_N", "Net N", "NET_N", 13));
			if (afficherN1) {
				colonnes.add(new ColonneDTO("NET_N1", "Net N-1", "NET_N1", 13));
			}
			if (afficherVariation) {
				colonnes.add(new ColonneDTO("VARIATION", "Variation", "VARIATION", 12));
			}
		}
		// 3. Sinon (TFT, CR, Bilan sans immo) → 2 colonnes
		else {
			colonnes.add(new ColonneDTO("NET_N", "Exercice N", "NET_N", 25));
			if (afficherN1) {
				colonnes.add(new ColonneDTO("NET_N1", "Exercice N-1", "NET_N1", 25));
			}
			if (afficherVariation) {
				colonnes.add(new ColonneDTO("VARIATION", "Variation", "VARIATION", 20));
			}
		}
	}

	// ============================================================
	// UTILITAIRES D'AFFICHAGE
	// ============================================================
	public String getCssFor(LigneEtatDTO ligne) {
		if (ligne == null || ligne.getStyle() == null)
			return "";
		return this.styleService.toCss(ligne.getStyle());
	}

	/** Indique si la ligne doit afficher un montant */
	public boolean isLigneMontant(LigneEtatDTO l) {
		if (l == null || l.getTypeLigne() == null)
			return false;
		return l.getTypeLigne() != TypeLigne.TIT && l.getTypeLigne() != TypeLigne.SEP
				&& l.getTypeLigne() != TypeLigne.ST;
	}

}