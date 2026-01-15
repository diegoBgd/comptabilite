package vewBean;

import entite.Depense;
import entite.Ecriture;
import entite.Encaissement;
import entite.Exercice;
import entite.Journal;
import entite.ParametreFinance;
import entite.ReglementClient;
import entite.ReglementFournisseur;
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
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;
import model.DepenseModel;
import model.EncaissementModel;
import model.ExerciceModel;
import model.JournalModel;
import model.ParametreFinanceModel;
import model.ReglementClientModel;
import model.ReglementModel;
import model.TypeChargeModel;
import model.UserModel;
import org.hibernate.SessionFactory;
import org.primefaces.PrimeFaces;
import persistances.DBConfiguration;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class AfficherEcritureVew implements Serializable {
	private static final long serialVersionUID = -264767355712423L;
	SessionFactory factory = DBConfiguration.getSessionFactory();

	private List<Ecriture> listeEcriture;

	private Journal selectedJrnl;

	private String codeJrnl;

	private String libelleJrnl;

	private String infoMsg;
	private String rechDateDeb;
	private String rechDateFin;
	private String rechLblJrnl;
	private String printTotDeb;
	private String printTotCrd;
	private String printSolde;
	private Date dateDebut;
	private Date dateFin;

	private List<SelectItem> listCharge;
	private int idTypeChrg;
	private int typeOperation;
	private List<Journal> listJrnl;
	private boolean pagination;
	TypeCharge selectedTypeChrg;
	Exercice selecetdExercice;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;
	List<Depense> listDepense;
	List<Encaissement> listEncaissement;
	List<ReglementClient> listReglementClt;
	List<ReglementFournisseur> listReglementFrn;
	List<Depense> listSortieFnd;
	ParametreFinance prm;
	
	public List<Ecriture> getListeEcriture() {
		return this.listeEcriture;
	}

	public void setListeEcriture(List<Ecriture> listeEcriture) {
		this.listeEcriture = listeEcriture;
	}

	public Journal getSelectedJrnl() {
		return this.selectedJrnl;
	}

	public void setSelectedJrnl(Journal selectedJrnl) {
		this.selectedJrnl = selectedJrnl;
	}

	public String getCodeJrnl() {
		return this.codeJrnl;
	}

	public void setCodeJrnl(String codeJrnl) {
		this.codeJrnl = codeJrnl;
	}

	public String getLibelleJrnl() {
		return this.libelleJrnl;
	}

	public void setLibelleJrnl(String libelleJrnl) {
		this.libelleJrnl = libelleJrnl;
	}

	public String getInfoMsg() {
		return this.infoMsg;
	}

	public void setInfoMsg(String infoMsg) {
		this.infoMsg = infoMsg;
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

	public List<SelectItem> getListCharge() {
		return this.listCharge;
	}

	public void setListCharge(List<SelectItem> listCharge) {
		this.listCharge = listCharge;
	}

	public int getIdTypeChrg() {
		return this.idTypeChrg;
	}

	public void setIdTypeChrg(int idTypeChrg) {
		this.idTypeChrg = idTypeChrg;
	}

	public List<Journal> getListJrnl() {
		return this.listJrnl;
	}

	public void setListJrnl(List<Journal> listJrnl) {
		this.listJrnl = listJrnl;
	}

	public String getRechLblJrnl() {
		return this.rechLblJrnl;
	}

	public void setRechLblJrnl(String rechLblJrnl) {
		this.rechLblJrnl = rechLblJrnl;
	}

	public String getPrintTotDeb() {
		return this.printTotDeb;
	}

	public void setPrintTotDeb(String printTotDeb) {
		this.printTotDeb = printTotDeb;
	}

	public String getPrintTotCrd() {
		return this.printTotCrd;
	}

	public void setPrintTotCrd(String printTotCrd) {
		this.printTotCrd = printTotCrd;
	}

	public String getPrintSolde() {
		return this.printSolde;
	}

	public void setPrintSolde(String printSolde) {
		this.printSolde = printSolde;
	}

	public int getTypeOperation() {
		return typeOperation;
	}

	public void setTypeOperation(int typeOperation) {
		this.typeOperation = typeOperation;
	}

	public boolean isPagination() {
		return this.pagination;
	}

	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}

	@PostConstruct
	public void initialize() {
		chargementSession();
		chargerTypeCharge();
		prm=new ParametreFinanceModel().getParameter(factory);
	}

	private void chargementSession() {
		this.session = HelperC.getSession();
		if (this.session != null) {
			this.exerCode = (String) this.session.getAttribute("exercice");
			this.currUserCode = (String) this.session.getAttribute("operateur");

			if (this.exerCode != null) {
				this.selecetdExercice = (new ExerciceModel()).getExercByCode(this.factory, this.exerCode);
			}
			if (this.currUserCode != null) {
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

	public void chargerJournal() {
		this.listJrnl = (new JournalModel()).getListJouranl(this.factory, this.rechLblJrnl);
	}

	public void searchJournal() {
		if (this.codeJrnl != null && !this.codeJrnl.equals("")) {

			this.selectedJrnl = (new JournalModel()).getJouralByCode(this.factory, this.codeJrnl);
			if (this.selectedJrnl != null) {
				getJrnlValues();
			} else {
				HelperC.afficherAttention("Attention", "Ce journal n'existe pas !");
			}
		}
	}

	public void getSelecedJrnlValue() {
		if (this.selectedJrnl != null) {

			getJrnlValues();
			PrimeFaces.current().executeScript("PF('dlgJrnl').hide();");
		}
	}

	private void getJrnlValues() {
		this.codeJrnl = this.selectedJrnl.getCodeJrnl();
		this.libelleJrnl = this.selectedJrnl.getLibelle();
	}

	private void chargerTypeCharge() {
		this.listCharge = new ArrayList<>();

		for (TypeCharge ty : (new TypeChargeModel()).getListCharge(this.factory, "")) {
			this.listCharge.add(new SelectItem(Integer.valueOf(ty.getId()), ty.getLibelle()));
		}
	}

	public void changeTypeChargElement(ValueChangeEvent event) {
		if (event != null && event.getNewValue() != null) {
			this.idTypeChrg = ((Integer) event.getNewValue()).intValue();
			if (this.idTypeChrg > 0)
				this.selectedTypeChrg = (new TypeChargeModel()).getChargeById(this.idTypeChrg, this.factory);
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

	public void chargerEcritures() {
		switch (this.typeOperation) {
		case 0:
			chargerEntree();
			break;
		case 1:
			chargerFactureClt();
			break;
		case 2:
			chargerReglementClt();
			break;
		case 3:
			chargerFactureFrn();
			break;
		case 4:
			chargerReglementFrn();
			break;
		case 5:
			chargerSortieFond();
			break;
		case 6:
			break;
		case 7:
			break;
		case 8:
			break;
		case 9:
			break;
		case 10:
			break;
		case 11:
			break;
		case 12:
			break;
		default:
			break;
		}
	}

	private void chargerEntree() {
		this.pagination = false;
		this.listEncaissement = (new EncaissementModel()).getListEncaissement(this.factory,
				this.selecetdExercice.getId(), this.dateDebut, this.dateFin, TypeEcriture.entreeFond);
		this.listeEcriture = new ArrayList<>();
		Ecriture ecriture = null;
		String compteDb="",compteCrd="";
		for (Encaissement enc : this.listEncaissement) {
			switch (enc.getModeReglement()) {
			case 1:
				compteDb=enc.getBank().getCodeCpbl();
				compteCrd=enc.getRecette().getCodeCpbl();
				break;
			case 2:				
					compteDb=prm.getCompteChqEnc();
					compteCrd=enc.getRecette().getCodeCpbl();				
				break;
			case 3:
				if (enc.getCompteBank() != null) {
					
					compteDb=enc.getCompteBank().getCodeCpb();
					compteCrd=enc.getRecette().getCodeCpbl();
				}
				break;

			default:
				break;
			}
			
			
			ecriture = createEcriture(compteDb, this.codeJrnl, enc.getPiece(),
					enc.getCommentaire(), enc.getMontantTTC().doubleValue(), enc.getDateOperation(), 0,TypeEcriture.entreeFond);
			this.listeEcriture.add(ecriture);
			
			ecriture = createEcriture(compteCrd, this.codeJrnl, enc.getPiece(),
					enc.getCommentaire(), enc.getMontantTTC().doubleValue(), enc.getDateOperation(), 1,TypeEcriture.entreeFond);
			this.listeEcriture.add(ecriture);
			
		}
		calculTotaux();
	}

	
	private void chargerFactureClt() {
		this.pagination = false;
		this.listEncaissement = (new EncaissementModel()).getListEncaissement(this.factory,
				this.selecetdExercice.getId(), this.dateDebut, this.dateFin, TypeEcriture.factureClient);
		this.listeEcriture = new ArrayList<>();
		Ecriture ecriture = null;
		String compteDb="",compteCrd="",compteTxe="";
		for (Encaissement enc : this.listEncaissement) {
			compteDb=enc.getPartener().getCodeCpbl();
			compteCrd=enc.getRecette().getCodeCpbl();
			
			ecriture = createEcriture(compteDb, this.codeJrnl, enc.getPiece(),
					enc.getCommentaire(), enc.getMontantTTC().doubleValue(), enc.getDateOperation(), 0,TypeEcriture.factureClient);
			this.listeEcriture.add(ecriture);
			
			ecriture = createEcriture(compteCrd, this.codeJrnl, enc.getPiece(),
					enc.getCommentaire(), enc.getMontant().doubleValue(), enc.getDateOperation(), 1,TypeEcriture.factureClient);
			this.listeEcriture.add(ecriture);
			
			if(enc.getTaxe()!=null)
			{
				compteTxe=enc.getTaxe().getCodeCpbl();
		
			ecriture = createEcriture(compteTxe, this.codeJrnl, enc.getPiece(),
					enc.getCommentaire(), enc.getMontantTVA().doubleValue(), enc.getDateOperation(), 1,TypeEcriture.factureClient);
			this.listeEcriture.add(ecriture);
			}
			
		}
		calculTotaux();
	}
	private void chargerReglementClt() {
		this.pagination = false;
		this.listReglementClt = new ReglementClientModel().getListReglement(this.factory,
				this.selecetdExercice.getId(), this.dateDebut, this.dateFin, null);
		this.listeEcriture = new ArrayList<>();
		Ecriture ecriture = null;
		String compteDb="",compteCrd="",compteTxe="";
		for (ReglementClient reg : this.listReglementClt) {
			reg.calculMontantTTC();
			switch (reg.getModeReglement()) {
			case 1:
				compteDb=reg.getCaisse().getCodeCpbl();
				compteCrd=reg.getEncaissement().getPartener().getCodeCpbl();
				break;
			case 2:				
					compteDb=prm.getCompteChqEnc();
					compteCrd=reg.getEncaissement().getPartener().getCodeCpbl();				
				break;
			case 3:
				if (reg.getAccount() != null) {
					
					compteDb=reg.getAccount().getCodeCpb();
					compteCrd=reg.getEncaissement().getPartener().getCodeCpbl();
				}
				break;

			default:
				break;
			}
			
			
			ecriture = createEcriture(compteDb, this.codeJrnl, reg.getPiece(),
					reg.getComment(), reg.getMontantTTC().doubleValue(), reg.getDateReglement(), 0,TypeEcriture.reglementClient);
			this.listeEcriture.add(ecriture);
			
			ecriture = createEcriture(compteCrd, this.codeJrnl, reg.getPiece(),
					reg.getComment(), reg.getMontantTTC().doubleValue(), reg.getDateReglement(), 1,TypeEcriture.reglementClient);
			this.listeEcriture.add(ecriture);
			
		}
	
		calculTotaux();
	}
	private void chargerFactureFrn() {
		this.listeEcriture = new ArrayList<>();
		this.listDepense = (new DepenseModel()).getListDepense(this.factory, this.selecetdExercice.getId(),
				TypeEcriture.factureFournisseur, this.dateDebut, this.dateFin);
		Ecriture ecriture = null;
		String compteDb="",compteCrd="",compteTxe="";
		for (Depense dep : this.listDepense) {

			
			compteDb=dep.getCharge().getCodeCpbl();
			compteCrd=dep.getPartener().getCodeCpbl();
			
			dep.calculMontantTTC();
			ecriture = createEcriture(compteDb, this.codeJrnl, dep.getPiece(), dep.getLibelle(),
					dep.getMontantTTC().doubleValue(), dep.getDateOperation(), 0,TypeEcriture.factureFournisseur);
			this.listeEcriture.add(ecriture);
			
			ecriture = createEcriture(compteCrd, this.codeJrnl, dep.getPiece(), dep.getLibelle(),
					dep.getMontant().doubleValue(), dep.getDateOperation(), 1,TypeEcriture.factureFournisseur);
			this.listeEcriture.add(ecriture);
			
			if(dep.getTaxe()!=null)
			{
				compteTxe=dep.getTaxe().getCodeCpbl();
			
			ecriture = createEcriture(compteTxe, this.codeJrnl, dep.getPiece(), dep.getLibelle(),
					dep.getMontantTTC().doubleValue()-dep.getMontant().doubleValue(), dep.getDateOperation(), 1,TypeEcriture.factureFournisseur);
			this.listeEcriture.add(ecriture);
			}
		}

		calculTotaux();
	}

	private void chargerReglementFrn() {
		this.pagination = false;
		this.listReglementFrn = new ReglementModel(). getListReglement (this.factory,
				this.selecetdExercice.getId(), this.dateDebut, this.dateFin, TypeEcriture.reglementFournisseur,null);
		this.listeEcriture = new ArrayList<>();
		Ecriture ecriture = null;
		String compteDb="",compteCrd="",compteTxe="";
		
		for (ReglementFournisseur rf : listReglementFrn) {
			rf.calculMontantTTC();
			switch (rf.getModeReglement()) {
			case 1:
				compteDb=rf.getDepense().getPartener().getCodeCpbl();
				compteCrd=rf.getCaisse().getCodeCpbl();
				
				break;
			case 2:		
				compteDb=rf.getDepense().getPartener().getCodeCpbl();
				compteCrd=prm.getCompteChqEm();
								
				break;
			case 3:
				if (rf.getAccount() != null) {
					compteDb=rf.getDepense().getPartener().getCodeCpbl();
					compteCrd=rf.getAccount().getCodeCpb();
					
				}
				break;

			default:
				break;
			}
			
			ecriture = createEcriture(compteDb, this.codeJrnl, rf.getPiece(),
					rf.getComment(), rf.getMontantTTC().doubleValue(), rf.getDateReglement(), 0,TypeEcriture.reglementFournisseur);
			this.listeEcriture.add(ecriture);
			
			ecriture = createEcriture(compteCrd, this.codeJrnl, rf.getPiece(),
					rf.getComment(), rf.getMontantTTC().doubleValue(), rf.getDateReglement(), 1,TypeEcriture.reglementFournisseur);
			this.listeEcriture.add(ecriture);
			
		}
		calculTotaux();
	}
	private void chargerSortieFond() {
		this.listeEcriture = new ArrayList<>();
		this.listDepense = (new DepenseModel()).getListDepense(this.factory, this.selecetdExercice.getId(),
				TypeEcriture.sortieFond, this.dateDebut, this.dateFin);
		Ecriture ecriture = null;
		String compteDb="",compteCrd="",compteTxe="";
		for (Depense dep : this.listDepense) {

			compteDb=dep.getCharge().getCodeCpbl();
			compteCrd=dep.getPartener().getCodeCpbl();
			
			dep.calculMontantTTC();
			ecriture = createEcriture(compteDb, this.codeJrnl, dep.getPiece(), dep.getLibelle(),
					dep.getMontantTTC().doubleValue(), dep.getDateOperation(), 0,TypeEcriture.sortieFond);
			this.listeEcriture.add(ecriture);
			
			ecriture = createEcriture(compteCrd, this.codeJrnl, dep.getPiece(), dep.getLibelle(),
					dep.getMontant().doubleValue(), dep.getDateOperation(), 1,TypeEcriture.sortieFond);
			this.listeEcriture.add(ecriture);
		

			if(dep.getTaxe()!=null) {
				compteTxe=dep.getTaxe().getCodeCpbl();
			
			ecriture = createEcriture(compteTxe, this.codeJrnl, dep.getPiece(), dep.getLibelle(),
					dep.getMontantTTC().doubleValue()-dep.getMontant().doubleValue(), dep.getDateOperation(), 1,TypeEcriture.sortieFond);
			this.listeEcriture.add(ecriture);
			}
		}

		calculTotaux();
	}
	private void calculTotaux() {
		double totDeb = 0.0D, totCrd = 0.0D, solde = 0.0D;
		if (this.listeEcriture.size() > 0) {
			this.pagination = true;
			for (Ecriture ec : this.listeEcriture) {
				totDeb += ec.getDebit();
				totCrd += ec.getCredit();
			}
		}
		solde = Math.abs(totDeb - totCrd);
		this.printTotCrd = HelperC.decimalNumber(totCrd, 0, true);
		this.printTotDeb = HelperC.decimalNumber(totDeb, 0, true);
		this.printSolde = HelperC.decimalNumber(solde, 0, true);
	}

	private Ecriture createEcriture(String compte, String journal, String piece, String libelle, double montant,
			Date dateEcr, int sensEcriture,TypeEcriture type) {
		Ecriture ecr = null;

		ecr = new Ecriture();
		ecr.setCpte(compte);
		ecr.setJrnl(journal);
		ecr.setDateOperation(dateEcr);
		switch (sensEcriture) {

		case 0:
			ecr.setDebit(montant);
			ecr.setCredit(0.0D);
			break;

		case 1:
			ecr.setDebit(0.0D);
			ecr.setCredit(montant);
			break;
		}

		ecr.setIdExercise(this.selecetdExercice.getId());
		ecr.setLibelle(libelle);

		ecr.setPieceCpb(piece);
		ecr.setTypeOperation(type);

		return ecr;
	}
}
