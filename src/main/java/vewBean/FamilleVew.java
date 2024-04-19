 package vewBean;
 
 import entite.Exercice;
 import entite.FamilleProduit;
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
 import model.FamilleModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 
 
 
 
 @ManagedBean
 @ViewScoped
 public class FamilleVew
   implements Serializable
 {
   private static final long serialVersionUID = -3304144328641162127L;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   
   private FamilleProduit selecetdFamille;
   
   private List<FamilleProduit> listFamille;
   
   private boolean disableMsg;
   private String code;
   private String designation;
   private String cptStock;
   private String cptVente;
   int idFm = 0;
   
   private String cptChrg;
   FamilleModel model;
   HttpSession session;
   
   public FamilleProduit getSelecetdFamille() {
     return this.selecetdFamille;
   }
   String exerCode; String currUserCode; User currentUser; Exercice selecetdExercice;
   public void setSelecetdFamille(FamilleProduit selecetdFamille) {
     this.selecetdFamille = selecetdFamille;
   }
   
   public List<FamilleProduit> getListFamille() {
     return this.listFamille;
   }
   
   public void setListFamille(List<FamilleProduit> listFamille) {
     this.listFamille = listFamille;
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
   
   public String getCptStock() {
     return this.cptStock;
   }
   
   public void setCptStock(String cptStock) {
     this.cptStock = cptStock;
   }
   
   public String getCptVente() {
     return this.cptVente;
   }
   
   public void setCptVente(String cptVente) {
     this.cptVente = cptVente;
   }
   
   public String getCptChrg() {
     return this.cptChrg;
   }
   
   public void setCptChrg(String cptChrg) {
     this.cptChrg = cptChrg;
   }
 
   
   @PostConstruct
   public void initialize() {
     this.model = new FamilleModel();
     this.disableMsg = true;
     chargementSession();
     chargerFamille();
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
 
   
   private void chargerFamille() {
     this.listFamille = this.model.getListFamille(this.factory, "");
   }
 
   
   public void searchFamille() {
     if (this.code != null && !this.code.equals("")) {
       
       this.selecetdFamille = this.model.getFamilleByCode(this.factory, this.code);
       getFamilleValues();
     } 
   }
 
   
   public void getSelectedFamilleValue() {
     getFamilleValues();
   }
   
   private void getFamilleValues() {
     this.disableMsg = true;
     if (this.selecetdFamille != null) {
       
       this.code = this.selecetdFamille.getCodeFm();
       this.designation = this.selecetdFamille.getLibelle();
       this.idFm = this.selecetdFamille.getId();
       this.disableMsg = false;
     } 
   }
 
   
   public void save() {
     if (this.code != null && !this.code.equals("")) {
       
       if (this.selecetdFamille == null)
         this.selecetdFamille = new FamilleProduit(); 
       this.selecetdFamille.setId(this.idFm);
       this.selecetdFamille.setCodeFm(this.code);
       this.selecetdFamille.setLibelle(this.designation);
       this.selecetdFamille.setCompteSortie(this.cptChrg);
       this.selecetdFamille.setCompteStock(this.cptStock);
       this.selecetdFamille.setCompteVente(this.cptVente);
       
       if (this.selecetdFamille.getId() == 0) {
         this.model.saveFamille(this.factory, this.selecetdFamille);
       } else {
         this.model.updateFamille(this.factory, this.selecetdFamille);
       }  chargerFamille();
       initializeControl();
     } 
   }
 
   
   public void delete() {
     if (this.selecetdFamille != null && this.selecetdFamille.getId() > 0) {
       
       this.model.deleteFamille(this.factory, this.selecetdFamille);
       chargerFamille();
       initializeControl();
     } 
   }
 
   
   public void initializeControl() {
     this.idFm = 0;
     this.code = "";
     this.designation = "";
     this.disableMsg = true;
     this.selecetdFamille = null;
   }
 }


