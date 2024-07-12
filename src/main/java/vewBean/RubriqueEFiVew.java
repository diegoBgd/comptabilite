 package vewBean;

			import entite.*;
 import entite.RubriqueEFi;
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

import org.hibernate.SessionFactory;
		  import org.primefaces.PrimeFaces;

 import model.*;

 import persistances.DBConfiguration;
 import utils.HelperC;
 
 @ManagedBean
 @ViewScoped
 public class RubriqueEFiVew implements Serializable {
   private static final long serialVersionUID = 1799881634686357121L;
   private RubriqueEFi selectedRubrique;
   private List<RubriqueEFi> listRubrique;
   private EtatFinancier selectedEfi;
   private List<EtatFinancier> listeEtat;
   private List<SelectItem> listElement;
   private CompteEFi selectedDetail;
   private boolean actf,pssf;
   private int valeur;
   private int valeurColone;
   private int type,typeRb;
   private String codeEfi;
   private String libelleEfi;
   private String symbol;
   private String infoMsg;
   private String element;
   private String formulElement;
   private String cptDeb;
   private String sign;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   
   private String cptFin;
   
   private String signeDetail;
   
   private boolean disableMsg;
   
   private boolean disableAddCpt;
   
   private boolean printable;
   
   private boolean title;
   private List<CompteEFi> listDetail;
   private String codeRub;
   private String designation;
   private String formul;
   boolean selected;
   int idRub = 0; RubriqueEFiModel model; Compte compteDb;
   Compte compteFn;
   Exercice selecetdExercice;
   HttpSession session;
   String exerCode;
   String currUserCode;
   User currentUser;
   int index = 0;

	public int getTypeRb() {
		return typeRb;
	}

	public void setTypeRb(int typeRb) {
		this.typeRb = typeRb;
	}

	public RubriqueEFi getSelectedRubrique() {
		return this.selectedRubrique;
	}
   public void setSelectedRubrique(RubriqueEFi selectedRubrique) {
     this.selectedRubrique = selectedRubrique;
   }
   public List<RubriqueEFi> getListRubrique() {
     return this.listRubrique;
   }
   public void setListRubrique(List<RubriqueEFi> listRubrique) {
     this.listRubrique = listRubrique;
   }
   public EtatFinancier getSelectedEfi() {
     return this.selectedEfi;
   }
   public void setSelectedEfi(EtatFinancier selectedEfi) {
     this.selectedEfi = selectedEfi;
   }
   public List<EtatFinancier> getListeEtat() {
     return this.listeEtat;
   }
   public void setListeEtat(List<EtatFinancier> listeEtat) {
     this.listeEtat = listeEtat;
   }
   public String getCodeEfi() {
     return this.codeEfi;
   }
   public void setCodeEfi(String codeEfi) {
     this.codeEfi = codeEfi;
   }
   public String getLibelleEfi() {
     return this.libelleEfi;
   }
   public void setLibelleEfi(String libelleEfi) {
     this.libelleEfi = libelleEfi;
   }
   public boolean isDisableMsg() {
     return this.disableMsg;
   }
   public void setDisableMsg(boolean disableMsg) {
     this.disableMsg = disableMsg;
   }
   public List<SelectItem> getListElement() {
     return this.listElement;
   }
   public void setListElement(List<SelectItem> listElement) {
     this.listElement = listElement;
   }
   public String getSymbol() {
     return this.symbol;
   }
   public void setSymbol(String symbol) {
     this.symbol = symbol;
   }
   public String getElement() {
     return this.element;
   }
   public void setElement(String element) {
     this.element = element;
   }
   public String getFormulElement() {
     return this.formulElement;
   }
   public void setFormulElement(String formulElement) {
     this.formulElement = formulElement;
   }
   public String getCptDeb() {
     return this.cptDeb;
   }
   public void setCptDeb(String cptDeb) {
     this.cptDeb = cptDeb;
   }
   public String getCptFin() {
     return this.cptFin;
   }
   public void setCptFin(String cptFin) {
     this.cptFin = cptFin;
   }
   public CompteEFi getSelectedDetail() {
     return this.selectedDetail;
   }
   public void setSelectedDetail(CompteEFi selectedDetail) {
     this.selectedDetail = selectedDetail;
   }
   public String getSigneDetail() {
     return this.signeDetail;
   }
   public void setSigneDetail(String signeDetail) {
     this.signeDetail = signeDetail;
   }
   public List<CompteEFi> getListDetail() {
     return this.listDetail;
   }
   public void setListDetail(List<CompteEFi> listDetail) {
     this.listDetail = listDetail;
   }
   
   public int getValeur() {
     return this.valeur;
   }
   
   public void setValeur(int valeur) {
     this.valeur = valeur;
   }
   
   public String getInfoMsg() {
     return this.infoMsg;
   }
   public void setInfoMsg(String infoMsg) {
     this.infoMsg = infoMsg;
   }
   public boolean isDisableAddCpt() {
     return this.disableAddCpt;
   }
   public void setDisableAddCpt(boolean disableAddCpt) {
     this.disableAddCpt = disableAddCpt;
   }
   public int getValeurColone() {
     return this.valeurColone;
   }
   public void setValeurColone(int valeurColone) {
     this.valeurColone = valeurColone;
   }
   
   public String getDesignation() {
     return this.designation;
   }
   
   public void setDesignation(String designation) {
     this.designation = designation;
   }
   
   public String getCodeRub() {
     return this.codeRub;
   }
   
   public void setCodeRub(String codeRub) {
     this.codeRub = codeRub;
   }
   
   public String getFormul() {
     return this.formul;
   }
   
   public void setFormul(String formul) {
     this.formul = formul;
   }
   
   public int getType() {
     return this.type;
   }
   
   public void setType(int type) {
     this.type = type;
   }
   
   public boolean isPrintable() {
     return this.printable;
   }
   
   public void setPrintable(boolean printable) {
     this.printable = printable;
   }
   
   public boolean isTitle() {
     return this.title;
   }
   
   public void setTitle(boolean title) {
     this.title = title;
   }
   
   public String getSign() {
     return this.sign;
   }
   
   public void setSign(String sign) {
     this.sign = sign;
   }
 
 
   
   @PostConstruct
   public void initialize() {
     this.disableMsg = true;
     this.formulElement = "";
     this.model = new RubriqueEFiModel();
     chargerEtat();
     this.listDetail = new ArrayList<>();
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
       
       if (this.currentUser == null || this.selecetdExercice == null)
         
         try {
           FacesContext.getCurrentInstance().getExternalContext().redirect("/comptabilite/login.xhtml");
         } catch (IOException e) {
           
           e.printStackTrace();
         }  
     } 
   }
   
   public void chargerRubrique() {
	
     this.listRubrique = this.model.getListRubrique(this.factory, this.selectedEfi,typeRb);
   }
 
   
   public void addElement() {
     if (this.symbol != null && !this.symbol.equals(""))
     {
       this.formulElement = String.valueOf(this.formulElement) + this.symbol;
     }
     if (this.element != null && !this.element.equals(""))
     {
       this.formulElement = String.valueOf(this.formulElement) + this.element;
     }
     refreshElementList();
     this.symbol = "";
   }
 
   
   public void removeElement() {
     String mot = "";
     
     if (this.formulElement != null && !this.formulElement.equals(""))
     {
       mot = this.formulElement.substring(0, this.formulElement.length() - 1);
     }
     this.formulElement = mot;
   }
   
   public void refreshElementList() {
     this.listElement = new ArrayList<>();
     chargerRubrique();
     if (this.listRubrique != null && this.listRubrique.size() > 0)
       for (RubriqueEFi rb : this.listRubrique)
         this.listElement.add(new SelectItem(rb.getCode(), rb.getCode()));  
   }
   
   public void changeElement(ValueChangeEvent event) {
     if (event != null && event.getNewValue() != null) {
       this.element = event.getNewValue().toString();
     }
   }
   
   public void searchRubrique() {
     this.disableMsg = true;
     if (this.codeRub != null && !this.codeRub.equals("")) {
       
       this.selectedRubrique = this.model.getRubriqueByCode(this.factory, this.codeRub);
       if (this.selectedRubrique != null) {
         getRubriqueValue();
       }
     } 
   }
   
   public void getSelectedRubriqueValues() {
     this.disableMsg = true;
     if (this.selectedRubrique != null) {
       getRubriqueValue();
     }
   }
   
   private void getRubriqueValue() {
     this.codeRub = this.selectedRubrique.getCode();
     this.selectedEfi = this.selectedRubrique.getEtat();
     this.formul = this.selectedRubrique.getFormule();
     this.idRub = this.selectedRubrique.getId();
     this.designation = this.selectedRubrique.getLibelle();
     this.printable = this.selectedRubrique.isPrintValue();
     this.title = this.selectedRubrique.isTitreValeur();
     this.type = this.selectedRubrique.getTypeValeur();
     this.sign = this.selectedRubrique.getSigne();
     this.listDetail = this.selectedRubrique.getListCompte();
     this.formulElement = this.formul;
     this.disableMsg = false;
    
   }
 
   
   public void chargerEtat() {
     this.listeEtat = (new EtatFinancierModel()).getListEFi(this.factory);
   }
 
   
   public void searchEtat() {
     if (this.codeEfi != null && !this.codeEfi.equals("")) {
       
       this.selectedEfi = (new EtatFinancierModel()).getEFiByCode(this.factory, this.codeEfi);
       if (this.selectedEfi != null) {
         
         getEfiValue();
         chargerRubrique();
       } else {
         
         this.codeEfi = "";
         this.libelleEfi = "";
       } 
     } 
   }
 
   
   public void getSelectedEfiValue() {
     if (this.selectedEfi != null) {
       
       getEfiValue();
       chargerRubrique();
       PrimeFaces.current().executeScript("PF('dlgEfi').hide();");
     } 
   }
   
   private void getEfiValue() {
     this.codeEfi = this.selectedEfi.getCode();
     this.libelleEfi = this.selectedEfi.getLibelle();
   }
 
 
   
   public void searchCompteDb() {
     this.infoMsg = ""; this.disableAddCpt = false;
     if (this.cptDeb != null && !this.cptDeb.equals("")) {
       
       this.compteDb = (new CompteModel()).getCompteByCode(this.factory, this.cptDeb);
		/*
		 * if (this.compteDb == null) {
		 * 
		 * this.infoMsg = "Ce compte n'existe pas !"; this.disableAddCpt = true; }
		 */
     } 
   }
   
   public void searchCompteFn() {
     this.infoMsg = ""; this.disableAddCpt = false;
     if (this.cptFin != null && !this.cptFin.equals("")) {
       
       this.compteFn = (new CompteModel()).getCompteByCode(this.factory, this.cptFin);
		/*
		 * if (this.compteFn == null) {
		 * 
		 * this.infoMsg = "Ce compte n'existe pas !"; this.disableAddCpt = true; }
		 */
     } 
   }
 
 
   
   public void addDetail() {
     if (this.selectedDetail == null)
       this.selectedDetail = new CompteEFi(); 
     this.selectedDetail.setCompteDeb(this.cptDeb);
     this.selectedDetail.setCompteFin(this.cptFin);
     this.selectedDetail.setRubrique(this.selectedRubrique);
     this.selectedDetail.setSigne(this.signeDetail);
     this.selectedDetail.setTypeSolde(this.valeur);
     this.selectedDetail.setColumnValue(this.valeurColone);
     if (!this.selected) {
       this.listDetail.add(this.selectedDetail);
     } else {
       this.listDetail.remove(this.selectedDetail);
       this.listDetail.add(this.index, this.selectedDetail);
     } 
     clearDetail();
   }
 
   
   public void removeDetail() {
     if (this.selectedDetail != null) {
       
       this.listDetail.remove(this.selectedDetail);
       clearDetail();
     } 
   }
   public void getSelectedDetailValue() {
     if (this.selectedDetail != null) {
       
       this.cptDeb = this.selectedDetail.getCompteDeb();
       this.cptFin = this.selectedDetail.getCompteFin();
       this.signeDetail = this.selectedDetail.getSigne();
       this.valeur = this.selectedDetail.getTypeSolde();
       this.valeurColone = this.selectedDetail.getColumnValue();
       this.selected = true;
       this.index = this.listDetail.indexOf(this.selectedDetail);
     } 
   }
   
   public void clearDetail() {
     this.cptDeb = "";
     this.cptFin = "";
     this.signeDetail = "";
     this.valeur = 1;
     this.selected = false;
     this.index = 0;
     this.valeurColone = 0;
   }
   
   public void save() {
     if (this.selectedEfi != null)
     { if (this.codeRub != null && !this.codeRub.equals("")) {
         if (this.selectedRubrique == null)
           this.selectedRubrique = new RubriqueEFi(); 
         this.selectedRubrique.setCode(this.codeRub);
         this.selectedRubrique.setEtat(this.selectedEfi);
         this.selectedRubrique.setFormule(this.formulElement);
         this.selectedRubrique.setId(this.idRub);
         this.selectedRubrique.setLibelle(this.designation);
         this.selectedRubrique.setPrintValue(this.printable);
         this.selectedRubrique.setSigne(this.sign);
         this.selectedRubrique.setTitreValeur(this.title);
         this.selectedRubrique.setTypeValeur(this.type);
 
       
         this.selectedRubrique.setListCompte(this.listDetail);
         
         if (this.selectedRubrique.getId() == 0) {
           this.model.saveRubrique(this.factory, this.selectedRubrique);
         } else {
           this.model.updateRubrique(this.factory, this.selectedRubrique);
         }  
         initializeControl();
         chargerRubrique();
       } else {
         HelperC.afficherAttention("Attention", "Il faut préciser la référence de la rubrique !");
       }  }
     else { HelperC.afficherAttention("Attention", "Il faut préciser l'état financier !"); }
   
   }
 
 
   
   public void delete() {
     if (this.selectedRubrique != null && this.selectedRubrique.getId() > 0) {
       
       this.model.deleteRubrique(this.factory, this.selectedRubrique);
       initializeControl();
       chargerRubrique();
     } 
   }
   
   public void initializeControl() {
     this.codeRub = "";
    // this.selectedEfi = null;
     this.formul = "";
     this.idRub = 0;
     this.designation = "";
     this.printable = false;
     this.title = false;
     this.type = 0;
     this.sign = "P";
     this.formulElement = "";
     this.disableMsg = true;
     this.selectedRubrique = null;
     this.cptDeb = "";
     this.cptFin = "";
     this.compteDb = null;
     this.compteFn = null;
     this.infoMsg = "";
     this.disableAddCpt = false;
     this.listDetail = new ArrayList<>();
   }
 }


