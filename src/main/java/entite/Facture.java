 package entite;
 
 import java.io.Serializable;
 import java.util.Date;
 import java.util.List;
 import javax.persistence.CascadeType;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.JoinColumn;
 import javax.persistence.ManyToOne;
 import javax.persistence.OneToMany;
 import javax.persistence.Table;
 
  
 
 
 @Entity
 @Table(name = "tb_facturation")
 public class Facture
   implements Serializable
 {
   private static final long serialVersionUID = -6622979142434633811L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "fact_id")
   private int id;
   @Column(name = "numero")
   private int numeroFact;
   @ManyToOne
   @JoinColumn(name = "client")
   private Clients customer;
   @Column(name = "nom_client")
   private String nomClient;
   @Column(name = "total_ht")
   private double totalHT;
   @Column(name = "total_ttc")
   private double totalTTc;
   @Column(name = "total_reduction")
   private double totalReduction;
   @Column(name = "accompte")
   private double accompte;
   @Column(name = "calcul_base_tvac")
   private boolean baseTx;
   @Column(name = "type_operation")
   private int typeOperation;
   @Column(name = "date_operation")
   private Date dateOperation;
   @Column(name = "date_echeance")
   private Date dateEcheance;
   @Column(name = "exercice")
   private Exercice exercise;
   @Column(name = "operation_origine")
   private int operOrigine;
   @Column(name = "comment")
   private String comment;
   @OneToMany(cascade = {CascadeType.ALL})
   @JoinColumn(name = "id_facture")
   private List<FactureDetail> factureDetail;
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public int getNumeroFact() {
     return this.numeroFact;
   }
   
   public void setNumeroFact(int numeroFact) {
     this.numeroFact = numeroFact;
   }
   
   public Clients getCustomer() {
     return this.customer;
   }
   
   public void setCustomer(Clients customer) {
     this.customer = customer;
   }
   
   public String getNomClient() {
     return this.nomClient;
   }
   
   public void setNomClient(String nomClient) {
     this.nomClient = nomClient;
   }
   
   public double getTotalHT() {
     return this.totalHT;
   }
   
   public void setTotalHT(double totalHT) {
     this.totalHT = totalHT;
   }
   
   public double getTotalTTc() {
     return this.totalTTc;
   }
   
   public void setTotalTTc(double totalTTc) {
     this.totalTTc = totalTTc;
   }
   
   public double getTotalReduction() {
     return this.totalReduction;
   }
   
   public void setTotalReduction(double totalReduction) {
     this.totalReduction = totalReduction;
   }
   
   public double getAccompte() {
     return this.accompte;
   }
   
   public void setAccompte(double accompte) {
     this.accompte = accompte;
   }
   
   public boolean isBaseTx() {
     return this.baseTx;
   }
   
   public void setBaseTx(boolean baseTx) {
     this.baseTx = baseTx;
   }
   
   public int getTypeOperation() {
     return this.typeOperation;
   }
   
   public void setTypeOperation(int typeOperation) {
     this.typeOperation = typeOperation;
   }
   
   public Date getDateOperation() {
     return this.dateOperation;
   }
   
   public void setDateOperation(Date dateOperation) {
     this.dateOperation = dateOperation;
   }
   
   public Date getDateEcheance() {
     return this.dateEcheance;
   }
   
   public void setDateEcheance(Date dateEcheance) {
     this.dateEcheance = dateEcheance;
   }
   
   public Exercice getExercise() {
     return this.exercise;
   }
   
   public void setExercise(Exercice exercise) {
     this.exercise = exercise;
   }
   
   public int getOperOrigine() {
     return this.operOrigine;
   }
   
   public void setOperOrigine(int operOrigine) {
     this.operOrigine = operOrigine;
   }
   
   public String getComment() {
     return this.comment;
   }
   
   public void setComment(String comment) {
     this.comment = comment;
   }
   
   public List<FactureDetail> getFactureDetail() {
     return this.factureDetail;
   }
   
   public void setFactureDetail(List<FactureDetail> factureDetail) {
     this.factureDetail = factureDetail;
   }
 }


