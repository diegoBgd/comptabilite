package entite;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tb_mapping_exclusions")
public class MappingExclusion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 865624890833182391L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_exclusion")
	private Long idExclusion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_mapping", nullable = false)
	private EtatFinanciersCompte mapping;

	@Column(name = "compte_debut", nullable = false, length = 20)
	private String compteDebut;

	@Column(name = "compte_fin", length = 20)
	private String compteFin;

	@Column(name = "type_plage", length = 20)
	private String typePlage = "PLAGE";

	@Column(name = "motif", length = 200)
	private String motif;

	// ---- Getters / Setters ----

	public Long getIdExclusion() {
		return idExclusion;
	}

	public void setIdExclusion(Long idExclusion) {
		this.idExclusion = idExclusion;
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

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}
}