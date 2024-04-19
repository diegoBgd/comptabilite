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
 import javax.persistence.Transient;
 import utils.HelperC;
 
  
 
 
 
 @Entity
 @Table(name = "tb_facturation_detail")
 public class FactureDetail
   implements Serializable
 {
   private static final long serialVersionUID = -5500243495833564909L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "det_id")
   private int id;
   @ManyToOne
   @JoinColumn(name = "produit")
   private Produit product;
   @ManyToOne
   @JoinColumn(name = "id_facture")
   private Facture invoice;
   @Column(name = "prix_vente")
   private double prixUnitaire;
   @Column(name = "montant_taxe")
   private double montantTaxe;
   @Column(name = "remise_prct")
   private double tauxRemise;
   @Column(name = "remise_forfait")
   private double remiseForfait;
   @Column(name = "quantite")
   private double quantity;
   @Column(name = "type_base")
   private int typeBase;
   @Transient
   private double montantHT;
   @Transient
   private double montantTTC;
   @Transient
   private String prodCod;
   @Transient
   private String prodName;
   @Transient
   private String printPu;
   @Transient
   private String printTotHT;
   @Transient
   private String printTotTTC;
   @Transient
   private String printTotTxe;
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public Produit getProduct() {
     return this.product;
   }
   
   public void setProduct(Produit product) {
     this.product = product;
   }
   
   public Facture getInvoice() {
     return this.invoice;
   }
   
   public void setInvoice(Facture invoice) {
     this.invoice = invoice;
   }
   
   public double getPrixUnitaire() {
     return this.prixUnitaire;
   }
   
   public void setPrixUnitaire(double prixUnitaire) {
     this.prixUnitaire = prixUnitaire;
   }
   
   public double getMontantTaxe() {
     this.montantTaxe = getMontantTTC() - getMontantHT();
     return this.montantTaxe;
   }
   
   public void setMontantTaxe(double montantTaxe) {
     this.montantTaxe = montantTaxe;
   }
   
   public double getTauxRemise() {
     return this.tauxRemise;
   }
   
   public void setTauxRemise(double tauxRemise) {
     this.tauxRemise = tauxRemise;
   }
   
   public double getRemiseForfait() {
     return this.remiseForfait;
   }
   
   public void setRemiseForfait(double remiseForfait) {
     this.remiseForfait = remiseForfait;
   }
   
   public double getQuantity() {
     return this.quantity;
   }
   
   public void setQuantity(double quantity) {
     this.quantity = quantity;
   }
 
   
   public String getProdCod() {
     return this.prodCod;
   }
   
   public void setProdCod(String prodCod) {
     this.prodCod = prodCod;
   }
   
   public String getProdName() {
     return this.prodName;
   }
   
   public void setProdName(String prodName) {
     this.prodName = prodName;
   }
   
   public double getMontantHT() {
     return this.montantHT;
   }
   
   public void setMontantHT(double montantHT) {
     this.montantHT = montantHT;
   }
 
   
   public double getMontantTTC() {
     return this.montantTTC;
   }
   
   public void setMontantTTC(double montantTTC) {
     this.montantTTC = montantTTC;
   }
 
 
   
   public String getPrintTotHT() {
     this.printTotHT = HelperC.decimalNumber(getMontantHT(), 0, true);
     return this.printTotHT;
   }
   
   public void setPrintTotHT(String printTotHT) {
     this.printTotHT = printTotHT;
   }
   
   public String getPrintTotTTC() {
     this.printTotTTC = HelperC.decimalNumber(getMontantTTC(), 0, true);
     return this.printTotTTC;
   }
   
   public void setPrintTotTTC(String printTotTTC) {
     this.printTotTTC = printTotTTC;
   }
   
   public int getTypeBase() {
     return this.typeBase;
   }
   
   public void setTypeBase(int typeBase) {
     this.typeBase = typeBase;
   }
   
   public String getPrintPu() {
     this.printPu = HelperC.decimalNumber(getPrixUnitaire(), 0, true);
     return this.printPu;
   }
   
   public void setPrintPu(String printPu) {
     this.printPu = printPu;
   }
 
   
   public String getPrintTotTxe() {
     this.printTotTxe = HelperC.decimalNumber(getMontantTaxe(), 0, true);
     return this.printTotTxe;
   }
 
   
   public void setPrintTotTxe(String printTotTxe) {
     this.printTotTxe = printTotTxe;
   }
 }


