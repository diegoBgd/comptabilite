 package vewBean;
 
 import entite.Exercice;
 import entite.FamilleProduit;
 import entite.SoufamilleProd;
 import entite.User;
 import java.io.IOException;
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.List;
 import javax.annotation.PostConstruct;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import javax.faces.context.FacesContext;
 import javax.faces.event.ValueChangeEvent;
 import javax.faces.model.SelectItem;
 import javax.servlet.http.HttpSession;
 import model.ExerciceModel;
 import model.FamilleModel;
 import model.SouFamilleModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 
 
 
 @ManagedBean
 @ViewScoped
 public class SousFamilleProduitVew
   implements Serializable
 {
   private static final long serialVersionUID = 8961883387300874997L;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   private List<SoufamilleProd> listSousFamille;
   private SoufamilleProd selectedSouFml;
   private List<SelectItem> listFamille;
   private int idFml;
   private boolean disableMsg;
   private String code;
   private String designation;
   FamilleProduit selectedFml;
   SouFamilleModel model;
   HttpSession session;
   String exerCode;
   String currUserCode;
   User currentUser;
   Exercice selecetdExercice;
   int idSf = 0;
 
 
 
 
 
   
   public List<SoufamilleProd> getListSousFamille() {
     return this.listSousFamille;
   }
   
   public void setListSousFamille(List<SoufamilleProd> listSousFamille) {
     this.listSousFamille = listSousFamille;
   }
   
   public SoufamilleProd getSelectedSouFml() {
     return this.selectedSouFml;
   }
   
   public void setSelectedSouFml(SoufamilleProd selectedSouFml) {
     this.selectedSouFml = selectedSouFml;
   }
   
   public List<SelectItem> getListFamille() {
     return this.listFamille;
   }
   
   public void setListFamille(List<SelectItem> listFamille) {
     this.listFamille = listFamille;
   }
   
   public int getIdFml() {
     return this.idFml;
   }
   
   public void setIdFml(int idFml) {
     this.idFml = idFml;
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
 
 
 
   
   @PostConstruct
   public void initialize() {
     this.model = new SouFamilleModel();
     this.disableMsg = true;
     chargementSession();
     chargerFamille();
     chargerSousFamille();
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
     this.listFamille = new ArrayList<>();
     
     for (FamilleProduit fm : (new FamilleModel()).getListFamille(this.factory, "")) {
       this.listFamille.add(new SelectItem(Integer.valueOf(fm.getId()), fm.getLibelle()));
     }
   }
 
   
   public void changeFamilleElement(ValueChangeEvent event) {
     if (event != null && event.getNewValue() != null) {
       
       this.idFml = ((Integer)event.getNewValue()).intValue();
       familleValue();
     } 
   }
   
   private void familleValue() {
     if (this.idFml > 0) {
       
       this.selectedFml = (new FamilleModel()).getFamilleById(this.idFml, this.factory);
       if (this.selectedFml != null) {
         this.listSousFamille = this.model.getListSousFamille(this.factory, this.selectedFml);
       }
     } 
   }
   
   public void searchSouFamille() {
     if (this.code != null && !this.code.equals("")) {
       
       this.selectedSouFml = this.model.getSousFamilleByCode(this.factory, this.code);
       getSfamilleValues();
     } 
   }
 
   
   public void getSelectedSouFamilleValue() {
     getSfamilleValues();
   }
 
   
   private void chargerSousFamille() {
     this.listSousFamille = this.model.getListSousFamille(this.factory, "");
   }
   private void getSfamilleValues() {
     this.disableMsg = true;
     if (this.selectedSouFml != null) {
       
       this.idSf = this.selectedSouFml.getId();
       this.code = this.selectedSouFml.getCodeSfm();
       this.designation = this.selectedSouFml.getLibelle();
       this.disableMsg = false;
     } 
   }
 
   
   public void save() {
     if (this.code != null && !this.code.equals("")) {
       
       if (this.selectedSouFml == null)
         this.selectedSouFml = new SoufamilleProd(); 
       this.selectedSouFml.setId(this.idSf);
       this.selectedSouFml.setCodeSfm(this.code);
       this.selectedSouFml.setLibelle(this.designation);
       this.selectedSouFml.setFamille(this.selectedFml);
       if (this.selectedFml != null) {
         
         if (this.selectedSouFml.getId() == 0) {
           this.model.saveSousFamille(this.factory, this.selectedSouFml);
         } else {
           this.model.updateSousFamille(this.factory, this.selectedSouFml);
         } 
         chargerSousFamille();
       } else {
         
         HelperC.afficherAttention("Attention", "Il faut préciser la famille !");
       } 
     } 
   }
   
   public void delete() {
     if (this.selectedSouFml != null && this.selectedSouFml.getId() > 0) {
       
       this.model.deleteSousFamille(this.factory, this.selectedSouFml);
       chargerSousFamille();
     } 
   }
 
   
   public void initializeControls() {
     this.idSf = 0;
     this.code = "";
     this.designation = "";
     this.disableMsg = true;
     this.selectedSouFml = null;
     this.selectedFml = null;
   }
 }


