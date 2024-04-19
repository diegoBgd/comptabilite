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
 @Table(name = "tb_facturation_taxe")
 public class FactureTaxe
   implements Serializable
 {
   private static final long serialVersionUID = 2151630210223853600L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_ftx")
   private int id;
   @ManyToOne
   @JoinColumn(name = "id_taxe")
   private Taxes tax;
   @Column(name = "taux")
   private double taux;
   @ManyToOne
   @JoinColumn(name = "detail_facture")
   private FactureDetail detFacture;
   @Transient
   private double montantTaxe;
   @Transient
   private String printTaxe;
   @Transient
   private boolean selected;
   @Transient
   private String printLibelle;
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public Taxes getTax() {
     return this.tax;
   }
   
   public void setTax(Taxes tax) {
     this.tax = tax;
   }
   
   public double getTaux() {
     return this.taux;
   }
   
   public void setTaux(double taux) {
     this.taux = taux;
   }
   
   public FactureDetail getDetFacture() {
     return this.detFacture;
   }
   
   public void setDetFacture(FactureDetail detFacture) {
     this.detFacture = detFacture;
   }
   
   public double getMontantTaxe() {
     return this.montantTaxe;
   }
   
   public void setMontantTaxe(double montantTaxe) {
     this.montantTaxe = montantTaxe;
   }
   
   public String getPrintTaxe() {
     this.printTaxe = HelperC.decimalNumber(getMontantTaxe(), 0, true);
     return this.printTaxe;
   }
   
   public void setPrintTaxe(String printTaxe) {
     this.printTaxe = printTaxe;
   }
   
   public boolean isSelected() {
     return this.selected;
   }
   
   public void setSelected(boolean selected) {
     this.selected = selected;
   }
   
   public String getPrintLibelle() {
     return this.printLibelle;
   }
   
   public void setPrintLibelle(String printLibelle) {
     this.printLibelle = printLibelle;
   }
 }


