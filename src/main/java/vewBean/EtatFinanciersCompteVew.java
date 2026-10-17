package vewBean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
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
import entite.EtatFinanciersCompte;
import entite.EtatFinanciersRubrique;
import entite.Exercice;
import entite.MappingExclusion;
import entite.MappingInclusion;
import entite.User;
import enumeration.NatureValeur;
import enumeration.TypeMouvement;
import model.EtatFinanciersCompteModel;
import model.EtatFinanciersModel;
import model.EtatFinanciersRubriqueModel;
import model.ExerciceModel;
import model.MappingExclusionModel;
import model.MappingInclusionModel;
import model.UserModel;
import persistances.DBConfiguration;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class EtatFinanciersCompteVew implements Serializable {

	private static final long serialVersionUID = 4359829324093820850L;

	SessionFactory factory = DBConfiguration.getSessionFactory();

	// ============================================================
	// SÉLECTION ÉTAT
	// ============================================================
	private List<EtatFinanciers> listEtat;
	private EtatFinanciers selectedEtat;
	private Long idEtat = 0L;

	// ============================================================
	// SÉLECTION RUBRIQUE
	// ============================================================
	private List<EtatFinanciersRubrique> listRubrique;
	private EtatFinanciersRubrique selectedRubrique;
	private Long idRubrique = 0L;

	// ============================================================
	// SÉLECTION RÈGLE DE MAPPING
	// ============================================================
	private List<EtatFinanciersCompte> listMapping;
	private EtatFinanciersCompte selectedMapping;
	private Long idMapping = 0L;

	// ============================================================
	// CHAMPS D'ÉDITION DE LA RÈGLE
	// ============================================================
	private String libelleRegle;
	private TypeMouvement typeMouvementSelectionne;
	private NatureValeur natureValeurSelectionnee;
	private BigDecimal coefficient;
	private String conditionSql;
	private Integer ordre;

	// ============================================================
	// INCLUSIONS / EXCLUSIONS EN COURS D'ÉDITION
	// ============================================================
	private String nouvelleInclusionDebut;
	private String nouvelleInclusionFin;
	private List<MappingInclusion> listInclusionsEnCours;

	private String nouvelleExclusionDebut;
	private String nouvelleExclusionFin;
	private String nouvelleExclusionMotif;
	private List<MappingExclusion> listExclusionsEnCours;

	// ============================================================
	// ÉNUMÉRATIONS
	// ============================================================
	private List<TypeMouvement> listeTypesMouvement;
	private List<NatureValeur> listeNaturesValeur;

	// ============================================================
	// ÉTAT DE L'INTERFACE
	// ============================================================
	private boolean disableMsg;

	// ============================================================
	// SESSION
	// ============================================================
	Exercice selectedExercice;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;

	// ============================================================
	// MODELS
	// ============================================================
	EtatFinanciersModel model;
	EtatFinanciersRubriqueModel rubriqueModel;
	EtatFinanciersCompteModel mappingModel;
	MappingInclusionModel inclusionModel;
	MappingExclusionModel exclusionModel;

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

		public List<EtatFinanciersRubrique> getListRubrique() {
			return listRubrique;
		}

		public void setListRubrique(List<EtatFinanciersRubrique> listRubrique) {
			this.listRubrique = listRubrique;
		}

		public EtatFinanciersRubrique getSelectedRubrique() {
			return selectedRubrique;
		}

		public void setSelectedRubrique(EtatFinanciersRubrique selectedRubrique) {
			this.selectedRubrique = selectedRubrique;
		}

		public Long getIdRubrique() {
			return idRubrique;
		}

		public void setIdRubrique(Long idRubrique) {
			this.idRubrique = idRubrique;
		}

		public List<EtatFinanciersCompte> getListMapping() {
			return listMapping;
		}

		public void setListMapping(List<EtatFinanciersCompte> listMapping) {
			this.listMapping = listMapping;
		}

		public EtatFinanciersCompte getSelectedMapping() {
			return selectedMapping;
		}

		public void setSelectedMapping(EtatFinanciersCompte selectedMapping) {
			this.selectedMapping = selectedMapping;
		}

		public Long getIdMapping() {
			return idMapping;
		}

		public void setIdMapping(Long idMapping) {
			this.idMapping = idMapping;
		}

		public String getLibelleRegle() {
			return libelleRegle;
		}

		public void setLibelleRegle(String libelleRegle) {
			this.libelleRegle = libelleRegle;
		}

		public TypeMouvement getTypeMouvementSelectionne() {
			return typeMouvementSelectionne;
		}

		public void setTypeMouvementSelectionne(TypeMouvement typeMouvementSelectionne) {
			this.typeMouvementSelectionne = typeMouvementSelectionne;
		}

		public NatureValeur getNatureValeurSelectionnee() {
			return natureValeurSelectionnee;
		}

		public void setNatureValeurSelectionnee(NatureValeur natureValeurSelectionnee) {
			this.natureValeurSelectionnee = natureValeurSelectionnee;
		}

		public BigDecimal getCoefficient() {
			return coefficient;
		}

		public void setCoefficient(BigDecimal coefficient) {
			this.coefficient = coefficient;
		}

		public String getConditionSql() {
			return conditionSql;
		}

		public void setConditionSql(String conditionSql) {
			this.conditionSql = conditionSql;
		}

		public Integer getOrdre() {
			return ordre;
		}

		public void setOrdre(Integer ordre) {
			this.ordre = ordre;
		}

		public List<MappingInclusion> getListInclusionsEnCours() {
			return listInclusionsEnCours;
		}

		public void setListInclusionsEnCours(List<MappingInclusion> listInclusionsEnCours) {
			this.listInclusionsEnCours = listInclusionsEnCours;
		}

		public List<MappingExclusion> getListExclusionsEnCours() {
			return listExclusionsEnCours;
		}

		public void setListExclusionsEnCours(List<MappingExclusion> listExclusionsEnCours) {
			this.listExclusionsEnCours = listExclusionsEnCours;
		}

		public String getNouvelleInclusionDebut() {
			return nouvelleInclusionDebut;
		}

		public void setNouvelleInclusionDebut(String nouvelleInclusionDebut) {
			this.nouvelleInclusionDebut = nouvelleInclusionDebut;
		}

		public String getNouvelleInclusionFin() {
			return nouvelleInclusionFin;
		}

		public void setNouvelleInclusionFin(String nouvelleInclusionFin) {
			this.nouvelleInclusionFin = nouvelleInclusionFin;
		}

		public String getNouvelleExclusionDebut() {
			return nouvelleExclusionDebut;
		}

		public void setNouvelleExclusionDebut(String nouvelleExclusionDebut) {
			this.nouvelleExclusionDebut = nouvelleExclusionDebut;
		}

		public String getNouvelleExclusionFin() {
			return nouvelleExclusionFin;
		}

		public void setNouvelleExclusionFin(String nouvelleExclusionFin) {
			this.nouvelleExclusionFin = nouvelleExclusionFin;
		}

		public String getNouvelleExclusionMotif() {
			return nouvelleExclusionMotif;
		}

		public void setNouvelleExclusionMotif(String nouvelleExclusionMotif) {
			this.nouvelleExclusionMotif = nouvelleExclusionMotif;
		}

		public List<TypeMouvement> getListeTypesMouvement() {
			return listeTypesMouvement;
		}

		public void setListeTypesMouvement(List<TypeMouvement> listeTypesMouvement) {
			this.listeTypesMouvement = listeTypesMouvement;
		}

		public List<NatureValeur> getListeNaturesValeur() {
			return listeNaturesValeur;
		}

		public void setListeNaturesValeur(List<NatureValeur> listeNaturesValeur) {
			this.listeNaturesValeur = listeNaturesValeur;
		}

		public boolean isDisableMsg() {
			return disableMsg;
		}

		public void setDisableMsg(boolean disableMsg) {
			this.disableMsg = disableMsg;
		}
	// ============================================================
	// INITIALISATION
	// ============================================================
	@PostConstruct
	public void initilaze() {
		this.model = new EtatFinanciersModel();
		this.rubriqueModel = new EtatFinanciersRubriqueModel();
		this.mappingModel = new EtatFinanciersCompteModel();
		this.inclusionModel = new MappingInclusionModel();
		this.exclusionModel = new MappingExclusionModel();

		this.disableMsg = true;
		this.listInclusionsEnCours = new ArrayList<MappingInclusion>();
		this.listExclusionsEnCours = new ArrayList<MappingExclusion>();

		chargementSession();
		chargerEtatFinancier();

		listeTypesMouvement = Arrays.asList(TypeMouvement.values());
		listeNaturesValeur = Arrays.asList(NatureValeur.values());

		// Présélection du premier état
		if (this.listEtat != null && !this.listEtat.isEmpty()) {
			this.selectedEtat = this.listEtat.get(0);
			this.idEtat = this.selectedEtat.getIdEtat();
			chargerRubrique();
		}

		initializeControl();
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
		if (this.listEtat == null)
			this.listEtat = new ArrayList<EtatFinanciers>();
	}

	private void chargerRubrique() {
		if (this.idEtat == null || this.idEtat == 0L) {
			this.listRubrique = new ArrayList<EtatFinanciersRubrique>();
			return;
		}
		this.listRubrique = this.rubriqueModel.getRubriquesByEtat(this.idEtat, this.factory);
		
		if (this.listRubrique == null)
			this.listRubrique = new ArrayList<EtatFinanciersRubrique>();
	}

	private void chargerMapping() {
		if (this.idRubrique == null || this.idRubrique == 0L) {
			this.listMapping = new ArrayList<EtatFinanciersCompte>();
			return;
		}
		this.listMapping = this.mappingModel.getMappingsByRubrique(this.idRubrique, this.factory);
		if (this.listMapping == null)
			this.listMapping = new ArrayList<EtatFinanciersCompte>();
	}

	// ============================================================
	// SÉLECTION
	// ============================================================
	public void getSelectedEFiValue() {
		if (this.selectedEtat != null) {
			this.idEtat = this.selectedEtat.getIdEtat();
			chargerRubrique();
			initializeControl();
		}
	}

	public void getSelectedRubriqueValue() {
		if (this.idRubrique != null && idRubrique>0L) {
			selectedRubrique = rubriqueModel.getRubriqueById(idRubrique, factory);
			chargerMapping();
			initializeControl();
		}
	}

	public void getSelectedMappingValue() {
		if (this.selectedMapping != null) {
			getMappingValues();
		}
	}

	private void getMappingValues() {
		this.idMapping = this.selectedMapping.getIdMapping();
		this.libelleRegle = this.selectedMapping.getLibelleRegle();
		this.typeMouvementSelectionne = this.selectedMapping.getTypeMouvement();
		this.natureValeurSelectionnee = this.selectedMapping.getNatureValeur();
		this.coefficient = this.selectedMapping.getCoefficient();
		this.conditionSql = this.selectedMapping.getConditionSql();
		this.ordre = this.selectedMapping.getOrdre();

		// Charger les inclusions / exclusions en mémoire
		this.listInclusionsEnCours = new ArrayList<MappingInclusion>();
		List<MappingInclusion> incs = this.inclusionModel.getInclusionsByMapping(this.idMapping,
				this.factory);
		if (incs != null)
			this.listInclusionsEnCours = incs;

		this.listExclusionsEnCours = new ArrayList<MappingExclusion>();
		List<MappingExclusion> excs = this.exclusionModel.getExclusionsByMapping(this.idMapping,
				this.factory);
		if (excs != null)
			this.listExclusionsEnCours = excs;

		this.disableMsg = false;
	}

	// ============================================================
	// AJOUT / SUPPRESSION D'INCLUSIONS
	// ============================================================
	public void ajouterInclusion() {
		if (nouvelleInclusionDebut == null || nouvelleInclusionDebut.trim().isEmpty()) {
			HelperC.afficherAttention("Attention", "Le compte de début est obligatoire.");
			return;
		}
		MappingInclusion inc = new MappingInclusion();
		inc.setCompteDebut(nouvelleInclusionDebut.trim());
		inc.setCompteFin(
				nouvelleInclusionFin != null && !nouvelleInclusionFin.trim().isEmpty() ? nouvelleInclusionFin.trim()
						: nouvelleInclusionDebut.trim());
		inc.setTypePlage("PLAGE");
		listInclusionsEnCours.add(inc);

		nouvelleInclusionDebut = null;
		nouvelleInclusionFin = null;
	}

	public void supprimerInclusion(MappingInclusion inc) {
		listInclusionsEnCours.remove(inc);
	}

	// ============================================================
	// AJOUT / SUPPRESSION D'EXCLUSIONS
	// ============================================================
	public void ajouterExclusion() {
		if (nouvelleExclusionDebut == null || nouvelleExclusionDebut.trim().isEmpty()) {
			HelperC.afficherAttention("Attention", "Le compte de début est obligatoire.");
			return;
		}
		MappingExclusion exc = new MappingExclusion();
		exc.setCompteDebut(nouvelleExclusionDebut.trim());
		exc.setCompteFin(
				nouvelleExclusionFin != null && !nouvelleExclusionFin.trim().isEmpty() ? nouvelleExclusionFin.trim()
						: nouvelleExclusionDebut.trim());
		exc.setTypePlage("PLAGE");
		exc.setMotif(nouvelleExclusionMotif);
		listExclusionsEnCours.add(exc);

		nouvelleExclusionDebut = null;
		nouvelleExclusionFin = null;
		nouvelleExclusionMotif = null;
	}

	public void supprimerExclusion(MappingExclusion exc) {
		listExclusionsEnCours.remove(exc);
	}

	// ============================================================
	// SAUVEGARDE
	// ============================================================
	public void save() {
	    // ============================================================
	    //  VALIDATIONS
	    // ============================================================
	    if (this.idRubrique == null || this.idRubrique == 0L) {
	        HelperC.afficherAttention("Attention", "Il faut sélectionner une rubrique !");
	        return;
	    }
	    if (libelleRegle == null || libelleRegle.trim().isEmpty()) {
	        HelperC.afficherAttention("Attention", "Le libellé de la règle est obligatoire.");
	        return;
	    }
	    if (typeMouvementSelectionne == null) {
	        HelperC.afficherAttention("Attention", "Le type de mouvement est obligatoire.");
	        return;
	    }
	    if (natureValeurSelectionnee == null) {
	        HelperC.afficherAttention("Attention", "La nature de valeur est obligatoire.");
	        return;
	    }
	    if (listInclusionsEnCours == null || listInclusionsEnCours.isEmpty()) {
	        HelperC.afficherAttention("Attention", "Il faut au moins une inclusion.");
	        return;
	    }

	    try {
	        // ============================================================
	        //  1. RECHARGER OU CRÉER LA RÈGLE
	        // ============================================================
	        EtatFinanciersCompte mapping;
	        if (this.idMapping != null && this.idMapping > 0) {
	            mapping = this.mappingModel.getMappingById(this.idMapping.intValue(), this.factory);
	            if (mapping == null) {
	                HelperC.afficherAttention("Erreur", "Règle introuvable.");
	                return;
	            }
	        } else {
	            mapping = new EtatFinanciersCompte();
	        }

	        // ============================================================
	        //  2. RÉCUPÉRER LA RUBRIQUE PARENTE
	        // ============================================================
	        EtatFinanciersRubrique rubrique = this.rubriqueModel
	                .getRubriqueById(this.idRubrique, this.factory);
	        if (rubrique == null) {
	            HelperC.afficherAttention("Erreur", "Rubrique introuvable.");
	            return;
	        }

	        // ============================================================
	        //  3. REMPLIR LA RÈGLE
	        // ============================================================
	        mapping.setIdMapping(idMapping);
	        mapping.setRubrique(rubrique);
	        mapping.setLibelleRegle(libelleRegle);
	        mapping.setTypeMouvement(typeMouvementSelectionne);
	        mapping.setNatureValeur(natureValeurSelectionnee);
	        mapping.setCoefficient(coefficient != null ? coefficient : BigDecimal.ONE);
	        mapping.setConditionSql(conditionSql);
	        mapping.setOrdre(ordre != null ? ordre : 0);

	        // ============================================================
	        //  4. REMPLIR LES INCLUSIONS (via la liste de l'entité)
	        // ============================================================
	        mapping.getInclusions().clear();

	        for (MappingInclusion inc : listInclusionsEnCours) {
	            // Créer une nouvelle instance propre (évite les conflits Hibernate)
	            MappingInclusion nouvelle = new MappingInclusion();
	            nouvelle.setCompteDebut(inc.getCompteDebut());
	            nouvelle.setCompteFin(inc.getCompteFin());
	            nouvelle.setTypePlage(inc.getTypePlage() != null ? inc.getTypePlage() : "PLAGE");
	            nouvelle.setMapping(mapping);   // rattachement
	            mapping.getInclusions().add(nouvelle);
	        }

	        // ============================================================
	        //  5. REMPLIR LES EXCLUSIONS (via la liste de l'entité)
	        // ============================================================
	        mapping.getExclusions().clear();

	        for (MappingExclusion exc : listExclusionsEnCours) {
	            MappingExclusion nouvelle = new MappingExclusion();
	            nouvelle.setCompteDebut(exc.getCompteDebut());
	            nouvelle.setCompteFin(exc.getCompteFin());
	            nouvelle.setTypePlage(exc.getTypePlage() != null ? exc.getTypePlage() : "PLAGE");
	            nouvelle.setMotif(exc.getMotif());
	            nouvelle.setMapping(mapping);
	            mapping.getExclusions().add(nouvelle);
	        }

	        // ============================================================
	        //  6. UN SEUL APPEL AU DAO (une seule transaction)
	        // ============================================================
	        this.mappingModel.saveMapping(factory, mapping);

	        // ============================================================
	        //  7. RECHARGER ET RÉINITIALISER
	        // ============================================================
	        HelperC.afficherInformation("Succès", "Règle enregistrée avec succès.");
	        chargerMapping();
	        initializeControl();

	    } catch (Exception e) {
	        HelperC.afficherAttention("Erreur",
	                "Erreur lors de l'enregistrement : " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	// ============================================================
	// SUPPRESSION
	// ============================================================
	public void delete() {
		if (this.selectedMapping == null || this.selectedMapping.getIdMapping() == null
				|| this.selectedMapping.getIdMapping() == 0L) {
			HelperC.afficherAttention("Attention", "Aucune règle sélectionnée.");
			return;
		}

		try {
			// Supprimer d'abord les inclusions
			List<MappingInclusion> incs = this.inclusionModel
					.getInclusionsByMapping(this.selectedMapping.getIdMapping(), this.factory);
			if (incs != null) {
				for (MappingInclusion inc : incs) {
					this.inclusionModel.deleteInclusion(factory, inc);
				}
			}

			// Supprimer les exclusions
			List<MappingExclusion> excs = this.exclusionModel
					.getExclusionsByMapping(this.selectedMapping.getIdMapping(), this.factory);
			if (excs != null) {
				for (MappingExclusion exc : excs) {
					this.exclusionModel.deleteExclusion(factory, exc);
				}
			}

			// Supprimer la règle
			this.mappingModel.deleteMapping(factory, this.selectedMapping);

			chargerMapping();
			initializeControl();

		} catch (Exception e) {
			HelperC.afficherAttention("Erreur", "Erreur lors de la suppression : " + e.getMessage());
			e.printStackTrace();
		}
	}

	// ============================================================
	// RÉINITIALISATION
	// ============================================================
	public void initializeControl() {
		this.idMapping = 0L;
		this.libelleRegle = null;
		this.typeMouvementSelectionne = TypeMouvement.SOLDE;
		this.natureValeurSelectionnee = NatureValeur.NET;
		this.coefficient = BigDecimal.ONE;
		this.conditionSql = null;
		this.ordre = 0;

		this.listInclusionsEnCours = new ArrayList<MappingInclusion>();
		this.listExclusionsEnCours = new ArrayList<MappingExclusion>();

		this.nouvelleInclusionDebut = null;
		this.nouvelleInclusionFin = null;
		this.nouvelleExclusionDebut = null;
		this.nouvelleExclusionFin = null;
		this.nouvelleExclusionMotif = null;

		this.disableMsg = true;
		this.selectedMapping = null;
	}

	
}