 package entite;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
 
 
 @Entity
 @Table(name = "tb_banque")
 public class Banque
   implements Serializable
 {
   private static final long serialVersionUID = -3306516775598421556L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "bk_id")
   private int id;
   @Column(name = "reference")
   private String codeBk;
   @Column(name = "libelle")
   private String libelle;
   @Column(name = "compte")
   private String codeCpbl;
   @Column(name = "caisse")
   private boolean caisse;
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public String getCodeBk() {
     return this.codeBk;
   }
   
   public void setCodeBk(String codeBk) {
     this.codeBk = codeBk;
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
   
   public boolean isCaisse() {
     return this.caisse;
   }
   
   public void setCaisse(boolean caisse) {
     this.caisse = caisse;
   }
 }


