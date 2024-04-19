 package entite;
 
 import java.io.Serializable;
 import java.util.Date;
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
 @Table(name = "tb_reglement_fourn")
 public class ReglementFournisseur
   implements Serializable
 {
   private static final long serialVersionUID = -5920611889842763810L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id_reg")
   private int id;
   @Column(name = "date_reglement")
   private Date dateReglement;
   @Column(name = "mode_reglement")
   private int modeReglement;
   @ManyToOne
   @JoinColumn(name = "depense")
   private Depense depense;
   @Column(name = "montant")
   private double montantRegle;
   @ManyToOne
   @JoinColumn(name = "devise")
   private Devise deviseRgl;
   @ManyToOne
   @JoinColumn(name = "type_depense")
   private TypeCharge typeDepense;
   @Column(name = "cours")
   private Double coursRgl;
   @Column(name = "exercice")
   private int idExercise;
   @Column(name = "type_regl")
   private TypeEcriture typeOperation;
   @Column(name = "comment")
   private String comment;
   @ManyToOne
   @JoinColumn(name = "taxe")
   private Taxes taxeRgl;
   @Column(name = "taux")
   private double taux;
   @Column(name = "num_piece")
   private String piece;
   @ManyToOne
   @JoinColumn(name = "id_caisse")
   private Banque caisse;
   @ManyToOne
   @JoinColumn(name = "id_compte")
   private BankAccount account;
   @Transient
   private String amountToPrint;
   @Transient
   private String dateToPrint;
   @Transient
   private double montantTTC;
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public Date getDateReglement() {
     return this.dateReglement;
   }
   
   public void setDateReglement(Date dateReglement) {
     this.dateReglement = dateReglement;
   }
   
   public int getModeReglement() {
     return this.modeReglement;
   }
   
   public void setModeReglement(int modeReglement) {
     this.modeReglement = modeReglement;
   }
   
   public Depense getDepense() {
     return this.depense;
   }
   
   public void setDepense(Depense depense) {
     this.depense = depense;
   }
   
   public double getMontantRegle() {
     return this.montantRegle;
   }
   
   public void setMontantRegle(double montantRegle) {
     this.montantRegle = montantRegle;
   }
   
   public Devise getDeviseRgl() {
     return this.deviseRgl;
   }
   
   public void setDeviseRgl(Devise deviseRgl) {
     this.deviseRgl = deviseRgl;
   }
   
   public Double getCoursRgl() {
     return this.coursRgl;
   }
   
   public void setCoursRgl(Double coursRgl) {
     this.coursRgl = coursRgl;
   }
   
   public int getIdExercise() {
     return this.idExercise;
   }
   
   public void setIdExercise(int idExercise) {
     this.idExercise = idExercise;
   }
   
   public String getComment() {
     return this.comment;
   }
   
   public void setComment(String comment) {
     this.comment = comment;
   }
   
   public Taxes getTaxeRgl() {
     return this.taxeRgl;
   }
   
   public void setTaxeRgl(Taxes taxeRgl) {
     this.taxeRgl = taxeRgl;
   }
   
   public double getTaux() {
     return this.taux;
   }
   
   public void setTaux(double taux) {
     this.taux = taux;
   }
   
   public String getPiece() {
     return this.piece;
   }
   
   public void setPiece(String piece) {
     this.piece = piece;
   }
   
   public Banque getCaisse() {
     return this.caisse;
   }
   
   public void setCaisse(Banque caisse) {
     this.caisse = caisse;
   }
   
   public BankAccount getAccount() {
     return this.account;
   }
   
   public void setAccount(BankAccount account) {
     this.account = account;
   }
   
   public TypeEcriture getTypeOperation() {
     return this.typeOperation;
   }
   
   public void setTypeOperation(TypeEcriture typeOperation) {
     this.typeOperation = typeOperation;
   }
 
   
   public TypeCharge getTypeDepense() {
     return this.typeDepense;
   }
   
   public void setTypeDepense(TypeCharge typeDepense) {
     this.typeDepense = typeDepense;
   }
   
   public String getDateToPrint() {
     this.dateToPrint = HelperC.convertDate(getDateReglement());
     return this.dateToPrint;
   }
   public String getAmountToPrint() {
     calculMontantTTC();
     this.amountToPrint = HelperC.decimalNumber(getMontantTTC(), 0, true);
     return this.amountToPrint;
   }
   
   public void setAmountToPrint(String amountToPrint) {
     this.amountToPrint = amountToPrint;
   }
   
   public double getMontantTTC() {
     return this.montantTTC;
   }
   
   public void setMontantTTC(double montantTTC) {
     this.montantTTC = montantTTC;
   }
   
   public void calculMontantTTC() {
     double ttc = 0.0D;
     if (getTaux() > 0.0D) {
       
       ttc = getMontantRegle() * (1.0D + getTaux() / 100.0D);
       setMontantTTC(ttc);
     } else {
       
       setMontantTTC(getMontantRegle());
     } 
   }
   public void calculMontantHT() {
     double ttc = 0.0D;
     if (getTaux() > 0.0D) {
       
       ttc = getMontantTTC() / (1.0D + getTaux() / 100.0D);
       setMontantRegle(ttc);
     } else {
       
       setMontantRegle(getMontantTTC());
     } 
   }
 }


