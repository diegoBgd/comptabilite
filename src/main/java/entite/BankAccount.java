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
 
 
 
 @Entity
 @Table(name = "tb_compte")
 public class BankAccount
   implements Serializable
 {
   private static final long serialVersionUID = -1859007420070194916L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ac_id")
   private int id;
   @Column(name = "reference")
   private String accCode;
   @Column(name = "libelle")
   private String libelle;
   @Column(name = "compte")
   private String codeCpb;
   @ManyToOne
   @JoinColumn(name = "banque")
   private Banque bank;
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public String getAccCode() {
     return this.accCode;
   }
   
   public void setAccCode(String accCode) {
     this.accCode = accCode;
   }
   
   public String getLibelle() {
     return this.libelle;
   }
   
   public void setLibelle(String libelle) {
     this.libelle = libelle;
   }
   
   public String getCodeCpb() {
     return this.codeCpb;
   }
   
   public void setCodeCpb(String codeCpb) {
     this.codeCpb = codeCpb;
   }
   
   public Banque getBank() {
     return this.bank;
   }
   
   public void setBank(Banque bank) {
     this.bank = bank;
   }
 }


