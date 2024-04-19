 package entite;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
 
 
 
 @Entity
 @Table(name = "tbl_centre_cout")
 public class CentreCout
   implements Serializable
 {
   private static final long serialVersionUID = 8788061362773108037L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ctr_id")
   private int id;
   @Column(name = "reference")
   private String code;
   @Column(name = "libelle")
   private String libelle;
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public String getCode() {
     return this.code;
   }
   
   public void setCode(String code) {
     this.code = code;
   }
   
   public String getLibelle() {
     return this.libelle;
   }
   
   public void setLibelle(String libelle) {
     this.libelle = libelle;
   }
 }


