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

import entite.EtatFinanciersStyleLigne;
import entite.Exercice;
import entite.User;
import enumeration.Alignement;
import enumeration.TypeBordure;
import enumeration.TypeLigne;
import model.EtatFinanciersStyleLigneModel;
import model.ExerciceModel;
import model.UserModel;
import persistances.DBConfiguration;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class EtatFinanciersStyleLigneVew implements Serializable {

	private static final long serialVersionUID = 4359829324093820851L;

	SessionFactory factory = DBConfiguration.getSessionFactory();

	// ============================================================
	// DONNÉES
	// ============================================================
	private List<EtatFinanciersStyleLigne> listStyle;
	private EtatFinanciersStyleLigne selectedStyle;

	private Long idStyle = 0L;
	private String code;
	private String designation;

	// ============================================================
	// CHAMPS D'ÉDITION
	// ============================================================
	private TypeLigne typeLigneSelectionne;
	private Boolean gras;
	private Boolean italique;
	private Boolean souligne;
	private Boolean majuscule;
	private Integer taillePolice;
	private String couleurTexte;
	private String couleurFond;
	private Alignement alignementSelectionne;
	private TypeBordure bordureHautSelectionnee;
	private TypeBordure bordureBasSelectionnee;
	private Integer indentation;
	private Boolean sautPageAvant;

	// ============================================================
	// ÉNUMÉRATIONS
	// ============================================================
	private List<TypeLigne> listeTypesLigne;
	private List<Alignement> listeAlignements;
	private List<TypeBordure> listeBordures;

	// ============================================================
	// ÉTAT INTERFACE
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
	EtatFinanciersStyleLigneModel model;

	// ============================================================
	// GETTERS / SETTERS
	// ============================================================
	public List<EtatFinanciersStyleLigne> getListStyle() {
		return listStyle;
	}

	public void setListStyle(List<EtatFinanciersStyleLigne> listStyle) {
		this.listStyle = listStyle;
	}

	public EtatFinanciersStyleLigne getSelectedStyle() {
		return selectedStyle;
	}

	public void setSelectedStyle(EtatFinanciersStyleLigne selectedStyle) {
		this.selectedStyle = selectedStyle;
	}

	public Long getIdStyle() {
		return idStyle;
	}

	public void setIdStyle(Long idStyle) {
		this.idStyle = idStyle;
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

	public TypeLigne getTypeLigneSelectionne() {
		return typeLigneSelectionne;
	}

	public void setTypeLigneSelectionne(TypeLigne typeLigneSelectionne) {
		this.typeLigneSelectionne = typeLigneSelectionne;
	}

	public Boolean getGras() {
		return gras;
	}

	public void setGras(Boolean gras) {
		this.gras = gras;
	}

	public Boolean getItalique() {
		return italique;
	}

	public void setItalique(Boolean italique) {
		this.italique = italique;
	}

	public Boolean getSouligne() {
		return souligne;
	}

	public void setSouligne(Boolean souligne) {
		this.souligne = souligne;
	}

	public Boolean getMajuscule() {
		return majuscule;
	}

	public void setMajuscule(Boolean majuscule) {
		this.majuscule = majuscule;
	}

	public Integer getTaillePolice() {
		return taillePolice;
	}

	public void setTaillePolice(Integer taillePolice) {
		this.taillePolice = taillePolice;
	}

	public String getCouleurTexte() {
		return couleurTexte;
	}

	public void setCouleurTexte(String couleurTexte) {
		this.couleurTexte = couleurTexte;
	}

	public String getCouleurFond() {
		return couleurFond;
	}

	public void setCouleurFond(String couleurFond) {
		this.couleurFond = couleurFond;
	}

	public Alignement getAlignementSelectionne() {
		return alignementSelectionne;
	}

	public void setAlignementSelectionne(Alignement alignementSelectionne) {
		this.alignementSelectionne = alignementSelectionne;
	}

	public TypeBordure getBordureHautSelectionnee() {
		return bordureHautSelectionnee;
	}

	public void setBordureHautSelectionnee(TypeBordure bordureHautSelectionnee) {
		this.bordureHautSelectionnee = bordureHautSelectionnee;
	}

	public TypeBordure getBordureBasSelectionnee() {
		return bordureBasSelectionnee;
	}

	public void setBordureBasSelectionnee(TypeBordure bordureBasSelectionnee) {
		this.bordureBasSelectionnee = bordureBasSelectionnee;
	}

	public Integer getIndentation() {
		return indentation;
	}

	public void setIndentation(Integer indentation) {
		this.indentation = indentation;
	}

	public Boolean getSautPageAvant() {
		return sautPageAvant;
	}

	public void setSautPageAvant(Boolean sautPageAvant) {
		this.sautPageAvant = sautPageAvant;
	}

	public List<TypeLigne> getListeTypesLigne() {
		return listeTypesLigne;
	}

	public void setListeTypesLigne(List<TypeLigne> listeTypesLigne) {
		this.listeTypesLigne = listeTypesLigne;
	}

	public List<Alignement> getListeAlignements() {
		return listeAlignements;
	}

	public void setListeAlignements(List<Alignement> listeAlignements) {
		this.listeAlignements = listeAlignements;
	}

	public List<TypeBordure> getListeBordures() {
		return listeBordures;
	}

	public void setListeBordures(List<TypeBordure> listeBordures) {
		this.listeBordures = listeBordures;
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
		this.model = new EtatFinanciersStyleLigneModel();
		this.disableMsg = true;

		chargementSession();
		chargerStyle();

		listeTypesLigne = Arrays.asList(TypeLigne.values());
		listeAlignements = Arrays.asList(Alignement.values());
		listeBordures = Arrays.asList(TypeBordure.values());

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

	private void chargerStyle() {
		this.listStyle = this.model.getAllStyles(this.factory);
		if (this.listStyle == null)
			this.listStyle = new ArrayList<EtatFinanciersStyleLigne>();
	}

	// ============================================================
	// SÉLECTION
	// ============================================================
	public void searchStyle() {
		this.disableMsg = true;

		if (this.code != null && !this.code.trim().isEmpty()) {
			try {
				TypeLigne tl = TypeLigne.valueOf(this.code.trim().toUpperCase());
				this.selectedStyle = this.model.getStyleByTypeLigne(tl.name(), this.factory);
				if (this.selectedStyle != null) {
					getStyleValues();
				}
			} catch (IllegalArgumentException e) {
				HelperC.afficherAttention("Attention",
						"Type de ligne invalide. Valeurs possibles : TIT, ST, RUB, AGR, TOT, SOL, SEP, CTRL");
			}
		}
	}

	public void getSelectedStyleValue() {
		if (this.selectedStyle != null) {
			getStyleValues();
		}
	}

	private void getStyleValues() {
		this.idStyle = this.selectedStyle.getIdStyle();
		this.typeLigneSelectionne = this.selectedStyle.getTypeLigne();
		this.gras = this.selectedStyle.getGras();
		this.italique = this.selectedStyle.getItalique();
		this.souligne = this.selectedStyle.getSouligne();
		this.majuscule = this.selectedStyle.getMajuscule();
		this.taillePolice = this.selectedStyle.getTaillePolice();
		this.couleurTexte = this.selectedStyle.getCouleurTexte();
		this.couleurFond = this.selectedStyle.getCouleurFond();
		this.alignementSelectionne = this.selectedStyle.getAlignement();
		this.bordureHautSelectionnee = this.selectedStyle.getBordureHaut();
		this.bordureBasSelectionnee = this.selectedStyle.getBordureBas();
		this.indentation = this.selectedStyle.getIndentation();
		this.sautPageAvant = this.selectedStyle.getSautPageAvant();
		this.disableMsg = false;
	}

	// ============================================================
	// SAUVEGARDE
	// ============================================================
	public void save() {
		if (typeLigneSelectionne == null) {
			HelperC.afficherAttention("Attention", "Le type de ligne est obligatoire.");
			return;
		}

		try {
			EtatFinanciersStyleLigne style;
			if (this.idStyle != null && this.idStyle > 0) {
				style = this.model.getStyleById(this.idStyle.intValue(), this.factory);
				if (style == null) {
					HelperC.afficherAttention("Erreur", "Style introuvable.");
					return;
				}
			} else {
				style = new EtatFinanciersStyleLigne();
			}

			style.setTypeLigne(typeLigneSelectionne);
			style.setGras(gras != null ? gras : false);
			style.setItalique(italique != null ? italique : false);
			style.setSouligne(souligne != null ? souligne : false);
			style.setMajuscule(majuscule != null ? majuscule : false);
			style.setTaillePolice(taillePolice != null ? taillePolice : 10);
			style.setCouleurTexte(couleurTexte != null ? couleurTexte : "#000000");
			style.setCouleurFond(couleurFond != null ? couleurFond : "#FFFFFF");
			style.setAlignement(alignementSelectionne != null ? alignementSelectionne : Alignement.GAUCHE);
			style.setBordureHaut(bordureHautSelectionnee != null ? bordureHautSelectionnee : TypeBordure.AUCUNE);
			style.setBordureBas(bordureBasSelectionnee != null ? bordureBasSelectionnee : TypeBordure.AUCUNE);
			style.setIndentation(indentation != null ? indentation : 0);
			style.setSautPageAvant(sautPageAvant != null ? sautPageAvant : false);

			this.model.saveStyle(factory, style);

			HelperC.afficherInformation("Succès", "Style enregistré avec succès.");
			chargerStyle();
			initializeControl();

		} catch (Exception e) {
			HelperC.afficherAttention("Erreur", "Erreur lors de l'enregistrement : " + e.getMessage());
			e.printStackTrace();
		}
	}

	// ============================================================
	// SUPPRESSION
	// ============================================================
	public void delete() {
		if (this.selectedStyle == null || this.selectedStyle.getIdStyle() == null
				|| this.selectedStyle.getIdStyle() == 0L) {
			HelperC.afficherAttention("Attention", "Aucun style sélectionné.");
			return;
		}

		try {
			this.model.deleteStyle(factory, this.selectedStyle);
			HelperC.afficherInformation("Succès", "Style supprimé.");
			chargerStyle();
			initializeControl();

		} catch (Exception e) {
			HelperC.afficherAttention("Erreur", "Erreur lors de la suppression : " + e.getMessage());
			e.printStackTrace();
		}
	}

	// ============================================================
	// INITIALISATION DES CHAMPS
	// ============================================================
	public void initializeControl() {
		this.idStyle = 0L;
		this.code = "";
		this.designation = "";
		this.typeLigneSelectionne = null;
		this.gras = false;
		this.italique = false;
		this.souligne = false;
		this.majuscule = false;
		this.taillePolice = 10;
		this.couleurTexte = "#000000";
		this.couleurFond = "#FFFFFF";
		this.alignementSelectionne = Alignement.GAUCHE;
		this.bordureHautSelectionnee = TypeBordure.AUCUNE;
		this.bordureBasSelectionnee = TypeBordure.AUCUNE;
		this.indentation = 0;
		this.sautPageAvant = false;
		this.disableMsg = true;
		this.selectedStyle = null;
	}

}