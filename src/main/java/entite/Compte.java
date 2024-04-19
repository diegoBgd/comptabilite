package entite;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_plan_compte")
public class Compte implements Serializable {
	private static final long serialVersionUID = 4138472418057064895L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cpt_id")
	private int id;
	@Column(name = "reference")
	private String compteCod;
	@Column(name = "libelle")
	private String libelle;
	@Column(name = "nature")
	private String nature;

	@Column(name = "detail")
	private boolean compteDetail=false;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompteCod() {
		return this.compteCod;
	}

	public void setCompteCod(String compteCod) {
		this.compteCod = compteCod;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getNature() {
		return this.nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public boolean isCompteDetail() {
		return compteDetail;
	}

	public void setCompteDetail(boolean compteDetail) {
		this.compteDetail = compteDetail;
	}

}
