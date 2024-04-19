 package vewBean;
 
 import entite.Clients;
 import entite.Devise;
 import entite.Exercice;
 import entite.Facture;
 import entite.FactureDetail;
 import entite.FactureTaxe;
 import entite.Produit;
 import entite.User;
 import java.io.IOException;
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import javax.annotation.PostConstruct;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import javax.faces.context.FacesContext;
 import javax.servlet.http.HttpSession;
 import model.ClientModel;
 import model.ExerciceModel;
 import model.FactureModel;
 import model.ProduitModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 
 
 
 
 
 
 @ManagedBean
 @ViewScoped
 public class FacturationVew
   implements Serializable
 {
   private static final long serialVersionUID = -5860159408701121533L;
   SessionFactory factory = DBConfiguration.getSessionFactory();
   
   private Facture selectedFacture;
   
   private Facture selectedProforma;
   
   private FactureDetail selectedDetail;
   private List<FactureDetail> listDetail;
   private List<Produit> listProd;
   private List<Clients> listClts;
   private List<FactureTaxe> listTaxe;
   private Date dateOp;
   private Date dateEch;
   private Produit selectedProd;
   private Clients selectedClient;
   private String clientName;
   private String clientCod;
   private String commentaire;
   private String rechClient;
   private String printDate;
   private String printDatEch;
   private String prdCod;
   private String prdName;
   private String rechProd;
   private double montantPU;
   int idFact = 0; private double qte; private double tva; private double pf; private double tc; private double avance; private double tauxTva; private double tauxPf; private double tauxTc; private double reduction; private double tauxReduce; private boolean disableSave; private int baseCalcul; private int numFacture; boolean selected; Exercice selecetdExercice; HttpSession session; String exerCode; String currUserCode; User currentUser;
   Devise selectedDev;
   FactureModel model;
   int index;
   
   public Facture getSelectedFacture() {
     return this.selectedFacture;
   }
   
   public void setSelectedFacture(Facture selectedFacture) {
     this.selectedFacture = selectedFacture;
   }
   
   public Facture getSelectedProforma() {
     return this.selectedProforma;
   }
   
   public void setSelectedProforma(Facture selectedProforma) {
     this.selectedProforma = selectedProforma;
   }
   
   public FactureDetail getSelectedDetail() {
     return this.selectedDetail;
   }
   
   public void setSelectedDetail(FactureDetail selectedDetail) {
     this.selectedDetail = selectedDetail;
   }
   
   public List<FactureDetail> getListDetail() {
     return this.listDetail;
   }
   
   public void setListDetail(List<FactureDetail> listDetail) {
     this.listDetail = listDetail;
   }
   
   public Clients getSelectedClient() {
     return this.selectedClient;
   }
   
   public void setSelectedClient(Clients selectedClient) {
     this.selectedClient = selectedClient;
   }
   
   public String getClientName() {
     return this.clientName;
   }
   
   public void setClientName(String clientName) {
     this.clientName = clientName;
   }
   
   public String getClientCod() {
     return this.clientCod;
   }
   
   public void setClientCod(String clientCod) {
     this.clientCod = clientCod;
   }
   
   public String getRechClient() {
     return this.rechClient;
   }
   
   public void setRechClient(String rechClient) {
     this.rechClient = rechClient;
   }
   
   public String getPrintDate() {
     return this.printDate;
   }
   
   public void setPrintDate(String printDate) {
     this.printDate = printDate;
   }
   
   public double getMontantPU() {
     return this.montantPU;
   }
   
   public void setMontantPU(double montantPU) {
     this.montantPU = montantPU;
   }
   
   public double getQte() {
     return this.qte;
   }
   
   public void setQte(double qte) {
     this.qte = qte;
   }
   
   public double getTva() {
     return this.tva;
   }
   
   public void setTva(double tva) {
     this.tva = tva;
   }
   
   public double getPf() {
     return this.pf;
   }
   
   public void setPf(double pf) {
     this.pf = pf;
   }
   
   public double getTc() {
     return this.tc;
   }
   
   public void setTc(double tc) {
     this.tc = tc;
   }
   
   public double getTauxTva() {
     return this.tauxTva;
   }
   
   public void setTauxTva(double tauxTva) {
     this.tauxTva = tauxTva;
   }
   
   public double getTauxPf() {
     return this.tauxPf;
   }
   
   public void setTauxPf(double tauxPf) {
     this.tauxPf = tauxPf;
   }
   
   public double getTauxTc() {
     return this.tauxTc;
   }
   
   public void setTauxTc(double tauxTc) {
     this.tauxTc = tauxTc;
   }
   
   public double getReduction() {
     return this.reduction;
   }
   
   public void setReduction(double reduction) {
     this.reduction = reduction;
   }
   
   public double getTauxReduce() {
     return this.tauxReduce;
   }
   
   public void setTauxReduce(double tauxReduce) {
     this.tauxReduce = tauxReduce;
   }
   
   public List<Produit> getListProd() {
     return this.listProd;
   }
   
   public void setListProd(List<Produit> listProd) {
     this.listProd = listProd;
   }
   
   public Produit getSelectedProd() {
     return this.selectedProd;
   }
   
   public void setSelectedProd(Produit selectedProd) {
     this.selectedProd = selectedProd;
   }
   
   public String getPrdCod() {
     return this.prdCod;
   }
   
   public void setPrdCod(String prdCod) {
     this.prdCod = prdCod;
   }
   
   public String getPrdName() {
     return this.prdName;
   }
   
   public void setPrdName(String prdName) {
     this.prdName = prdName;
   }
   
   public String getRechProd() {
     return this.rechProd;
   }
   
   public void setRechProd(String rechProd) {
     this.rechProd = rechProd;
   }
   public List<Clients> getListClts() {
     return this.listClts;
   }
   public void setListClts(List<Clients> listClts) {
     this.listClts = listClts;
   }
   
   public int getBaseCalcul() {
     return this.baseCalcul;
   }
   
   public void setBaseCalcul(int baseCalcul) {
     this.baseCalcul = baseCalcul;
   }
   
   public boolean isDisableSave() {
     return this.disableSave;
   }
   
   public void setDisableSave(boolean disableSave) {
     this.disableSave = disableSave;
   }
   
   public String getPrintDatEch() {
     return this.printDatEch;
   }
   
   public void setPrintDatEch(String printDatEch) {
     this.printDatEch = printDatEch;
   }
   public List<FactureTaxe> getListTaxe() {
     return this.listTaxe;
   }
   
   public void setListTaxe(List<FactureTaxe> listTaxe) {
     this.listTaxe = listTaxe;
   }
   
   public Date getDateOp() {
     return this.dateOp;
   }
   
   public void setDateOp(Date dateOp) {
     this.dateOp = dateOp;
   }
   
   public Date getDateEch() {
     return this.dateEch;
   }
   
   public void setDateEch(Date dateEch) {
     this.dateEch = dateEch;
   }
   
   public int getNumFacture() {
     return this.numFacture;
   }
   
   public void setNumFacture(int numFacture) {
     this.numFacture = numFacture;
   }
   
   public String getCommentaire() {
     return this.commentaire;
   }
   
   public void setCommentaire(String commentaire) {
     this.commentaire = commentaire;
   }
   
   public double getAvance() {
     return this.avance;
   }
   
   public void setAvance(double avance) {
     this.avance = avance;
   }
 
 
   
   @PostConstruct
   public void initialize() {
     this.printDate = HelperC.convertDate(new Date());
     this.model = new FactureModel();
     this.dateOp = new Date();
     chargementSession();
     this.listDetail = new ArrayList<>();
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
 
   
   public void searchClient() {
     if (this.clientCod != null && this.clientCod.equals("")) {
       
       this.selectedClient = (new ClientModel()).getClientByCode(this.factory, getClientCod());
       getClientValues();
     } 
   }
   
   private void getClientValues() {
     if (this.selectedClient != null) {
       
       this.clientCod = this.selectedClient.getCodeClt();
       this.clientName = this.selectedClient.getNameClt();
     } 
   }
   
   public void getSelectedCltValue() {
     getClientValues();
   }
   
   public void chargerClient() {
     this.listClts = (new ClientModel()).getListClients(this.factory, this.rechClient);
   }
 
   
   public void chargerProduits() {
     this.listProd = (new ProduitModel()).getListProduit(this.factory, this.rechProd);
   }
 
   
   public void searchProduct() {
     if (this.prdCod != null && !this.prdCod.equals("")) {
       
       this.selectedProd = (new ProduitModel()).getProduitByCode(this.factory, this.prdCod);
       getProductValue();
     } 
   }
 
   
   private void getProductValue() {
     if (this.selectedProd != null) {
       
       this.prdCod = this.selectedProd.getCodePrd();
       this.prdName = this.selectedProd.getLibelle();
     } 
   }
 
 
   
   public void getSelectedProductValue() {
     getProductValue();
   }
   
   public void changeDate() {
     this.disableSave = false;
     if (this.printDate.replace("/", "").replace("_", "").trim().equals("")) {
       this.dateOp = null;
     }
     else {
       
       this.dateOp = HelperC.validerDate(this.printDate);
       if (this.dateOp == null) {
         this.disableSave = true;
         HelperC.afficherAttention("Attention", "Date invalide !");
       } else {
         
         this.printDate = HelperC.convertDate(this.dateOp);
         if (!HelperC.periodeValide(this.dateOp, this.selecetdExercice.getDateDebut(), 
             this.selecetdExercice.getDateFin())) {
           HelperC.afficherAttention("Attention", "La date saisie est en dehors de l'exercice !");
           this.disableSave = true;
         } 
       } 
     } 
   }
 
   
   public void changeDateEcheance() {
     this.disableSave = false;
     if (this.printDatEch.replace("/", "").replace("_", "").trim().equals("")) {
       this.dateEch = null;
     }
     else {
       
       this.dateEch = HelperC.validerDate(this.printDatEch);
       if (this.dateEch == null) {
         this.disableSave = true;
         HelperC.afficherAttention("Attention", "Date invalide !");
       } else {
         
         this.printDatEch = HelperC.convertDate(this.dateEch);
       } 
     } 
   }
 
   
   public void addDetail() {
     if (this.selectedDetail == null)
       this.selectedDetail = new FactureDetail(); 
     this.selectedDetail.setPrixUnitaire(this.montantPU);
     this.selectedDetail.setQuantity(this.qte);
     this.selectedDetail.setRemiseForfait(this.reduction);
     this.selectedDetail.setTauxRemise(this.tauxReduce);
     this.selectedDetail.setProduct(this.selectedProd);
     this.selectedDetail.setProdCod(this.prdCod);
     this.selectedDetail.setProdName(this.prdName);
     this.selectedDetail.setTypeBase(this.baseCalcul);
     if (!this.selected) {
       this.listDetail.add(this.selectedDetail);
     } else {
       this.listDetail.remove(this.selectedDetail);
       this.listDetail.add(this.index, this.selectedDetail);
     } 
   }
 
   
   public void removeElement() {
     if (this.selectedDetail != null)
     {
       this.listDetail.remove(this.selectedDetail); } 
   }
   
   public void getSelectedDetailValue() {
     if (this.selectedDetail != null) {
       this.montantPU = this.selectedDetail.getPrixUnitaire();
       this.tauxReduce = this.selectedDetail.getTauxRemise();
       this.baseCalcul = this.selectedDetail.getTypeBase();
       this.selectedProd = this.selectedDetail.getProduct();
       getProductValue();
       this.selected = true;
       this.index = this.listDetail.indexOf(this.selectedDetail);
     } 
   }
   
   public void getSelectedFacturation() {
     this.idFact = this.selectedFacture.getId();
     this.numFacture = this.selectedFacture.getNumeroFact();
     this.avance = this.selectedFacture.getAccompte();
     this.dateEch = this.selectedFacture.getDateEcheance();
     this.dateEch = this.selectedFacture.getDateOperation();
     this.commentaire = this.selectedFacture.getComment();
     this.selectedClient = this.selectedFacture.getCustomer();
     getClientValues();
     this.listDetail = this.selectedFacture.getFactureDetail();
   }
   
   public void save() {
     if (this.selectedFacture == null)
       this.selectedFacture = new Facture(); 
     this.selectedFacture.setId(this.idFact);
     this.selectedFacture.setAccompte(this.avance);
     this.selectedFacture.setComment(this.commentaire);
     this.selectedFacture.setCustomer(this.selectedClient);
     this.selectedFacture.setDateEcheance(this.dateEch);
     this.selectedFacture.setDateOperation(this.dateOp);
     this.selectedFacture.setExercise(this.selecetdExercice);
     this.selectedFacture.setFactureDetail(this.listDetail);
     this.selectedFacture.setNomClient(this.clientName);
     this.selectedFacture.setNumeroFact(this.numFacture);
     this.selectedFacture.setTypeOperation(0);
     
     if (this.selectedFacture.getId() == 0)
       this.model.saveFacturation(this.factory, this.selectedFacture); 
   }
 }


