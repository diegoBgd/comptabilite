 package entite;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
 

 
 @Entity
 @Table(name = "tb_devise")
 public class Devise
   implements Serializable
 {
   private static final long serialVersionUID = 6371517942429628936L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "dev_id")
   private int id;
   @Column(name = "reference")
   private String codeDev;
   @Column(name = "libelle")
   private String libelle;
   @Column(name = "symbole")
   private String symbole;
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public String getCodeDev() {
     return this.codeDev;
   }
   
   public void setCodeDev(String codeDev) {
     this.codeDev = codeDev;
   }
   
   public String getLibelle() {
     return this.libelle;
   }
   
   public void setLibelle(String libelle) {
     this.libelle = libelle;
   }
   
   public String getSymbole() {
     return this.symbole;
   }
   
   public void setSymbole(String symbole) {
     this.symbole = symbole;
   }
 }


