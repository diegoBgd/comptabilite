 package vewBean;
 
 import entite.Amortissement;
 import entite.CalculAmortissement;
 import entite.Compte;
 import entite.Exercice;
 import entite.Immobilise;
 import entite.User;
 import java.io.IOException;
 import java.io.Serializable;
 import java.util.Date;
 import java.util.List;
 import javax.annotation.PostConstruct;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import javax.faces.context.FacesContext;
 import javax.servlet.http.HttpSession;
 import model.CompteModel;
 import model.ExerciceModel;
 import model.ImmobiliseModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import org.primefaces.PrimeFaces;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 
 
 
 
 @ManagedBean
 @ViewScoped
 public class ImmoVew
   implements Serializable
 {
   private static final long serialVersionUID = -1699518730406160906L;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   ImmobiliseModel model;
   Exercice selecetdExercice;
   HttpSession session;
   String exerCode;
   String currUserCode;
   User currentUser;
   CalculAmortissement calcul;
   int idImmo = 0;
   
   private Immobilise selectedImmo;
   
   private List<Immobilise> listImmo;
   
   private String motRechImmo;
   
   private String printDateAq;
   private String cptImmo;
   private String cptAmrt;
   private String cptDot;
   private String lblCptImmo;
   private String lblCptAmrt;
   private String lblCptDotAmrt;
   private String rechLblCpt;
   private String numMsg;
   private String rechCodCpt;
   private boolean disableMsg;
   private Compte selectedCptImmo;
   private Compte selectedCptAmrt;
   
   public Immobilise getSelectedImmo() {
     return this.selectedImmo;
   }
   private Compte selectedCptDot; private List<Compte> listCptImmo; private List<Compte> listCptAmrt; private List<Compte> listCptDot; private List<Amortissement> listAmrt; private Date dateAcq; private String code; private String designation; private String numeroInv; private double montantAnterieur; private double montantExercice; private double montantVnc; private double montantAcq; private double taux; private int amortizable; private int duree; private int type;
   public void setSelectedImmo(Immobilise selectedImmo) {
     this.selectedImmo = selectedImmo;
   }
   
   public List<Immobilise> getListImmo() {
     return this.listImmo;
   }
   
   public void setListImmo(List<Immobilise> listImmo) {
     this.listImmo = listImmo;
   }
   
   public String getLblCptImmo() {
     return this.lblCptImmo;
   }
   
   public void setLblCptImmo(String lblCptImmo) {
     this.lblCptImmo = lblCptImmo;
   }
   
   public String getLblCptAmrt() {
     return this.lblCptAmrt;
   }
   
   public void setLblCptAmrt(String lblCptAmrt) {
     this.lblCptAmrt = lblCptAmrt;
   }
   
   public String getLblCptDotAmrt() {
     return this.lblCptDotAmrt;
   }
   
   public void setLblCptDotAmrt(String lblCptDotAmrt) {
     this.lblCptDotAmrt = lblCptDotAmrt;
   }
   
   public boolean isDisableMsg() {
     return this.disableMsg;
   }
   
   public void setDisableMsg(boolean disableMsg) {
     this.disableMsg = disableMsg;
   }
   
   public Compte getSelectedCptImmo() {
     return this.selectedCptImmo;
   }
   
   public void setSelectedCptImmo(Compte selectedCptImmo) {
     this.selectedCptImmo = selectedCptImmo;
   }
   
   public Compte getSelectedCptAmrt() {
     return this.selectedCptAmrt;
   }
   
   public void setSelectedCptAmrt(Compte selectedCptAmrt) {
     this.selectedCptAmrt = selectedCptAmrt;
   }
   
   public Compte getSelectedCptDot() {
     return this.selectedCptDot;
   }
 
   
   public void setSelectedCptDot(Compte selectedCptDot) {
     this.selectedCptDot = selectedCptDot;
   }
   
   public List<Compte> getListCptImmo() {
     return this.listCptImmo;
   }
   
   public void setListCptImmo(List<Compte> listCptImmo) {
     this.listCptImmo = listCptImmo;
   }
   
   public List<Compte> getListCptAmrt() {
     return this.listCptAmrt;
   }
   
   public void setListCptAmrt(List<Compte> listCptAmrt) {
     this.listCptAmrt = listCptAmrt;
   }
   
   public List<Compte> getListCptDot() {
     return this.listCptDot;
   }
   
   public void setListCptDot(List<Compte> listCptDot) {
     this.listCptDot = listCptDot;
   }
   
   public String getRechLblCpt() {
     return this.rechLblCpt;
   }
 
   
   public void setRechLblCpt(String rechLblCpt) {
     this.rechLblCpt = rechLblCpt;
   }
 
   
   public String getMotRechImmo() {
     return this.motRechImmo;
   }
 
   
   public void setMotRechImmo(String motRechImmo) {
     this.motRechImmo = motRechImmo;
   }
   
   public List<Amortissement> getListAmrt() {
     return this.listAmrt;
   }
 
   
   public void setListAmrt(List<Amortissement> listAmrt) {
     this.listAmrt = listAmrt;
   }
   
   public String getPrintDateAq() {
     return this.printDateAq;
   }
   
   public void setPrintDateAq(String printDateAq) {
     this.printDateAq = printDateAq;
   }
 
   
   public String getCptImmo() {
     return this.cptImmo;
   }
 
   
   public void setCptImmo(String cptImmo) {
     this.cptImmo = cptImmo;
   }
 
   
   public String getCptAmrt() {
     return this.cptAmrt;
   }
 
   
   public void setCptAmrt(String cptAmrt) {
     this.cptAmrt = cptAmrt;
   }
 
   
   public String getCptDot() {
     return this.cptDot;
   }
 
   
   public void setCptDot(String cptDot) {
     this.cptDot = cptDot;
   }
   
   public String getNumMsg() {
     return this.numMsg;
   }
   
   public void setNumMsg(String numMsg) {
     this.numMsg = numMsg;
   }
   
   public String getRechCodCpt() {
     return this.rechCodCpt;
   }
   
   public void setRechCodCpt(String rechCodCpt) {
     this.rechCodCpt = rechCodCpt;
   }
   
   public Date getDateAcq() {
     return this.dateAcq;
   }
   
   public void setDateAcq(Date dateAcq) {
     this.dateAcq = dateAcq;
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
   
   public double getMontantAnterieur() {
     return this.montantAnterieur;
   }
   
   public void setMontantAnterieur(double montantAnterieur) {
     this.montantAnterieur = montantAnterieur;
   }
   
   public double getMontantExercice() {
     return this.montantExercice;
   }
   
   public void setMontantExercice(double montantExercice) {
     this.montantExercice = montantExercice;
   }
   
   public double getMontantVnc() {
     return this.montantVnc;
   }
   
   public void setMontantVnc(double montantVnc) {
     this.montantVnc = montantVnc;
   }
   
   public int getAmortizable() {
     return this.amortizable;
   }
   
   public void setAmortizable(int amortizable) {
     this.amortizable = amortizable;
   }
   
   public double getMontantAcq() {
     return this.montantAcq;
   }
   
   public void setMontantAcq(double montantAcq) {
     this.montantAcq = montantAcq;
   }
   
   public String getNumeroInv() {
     return this.numeroInv;
   }
   
   public void setNumeroInv(String numeroInv) {
     this.numeroInv = numeroInv;
   }
   
   public double getTaux() {
     return this.taux;
   }
   
   public void setTaux(double taux) {
     this.taux = taux;
   }
   
   public int getDuree() {
     return this.duree;
   }
   
   public void setDuree(int duree) {
     this.duree = duree;
   }
   
   public int getType() {
     return this.type;
   }
   
   public void setType(int type) {
     this.type = type;
   }
 
 
   
   @PostConstruct
   public void initialize() {
     this.model = new ImmobiliseModel();
     
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
 
 
 
 
 
   
   public void chargerCompteImmo() {
     this.listCptImmo = (new CompteModel()).getListCompte(this.factory, this.rechLblCpt, this.rechCodCpt);
   }
   
   public void searchCompteImmo() {
     if (this.cptImmo != null && this.cptImmo != null) {
       
       this.selectedCptImmo = (new CompteModel()).getCompteByCode(this.factory, this.cptImmo);
       if (this.selectedCptImmo != null) {
         getCptImmoValues();
       }
     } 
   }
   
   private void getCptImmoValues() {
     this.cptImmo = this.selectedCptImmo.getCompteCod();
     this.lblCptImmo = this.selectedCptImmo.getLibelle();
   }
 
   
   public void getSelecedCptImmoValue() {
     if (this.selectedCptImmo != null) {
       
       getCptImmoValues();
       PrimeFaces.current().executeScript("PF('dlgCptImmo').hide();");
     } 
   }
 
 
 
   
   public void chargerCompteAmrt() {
     this.listCptAmrt = (new CompteModel()).getListCompte(this.factory, this.rechLblCpt, this.rechCodCpt);
   }
   
   public void searchCompteAmrt() {
     if (this.cptAmrt != null && this.cptAmrt != null) {
       
       this.selectedCptAmrt = (new CompteModel()).getCompteByCode(this.factory, this.cptAmrt);
       if (this.selectedCptAmrt != null) {
         getCptAmrtValues();
       }
     } 
   }
   
   private void getCptAmrtValues() {
     this.lblCptAmrt = this.selectedCptAmrt.getLibelle();
     this.cptAmrt = this.selectedCptAmrt.getCompteCod();
   }
 
   
   public void getSelecedCptAmrtValue() {
     if (this.selectedCptAmrt != null) {
       
       getCptAmrtValues();
       PrimeFaces.current().executeScript("PF('dlgCptAmrt').hide();");
     } 
   }
 
 
 
   
   public void chargerCompteDot() {
     this.listCptDot = (new CompteModel()).getListCompte(this.factory, this.rechLblCpt, this.rechCodCpt);
   }
   
   public void searchCompteDot() {
     if (this.cptDot != null && this.cptDot != null) {
       
       this.selectedCptDot = (new CompteModel()).getCompteByCode(this.factory, this.cptDot);
       if (this.selectedCptDot != null) {
         getCptDotAmrtValues();
       }
     } 
   }
   
   private void getCptDotAmrtValues() {
     this.lblCptDotAmrt = this.selectedCptDot.getLibelle();
     this.cptDot = this.selectedCptDot.getCompteCod();
   }
 
   
   public void getSelecedCptDotAmrtValue() {
     if (this.selectedCptDot != null) {
       
       getCptDotAmrtValues();
       PrimeFaces.current().executeScript("PF('dlgCptDotAmrt').hide();");
     } 
   }
 
 
 
   
   public void changeDate() {
     if (this.printDateAq.replace("/", "").replace("_", "").trim().equals("")) {
       this.dateAcq = null;
     }
     else {
       
       this.dateAcq = HelperC.validerDate(this.printDateAq);
       if (this.dateAcq == null) {
         
         this.printDateAq = "";
         HelperC.afficherMessage("Information", "Date invalide!");
       } else {
         
         this.printDateAq = HelperC.convertDate(this.dateAcq);
         createImmo();
       } 
     } 
   }
   
   public void chargerImmo() {
     this.listImmo = this.model.getListImmo(this.factory, this.motRechImmo);
     this.numMsg = String.valueOf(this.listImmo.size()) + " éléments trouvés";
   }
 
   
   public void searchImmo() {
     if (this.code != null && !this.code.equals("")) {
       
       this.selectedImmo = this.model.getImmoByCode(this.factory, this.code);
       if (this.selectedImmo != null)
       {
         getImmoValues();
       }
     } 
   }
 
   
   public void takeSelectedValues() {
     if (this.selectedImmo != null) {
       
       getImmoValues();
       PrimeFaces.current().executeScript("PF('dlgImmo').hide();");
     } 
   }
   
   private void getImmoValues() {
     this.idImmo = this.selectedImmo.getId();
     this.code = this.selectedImmo.getCodeImmo();
     this.designation = this.selectedImmo.getLibelle();
     this.montantAnterieur = this.selectedImmo.getAmortAnterieur();
     this.montantExercice = this.selectedImmo.getAmortEncour();
     this.amortizable = this.selectedImmo.getAmortissable();
     this.cptAmrt = this.selectedImmo.getCompteAmrt();
     this.cptDot = this.selectedImmo.getCompteDotAmrt();
     this.cptImmo = this.selectedImmo.getCompteImmo();
     this.dateAcq = this.selectedImmo.getDateAcquisition();
     this.montantAcq = this.selectedImmo.getMontantAcq();
     this.duree = this.selectedImmo.getNbrAnne();
     this.numeroInv = this.selectedImmo.getNumero();
     this.taux = this.selectedImmo.getTauxAmort();
     this.type = this.selectedImmo.getTypeAmort();
     this.montantVnc = this.selectedImmo.getVnc();
     this.cptAmrt = this.selectedImmo.getCompteAmrt();
     this.cptDot = this.selectedImmo.getCompteDotAmrt();
     this.cptImmo = this.selectedImmo.getCompteImmo();
     this.printDateAq = HelperC.changeDateFormate(this.dateAcq);
     searchCompteAmrt();
     searchCompteDot();
     searchCompteImmo();
     this.disableMsg = false;
     CalculAmortissement cal = new CalculAmortissement();
     this.listAmrt = cal.tableauAmortissemet(this.selectedImmo, 0, this.selecetdExercice);
   }
 
   
   public void amortisationChanged() {
     createImmo();
   }
   
   public void typeChanged() {
     createImmo();
   }
   
   public void createImmo() {
     this.listAmrt = null;
     
     if (this.selectedImmo == null)
       this.selectedImmo = new Immobilise(); 
     this.selectedImmo.setMontantAcq(getMontantAcq());
     this.selectedImmo.setTauxAmort(this.taux);
     this.selectedImmo.setNbrAnne(this.duree);
     this.selectedImmo.setDateAcquisition(this.dateAcq);
     this.selectedImmo.setTypeAmort(this.type);
     if (this.selectedImmo.getAmortissable() == 0 && 
       this.selectedImmo.getDateAcquisition() != null && this.selectedImmo.getMontantAcq() > 0.0D) {
 
       
       this.calcul = new CalculAmortissement();
       this.listAmrt = this.calcul.tableauAmortissemet(this.selectedImmo, 0, this.selecetdExercice);
     } 
   }
 
   
   public void changeTaux() {
     if (this.duree > 0) {
       
       this.taux = (100 / this.duree);
       createImmo();
     } 
   }
   
   public void changeDuree() {
     if (this.taux > 0.0D) {
       
       this.duree = (int)Math.round(100.0D / this.taux);
       createImmo();
     } 
   }
   public void save() {
     if (this.code != null && !this.code.equals("")) {
       if (this.selectedImmo == null)
         this.selectedImmo = new Immobilise(); 
       this.selectedImmo.setAmortAnterieur(this.montantAnterieur);
       this.selectedImmo.setAmortEncour(this.montantExercice);
       this.selectedImmo.setAmortissable(this.amortizable);
       this.selectedImmo.setCompteAmrt(this.cptAmrt);
       this.selectedImmo.setCompteDotAmrt(this.cptDot);
       this.selectedImmo.setCompteImmo(this.cptDot);
       this.selectedImmo.setDateAcquisition(this.dateAcq);
       this.selectedImmo.setLibelle(this.designation);
       this.selectedImmo.setMontantAcq(this.montantAcq);
       this.selectedImmo.setNbrAnne(this.duree);
       this.selectedImmo.setTauxAmort(this.taux);
       this.selectedImmo.setNumero(this.numeroInv);
       this.selectedImmo.setCodeImmo(this.code);
       this.selectedImmo.setTypeAmort(this.type);
 
       
       if (this.selectedImmo.getId() == 0) {
         this.model.saveImmo(this.factory, this.selectedImmo);
       } else {
         this.model.updateImmo(this.factory, this.selectedImmo);
       } 
       
       initializeControl();
     }
     else {
       
       HelperC.afficherAttention("Attention", "Il faut préciser le code de l'immo !");
     } 
   }
   
   public void delete() {
     if (this.selectedImmo.getId() > 0) {
       
       this.model.deleteImmo(this.factory, this.selectedImmo);
       initializeControl();
     } else {
       
       HelperC.afficherDeleteMessage();
     } 
   }
   public void initializeControl() {
     this.montantAnterieur = 0.0D;
     this.montantExercice = 0.0D;
     this.amortizable = 0;
     this.cptAmrt = "";
     this.cptAmrt = "";
     this.cptImmo = "";
     this.dateAcq = null;
     this.idImmo = 0;
     this.designation = "";
     this.montantAcq = 0.0D;
     this.duree = 0;
     this.numeroInv = "";
     this.taux = 0.0D;
     this.type = 0;
     this.montantVnc = 0.0D;
     this.code = "";
     this.printDateAq = "";
     this.cptAmrt = "";
     this.cptDot = "";
     this.cptImmo = "";
     this.lblCptAmrt = "";
     this.lblCptDotAmrt = "";
     this.lblCptImmo = "";
     this.lblCptAmrt = null;
     this.disableMsg = true;
     this.listAmrt = null;
   }
 }


