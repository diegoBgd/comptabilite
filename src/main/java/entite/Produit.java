 package entite;
 
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.List;
 import javax.persistence.CascadeType;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.FetchType;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.JoinColumn;
 import javax.persistence.ManyToOne;
 import javax.persistence.OneToMany;
 import javax.persistence.Table;
 import javax.persistence.Transient;
 import org.hibernate.annotations.Fetch;
 import org.hibernate.annotations.FetchMode;
  
 
 @Entity
 @Table(name = "tb_produit")
 public class Produit
   implements Serializable
 {
   private static final long serialVersionUID = -8714641714293171328L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "prod_id")
   private int id;
   @Column(name = "reference")
   private String codePrd;
   @Column(name = "libelle")
   private String libelle;
   @Column(name = "codebare")
   private String codeBar;
   @Column(name = "unite")
   private String unite;
   @Column(name = "nbre_unite")
   private int nombreUnite;
   @Column(name = "gerer_stock")
   private boolean gererStock;
   @Column(name = "prix_revient")
   private double prixUnitaire;
   @Column(name = "marge")
   private double marge;
   @Column(name = "taux_marge")
   private double tauxMarge;
   @Column(name = "prix_vente_ht")
   private double pvHt;
   @Column(name = "prix_vente_ttc")
   private double pvTTC;
   @Column(name = "taux_taxe")
   private double tauxTaxe;
   @Column(name = "compte_vente")
   private String compteVente;
   @Column(name = "compte_stock")
   private String compteStock;
   @Column(name = "compte_sortie")
   private String compteSortie;
   @ManyToOne
   @JoinColumn(name = "sous_famille")
   private SoufamilleProd sfamille;
   @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER, orphanRemoval = true)
   @JoinColumn(name = "id_prod")
   @Fetch(FetchMode.SELECT)
   private List<ProduitTaxe> listTaxes;
   @Transient
   private double pvHtva;
   @Transient
   private double pvTvac;
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public String getCodePrd() {
     return this.codePrd;
   }
   
   public void setCodePrd(String codePrd) {
     this.codePrd = codePrd;
   }
   
   public String getLibelle() {
     return this.libelle;
   }
   
   public void setLibelle(String libelle) {
     this.libelle = libelle;
   }
   
   public String getCodeBar() {
     return this.codeBar;
   }
   
   public void setCodeBar(String codeBar) {
     this.codeBar = codeBar;
   }
   
   public String getUnite() {
     return this.unite;
   }
   
   public void setUnite(String unite) {
     this.unite = unite;
   }
   
   public int getNombreUnite() {
     return this.nombreUnite;
   }
   
   public void setNombreUnite(int nombreUnite) {
     this.nombreUnite = nombreUnite;
   }
   
   public boolean isGererStock() {
     return this.gererStock;
   }
   
   public void setGererStock(boolean gererStock) {
     this.gererStock = gererStock;
   }
   
   public double getPrixUnitaire() {
     return this.prixUnitaire;
   }
   
   public void setPrixUnitaire(double prixUnitaire) {
     this.prixUnitaire = prixUnitaire;
   }
   
   public double getMarge() {
     return this.marge;
   }
   
   public void setMarge(double marge) {
     this.marge = marge;
   }
   
   public double getTauxMarge() {
     return this.tauxMarge;
   }
   
   public void setTauxMarge(double tauxMarge) {
     this.tauxMarge = tauxMarge;
   }
   
   public double getPvHt() {
     return this.pvHt;
   }
   
   public void setPvHt(double pvHt) {
     this.pvHt = pvHt;
   }
   
   public double getTauxTaxe() {
     return this.tauxTaxe;
   }
   
   public void setTauxTaxe(double tauxTaxe) {
     this.tauxTaxe = tauxTaxe;
   }
   
   public double getPvTTC() {
     return this.pvTTC;
   }
   
   public void setPvTTC(double pvTTC) {
     this.pvTTC = pvTTC;
   }
   
   public String getCompteVente() {
     return this.compteVente;
   }
   
   public void setCompteVente(String compteVente) {
     this.compteVente = compteVente;
   }
   
   public String getCompteStock() {
     return this.compteStock;
   }
   
   public void setCompteStock(String compteStock) {
     this.compteStock = compteStock;
   }
   
   public String getCompteSortie() {
     return this.compteSortie;
   }
   
   public void setCompteSortie(String compteSortie) {
     this.compteSortie = compteSortie;
   }
   
   public SoufamilleProd getSfamille() {
     return this.sfamille;
   }
   
   public void setSfamille(SoufamilleProd sfamille) {
     this.sfamille = sfamille;
   }
   public double getPvHtva() {
     return this.pvHtva;
   }
   
   public void setPvHtva(double pvHtva) {
     this.pvHtva = pvHtva;
   }
   
   public double getPvTvac() {
     return this.pvTvac;
   }
   
   public void setPvTvac(double pvTvac) {
     this.pvTvac = pvTvac;
   }
   public List<ProduitTaxe> getListTaxes() {
     return this.listTaxes;
   }
   
   public void setListTaxes(List<ProduitTaxe> listTaxes) {
     this.listTaxes = listTaxes;
   }
   
   public void calculMarge() {
     if (getPrixUnitaire() > 0.0D) {
       setMarge(getPrixUnitaire() * getTauxMarge() / 100.0D);
       setPvHt(getPrixUnitaire() + getMarge());
     } 
   }
 
 
   
   public void calculTauxMarge() {
     if (getPrixUnitaire() > 0.0D) {
 
       
       setTauxMarge(getMarge() * 100.0D / getPrixUnitaire());
       setPvHt(getPrixUnitaire() + getMarge());
     } 
   }
 
   
   public void calculPVTT() {
     double tc = 0.0D, tva = 0.0D, pf = 0.0D;
     if (getListTaxes().size() > 0)
     {
       for (ProduitTaxe pt : getListTaxes()) {
         switch (pt.getTaxe().getType()) {
           case 0:
             tc = getPvHt() * pt.getTaux() / 100.0D;
             pt.setValeurTaxe(tc);
 
           
           case 1:
             setPvHtva(getPvHt() + tc);
             tva = getPvHtva() * pt.getTaux() / 100.0D;
             pt.setValeurTaxe(tva);
           
           case 2:
             pf = getPvHt() * pt.getTaux() / 100.0D;
             pt.setValeurTaxe(pf);
         } 
       
       } 
     }
     setPvTvac(getPvHtva() + tva);
     setPvTTC(getPvTvac() + pf);
   }
 
   
   public Produit() {
     this.listTaxes = new ArrayList<>();
   }
 }


