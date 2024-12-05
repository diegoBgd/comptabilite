package entite;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_partenaire")

public class Partenaire implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4813870530155921540L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "part_id")
	private int id;

	@Column(name = "reference")
	private String codePartener;

	@Column(name = "libelle")
	private String nomination;

	@Column(name = "compte")
	private String codeCpbl;

	@Column(name = "nif")
	private String nif;

	@Column(name = "telephone_num")
	private String telephone;

	@Column(name = "e_mail")
	private String email;

	@Column(name = "type_partener")
	@Enumerated(EnumType.ORDINAL)
	private TypePartener pType;

	public Partenaire() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodePartener() {
		return codePartener;
	}

	public void setCodePartener(String codePartener) {
		this.codePartener = codePartener;
	}

	public String getNomination() {
		return nomination;
	}

	public void setNomination(String nomination) {
		this.nomination = nomination;
	}

	public String getCodeCpbl() {
		return codeCpbl;
	}

	public void setCodeCpbl(String codeCpbl) {
		this.codeCpbl = codeCpbl;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TypePartener getpType() {
		return pType;
	}

	public void setpType(TypePartener pType) {
		this.pType = pType;
	}

}
