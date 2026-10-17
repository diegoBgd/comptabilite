package enumeration;

public enum TypeValeur {
	   NET("Net"),
	    BRUT("Brut"),
	    AMORT("Amortissement");

	    private final String libelle;

	    TypeValeur(String libelle) {
	        this.libelle = libelle;
	    }

	    public String getLibelle() {
	        return libelle;
	    }
}
