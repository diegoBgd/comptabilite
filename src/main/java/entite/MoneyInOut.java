package entite;

import java.io.Serializable;

public class MoneyInOut implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = -4244850541276881242L;
	private String printDate;
	private String comment;
	private double motantTTC;
	private double montantTaxe;
	private String reference;
	private String tiers;
	private String refTiers;
	private String printTTC;
	private String printTaxe;
	private String printHT;
	private String printIn;
	private String printOut;
	private String piece;
	private String paiment;
	
	public MoneyInOut() {

	}

	public String getPrintDate() {
		return printDate;
	}

	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public double getMotantTTC() {
		return motantTTC;
	}

	public void setMotantTTC(double motantTTC) {
		this.motantTTC = motantTTC;
	}

	public double getMontantTaxe() {
		return montantTaxe;
	}

	public void setMontantTaxe(double montantTaxe) {
		this.montantTaxe = montantTaxe;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getTiers() {
		return tiers;
	}

	public void setTiers(String tiers) {
		this.tiers = tiers;
	}

	public String getRefTiers() {
		return refTiers;
	}

	public void setRefTiers(String refTiers) {
		this.refTiers = refTiers;
	}

	public String getPrintTTC() {
		return printTTC;
	}

	public void setPrintTTC(String printTTC) {
		this.printTTC = printTTC;
	}

	public String getPrintTaxe() {
		return printTaxe;
	}

	public void setPrintTaxe(String printTaxe) {
		this.printTaxe = printTaxe;
	}

	public String getPrintHT() {
		return printHT;
	}

	public void setPrintHT(String printHT) {
		this.printHT = printHT;
	}

	public String getPrintIn() {
		return printIn;
	}

	public void setPrintIn(String printIn) {
		this.printIn = printIn;
	}

	public String getPrintOut() {
		return printOut;
	}

	public void setPrintOut(String printOut) {
		this.printOut = printOut;
	}

	public String getPiece() {
		return piece;
	}

	public void setPiece(String piece) {
		this.piece = piece;
	}

	public String getPaiment() {
		return paiment;
	}

	public void setPaiment(String paiment) {
		this.paiment = paiment;
	}

}
