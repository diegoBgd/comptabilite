 package vewBean;
 
 import entite.Exercice;
 import entite.User;
 import java.io.IOException;
 import java.io.Serializable;
 import java.util.Date;
 import java.util.List;
 import javax.annotation.PostConstruct;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import javax.faces.context.FacesContext;
 import javax.servlet.http.HttpSession;
 import model.ExerciceModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import org.primefaces.PrimeFaces;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 
 
 
 
 @ManagedBean
 @ViewScoped
 public class ExerciceVew
   implements Serializable
 {
   private static final long serialVersionUID = 6828921214873303358L;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   
   private String printDateDebut;
   
   private String printDateFin;
   
   private String libelleExPrcd;
   
   private String printDateDbPrcd;
   
   private String printDateFnPrcd;
   private List<Exercice> listExercice;
   private Exercice selectedExercice;
   private boolean disableMsg;
   private boolean disableSave;
   private String code;
   private String designation;
   int idEx = 0; 
   private String codePrcd; 
   private Date dateDb; 
   private Date dateFn; 
   ExerciceModel model; 
   Exercice exercicePrcd; 
   Exercice selecetdExercice; 
   HttpSession session;
   String exerCode;
   String currUserCode;
   User currentUser;
   boolean exist = true;
   
   public Exercice getSelectedExercice() {
     return this.selectedExercice;
   }
   
   public void setSelectedExercice(Exercice selectedExercice) {
     this.selectedExercice = selectedExercice;
   }
   
   public String getPrintDateDebut() {
     return this.printDateDebut;
   }
   
   public void setPrintDateDebut(String printDateDebut) {
     this.printDateDebut = printDateDebut;
   }
   
   public String getPrintDateFin() {
     return this.printDateFin;
   }
   
   public void setPrintDateFin(String printDateFin) {
     this.printDateFin = printDateFin;
   }
   
   public String getLibelleExPrcd() {
     return this.libelleExPrcd;
   }
   
   public void setLibelleExPrcd(String libelleExPrcd) {
     this.libelleExPrcd = libelleExPrcd;
   }
   
   public String getPrintDateDbPrcd() {
     return this.printDateDbPrcd;
   }
   
   public void setPrintDateDbPrcd(String printDateDbPrcd) {
     this.printDateDbPrcd = printDateDbPrcd;
   }
   
   public String getPrintDateFnPrcd() {
     return this.printDateFnPrcd;
   }
   
   public void setPrintDateFnPrcd(String printDateFnPrcd) {
     this.printDateFnPrcd = printDateFnPrcd;
   }
   
   public boolean isDisableMsg() {
     return this.disableMsg;
   }
   
   public void setDisableMsg(boolean disableMsg) {
     this.disableMsg = disableMsg;
   }
   
   public boolean isDisableSave() {
     return this.disableSave;
   }
   
   public void setDisableSave(boolean disableSave) {
     this.disableSave = disableSave;
   }
   public List<Exercice> getListExercice() {
     return this.listExercice;
   }
   
   public void setListExercice(List<Exercice> listExercice) {
     this.listExercice = listExercice;
   }
   
   public String getCode() {
     return this.code;
   }
   
   public void setCode(String code) {
     this.code = code;
   }
   
   public String getDesignation() {
     return this.designation;
   }
   
   public void setDesignation(String designation) {
     this.designation = designation;
   }
   
   public Date getDateDb() {
     return this.dateDb;
   }
   
   public void setDateDb(Date dateDb) {
     this.dateDb = dateDb;
   }
   
   public Date getDateFn() {
     return this.dateFn;
   }
   
   public void setDateFn(Date dateFn) {
     this.dateFn = dateFn;
   }
   
   public String getCodePrcd() {
     return this.codePrcd;
   }
   
   public void setCodePrcd(String codePrcd) {
     this.codePrcd = codePrcd;
   }
   
   @PostConstruct
   public void initialize() {
     this.model = new ExerciceModel();
     chargementSession();
     this.session = HelperC.getSession();
   }
 
   
   private void chargementSession() {
     this.session = HelperC.getSession();
     if (this.session != null) {
       this.exerCode = (String)this.session.getAttribute("exercice");
       this.currUserCode = (String)this.session.getAttribute("operateur");
       this.exist = Boolean.getBoolean(this.session.getAttribute("existUser").toString());
       if (this.exerCode != null) {
         this.selecetdExercice = (new ExerciceModel()).getExercByCode(this.factory, this.exerCode);
       }
       if (this.currUserCode != null)
       {
         this.currentUser = (new UserModel()).getUserByCode(this.factory, this.currUserCode);
       }
       if (this.exist)
       {
         if (this.currentUser == null || this.selecetdExercice == null) {
           
           try {
             FacesContext.getCurrentInstance().getExternalContext().redirect("/comptabilite/login.xhtml");
           } catch (IOException e) {
             
             e.printStackTrace();
           }
         
         } else {
           
           this.code = this.exerCode;
           chargerExercice();
         } 
       }
     } 
   }
 
 
   
   public void allExercises() {
     this.listExercice = this.model.getListExercice(this.factory);
   }
   public void searchExercice() {
     chargerExercice();
   }
   
   private void chargerExercice() {
     this.disableMsg = true;
     this.selectedExercice = this.model.getExercByCode(this.factory, this.code);
     if (this.selectedExercice != null) {
       getExerciseValues();
     }
   }
   
   public void getSelectedExercise() {
     if (this.selectedExercice != null) {
       
       getExerciseValues();
       PrimeFaces.current().executeScript("PF('dlgResearch').hide();");
     } 
   }
   
   private void getExerciseValues() {
     this.idEx = this.selectedExercice.getId();
     this.dateDb = this.selectedExercice.getDateDebut();
     this.dateFn = this.selectedExercice.getDateFin();
     this.code = this.selectedExercice.getExCode();
     this.codePrcd = this.selectedExercice.getExPrcdCode();
     this.printDateDebut = HelperC.convertDate(this.selectedExercice.getDateDebut());
     this.printDateFin = HelperC.convertDate(this.selectedExercice.getDateFin());
     this.designation = this.selecetdExercice.getLibelle();
     
     if (this.selectedExercice.getExPrcdCode() != null && !this.selectedExercice.getExPrcdCode().equals("")) {
       this.exercicePrcd = this.model.getExercByCode(this.factory, this.selectedExercice.getExPrcdCode());
       if (this.exercicePrcd != null) {
         this.libelleExPrcd = this.exercicePrcd.getLibelle();
         this.printDateDbPrcd = HelperC.convertDate(this.exercicePrcd.getDateDebut());
         this.printDateFnPrcd = HelperC.convertDate(this.exercicePrcd.getDateFin());
       } 
     } 
     this.disableMsg = false;
   }
 
   
   public void searchExercicePrcd() {
     this.disableSave = false;
     if (this.code != null && !this.code.equals("")) {
       
       if (!this.codePrcd.equals(this.code)) {
         
         this.exercicePrcd = this.selectedExercice = this.model.getExercByCode(this.factory, this.codePrcd);
         if (this.exercicePrcd != null) {
           
           this.libelleExPrcd = this.exercicePrcd.getLibelle();
           this.printDateDbPrcd = HelperC.convertDate(this.exercicePrcd.getDateDebut());
           this.printDateFnPrcd = HelperC.convertDate(this.exercicePrcd.getDateFin());
         }
         else {
           
           this.libelleExPrcd = "";
           this.printDateDbPrcd = "";
           this.printDateFnPrcd = "";
         } 
       } else {
         
         HelperC.afficherAttention("Attention", "L'exercice encours doit être différent de l'exercice précédent");
         this.disableSave = true;
       } 
     } else {
       
       HelperC.afficherAttention("Attention", "Il faut préciser la référence !");
     } 
   } public void changeDateDebut() {
     if (this.printDateDebut.replace("/", "").replace("_", "").trim().equals("")) {
       
       this.dateDb = null;
     }
     else {
       
       this.dateDb = HelperC.validerDate(this.printDateDebut);
       if (this.dateDb == null) {
         this.disableSave = true;
         this.printDateDebut = "";
         HelperC.afficherMessage("Information", "Date invalide!");
       } else {
         
         this.printDateDebut = HelperC.convertDate(this.dateDb);
       } 
     } 
   }
   
   public void changeDateFin() {
     if (this.printDateFin.replace("/", "").replace("_", "").trim().equals("")) {
       this.dateFn = null;
     }
     else {
       
       this.dateFn = HelperC.validerDate(this.printDateFin);
       if (this.dateFn == null) {
         this.disableSave = true;
         this.printDateFin = "";
         HelperC.afficherMessage("Information", "Date invalide!");
       } else {
         
         this.printDateFin = HelperC.convertDate(this.dateFn);
       } 
     } 
   }
   
   public void save() {
     if (this.selectedExercice == null)
       this.selectedExercice = new Exercice(); 
     this.selectedExercice.setId(this.idEx);
     this.selectedExercice.setExCode(this.code);
     this.selectedExercice.setDateDebut(this.dateDb);
     this.selectedExercice.setDateFin(this.dateFn);
     this.selectedExercice.setLibelle(this.designation);
     this.selectedExercice.setExPrcdCode(this.codePrcd);
     
     if (this.selectedExercice.getId() == 0) {
       this.model.saveExercice(this.factory, this.selectedExercice);
     } else {
       this.model.updateExercice(this.factory, this.selectedExercice);
     } 
   }
   
   public void delete() {
     if (this.selectedExercice != null)
     {
       this.model.deleteExercice(this.factory, this.selectedExercice);
     }
   }
   
   public void initialiseControls() {
     this.idEx = 0;
     this.dateDb = null;
     this.dateFn = null;
     this.code = "";
     this.codePrcd = "";
     this.designation = "";
     this.libelleExPrcd = "";
     this.printDateDbPrcd = "";
     this.printDateFnPrcd = "";
     this.printDateDebut = "";
     this.printDateFin = "";
     this.disableMsg = true;
     this.disableSave = false;
     this.selectedExercice = null;
   }
 }


