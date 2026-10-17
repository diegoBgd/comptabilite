package dto;

import java.io.Serializable;

public class ColonneDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String code; // ex: "BRUT_N", "NET_N", "NET_N1"
	private String libelle; // ex: "Brut N", "Net N-1"
	private String type; // "BRUT", "AMORT", "NET_N", "NET_N1", "VARIATION"
	private int largeur; // en pourcentage

	public ColonneDTO() {
	}

	public ColonneDTO(String code, String libelle, String type, int largeur) {
		this.code = code;
		this.libelle = libelle;
		this.type = type;
		this.largeur = largeur;
	}

	// getters / setters
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLargeur() {
		return largeur;
	}

	public void setLargeur(int largeur) {
		this.largeur = largeur;
	}
}