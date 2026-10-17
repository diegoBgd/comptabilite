package enumeration;

public enum TypeEtat {
	STOCK("BILAN"), 
	RESULTAT("COMPTE RESULTAT"),
	FLUX("TABLEAUX DE FLUX DE TRESORERIE");

	private final String libelle;

	TypeEtat(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
