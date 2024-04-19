 package entite;
 
 import java.io.Serializable;
 import java.util.Date;
 import java.util.List;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
 import javax.persistence.Transient;
 import utils.HelperC;
 
  
 
 
 @Entity
 @Table(name = "tb_immobilise")
 public class Immobilise
   implements Serializable
 {
   private static final long serialVersionUID = 1216916235748945522L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "immo_id")
   private int id;
   @Column(name = "reference", unique = true)
   private String codeImmo;
   @Column(name = "libelle")
   private String libelle;
   @Column(name = "numero")
   private String numero;
   @Column(name = "val_acq")
   private double montantAcq;
   @Column(name = "taux_amrt")
   private double tauxAmort;
   @Column(name = "type_amrt")
   private int typeAmort;
   @Column(name = "period")
   private int nbrAnne;
   @Column(name = "date_acq")
   private Date dateAcquisition;
   @Column(name = "amortizable")
   private int amortissable;
   @Column(name = "compte_immo")
   private String compteImmo;
   @Column(name = "compte_amrt")
   private String compteAmrt;
   @Column(name = "compte_dot_amrt")
   private String compteDotAmrt;
   @Transient
   private List<Amortissement> listAmortissement;
   @Transient
   private double amortEncour;
   @Transient
   private double amortAnterieur;
   @Transient
   private double vnc;
   @Transient
   private String printAcqValue;
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public String getCodeImmo() {
     return this.codeImmo;
   }
   
   public void setCodeImmo(String codeImmo) {
     this.codeImmo = codeImmo;
   }
   
   public String getLibelle() {
     return this.libelle;
   }
   
   public void setLibelle(String libelle) {
     this.libelle = libelle;
   }
   
   public String getNumero() {
     return this.numero;
   }
   
   public void setNumero(String numero) {
     this.numero = numero;
   }
   
   public double getMontantAcq() {
     return this.montantAcq;
   }
   
   public void setMontantAcq(double montantAcq) {
     this.montantAcq = montantAcq;
   }
   
   public double getTauxAmort() {
     return this.tauxAmort;
   }
   
   public void setTauxAmort(double tauxAmort) {
     this.tauxAmort = tauxAmort;
   }
   
   public int getTypeAmort() {
     return this.typeAmort;
   }
   
   public void setTypeAmort(int typeAmort) {
     this.typeAmort = typeAmort;
   }
   
   public int getNbrAnne() {
     return this.nbrAnne;
   }
   
   public void setNbrAnne(int nbrAnne) {
     this.nbrAnne = nbrAnne;
   }
   
   public Date getDateAcquisition() {
     return this.dateAcquisition;
   }
   
   public void setDateAcquisition(Date dateAcquisition) {
     this.dateAcquisition = dateAcquisition;
   }
   
   public int getAmortissable() {
     return this.amortissable;
   }
   public void setAmortissable(int amortissable) {
     this.amortissable = amortissable;
   }
   public String getCompteImmo() {
     return this.compteImmo;
   }
   
   public void setCompteImmo(String compteImmo) {
     this.compteImmo = compteImmo;
   }
   
   public String getCompteAmrt() {
     return this.compteAmrt;
   }
   
   public void setCompteAmrt(String compteAmrt) {
     this.compteAmrt = compteAmrt;
   }
   
   public String getCompteDotAmrt() {
     return this.compteDotAmrt;
   }
   
   public void setCompteDotAmrt(String compteDotAmrt) {
     this.compteDotAmrt = compteDotAmrt;
   }
   
   public double getAmortEncour() {
     return this.amortEncour;
   }
   
   public void setAmortEncour(double amortEncour) {
     this.amortEncour = amortEncour;
   }
   
   public double getAmortAnterieur() {
     return this.amortAnterieur;
   }
   
   public void setAmortAnterieur(double amortAnterieur) {
     this.amortAnterieur = amortAnterieur;
   }
   
   public double getVnc() {
     return this.vnc;
   }
   
   public void setVnc(double vnc) {
     this.vnc = vnc;
   }
   public String getPrintAcqValue() {
     this.printAcqValue = HelperC.decimalNumber(getMontantAcq(), 0, true);
     return this.printAcqValue;
   }
   public void setPrintAcqValue(String printAcqValue) {
     this.printAcqValue = printAcqValue;
   }
 }


