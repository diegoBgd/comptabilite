 package vewBean;
 
 import entite.CentreCout;
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
 import model.CentreCoutModel;
 import model.ExerciceModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 
 
 
 @ManagedBean
 @ViewScoped
 public class CentreCoutVew
   implements Serializable
 {
   private static final long serialVersionUID = -3310956426941341162L;
   private CentreCout selectedCentre;
   private List<CentreCout> listCentre;
   private boolean disableMsg;
   private String codeCentre;
   private String designation;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   CentreCoutModel model;
   Exercice selecetdExercice;
   HttpSession session;
   String exerCode;
   String currUserCode;
   User currentUser;
   int idCentre = 0;
 
 
 
 
   
   public CentreCout getSelectedCentre() {
     return this.selectedCentre;
   }
   
   public void setSelectedCentre(CentreCout selectedCentre) {
     this.selectedCentre = selectedCentre;
   }
   
   public List<CentreCout> getListCentre() {
     return this.listCentre;
   }
   
   public void setListCentre(List<CentreCout> listCentre) {
     this.listCentre = listCentre;
   }
   
   public boolean isDisableMsg() {
     return this.disableMsg;
   }
   
   public void setDisableMsg(boolean disableMsg) {
     this.disableMsg = disableMsg;
   }
   
   public String getCodeCentre() {
     return this.codeCentre;
   }
   
   public void setCodeCentre(String codeCentre) {
     this.codeCentre = codeCentre;
   }
   
   public String getDesignation() {
     return this.designation;
   }
   
   public void setDesignation(String designation) {
     this.designation = designation;
   }
 
   
   @PostConstruct
   public void initialize() {
     this.model = new CentreCoutModel();
     this.disableMsg = true;
     chargerCentre();
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
       }
     } 
   }
   
   private void chargerCentre() {
     this.listCentre = this.model.getListCentreCout(this.factory);
   }
 
   
   public void searchCentreCout() {
     if (this.codeCentre != null && !this.codeCentre.equals("")) {
       
       this.selectedCentre = this.model.getCentreCoutByCode(this.factory, this.codeCentre);
       if (this.selectedCentre != null)
         getCentreValue(); 
     } 
   }
   public void getSelectedCentreValue() {
     if (this.selectedCentre != null) {
       getCentreValue();
     }
   }
   
   private void getCentreValue() {
     this.codeCentre = this.selectedCentre.getCode();
     this.designation = this.selectedCentre.getLibelle();
     this.idCentre = this.selectedCentre.getId();
     this.disableMsg = false;
   }
 
   
   public void save() {
     if (this.codeCentre != null && !this.designation.equals("")) {
       
       if (this.selectedCentre == null)
         this.selectedCentre = new CentreCout(); 
       this.selectedCentre.setCode(this.codeCentre);
       this.selectedCentre.setId(this.idCentre);
       this.selectedCentre.setLibelle(this.designation);
       
       if (this.selectedCentre.getId() == 0) {
         this.model.saveCentreCout(this.factory, this.selectedCentre);
       } else {
         this.model.updateCentreCout(this.factory, this.selectedCentre);
       }  chargerCentre();
       initializeControl();
     } else {
       
       HelperC.afficherAttention("Attention", "Il faut préciser la référence !");
     } 
   }
   
   public void delete() {
     if (this.selectedCentre != null && this.selectedCentre.getId() > 0) {
       
       this.model.deleteCentreCout(this.factory, this.selectedCentre);
       chargerCentre();
       initializeControl();
     } 
   }
 
   
   public void initializeControl() {
     setCodeCentre("");
     this.designation = "";
     this.idCentre = 0;
     this.disableMsg = true;
     this.selectedCentre = null;
   }
 }


