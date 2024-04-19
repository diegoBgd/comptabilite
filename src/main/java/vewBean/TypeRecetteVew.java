 package vewBean;
 
 import entite.Compte;
 import entite.Exercice;
 import entite.TypeRecette;
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
 import model.TypeRecetteModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import org.primefaces.PrimeFaces;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 
 
 
 
 @ManagedBean
 @ViewScoped
 public class TypeRecetteVew
   implements Serializable
 {
   private static final long serialVersionUID = 8728803784003519247L;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   
   private List<Compte> listCpte;
   
   private boolean disableMsg;
   
   private String libelleCpt;
   
   private String rechLblCpt;
   
   private String rechCodCpt;
   private Compte selectedCpt;
   private TypeRecette selectedTypeRct;
   private List<TypeRecette> listTypeRct;
   int idRec = 0; private String code; private String designation; private String compteCpbl; TypeRecetteModel model; Exercice selecetdExercice;
   HttpSession session;
   String exerCode;
   String currUserCode;
   User currentUser;
   
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
   
   public TypeRecette getSelectedTypeRct() {
     return this.selectedTypeRct;
   }
   
   public void setSelectedTypeRct(TypeRecette selectedTypeRct) {
     this.selectedTypeRct = selectedTypeRct;
   }
   
   public List<TypeRecette> getListTypeRct() {
     return this.listTypeRct;
   }
   
   public void setListTypeRct(List<TypeRecette> listTypeRct) {
     this.listTypeRct = listTypeRct;
   }
   
   public String getRechCodCpt() {
     return this.rechCodCpt;
   }
   
   public void setRechCodCpt(String rechCodCpt) {
     this.rechCodCpt = rechCodCpt;
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
   
   public String getCompteCpbl() {
     return this.compteCpbl;
   }
   
   public void setCompteCpbl(String compteCpbl) {
     this.compteCpbl = compteCpbl;
   }
 
   
   @PostConstruct
   public void initialize() {
     this.model = new TypeRecetteModel();
     this.disableMsg = true;
     chargementSession();
     chargerTypRecette();
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
   
   private void chargerTypRecette() {
     this.listTypeRct = this.model.getListTypeRecette(this.factory, "");
   }
 
   
   public void searchTypeRecette() {
     if (this.code != null && !this.code.equals("")) {
       
       this.selectedTypeRct = this.model.getTypeProduiByCode(this.factory, this.code);
       if (this.selectedTypeRct != null) {
         getTypeRecValue();
       }
     } 
   }
   
   public void getSelectedTypRctValues() {
     if (this.selectedTypeRct != null)
       getTypeRecValue(); 
   }
   private void getTypeRecValue() {
     this.compteCpbl = this.selectedTypeRct.getCodeCpbl();
     this.code = this.selectedTypeRct.getCodeRec();
     this.idRec = this.selectedTypeRct.getId();
     this.designation = this.selectedTypeRct.getLibelle();
     this.disableMsg = false;
     searchCompte();
   }
 
   
   public void save() {
     if (this.code != null && !this.code.equals("")) {
       
       if (this.selectedTypeRct == null)
         this.selectedTypeRct = new TypeRecette(); 
       this.selectedTypeRct.setCodeCpbl(this.compteCpbl);
       this.selectedTypeRct.setCodeRec(this.code);
       this.selectedTypeRct.setId(this.idRec);
       this.selectedTypeRct.setLibelle(this.designation);
       
       if (this.selectedTypeRct.getId() == 0) {
         this.model.saveTypeProduit(this.factory, this.selectedTypeRct);
       } else {
         this.model.updateTypeProduit(this.factory, this.selectedTypeRct);
       }  initializeControl();
       chargerTypRecette();
     } else {
       
       HelperC.afficherAttention("Attention", "Il faut préciser la référence !");
     } 
   }
   
   public void delete() {
     if (this.selectedTypeRct != null && this.selectedTypeRct.getId() > 0) {
       
       this.model.deleteTypeProduit(this.factory, this.selectedTypeRct);
       initializeControl();
       chargerTypRecette();
     } 
   }
 
   
   public void initializeControl() {
     this.compteCpbl = "";
     this.code = "";
     this.idRec = 0;
     this.designation = "";
     this.disableMsg = true;
     this.libelleCpt = "";
     this.selectedCpt = null;
     this.selectedTypeRct = null;
   }
 }


