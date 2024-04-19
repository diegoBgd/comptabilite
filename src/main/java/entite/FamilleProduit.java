 package entite;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
 
 
 @Entity
 @Table(name = "tb_famille_produit")
 public class FamilleProduit
   implements Serializable
 {
   private static final long serialVersionUID = 4502045699004940165L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "fm_id")
   private int id;
   @Column(name = "reference")
   private String codeFm;
   @Column(name = "libelle")
   private String libelle;
   @Column(name = "compte_vente")
   private String compteVente;
   @Column(name = "compte_stock")
   private String compteStock;
   @Column(name = "compte_sortie")
   private String compteSortie;
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public String getCodeFm() {
     return this.codeFm;
   }
   
   public void setCodeFm(String codeFm) {
     this.codeFm = codeFm;
   }
   
   public String getLibelle() {
     return this.libelle;
   }
   
   public void setLibelle(String libelle) {
     this.libelle = libelle;
   }
   
   public String getCompteVente() {
     return this.compteVente;
   }
   
   public void setCompteVente(String compteVente) {
     this.compteVente = compteVente;
   }
   
   public String getCompteStock() {
     return this.compteStock;
   }
   
   public void setCompteStock(String compteStock) {
     this.compteStock = compteStock;
   }
   
   public String getCompteSortie() {
     return this.compteSortie;
   }
   
   public void setCompteSortie(String compteSortie) {
     this.compteSortie = compteSortie;
   }
 }


