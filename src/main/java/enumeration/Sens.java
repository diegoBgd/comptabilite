package enumeration;

public enum Sens {
	   DEBIT("Débit"),
	    CREDIT("Crédit"),
	    DEBIT_CREDIT("Débit et Crédit");

	    private final String libelle;

	    Sens(String libelle) {
	        this.libelle = libelle;
	    }

	    public String getLibelle() {
	        return libelle;
	    }
}
