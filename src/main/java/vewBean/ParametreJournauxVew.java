package vewBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
import org.primefaces.PrimeFaces;

import entite.Exercice;
import entite.Journal;
import entite.ParametreJournaux;
import entite.TypeEcriture;
import entite.User;
import model.ExerciceModel;
import model.JournalModel;
import model.ParametreJournauxModel;
import model.UserModel;
import persistances.DBConfiguration;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class ParametreJournauxVew implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4088346546613111400L;

	private boolean disableMsg;
	private String codeJrnl;
	private String libelleJrnl;
	private List<Journal> listJournal;
	private List<ParametreJournaux>listParam;
	private ParametreJournaux selectedParm;
	private String motRech;
	private Journal selectedJrnl;
	private int typeOperation;
	
	Exercice selecetdExercice;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;
	SessionFactory factory = DBConfiguration.getSessionFactory();
	ParametreJournauxModel model;
	int idPrm = 0;
    TypeEcriture type;
    
	public boolean isDisableMsg() {
		return disableMsg;
	}

	public void setDisableMsg(boolean disableMsg) {
		this.disableMsg = disableMsg;
	}

	public String getCodeJrnl() {
		return codeJrnl;
	}

	public void setCodeJrnl(String codeJrnl) {
		this.codeJrnl = codeJrnl;
	}

	public String getLibelleJrnl() {
		return libelleJrnl;
	}

	public void setLibelleJrnl(String libelleJrnl) {
		this.libelleJrnl = libelleJrnl;
	}

	public List<Journal> getListJournal() {
		return listJournal;
	}

	public void setListJournal(List<Journal> listJournal) {
		this.listJournal = listJournal;
	}

	public ParametreJournaux getSelectedParm() {
		return selectedParm;
	}

	public void setSelectedParm(ParametreJournaux selectedParm) {
		this.selectedParm = selectedParm;
	}

	public String getMotRech() {
		return motRech;
	}

	public void setMotRech(String motRech) {
		this.motRech = motRech;
	}

	public int getTypeOperation() {
		return typeOperation;
	}

	public void setTypeOperation(int typeOperation) {
		this.typeOperation = typeOperation;
	}

	public List<ParametreJournaux> getListParam() {
		return listParam;
	}

	public void setListParam(List<ParametreJournaux> listParam) {
		this.listParam = listParam;
	}

	public Journal getSelectedJrnl() {
		return selectedJrnl;
	}

	public void setSelectedJrnl(Journal selectedJrnl) {
		this.selectedJrnl = selectedJrnl;
	}

	@PostConstruct
	public void initialize() {
		this.model = new ParametreJournauxModel();
		this.disableMsg = true;
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
		}
	}

	public void chargerJournal() {
		this.listJournal = (new JournalModel()).getListJouranl(this.factory, this.motRech);

	}

	public void chargerParam() {
		listParam=model.getAllParmJournaux(factory);
	}
	public void getSelctedJournal() {
		if (this.selectedJrnl != null) {

			this.codeJrnl = this.selectedJrnl.getCodeJrnl();
			this.libelleJrnl = this.selectedJrnl.getLibelle();
			PrimeFaces.current().executeScript("PF('dlgJrnl').hide();");
		}
	}

	public void searchJournal() {
		if (this.codeJrnl != null && !this.codeJrnl.equals("")) {

			this.selectedJrnl = (new JournalModel()).getJouralByCode(this.factory, this.codeJrnl);
			if (this.selectedJrnl != null) {

				this.codeJrnl = this.selectedJrnl.getCodeJrnl();
				this.libelleJrnl = this.selectedJrnl.getLibelle();
			}
		}
	}
	
	public void getSelecedParamValue() {
		if (this.selectedParm != null) {

			getParamValues();
			PrimeFaces.current().executeScript("PF('dlgCpt').hide();");
		}
	}
	private void getParamValues() {
		
		this.type = this.selectedParm.getTypeOperation();
		this.idPrm=this.selectedParm.getId();
		this.codeJrnl=selectedParm.getCodeJournal();
		searchJournal();
	}

	public void save() {
		if (this.selectedParm == null)
			this.selectedParm = new ParametreJournaux();
		this.selectedParm.setId(this.idPrm);
		this.selectedParm.setCodeJournal(codeJrnl);
		this.setTypeOperation(typeOperation);
		chargerParam();
	}
	 
	public void delete() {
		if(selectedParm!=null && selectedParm.getId()>0)
			model.deleteParmJournaux(factory, selectedParm);
	}
	 public void initializeControl() {
	     this.idPrm = 0;
	     typeOperation=0;
	     this.selectedJrnl = null;
	     this.selectedParm = null;
	     this.codeJrnl = "";
	     this.libelleJrnl = "";
	   }
}
