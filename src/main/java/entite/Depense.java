package entite;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import javax.persistence.*;

import utils.HelperC;

@Entity
@Table(name = "tb_depense")
public class Depense implements Serializable {
	private static final long serialVersionUID = -7019025387429924200L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dep_id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "type_charge")
	private TypeCharge charge;

	@Column(name = "montant", precision = 18, scale = 2)
	private BigDecimal montant = BigDecimal.ZERO;

	@ManyToOne
	@JoinColumn(name = "taxe")
	private Taxes taxe;

	@Column(name = "taux", precision = 10, scale = 2)
	private BigDecimal tauxTaxe = BigDecimal.ZERO;

	@ManyToOne
	@JoinColumn(name = "fournisseur")
	private Partenaire partener;

	@ManyToOne
	@JoinColumn(name = "devise")
	private Devise devise;

	@Column(name = "cours", precision = 18, scale = 4)
	private BigDecimal coursDev = BigDecimal.ONE;

	@Column(name = "libelle")
	private String libelle;

	@Column(name = "exercice")
	private int idExercise;

	@Enumerated(EnumType.STRING)
	@Column(name = "type_depense")
	private TypeEcriture typeOperation;

	@ManyToOne
	@JoinColumn(name = "centre_cout")
	private CentreCout centre;

	@Column(name = "date_operation")
	@Temporal(TemporalType.DATE)
	private Date dateOperation;

	@Column(name = "num_piece")
	private String piece;

	@Column(name = "mode_reglement")
	private int modeReglement;

	@Transient
	private String dateToPrint;

	@Transient
	private String amountToPrint;

	@Transient
	private BigDecimal montantTTC = BigDecimal.ZERO;

	@Transient
	private BigDecimal montantTotRegle = BigDecimal.ZERO;

	// --- Getters / Setters ---
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TypeCharge getCharge() {
		return charge;
	}

	public void setCharge(TypeCharge charge) {
		this.charge = charge;
	}

	public BigDecimal getMontant() {
		return montant;
	}

	public void setMontant(BigDecimal montant) {
		this.montant = montant;
	}

	public Taxes getTaxe() {
		return taxe;
	}

	public void setTaxe(Taxes taxe) {
		this.taxe = taxe;
	}

	public BigDecimal getTauxTaxe() {
		return tauxTaxe;
	}

	public void setTauxTaxe(BigDecimal tauxTaxe) {
		this.tauxTaxe = tauxTaxe;
	}

	public Partenaire getPartener() {
		return partener;
	}

	public void setPartener(Partenaire partener) {
		this.partener = partener;
	}

	public Devise getDevise() {
		return devise;
	}

	public void setDevise(Devise devise) {
		this.devise = devise;
	}

	public BigDecimal getCoursDev() {
		return coursDev;
	}

	public void setCoursDev(BigDecimal coursDev) {
		this.coursDev = coursDev;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public int getIdExercise() {
		return idExercise;
	}

	public void setIdExercise(int idExercise) {
		this.idExercise = idExercise;
	}

	public CentreCout getCentre() {
		return centre;
	}

	public void setCentre(CentreCout centre) {
		this.centre = centre;
	}

	public Date getDateOperation() {
		return dateOperation;
	}

	public void setDateOperation(Date dateOperation) {
		this.dateOperation = dateOperation;
	}

	public String getPiece() {
		return piece;
	}

	public void setPiece(String piece) {
		this.piece = piece;
	}

	public int getModeReglement() {
		return modeReglement;
	}

	public void setModeReglement(int modeReglement) {
		this.modeReglement = modeReglement;
	}

	public TypeEcriture getTypeOperation() {
		return typeOperation;
	}

	public void setTypeOperation(TypeEcriture typeOperation) {
		this.typeOperation = typeOperation;
	}

	// --- Champs calculés et formatés ---
	public String getDateToPrint() {
		this.dateToPrint = HelperC.convertDate(getDateOperation());
		return this.dateToPrint;
	}

	public String getAmountToPrint() {
		calculMontantTTC();
		this.amountToPrint = HelperC.decimalNumber(getMontantTTC(), 0, true);
		return this.amountToPrint;
	}

	// --- Calculs financiers ---
	public void calculMontantTTC() {
		if (tauxTaxe != null && tauxTaxe.compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal multiplier = tauxTaxe.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)
					.add(BigDecimal.ONE);
			montantTTC = montant.multiply(multiplier).setScale(2, RoundingMode.HALF_UP);
		} else {
			montantTTC = montant;
		}
	}

	public void calculMontantHT() {
		if (tauxTaxe != null && tauxTaxe.compareTo(BigDecimal.ZERO) > 0) {
			BigDecimal divisor = tauxTaxe.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP).add(BigDecimal.ONE);
			montant = montantTTC.divide(divisor, 2, RoundingMode.HALF_UP);
		} else {
			montant = montantTTC;
		}
	}

	public BigDecimal getMontantTTC() {
		return montantTTC;
	}

	public void setMontantTTC(BigDecimal montantTTC) {
		this.montantTTC = montantTTC;
	}

	public BigDecimal getMontantTotRegle() {
		return montantTotRegle;
	}

	public void setMontantTotRegle(BigDecimal montantTotRegle) {
		this.montantTotRegle = montantTotRegle;
	}
}
