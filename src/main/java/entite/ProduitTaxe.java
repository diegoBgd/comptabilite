package entite;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

@Entity
@Table(name = "tb_produit_taxe")
public class ProduitTaxe implements Serializable {

    private static final long serialVersionUID = -5270737652631871279L;

    /** Clé primaire auto-incrémentée correspondant à pt_id */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pt_id")
    private Long id;

    /** Produit associé */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    /** Taxe associée */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taxe_id", nullable = false)
    private Taxes taxe;

    /** Taux de la taxe */
    @Column(name = "taux", precision = 10, scale = 2, nullable = false)
    private BigDecimal taux;

    /** Valeur calculée de la taxe */
    @Transient
    private BigDecimal valeurTaxe;

    // =========================
    // ===== GETTERS / SETTERS =====
    // =========================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Taxes getTaxe() {
        return taxe;
    }

    public void setTaxe(Taxes taxe) {
        this.taxe = taxe;
    }

    public BigDecimal getTaux() {
        return taux;
    }

    public void setTaux(BigDecimal taux) {
        this.taux = taux;
    }

    public BigDecimal getValeurTaxe() {
    	  BigDecimal valeur=getTaux().divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
    	valeurTaxe=getProduit().getPvHt().multiply(valeur);
        return valeurTaxe;
    }

   
}
