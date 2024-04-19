 package entite;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
 
 @Entity
 @Table(name = "tb_taxe")
 public class Taxes
   implements Serializable
 {
   private static final long serialVersionUID = -7276040693911187065L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "tx_id")
   private int id;
   @Column(name = "reference")
   private String codeTaxe;
   @Column(name = "libelle")
   private String libelle;
   @Column(name = "taux")
   private double taux;
   @Column(name = "compte")
   private String codeCpbl;
   @Column(name = "type_taxe")
   private int type;
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public String getCodeTaxe() {
     return this.codeTaxe;
   }
   
   public void setCodeTaxe(String codeTaxe) {
     this.codeTaxe = codeTaxe;
   }
   
   public String getLibelle() {
     return this.libelle;
   }
   
   public void setLibelle(String libelle) {
     this.libelle = libelle;
   }
   
   public double getTaux() {
     return this.taux;
   }
   
   public void setTaux(double taux) {
     this.taux = taux;
   }
   
   public String getCodeCpbl() {
     return this.codeCpbl;
   }
   
   public void setCodeCpbl(String codeCpbl) {
     this.codeCpbl = codeCpbl;
   }
 
   
   public int getType() {
     return this.type;
   }
   
   public void setType(int type) {
     this.type = type;
   }
 }


