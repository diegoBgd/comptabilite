 package vewBean;
 
 import entite.Constante;
 import entite.Exercice;
 import entite.FonctionRole;
 import entite.Fonctionalite;
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
 import model.ExerciceModel;
 import model.FonctionRoleModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import org.primefaces.PrimeFaces;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 
 
 
 
 @ManagedBean
 @ViewScoped
 public class FonctionRoleVew
   implements Serializable
 {
   private static final long serialVersionUID = 5971191483281994193L;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   
   private boolean disableMsg;
   
   private boolean selectAll;
   
   private String code;
   private String designation;
   private String motRecherche;
   private FonctionRole selectedRole;
   private List<Fonctionalite> listFonction;
   int idRole = 0; private List<FonctionRole> listRole; FonctionRoleModel model; Exercice selecetdExercice; HttpSession session; String exerCode;
   String currUserCode;
   String fonctionSelected;
   User currentUser;
   boolean exist = true;
   
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
   public FonctionRole getSelectedRole() {
     return this.selectedRole;
   }
   public void setSelectedRole(FonctionRole selectedRole) {
     this.selectedRole = selectedRole;
   }
   
   public List<Fonctionalite> getListFonction() {
     return this.listFonction;
   }
   public void setListFonction(List<Fonctionalite> listFonction) {
     this.listFonction = listFonction;
   }
   public boolean isSelectAll() {
     return this.selectAll;
   }
   public void setSelectAll(boolean selectAll) {
     this.selectAll = selectAll;
   }
   
   public String getMotRecherche() {
     return this.motRecherche;
   }
   public void setMotRecherche(String motRecherche) {
     this.motRecherche = motRecherche;
   }
   
   public List<FonctionRole> getListRole() {
     return this.listRole;
   }
   public void setListRole(List<FonctionRole> listRole) {
     this.listRole = listRole;
   }
   
   @PostConstruct
   public void initialize() {
     this.model = new FonctionRoleModel();
     this.disableMsg = true;
     chargementSession();
     this.listFonction = new ArrayList<>();
     chargerFonction();
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
 
   
   private void chargerFonction() {
     Fonctionalite fx = null; byte b; int i; Constante.Fonction[] arrayOfFonction;
     for (i = (arrayOfFonction = Constante.Fonction.values()).length, b = 0; b < i; ) { Constante.Fonction f = arrayOfFonction[b];
       
       fx = new Fonctionalite();
       fx.setLibelle(Constante.getLibelle(f));
       fx.setNumero(f.ordinal());
       fx.setSelected(false);
       this.listFonction.add(fx);
       b++; }
   
   } public void searchRole() {
     if (this.code != null && !this.code.equals("")) {
       
       this.selectedRole = this.model.getRoleByCode(this.factory, this.code);
       setRoleValue();
     } 
   }
   private void setRoleValue() {
     if (this.selectedRole != null) {
       
       this.disableMsg = false;
       this.idRole = this.selectedRole.getId();
       this.code = this.selectedRole.getCodeRole();
       this.designation = this.selectedRole.getLibelleRole();
       this.fonctionSelected = this.selectedRole.getAcces();
       selection();
     } 
   }
   
   private void getSelectedFx() {
     this.fonctionSelected = "";
     
     for (Fonctionalite fxte : this.listFonction) {
       if (fxte.isSelected())
         this.fonctionSelected = String.valueOf(this.fonctionSelected) + fxte.getNumero() + " "; 
     } 
   }
   private void selection() {
     String num = "";
     
     if (this.fonctionSelected != null && !this.fonctionSelected.equals("")) {
       
       String[] valeur = this.fonctionSelected.split(" ");
       
       for (int i = 0; i < valeur.length; i++) {
         
         num = String.valueOf(valeur[i]);
         if (!num.trim().equals("")) {
           selectFonction(num);
         }
       } 
     } 
   }
   
   private void selectFonction(String position) {
     int pos = Integer.valueOf(position).intValue();
     for (Fonctionalite fxte : this.listFonction) {
       if (pos == fxte.getNumero())
         fxte.setSelected(true); 
     } 
   }
   
   public void selectAllFx() {
     for (Fonctionalite fxte : this.listFonction) {
       fxte.setSelected(this.selectAll);
     }
   }
 
 
 
   
   public void chargerRole() {
     this.listRole = this.model.getListRole(this.factory, this.motRecherche);
   }
   public void takeSelectedRole() {
     setRoleValue();
     PrimeFaces.current().executeScript("PF('dlgResearch').hide();");
   }
   public void saveRole() {
     if (this.code == null || this.code.equals("")) {
       
       HelperC.afficherAttention("Attention", "Il faut préciser la référence !");
       return;
     } 
     if (this.selectedRole == null)
       this.selectedRole = new FonctionRole(); 
     getSelectedFx();
     this.selectedRole.setCodeRole(this.code);
     this.selectedRole.setAcces(this.fonctionSelected);
     this.selectedRole.setLibelleRole(this.designation);
     
     if (this.idRole == 0) {
       this.model.saveRole(this.factory, this.selectedRole);
     } else {
       this.model.updateRole(this.factory, this.selectedRole);
     }  initializeControl();
   }
 
   
   public void deleteRole() {
     if (this.selectedRole == null) {
       
       HelperC.afficherDeleteMessage();
       return;
     } 
     if (this.idRole > 0) {
       
       this.model.deleteRole(this.factory, this.selectedRole);
       initializeControl();
     } 
   }
   
   public void initializeControl() {
     this.idRole = 0;
     this.selectedRole = null;
     this.code = "";
     this.designation = "";
     this.disableMsg = true;
     this.selectAll = false;
     this.listFonction.clear();
     chargerFonction();
   }
 }


