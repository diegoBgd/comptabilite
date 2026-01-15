package entite;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;



@Entity
@Table(name = "tb_produit")
public class Produit implements Serializable {

    private static final long serialVersionUID = -8714641714293171328L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private Long id;

    @Column(name = "reference", nullable = false, unique = true)
    private String codePrd;

    @Column(name = "libelle", nullable = false)
    private String libelle;

    @Column(name = "codebare")
    private String codeBar;

    @Column(name = "unite")
    private String unite;

    @Column(name = "nbre_unite")
    private int nombreUnite;

    @Column(name = "gerer_stock")
    private boolean gererStock;

    @Column(name = "prix_revient", precision = 15, scale = 2)
    private BigDecimal prixUnitaire = BigDecimal.ZERO;

    @Column(name = "marge", precision = 15, scale = 2)
    private BigDecimal marge = BigDecimal.ZERO;

    @Column(name = "taux_marge", precision = 6, scale = 2)
    private BigDecimal tauxMarge = BigDecimal.ZERO;

    @Column(name = "prix_vente_ht", precision = 15, scale = 2)
    private BigDecimal pvHt = BigDecimal.ZERO;

    @Column(name = "prix_vente_ttc", precision = 15, scale = 2)
    private BigDecimal pvTTC = BigDecimal.ZERO;

    @Column(name = "compte_vente")
    private String compteVente;

    @Column(name = "compte_stock")
    private String compteStock;

    @Column(name = "compte_sortie")
    private String compteSortie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sous_famille")
    private SoufamilleProd sfamille;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProduitTaxe> listTaxes = new ArrayList<>();
    
    @Transient
    private BigDecimal totalTaxe = BigDecimal.ZERO;

    public Produit() {}

  

    // =========================
    // === MÉTHODES MÉTIER  ===
    // =========================

   
    
    public void calculMarge() {
        if (prixUnitaire != null && tauxMarge != null) {
            marge = prixUnitaire.multiply(tauxMarge).divide(BigDecimal.valueOf(100));
            pvHt = prixUnitaire.add(marge);
        }
    }

    public void calculTauxMarge() {
        if (prixUnitaire != null && prixUnitaire.compareTo(BigDecimal.ZERO) > 0) {
            tauxMarge = marge.multiply(BigDecimal.valueOf(100))
                    .divide(prixUnitaire, 2, BigDecimal.ROUND_HALF_UP);
            pvHt = prixUnitaire.add(marge);
        }
    }

    public void calculPVTT() {
        if (pvHt == null) pvHt = BigDecimal.ZERO;
        totalTaxe = BigDecimal.ZERO;

        for (ProduitTaxe pt : listTaxes) {
            if (pt.getTaux() != null) {
                totalTaxe = totalTaxe.add(pt.getValeurTaxe());
            }
        }
        pvTTC = pvHt.add(totalTaxe);
    }

    // =========================
    // === GETTERS/SETTERS  ===
    // =========================

    // ... (tes getters et setters inchangés)



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodePrd() {
        return codePrd;
    }

    public void setCodePrd(String codePrd) {
        this.codePrd = codePrd;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCodeBar() {
        return codeBar;
    }

    public void setCodeBar(String codeBar) {
        this.codeBar = codeBar;
    }

    public String getUnite() {
        return unite;
    }

    public void setUnite(String unite) {
        this.unite = unite;
    }

    public int getNombreUnite() {
        return nombreUnite;
    }

    public void setNombreUnite(int nombreUnite) {
        this.nombreUnite = nombreUnite;
    }

    public boolean isGererStock() {
        return gererStock;
    }

    public void setGererStock(boolean gererStock) {
        this.gererStock = gererStock;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public BigDecimal getMarge() {
        return marge;
    }

    public void setMarge(BigDecimal marge) {
        this.marge = marge;
    }

    public BigDecimal getTauxMarge() {
        return tauxMarge;
    }

    public void setTauxMarge(BigDecimal tauxMarge) {
        this.tauxMarge = tauxMarge;
    }

    public BigDecimal getPvHt() {
        return pvHt;
    }

    public void setPvHt(BigDecimal pvHt) {
        this.pvHt = pvHt;
    }

    public BigDecimal getPvTTC() {
        return pvTTC;
    }

    public void setPvTTC(BigDecimal pvTTC) {
        this.pvTTC = pvTTC;
    }

    public String getCompteVente() {
        return compteVente;
    }

    public void setCompteVente(String compteVente) {
        this.compteVente = compteVente;
    }

    public String getCompteStock() {
        return compteStock;
    }

    public void setCompteStock(String compteStock) {
        this.compteStock = compteStock;
    }

    public String getCompteSortie() {
        return compteSortie;
    }

    public void setCompteSortie(String compteSortie) {
        this.compteSortie = compteSortie;
    }

    public SoufamilleProd getSfamille() {
        return sfamille;
    }

    public void setSfamille(SoufamilleProd sfamille) {
        this.sfamille = sfamille;
    }

    public List<ProduitTaxe> getListTaxes() {
        return listTaxes;
    }

    public void setListTaxes(List<ProduitTaxe> listTaxes) {
        this.listTaxes = listTaxes;
    }

    public BigDecimal getTotalTaxe() {
        return totalTaxe;
    }

    public void setTotalTaxe(BigDecimal totalTaxe) {
        this.totalTaxe = totalTaxe;
    }
}
