package entite;

import javax.persistence.*;

import enumeration.NatureValeur;
import enumeration.Sens;
import enumeration.TypeMouvement;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_mapping_comptes")
public class EtatFinanciersCompte implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4598128403009330336L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_mapping")
	private Long idMapping;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_rubrique", nullable = false)
	private EtatFinanciersRubrique rubrique;

	@Column(name = "libelle_regle", length = 200)
	private String libelleRegle;

	@Enumerated(EnumType.STRING)
	@Column(name = "type_mouvement", nullable = false, length = 20)
	private TypeMouvement typeMouvement;

	@Enumerated(EnumType.STRING)
	@Column(name = "nature_valeur", length = 20)
	private NatureValeur natureValeur = NatureValeur.NET;

	@Enumerated(EnumType.STRING)
	@Column(name = "sens", length = 20)
	private Sens sens;

	@Column(name = "coefficient")
	private BigDecimal coefficient = BigDecimal.ONE;

	@Column(name = "operateur_logique", length = 5)
	private String operateurLogique = "OU";

	@Column(name = "condition_sql", length = 500)
	private String conditionSql;

	@Column(name = "ordre")
	private Integer ordre = 0;

	@OneToMany(mappedBy = "mapping", cascade = CascadeType.ALL,
	           fetch = FetchType.LAZY, orphanRemoval = true)
	private List<MappingInclusion> inclusions = new ArrayList<>();

	@OneToMany(mappedBy = "mapping", cascade = CascadeType.ALL,
	           fetch = FetchType.LAZY, orphanRemoval = true)
	private List<MappingExclusion> exclusions = new ArrayList<>();

	// ---- Getters / Setters ----

	public Long getIdMapping() {
		return idMapping;
	}

	public void setIdMapping(Long idMapping) {
		this.idMapping = idMapping;
	}

	public EtatFinanciersRubrique getRubrique() {
		return rubrique;
	}

	public void setRubrique(EtatFinanciersRubrique rubrique) {
		this.rubrique = rubrique;
	}

	public String getLibelleRegle() {
		return libelleRegle;
	}

	public void setLibelleRegle(String libelleRegle) {
		this.libelleRegle = libelleRegle;
	}

	public TypeMouvement getTypeMouvement() {
		return typeMouvement;
	}

	public void setTypeMouvement(TypeMouvement typeMouvement) {
		this.typeMouvement = typeMouvement;
	}

	public NatureValeur getNatureValeur() {
		return natureValeur;
	}

	public void setNatureValeur(NatureValeur natureValeur) {
		this.natureValeur = natureValeur;
	}

	public Sens getSens() {
		return sens;
	}

	public void setSens(Sens sens) {
		this.sens = sens;
	}

	public BigDecimal getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(BigDecimal coefficient) {
		this.coefficient = coefficient;
	}

	public String getOperateurLogique() {
		return operateurLogique;
	}

	public void setOperateurLogique(String operateurLogique) {
		this.operateurLogique = operateurLogique;
	}

	public String getConditionSql() {
		return conditionSql;
	}

	public void setConditionSql(String conditionSql) {
		this.conditionSql = conditionSql;
	}

	public Integer getOrdre() {
		return ordre;
	}

	public void setOrdre(Integer ordre) {
		this.ordre = ordre;
	}

	public List<MappingInclusion> getInclusions() {
		return inclusions;
	}

	public void setInclusions(List<MappingInclusion> inclusions) {
		this.inclusions = inclusions;
	}

	public List<MappingExclusion> getExclusions() {
		return exclusions;
	}

	public void setExclusions(List<MappingExclusion> exclusions) {
		this.exclusions = exclusions;
	}
}
