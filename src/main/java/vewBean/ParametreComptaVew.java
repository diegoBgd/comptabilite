 package vewBean;
 
 import entite.Compte;
 import entite.Exercice;
 import entite.Journal;
 import entite.ParametreCompta;
 import entite.User;
 import java.io.IOException;
 import java.io.Serializable;
 import javax.annotation.PostConstruct;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import javax.faces.context.FacesContext;
 import javax.servlet.http.HttpSession;
 import model.CompteModel;
 import model.ExerciceModel;
 import model.JournalModel;
 import model.ParametreComptaModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 
 
 
 
 @ManagedBean
 @ViewScoped
 public class ParametreComptaVew
   implements Serializable
 {
   private static final long serialVersionUID = -7465840185623778530L;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   
   private ParametreCompta selectedPrm;
   
   private String cptRs;
   
   private String cptANC;
   
   private String cptAND;
   
   private String jrnAN;
   
   private String libelleCptRs;
   private String libelleCptAnc;
   private String libelleCptAnd;
   private String libelleJrnAn;
   
   public ParametreCompta getSelectedPrm() {
     return this.selectedPrm;
   }
   private String jrnOD; private String libelleJrnOd; private boolean disableMsg; Exercice selecetdExercice; HttpSession session; String exerCode; String currUserCode; User currentUser; ParametreComptaModel model; int idParm;
   public void setSelectedPrm(ParametreCompta selectedPrm) {
     this.selectedPrm = selectedPrm;
   }
   
   public String getCptRs() {
     return this.cptRs;
   }
   
   public void setCptRs(String cptRs) {
     this.cptRs = cptRs;
   }
   
   public String getCptANC() {
     return this.cptANC;
   }
   
   public void setCptANC(String cptANC) {
     this.cptANC = cptANC;
   }
   
   public String getCptAND() {
     return this.cptAND;
   }
   
   public void setCptAND(String cptAND) {
     this.cptAND = cptAND;
   }
   
   public String getJrnAN() {
     return this.jrnAN;
   }
   
   public void setJrnAN(String jrnAN) {
     this.jrnAN = jrnAN;
   }
   
   public boolean isDisableMsg() {
     return this.disableMsg;
   }
   
   public void setDisableMsg(boolean disableMsg) {
     this.disableMsg = disableMsg;
   }
   
   public String getLibelleCptRs() {
     return this.libelleCptRs;
   }
   
   public void setLibelleCptRs(String libelleCptRs) {
     this.libelleCptRs = libelleCptRs;
   }
   
   public String getLibelleCptAnc() {
     return this.libelleCptAnc;
   }
   
   public void setLibelleCptAnc(String libelleCptAnc) {
     this.libelleCptAnc = libelleCptAnc;
   }
   
   public String getLibelleCptAnd() {
     return this.libelleCptAnd;
   }
   
   public void setLibelleCptAnd(String libelleCptAnd) {
     this.libelleCptAnd = libelleCptAnd;
   }
   
   public String getLibelleJrnAn() {
     return this.libelleJrnAn;
   }
   
   public void setLibelleJrnAn(String libelleJrnAn) {
     this.libelleJrnAn = libelleJrnAn;
   }
   
   public String getJrnOD() {
     return this.jrnOD;
   }
   
   public void setJrnOD(String jrnOD) {
     this.jrnOD = jrnOD;
   }
   
   public String getLibelleJrnOd() {
     return this.libelleJrnOd;
   }
   
   public void setLibelleJrnOd(String libelleJrnOd) {
     this.libelleJrnOd = libelleJrnOd;
   }
 
   
   @PostConstruct
   public void initialize() {
     this.disableMsg = true;
     this.model = new ParametreComptaModel();
     chargementSession();
     chargerParametre();
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
   
   private void chargerParametre() {
     this.selectedPrm = this.model.getParameter(this.factory);
     if (this.selectedPrm != null) {
       
       this.idParm = this.selectedPrm.getIdPrm();
       this.cptRs = this.selectedPrm.getCompteRs();
       this.cptANC = this.selectedPrm.getCompteANC();
       this.cptAND = this.selectedPrm.getCompteAND();
       this.jrnAN = this.selectedPrm.getJournalAN();
       this.jrnOD = this.selectedPrm.getJournalOD();
       searchCompteResultat();
       searchCompteANC();
       searchCompteAND();
       searchJournalAN();
       searchJournalOD();
       this.disableMsg = false;
     } 
   }
   
   public void searchCompteResultat() {
     Compte cpt = null;
     if (this.cptRs != null && !this.cptRs.equals("")) {
       
       cpt = (new CompteModel()).getCompteByCode(this.factory, this.cptRs);
       if (cpt != null)
         this.libelleCptRs = cpt.getLibelle(); 
     } 
   }
   public void searchCompteANC() {
     Compte cpt = null;
     if (this.cptANC != null && !this.cptANC.equals("")) {
       
       cpt = (new CompteModel()).getCompteByCode(this.factory, this.cptANC);
       if (cpt != null)
         this.libelleCptAnc = cpt.getLibelle(); 
     } 
   }
   
   public void searchCompteAND() {
     Compte cpt = null;
     if (this.cptAND != null && !this.cptAND.equals("")) {
       
       cpt = (new CompteModel()).getCompteByCode(this.factory, this.cptAND);
       if (cpt != null)
         this.libelleCptAnd = cpt.getLibelle(); 
     } 
   }
   public void searchJournalAN() {
     Journal jrn = null;
     if (this.jrnAN != null && !this.jrnAN.equals("")) {
       
       jrn = (new JournalModel()).getJouralByCode(this.factory, this.jrnAN);
       if (jrn != null)
         this.libelleJrnAn = jrn.getLibelle(); 
     } 
   }
   
   public void searchJournalOD() {
     Journal jrn = null;
     if (this.jrnOD != null && !this.jrnOD.equals("")) {
       
       jrn = (new JournalModel()).getJouralByCode(this.factory, this.jrnOD);
       if (jrn != null) {
         this.libelleJrnOd = jrn.getLibelle();
       }
     } 
   }
   
   public void save() {
     if (this.selectedPrm == null)
       this.selectedPrm = new ParametreCompta(); 
     this.selectedPrm.setIdPrm(this.idParm);
     this.selectedPrm.setCompteANC(this.cptANC);
     this.selectedPrm.setCompteAND(this.cptAND);
     this.selectedPrm.setCompteRs(this.cptRs);
     this.selectedPrm.setJournalAN(this.jrnAN);
     this.selectedPrm.setJournalOD(this.jrnOD);
     this.model.saveParametreCmpta(this.factory, this.selectedPrm);
     chargerParametre();
   }
   
   public void delete() {
     if (this.selectedPrm != null && this.selectedPrm.getIdPrm() > 0) {
       
       this.model.deleteParametreCmpta(this.factory, this.selectedPrm);
       this.idParm = 0;
       this.cptRs = "";
       this.cptANC = "";
       this.cptAND = "";
       this.jrnAN = "";
       this.jrnOD = "";
       this.libelleCptAnc = "";
       this.libelleCptAnd = "";
       this.libelleCptRs = "";
       this.libelleJrnAn = "";
       this.libelleJrnOd = "";
       this.selectedPrm = null;
     } 
   }
 }


