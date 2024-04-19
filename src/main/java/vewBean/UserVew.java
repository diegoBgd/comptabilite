 package vewBean;
 
 import entite.Exercice;
 import entite.FonctionRole;
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
 import model.FonctionRoleModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import org.primefaces.PrimeFaces;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 
 
 
 
 @ManagedBean
 @ViewScoped
 public class UserVew
   implements Serializable
 {
   private static final long serialVersionUID = 7247210471447875557L;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   
   UserModel model;
   
   private User selectedUser;
   
   private List<User> listUs;
   
   private boolean disableMsg;
   private boolean disableSave;
   private boolean enable;
   private String confirmPwd;
   private String code;
   private String nom;
   private String motPasse;
   int idUs = 0; private String mail; private String motRecherche; private List<SelectItem> listeRole; private int idRole; Exercice selecetdExercice; HttpSession session; String exerCode;
   String currUserCode;
   User currentUser;
   FonctionRole selectedRole;
   boolean exist = true;
   
   public User getSelectedUser() {
     return this.selectedUser;
   }
   
   public void setSelectedUser(User selectedUser) {
     this.selectedUser = selectedUser;
   }
   
   public List<User> getListUs() {
     return this.listUs;
   }
   
   public void setListUs(List<User> listUs) {
     this.listUs = listUs;
   }
   
   public boolean isDisableMsg() {
     return this.disableMsg;
   }
   
   public void setDisableMsg(boolean disableMsg) {
     this.disableMsg = disableMsg;
   }
   public String getConfirmPwd() {
     return this.confirmPwd;
   }
   
   public void setConfirmPwd(String confirmPwd) {
     this.confirmPwd = confirmPwd;
   }
   
   public boolean isDisableSave() {
     return this.disableSave;
   }
   
   public void setDisableSave(boolean disableSave) {
     this.disableSave = disableSave;
   }
   
   public boolean isEnable() {
     return this.enable;
   }
   
   public void setEnable(boolean enable) {
     this.enable = enable;
   }
   
   public String getCode() {
     return this.code;
   }
   
   public void setCode(String code) {
     this.code = code;
   }
   
   public String getNom() {
     return this.nom;
   }
   
   public void setNom(String nom) {
     this.nom = nom;
   }
   
   public String getMotPasse() {
     return this.motPasse;
   }
   
   public void setMotPasse(String motPasse) {
     this.motPasse = motPasse;
   }
   
   public String getMail() {
     return this.mail;
   }
   
   public void setMail(String mail) {
     this.mail = mail;
   }
   
   public List<SelectItem> getListeRole() {
     return this.listeRole;
   }
   
   public void setListeRole(List<SelectItem> listeRole) {
     this.listeRole = listeRole;
   }
   public int getIdRole() {
     return this.idRole;
   }
   
   public void setIdRole(int idRole) {
     this.idRole = idRole;
   }
   
   public String getMotRecherche() {
     return this.motRecherche;
   }
   
   public void setMotRecherche(String motRecherche) {
     this.motRecherche = motRecherche;
   }
 
   
   @PostConstruct
   public void initialize() {
     this.model = new UserModel();
     this.disableMsg = true;
     chargementSession();
     chargerRole();
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
       if (this.exist && (
         this.currentUser == null || this.selecetdExercice == null)) {
         
         try {
           FacesContext.getCurrentInstance().getExternalContext().redirect("/comptabilite/login.xhtml");
         } catch (IOException e) {
           
           e.printStackTrace();
         } 
       }
     } 
   }
   
   private void chargerRole() {
     this.listeRole = new ArrayList<>();
     for (FonctionRole fx : (new FonctionRoleModel()).getListRole(this.factory, ""))
       this.listeRole.add(new SelectItem(Integer.valueOf(fx.getId()), fx.getLibelleRole())); 
   }
   
   public void changeRoleElement(ValueChangeEvent event) {
     if (event != null && event.getNewValue() != null) {
       
       this.idRole = ((Integer)event.getNewValue()).intValue();
       searchRole();
     } 
   }
   
   private void searchRole() {
     this.selectedRole = (new FonctionRoleModel()).getRoleById(this.idRole, this.factory);
   }
   
   public void displayUsers() {
     this.listUs = this.model.getListUser(this.factory, this.motRecherche);
   }
 
 
   
   public void saveUser() {
     if (this.code != null && !this.code.equals("")) {
       
       if (this.selectedUser == null)
         this.selectedUser = new User(); 
       this.selectedUser.setId(this.idUs);
       this.selectedUser.setUserCod(this.code);
       this.selectedUser.setUserName(this.nom);
       this.selectedUser.setUserPwd(this.motPasse);
       this.selectedUser.seteMail(this.mail);
       this.selectedUser.setActif(this.enable);
       this.selectedUser.setRole(this.selectedRole);
       
       if (this.selectedUser.getId() == 0) {
         this.model.saveUser(this.selectedUser, this.factory);
       } else {
         this.model.updateUser(this.selectedUser, this.factory);
       } 
       initializeControl();
     } else {
       
       HelperC.afficherAttention("Attention", "Il faut préciser la référence !");
     } 
   }
   public void confirmPwd() {
     if (this.mail != null && !this.motPasse.equals("") && this.confirmPwd != null && !this.confirmPwd.equals(""))
     {
       if (!this.motPasse.equals(this.confirmPwd)) {
         HelperC.afficherAttention("Attention", "Mot de passe incorrect !");
         this.disableSave = true;
       } 
     }
   }
   
   public void searchUser() {
     this.selectedUser = this.model.getUserByCode(this.factory, this.code);
     this.disableMsg = true;
     if (this.selectedUser != null)
     {
       getUserValues();
     }
   }
   
   public void getUser() {
     this.disableMsg = true;
     if (this.selectedUser != null) {
 
       
       getUserValues();
       PrimeFaces.current().executeScript("PF('dlgResearch').hide();");
     } 
   }
   
   private void getUserValues() {
     this.idUs = this.selectedUser.getId();
     this.code = this.selectedUser.getUserCod();
     this.nom = this.selectedUser.getUserName();
     this.motPasse = this.selectedUser.getUserPwd();
     this.enable = this.selectedUser.isActif();
     this.selectedRole = this.selectedUser.getRole();
     if (this.selectedRole != null)
       this.idRole = this.selectedRole.getId(); 
     this.mail = this.selectedUser.geteMail();
     this.disableMsg = false;
   }
 
   
   public void deleteUser() {
     if (this.selectedUser != null) {
       this.model.deleteUser(this.selectedUser, this.factory);
       initializeControl();
     } 
   }
   
   public void initializeControl() {
     this.idUs = 0;
     this.code = "";
     this.nom = "";
     this.motPasse = "";
     this.mail = "";
     this.confirmPwd = "";
     this.enable = true;
     this.disableMsg = true;
     this.disableSave = false;
     this.selectedUser = null;
   }
 }


