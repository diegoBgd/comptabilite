 package vewBean;
 
 import entite.Compte;
 import entite.Exercice;
 import entite.Taxes;
 import entite.User;
 import java.io.IOException;
 import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
 import javax.annotation.PostConstruct;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import javax.faces.context.FacesContext;
 import javax.servlet.http.HttpSession;
 import model.CompteModel;
 import model.ExerciceModel;
 import model.TaxeModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import org.primefaces.PrimeFaces;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 @ManagedBean
 @ViewScoped
 public class TaxeVew
   implements Serializable
 {
   private static final long serialVersionUID = 6493164447734397906L;
   private Taxes selectedTaxe;
   private List<Taxes> listTaxes;
   private List<Compte> listCpte;
   private boolean disableMsg;
   private String libelleCpt;
   private String rechLblCpt;
   private String rechCodCpt;
   private String compteCpbl;
   private String code;
   private String designation;
   private Compte selectedCpt;
   private int typeTx;
   private BigDecimal tauxTaxe;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   
   TaxeModel model;
   
   Exercice selecetdExercice;
   HttpSession session;
   int idTx = 0;
   
   String exerCode;
   String currUserCode;
   User currentUser;
   
   public Taxes getSelectedTaxe() {
     return this.selectedTaxe;
   }
   
   public void setSelectedTaxe(Taxes selectedTaxe) {
     this.selectedTaxe = selectedTaxe;
   }
   
   public List<Taxes> getListTaxes() {
     return this.listTaxes;
   }
   
   public void setListTaxes(List<Taxes> listTaxes) {
     this.listTaxes = listTaxes;
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
   
   public BigDecimal getTauxTaxe() {
     return this.tauxTaxe;
   }
   
   public void setTauxTaxe(BigDecimal tauxTaxe) {
     this.tauxTaxe = tauxTaxe;
   }
   
   public int getTypeTx() {
     return this.typeTx;
   }
   
   public void setTypeTx(int typeTx) {
     this.typeTx = typeTx;
   }
 
   
   @PostConstruct
   public void initialize() {
     this.model = new TaxeModel();
     this.disableMsg = true;
     chargementSession();
     chargerTaxes();
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
 
   
   public void searchTaxe() {
     if (this.code != null && !this.code.equals("")) {
       
       this.selectedTaxe = this.model.getTaxeByCode(this.factory, this.code);
       if (this.selectedTaxe != null)
         getTaxeValue(); 
     } 
   }
   public void getSelectedTaxeValues() {
     if (this.selectedTaxe != null) {
       getTaxeValue();
     }
   }
   
   private void getTaxeValue() {
     this.compteCpbl = this.selectedTaxe.getCodeCpbl();
     this.code = this.selectedTaxe.getCodeTaxe();
     this.idTx = this.selectedTaxe.getId();
     this.designation = this.selectedTaxe.getLibelle();
     this.tauxTaxe = this.selectedTaxe.getTaux();
     this.typeTx = this.selectedTaxe.getType();
     
     this.disableMsg = false;
     searchCompte();
   }
 
   
   private void chargerTaxes() {
     this.listTaxes = this.model.getListTaxes(this.factory, "");
   }
 
   
   public void save() {
     if (this.code != null && !this.code.equals("")) {
       
       if (this.selectedTaxe == null)
         this.selectedTaxe = new Taxes(); 
       this.selectedTaxe.setCodeCpbl(this.compteCpbl);
       this.selectedTaxe.setCodeTaxe(this.code);
       this.selectedTaxe.setId(this.idTx);
       this.selectedTaxe.setLibelle(this.designation);
       this.selectedTaxe.setTaux(this.tauxTaxe);
       this.selectedTaxe.setType(this.typeTx);
       if (this.selectedTaxe.getId() == 0) {
         this.model.saveTaxe(this.factory, this.selectedTaxe);
       } else {
         this.model.updateTaxe(this.factory, this.selectedTaxe);
       }  chargerTaxes();
       initializeControl();
     } else {
       
       HelperC.afficherAttention("Attention", "Il faut préciser la référence !");
     } 
   }
   public void delete() {
     if (this.selectedTaxe != null && this.selectedTaxe.getId() > 0) {
       
       this.model.deleteTaxe(this.factory, this.selectedTaxe);
       chargerTaxes();
       initializeControl();
     } 
   }
 
   
   public void initializeControl() {
     this.compteCpbl = "";
     this.code = "";
     this.idTx = 0;
     this.designation = "";
     this.tauxTaxe = BigDecimal.ZERO;
     this.selectedCpt = null;
     this.selectedTaxe = null;
     this.libelleCpt = "";
     this.disableMsg = true;
   }
 }


