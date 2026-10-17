package enumeration;

public enum Alignement {
	GAUCHE("Gauche"), CENTRE("Centré"), DROITE("Droite");

	private final String libelle;

	Alignement(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
