 package entite;
 
 import java.io.Serializable;
 import java.util.Date;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
 import javax.persistence.Transient;
 import utils.HelperC;
 
  
 @Entity
 @Table(name = "tb_exercice")
 public class Exercice
   implements Serializable
 {
   private static final long serialVersionUID = -3737631155026374033L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "ex_id")
   private int id;
   @Column(name = "reference")
   private String exCode;
   @Column(name = "libelle")
   private String libelle;
   @Column(name = "periode_debut")
   private Date dateDebut;
   @Column(name = "periode_fin")
   private Date dateFin;
   @Column(name = "exercice_prcd")
   private String exPrcdCode;
   @Transient
   private String printDeb;
   @Transient
   private String printFin;
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public String getExCode() {
     return this.exCode;
   }
   
   public void setExCode(String exCode) {
     this.exCode = exCode;
   }
   
   public String getLibelle() {
     return this.libelle;
   }
   
   public void setLibelle(String libelle) {
     this.libelle = libelle;
   }
   
   public Date getDateDebut() {
     return this.dateDebut;
   }
   
   public void setDateDebut(Date dateDebut) {
     this.dateDebut = dateDebut;
   }
   
   public Date getDateFin() {
     return this.dateFin;
   }
   
   public void setDateFin(Date dateFin) {
     this.dateFin = dateFin;
   }
   
   public String getExPrcdCode() {
     return this.exPrcdCode;
   }
   
   public void setExPrcdCode(String exPrcdCode) {
     this.exPrcdCode = exPrcdCode;
   }
   
   public String getPrintDeb() {
     this.printDeb = HelperC.convertDate(getDateDebut());
     return this.printDeb;
   }
   
   public void setPrintDeb(String printDeb) {
     this.printDeb = printDeb;
   }
   
   public String getPrintFin() {
     this.printFin = HelperC.convertDate(getDateFin());
     return this.printFin;
   }
   
   public void setPrintFin(String printFin) {
     this.printFin = printFin;
   }
 }


