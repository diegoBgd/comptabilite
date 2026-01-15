package entite;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import utils.HelperC;

@Entity
@Table(name = "tb_encaissement")
public class Encaissement implements Serializable {

    private static final long serialVersionUID = -6359812495672782954L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enc_id")
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_operation")
    private Date dateOperation;

    @Column(name = "reference")
    private String numOperation;

    @Column(name = "exercice")
    private int idExercise;

    @Enumerated(EnumType.STRING)
    @Column(name = "type_entree")
    private TypeEcriture typeEntree;

    @ManyToOne
    @JoinColumn(name = "type_recette")
    private TypeRecette recette;

    @Column(name = "mode_reglement")
    private int modeReglement;

    @ManyToOne
    @JoinColumn(name = "banque")
    private Banque bank;

    @ManyToOne
    @JoinColumn(name = "client")
    private Partenaire partener;

    @ManyToOne
    @JoinColumn(name = "compte_bank")
    private BankAccount compteBank;

    @ManyToOne
    @JoinColumn(name = "devise")
    private Devise devise;

    @ManyToOne
    @JoinColumn(name = "taxe")
    private Taxes taxe;

    @Column(name = "taux_taxe", precision = 10, scale = 2)
    private BigDecimal tauxTaxe = BigDecimal.ZERO;

    @Column(name = "cours", precision = 18, scale = 4)
    private BigDecimal coursDev = BigDecimal.ONE;

    @Column(name = "piece")
    private String piece;

    @Column(name = "comment")
    private String commentaire;

    @Column(name = "montant", precision = 18, scale = 2)
    private BigDecimal montant = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "centre_cout")
    private CentreCout centre;

    @Transient
    private String dateToPrint;

    @Transient
    private String amountToPrint;

    @Transient
    private BigDecimal montantTTC = BigDecimal.ZERO;

    // --------------------
    // Getters & Setters
    // --------------------

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
 
    public String getNumOperation() { return numOperation; }

    public void setNumOperation(String numOperation) { this.numOperation = numOperation; }

    public Date getDateOperation() { return dateOperation; }

    public void setDateOperation(Date dateOperation) { this.dateOperation = dateOperation; }

    public int getIdExercise() { return idExercise; }

    public void setIdExercise(int idExercise) { this.idExercise = idExercise; }

    public TypeRecette getRecette() { return recette; }

    public void setRecette(TypeRecette recette) { this.recette = recette; }

    public int getModeReglement() { return modeReglement; }

    public void setModeReglement(int modeReglement) { this.modeReglement = modeReglement; }

    public Banque getBank() { return bank; }

    public void setBank(Banque bank) { this.bank = bank; }

    public BankAccount getCompteBank() { return compteBank; }

    public void setCompteBank(BankAccount compteBank) { this.compteBank = compteBank; }

    public Devise getDevise() { return devise; }

    public void setDevise(Devise devise) { this.devise = devise; }

    public Taxes getTaxe() { return taxe; }

    public void setTaxe(Taxes taxe) { this.taxe = taxe; }

    public BigDecimal getTauxTaxe() { return tauxTaxe; }

    public void setTauxTaxe(BigDecimal tauxTaxe) { this.tauxTaxe = tauxTaxe != null ? tauxTaxe : BigDecimal.ZERO; }

    public BigDecimal getCoursDev() { return coursDev; }

    public void setCoursDev(BigDecimal coursDev) { this.coursDev = coursDev != null ? coursDev : BigDecimal.ONE; }

    public String getPiece() { return piece; }

    public void setPiece(String piece) { this.piece = piece; }

    public String getCommentaire() { return commentaire; }

    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }

    public BigDecimal getMontant() { return montant; }

    public void setMontant(BigDecimal montant) { this.montant = montant != null ? montant : BigDecimal.ZERO; }

    public CentreCout getCentre() { return centre; }

    public void setCentre(CentreCout centre) { this.centre = centre; }

    public TypeEcriture getTypeEntree() { return typeEntree; }

    public void setTypeEntree(TypeEcriture typeEntree) { this.typeEntree = typeEntree; }

    public Partenaire getPartener() { return partener; }

    public void setPartener(Partenaire partener) { this.partener = partener; }

    // --------------------
    // Formattage affichage
    // --------------------

    public String getDateToPrint() {
        this.dateToPrint = HelperC.convertDate(getDateOperation());
        return this.dateToPrint;
    }

    public String getAmountToPrint() {
        calculMontantTTC();
        this.amountToPrint = HelperC.decimalNumber(getMontantTTC().doubleValue(), 0, true);
        return this.amountToPrint;
    }

    // --------------------
    // Calculs financiers
    // --------------------

    public void calculMontantTTC() {
        if (montant == null) montant = BigDecimal.ZERO;
        if (tauxTaxe == null) tauxTaxe = BigDecimal.ZERO;

        BigDecimal facteur = BigDecimal.ONE.add(tauxTaxe.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
        montantTTC = montant.multiply(facteur).setScale(2, RoundingMode.HALF_UP);
    }

    public void calculMontantHT() {
        if (montantTTC == null) montantTTC = BigDecimal.ZERO;
        if (tauxTaxe == null) tauxTaxe = BigDecimal.ZERO;

        BigDecimal diviseur = BigDecimal.ONE.add(tauxTaxe.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP));
        montant = montantTTC.divide(diviseur, 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getMontantTTC() {
        if (montantTTC == null || montantTTC.compareTo(BigDecimal.ZERO) == 0) {
            calculMontantTTC();
        }
        return montantTTC;
    }

    public BigDecimal getMontantTVA() {
        return getMontantTTC().subtract(getMontant()).setScale(2, RoundingMode.HALF_UP);
    }
}
