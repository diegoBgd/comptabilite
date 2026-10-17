package vewBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;


import entite.EtatFinanciers;
import entite.EtatFinanciersRubrique;
import entite.Exercice;
import entite.User;
import enumeration.TypeCalcul;
import enumeration.TypeLigne;
import enumeration.TypeValeur;
import model.EtatFinanciersModel;
import model.EtatFinanciersRubriqueModel;
import model.ExerciceModel;
import model.UserModel;
import persistances.DBConfiguration;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class EtatFinanciersRubriqueVew implements Serializable {

	private static final long serialVersionUID = 4359829324093820849L;

	SessionFactory factory = DBConfiguration.getSessionFactory();

	// ---- Sélection ----
	private EtatFinanciers selectedEtat;
	private List<EtatFinanciers> listEtat;

	private EtatFinanciersRubrique selectedRubrique;
	private List<EtatFinanciersRubrique> listRubrique;
	private List<EtatFinanciersRubrique> listRubriqueParente;

	// ---- Énumérations ----
	private List<TypeLigne> listeTypesLigne;
	private List<TypeCalcul> listeTypesCalcul;
	private List<TypeValeur> listeTypesValeur;

	private TypeLigne typeLigneSelectionne;
	private TypeCalcul typeCalculSelectionne;
	private TypeValeur typeValeurSelectionne;

	// ---- Champs d'édition ----
	private boolean disableMsg;
	private Long idRubriqueParent = 0L;

	private Long idEtat = 0L;
	private String code;
	private String designation;
	private String section;
	private Integer niveau;
	private String formule;
	private Integer ordreAffichage;
	private Integer signe;
	private boolean afficheAmortissements;
	private boolean visible;
	private boolean imprimable;
	private EtatFinanciersRubrique rubriqueParenteSelectionnee;

	 Long idRubrique = 0L;
	 
	// ---- Session ----
	Exercice selectedExercice;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;

	// ---- Models ----
	EtatFinanciersModel model;
	EtatFinanciersRubriqueModel rubriqueModel;

	// ============================================================
		// GETTERS / SETTERS
		// ============================================================
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

		public EtatFinanciersRubrique getSelectedRubrique() {
			return selectedRubrique;
		}

		public void setSelectedRubrique(EtatFinanciersRubrique selectedRubrique) {
			this.selectedRubrique = selectedRubrique;
		}

		public List<EtatFinanciersRubrique> getListRubrique() {
			return listRubrique;
		}

		public void setListRubrique(List<EtatFinanciersRubrique> listRubrique) {
			this.listRubrique = listRubrique;
		}

		public List<EtatFinanciersRubrique> getListRubriqueParente() {
			return listRubriqueParente;
		}

		public void setListRubriqueParente(List<EtatFinanciersRubrique> listRubriqueParente) {
			this.listRubriqueParente = listRubriqueParente;
		}

		public List<TypeLigne> getListeTypesLigne() {
			return listeTypesLigne;
		}

		public void setListeTypesLigne(List<TypeLigne> listeTypesLigne) {
			this.listeTypesLigne = listeTypesLigne;
		}

		public List<TypeCalcul> getListeTypesCalcul() {
			return listeTypesCalcul;
		}

		public void setListeTypesCalcul(List<TypeCalcul> listeTypesCalcul) {
			this.listeTypesCalcul = listeTypesCalcul;
		}

		public List<TypeValeur> getListeTypesValeur() {
			return listeTypesValeur;
		}

		public void setListeTypesValeur(List<TypeValeur> listeTypesValeur) {
			this.listeTypesValeur = listeTypesValeur;
		}

		public TypeLigne getTypeLigneSelectionne() {
			return typeLigneSelectionne;
		}

		public void setTypeLigneSelectionne(TypeLigne typeLigneSelectionne) {
			this.typeLigneSelectionne = typeLigneSelectionne;
		}

		public TypeCalcul getTypeCalculSelectionne() {
			return typeCalculSelectionne;
		}

		public void setTypeCalculSelectionne(TypeCalcul typeCalculSelectionne) {
			this.typeCalculSelectionne = typeCalculSelectionne;
		}

		public TypeValeur getTypeValeurSelectionne() {
			return typeValeurSelectionne;
		}

		public void setTypeValeurSelectionne(TypeValeur typeValeurSelectionne) {
			this.typeValeurSelectionne = typeValeurSelectionne;
		}

		public boolean isDisableMsg() {
			return disableMsg;
		}

		public void setDisableMsg(boolean disableMsg) {
			this.disableMsg = disableMsg;
		}

		
		public Long getIdEtat() {
			return idEtat;
		}

		public void setIdEtat(Long idEtat) {
			this.idEtat = idEtat;
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

		public String getSection() {
			return section;
		}

		public void setSection(String section) {
			this.section = section;
		}

		public Integer getNiveau() {
			return niveau;
		}

		public void setNiveau(Integer niveau) {
			this.niveau = niveau;
		}

		public String getFormule() {
			return formule;
		}

		public void setFormule(String formule) {
			this.formule = formule;
		}

		public Integer getOrdreAffichage() {
			return ordreAffichage;
		}

		public void setOrdreAffichage(Integer ordreAffichage) {
			this.ordreAffichage = ordreAffichage;
		}

		public Integer getSigne() {
			return signe;
		}

		public void setSigne(Integer signe) {
			this.signe = signe;
		}

		public boolean isAfficheAmortissements() {
			return afficheAmortissements;
		}

		public void setAfficheAmortissements(boolean afficheAmortissements) {
			this.afficheAmortissements = afficheAmortissements;
		}

		public boolean isVisible() {
			return visible;
		}

		public void setVisible(boolean visible) {
			this.visible = visible;
		}

		public boolean isImprimable() {
			return imprimable;
		}

		public void setImprimable(boolean imprimable) {
			this.imprimable = imprimable;
		}

		public EtatFinanciersRubrique getRubriqueParenteSelectionnee() {
			return rubriqueParenteSelectionnee;
		}

		public void setRubriqueParenteSelectionnee(EtatFinanciersRubrique rubriqueParenteSelectionnee) {
			this.rubriqueParenteSelectionnee = rubriqueParenteSelectionnee;
		}
		
	public Long getIdRubriqueParent() {
			return idRubriqueParent;
		}

		public void setIdRubriqueParent(Long idRubriqueParent) {
			this.idRubriqueParent = idRubriqueParent;
		}

	// ============================================================
	// INITIALISATION
	// ============================================================
	@PostConstruct
	public void initilaze() {
		this.model = new EtatFinanciersModel();
		this.rubriqueModel = new EtatFinanciersRubriqueModel();
		this.disableMsg = true;
		chargementSession();
		chargerEtatFinancier();

		listeTypesLigne = Arrays.asList(TypeLigne.values());
		listeTypesCalcul = Arrays.asList(TypeCalcul.values());
		listeTypesValeur = Arrays.asList(TypeValeur.values());

		// Présélection du premier état
		if (this.listEtat != null && !this.listEtat.isEmpty()) {
			this.selectedEtat = this.listEtat.get(0);
			this.idEtat = this.selectedEtat.getIdEtat();
			chargerRubrique();
		}
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
		this.listEtat = this.model.getAllEtatFinanciers(this.factory);
	}

	private void chargerRubrique() {
		if (this.idEtat == null || this.idEtat == 0L) {
			this.listRubrique = new ArrayList<EtatFinanciersRubrique>();
			this.listRubriqueParente = new ArrayList<EtatFinanciersRubrique>();
			return;
		}
		this.listRubrique = this.rubriqueModel.getRubriquesByEtat(this.idEtat, this.factory);
		if (this.listRubrique == null)
			this.listRubrique = new ArrayList<EtatFinanciersRubrique>();

		// Rubriques parentes possibles = titres et sous-titres
		this.listRubriqueParente = new ArrayList<EtatFinanciersRubrique>();
		for (EtatFinanciersRubrique r : this.listRubrique) {
			if (r.getTypeLigne() == TypeLigne.TIT || r.getTypeLigne() == TypeLigne.ST) {
				this.listRubriqueParente.add(r);
			}
		}
	}

	// ============================================================
	// SÉLECTION DE L'ÉTAT
	// ============================================================
	
	public void getSelectedEFiValue() {
	    if (this.idEtat == null || this.idEtat == 0L) {
	        this.selectedEtat = null;
	        this.listRubrique = new ArrayList<EtatFinanciersRubrique>();
	        this.listRubriqueParente = new ArrayList<EtatFinanciersRubrique>();
	        return;
	    }
	    // Recharger l'entité à partir de l'ID
	    this.selectedEtat = this.model.getEtatFinanciersById(this.idEtat, this.factory);
	   // chargerRubrique();
	   // initializeControl();
	}
	// ============================================================
		// RUBRIQUE PARENT
		// ============================================================
		
	public void getSelectedParentValue() {
	    if (this.idRubriqueParent == null || this.idRubriqueParent == 0L) {
	        this.rubriqueParenteSelectionnee = null;
	       
	        return;
	    }
	    // Recharger l'entité à partir de l'ID
	    rubriqueParenteSelectionnee = rubriqueModel.getRubriqueById(this.idRubriqueParent, this.factory);
	  
	}
	// ============================================================
	// RECHERCHE D'UNE RUBRIQUE
	// ============================================================
	public void searchRubrique() {
	    this.disableMsg = true;
	    if (this.code != null && !this.code.equals("") 
	            && this.idEtat != null && this.idEtat > 0) {
	        this.selectedRubrique = this.rubriqueModel
	                .getRubriqueByCode(this.idEtat.intValue(), this.code, this.factory);
	        if (this.selectedRubrique != null) {
	            getRubriqueValues();
	        }
	    }
	}

	public void getSelectedRubriqueValue() {
		if (this.selectedRubrique != null) {
			getRubriqueValues();
		}
	}

	private void getRubriqueValues() {
		this.idRubrique = this.selectedRubrique.getIdRubrique();
		this.code = this.selectedRubrique.getCodeRubrique();
		this.designation = this.selectedRubrique.getLibelleRubrique();
		this.section = this.selectedRubrique.getSection();
		this.niveau = this.selectedRubrique.getNiveau();
		this.typeLigneSelectionne = this.selectedRubrique.getTypeLigne();
		this.typeCalculSelectionne = this.selectedRubrique.getTypeCalcul();
		this.typeValeurSelectionne = this.selectedRubrique.getTypeValeur();
		this.formule = this.selectedRubrique.getFormule();
		this.ordreAffichage = this.selectedRubrique.getOrdreAffichage();
		this.signe = this.selectedRubrique.getSigne();
		this.afficheAmortissements = Boolean.TRUE.equals(this.selectedRubrique.getAfficheAmortissements());
		this.visible = Boolean.TRUE.equals(this.selectedRubrique.getVisible());
		this.imprimable = Boolean.TRUE.equals(this.selectedRubrique.getImprimable());
		this.rubriqueParenteSelectionnee = this.selectedRubrique.getRubriqueParent();
		if(rubriqueParenteSelectionnee!=null)
		idRubriqueParent=rubriqueParenteSelectionnee.getIdRubrique();
		this.disableMsg = false;
	}

	// ============================================================
	// SAUVEGARDE
	// ============================================================
	public void save() {
		if (code == null || code.equals("")) {
			HelperC.afficherAttention("Attention", "Il faut préciser le code !");
			return;
		}
		if (designation == null || designation.equals("")) {
			HelperC.afficherAttention("Attention", "Il faut préciser le libellé !");
			return;
		}
		if (this.idEtat == null || this.idEtat == 0L) {
			HelperC.afficherAttention("Attention", "Il faut sélectionner un état !");
			return;
		}
		if (this.typeLigneSelectionne == null ) {
			HelperC.afficherAttention("Attention", "Il faut préciser le type de ligne !");
			return;
		}
		if (this.typeCalculSelectionne == null ) {
			HelperC.afficherAttention("Attention", "Il faut préciser le type de calcul !");
			return;
		}
		if (this.section == null || this.section.equals("")) {
			HelperC.afficherAttention("Attention", "Il faut préciser la séction !");
			return;
		}
		if (this.selectedRubrique == null)
			this.selectedRubrique = new EtatFinanciersRubrique();

		// Récupérer l'état parent
		EtatFinanciers etat = this.model.getEtatFinanciersById(this.idEtat, this.factory);

		this.selectedRubrique.setEtat(etat);
		this.selectedRubrique.setCodeRubrique(code);
		this.selectedRubrique.setLibelleRubrique(designation);
		this.selectedRubrique.setSection(section);
		this.selectedRubrique.setNiveau(niveau != null ? niveau : 3);
		this.selectedRubrique.setTypeLigne(typeLigneSelectionne);
		this.selectedRubrique.setTypeCalcul(typeCalculSelectionne);
		this.selectedRubrique.setTypeValeur(typeValeurSelectionne);
		this.selectedRubrique.setFormule(formule);
		this.selectedRubrique.setOrdreAffichage(ordreAffichage != null ? ordreAffichage : 0);
		this.selectedRubrique.setSigne(signe != null ? signe : 1);
		this.selectedRubrique.setAfficheAmortissements(afficheAmortissements);
		this.selectedRubrique.setVisible(visible);
		this.selectedRubrique.setImprimable(imprimable);
		this.selectedRubrique.setRubriqueParent(rubriqueParenteSelectionnee);

		this.rubriqueModel.saveRubrique(factory, selectedRubrique);

		chargerRubrique();
		initializeControl();
	}

	// ============================================================
	// SUPPRESSION
	// ============================================================
	public void delete() {
		if (this.selectedRubrique != null && this.selectedRubrique.getIdRubrique() > 0) {
			this.rubriqueModel.deleteRubrique(factory, selectedRubrique);
			initializeControl();
			chargerRubrique();
		}
	}

	// ============================================================
	// RÉINITIALISATION
	// ============================================================
	public void initializeControl() {
		this.idRubrique = 0L;
		this.code = "";
		this.designation = "";
		this.section = null;
		this.niveau = 1;
		this.formule = null;
		this.ordreAffichage = null;
		this.signe = 1;
		this.afficheAmortissements = false;
		this.visible = false;
		this.imprimable = false;
		this.rubriqueParenteSelectionnee = null;
		this.disableMsg = false;
		this.selectedRubrique = null;
		this.typeLigneSelectionne =null;
		this.typeCalculSelectionne = null;
		this.typeValeurSelectionne = null;
	}

	// ============================================================
	// DÉPLACEMENT HAUT / BAS
	// ============================================================
	public void monter(EtatFinanciersRubrique r) {
		echangerOrdre(r, -1);
	}

	public void descendre(EtatFinanciersRubrique r) {
		echangerOrdre(r, +1);
	}
	public void preparerModification(EtatFinanciersRubrique r) {
	    this.selectedRubrique = r;
	    getRubriqueValues();
	}
	// Vérifie si c'est la première ligne du tableau
	public boolean isPremiereLigne(int index) {
	    return index == 0;
	}

	// Vérifie si c'est la dernière ligne du tableau
	public boolean isDerniereLigne(int index) {
	    if (listRubrique == null || listRubrique.isEmpty()) {
	        return true;
	    }
	    return index == (listRubrique.size() - 1);
	}
	private void echangerOrdre(EtatFinanciersRubrique r, int direction) {
	    try {
	        if (r == null || listRubrique == null) return;

	        int idx = listRubrique.indexOf(r);
	        int cible = idx + direction;
	        if (cible < 0 || cible >= listRubrique.size()) return;

	        EtatFinanciersRubrique autre = listRubrique.get(cible);

	        Integer ordreR = r.getOrdreAffichage();
	        Integer ordreAutre = autre.getOrdreAffichage();

	        // On utilise une valeur temporaire pour éviter les conflits de contrainte unique en base
	        r.setOrdreAffichage(-999);
	        rubriqueModel.saveRubrique(factory, r);

	        r.setOrdreAffichage(ordreAutre);
	        autre.setOrdreAffichage(ordreR);

	        rubriqueModel.saveRubrique(factory, autre);
	        rubriqueModel.saveRubrique(factory, r);

	        chargerRubrique();
	    } catch (Exception e) {
	        e.printStackTrace();
	        HelperC.afficherAttention("Erreur", "Erreur lors du déplacement : " + e.getMessage());
	    }
	}

	
}
