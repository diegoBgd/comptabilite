 package entite;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
  
 @Entity
 @Table(name = "tb_type_charge")
 public class TypeCharge
   implements Serializable
 {
   private static final long serialVersionUID = -6554439190701140320L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ch_id")
   private int id;
   @Column(name = "reference")
   private String codeChg;
   @Column(name = "libelle")
   private String libelle;
   @Column(name = "nature")
   private int nature;
   @Column(name = "compte")
   private String codeCpbl;
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public String getCodeChg() {
     return this.codeChg;
   }
   
   public void setCodeChg(String codeChg) {
     this.codeChg = codeChg;
   }
   
   public String getLibelle() {
     return this.libelle;
   }
   
   public void setLibelle(String libelle) {
     this.libelle = libelle;
   }
   
   public int getNature() {
     return this.nature;
   }
   
   public void setNature(int nature) {
     this.nature = nature;
   }
   
   public String getCodeCpbl() {
     return this.codeCpbl;
   }
   
   public void setCodeCpbl(String codeCpbl) {
     this.codeCpbl = codeCpbl;
   }
 }


