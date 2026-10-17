package dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import entite.EtatFinanciersStyleLigne;
import enumeration.TypeLigne;
import enumeration.TypeValeur;

public class LigneEtatDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8401816420663190165L;
	// Identité
	private String code;
	private String libelle;

	// Montants génériques (TFT, CR, Bilan Passif / Actif circulant)
	private BigDecimal montantN = BigDecimal.ZERO;
	private BigDecimal montantN1 = BigDecimal.ZERO;
	private BigDecimal variation = BigDecimal.ZERO;

	// Montants spécifiques Bilan — ACTIF IMMOBILISÉ
	private BigDecimal brutN = BigDecimal.ZERO;
	private BigDecimal amortN = BigDecimal.ZERO;
	private BigDecimal netN = BigDecimal.ZERO;
	private BigDecimal netN1 = BigDecimal.ZERO;

	// Métadonnées de présentation
	private TypeLigne typeLigne;
	private Integer niveau;
	private boolean afficheAmortissements;
	private TypeValeur typeValeur;
	private boolean visible;
	private EtatFinanciersStyleLigne style;
	private Map<String, BigDecimal> montantsParColonne = new HashMap<>();

	public Map<String, BigDecimal> getMontantsParColonne() { return montantsParColonne; }
	public void setMontantsParColonne(Map<String, BigDecimal> m) { this.montantsParColonne = m; }

	/** Utilitaire pour le XHTML */
	public BigDecimal getMontant(String codeColonne) {
	    BigDecimal v = montantsParColonne.get(codeColonne);
	    return v != null ? v : BigDecimal.ZERO;
	}
	public LigneEtatDTO() {
	}

	// ---- Getters / Setters ----

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public BigDecimal getMontantN() {
		return montantN;
	}

	public void setMontantN(BigDecimal montantN) {
		this.montantN = montantN;
	}

	public BigDecimal getMontantN1() {
		return montantN1;
	}

	public void setMontantN1(BigDecimal montantN1) {
		this.montantN1 = montantN1;
	}

	public BigDecimal getVariation() {
		return variation;
	}

	public void setVariation(BigDecimal variation) {
		this.variation = variation;
	}

	public BigDecimal getBrutN() {
		return brutN;
	}

	public void setBrutN(BigDecimal brutN) {
		this.brutN = brutN;
	}

	public BigDecimal getAmortN() {
		return amortN;
	}

	public void setAmortN(BigDecimal amortN) {
		this.amortN = amortN;
	}

	public BigDecimal getNetN() {
		return netN;
	}

	public void setNetN(BigDecimal netN) {
		this.netN = netN;
	}

	public BigDecimal getNetN1() {
		return netN1;
	}

	public void setNetN1(BigDecimal netN1) {
		this.netN1 = netN1;
	}

	
	public TypeLigne getTypeLigne() {
		return typeLigne;
	}

	public void setTypeLigne(TypeLigne typeLigne) {
		this.typeLigne = typeLigne;
	}

	public Integer getNiveau() {
		return niveau;
	}

	public void setNiveau(Integer niveau) {
		this.niveau = niveau;
	}

	public boolean isAfficheAmortissements() {
		return afficheAmortissements;
	}

	public void setAfficheAmortissements(boolean afficheAmortissements) {
		this.afficheAmortissements = afficheAmortissements;
	}

	
	public TypeValeur getTypeValeur() {
		return typeValeur;
	}

	public void setTypeValeur(TypeValeur typeValeur) {
		this.typeValeur = typeValeur;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public EtatFinanciersStyleLigne getStyle() {
		return style;
	}

	public void setStyle(EtatFinanciersStyleLigne style) {
		this.style = style;
	}
}