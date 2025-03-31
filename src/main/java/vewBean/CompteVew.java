package vewBean;

import entite.Compte;
import entite.Exercice;
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
import model.EcritureModel;
import model.ExerciceModel;
import model.UserModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.SessionFactory;
import persistances.DBConfiguration;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class CompteVew implements Serializable {
	private static final long serialVersionUID = -7403556883989160120L;
	SessionFactory factory = DBConfiguration.getSessionFactory();

	private Compte selectedCompte;

	private List<Compte> listCpt;

	private boolean disableMsg, detail;
	private String code;
	private String designation;
	private String typeCpte;
	private String urlFile;
	int idCpt = 0;
	CompteModel model;
	Exercice selecetdExercice;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;

	public Compte getSelectedCompte() {
		return this.selectedCompte;
	}

	public void setSelectedCompte(Compte selectedCompte) {
		this.selectedCompte = selectedCompte;
	}

	public List<Compte> getListCpt() {
		return this.listCpt;
	}

	public void setListCpt(List<Compte> listCpt) {
		this.listCpt = listCpt;
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

	public String getTypeCpte() {
		return this.typeCpte;
	}

	public void setTypeCpte(String typeCpte) {
		this.typeCpte = typeCpte;
	}

	public String getUrlFile() {
		return urlFile;
	}

	public void setUrlFile(String urlFile) {
		this.urlFile = urlFile;
	}

	public boolean isDetail() {
		return detail;
	}

	public void setDetail(boolean detail) {
		this.detail = detail;
	}

	@PostConstruct
	public void initialize() {
		this.model = new CompteModel();
		chargementSession();
		chargerCpte();
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

	private void chargerCpte() {
		this.listCpt = this.model.getListCompte(this.factory, "", "");
	}

	public void searchCompte() {
		this.disableMsg = true;
		if (this.code != null && !this.code.equals("")) {

			this.selectedCompte = this.model.getCompteByCode(this.factory, this.code);
			if (this.selectedCompte != null) {
				getCptValues();
			}
		}
	}

	private void getCptValues() {
		this.idCpt = this.selectedCompte.getId();
		this.code = this.selectedCompte.getCompteCod();
		this.designation = this.selectedCompte.getLibelle();
		this.typeCpte = this.selectedCompte.getNature();
		this.disableMsg = false;
		detail=selectedCompte.isCompteDetail();
	}

	public void getSelectedValues() {
		this.disableMsg = true;
		if (this.selectedCompte != null) {
			getCptValues();
		}
	}

	public void save() {
		if (this.code != null && !this.code.equals("")) {

			if (this.selectedCompte == null)
				this.selectedCompte = new Compte();
			this.selectedCompte.setCompteCod(this.code);
			this.selectedCompte.setId(this.idCpt);
			this.selectedCompte.setLibelle(this.designation);
			this.selectedCompte.setNature(this.typeCpte);
			this.selectedCompte.setCompteDetail(detail);
			if (this.selectedCompte.getId() == 0) {
				this.model.saveCompte(this.factory, this.selectedCompte);
			} else {
				this.model.updateCompte(this.factory, this.selectedCompte);
			}
			initializeControl();
		} else {

			HelperC.afficherAttention("Attention", "Il faut préciser la référence !");
		}
	}

	public void delete() {
		if (this.selectedCompte != null && this.selectedCompte.getId() > 0) {
			boolean used = new EcritureModel().isCompteUsed(factory, selectedCompte.getCompteCod());
			if (!used) {
				this.model.deleteCompte(this.factory, this.selectedCompte);
				initializeControl();
			}
			else
				HelperC.afficherAttention("Attention", "Impossible de supprimer un compte déjà utilisé !");
		}
	}

	public void initializeControl() {
		this.idCpt = 0;
		this.code = "";
		this.designation = "";
		this.typeCpte = "";
		this.disableMsg = true;
		this.selectedCompte = null;
		detail=false;
		chargerCpte();
	}

	public void getAccountPlan() {
		if (urlFile == null || urlFile.equals("")) {

			HelperC.afficherAttention("Attention", "Il faut préciser le fichier excel !");
			return;

		}
		File f = new File(urlFile);
		try {

			FileInputStream file = new FileInputStream(f);
			XSSFWorkbook xSSFWorkbook = new XSSFWorkbook(file);
			Sheet s = xSSFWorkbook.getSheetAt(0);
			int rownumber = s.getLastRowNum() + 1;

			Compte cpt = null;
			String code = null, libelle = null;
			int tmp = 0, i = 0;

			Iterator<Row> rowIterator = s.iterator();

			while (rowIterator.hasNext()) {

				Row row = s.getRow(i);
				if (row != null) {
					Cell cellCode = row.getCell(0);
					Cell cellNom = row.getCell(1);

					int a=(int)cellCode.getNumericCellValue();

					code =String.valueOf(a);
					libelle = cellNom.getStringCellValue();

					cpt = this.model.getCompteByCode(this.factory, code);
					if (cpt == null) {

						cpt = new Compte();
						cpt.setCompteCod(code);
						cpt.setLibelle(libelle);
						model.saveCompte(factory, cpt);
					}

					i++;
				}
			}
			chargerCpte();

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
}
