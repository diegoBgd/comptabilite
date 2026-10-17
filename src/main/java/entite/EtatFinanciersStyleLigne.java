package entite;

import javax.persistence.*;

import enumeration.Alignement;
import enumeration.TypeBordure;
import enumeration.TypeLigne;

import java.io.Serializable;

@Entity
@Table(name = "tb_style_ligne")
public class EtatFinanciersStyleLigne implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_style")
	private Long idStyle;

	@Column(name = "type_ligne", nullable = false, unique = true, length = 10)
	private TypeLigne typeLigne;

	@Column(name = "gras")
	private Boolean gras = false;

	@Column(name = "italique")
	private Boolean italique = false;

	@Column(name = "souligne")
	private Boolean souligne = false;

	@Column(name = "majuscule")
	private Boolean majuscule = false;

	@Column(name = "taille_police")
	private Integer taillePolice = 10;

	@Column(name = "couleur_texte", length = 10)
	private String couleurTexte = "#000000";

	@Column(name = "couleur_fond", length = 10)
	private String couleurFond = "#FFFFFF";

	@Column(name = "indentation")
	private Integer indentation = 0;

	@Column(name = "saut_page_avant")
	private Boolean sautPageAvant = false;

	@Enumerated(EnumType.STRING)
	@Column(name = "alignement", length = 10)
	private Alignement alignement = Alignement.GAUCHE;

	@Enumerated(EnumType.STRING)
	@Column(name = "bordure_haut", length = 10)
	private TypeBordure bordureHaut = TypeBordure.AUCUNE;

	@Enumerated(EnumType.STRING)
	@Column(name = "bordure_bas", length = 10)
	private TypeBordure bordureBas = TypeBordure.AUCUNE;

	// ---- Getters / Setters ----

	public Long getIdStyle() {
		return idStyle;
	}

	public void setIdStyle(Long idStyle) {
		this.idStyle = idStyle;
	}

	public TypeLigne getTypeLigne() {
		return typeLigne;
	}

	public void setTypeLigne(TypeLigne typeLigne) {
		this.typeLigne = typeLigne;
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

	public Alignement getAlignement() {
		return alignement;
	}

	public void setAlignement(Alignement alignement) {
		this.alignement = alignement;
	}

	public TypeBordure getBordureHaut() {
		return bordureHaut;
	}

	public void setBordureHaut(TypeBordure bordureHaut) {
		this.bordureHaut = bordureHaut;
	}

	public TypeBordure getBordureBas() {
		return bordureBas;
	}

	public void setBordureBas(TypeBordure bordureBas) {
		this.bordureBas = bordureBas;
	}
	
	
}