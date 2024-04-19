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
 @Table(name = "tb_depense")
 public class Depense
   implements Serializable
 {
   private static final long serialVersionUID = -7019025387429924200L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "dep_id")
   private int id;
   @ManyToOne
   @JoinColumn(name = "type_charge")
   private TypeCharge charge;
   @Column(name = "montant")
   private double montant;
   @ManyToOne
   @JoinColumn(name = "taxe")
   private Taxes taxe;
   @Column(name = "taux")
   private double tauxTaxe;
   @ManyToOne
   @JoinColumn(name = "fournisseur")
   private Fournisseur fourn;
   @ManyToOne
   @JoinColumn(name = "devise")
   private Devise devise;
   @Column(name = "cours")
   private double coursDev;
   @Column(name = "libelle")
   private String libelle;
   @Column(name = "exercice")
   private int idExercise;
   @Column(name = "type_depense")
   private TypeEcriture typeOperation;
   @ManyToOne
   @JoinColumn(name = "centre_cout")
   private CentreCout centre;
   @Column(name = "date_operation")
   private Date dateOperation;
   @Column(name = "num_piece")
   private String piece;
   @Transient
   private String dateToPrint;
   @Transient
   private String amountToPrint;
   @Transient
   private double montantTTC;
   @Transient
   private double montantTotRegle;
   
   public int getId() {
     return this.id;
   }
   public void setId(int id) {
     this.id = id;
   }
   public TypeCharge getCharge() {
     return this.charge;
   }
   public void setCharge(TypeCharge charge) {
     this.charge = charge;
   }
   public double getMontant() {
     return this.montant;
   }
   public void setMontant(double montant) {
     this.montant = montant;
   }
   public Taxes getTaxe() {
     return this.taxe;
   }
   public void setTaxe(Taxes taxe) {
     this.taxe = taxe;
   }
   public double getTauxTaxe() {
     return this.tauxTaxe;
   }
   public void setTauxTaxe(double tauxTaxe) {
     this.tauxTaxe = tauxTaxe;
   }
   public Fournisseur getFourn() {
     return this.fourn;
   }
   public void setFourn(Fournisseur fourn) {
     this.fourn = fourn;
   }
   public Devise getDevise() {
     return this.devise;
   }
   public void setDevise(Devise devise) {
     this.devise = devise;
   }
   public double getCoursDev() {
     return this.coursDev;
   }
   public void setCoursDev(double coursDev) {
     this.coursDev = coursDev;
   }
   public String getLibelle() {
     return this.libelle;
   }
   public void setLibelle(String libelle) {
     this.libelle = libelle;
   }
   public int getIdExercise() {
     return this.idExercise;
   }
   public void setIdExercise(int idExercise) {
     this.idExercise = idExercise;
   }
   public CentreCout getCentre() {
     return this.centre;
   }
   public void setCentre(CentreCout centre) {
     this.centre = centre;
   }
   public Date getDateOperation() {
     return this.dateOperation;
   }
   public void setDateOperation(Date dateOperation) {
     this.dateOperation = dateOperation;
   }
   public String getPiece() {
     return this.piece;
   }
   public void setPiece(String piece) {
     this.piece = piece;
   }
   
   public String getDateToPrint() {
     this.dateToPrint = HelperC.convertDate(getDateOperation());
     return this.dateToPrint;
   }
   
   public void setDateToPrint(String dateToPrint) {
     this.dateToPrint = dateToPrint;
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
   
   public double getMontantTotRegle() {
     return this.montantTotRegle;
   }
   
   public void setMontantTotRegle(double montantTotRegle) {
     this.montantTotRegle = montantTotRegle;
   }
   public TypeEcriture getTypeOperation() {
     return this.typeOperation;
   }
   
   public void setTypeOperation(TypeEcriture typeOperation) {
     this.typeOperation = typeOperation;
   }
 
   
   public void calculMontantTTC() {
     double ttc = 0.0D;
     if (getTauxTaxe() > 0.0D) {
       
       ttc = getMontant() * (1.0D + getTauxTaxe() / 100.0D);
       setMontantTTC(ttc);
     } else {
       
       setMontantTTC(getMontant());
     } 
   }
   public void calculMontantHT() {
     double ttc = 0.0D;
     if (getTauxTaxe() > 0.0D) {
       
       ttc = getMontantTTC() / (1.0D + getTauxTaxe() / 100.0D);
       setMontant(ttc);
     } else {
       
       setMontant(getMontantTTC());
     } 
   }
 }


