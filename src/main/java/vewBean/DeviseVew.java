 package vewBean;
 
 import entite.Devise;
 import entite.Exercice;
 import entite.User;
 import java.io.IOException;
 import java.io.Serializable;
 import java.util.List;
 import javax.annotation.PostConstruct;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import javax.faces.context.FacesContext;
 import javax.servlet.http.HttpSession;
 import model.DeviseModel;
 import model.ExerciceModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 
 
 
 
 
 @ManagedBean
 @ViewScoped
 public class DeviseVew
   implements Serializable
 {
   private static final long serialVersionUID = -8567813602036244659L;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   
   private Devise selectedDevise;
   
   private List<Devise> listDevise;
   
   private boolean disableMsg;
   
   private String code;
   private String designation;
   private String abrv;
   int idDev = 0; DeviseModel model; Exercice selecetdExercice;
   HttpSession session;
   String exerCode;
   String currUserCode;
   User currentUser;
   
   public Devise getSelectedDevise() {
     return this.selectedDevise;
   }
   
   public void setSelectedDevise(Devise selectedDevise) {
     this.selectedDevise = selectedDevise;
   }
   
   public List<Devise> getListDevise() {
     return this.listDevise;
   }
   
   public void setListDevise(List<Devise> listDevise) {
     this.listDevise = listDevise;
   }
   
   public boolean isDisableMsg() {
     return this.disableMsg;
   }
   
   public void setDisableMsg(boolean disableMsg) {
     this.disableMsg = disableMsg;
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
   
   public String getAbrv() {
     return this.abrv;
   }
   
   public void setAbrv(String abrv) {
     this.abrv = abrv;
   }
 
   
   @PostConstruct
   public void initialize() {
     this.model = new DeviseModel();
     this.disableMsg = true;
     chargementSession();
     chargerDevise();
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
   
   private void chargerDevise() {
     this.listDevise = this.model.getListDevise(this.factory, "");
   }
   
   public void searchDevise() {
     if (this.code != null && !this.code.equals("")) {
       
       this.selectedDevise = this.model.getDeviseByCode(this.factory, this.code);
       if (this.selectedDevise != null) {
         setDeviseValues();
       }
     } 
   }
   
   public void getSelectedDeviseValue() {
     if (this.selectedDevise != null)
       setDeviseValues(); 
   }
   
   private void setDeviseValues() {
     this.idDev = this.selectedDevise.getId();
     this.code = this.selectedDevise.getCodeDev();
     this.designation = this.selectedDevise.getLibelle();
     this.abrv = this.selectedDevise.getSymbole();
     this.disableMsg = false;
   }
 
   
   public void save() {
     if (this.code != null && !this.code.equals("")) {
       
       if (this.selectedDevise == null)
         this.selectedDevise = new Devise(); 
       this.selectedDevise.setCodeDev(this.code);
       this.selectedDevise.setId(this.idDev);
       this.selectedDevise.setLibelle(this.designation);
       this.selectedDevise.setSymbole(this.abrv);
       
       if (this.selectedDevise.getId() == 0) {
         this.model.saveDevise(this.factory, this.selectedDevise);
       } else {
         this.model.updateDevise(this.factory, this.selectedDevise);
       }  chargerDevise();
       initializeControls();
     } else {
       
       HelperC.afficherAttention("Attention", "Il faut préciser la référence !");
     } 
   }
   
   public void delete() {
     if (this.selectedDevise != null && this.selectedDevise.getId() > 0) {
       
       this.model.deleteDevise(this.factory, this.selectedDevise);
       chargerDevise();
       initializeControls();
     } else {
       
       HelperC.afficherDeleteMessage();
     } 
   }
   
   public void initializeControls() {
     this.idDev = 0;
     this.code = "";
     this.designation = "";
     this.abrv = "";
     this.disableMsg = true;
     this.selectedDevise = null;
   }
 }


