 package vewBean;
 
 import entite.Constante;
 import entite.Exercice;
 import entite.FonctionRole;
 import entite.User;
 import java.io.Serializable;
 import javax.annotation.PostConstruct;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import javax.servlet.http.HttpSession;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import persistances.DBConfiguration;
 import utils.HelperC;

 
 @ManagedBean
 @ViewScoped
 public class MenuVew
   implements Serializable
 {
   private static final long serialVersionUID = 1715041587664460395L;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   
   UserModel model;
   
   Exercice selecetdExercice;
   HttpSession session;
   String exerCode;
   String currUserCode;
   User currentUser;
   FonctionRole selectedRole;
   String roles;
   boolean exist = true;
   private boolean gestionAcces;
   private boolean parametreGeneraux;
   private boolean parametreEfi;
   private boolean journal;
   private boolean planComptable;
   private boolean saisieEcritures; 
   private boolean editionEfi; 
   private boolean editionHistoriques; 
   private boolean saisieImmo; 
   private boolean amortissement; 
   private boolean cession; 
   private boolean parametrageFinance; 
   private boolean entreeFond; 
   private boolean reglementClient; 
   private boolean factureFourn; 
   private boolean reglementFournisseur; 
   private boolean autresDepenses; 
   private boolean immo; 
   private boolean compta;
   
   public boolean isGestionAcces() {
     return this.gestionAcces;
   }
   public void setGestionAcces(boolean gestionAcces) {
     this.gestionAcces = gestionAcces;
   }
   
   public boolean isParametreGeneraux() {
     return this.parametreGeneraux;
   }
   
   public void setParametreGeneraux(boolean parametreGeneraux) {
     this.parametreGeneraux = parametreGeneraux;
   }
   
   public boolean isParametreEfi() {
     return this.parametreEfi;
   }
   
   public void setParametreEfi(boolean parametreEfi) {
     this.parametreEfi = parametreEfi;
   }
   
   public boolean isJournal() {
     return this.journal;
   }
   
   public void setJournal(boolean journal) {
     this.journal = journal;
   }
   
   public boolean isPlanComptable() {
     return this.planComptable;
   }
   
   public void setPlanComptable(boolean planComptable) {
     this.planComptable = planComptable;
   }
   
   public boolean isSaisieEcritures() {
     return this.saisieEcritures;
   }
   
   public void setSaisieEcritures(boolean saisieEcritures) {
     this.saisieEcritures = saisieEcritures;
   }
   
   public boolean isEditionEfi() {
     return this.editionEfi;
   }
   
   public void setEditionEfi(boolean editionEfi) {
     this.editionEfi = editionEfi;
   }
   
   public boolean isEditionHistoriques() {
     return this.editionHistoriques;
   }
   
   public void setEditionHistoriques(boolean editionHistoriques) {
     this.editionHistoriques = editionHistoriques;
   }
   
   public boolean isSaisieImmo() {
     return this.saisieImmo;
   }
   
   public void setSaisieImmo(boolean saisieImmo) {
     this.saisieImmo = saisieImmo;
   }
   
   public boolean isAmortissement() {
     return this.amortissement;
   }
   
   public void setAmortissement(boolean amortissement) {
     this.amortissement = amortissement;
   }
   
   public boolean isCession() {
     return this.cession;
   }
   
   public void setCession(boolean cession) {
     this.cession = cession;
   }
   
   public boolean isParametrageFinance() {
     return this.parametrageFinance;
   }
   
   public void setParametrageFinance(boolean parametrageFinance) {
     this.parametrageFinance = parametrageFinance;
   }
   
   public boolean isEntreeFond() {
     return this.entreeFond;
   }
   
   public void setEntreeFond(boolean entreeFond) {
     this.entreeFond = entreeFond;
   }
   
   public boolean isReglementClient() {
     return this.reglementClient;
   }
   
   public void setReglementClient(boolean reglementClient) {
     this.reglementClient = reglementClient;
   }
   
   public boolean isFactureFourn() {
     return this.factureFourn;
   }
   
   public void setFactureFourn(boolean factureFourn) {
     this.factureFourn = factureFourn;
   }
   
   public boolean isReglementFournisseur() {
     return this.reglementFournisseur;
   }
   
   public void setReglementFournisseur(boolean reglementFournisseur) {
     this.reglementFournisseur = reglementFournisseur;
   }
   
   public boolean isAutresDepenses() {
     return this.autresDepenses;
   }
   
   public void setAutresDepenses(boolean autresDepenses) {
     this.autresDepenses = autresDepenses;
   }
   
   public boolean isImmo() {
     return this.immo;
   }
   
   public void setImmo(boolean immo) {
     this.immo = immo;
   }
   
   public boolean isCompta() {
     return this.compta;
   }
   
   public void setCompta(boolean compta) {
     this.compta = compta;
   }
 
   
   @PostConstruct
   public void initialize() {
     this.model = new UserModel();
     chargementSession();
   }
   
   private void chargementSession() {
     this.session = HelperC.getSession();
     if (this.session != null) {
       this.exerCode = (String)this.session.getAttribute("exercice");
       this.currUserCode = (String)this.session.getAttribute("operateur");
       String ex = this.session.getAttribute("existUser").toString();
       
       if (ex.equals("true")) {
         
         if (this.currUserCode != null) {
           
           this.currentUser = (new UserModel()).getUserByCode(this.factory, this.currUserCode);
           if (this.currentUser != null) {
             
             this.selectedRole = this.currentUser.getRole();
             if (this.selectedRole != null) {
               
               this.roles = this.selectedRole.getAcces();
               selection();
             } 
           } 
         } 
       } else {
         
         this.gestionAcces = true;
       } 
     } 
   }
   
   private void selection() {
     String num = "";
     
     this.immo = false;
     this.compta = false;
     
     if (this.roles != null && !this.roles.equals("")) {
       
       String[] valeur = this.roles.split(" ");
       
       for (int i = 0; i < valeur.length; i++) {
         
         num = String.valueOf(valeur[i]);
         
         switch (Constante.getRole(Integer.valueOf(num).intValue())) {
           case GestionAcces:
             setGestionAcces(true);
             break;
           case ParametreGeneraux:
             setParametreGeneraux(true);
             break;
           case ParametreEfi:
             setParametreEfi(true);
             break;
           case Journal:
             setJournal(true);
             break;
           case PlanComptable:
             setPlanComptable(true);
             break;
           case SaisieEcritures:
             setSaisieEcritures(true);
             break;
           case EditionEfi:
             setEditionEfi(true);
             break;
           case EditionHistoriques:
             setEditionHistoriques(true);
             break;
           case SaisieImmo:
             setSaisieImmo(true);
             break;
           case Amortissement:
             setAmortissement(true);
             break;
           case Cession:
             setCession(true);
             break;
           case ParametrageFinance:
             setParametrageFinance(true);
             break;
           case EntreeFond:
             setEntreeFond(true);
             break;
           case ReglementClient:
             setReglementClient(true);
             break;
           case FactureFourn:
             setFactureFourn(true);
             break;
           case ReglementFournisseur:
             setReglementFournisseur(true);
             break;
           case AutresDepenses:
             setAutresDepenses(true);
             break;
         } 
 
       
       } 
       if (this.saisieImmo || this.amortissement || this.cession)
         this.immo = true; 
       if (this.parametreEfi || this.journal || this.planComptable || this.saisieEcritures || this.editionEfi || this.editionHistoriques)
         this.compta = true; 
     } 
   }
 }


