package enumeration;

public enum TypeLigne {
	TIT("Titre de section"),
    ST("Sous-titre"),
    RUB("Rubrique"),
    AGR("Agrégat"),
    TOT("Total"),
    SOL("Solde"),
    SEP("Séparateur"),
    CTRL("Contrôle");

    private final String libelle;

    TypeLigne(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}
