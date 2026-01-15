 package vewBean;
 

 import java.io.IOException;
 import javax.annotation.PostConstruct;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import javax.faces.context.FacesContext;

import org.hibernate.SessionFactory;

import entite.ParametreConnectionC;
import persistances.DBConfiguration;


 
 
 
 @ManagedBean
 @ViewScoped
 public class ParametreConnectionB
   extends ParametreConnectionC
 {
   private static final long serialVersionUID = -6756194751191247346L;
   ParametreConnectionC parametre;
   private String user;
   private String pwd;
   private String serveur;
   private String dataBase;
   private String infoMsg;
   private boolean showMsg;
   
   @PostConstruct
   private void init() {
     this.parametre = new ParametreConnectionC();
     chargerParameter();
   }
   
   public String getUser() {
     return this.user;
   }
   
   public void setUser(String user) {
     this.user = user;
   }
   
   public String getPwd() {
     return this.pwd;
   }
   
   public void setPwd(String pwd) {
     this.pwd = pwd;
   }
   
   public String getServeur() {
     return this.serveur;
   }
   
   public void setServeur(String serveur) {
     this.serveur = serveur;
   }
   
   public String getDataBase() {
     return this.dataBase;
   }
   
   public void setDataBase(String dataBase) {
     this.dataBase = dataBase;
   }
   
   public String getInfoMsg() {
     return this.infoMsg;
   }
   
   public void setInfoMsg(String infoMsg) {
     this.infoMsg = infoMsg;
   }
   
   public boolean isShowMsg() {
     return this.showMsg;
   }
   
   public void setShowMsg(boolean showMsg) {
     this.showMsg = showMsg;
   }
 
   
   public void returnLogin() {
    
     FacesContext context = FacesContext.getCurrentInstance();
     
     try {
       context.getExternalContext().redirect("/comptabilite/login.jsf");
     } catch (IOException e) {
       
       e.printStackTrace();
     } 
   }
 
   
   public void testConnection() {
	   SessionFactory factory = DBConfiguration.getSessionFactory();
     if (factory != null) {
       this.infoMsg = "Connexion réussie!";
     } else {
       this.infoMsg = "Connexion échouée !";
     } 
     this.showMsg = true;
   }
 
   
   private void chargerParameter() {
     this.parametre.readChaine();
     this.serveur = this.parametre.getNomHost();
     this.dataBase = this.parametre.getNomBaseDeDonnee();
     this.user = this.parametre.getNomUtilisateur();
     this.pwd = this.parametre.getMotDePasse();
   }
   
   public void save() {
     this.infoMsg = "";
     this.showMsg = false;
     this.parametre.setMotDePasse(this.pwd);
     this.parametre.setNomBaseDeDonnee(this.dataBase);
     this.parametre.setNomHost(this.serveur);
     this.parametre.setNomUtilisateur(this.user);
     this.infoMsg = this.parametre.enregistrer();
     this.showMsg = true;
   }
 }

