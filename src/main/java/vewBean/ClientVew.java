 package vewBean;
 
 import entite.Clients;
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
 import model.ClientModel;
 import model.CompteModel;
 import model.ExerciceModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import org.primefaces.PrimeFaces;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 
 
 
 
 @ManagedBean
 @ViewScoped
 public class ClientVew
   implements Serializable
 {
   private static final long serialVersionUID = 7445295832653697135L;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   
   private Clients selectedClient;
   
   private List<Clients> listClt;
   
   private List<Compte> listCpte;
   
   private boolean disableMsg;
   private String libelleCpt;
   private String rechLblCpt;
   private String rechCodCpt;
   private String compteCpbl;

   private String codeClient; 
   private String nomClient; 
   private Compte selectedCpt; 
   ClientModel model; 
   Exercice selecetdExercice;
   
   HttpSession session;
   String exerCode;
   String currUserCode;
   User currentUser;
   int idClt = 0;
   
   public Clients getSelectedClient() {
     return this.selectedClient;
   }
   
   public void setSelectedClient(Clients selectedClient) {
     this.selectedClient = selectedClient;
   }
   
   public List<Clients> getListClt() {
     return this.listClt;
   }
   
   public void setListClt(List<Clients> listClt) {
     this.listClt = listClt;
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
   
   public String getCodeClient() {
     return this.codeClient;
   }
   
   public void setCodeClient(String codeClient) {
     this.codeClient = codeClient;
   }
   
   public String getNomClient() {
     return this.nomClient;
   }
   
   public void setNomClient(String nomClient) {
     this.nomClient = nomClient;
   }
 
   
   @PostConstruct
   public void initialize() {
     this.model = new ClientModel();
     this.disableMsg = true;
     chargementSession();
     chargerClients();
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
 
   
   private void chargerClients() {
     this.listClt = this.model.getListClients(this.factory, "");
   }
   
   public void searchClient() {
     this.disableMsg = true;
     if (this.codeClient != null && !this.codeClient.equals("")) {
       
       this.selectedClient = this.model.getClientByCode(this.factory, this.codeClient);
       if (this.selectedClient != null)
       {
         setClientValues();
       }
     } 
   }
   
   public void getSelectedClientValues() {
     setClientValues();
   }
   
   private void setClientValues() {
     this.idClt = this.selectedClient.getId();
     this.codeClient = this.selectedClient.getCodeClt();
     this.compteCpbl = this.selectedClient.getCodeCpbl();
     this.nomClient = this.selectedClient.getNameClt();
     searchCompte();
     this.disableMsg = false;
   }
 
   
   public void save() {
     if (this.codeClient != null && !this.codeClient.equals("")) {
       
       if (this.selectedClient == null)
         this.selectedClient = new Clients(); 
       this.selectedClient.setCodeClt(this.codeClient);
       this.selectedClient.setCodeCpbl(this.compteCpbl);
       this.selectedClient.setId(this.idClt);
       this.selectedClient.setNameClt(this.nomClient);
       
       if (this.selectedClient.getId() == 0) {
         this.model.saveClient(this.factory, this.selectedClient);
       } else {
         this.model.updateClient(this.factory, this.selectedClient);
       }  initializeControl();
       chargerClients();
     } else {
       
       HelperC.afficherAttention("Attention", "Il faut préciser la référence !");
     } 
   }
   
   public void delete() {
     if (this.selectedClient != null && this.selectedClient.getId() > 0) {
       
       this.model.deleteClient(this.factory, this.selectedClient);
       initializeControl();
       chargerClients();
     } 
   }
   
   public void initializeControl() {
     this.idClt = 0;
     this.codeClient = "";
     this.compteCpbl = "";
     this.nomClient = "";
     this.libelleCpt = "";
     this.selectedCpt = null;
     this.selectedClient = null;
     this.disableMsg = true;
   }
 }


