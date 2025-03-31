package entite;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
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
	@Column(name = "taux_taxe")
	private double tauxTaxe;
	@Column(name = "cours")
	private double coursDev;
	@Column(name = "piece")
	private String piece;
	@Column(name = "comment")
	private String commentaire;
	@Column(name = "montant")
	private double montant;
	@ManyToOne
	@JoinColumn(name = "centre_cout")
	private CentreCout centre;
	@Transient
	private String dateToPrint;
	@Transient
	private String amountToPrint;
	@Transient
	private double montantTTC;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNumOperation() {
		return this.numOperation;
	}

	public void setNumOperation(String numOperation) {
		this.numOperation = numOperation;
	}

	public Date getDateOperation() {
		return this.dateOperation;
	}

	public void setDateOperation(Date dateOperation) {
		this.dateOperation = dateOperation;
	}

	public int getIdExercise() {
		return this.idExercise;
	}

	public void setIdExercise(int idExercise) {
		this.idExercise = idExercise;
	}

	public TypeRecette getRecette() {
		return this.recette;
	}

	public void setRecette(TypeRecette recette) {
		this.recette = recette;
	}

	public int getModeReglement() {
		return this.modeReglement;
	}

	public void setModeReglement(int modeReglement) {
		this.modeReglement = modeReglement;
	}

	public Banque getBank() {
		return this.bank;
	}

	public void setBank(Banque bank) {
		this.bank = bank;
	}

	public BankAccount getCompteBank() {
		return this.compteBank;
	}

	public void setCompteBank(BankAccount compteBank) {
		this.compteBank = compteBank;
	}

	public Devise getDevise() {
		return this.devise;
	}

	public void setDevise(Devise devise) {
		this.devise = devise;
	}

	public Taxes getTaxe() {
		return this.taxe;
	}

	public void setTaxe(Taxes taxe) {
		this.taxe = taxe;
	}

	public double getTauxTaxe() {
		return this.tauxTaxe;
	}

	public void setTauxTaxe(double tauxTaxe) {
		this.tauxTaxe = tauxTaxe;
	}

	public double getCoursDev() {
		return this.coursDev;
	}

	public void setCoursDev(double coursDev) {
		this.coursDev = coursDev;
	}

	public String getPiece() {
		return this.piece;
	}

	public void setPiece(String piece) {
		this.piece = piece;
	}

	public String getCommentaire() {
		return this.commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public double getMontant() {
		return this.montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public CentreCout getCentre() {
		return this.centre;
	}

	public void setCentre(CentreCout centre) {
		this.centre = centre;
	}

	public String getDateToPrint() {
		this.dateToPrint = HelperC.convertDate(getDateOperation());
		return this.dateToPrint;
	}

	public void setDateToPrint(String dateToPrint) {
		this.dateToPrint = dateToPrint;
	}

	public String getAmountToPrint() {
		calculMontantTTC();
		this.amountToPrint = HelperC.decimalNumber(getMontantTTC(), 0, true);
		return this.amountToPrint;
	}

	public void setAmountToPrint(String amountToPrint) {
		this.amountToPrint = amountToPrint;
	}

	public double getMontantTTC() {
		return this.montantTTC;
	}

	public void setMontantTTC(double montantTTC) {
		this.montantTTC = montantTTC;
	}

	public TypeEcriture getTypeEntree() {
		return this.typeEntree;
	}

	public void setTypeEntree(TypeEcriture typeEntree) {
		this.typeEntree = typeEntree;
	}

	public Partenaire getPartener() {
		return partener;
	}

	public void setPartener(Partenaire partener) {
		this.partener = partener;
	}

	public void calculMontantTTC() {
		double ttc = 0.0D;
		if (getTauxTaxe() > 0.0D) {

			ttc = getMontant() * (1.0D + getTauxTaxe() / 100.0D);
			setMontantTTC(ttc);
		} else {

			setMontantTTC(getMontant());
		}
	}

	public void calculMontantHT() {
		double ttc = 0.0D;
		if (getTauxTaxe() > 0.0D) {

			ttc = getMontantTTC() / (1.0D + getTauxTaxe() / 100.0D);
			setMontant(ttc);
		} else {

			setMontant(getMontantTTC());
		}
	}
}
