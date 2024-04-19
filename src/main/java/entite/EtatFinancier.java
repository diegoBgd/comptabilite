 package entite;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;

 
 
 
 @Entity
 @Table(name = "tb_etat_financier")
 public class EtatFinancier
   implements Serializable
 {
   private static final long serialVersionUID = 6098741606096155773L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ef_id")
   private int id;
   @Column(name = "reference")
   private String code;
   @Column(name = "libelle")
   private String libelle;
   @Column(name = "type_etat", columnDefinition = "int default 0", nullable = false)
   private int typeEtat;
   
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
   
   public int getTypeEtat() {
     return this.typeEtat;
   }
   
   public void setTypeEtat(int typeEtat) {
     this.typeEtat = typeEtat;
   }
 }


