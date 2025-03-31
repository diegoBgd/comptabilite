 package entite;
 
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.List;
 import javax.persistence.CascadeType;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.FetchType;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.JoinColumn;
 import javax.persistence.ManyToOne;
 import javax.persistence.OneToMany;
 import javax.persistence.Table;
 import javax.persistence.Transient;
 
  
 
 
 @Entity
 @Table(name = "tb_etat_financier_rubrique")
 public class RubriqueEFi
   implements Serializable
 {
   private static final long serialVersionUID = 6638662236746968320L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "rub_id")
   private int id;
   @Column(name = "reference")
   private String code;
   @Column(name = "libelle")
   private String libelle;
   @Column(name = "formule", columnDefinition = "varchar(150) default ''")
   private String formule;
   @Column(name = "signe")
   private String signe;
   @ManyToOne
   @JoinColumn(name = "id_etat")
   private EtatFinancier etat;
   @Column(name = "type_valuer")
   private int typeValeur;
   @Column(name = "titre")
   private boolean titreValeur;
   @Column(name = "afficher_valeur")
   private boolean printValue;
   @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
   @JoinColumn(name = "rubrique_id")
   private List<CompteEFi> listCompte = new ArrayList<>();
   @Transient
   private double brut; 
   @Transient
   private double amortissemnt;
   @Transient
   private double amortissemntPrcd;
   @Transient
   private double netAn; 
   @Transient
   private double netAprcd;
   @Transient
   private boolean calculated; 
   
   public int getId()
   { 
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
   public String getFormule() {
     return this.formule;
   }
   
   public void setFormule(String formule) {
     this.formule = formule;
   }
   
   public String getSigne() {
     return this.signe;
   }
   
   public void setSigne(String signe) {
     this.signe = signe;
   }
   
   public EtatFinancier getEtat() {
     return this.etat;
   }
   
   public void setEtat(EtatFinancier etat) {
     this.etat = etat;
   }
   
   public int getTypeValeur() {
     return this.typeValeur;
   }
   
   public void setTypeValeur(int typeValeur) {
     this.typeValeur = typeValeur;
   }
   
   public boolean isTitreValeur() {
     return this.titreValeur;
   }
   
   public void setTitreValeur(boolean titreValeur) {
     this.titreValeur = titreValeur;
   }
   
   public boolean isPrintValue() {
     return this.printValue;
   }
   
   public void setPrintValue(boolean printValue) {
     this.printValue = printValue;
   }
   
   public List<CompteEFi> getListCompte() {
     return this.listCompte;
   }
   
   public void setListCompte(List<CompteEFi> listCompte) {
     this.listCompte = listCompte;
   }
   
   public double getBrut() {
     return this.brut;
   }
   
   public void setBrut(double brut) {
     this.brut = brut;
   }
   
   public double getAmortissemnt() {
     return this.amortissemnt;
   }
   
   public void setAmortissemnt(double amortissemnt) {
     this.amortissemnt = amortissemnt;
   }
   
   public double getNetAn() {
	  
     return this.netAn;
   }
   
   public void setNetAn(double netAn) {
     this.netAn = netAn;
   }
   
   public double getNetAprcd() {
     return this.netAprcd;
   }
   
   public void setNetAprcd(double netAprcd) {
     this.netAprcd = netAprcd;
   }
   
   public boolean isCalculated() {
     return this.calculated;
   }
   
   public void setCalculated(boolean calculated) {
     this.calculated = calculated;
   }
public double getAmortissemntPrcd() {
	return amortissemntPrcd;
}
public void setAmortissemntPrcd(double amortissemntPrcd) {
	this.amortissemntPrcd = amortissemntPrcd;
}
   
 }


