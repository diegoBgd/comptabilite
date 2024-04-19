 package entite;
 
 import java.io.Serializable;
 
 
 
 public class Fonctionalite
   implements Serializable
 {
   private static final long serialVersionUID = -1328287068679573152L;
   private int numero;
   private String libelle;
   private boolean selected;
   
   public int getNumero() {
     return this.numero;
   }
   
   public void setNumero(int numero) {
     this.numero = numero;
   }
   
   public String getLibelle() {
     return this.libelle;
   }
   
   public void setLibelle(String libelle) {
     this.libelle = libelle;
   }
   
   public boolean isSelected() {
     return this.selected;
   }
   
   public void setSelected(boolean selected) {
     this.selected = selected;
   }
 }


