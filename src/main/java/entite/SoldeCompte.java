package entite;

import utils.HelperC;

public class SoldeCompte {

	private String code,libelle,printDb,printCd,printSD,printSC;
	private double debit,credit,soldeCdt,soldeDbt;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public double getDebit() {
		return debit;
	}
	public void setDebit(double debit) {
		this.debit = debit;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public double getSoldeCdt() {
		return soldeCdt;
	}
	public void setSoldeCdt(double soldeCdt) {
		this.soldeCdt = soldeCdt;
	}
	public double getSoldeDbt() {
		return soldeDbt;
	}
	public void setSoldeDbt(double soldeDbt) {
		this.soldeDbt = soldeDbt;
	}
	public String getPrintDb() {
		printDb=HelperC.decimalNumber(getDebit(), 0, true);
		return printDb;
	}
	public void setPrintDb(String printDb) {
		this.printDb = printDb;
	}
	public String getPrintCd() {
		printCd=HelperC.decimalNumber(getCredit(), 0, true);
		return printCd;
	}
	public void setPrintCd(String printCd) {
		this.printCd = printCd;
	}
	public String getPrintSD() {
		printSD=HelperC.decimalNumber(getSoldeDbt(), 0, true);
		return printSD;
	}
	public void setPrintSD(String printSD) {
		
		this.printSD = printSD;
	}
	public String getPrintSC() {
		printSC=HelperC.decimalNumber(getSoldeCdt(), 0, true);
		return printSC;
	}
	public void setPrintSC(String printSC) {
		this.printSC = printSC;
	}
	
}
