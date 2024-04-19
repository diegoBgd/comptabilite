 package entite;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
 
 
 
 
 @Entity
 @Table(name = "tb_fournisseur")
 public class Fournisseur
   implements Serializable
 {
   private static final long serialVersionUID = 395913507690373040L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "frn_id")
   private int id;
   @Column(name = "reference")
   private String codeFrn;
   @Column(name = "libelle")
   private String nameFrn;
   @Column(name = "compte")
   private String codeCpbl;
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public String getCodeFrn() {
     return this.codeFrn;
   }
   
   public void setCodeFrn(String codeFrn) {
     this.codeFrn = codeFrn;
   }
   
   public String getNameFrn() {
     return this.nameFrn;
   }
   
   public void setNameFrn(String nameFrn) {
     this.nameFrn = nameFrn;
   }
   
   public String getCodeCpbl() {
     return this.codeCpbl;
   }
   
   public void setCodeCpbl(String codeCpbl) {
     this.codeCpbl = codeCpbl;
   }
 }


