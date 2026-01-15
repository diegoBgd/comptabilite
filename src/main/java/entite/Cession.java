package entite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import utils.HelperC;

@Entity
@Table(name = "tb_cession")
public class Cession implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2806085969340297131L;

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cession_id")
	private int id;

	@ManyToOne
	@JoinColumn(name = "id_immo")
	private Immobilise immo;
	
	@Column(name = "reference")
	private String codeCession;

	@Column(name = "comment")
	private String motif;

	@Column(name = "type_cession")
	@Enumerated(EnumType.STRING)
	private TypeCession typeCession;

	@Column(name = "date_cession")
	private Date dateCession;
	
	@Column(name = "montant_cession")
	private double montantCession;
	
	@Column(name = "montant_vnc")
	private double vnc;
	
	@Column(name = "compte_amrt")
	private String compteAmort;
	
	@Column(name = "numero_piece")
	private String piece;

	@Transient
	private String printDateCession;
	
	@Transient
	private String printVnc;
	
	@Transient
	private String printMontant;
	
	@Transient
	private List<Ecriture> listEcriture;
	 
	public Cession() {
		listEcriture=new ArrayList<Ecriture>();
	}
	
	public List<Ecriture> getListEcriture() {
		return listEcriture;
	}

	public void setListEcriture(List<Ecriture> listEcriture) {
		this.listEcriture = listEcriture;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Immobilise getImmo() {
		return immo;
	}

	public void setImmo(Immobilise immo) {
		this.immo = immo;
	}

	public TypeCession getTypeCession() {
		return typeCession;
	}

	public void setTypeCession(TypeCession typeCession) {
		this.typeCession = typeCession;
	}

	public Date getDateCession() {
		return dateCession;
	}

	public void setDateCession(Date dateCession) {
		this.dateCession = dateCession;
	}

	public double getMontantCession() {
		return montantCession;
	}

	public void setMontantCession(double montantCession) {
		this.montantCession = montantCession;
	}

	public double getVnc() {
		return vnc;
	}

	public void setVnc(double vnc) {
		this.vnc = vnc;
	}

	public String getCompteAmort() {
		return compteAmort;
	}

	public void setCompteAmort(String compteAmort) {
		this.compteAmort = compteAmort;
	}

	public String getPiece() {
		return piece;
	}

	public void setPiece(String piece) {
		this.piece = piece;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public String getPrintDateCession() {
		printDateCession=HelperC.convertDate(getDateCession());
		return printDateCession;
	}

	public void setPrintDateCession(String printDateCession) {
		this.printDateCession = printDateCession;
	}

	public String getPrintVnc() {
		
		return HelperC.decimalNumber(getVnc(), 0, true);
	}

	public void setPrintVnc(String printVnc) {
		this.printVnc = printVnc;
	}

	public String getPrintMontant() {
		return HelperC.decimalNumber(getMontantCession(), 0, true);
	}

	public void setPrintMontant(String printMontant) {
		this.printMontant = printMontant;
	}
	
	
}
