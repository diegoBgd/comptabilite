 package vewBean;
 
 import entite.BankAccount;
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
 import model.BankAccountModel;
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
 public class BankAcountVew
   implements Serializable
 {
   private static final long serialVersionUID = 974522867680052964L;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   
   private BankAccount selectedbkAccount;
   
   private List<BankAccount> listAccount;
   
   private Banque selectedBk;
   
   private List<Banque> listBk;
   
   private List<Compte> listCpte;
   
   private String codeBk;
   
   private String libelleBk;
   private boolean disableMsg;
   private String libelleCpt;
   private String rechLblCpt;
   private String rechLblBk;
   int idCpt = 0; private String designation; private String rechCodCpt; private String codeCompte; private String compteBank; private Compte selectedCpt; BankAccountModel model; Exercice selecetdExercice; HttpSession session;
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
   public BankAccount getSelectedbkAccount() {
     return this.selectedbkAccount;
   }
   public void setSelectedbkAccount(BankAccount selectedbkAccount) {
     this.selectedbkAccount = selectedbkAccount;
   }
   public List<BankAccount> getListAccount() {
     return this.listAccount;
   }
   public void setListAccount(List<BankAccount> listAccount) {
     this.listAccount = listAccount;
   }
   
   public String getCodeBk() {
     return this.codeBk;
   }
   public void setCodeBk(String codeBk) {
     this.codeBk = codeBk;
   }
   
   public String getLibelleBk() {
     return this.libelleBk;
   }
   public void setLibelleBk(String libelleBk) {
     this.libelleBk = libelleBk;
   }
   public String getRechLblBk() {
     return this.rechLblBk;
   }
   public void setRechLblBk(String rechLblBk) {
     this.rechLblBk = rechLblBk;
   }
   
   public String getRechCodCpt() {
     return this.rechCodCpt;
   }
   public void setRechCodCpt(String rechCodCpt) {
     this.rechCodCpt = rechCodCpt;
   }
   
   public String getCodeCompte() {
     return this.codeCompte;
   }
   public void setCodeCompte(String codeCompte) {
     this.codeCompte = codeCompte;
   }
   
   public String getCompteBank() {
     return this.compteBank;
   }
   public void setCompteBank(String compteBank) {
     this.compteBank = compteBank;
   }
   
   public String getDesignation() {
     return this.designation;
   }
   public void setDesignation(String designation) {
     this.designation = designation;
   }
   
   @PostConstruct
   public void initialize() {
     this.model = new BankAccountModel();
     chargementBkAccount();
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
   
   public void chargermentBanque() {
     this.listBk = (new BanqueModel()).getListBanque(this.factory, this.rechLblBk);
   }
   
   public void searchBank() {
     if (getCodeBk() != null && !getCodeBk().equals("")) {
       
       this.selectedBk = (new BanqueModel()).getBanqueByCode(this.factory, getCodeBk());
       if (this.selectedBk != null) {
         setBankValues();
       }
     } 
   }
   
   public void searchCompte() {
     if (this.codeCompte != null && this.codeCompte != null) {
       
       this.selectedCpt = (new CompteModel()).getCompteByCode(this.factory, this.codeCompte);
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
     this.codeCompte = this.selectedCpt.getCompteCod();
     this.libelleCpt = this.selectedCpt.getLibelle();
   }
 
   
   public void chargerCompte() {
     this.listCpte = (new CompteModel()).getListCompte(this.factory, this.rechLblCpt, this.rechCodCpt);
   }
 
   
   public void getSelectedBnkValues() {
     if (this.selectedBk != null) {
       
       setBankValues();
       PrimeFaces.current().executeScript("PF('dlgBk').hide();");
     } 
   }
 
   
   private void setBankValues() {
     this.codeBk = this.selectedBk.getCodeBk();
     this.libelleBk = this.selectedBk.getLibelle();
   }
 
   
   private void chargementBkAccount() {
     this.listAccount = this.model.getListBankAccount(this.factory, "");
   }
   
   public void searchBkAcount() {
     this.disableMsg = true;
     if (this.compteBank != null && !this.compteBank.equals("")) {
       
       this.selectedbkAccount = this.model.getBankAccountByCode(this.factory, this.compteBank);
       if (this.selectedbkAccount != null)
       {
         setBkAccountValues();
       }
     } 
   }
   
   public void getBkAccountSelected() {
     setBkAccountValues();
   }
   private void setBkAccountValues() {
     this.idCpt = this.selectedbkAccount.getId();
     this.compteBank = this.selectedbkAccount.getAccCode();
     this.selectedBk = this.selectedbkAccount.getBank();
     this.codeCompte = this.selectedbkAccount.getCodeCpb();
     this.designation = this.selectedbkAccount.getLibelle();
     this.selectedBk = this.selectedbkAccount.getBank();
     searchCompte();
     setBankValues();
     
     this.disableMsg = false;
   }
   
   public void save() {
     if (this.compteBank != null && !this.compteBank.equals("")) {
       if (this.selectedBk != null) {
         if (this.selectedbkAccount == null)
           this.selectedbkAccount = new BankAccount(); 
         this.selectedbkAccount.setAccCode(this.compteBank);
         this.selectedbkAccount.setBank(this.selectedBk);
         this.selectedbkAccount.setCodeCpb(this.codeCompte);
         this.selectedbkAccount.setId(this.idCpt);
         this.selectedbkAccount.setLibelle(this.designation);
         
         if (this.selectedbkAccount.getId() == 0) {
           this.model.saveBankAcount(this.factory, this.selectedbkAccount);
         } else {
           this.model.updateBankAcount(this.factory, this.selectedbkAccount);
         }  chargementBkAccount();
         initializeControl();
       } else {
         HelperC.afficherAttention("Attention", "Il faut préciser la Banque !");
       } 
     } else {
       HelperC.afficherAttention("Attention", "Il faut préciser le N° de compte !");
     } 
   }
 
   
   public void delete() {
     if (this.selectedbkAccount.getId() > 0) {
       
       this.model.deleteBankAcount(this.factory, this.selectedbkAccount);
       chargementBkAccount();
       initializeControl();
     } 
   }
 
   
   public void initializeControl() {
     this.idCpt = 0;
     this.codeCompte = "";
     this.compteBank = "";
     this.designation = "";
     this.selectedBk = null;
     this.selectedbkAccount = null;
     this.selectedCpt = null;
     this.codeBk = "";
     this.libelleBk = "";
     this.libelleCpt = "";
     this.disableMsg = true;
   }
 }


