package entite;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_journal")
public class Journal implements Serializable {
	private static final long serialVersionUID = -3634648356510168303L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "jrnl_id")
	private int id;

	@Column(name = "reference")
	private String codeJrnl;

	@Column(name = "libelle")
	private String libelle;

	@Column(name = "cpt_contrepartie")
	private String contrePartie;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodeJrnl() {
		return this.codeJrnl;
	}

	public void setCodeJrnl(String codeJrnl) {
		this.codeJrnl = codeJrnl;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getContrePartie() {
		return contrePartie;
	}

	public void setContrePartie(String contrePartie) {
		this.contrePartie = contrePartie;
	}

}
