 package vewBean;
 
 import entite.CentreCout;
 import entite.Compte;
 import entite.Depense;
 import entite.Devise;
 import entite.Exercice;
 import entite.Fournisseur;
 import entite.Taxes;
 import entite.TypeCharge;
 import entite.TypeEcriture;
 import entite.User;
 import java.io.IOException;
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import javax.annotation.PostConstruct;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import javax.faces.component.UIComponent;
 import javax.faces.context.FacesContext;
 import javax.faces.event.ValueChangeEvent;
 import javax.faces.model.SelectItem;
 import javax.servlet.http.HttpSession;
 import model.CentreCoutModel;
 import model.DepenseModel;
 import model.DeviseModel;
 import model.ExerciceModel;
 import model.FournisseurModel;
 import model.TaxeModel;
 import model.TypeChargeModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import org.primefaces.PrimeFaces;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 
 
 @ManagedBean
 @ViewScoped
 public class DepenseVew
   implements Serializable
 {
   private static final long serialVersionUID = 7331731613847598014L;
   private List<Depense> listDepense;
   private List<Fournisseur> listFournisseur;
   private List<TypeCharge> listCharge;
   private List<SelectItem> listTaxe;
   private List<SelectItem> listDevise;
   private List<SelectItem> listCentre;
   private Depense selectedDepense;
   private int idTaxe;
   private int idDevise;
   private int idCentre;
   private Fournisseur selectedFourn;
   private TypeCharge selectedCharg;
   private boolean disableMsg;
   private String codeFrn;
   private String libelleFrn;
   private String printDate;
   private String rechLblChg;
   SessionFactory factory = DBConfiguration.getSessionFactory(); private String infoMsg; private String printTot; private String printTotRgl; private String codeChrg; private String libelleChrg; private String rechLblFrn; private String rechDateDeb; private String rechDateFin; private String printSolde; private String comment; private String pieceCmpt; private Date dateDebut; private Date dateFin; private Date dateOp; private double tauxDevise; private double tauxTva; private double montantHt; private double montantTvac; DepenseModel model; Compte compteDb;
   Compte compteFn;
   Exercice selecetdExercice;
   HttpSession session;
   String exerCode;
   String currUserCode;
   User currentUser;
   Devise selectedDev;
   Taxes selectedTaxe;
   CentreCout selectedCentre;
   int idDep = 0;
 
   
   TypeEcriture typeOper;
 
 
   
   public List<Depense> getListDepense() {
     return this.listDepense;
   }
   
   public void setListDepense(List<Depense> listDepense) {
     this.listDepense = listDepense;
   }
   
   public List<Fournisseur> getListFournisseur() {
     return this.listFournisseur;
   }
   
   public void setListFournisseur(List<Fournisseur> listFournisseur) {
     this.listFournisseur = listFournisseur;
   }
   
   public List<TypeCharge> getListCharge() {
     return this.listCharge;
   }
   
   public void setListCharge(List<TypeCharge> listCharge) {
     this.listCharge = listCharge;
   }
   
   public List<SelectItem> getListTaxe() {
     return this.listTaxe;
   }
   
   public void setListTaxe(List<SelectItem> listTaxe) {
     this.listTaxe = listTaxe;
   }
   
   public List<SelectItem> getListDevise() {
     return this.listDevise;
   }
   
   public void setListDevise(List<SelectItem> listDevise) {
     this.listDevise = listDevise;
   }
   
   public List<SelectItem> getListCentre() {
     return this.listCentre;
   }
   
   public void setListCentre(List<SelectItem> listCentre) {
     this.listCentre = listCentre;
   }
   
   public Depense getSelectedDepense() {
     return this.selectedDepense;
   }
   
   public void setSelectedDepense(Depense selectedDepense) {
     this.selectedDepense = selectedDepense;
   }
   
   public int getIdTaxe() {
     return this.idTaxe;
   }
   
   public void setIdTaxe(int idTaxe) {
     this.idTaxe = idTaxe;
   }
   
   public int getIdDevise() {
     return this.idDevise;
   }
   
   public void setIdDevise(int idDevise) {
     this.idDevise = idDevise;
   }
   
   public int getIdCentre() {
     return this.idCentre;
   }
   
   public void setIdCentre(int idCentre) {
     this.idCentre = idCentre;
   }
   
   public Fournisseur getSelectedFourn() {
     return this.selectedFourn;
   }
   
   public void setSelectedFourn(Fournisseur selectedFourn) {
     this.selectedFourn = selectedFourn;
   }
   
   public TypeCharge getSelectedCharg() {
     return this.selectedCharg;
   }
   
   public void setSelectedCharg(TypeCharge selectedCharg) {
     this.selectedCharg = selectedCharg;
   }
   
   public boolean isDisableMsg() {
     return this.disableMsg;
   }
   public void setDisableMsg(boolean disableMsg) {
     this.disableMsg = disableMsg;
   }
   public String getCodeFrn() {
     return this.codeFrn;
   }
   public void setCodeFrn(String codeFrn) {
     this.codeFrn = codeFrn;
   }
   public String getLibelleFrn() {
     return this.libelleFrn;
   }
   public void setLibelleFrn(String libelleFrn) {
     this.libelleFrn = libelleFrn;
   }
   public String getCodeChrg() {
     return this.codeChrg;
   }
   public void setCodeChrg(String codeChrg) {
     this.codeChrg = codeChrg;
   }
   public String getLibelleChrg() {
     return this.libelleChrg;
   }
   public void setLibelleChrg(String libelleChrg) {
     this.libelleChrg = libelleChrg;
   }
   public String getPrintDate() {
     return this.printDate;
   }
   public void setPrintDate(String printDate) {
     this.printDate = printDate;
   }
   public String getRechLblFrn() {
     return this.rechLblFrn;
   }
   
   public void setRechLblFrn(String rechLblFrn) {
     this.rechLblFrn = rechLblFrn;
   }
   public String getRechLblChg() {
     return this.rechLblChg;
   }
   public void setRechLblChg(String rechLblChg) {
     this.rechLblChg = rechLblChg;
   }
   public String getRechDateDeb() {
     return this.rechDateDeb;
   }
   public void setRechDateDeb(String rechDateDeb) {
     this.rechDateDeb = rechDateDeb;
   }
   public String getRechDateFin() {
     return this.rechDateFin;
   }
   public void setRechDateFin(String rechDateFin) {
     this.rechDateFin = rechDateFin;
   }
   public Date getDateDebut() {
     return this.dateDebut;
   }
   public void setDateDebut(Date dateDebut) {
     this.dateDebut = dateDebut;
   }
   public Date getDateFin() {
     return this.dateFin;
   }
   public void setDateFin(Date dateFin) {
     this.dateFin = dateFin;
   }
   public String getInfoMsg() {
     return this.infoMsg;
   }
   public void setInfoMsg(String infoMsg) {
     this.infoMsg = infoMsg;
   }
   public String getPrintTot() {
     return this.printTot;
   }
   public void setPrintTot(String printTot) {
     this.printTot = printTot;
   }
   public String getPrintTotRgl() {
     return this.printTotRgl;
   }
   public void setPrintTotRgl(String printTotRgl) {
     this.printTotRgl = printTotRgl;
   }
   public String getPrintSolde() {
     return this.printSolde;
   }
   public void setPrintSolde(String printSolde) {
     this.printSolde = printSolde;
   }
   
   public Date getDateOp() {
     return this.dateOp;
   }
   
   public void setDateOp(Date dateOp) {
     this.dateOp = dateOp;
   }
   
   public double getTauxDevise() {
     return this.tauxDevise;
   }
   
   public void setTauxDevise(double tauxDevise) {
     this.tauxDevise = tauxDevise;
   }
   
   public double getTauxTva() {
     return this.tauxTva;
   }
   
   public void setTauxTva(double tauxTva) {
     this.tauxTva = tauxTva;
   }
   
   public double getMontantHt() {
     return this.montantHt;
   }
   
   public void setMontantHt(double montantHt) {
     this.montantHt = montantHt;
   }
   
   public double getMontantTvac() {
     return this.montantTvac;
   }
   
   public void setMontantTvac(double montantTvac) {
     this.montantTvac = montantTvac;
   }
   
   public String getComment() {
     return this.comment;
   }
   
   public void setComment(String comment) {
     this.comment = comment;
   }
   
   public String getPieceCmpt() {
     return this.pieceCmpt;
   }
   
   public void setPieceCmpt(String pieceCmpt) {
     this.pieceCmpt = pieceCmpt;
   }
 
   
   @PostConstruct
   public void initialize() {
     this.disableMsg = true;
     this.model = new DepenseModel();
     chargementSession();
     chargerTaxe();
     chargerDevise();
     chargerCentre();
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
       } else {
         
         UIComponent frmFactFourn = null;
         FacesContext context = FacesContext.getCurrentInstance();
         frmFactFourn = context.getViewRoot().findComponent("frmFF");
         
         UIComponent frmAutreDep = null;
         frmAutreDep = context.getViewRoot().findComponent("frmOD");
 
         
         if (frmFactFourn != null) {
           this.typeOper = TypeEcriture.factureFournisseur;
         }
         if (frmAutreDep != null) {
           this.typeOper = TypeEcriture.autreDepense;
         }
       } 
     } 
   }
 
   
   public void chargerDepense() {
     this.listDepense = this.model.getListDepense(this.factory, this.selecetdExercice.getId(), null, this.dateDebut, this.dateFin);
   }
   
   public void changeDate() {
     if (this.printDate.replace("/", "").replace("_", "").trim().equals("")) {
       
       this.dateOp = null;
     } else {
       
       this.dateOp = HelperC.validerDate(this.printDate);
       if (this.dateOp == null) {
         
         HelperC.afficherAttention("Attention", "Date invalide !");
       } else {
         
         this.printDate = HelperC.convertDate(this.dateOp);
         if (!HelperC.periodeValide(this.dateOp, this.selecetdExercice.getDateDebut(), this.selecetdExercice.getDateFin()))
         {
           HelperC.afficherAttention("Attention", "La date saisie est en dehors de l'exercice !");
         }
       } 
     } 
   }
   
   public void changeDateDebut() {
     if (this.rechDateDeb.replace("/", "").replace("_", "").trim().equals("")) {
       
       setDateDebut(null);
     } else {
       
       setDateDebut(HelperC.validerDate(this.rechDateDeb));
       if (getDateDebut() == null) {
         this.infoMsg = "Date invalide!";
       } else {
         
         this.rechDateDeb = HelperC.convertDate(getDateDebut());
       } 
     } 
   }
   
   public void changeDateFin() {
     if (this.rechDateFin.replace("/", "").replace("_", "").trim().equals("")) {
       
       setDateFin(null);
     } else {
       
       setDateFin(HelperC.validerDate(this.rechDateFin));
       if (getDateFin() == null) {
         
         this.infoMsg = "Date invalide!";
       } else {
         
         this.rechDateFin = HelperC.convertDate(getDateFin());
       } 
     } 
   }
   
   private void chargerTaxe() {
     this.listTaxe = new ArrayList<>();
     for (Taxes tx : (new TaxeModel()).getListTaxes(this.factory, "")) {
       this.listTaxe.add(new SelectItem(Integer.valueOf(tx.getId()), tx.getLibelle()));
     }
   }
 
   
   private void chargerDevise() {
     this.listDevise = new ArrayList<>();
     for (Devise dv : (new DeviseModel()).getListDevise(this.factory, "")) {
       this.listDevise.add(new SelectItem(Integer.valueOf(dv.getId()), dv.getLibelle()));
     }
   }
 
   
   private void chargerCentre() {
     this.listCentre = new ArrayList<>();
     for (CentreCout c : (new CentreCoutModel()).getListCentreCout(this.factory)) {
       this.listCentre.add(new SelectItem(Integer.valueOf(c.getId()), c.getLibelle()));
     }
   }
   
   public void changeCentreElement(ValueChangeEvent event) {
     if (event != null && event.getNewValue() != null) {
       
       this.idCentre = ((Integer)event.getNewValue()).intValue();
       centreValue();
     } 
   }
   
   private void centreValue() {
     if (this.idCentre > 0)
       this.selectedCentre = (new CentreCoutModel()).getCentreCoutById(this.idCentre, this.factory); 
   }
   public void changeTaxeElement(ValueChangeEvent event) {
     if (event != null && event.getNewValue() != null) {
       
       this.idTaxe = ((Integer)event.getNewValue()).intValue();
       taxeValue();
     } 
   }
   
   private void taxeValue() {
     if (this.idTaxe > 0) {
       
       this.selectedTaxe = (new TaxeModel()).getTaxeById(this.idTaxe, this.factory);
       if (this.selectedTaxe != null) {
         
         this.tauxTva = this.selectedTaxe.getTaux();
         calculerMontantTTC();
       } else {
         
         this.tauxTva = 0.0D;
       } 
     } 
   }
   public void changeDeviseElement(ValueChangeEvent event) {
     if (event != null && event.getNewValue() != null) {
       
       this.idDevise = ((Integer)event.getNewValue()).intValue();
       deviseValue();
     } 
   }
   
   private void deviseValue() {
     if (this.idDevise > 0) {
       this.selectedDev = (new DeviseModel()).getDeviseById(this.idDevise, this.factory);
     }
   }
   
   public void searchFournisseur() {
     if (this.codeFrn != null && !this.codeFrn.equals("")) {
       
       this.selectedFourn = (new FournisseurModel()).getFournisseurByCode(this.factory, this.codeFrn);
       if (this.selectedFourn != null)
       {
         getFrnValue();
       }
     } 
   }
   
   public void chargerFournisseur() {
     this.listFournisseur = (new FournisseurModel()).getListFournisseur(this.factory, this.rechLblFrn);
   }
 
   
   public void chargerCharge() {
     this.listCharge = (new TypeChargeModel()).getListCharge(this.factory, this.libelleChrg);
   }
   
   private void getFrnValue() {
     this.codeFrn = this.selectedFourn.getCodeFrn();
     this.libelleFrn = this.selectedFourn.getNameFrn();
   }
 
   
   public void getSelectedFrnValue() {
     if (this.selectedFourn != null) {
       
       getFrnValue();
       PrimeFaces.current().executeScript("PF('dlgFrn').hide();");
     } 
   }
   
   public void searchCharge() {
     if (this.codeChrg != null && !this.codeChrg.equals("")) {
       
       this.selectedCharg = (new TypeChargeModel()).getChargeByCode(this.factory, this.codeChrg);
       if (this.selectedCharg != null)
       {
         getChargeValue();
       }
     } 
   }
 
 
   
   private void getChargeValue() {
     this.codeChrg = this.selectedCharg.getCodeChg();
     this.libelleChrg = this.selectedCharg.getLibelle();
   }
 
   
   public void getSelectedChrValue() {
     if (this.selectedCharg != null) {
       
       getChargeValue();
       PrimeFaces.current().executeScript("PF('dlgChg').hide();");
     } 
   }
   
   public void getSelectedDepenseValue() {
     this.disableMsg = true;
     if (this.selectedDepense != null) {
       
       getDepenseValue();
       PrimeFaces.current().executeScript("PF('dlgDep').hide();");
     } 
   }
 
   
   public void calculerMontantTTC() {
     this.montantTvac = 0.0D;
     if (this.tauxTva > 0.0D) {
       
       this.montantTvac = this.montantHt * (1.0D + this.tauxTva / 100.0D);
     }
     else {
       
       this.montantTvac = this.montantHt;
     } 
   }
   public void calculerMontantHT() {
     this.montantHt = 0.0D;
     if (this.tauxTva > 0.0D) {
       
       this.montantHt = this.montantTvac / (1.0D + this.tauxTva / 100.0D);
     }
     else {
       
       this.montantHt = this.montantTvac;
     } 
   }
   private void getDepenseValue() {
     this.idDep = this.selectedDepense.getId();
     this.selectedCentre = this.selectedDepense.getCentre();
     this.selectedCharg = this.selectedDepense.getCharge();
     this.selectedDev = this.selectedDepense.getDevise();
     this.selectedFourn = this.selectedDepense.getFourn();
     this.selectedTaxe = this.selectedDepense.getTaxe();
     this.tauxDevise = this.selectedDepense.getCoursDev();
     this.dateOp = this.selectedDepense.getDateOperation();
     this.comment = this.selectedDepense.getLibelle();
     this.montantHt = this.selectedDepense.getMontant();
     this.tauxTva = this.selectedDepense.getTauxTaxe();
     this.pieceCmpt = this.selectedDepense.getPiece();
     this.printDate = HelperC.changeDateFormate(this.dateOp);
     if (this.selectedCentre != null) {
       
       this.idCentre = this.selectedCentre.getId();
       centreValue();
     } 
     if (this.selectedDev != null) {
       
       this.idDevise = this.selectedDev.getId();
       deviseValue();
     } 
     if (this.selectedTaxe != null) {
       
       this.idTaxe = this.selectedTaxe.getId();
       taxeValue();
     } 
     if (this.selectedFourn != null)
     {
       getFrnValue();
     }
     if (this.selectedCharg != null)
     {
       getChargeValue();
     }
     this.disableMsg = false;
   }
 
   
   public void save() {
     if (this.dateOp != null) {
       
       if (this.selectedDepense == null)
         this.selectedDepense = new Depense(); 
       this.selectedDepense.setId(this.idDep);
       this.selectedDepense.setCentre(this.selectedCentre);
       this.selectedDepense.setCharge(this.selectedCharg);
       this.selectedDepense.setCoursDev(this.tauxDevise);
       this.selectedDepense.setDateOperation(this.dateOp);
       this.selectedDepense.setDevise(this.selectedDev);
       this.selectedDepense.setFourn(this.selectedFourn);
       this.selectedDepense.setIdExercise(this.selecetdExercice.getId());
       this.selectedDepense.setLibelle(this.comment);
       this.selectedDepense.setMontant(this.montantHt);
       this.selectedDepense.setPiece(this.pieceCmpt);
       this.selectedDepense.setTauxTaxe(this.tauxTva);
       this.selectedDepense.setTaxe(this.selectedTaxe);
       this.selectedDepense.setTypeOperation(this.typeOper);
       
       if (this.selectedDepense.getId() == 0) {
         this.model.saveDepense(this.factory, this.selectedDepense);
       } else {
         this.model.updateDepense(this.factory, this.selectedDepense);
       } 
       initializeControl();
     } else {
       
       HelperC.afficherAttention("Attention", "Il faut préciser la date");
     } 
   }
   
   public void delete() {
     if (this.selectedDepense != null && this.selectedDepense.getId() > 0) {
       
       this.model.deleteDepense(this.factory, this.selectedDepense);
       initializeControl();
     } 
   }
 
   
   public void initializeControl() {
     this.idDep = 0;
     this.selectedCentre = null;
     this.selectedCharg = null;
     this.selectedDev = null;
     this.selectedFourn = null;
     this.selectedTaxe = null;
     this.tauxTva = 0.0D;
     this.dateOp = null;
     this.comment = "";
     this.montantTvac = 0.0D;
     this.tauxDevise = 0.0D;
     this.montantHt = 0.0D;
     this.pieceCmpt = "";
     this.codeChrg = "";
     this.codeFrn = "";
     this.libelleChrg = "";
     this.libelleFrn = "";
     this.idCentre = 0;
     this.idDevise = 0;
     this.idTaxe = 0;
     this.disableMsg = true;
     this.selectedDepense = null;
   }
 }


