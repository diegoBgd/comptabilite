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
 @Table(name = "tb_parametre_immo")
 public class ParametreImmo
   implements Serializable
 {
   private static final long serialVersionUID = -3221369822013180690L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "jrnl_Parm")
   private int idParm;
   @Column(name = "valeur_non_amrt")
   private double valeurNonAmort;
   @ManyToOne
   @JoinColumn(name = "id_jrnl_amrt")
   private Journal journalAmort;
   @ManyToOne
   @JoinColumn(name = "id_jrnl_cession")
   private Journal journalCession;
   
   public int getIdParm() {
     return this.idParm;
   }
   public void setIdParm(int idParm) {
     this.idParm = idParm;
   }
   public double getValeurNonAmort() {
     return this.valeurNonAmort;
   }
   public void setValeurNonAmort(double valeurNonAmort) {
     this.valeurNonAmort = valeurNonAmort;
   }
   public Journal getJournalAmort() {
     return this.journalAmort;
   }
   public void setJournalAmort(Journal journalAmort) {
     this.journalAmort = journalAmort;
   }
 }


