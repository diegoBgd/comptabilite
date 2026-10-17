package enumeration;

public enum ModeCalcul {
	DIRECT("Direct"),
    INDIRECT("Indirect");

    private final String libelle;

    ModeCalcul(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
