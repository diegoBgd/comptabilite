 package vewBean;
 
 import entite.Banque;
 import entite.Compte;
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
 import model.BanqueModel;
 import model.CompteModel;
 import model.ExerciceModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import org.primefaces.PrimeFaces;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 
 
 
 
 @ManagedBean
 @ViewScoped
 public class BanqueVew
   implements Serializable
 {
   private static final long serialVersionUID = -4265680718240831592L;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   
   private Banque selectedBk;
   
   private List<Banque> listBk;
   
   private List<Compte> listCpte;
   private boolean disableMsg;
   private boolean cash;
   private String libelleCpt;
   int idBk = 0; private String rechLblCpt; private String rechCodCpt; private String codeBanque; private String compteCptbl;
   private String designation;
   private Compte selectedCpt;
   BanqueModel model;
   Exercice selecetdExercice;
   HttpSession session;
   String exerCode;
   String currUserCode;
   User currentUser;
   
   public Banque getSelectedBk() {
     return this.selectedBk;
   }
   
   public void setSelectedBk(Banque selectedBk) {
     this.selectedBk = selectedBk;
   }
   
   public List<Banque> getListBk() {
     return this.listBk;
   }
   
   public void setListBk(List<Banque> listBk) {
     this.listBk = listBk;
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
   
   public Compte getSelectedCpt() {
     return this.selectedCpt;
   }
   
   public void setSelectedCpt(Compte selectedCpt) {
     this.selectedCpt = selectedCpt;
   }
   
   public String getRechLblCpt() {
     return this.rechLblCpt;
   }
   
   public void setRechLblCpt(String rechLblCpt) {
     this.rechLblCpt = rechLblCpt;
   }
   
   public List<Compte> getListCpte() {
     return this.listCpte;
   }
   
   public void setListCpte(List<Compte> listCpte) {
     this.listCpte = listCpte;
   }
   
   public String getRechCodCpt() {
     return this.rechCodCpt;
   }
   
   public void setRechCodCpt(String rechCodCpt) {
     this.rechCodCpt = rechCodCpt;
   }
   
   public String getCodeBanque() {
     return this.codeBanque;
   }
   
   public void setCodeBanque(String codeBanque) {
     this.codeBanque = codeBanque;
   }
   
   public String getCompteCptbl() {
     return this.compteCptbl;
   }
   
   public void setCompteCptbl(String compteCptbl) {
     this.compteCptbl = compteCptbl;
   }
   public String getDesignation() {
     return this.designation;
   }
   
   public void setDesignation(String designation) {
     this.designation = designation;
   }
   
   public boolean isCash() {
     return this.cash;
   }
   
   public void setCash(boolean cash) {
     this.cash = cash;
   }
 
   
   @PostConstruct
   public void initialize() {
     this.model = new BanqueModel();
     chargermentBanque();
     this.disableMsg = true;
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
   
   private void chargermentBanque() {
     this.listBk = this.model.getListBanque(this.factory, "");
   }
   
   public void searchBank() {
     if (this.codeBanque != null && !this.codeBanque.equals("")) {
       
       this.selectedBk = this.model.getBanqueByCode(this.factory, this.codeBanque);
       if (this.selectedBk != null) {
         setBankValues();
       }
     } 
   }
   
   public void searchCompte() {
     if (this.compteCptbl != null && this.compteCptbl != null) {
       
       this.selectedCpt = (new CompteModel()).getCompteByCode(this.factory, this.compteCptbl);
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
     this.compteCptbl = this.selectedCpt.getCompteCod();
     this.libelleCpt = this.selectedCpt.getLibelle();
   }
 
   
   public void chargerCompte() {
     this.listCpte = (new CompteModel()).getListCompte(this.factory, this.rechLblCpt, this.rechCodCpt);
   }
   
   public void getSelectedBnkValues() {
     this.disableMsg = true;
     if (this.selectedBk != null)
       setBankValues(); 
   }
   
   private void setBankValues() {
     this.idBk = this.selectedBk.getId();
     this.cash = this.selectedBk.isCaisse();
     this.codeBanque = this.selectedBk.getCodeBk();
     this.designation = this.selectedBk.getLibelle();
     this.compteCptbl = this.selectedBk.getCodeCpbl();
     searchCompte();
     this.disableMsg = false;
   }
   
   public void save() {
     if (this.codeBanque != null && !this.codeBanque.equals("")) {
       
       if (this.selectedBk == null)
         this.selectedBk = new Banque(); 
       this.selectedBk.setId(this.idBk);
       this.selectedBk.setCaisse(this.cash);
       this.selectedBk.setCodeBk(this.codeBanque);
       this.selectedBk.setLibelle(this.designation);
       this.selectedBk.setCodeCpbl(this.compteCptbl);
       
       if (this.selectedBk.getId() == 0) {
         this.model.saveBanque(this.factory, this.selectedBk);
       } else {
         this.model.updateBanque(this.factory, this.selectedBk);
       }  chargermentBanque();
       initializeControl();
     } else {
       
       HelperC.afficherAttention("Attention", "Il faut préciser la référence !");
     } 
   }
   
   public void delete() {
     if (this.selectedBk != null) {
       
       this.model.deleteBanque(this.factory, this.selectedBk);
       chargermentBanque();
       initializeControl();
     } 
   }
   
   public void initializeControl() {
     this.idBk = 0;
     this.cash = false;
     this.codeBanque = "";
     this.designation = "";
     this.compteCptbl = "";
     this.libelleCpt = "";
     this.disableMsg = true;
     this.selectedBk = null;
   }
 }


