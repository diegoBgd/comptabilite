 package vewBean;
 
 import entite.Compte;
 import entite.Exercice;
 import entite.Fournisseur;
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
 import model.FournisseurModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import org.primefaces.PrimeFaces;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 @ManagedBean
 @ViewScoped
 public class FournisseurVew
   implements Serializable
 {
   private static final long serialVersionUID = -4907209666101063748L;
   private Fournisseur selectedFrn;
   private List<Fournisseur> listFrn;
   private List<Compte> listCpte;
   private boolean disableMsg;
   private String libelleCpt;
   private String rechLblCpt;
   private String rechCodCpt;
   private Compte selectedCpt;
   private String compteCpbl;
   private String code;
   private String nom;
   FournisseurModel model;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   Exercice selecetdExercice;
   HttpSession session;
   String exerCode;
   String currUserCode;
   User currentUser;
   int idFrn = 0;
 
 
 
 
   
   public Fournisseur getSelectedFrn() {
     return this.selectedFrn;
   }
   public void setSelectedFrn(Fournisseur selectedFrn) {
     this.selectedFrn = selectedFrn;
   }
   public List<Fournisseur> getListFrn() {
     return this.listFrn;
   }
   public void setListFrn(List<Fournisseur> listFrn) {
     this.listFrn = listFrn;
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
   
   public String getNom() {
     return this.nom;
   }
   
   public void setNom(String nom) {
     this.nom = nom;
   }
 
   
   @PostConstruct
   public void initialize() {
     this.model = new FournisseurModel();
     this.disableMsg = true;
     chargementSession();
     chargerFournisseur();
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
     if (this.compteCpbl != null && !this.compteCpbl.equals("")) {
       
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
 
   
   private void chargerFournisseur() {
     this.listFrn = this.model.getListFournisseur(this.factory, "");
   }
   public void searchFournisseur() {
     if (this.code != null && this.code.equals("")) {
       
       this.selectedFrn = this.model.getFournisseurByCode(this.factory, "");
       if (this.selectedFrn != null) {
         setFrnValue();
       }
     } 
   }
   
   public void getSelectedFrnValues() {
     if (this.selectedFrn != null)
       setFrnValue(); 
   }
   private void setFrnValue() {
     this.idFrn = this.selectedFrn.getId();
     this.compteCpbl = this.selectedFrn.getCodeCpbl();
     this.code = this.selectedFrn.getCodeFrn();
     this.nom = this.selectedFrn.getNameFrn();
     searchCompte();
     this.disableMsg = false;
   }
 
   
   public void save() {
     if (this.code != null && !this.code.equals("")) {
       
       if (this.selectedFrn == null)
         this.selectedFrn = new Fournisseur(); 
       this.selectedFrn.setCodeCpbl(this.compteCpbl);
       this.selectedFrn.setCodeFrn(this.code);
       this.selectedFrn.setId(this.idFrn);
       this.selectedFrn.setNameFrn(this.nom);
       
       if (this.selectedFrn.getId() == 0) {
         this.model.saveFournisseur(this.factory, this.selectedFrn);
       } else {
         this.model.updateFournisseur(this.factory, this.selectedFrn);
       }  initializeControl();
       chargerFournisseur();
     } else {
       
       HelperC.afficherAttention("Attention", "Il faut préciser la référence !");
     } 
   }
   
   public void delete() {
     if (this.selectedFrn != null && this.selectedFrn.getId() > 0) {
       
       this.model.deleteClient(this.factory, this.selectedFrn);
       initializeControl();
       chargerFournisseur();
     } 
   }
 
   
   public void initializeControl() {
     this.idFrn = 0;
     this.compteCpbl = "";
     this.code = "";
     this.nom = "";
     this.compteCpbl = "";
     this.libelleCpt = "";
     this.disableMsg = true;
     this.selectedCpt = null;
     this.selectedFrn = null;
   }
 }


