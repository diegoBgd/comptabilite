 package vewBean;
 
 import entite.Exercice;
 import entite.Journal;
 import entite.ParametreImmo;
 import entite.User;
 import java.io.IOException;
 import java.io.Serializable;
 import java.util.List;
 import javax.annotation.PostConstruct;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import javax.faces.context.FacesContext;
 import javax.servlet.http.HttpSession;
 import model.ExerciceModel;
 import model.JournalModel;
 import model.ParametreImmoModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import org.primefaces.PrimeFaces;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 @ManagedBean
 @ViewScoped
 public class ParametreImmoVew
   implements Serializable
 {
   private static final long serialVersionUID = -7028286660720933865L;
   private boolean disableMsg;
   private Journal selectedJrnlAmrt;
   private String codeJrnl;
   private String libelleJrnl;
   private String motRech;
   private String numMsg;
   private List<Journal> listJournal;
   private ParametreImmo selectedParm;
   private double nonAmrtValue;
   Exercice selecetdExercice;
   HttpSession session;
   String exerCode;
   String currUserCode;
   User currentUser;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   ParametreImmoModel model;
   int idPrm = 0;
 
 
 
 
   
   public List<Journal> getListJournal() {
     return this.listJournal;
   }
   
   public void setListJournal(List<Journal> listJournal) {
     this.listJournal = listJournal;
   }
   
   public boolean isDisableMsg() {
     return this.disableMsg;
   }
   
   public void setDisableMsg(boolean disableMsg) {
     this.disableMsg = disableMsg;
   }
   
   public Journal getSelectedJrnlAmrt() {
     return this.selectedJrnlAmrt;
   }
   
   public void setSelectedJrnlAmrt(Journal selectedJrnlAmrt) {
     this.selectedJrnlAmrt = selectedJrnlAmrt;
   }
   
   public String getCodeJrnl() {
     return this.codeJrnl;
   }
   
   public void setCodeJrnl(String codeJrnl) {
     this.codeJrnl = codeJrnl;
   }
   
   public String getLibelleJrnl() {
     return this.libelleJrnl;
   }
   
   public void setLibelleJrnl(String libelleJrnl) {
     this.libelleJrnl = libelleJrnl;
   }
   
   public ParametreImmo getSelectedParm() {
     return this.selectedParm;
   }
   
   public void setSelectedParm(ParametreImmo selectedParm) {
     this.selectedParm = selectedParm;
   }
   
   public String getMotRech() {
     return this.motRech;
   }
   
   public void setMotRech(String motRech) {
     this.motRech = motRech;
   }
   
   public String getNumMsg() {
     return this.numMsg;
   }
   
   public void setNumMsg(String numMsg) {
     this.numMsg = numMsg;
   }
   
   public double getNonAmrtValue() {
     return this.nonAmrtValue;
   }
   
   public void setNonAmrtValue(double nonAmrtValue) {
     this.nonAmrtValue = nonAmrtValue;
   }
 
   
   @PostConstruct
   public void initialize() {
     this.model = new ParametreImmoModel();
     this.disableMsg = true;
     chargementSession();
     chargerParameter();
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
       }
     } 
   }
   
   public void chargerJournal() {
     this.listJournal = (new JournalModel()).getListJouranl(this.factory, this.motRech);
     this.numMsg = String.valueOf(this.listJournal.size()) + " éléments trouvés";
   }
   
   private void chargerParameter() {
     this.selectedParm = this.model.getParmImmo(this.factory);
     if (this.selectedParm != null) {
       
       this.idPrm = this.selectedParm.getIdParm();
       this.nonAmrtValue = this.selectedParm.getValeurNonAmort();
       this.selectedJrnlAmrt = this.selectedParm.getJournalAmort();
       getSelctedJournal();
       this.disableMsg = false;
     } 
   }
   
   public void getSelctedJournal() {
     if (this.selectedJrnlAmrt != null) {
       
       this.codeJrnl = this.selectedJrnlAmrt.getCodeJrnl();
       this.libelleJrnl = this.selectedJrnlAmrt.getLibelle();
       PrimeFaces.current().executeScript("PF('dlgJrnl').hide();");
     } 
   }
   
   public void searchJournal() {
     if (this.codeJrnl != null && !this.codeJrnl.equals("")) {
       
       this.selectedJrnlAmrt = (new JournalModel()).getJouralByCode(this.factory, this.codeJrnl);
       if (this.selectedJrnlAmrt != null) {
         
         this.codeJrnl = this.selectedJrnlAmrt.getCodeJrnl();
         this.libelleJrnl = this.selectedJrnlAmrt.getLibelle();
       } 
     } 
   }
   
   public void save() {
     if (this.selectedParm == null)
       this.selectedParm = new ParametreImmo(); 
     this.selectedParm.setIdParm(this.idPrm);
     this.selectedParm.setJournalAmort(this.selectedJrnlAmrt);
     this.selectedParm.setValeurNonAmort(this.nonAmrtValue);
     
     if (this.selectedParm.getIdParm() == 0) {
       this.model.saveParmImmo(this.factory, this.selectedParm);
     } else {
       this.model.updateParmImmo(this.factory, this.selectedParm);
     } 
   }
   
   public void delete() {
     if (this.selectedParm != null && this.selectedParm.getIdParm() > 0)
     {
       this.model.deleteParmImmo(this.factory, this.selectedParm);
     }
   }
   
   public void initializeControl() {
     this.idPrm = 0;
     this.selectedJrnlAmrt = null;
     this.selectedParm = null;
     this.codeJrnl = "";
     this.libelleJrnl = "";
   }
 }


