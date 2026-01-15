package reportEdition;

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

import org.hibernate.SessionFactory;

import entite.BankAccount;
import entite.Banque;
import entite.Encaissement;
import entite.Exercice;
import entite.MoneyInOut;
import entite.ReglementClient;
import entite.TypeEcriture;
import entite.User;
import model.BankAccountModel;
import model.BanqueModel;
import model.EncaissementModel;
import model.ExerciceModel;
import model.ReglementClientModel;
import model.UserModel;
import persistances.DBConfiguration;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class EntreeSortieFond implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6706225985254917088L;
	SessionFactory factory = DBConfiguration.getSessionFactory();
	private String rechDateDeb;
	private String rechDateFin;
	private Date dateDebut;
	private Date dateFin;
	private List<SelectItem> listeCpte;
	private List<SelectItem>listBanque;
	private List<MoneyInOut> listES;
	private int idBk;
	private int idCpte;
	private String printTot;
	private  boolean pagination; 
	Banque selectedBk;
	BankAccount selectedCompte;
	Exercice selecetdExercice;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;
	double total;
	
	List<Encaissement>listEntre;
	List<ReglementClient>listRglClt;
	public EntreeSortieFond() {
		
	}

	public String getRechDateDeb() {
		return rechDateDeb;
	}

	public void setRechDateDeb(String rechDateDeb) {
		this.rechDateDeb = rechDateDeb;
	}

	public String getRechDateFin() {
		return rechDateFin;
	}

	public void setRechDateFin(String rechDateFin) {
		this.rechDateFin = rechDateFin;
	}
	
	public List<SelectItem> getListeCpte() {
		return listeCpte;
	}

	public void setListeCpte(List<SelectItem> listeCpte) {
		this.listeCpte = listeCpte;
	}

	public List<SelectItem> getListBanque() {
		return listBanque;
	}

	public void setListBanque(List<SelectItem> listBanque) {
		this.listBanque = listBanque;
	}

	public int getIdBk() {
		return idBk;
	}

	public void setIdBk(int idBk) {
		this.idBk = idBk;
	}

	public int getIdCpte() {
		return idCpte;
	}

	public void setIdCpte(int idCpte) {
		this.idCpte = idCpte;
	}

	public List<Encaissement> getListEntre() {
		return listEntre;
	}

	public void setListEntre(List<Encaissement> listEntre) {
		this.listEntre = listEntre;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public List<MoneyInOut> getListES() {
		return listES;
	}

	public void setListES(List<MoneyInOut> listES) {
		this.listES = listES;
	}

	public String getPrintTot() {
		return printTot;
	}

	public void setPrintTot(String printTot) {
		this.printTot = printTot;
	}

	public boolean isPagination() {
		return pagination;
	}

	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}

	@PostConstruct
	public void initialize() {
		
		chargementSession();
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
			else {
				
				chargerBank();
				
			}
		}
	}
	
	private void chargerBank() {
		listBanque = new ArrayList<>();
		for (Banque bk : new BanqueModel().getListBanque(factory, "")) {			
			listBanque.add(new SelectItem(bk.getId(), bk.getLibelle()));
		}
	}

	private void chargerBankAccount() {
		listeCpte = new ArrayList<>();
		for (BankAccount cpte : new BankAccountModel().getListAccount(factory, selectedBk)) {
			listeCpte.add(new SelectItem(Integer.valueOf(cpte.getId()), cpte.getLibelle()));
		}
	}

	public void changeBankElement(ValueChangeEvent event) {
		if (event != null && event.getNewValue() != null) {

			idBk = ((Integer) event.getNewValue()).intValue();
			selectedBk=new BanqueModel().getBanqueById(idBk, factory);
			chargerBankAccount();
		}
	}
	
	public void changeBankAcountElement(ValueChangeEvent event) {
		if (event != null && event.getNewValue() != null) {

			idCpte = ((Integer) event.getNewValue()).intValue();
			selectedCompte=new BankAccountModel().getBankAccountById(idCpte, factory);
		}
	}
	public void changeDateDebut() {
		
		if (this.rechDateDeb.replace("/", "").replace("_", "").trim().equals("")) {

			setDateDebut(null);
		} else {

			setDateDebut(HelperC.validerDate(this.rechDateDeb));
			if (getDateDebut() == null) {
				HelperC.afficherAttention("Attention", "Date invalide! !");
				
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

				HelperC.afficherAttention("Attention", "Date invalide! !");
			} else {

				this.rechDateFin = HelperC.convertDate(getDateFin());
			}
		}
	}
	public void chargerOperation() {
		listEntre=new EncaissementModel().getHistoriquEncaissement(factory, selecetdExercice.getId(), dateDebut, dateFin,selectedBk,selectedCompte);
		listRglClt=new ReglementClientModel().getHistoriqueReglement(factory, selecetdExercice.getId(), dateDebut, dateFin,selectedBk,selectedCompte);
		listES=new ArrayList<MoneyInOut>();
		total=0;
		MoneyInOut mio;
		pagination=false;
		for (Encaissement enc : listEntre) {
			enc.calculMontantTTC();
			total+=enc.getMontantTTC().doubleValue();
			
			mio=new MoneyInOut();
			mio.setComment(enc.getCommentaire());
			mio.setMotantTTC(enc.getMontantTTC().doubleValue());
			mio.setPrintDate(enc.getDateToPrint());
			mio.setReference(enc.getNumOperation());
			mio.setPrintTTC(enc.getAmountToPrint());
			mio.setTiers(enc.getRecette().getLibelle());
			
			
			switch (enc.getModeReglement()) {
			case 1:
				mio.setPaiment("ESPECE");
			
				break;
			case 2:
				mio.setPaiment("CHEQUE : "+enc.getPiece());
				
				break;
			case 3:
				mio.setPaiment("VIREMENT : "+enc.getPiece());
				
				break;
			default:
				break;
			}
			if(enc.getTypeEntree().equals(TypeEcriture.factureClient))
				mio.setPaiment(enc.getNumOperation());
			listES.add(mio);
			
		}
		
		for (ReglementClient rgl : listRglClt) {
			rgl.calculMontantTTC();
			total+=rgl.getMontantTTC().doubleValue();
			
			mio=new MoneyInOut();
			mio.setComment(rgl.getComment());
			mio.setMotantTTC(rgl.getMontantTTC().doubleValue());
			mio.setPrintDate(rgl.getDateToPrint());
			mio.setPrintTTC(rgl.getAmountToPrint());
			mio.setTiers("REGLEMENT CLIENT");
			
			switch (rgl.getModeReglement()) {
			case 1:
				mio.setPaiment("ESPECE");
				
				break;
			case 2:
				mio.setPaiment("CHEQUE : "+rgl.getPiece());
				
				break;
			case 3:
				mio.setPaiment("VIREMENT : "+rgl.getPiece());
				
				break;
			default:
				break;
			}
			listES.add(mio);
		}
		if(listES.size()>10)
			pagination=true;
		printTot=HelperC.decimalNumber(total, 0, true);
	}

	
}
