 package entite;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
  
 
 @Entity
 @Table(name = "tb_parametre_compta")
 public class ParametreCompta
   implements Serializable
 {
   private static final long serialVersionUID = 6049451131145949784L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_prm")
   private int idPrm;
   @Column(name = "journal_an")
   private String journalAN;
   @Column(name = "compte_rs")
   private String compteRs;
   @Column(name = "compte_and")
   private String compteAND;
   @Column(name = "compte_anc")
   private String compteANC;
   @Column(name = "journal_od")
   private String journalOD;
   
   public int getIdPrm() {
     return this.idPrm;
   }
   
   public void setIdPrm(int idPrm) {
     this.idPrm = idPrm;
   }
   
   public String getJournalAN() {
     return this.journalAN;
   }
   
   public void setJournalAN(String journalAN) {
     this.journalAN = journalAN;
   }
   
   public String getCompteRs() {
     return this.compteRs;
   }
   
   public void setCompteRs(String compteRs) {
     this.compteRs = compteRs;
   }
   
   public String getCompteAND() {
     return this.compteAND;
   }
   
   public void setCompteAND(String compteAND) {
     this.compteAND = compteAND;
   }
   
   public String getCompteANC() {
     return this.compteANC;
   }
   
   public void setCompteANC(String compteANC) {
     this.compteANC = compteANC;
   }
   
   public String getJournalOD() {
     return this.journalOD;
   }
   
   public void setJournalOD(String journalOD) {
     this.journalOD = journalOD;
   }
 }


