package vewBean;

import entite.Compte;
import entite.Exercice;
import entite.Journal;
import entite.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import model.CompteModel;
import model.ExerciceModel;
import model.JournalModel;
import model.UserModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SessionFactory;
import org.primefaces.PrimeFaces;

import persistances.DBConfiguration;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class JournalVew implements Serializable {
	private static final long serialVersionUID = 6366056400321382559L;
	SessionFactory factory = DBConfiguration.getSessionFactory();

	private Journal selectedJournal;

	private List<Journal> listJrnl;

	private boolean disableMsg;

	private String code,rechLblCpt,rechCodCpt,codeCpt,libelleCpt;

	private String designation;
	 private Compte selectedCpte;
	 private List<Compte> listeCpte;
	int idJrn = 0;
	JournalModel model;
	Exercice selecetdExercice;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;

	public Journal getSelectedJournal() {
		return this.selectedJournal;
	}

	public void setSelectedJournal(Journal selectedJournal) {
		this.selectedJournal = selectedJournal;
	}

	public List<Journal> getListJrnl() {
		return this.listJrnl;
	}

	public void setListJrnl(List<Journal> listJrnl) {
		this.listJrnl = listJrnl;
	}

	public boolean isDisableMsg() {
		return this.disableMsg;
	}

	public void setDisableMsg(boolean disableMsg) {
		this.disableMsg = disableMsg;
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

	public String getRechLblCpt() {
		return rechLblCpt;
	}

	public void setRechLblCpt(String rechLblCpt) {
		this.rechLblCpt = rechLblCpt;
	}

	public String getRechCodCpt() {
		return rechCodCpt;
	}

	public void setRechCodCpt(String rechCodCpt) {
		this.rechCodCpt = rechCodCpt;
	}

	public String getCodeCpt() {
		return codeCpt;
	}

	public void setCodeCpt(String codeCpt) {
		this.codeCpt = codeCpt;
	}

	public String getLibelleCpt() {
		return libelleCpt;
	}

	public void setLibelleCpt(String libelleCpt) {
		this.libelleCpt = libelleCpt;
	}

	public Compte getSelectedCpte() {
		return selectedCpte;
	}

	public void setSelectedCpte(Compte selectedCpte) {
		this.selectedCpte = selectedCpte;
	}

	public List<Compte> getListeCpte() {
		return listeCpte;
	}

	public void setListeCpte(List<Compte> listeCpte) {
		this.listeCpte = listeCpte;
	}

	@PostConstruct
	public void initilaze() {
		this.model = new JournalModel();

		chargerJournal();
		chargementSession();
		this.disableMsg = true;
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
	public void chargerCompte() {
		this.listeCpte = (new CompteModel()).getListCompte(this.factory, this.rechLblCpt, this.rechCodCpt);
	}
	public void searchCpt() {
		if (this.codeCpt != null && !this.codeCpt.equals("")) {

			this.selectedCpte = (new CompteModel()).getCompteByCode(this.factory, this.codeCpt);
			if (this.selectedCpte != null) {
				getCptValues();
			} else {
				HelperC.afficherAttention("Attention", "Ce compte n'existe pas !");
				
			}
		}
	}
	private void getCptValues() {
	
		this.codeCpt = this.selectedCpte.getCompteCod();
		this.libelleCpt = this.selectedCpte.getLibelle();
		
	}
	public void getSelecedCptValue() {
		if (this.selectedCpte != null) {

			getCptValues();
			PrimeFaces.current().executeScript("PF('dlgCpt').hide();");
		}
	}
	public void searchJournal() {
		if (this.code != null && !this.code.equals("")) {

			this.disableMsg = true;
			this.selectedJournal = this.model.getJouralByCode(this.factory, this.code);
			if (this.selectedJournal != null) {
				getJournalValues();
			}
		}
	}

	private void getJournalValues() {
		this.idJrn = this.selectedJournal.getId();
		this.code = this.selectedJournal.getCodeJrnl();
		this.designation = this.selectedJournal.getLibelle();
		this.disableMsg = false;
		codeCpt=selectedJournal.getContrePartie();
		searchCpt();
	}

	public void getSelectedValues() {
		this.disableMsg = true;
		if (this.selectedJournal != null) {
			getJournalValues();
		}
	}

	private void chargerJournal() {
		this.listJrnl = this.model.getListJouranl(this.factory);
	}

	public void save() {
		if (this.code != null && !this.code.equals("")) {

			if (this.selectedJournal == null)
				this.selectedJournal = new Journal();
			this.selectedJournal.setId(this.idJrn);
			this.selectedJournal.setCodeJrnl(this.code);
			this.selectedJournal.setLibelle(this.designation);
			this.selectedJournal.setContrePartie(codeCpt);
			if (this.selectedJournal.getId() == 0) {
				this.model.saveJournal(this.factory, this.selectedJournal);
			} else {
				this.model.updateJournal(this.factory, this.selectedJournal);
			}
			initializeControls();
			chargerJournal();
		} else {

			HelperC.afficherAttention("Attention", "Il faut préciser la référence !");
		}
	}

	public void delete() {
		if (this.selectedJournal != null && this.selectedJournal.getId() > 0) {

			this.model.deleteJournal(this.factory, this.selectedJournal);
			initializeControls();
			chargerJournal();
		}
	}

	public void initializeControls() {
		this.idJrn = 0;
		this.code = "";
		this.designation = "";
		this.disableMsg = true;
		this.selectedJournal = null;
		this.codeCpt = "";
		this.libelleCpt = "";
		selectedCpte=null;
	}

	@SuppressWarnings("resource")
	public void getJournal() {
		File f = new File("D:\\VIRAGO\\COMPTAVIRAGO\\journal.xlsx");
		try {
			FileInputStream file = new FileInputStream(f);
			XSSFWorkbook xSSFWorkbook = new XSSFWorkbook(file);
			Sheet s = xSSFWorkbook.getSheetAt(0);
			int rownumber = s.getLastRowNum() + 1;

			System.out.println("Nombre - " + rownumber);
			Journal jnl = null;
			String code = null, libelle = null;
			int tmp = 0, i = 0;

			Iterator<Row> rowIterator = s.iterator();
			while (rowIterator.hasNext()) {

				Row row = s.getRow(i);
				if (row != null) {
					Cell cell1 = row.getCell(0);
					Cell cell2 = row.getCell(1);

					code = cell1.getStringCellValue();
					libelle = cell2.getStringCellValue();

					jnl = this.model.getJouralByCode(this.factory, code);
					if (jnl == null) {

						jnl = new Journal();
						jnl.setCodeJrnl(code);
						jnl.setLibelle(libelle);
					}

					System.out.println(String.valueOf(code) + " - " + libelle);
					i++;
				}
			}
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
