package entite;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import utils.HelperC;

@Entity
@Table(name = "tb_ecriture")
public class Ecriture implements Serializable {
	private static final long serialVersionUID = 5888044904252459148L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "operation_id")
	private int id;
	@Column(name = "piece")
	private String pieceCpb;
	@Column(name = "libelle_operation")
	private String libelle;
	@Column(name = "compte")
	private String cpte;
	@Column(name = "journal")
	private String jrnl;
	@Temporal(TemporalType.DATE)
	@Column(name = "date_operation")
	private Date dateOperation;
	@Column(name = "exerice_id")
	private int idExercise;
	@Column(name = "type_operation")
	@Enumerated(EnumType.STRING)
	private TypeEcriture typeOperation;
	@Column(name = "source_operation")
	private int sourceOperation;
	@Column(name = "debit")
	private double debit;
	@Column(name = "credit")
	private double credit;
	
	@Transient
	private String printDate;
	@Transient
	private String printDebit;
	@Transient
	private String printCredit;
	@Transient
	private int orderIndex;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPieceCpb() {
		return this.pieceCpb;
	}

	public void setPieceCpb(String pieceCpb) {
		this.pieceCpb = pieceCpb;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getCpte() {
		return this.cpte;
	}

	public void setCpte(String cpte) {
		this.cpte = cpte;
	}

	public String getJrnl() {
		return this.jrnl;
	}

	public void setJrnl(String jrnl) {
		this.jrnl = jrnl;
	}

	public Date getDateOperation() {
		return this.dateOperation;
	}

	public void setDateOperation(Date dateOperation) {
		this.dateOperation = dateOperation;
	}

	public TypeEcriture getTypeOperation() {
		return this.typeOperation;
	}

	public void setTypeOperation(TypeEcriture typeOperation) {
		this.typeOperation = typeOperation;
	}

	public int getSourceOperation() {
		return this.sourceOperation;
	}

	public void setSourceOperation(int sourceOperation) {
		this.sourceOperation = sourceOperation;
	}

	public double getDebit() {
		return this.debit;
	}

	public void setDebit(double debit) {
		this.debit = debit;
	}

	public double getCredit() {
		return this.credit;
	}

	public void setCredit(double credit) {
		this.credit = credit;
	}

	public int getIdExercise() {
		return this.idExercise;
	}

	public void setIdExercise(int idExercise) {
		this.idExercise = idExercise;
	}

	public String getPrintDate() {
		this.printDate = HelperC.convertDate(getDateOperation());
		return this.printDate;
	}

	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	public String getPrintDebit() {
		this.printDebit = HelperC.decimalNumber(getDebit(), 0, true);
		return this.printDebit;
	}

	public void setPrintDebit(String printDebit) {
		this.printDebit = printDebit;
	}

	public String getPrintCredit() {
		this.printCredit = HelperC.decimalNumber(getCredit(), 0, true);
		return this.printCredit;
	}

	public void setPrintCredit(String printCredit) {
		this.printCredit = printCredit;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

}
