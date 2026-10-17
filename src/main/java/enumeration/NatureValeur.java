package enumeration;

public enum NatureValeur {
	BRUT("Brut"),
    AMORT("Amortissement"),
    NET("Net");

    private final String libelle;

    NatureValeur(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
