package enumeration;

public enum TypeMouvement {
	SOLDE("Solde"), VARIATION("Variation"), FLUX("Flux");

	private final String libelle;

	TypeMouvement(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelle() {
		return libelle;
	}
}
