package entite;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tb_service")
public class Service implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6300755790538290993L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serv_id")
	private int id;
	@Column(name = "reference")
	private String code;
	@Column(name = "libelle")
	private String libelle;
	@ManyToOne
	@JoinColumn(name = "departement")
	private Departement departement;

	public Service() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

}
