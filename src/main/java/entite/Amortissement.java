 package entite;
 
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.List;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.JoinColumn;
 import javax.persistence.ManyToOne;
 import javax.persistence.Table;
 import javax.persistence.Transient;
 import utils.HelperC;

 
 @Entity
 @Table(name = "tb_amortissement")
 public class Amortissement
   implements Serializable
 {
   private static final long serialVersionUID = -3416666589082718487L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "amrt_id")
   private int id;
   @Column(name = "period_amrt")
   private int periodeAmrt;
   @ManyToOne
   @JoinColumn(name = "id_immo")
   private Immobilise immo;
   @Column(name = "taux_amrt")
   private double tauxAmrt;
   @Column(name = "amrt_exercice")
   private double amortExercice;
   @Column(name = "amrt_ant")
   private double amrtAnterieur;
   @Column(name = "base_amrt")
   private double baseAmortissement;
   @Transient
   private double amrtRestant;
   @Transient
   private double vnc;
   @Transient
   private double cumul;
   @Transient
   private int numero;
   @ManyToOne
   @JoinColumn(name = "id_exercice")
   private Exercice exercice;
   @Transient
   private String numExercice;
   @Transient
   private List<Ecriture> listEcriture = new ArrayList<>();
   @Transient
   private String montantExercice;
   @Transient
   private String montantBase; 
   @Transient
   private String montantCumul;
   
   public int getId() { return this.id; } 
   @Transient
   private String montantVnc; 
   @Transient
   private String montantAnterieur; 
   @Transient
   private String dateAcq; 
   
   public void setId(int id) {
     this.id = id;
   }
   
   public int getPeriodeAmrt() {
     return this.periodeAmrt;
   }
   
   public void setPeriodeAmrt(int periodeAmrt) {
     this.periodeAmrt = periodeAmrt;
   }
   
   public Immobilise getImmo() {
     return this.immo;
   }
   
   public void setImmo(Immobilise immo) {
     this.immo = immo;
   }
   
   public double getTauxAmrt() {
     return this.tauxAmrt;
   }
   
   public void setTauxAmrt(double tauxAmrt) {
     this.tauxAmrt = tauxAmrt;
   }
   
   public double getAmortExercice() {
     return this.amortExercice;
   }
   
   public void setAmortExercice(double amortExercice) {
     this.amortExercice = amortExercice;
   }
   
   public double getAmrtAnterieur() {
     return this.amrtAnterieur;
   }
   
   public void setAmrtAnterieur(double amrtAnterieur) {
     this.amrtAnterieur = amrtAnterieur;
   }
   
   public double getAmrtRestant() {
     return this.amrtRestant;
   }
   
   public void setAmrtRestant(double amrtRestant) {
     this.amrtRestant = amrtRestant;
   }
   
   public double getVnc() {
     return this.vnc;
   }
   
   public void setVnc(double vnc) {
     this.vnc = vnc;
   }
   
   public double getCumul() {
     return this.cumul;
   }
   
   public void setCumul(double cumul) {
     this.cumul = cumul;
   }
   
   public double getBaseAmortissement() {
     return this.baseAmortissement;
   }
   
   public void setBaseAmortissement(double baseAmortissement) {
     this.baseAmortissement = baseAmortissement;
   }
   
   public Exercice getExercice() {
     return this.exercice;
   }
   
   public void setExercice(Exercice exercice) {
     this.exercice = exercice;
   }
   
   public String getNumExercice() {
     return this.numExercice;
   }
   
   public void setNumExercice(String numExercice) {
     this.numExercice = numExercice;
   }
   
   public List<Ecriture> getListEcriture() {
     return this.listEcriture;
   }
   
   public void setListEcriture(List<Ecriture> listEcriture) {
     this.listEcriture = listEcriture;
   }
   
   public String getMontantExercice() {
     this.montantExercice = HelperC.decimalNumber(this.amortExercice, 0, true);
     return this.montantExercice;
   }
   
   public void setMontantExercice(String montantExercice) {
     this.montantExercice = montantExercice;
   }
   
   public String getMontantBase() {
     this.montantBase = HelperC.decimalNumber(this.baseAmortissement, 0, true);
     return this.montantBase;
   }
   
   public void setMontantBase(String montantBase) {
     this.montantBase = montantBase;
   }
   
   public String getMontantCumul() {
     this.montantCumul = HelperC.decimalNumber(this.cumul, 0, true);
     return this.montantCumul;
   }
   
   public void setMontantCumul(String montantCumul) {
     this.montantCumul = montantCumul;
   }
   
   public String getMontantVnc() {
     this.montantVnc = HelperC.decimalNumber(this.vnc, 0, true);
     return this.montantVnc;
   }
   
   public void setMontantVnc(String montantVnc) {
     this.montantVnc = montantVnc;
   }
   
   public int getNumero() {
     return this.numero;
   }
   
   public void setNumero(int numero) {
     this.numero = numero;
   }
   
   public String getMontantAnterieur() {
     this.montantAnterieur = HelperC.decimalNumber(this.amrtAnterieur, 0, true);
     return this.montantAnterieur;
   }
   
   public void setMonantAnterieur(String montantAnterieur) {
     this.montantAnterieur = montantAnterieur;
   }
   
   public String getDateAcq() {
     this.dateAcq = HelperC.changeDateFormate(this.immo.getDateAcquisition());
     return this.dateAcq;
   }
   
   public void setDateAcq(String dateAcq) {
     this.dateAcq = dateAcq;
   }
 }


