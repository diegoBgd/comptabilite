package enumeration;

public enum TypeCalcul {
	 DIRECT("Direct"),
	    AGREGE("Agrégé"),
	    FORMULE("Formule"),
	    SAISIE("Saisie manuelle");

	    private final String libelle;

	    TypeCalcul(String libelle) {
	        this.libelle = libelle;
	    }

	    public String getLibelle() {
	        return libelle;
	    }
}
