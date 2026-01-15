package entite;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import javax.persistence.*;

import utils.HelperC;

@Entity
@Table(name = "tb_reglement_client")
public class ReglementClient implements Serializable {

    private static final long serialVersionUID = 5424995601076332786L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reg")
    private int id;

    @Column(name = "date_reglement")
    @Temporal(TemporalType.DATE)
    private Date dateReglement;

    @Column(name = "mode_reglement")
    private int modeReglement;

    @ManyToOne
    @JoinColumn(name = "encaismt")
    private Encaissement encaissement;

    @Column(name = "montant", precision = 19, scale = 4)
    private BigDecimal montantRegle;

    @ManyToOne
    @JoinColumn(name = "devise")
    private Devise deviseRgl;

    @Column(name = "cours", precision = 19, scale = 4)
    private BigDecimal coursRgl;

    @Column(name = "exercice")
    private int idExercise;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "taxe")
    private Taxes taxeRgl;

    @Column(name = "taux", precision = 10, scale = 4)
    private BigDecimal taux;

    @Column(name = "num_piece")
    private String piece;

    @ManyToOne
    @JoinColumn(name = "id_caisse")
    private Banque caisse;

    @ManyToOne
    @JoinColumn(name = "id_compte")
    private BankAccount account;

    @Transient
    private String amountToPrint;

    @Transient
    private String dateToPrint;

    @Transient
    private BigDecimal montantTTC;

    // ---------------- GETTERS & SETTERS ----------------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateReglement() {
        return dateReglement;
    }

    public void setDateReglement(Date dateReglement) {
        this.dateReglement = dateReglement;
    }

    public int getModeReglement() {
        return modeReglement;
    }

    public void setModeReglement(int modeReglement) {
        this.modeReglement = modeReglement;
    }

    public Encaissement getEncaissement() {
        return encaissement;
    }

    public void setEncaissement(Encaissement encaissement) {
        this.encaissement = encaissement;
    }

    public BigDecimal getMontantRegle() {
        return montantRegle;
    }

    public void setMontantRegle(BigDecimal montantRegle) {
        this.montantRegle = montantRegle;
    }

    public Devise getDeviseRgl() {
        return deviseRgl;
    }

    public void setDeviseRgl(Devise deviseRgl) {
        this.deviseRgl = deviseRgl;
    }

    public BigDecimal getCoursRgl() {
        return coursRgl;
    }

    public void setCoursRgl(BigDecimal coursRgl) {
        this.coursRgl = coursRgl;
    }

    public int getIdExercise() {
        return idExercise;
    }

    public void setIdExercise(int idExercise) {
        this.idExercise = idExercise;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Taxes getTaxeRgl() {
        return taxeRgl;
    }

    public void setTaxeRgl(Taxes taxeRgl) {
        this.taxeRgl = taxeRgl;
    }

    public BigDecimal getTaux() {
        return taux;
    }

    public void setTaux(BigDecimal taux) {
        this.taux = taux;
    }

    public String getPiece() {
        return piece;
    }

    public void setPiece(String piece) {
        this.piece = piece;
    }

    public Banque getCaisse() {
        return caisse;
    }

    public void setCaisse(Banque caisse) {
        this.caisse = caisse;
    }

    public BankAccount getAccount() {
        return account;
    }

    public void setAccount(BankAccount account) {
        this.account = account;
    }

    public String getAmountToPrint() {
        calculMontantTTC();
        this.amountToPrint = HelperC.decimalNumber(getMontantTTC(), 0, true);
        return this.amountToPrint;
    }

    public void setAmountToPrint(String amountToPrint) {
        this.amountToPrint = amountToPrint;
    }

    public String getDateToPrint() {
        this.dateToPrint = HelperC.convertDate(getDateReglement());
        return this.dateToPrint;
    }

    public void setDateToPrint(String dateToPrint) {
        this.dateToPrint = dateToPrint;
    }

    public BigDecimal getMontantTTC() {
        return montantTTC;
    }

    public void setMontantTTC(BigDecimal montantTTC) {
        this.montantTTC = montantTTC;
    }

    // ---------------- MÉTHODES DE CALCUL ----------------

    public void calculMontantTTC() {
        if (montantRegle == null) montantRegle = BigDecimal.ZERO;
        if (taux == null) taux = BigDecimal.ZERO;

        BigDecimal ttc = montantRegle;

        if (taux.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal facteur = BigDecimal.ONE.add(taux.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP));
            ttc = montantRegle.multiply(facteur).setScale(2, RoundingMode.HALF_UP);
        }

        setMontantTTC(ttc);
    }

    public void calculMontantHT() {
        if (montantTTC == null) montantTTC = BigDecimal.ZERO;
        if (taux == null) taux = BigDecimal.ZERO;

        BigDecimal ht = montantTTC;

        if (taux.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal facteur = BigDecimal.ONE.add(taux.divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP));
            ht = montantTTC.divide(facteur, 2, RoundingMode.HALF_UP);
        }

        setMontantRegle(ht);
    }
}
