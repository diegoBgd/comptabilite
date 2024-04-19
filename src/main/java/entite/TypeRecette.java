 package entite;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
 
 @Entity
 @Table(name = "tb_type_recette")
 public class TypeRecette
   implements Serializable
 {
   private static final long serialVersionUID = -3773286461474016406L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "rec_id")
   private int id;
   @Column(name = "reference")
   private String codeRec;
   @Column(name = "libelle")
   private String libelle;
   @Column(name = "compte")
   private String codeCpbl;
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public String getCodeRec() {
     return this.codeRec;
   }
   
   public void setCodeRec(String codeRec) {
     this.codeRec = codeRec;
   }
   
   public String getLibelle() {
     return this.libelle;
   }
   
   public void setLibelle(String libelle) {
     this.libelle = libelle;
   }
   
   public String getCodeCpbl() {
     return this.codeCpbl;
   }
   
   public void setCodeCpbl(String codeCpbl) {
     this.codeCpbl = codeCpbl;
   }
 }


