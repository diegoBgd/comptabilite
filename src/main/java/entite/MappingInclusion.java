package entite;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_mapping_inclusions")
public class MappingInclusion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1288035615617768232L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_inclusion")
	private Long idInclusion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_mapping", nullable = false)
	private EtatFinanciersCompte mapping;

	@Column(name = "compte_debut", nullable = false, length = 20)
	private String compteDebut;

	@Column(name = "compte_fin", length = 20)
	private String compteFin;

	@Column(name = "type_plage", length = 20)
	private String typePlage = "PLAGE";

	// ---- Getters / Setters ----

	public Long getIdInclusion() {
		return idInclusion;
	}

	public void setIdInclusion(Long idInclusion) {
		this.idInclusion = idInclusion;
	}

	public EtatFinanciersCompte getMapping() {
		return mapping;
	}

	public void setMapping(EtatFinanciersCompte mapping) {
		this.mapping = mapping;
	}

	public String getCompteDebut() {
		return compteDebut;
	}

	public void setCompteDebut(String compteDebut) {
		this.compteDebut = compteDebut;
	}

	public String getCompteFin() {
		return compteFin;
	}

	public void setCompteFin(String compteFin) {
		this.compteFin = compteFin;
	}

	public String getTypePlage() {
		return typePlage;
	}

	public void setTypePlage(String typePlage) {
		this.typePlage = typePlage;
	}
}