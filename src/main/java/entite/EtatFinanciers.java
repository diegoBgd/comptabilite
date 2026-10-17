package entite;

import javax.persistence.*;

import enumeration.ModeCalcul;
import enumeration.TypeEtat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_etats_financiers")
public class EtatFinanciers implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6838940126830309685L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_etat")
	private Long idEtat;

	@Column(name = "code_etat", nullable = false, unique = true, length = 20)
	private String codeEtat;

	@Column(name = "libelle_etat", nullable = false, length = 100)
	private String libelleEtat;

	@Enumerated(EnumType.STRING)
    @Column(name = "type_etat", nullable = false, length = 20)
    private TypeEtat typeEtat;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode_calcul", nullable = false, length = 20)
    private ModeCalcul modeCalcul;


	@Column(name = "actif")
	private Boolean actif = true;

	@Column(name = "date_creation")
	private LocalDateTime dateCreation = LocalDateTime.now();

	@OneToMany(mappedBy = "etat", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	@OrderBy("ordreAffichage ASC")
	private List<EtatFinanciersRubrique> rubriques = new ArrayList<>();

	// ---- Getters / Setters ----

	public Long getIdEtat() {
		return idEtat;
	}

	public void setIdEtat(Long idEtat) {
		this.idEtat = idEtat;
	}

	public String getCodeEtat() {
		return codeEtat;
	}

	public void setCodeEtat(String codeEtat) {
		this.codeEtat = codeEtat;
	}

	public String getLibelleEtat() {
		return libelleEtat;
	}

	public void setLibelleEtat(String libelleEtat) {
		this.libelleEtat = libelleEtat;
	}

	

	public TypeEtat getTypeEtat() {
		return typeEtat;
	}

	public void setTypeEtat(TypeEtat typeEtat) {
		this.typeEtat = typeEtat;
	}

	public ModeCalcul getModeCalcul() {
		return modeCalcul;
	}

	public void setModeCalcul(ModeCalcul modeCalcul) {
		this.modeCalcul = modeCalcul;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

	public List<EtatFinanciersRubrique> getRubriques() {
		return rubriques;
	}

	public void setRubriques(List<EtatFinanciersRubrique> rubriques) {
		this.rubriques = rubriques;
	}
}
