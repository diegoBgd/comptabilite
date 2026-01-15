 package entite;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.ManyToOne;
 import javax.persistence.Table;
 import javax.persistence.Transient;
 
 
 @Entity
 @Table(name = "tb_etat_financier_cpt")
 public class CompteEFi
   implements Serializable
 {
   private static final long serialVersionUID = 132410247654359466L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "det_id")
   private int id;
   @Column(name = "compte_debut")
   private String compteDeb;
   @Column(name = "compte_fin")
   private String compteFin;
   @Column(name = "signe")
   private String signe;
   @Column(name = "type_solde")
   private int typeSolde;
   @Column(name = "colone_valeur")
   private int columnValue;
   @ManyToOne
   private RubriqueEFi rubrique;
   @Transient
   private String printSldType;
   @Transient
   private Long valeur;
   @Transient
   private Long valeurPrecedent;
   @Transient
   private boolean calucule;
   @Transient
   private int ligne;
   
	public int getLigne() {
		return ligne;
	}

	public void setLigne(int ligne) {
		this.ligne = ligne;
	}

	public int getId() {
     return this.id;
    }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public String getCompteDeb() {
     return this.compteDeb;
   }
   
   public void setCompteDeb(String compteDeb) {
     this.compteDeb = compteDeb;
   }
   
   public String getCompteFin() {
     return this.compteFin;
   }
   
   public void setCompteFin(String compteFin) {
     this.compteFin = compteFin;
   }
   
   public String getSigne() {
     return this.signe;
   }
   
   public void setSigne(String signe) {
     this.signe = signe;
   }
   
   public int getTypeSolde() {
     return this.typeSolde;
   }
   
   public void setTypeSolde(int typeSolde) {
     this.typeSolde = typeSolde;
   }
   
   public RubriqueEFi getRubrique() {
     return this.rubrique;
   }
   public void setRubrique(RubriqueEFi rubrique) {
     this.rubrique = rubrique;
   }
   public int getColumnValue() {
     return this.columnValue;
   }
   public void setColumnValue(int columnValue) {
     this.columnValue = columnValue;
   }
 
   
   public String getPrintSldType() {
     switch (getTypeSolde()) {
       case 1:
         this.printSldType = "S";
         break;
       case 2:
         this.printSldType = "S.D";
         break;
       case 3:
         this.printSldType = "S.C";
         break;
     } 
 
     
     return this.printSldType;
   }
   
   public void setPrintSldType(String printSldType) {
     this.printSldType = printSldType;
   }
   
   public Long getValeur() {
     return this.valeur;
   }
   
   public void setValeur(Long valeur) {
     this.valeur = valeur;
   }
   
   public boolean isCalucule() {
     return this.calucule;
   }
   
   public void setCalucule(boolean calucule) {
     this.calucule = calucule;
   }

public Long getValeurPrecedent() {
	return valeurPrecedent;
}

public void setValeurPrecedent(Long valeurPrecedent) {
	this.valeurPrecedent = valeurPrecedent;
}
   
 }


