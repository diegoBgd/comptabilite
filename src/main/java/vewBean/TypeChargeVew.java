 package vewBean;
 
 import entite.Compte;
 import entite.Exercice;
 import entite.TypeCharge;
 import entite.User;
 import java.io.IOException;
 import java.io.Serializable;
 import java.util.List;
 import javax.annotation.PostConstruct;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import javax.faces.context.FacesContext;
 import javax.servlet.http.HttpSession;
 import model.CompteModel;
 import model.ExerciceModel;
 import model.TypeChargeModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import org.primefaces.PrimeFaces;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 
 
 
 
 @ManagedBean
 @ViewScoped
 public class TypeChargeVew
   implements Serializable
 {
   SessionFactory factory = DBConfiguration.getSessionFactory();
   
   private static final long serialVersionUID = -7466859560434626257L;
   
   private TypeCharge selectedChrg;
   
   private List<TypeCharge> listChrg;
   
   private List<Compte> listCpte;
   
   private boolean disableMsg;
   
   private String libelleCpt;
   
   private String rechLblCpt;
   private String rechCodCpt;
   private Compte selectedCpt;
   int idChrg = 0; private String compteCpbl; private String code; private String designation; private int typeChrg; TypeChargeModel model; Exercice selecetdExercice;
   HttpSession session;
   String exerCode;
   String currUserCode;
   User currentUser;
   
   public TypeCharge getSelectedChrg() {
     return this.selectedChrg;
   }
   
   public void setSelectedChrg(TypeCharge selectedChrg) {
     this.selectedChrg = selectedChrg;
   }
   
   public List<TypeCharge> getListChrg() {
     return this.listChrg;
   }
   
   public void setListChrg(List<TypeCharge> listChrg) {
     this.listChrg = listChrg;
   }
   
   public List<Compte> getListCpte() {
     return this.listCpte;
   }
   
   public void setListCpte(List<Compte> listCpte) {
     this.listCpte = listCpte;
   }
   
   public boolean isDisableMsg() {
     return this.disableMsg;
   }
   
   public void setDisableMsg(boolean disableMsg) {
     this.disableMsg = disableMsg;
   }
   
   public String getLibelleCpt() {
     return this.libelleCpt;
   }
   
   public void setLibelleCpt(String libelleCpt) {
     this.libelleCpt = libelleCpt;
   }
   
   public String getRechLblCpt() {
     return this.rechLblCpt;
   }
   
   public void setRechLblCpt(String rechLblCpt) {
     this.rechLblCpt = rechLblCpt;
   }
   
   public Compte getSelectedCpt() {
     return this.selectedCpt;
   }
   
   public void setSelectedCpt(Compte selectedCpt) {
     this.selectedCpt = selectedCpt;
   }
   
   public String getRechCodCpt() {
     return this.rechCodCpt;
   }
   
   public void setRechCodCpt(String rechCodCpt) {
     this.rechCodCpt = rechCodCpt;
   }
   
   public String getCompteCpbl() {
     return this.compteCpbl;
   }
   
   public void setCompteCpbl(String compteCpbl) {
     this.compteCpbl = compteCpbl;
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
   
   public int getTypeChrg() {
     return this.typeChrg;
   }
   
   public void setTypeChrg(int typeChrg) {
     this.typeChrg = typeChrg;
   }
 
   
   @PostConstruct
   public void initialize() {
     this.model = new TypeChargeModel();
     this.disableMsg = true;
     chargementSession();
     chargementChrg();
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
 
   
   public void searchCompte() {
     if (this.compteCpbl != null && this.compteCpbl != null) {
       
       this.selectedCpt = (new CompteModel()).getCompteByCode(this.factory, this.compteCpbl);
       if (this.selectedCpt != null)
         getCptValues(); 
     } 
   }
   
   public void getSelecedCptValue() {
     if (this.selectedCpt != null) {
       
       getCptValues();
       PrimeFaces.current().executeScript("PF('dlgCpt').hide();");
     } 
   }
 
   
   private void getCptValues() {
     this.compteCpbl = this.selectedCpt.getCompteCod();
     this.libelleCpt = this.selectedCpt.getLibelle();
   }
 
   
   public void chargerCompte() {
     this.listCpte = (new CompteModel()).getListCompte(this.factory, this.rechLblCpt, this.rechCodCpt);
   }
 
   
   private void chargementChrg() {
     this.listChrg = this.model.getListCharge(this.factory, "");
   }
   
   public void searchCharge() {
     this.disableMsg = true;
     if (this.code != null && !this.code.equals("")) {
       
       this.selectedChrg = this.model.getChargeByCode(this.factory, this.code);
       if (this.selectedChrg != null) {
         getChrgValues();
       }
     } 
   }
 
 
 
 
   
   public void getSelectedChrgValue() {
     if (this.selectedChrg != null) {
       getChrgValues();
     }
   }
   
   private void getChrgValues() {
     this.code = this.selectedChrg.getCodeChg();
     this.compteCpbl = this.selectedChrg.getCodeCpbl();
     this.idChrg = this.selectedChrg.getId();
     this.designation = this.selectedChrg.getLibelle();
     this.typeChrg = this.selectedChrg.getNature();
     this.disableMsg = false;
     searchCompte();
   }
 
   
   public void save() {
     if (this.code != null && !this.code.equals("")) {
       
       if (this.selectedChrg == null)
         this.selectedChrg = new TypeCharge(); 
       this.selectedChrg.setCodeChg(this.code);
       this.selectedChrg.setCodeCpbl(this.compteCpbl);
       this.selectedChrg.setId(this.idChrg);
       this.selectedChrg.setLibelle(this.designation);
       this.selectedChrg.setNature(this.typeChrg);
       
       if (this.selectedChrg.getId() == 0) {
         this.model.saveCharge(this.factory, this.selectedChrg);
       } else {
         this.model.updateCharge(this.factory, this.selectedChrg);
       } 
       initializeControl();
       chargementChrg();
     } else {
       
       HelperC.afficherAttention("Attention", "Il faut préciser la référence !");
     } 
   }
 
   
   public void delete() {
     if (this.selectedChrg != null && this.selectedChrg.getId() > 0) {
       
       this.model.deleteCharge(this.factory, this.selectedChrg);
       initializeControl();
       chargementChrg();
     } 
   }
   
   public void initializeControl() {
     this.code = "";
     this.compteCpbl = "";
     this.idChrg = 0;
     this.designation = "";
     this.typeChrg = 0;
     this.disableMsg = true;
     this.libelleCpt = "";
     this.selectedCpt = null;
     this.selectedChrg = null;
   }
 }


