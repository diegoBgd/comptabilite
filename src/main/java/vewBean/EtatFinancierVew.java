 package vewBean;
 
 import entite.EtatFinancier;
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
 import model.EtatFinancierModel;
 import model.ExerciceModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 @ManagedBean
 @ViewScoped
 public class EtatFinancierVew
   implements Serializable
 {
   private static final long serialVersionUID = 4012060847807859549L;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   
   private EtatFinancier selecredEFi;
   private List<EtatFinancier> listEFi;
   private boolean disableMsg;
   private String codeSt;
   private String designation;
   private int type;
   EtatFinancierModel model;
   Exercice selecetdExercice;
   HttpSession session;
   String exerCode;
   String currUserCode;
   User currentUser;
   int idSt = 0;
 
 
 
 
   
   public EtatFinancier getSelecredEFi() {
     return this.selecredEFi;
   }
 
   
   public void setSelecredEFi(EtatFinancier selecredEFi) {
     this.selecredEFi = selecredEFi;
   }
 
   
   public List<EtatFinancier> getListEFi() {
     return this.listEFi;
   }
 
   
   public void setListEFi(List<EtatFinancier> listEFi) {
     this.listEFi = listEFi;
   }
 
   
   public boolean isDisableMsg() {
     return this.disableMsg;
   }
 
   
   public void setDisableMsg(boolean disableMsg) {
     this.disableMsg = disableMsg;
   }
   
   public String getCodeSt() {
     return this.codeSt;
   }
 
   
   public void setCodeSt(String codeSt) {
     this.codeSt = codeSt;
   }
 
   
   public String getDesignation() {
     return this.designation;
   }
   
   public void setDesignation(String designation) {
     this.designation = designation;
   }
 
   
   public int getType() {
     return this.type;
   }
 
   
   public void setType(int type) {
     this.type = type;
   }
 
 
   
   @PostConstruct
   public void initilaze() {
     this.model = new EtatFinancierModel();
     this.disableMsg = true;
     chargementSession();
     chargerEtatFinancier();
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
   
   private void chargerEtatFinancier() {
     this.listEFi = this.model.getListEFi(this.factory);
   }
 
   
   public void searchEtatFinancier() {
     this.disableMsg = true;
     
     if (this.codeSt != null && !this.codeSt.equals("")) {
       
       this.selecredEFi = this.model.getEFiByCode(this.factory, this.codeSt);
       if (this.selecredEFi != null) {
         getEFivalues();
       }
     } 
   }
   
   public void getSelectedEFiValue() {
     if (this.selecredEFi != null) {
       getEFivalues();
     }
   }
   
   private void getEFivalues() {
     this.codeSt = this.selecredEFi.getCode();
     this.idSt = this.selecredEFi.getId();
     this.designation = this.selecredEFi.getLibelle();
     this.type = this.selecredEFi.getTypeEtat();
     this.disableMsg = false;
   }
 
   
   public void save() {
     if (this.codeSt != null && !this.codeSt.equals("")) {
       
       if (this.selecredEFi == null)
         this.selecredEFi = new EtatFinancier(); 
       this.selecredEFi.setCode(this.codeSt);
       this.selecredEFi.setId(this.idSt);
       this.selecredEFi.setLibelle(this.designation);
       this.selecredEFi.setTypeEtat(this.type);
       if (this.selecredEFi.getId() == 0) {
         this.model.saveEtatFinancier(this.factory, this.selecredEFi);
       } else {
         this.model.updateEtatFinancier(this.factory, this.selecredEFi);
       }  initializeControl();
       chargerEtatFinancier();
     } else {
       
       HelperC.afficherAttention("Attention", "Il faut préciser la référence !");
     } 
   }
   
   public void delete() {
     if (this.selecredEFi != null && this.selecredEFi.getId() > 0) {
       
       this.model.deleteEtatFinancier(this.factory, this.selecredEFi);
       initializeControl();
       chargerEtatFinancier();
     } 
   }
 
   
   public void initializeControl() {
     this.idSt = 0;
     this.codeSt = "";
     this.designation = "";
     this.disableMsg = true;
     this.selecredEFi = null;
   }
 }


