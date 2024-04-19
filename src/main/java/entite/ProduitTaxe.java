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
  
 
 @Entity
 @Table(name = "tb_produit_taxe")
 public class ProduitTaxe
   implements Serializable
 {
   private static final long serialVersionUID = -5776183440001382255L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "pt_id")
   private int id;
   @ManyToOne
   @JoinColumn(name = "id_prod")
   private Produit product;
   @ManyToOne
   @JoinColumn(name = "id_taxe")
   private Taxes taxe;
   @Column(name = "taux")
   private double taux;
   @Transient
   private double valeurTaxe;
   
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
   
   public Taxes getTaxe() {
     return this.taxe;
   }
   
   public void setTaxe(Taxes taxe) {
     this.taxe = taxe;
   }
   
   public double getTaux() {
     return this.taux;
   }
   
   public void setTaux(double taux) {
     this.taux = taux;
   }
   
   public double getValeurTaxe() {
     return this.valeurTaxe;
   }
   
   public void setValeurTaxe(double valeurTaxe) {
     this.valeurTaxe = valeurTaxe;
   }
 }


