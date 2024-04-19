 package vewBean;
 
 import entite.Amortissement;
 import entite.CalculAmortissement;
 import entite.Exercice;
 import entite.Immobilise;
 import entite.Journal;
 import entite.ParametreImmo;
 import entite.User;
 import java.io.IOException;
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.List;
 import javax.annotation.PostConstruct;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import javax.faces.context.FacesContext;
 import javax.servlet.http.HttpSession;
 import model.AmortisementModel;
 import model.ExerciceModel;
 import model.ImmobiliseModel;
 import model.ParametreImmoModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 
 
 
 
 
 @ManagedBean
 @ViewScoped
 public class AmortisementVew
   implements Serializable
 {
   private static final long serialVersionUID = -7227317408164104141L;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   private List<Amortissement> listAmrt;
   private int progressValue;
   List<Immobilise> listImmo;
   Exercice selecetdExercice;
   HttpSession session;
   String exerCode;
   String currUserCode;
   User currentUser;
   ImmobiliseModel immoMdl;
   CalculAmortissement calAmrt;
   Amortissement amort;
   Journal jrnalAmrt;
   User utilisteur;
   AmortisementModel model;
   ParametreImmo parametre;
   
   public List<Amortissement> getListAmrt() {
     return this.listAmrt;
   }
   public void setListAmrt(List<Amortissement> listAmrt) {
     this.listAmrt = listAmrt;
   }
   
   public int getProgressValue() {
     return this.progressValue;
   }
   public void setProgressValue(int progressValue) {
     this.progressValue = progressValue;
   }
 
   
   @PostConstruct
   public void initialize() {
     chargementSession();
   }
   private void chargementSession() {
     this.session = HelperC.getSession();
     if (this.session != null) {
       this.exerCode = (String)this.session.getAttribute("exercice");
       this.currUserCode = (String)this.session.getAttribute("operateur");
       
       if (this.exerCode != null) {
         this.selecetdExercice = (new ExerciceModel()).getExercByCode(this.factory, this.exerCode);
       }
       if (this.currUserCode != null)
       {
         this.currentUser = (new UserModel()).getUserByCode(this.factory, this.currUserCode);
       }
       
       if (this.currentUser == null || this.selecetdExercice == null) {
         
         try {
           FacesContext.getCurrentInstance().getExternalContext().redirect("/comptabilite/login.xhtml");
         } catch (IOException e) {
           
           e.printStackTrace();
         } 
       } else {
         
         this.calAmrt = new CalculAmortissement();
         this.model = new AmortisementModel();
         chargerarametre();
       } 
     } 
   }
   
   private void chargerarametre() {
     this.parametre = (new ParametreImmoModel()).getParmImmo(this.factory);
     if (this.parametre != null)
       this.jrnalAmrt = this.parametre.getJournalAmort(); 
   }
   private void chargerImmo() {
     this.immoMdl = new ImmobiliseModel();
     this.listImmo = this.immoMdl.getListImmo(this.factory, "");
   }
   public void calculAmortissement() throws InterruptedException {
     this.listAmrt = new ArrayList<>();
     int i = 0;
     chargerImmo();
     if (this.listImmo.size() > 0) {
       
       this.progressValue = 0;
       
       for (Immobilise immo : this.listImmo) {
         
         this.progressValue = i * 100 / this.listImmo.size();
         
         this.calAmrt.tableauAmortissemet(immo, immo.getNbrAnne(), this.selecetdExercice);
         this.amort = this.calAmrt.amortissementEncours(this.selecetdExercice.getExCode());
         immo.setAmortEncour(this.amort.getAmortExercice());
         this.amort.setImmo(immo);
         this.amort.setExercice(this.selecetdExercice);
         
         if (this.jrnalAmrt != null) {
           
           this.calAmrt.listEcriture(immo, this.jrnalAmrt, this.selecetdExercice.getId(), "amortissement Immo :" + immo.getLibelle() + ": Exercice " + this.selecetdExercice.getExCode(), this.selecetdExercice.getDateFin(), this.currentUser);
           
           this.amort.setListEcriture(this.calAmrt.getListEcriture());
         } 
         i++;
         this.amort.setNumero(i);
         this.listAmrt.add(this.amort);
         
         Thread.sleep(60L);
       } 
     } 
   }
 
   
   public void save() {
     if (this.listAmrt.size() > 0)
     {
       this.model.saveAmortissement(this.factory, this.listAmrt);
     }
   }
 
   
   public void delete() {
     this.model.deleteAmortissemnt(this.factory, this.selecetdExercice);
   }
   
   public void initializeControls() {
     this.listAmrt.clear();
   }
 }


