package entite;

import javax.persistence.*;

import enumeration.TypeCalcul;
import enumeration.TypeLigne;
import enumeration.TypeValeur;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_rubriques_etat")
public class EtatFinanciersRubrique implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8784560179421287839L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_rubrique")
	private Long idRubrique;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_etat", nullable = false)
	private EtatFinanciers etat;

	@Column(name = "code_rubrique", nullable = false, length = 20)
	private String codeRubrique;

	@Column(name = "libelle_rubrique", nullable = false, length = 200)
	private String libelleRubrique;

	@Column(name = "section", length = 10)
	private String section;

	@Column(name = "niveau")
	private Integer niveau = 3;

	  @Enumerated(EnumType.STRING)
	    @Column(name = "type_ligne", nullable = false, length = 10)
	    private TypeLigne typeLigne;

	    @Enumerated(EnumType.STRING)
	    @Column(name = "type_calcul", nullable = false, length = 20)
	    private TypeCalcul typeCalcul;

	    @Enumerated(EnumType.STRING)
	    @Column(name = "type_valeur", length = 20)
	    private TypeValeur typeValeur = TypeValeur.NET;
	    
	@Column(name = "ordre_affichage", nullable = false)
	private Integer ordreAffichage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rubrique_parent")
	private EtatFinanciersRubrique rubriqueParent;

	@Column(name = "formule", length = 500)
	private String formule;

	@Column(name = "sens_normal", length = 5)
	private String sensNormal;

	@Column(name = "signe")
	private Integer signe = 1;

	@Column(name = "affiche_amortissements")
	private Boolean afficheAmortissements = false;

	@Column(name = "visible")
	private Boolean visible = true;

	@Column(name = "imprimable")
	private Boolean imprimable = true;

	@OneToMany(mappedBy = "rubrique", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<EtatFinanciersCompte> mappings = new ArrayList<>();

	// ---- Getters / Setters ----

	public Long getIdRubrique() {
		return idRubrique;
	}

	public void setIdRubrique(Long idRubrique) {
		this.idRubrique = idRubrique;
	}

	public EtatFinanciers getEtat() {
		return etat;
	}

	public void setEtat(EtatFinanciers etat) {
		this.etat = etat;
	}

	public String getCodeRubrique() {
		return codeRubrique;
	}

	public void setCodeRubrique(String codeRubrique) {
		this.codeRubrique = codeRubrique;
	}

	public String getLibelleRubrique() {
		return libelleRubrique;
	}

	public void setLibelleRubrique(String libelleRubrique) {
		this.libelleRubrique = libelleRubrique;
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

	
	public Integer getOrdreAffichage() {
		return ordreAffichage;
	}

	public void setOrdreAffichage(Integer ordreAffichage) {
		this.ordreAffichage = ordreAffichage;
	}

	public EtatFinanciersRubrique getRubriqueParent() {
		return rubriqueParent;
	}

	public void setRubriqueParent(EtatFinanciersRubrique rubriqueParent) {
		this.rubriqueParent = rubriqueParent;
	}

	
	public String getFormule() {
		return formule;
	}

	public void setFormule(String formule) {
		this.formule = formule;
	}

	public String getSensNormal() {
		return sensNormal;
	}

	public void setSensNormal(String sensNormal) {
		this.sensNormal = sensNormal;
	}

	public Integer getSigne() {
		return signe;
	}

	public void setSigne(Integer signe) {
		this.signe = signe;
	}

	public Boolean getAfficheAmortissements() {
		return afficheAmortissements;
	}

	public void setAfficheAmortissements(Boolean afficheAmortissements) {
		this.afficheAmortissements = afficheAmortissements;
	}

	
	public TypeLigne getTypeLigne() {
		return typeLigne;
	}

	public void setTypeLigne(TypeLigne typeLigne) {
		this.typeLigne = typeLigne;
	}

	public TypeCalcul getTypeCalcul() {
		return typeCalcul;
	}

	public void setTypeCalcul(TypeCalcul typeCalcul) {
		this.typeCalcul = typeCalcul;
	}

	public TypeValeur getTypeValeur() {
		return typeValeur;
	}

	public void setTypeValeur(TypeValeur typeValeur) {
		this.typeValeur = typeValeur;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Boolean getImprimable() {
		return imprimable;
	}

	public void setImprimable(Boolean imprimable) {
		this.imprimable = imprimable;
	}

	public List<EtatFinanciersCompte> getMappings() {
		return mappings;
	}

	public void setMappings(List<EtatFinanciersCompte> mappings) {
		this.mappings = mappings;
	}
}