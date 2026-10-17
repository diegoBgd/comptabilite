package enumeration;

public enum TypeBordure {
	AUCUNE("Aucune"), SIMPLE("Simple"), DOUBLE("Double");

	private final String libelle;

	TypeBordure(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
