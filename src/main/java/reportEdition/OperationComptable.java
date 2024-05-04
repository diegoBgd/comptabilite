package reportEdition;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEvent;
import com.itextpdf.text.pdf.PdfWriter;

import entite.Compte;
import entite.Ecriture;
import entite.Exercice;
import entite.Journal;
import entite.SoldeCompte;
import entite.User;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.CompteModel;
import model.EcritureModel;
import model.ExerciceModel;
import model.JournalModel;
import model.UserModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.bouncycastle.jce.provider.JDKDSASigner.ecDSA;
import org.hibernate.SessionFactory;
import org.primefaces.PrimeFaces;
import persistances.DBConfiguration;

import utils.HelperC;
import utils.HelperItext;
import utils.ItextFooterHelper;

@ManagedBean
@ViewScoped
public class OperationComptable implements Serializable {
	private static final long serialVersionUID = -8445714579339341735L;
	private List<Ecriture> listeEcriture;
	private Compte selectedCpte;
	private List<Compte> listeCpte;
	private Journal selectedJrnl;
	private List<Journal> listJrnl;
	private Date dateDebut;
	private Date dateFin;
	private boolean disableSave;
	private boolean pagination;
	private String printDate;
	private String rechPiece;
	private String rechLblCpt;
	private String rechLblJrnl;
	SessionFactory factory = DBConfiguration.getSessionFactory();
	private String rechCodCpt;
	private String rechJrnal;
	private String rechDateDeb;
	private String rechDateFin;
	private String rechCpte;
	private String printSoldeDb;
	private String printSoldeCd;
	private String codeJrnl;
	private String codeCpt;
	private String libelleJrnl;
	private String libelleCpt;
	private String printTotDeb;
	private String printTotCrd;
	private String printSolde;
	private String infoMsg;
	//private List<Object[]> listSolde;
	private List<SoldeCompte>listSolde;
	EcritureModel model;
	Exercice selecetdExercice;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;
	

	public List<Ecriture> getListeEcriture() {
		return this.listeEcriture;
	}

	public void setListeEcriture(List<Ecriture> listeEcriture) {
		this.listeEcriture = listeEcriture;
	}

	public Compte getSelectedCpte() {
		return this.selectedCpte;
	}

	public void setSelectedCpte(Compte selectedCpte) {
		this.selectedCpte = selectedCpte;
	}

	public List<Compte> getListeCpte() {
		return this.listeCpte;
	}

	public void setListeCpte(List<Compte> listeCpte) {
		this.listeCpte = listeCpte;
	}

	public Journal getSelectedJrnl() {
		return this.selectedJrnl;
	}

	public void setSelectedJrnl(Journal selectedJrnl) {
		this.selectedJrnl = selectedJrnl;
	}

	public List<Journal> getListJrnl() {
		return this.listJrnl;
	}

	public void setListJrnl(List<Journal> listJrnl) {
		this.listJrnl = listJrnl;
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

	public String getPrintDate() {
		return this.printDate;
	}

	public void setPrintDate(String printDate) {
		this.printDate = printDate;
	}

	public String getRechPiece() {
		return this.rechPiece;
	}

	public void setRechPiece(String rechPiece) {
		this.rechPiece = rechPiece;
	}

	public String getRechLblCpt() {
		return this.rechLblCpt;
	}

	public void setRechLblCpt(String rechLblCpt) {
		this.rechLblCpt = rechLblCpt;
	}

	public String getRechLblJrnl() {
		return this.rechLblJrnl;
	}

	public void setRechLblJrnl(String rechLblJrnl) {
		this.rechLblJrnl = rechLblJrnl;
	}

	public String getRechJrnal() {
		return this.rechJrnal;
	}

	public void setRechJrnal(String rechJrnal) {
		this.rechJrnal = rechJrnal;
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

	public String getRechCpte() {
		return this.rechCpte;
	}

	public void setRechCpte(String rechCpte) {
		this.rechCpte = rechCpte;
	}

	public String getCodeJrnl() {
		return this.codeJrnl;
	}

	public void setCodeJrnl(String codeJrnl) {
		this.codeJrnl = codeJrnl;
	}

	public String getCodeCpt() {
		return this.codeCpt;
	}

	public void setCodeCpt(String codeCpt) {
		this.codeCpt = codeCpt;
	}

	public String getLibelleJrnl() {
		return this.libelleJrnl;
	}

	public void setLibelleJrnl(String libelleJrnl) {
		this.libelleJrnl = libelleJrnl;
	}

	public String getLibelleCpt() {
		return this.libelleCpt;
	}

	public void setLibelleCpt(String libelleCpt) {
		this.libelleCpt = libelleCpt;
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

	public String getInfoMsg() {
		return this.infoMsg;
	}

	public void setInfoMsg(String infoMsg) {
		this.infoMsg = infoMsg;
	}

	public boolean isDisableSave() {
		return this.disableSave;
	}

	public void setDisableSave(boolean disableSave) {
		this.disableSave = disableSave;
	}

	public String getRechCodCpt() {
		return this.rechCodCpt;
	}

	public void setRechCodCpt(String rechCodCpt) {
		this.rechCodCpt = rechCodCpt;
	}

	public boolean isPagination() {
		return this.pagination;
	}

	public void setPagination(boolean pagination) {
		this.pagination = pagination;
	}

	public String getPrintSoldeDb() {
		return this.printSoldeDb;
	}

	public void setPrintSoldeDb(String printSoldeDb) {
		this.printSoldeDb = printSoldeDb;
	}

	public String getPrintSoldeCd() {
		return this.printSoldeCd;
	}

	public void setPrintSoldeCd(String printSoldeCd) {
		this.printSoldeCd = printSoldeCd;
	}
	
	public List<SoldeCompte> getListSolde() {
		return listSolde;
	}

	public void setListSolde(List<SoldeCompte> listSolde) {
		this.listSolde = listSolde;
	}

	@PostConstruct
	public void initialize() {
		this.model = new EcritureModel();
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

	public void chargerCompte() {
		this.listeCpte = (new CompteModel()).getListCompte(this.factory, this.rechLblCpt, this.rechCodCpt);
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
				this.disableSave = true;
			}
		}
	}

	public void searchCpt() {
		if (this.codeCpt != null && !this.codeCpt.equals("")) {

			this.selectedCpte = (new CompteModel()).getCompteByCode(this.factory, this.codeCpt);
			if (this.selectedCpte != null) {
				getCptValues();
				
			} else {
				HelperC.afficherAttention("Attention", "Ce compte n'existe pas !");
				this.disableSave = true;
			}
		}
	}

	public void getSelecedCptValue() {
		if (this.selectedCpte != null) {

			getCptValues();
			PrimeFaces.current().executeScript("PF('dlgCpt').hide();");
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
		this.rechJrnal = this.codeJrnl;
	}

	private void getCptValues() {
		this.codeCpt = this.selectedCpte.getCompteCod();
		this.libelleCpt = this.selectedCpte.getLibelle();
		this.rechCpte = this.codeCpt;
	}

	
	public void chargerEcriture() {
		this.pagination = false;
		listeEcriture = this.model.getListEcriture(this.factory, this.selecetdExercice.getId(), this.rechJrnal,
				this.rechCpte, this.rechPiece, this.dateDebut, this.dateFin);
		
		if (this.listeEcriture != null && this.listeEcriture.size() > 0) {

			this.pagination = true;
			calculTotaux();
		}
	}
	public void chargerSoldeJournaux() {
	SoldeCompte sc=null; 
	listSolde=new ArrayList<SoldeCompte>();
		List<Object[]> listSoldeJrnl=model.getListSoldeJournal(factory,selecetdExercice.getId(), codeJrnl,dateDebut,dateFin);
		double deb=0,crd=0,sold=0;
		Journal jnl;
		for (Object[] ob :listSoldeJrnl) 
		{
			sc=new SoldeCompte();
			jnl=new JournalModel().getJouralByCode(factory, ob[0].toString());
			sc.setCode(ob[0].toString()); 
			if(jnl!=null)
			sc.setLibelle(jnl.getLibelle());
			deb=Double.parseDouble(ob[1].toString());
			crd=Double.parseDouble(ob[2].toString());
			
			sold=deb-crd;
			
			if(sold>0)
				sc.setSoldeDbt(sold);
			else
				sc.setSoldeCdt(-sold);
			
			sc.setDebit(deb);
			sc.setCredit(crd);
			
			listSolde.add(sc);
			
			//sc.set( ob[0].toString()); 
		}
	}
	private void calculTotaux() {
		double totDeb = 0.0D, totCrd = 0.0D, solde = 0.0D;
		printTotCrd="";printTotDeb="";printSoldeCd="";printSoldeDb="";
		for (Ecriture ec : this.listeEcriture) {
			totDeb += ec.getDebit();
			totCrd += ec.getCredit();
		}

		solde = Math.abs(totDeb - totCrd);
		this.printTotCrd = HelperC.decimalNumber(totCrd, 0, true);
		this.printTotDeb = HelperC.decimalNumber(totDeb, 0, true);
		if (solde > 0.0D)
			this.printSoldeDb = HelperC.decimalNumber(solde, 0, true);
		if (solde < 0.0D)
			this.printSoldeCd = HelperC.decimalNumber(Math.abs(solde), 0, true);
	}

	public void changeDateDebut() {
		this.infoMsg = "";
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
		this.infoMsg = "";
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
	
	public void chargerSoldeCompte() {
		List<Object[]> soldes;
		if(selectedCpte!=null)
		{
		 soldes = model.getSoldeCompte(factory, selecetdExercice.getId(), "", selectedCpte.getCompteCod(), dateDebut, dateFin);
		listSolde=new ArrayList<SoldeCompte>();
		SoldeCompte solde=null;
		double cd=0,db=0;
		if(listeCpte==null || listeCpte.size()==0)
		{
			rechCodCpt=codeCpt;
		chargerCompte();
		}
		for (Compte cpt : listeCpte) {
			solde=new SoldeCompte();
			solde.setCode(cpt.getCompteCod());
			solde.setLibelle(cpt.getLibelle());
			
			db=getTotMvt(cpt.getCompteCod(), soldes, "D");
			cd=getTotMvt(cpt.getCompteCod(), soldes, "C");
			
			solde.setDebit(db);
			solde.setCredit(cd);
			
			if(db>cd)
				solde.setSoldeDbt(db-cd);
			else
				solde.setSoldeCdt(cd-db);
			
			if(db>0||cd>0)
				listSolde.add(solde);
			
			
			cd=0;db=0;
		}
		}
	}
	
	
	public void printJournalPdf() {
		try {
			Image image = null;
			Document doc = new Document(PageSize.A4);
			ByteArrayOutputStream docMem = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(doc, docMem);
			doc.addAuthor("Gatech");
			doc.addProducer();
			doc.addCreationDate();
			doc.addTitle("Journal");
			doc.open();
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
					.getContext();
			image = Image
					.getInstance(String.valueOf(servletContext.getRealPath("/resources")) + "\\Images\\" + "logo.png");
			image.scaleAbsoluteHeight(90.0F);
			image.scaleAbsoluteWidth(90.0F);
			doc.add((Element) image);
			writer.setPageEvent((PdfPageEvent) new ItextFooterHelper(
					new Phrase("Produit Gatech                          "+HelperC.convertDateHeureMin(Calendar.getInstance().getTime()), new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 0))));

			doc.add((Element) pageHeader("HISTORIQUE DU JOURNAL : "+codeJrnl));
			doc.add((Element) getTableJournal());

			doc.close();

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
			res.setHeader("Cache-Control", "Max-age=100");
			res.setContentType("application/pdf");
			res.setHeader("content-disposition", (new StringBuilder("online;filename=Journal.pdf")).toString());
			ServletOutputStream out = res.getOutputStream();
			res.setContentLength(docMem.size());
			docMem.writeTo((OutputStream) out);
			out.flush();
			out.close();
			context.responseComplete();
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}
	
	public void printComptePdf() {
		try {
			Image image = null;
			Document doc = new Document(PageSize.A4);
			ByteArrayOutputStream docMem = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(doc, docMem);
			doc.addAuthor("Gatech");
			doc.addProducer();
			doc.addCreationDate();
			doc.addTitle("Compte");
			doc.open();
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
					.getContext();
			image = Image
					.getInstance(String.valueOf(servletContext.getRealPath("/resources")) + "\\Images\\" + "logo.png");
			image.scaleAbsoluteHeight(90.0F);
			image.scaleAbsoluteWidth(90.0F);
			doc.add((Element) image);
			writer.setPageEvent((PdfPageEvent) new ItextFooterHelper(
					new Phrase("Produit Gatech                          "+HelperC.convertDateHeureMin(Calendar.getInstance().getTime()), new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 0))));

			doc.add((Element) pageHeader("HISTORIQUE DU COMPTE : "+codeCpt));
			doc.add((Element) getTableJournal());

			doc.close();

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
			res.setHeader("Cache-Control", "Max-age=100");
			res.setContentType("application/pdf");
			res.setHeader("content-disposition", (new StringBuilder("online;filename=compte.pdf")).toString());
			ServletOutputStream out = res.getOutputStream();
			res.setContentLength(docMem.size());
			docMem.writeTo((OutputStream) out);
			out.flush();
			out.close();
			context.responseComplete();
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}
	public void printSoldeComptePdf() {
		try {
			Image image = null;
			Document doc = new Document(PageSize.A4);
			ByteArrayOutputStream docMem = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(doc, docMem);
			doc.addAuthor("Gatech");
			doc.addProducer();
			doc.addCreationDate();
			doc.addTitle("sodeCompte");
			doc.open();
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
					.getContext();
			image = Image
					.getInstance(String.valueOf(servletContext.getRealPath("/resources")) + "\\Images\\" + "logo.png");
			image.scaleAbsoluteHeight(90.0F);
			image.scaleAbsoluteWidth(90.0F);
			doc.add((Element) image);
			writer.setPageEvent((PdfPageEvent) new ItextFooterHelper(
					new Phrase("Produit Gatech                "+HelperC.convertDateHeureMin(Calendar.getInstance().getTime()), new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 0))));

			doc.add((Element) pageHeader("SOLDE DES COMPTES : "+codeCpt));
			doc.add((Element) getTableSoldeCompte());

			doc.close();

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
			res.setHeader("Cache-Control", "Max-age=100");
			res.setContentType("application/pdf");
			res.setHeader("content-disposition", (new StringBuilder("online;filename=sodeCompte.pdf")).toString());
			ServletOutputStream out = res.getOutputStream();
			res.setContentLength(docMem.size());
			docMem.writeTo((OutputStream) out);
			out.flush();
			out.close();
			context.responseComplete();
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}
	
	public void printSoldeJournalPdf() {
		try {
			Image image = null;
			Document doc = new Document(PageSize.A4);
			ByteArrayOutputStream docMem = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(doc, docMem);
			doc.addAuthor("Gatech");
			doc.addProducer();
			doc.addCreationDate();
			doc.addTitle("sodeJournal");
			doc.open();
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
					.getContext();
			image = Image
					.getInstance(String.valueOf(servletContext.getRealPath("/resources")) + "\\Images\\" + "logo.png");
			image.scaleAbsoluteHeight(90.0F);
			image.scaleAbsoluteWidth(90.0F);
			doc.add((Element) image);
			writer.setPageEvent((PdfPageEvent) new ItextFooterHelper(
					new Phrase("Produit Gatech                "+HelperC.convertDateHeureMin(Calendar.getInstance().getTime()), new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 0))));

			doc.add((Element) pageHeader("SOLDE DES JOURNAUX  "));
			doc.add((Element) getTableSoldeJournal());

			doc.close();

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
			res.setHeader("Cache-Control", "Max-age=100");
			res.setContentType("application/pdf");
			res.setHeader("content-disposition", (new StringBuilder("online;filename=sodeJournal.pdf")).toString());
			ServletOutputStream out = res.getOutputStream();
			res.setContentLength(docMem.size());
			docMem.writeTo((OutputStream) out);
			out.flush();
			out.close();
			context.responseComplete();
		} catch (Exception e) {

			System.out.println(e.getMessage());
		}
	}
	
	private PdfPTable pageHeader(String titre) throws DocumentException, IOException {
		PdfPTable table = null;
		table = new PdfPTable(4);
		PdfPCell cell = new PdfPCell();
		table.setWidthPercentage(100.0F);
		cell.setBorder(0);
		int[] largeurCollones = { 25, 25, 25, 25 };
		table.setWidths(largeurCollones);

		String period="";
		
		if(dateDebut!=null)
			period+=" Date début :"+HelperC.convertDate(dateDebut);
		if(dateFin!=null)
			period+=" Date fin :"+HelperC.convertDate(dateFin);
		
		cell = HelperItext.getPdfCell(period,
				FontFactory.getFont("Times-Roman", 8.0F, 0, BaseColor.BLACK), 1, 3, 0, 4, BaseColor.WHITE,
				BaseColor.WHITE, 1);
		table.addCell(cell);

		cell = HelperItext.getPdfCell("", FontFactory.getFont("Times-Roman", 12.0F, 0, BaseColor.BLACK), 1, 1, 0, 4,
				BaseColor.WHITE, BaseColor.WHITE, 1);
		table.addCell(cell);

		cell = HelperItext.getPdfCell("", FontFactory.getFont("Times-Roman", 12.0F, 1, BaseColor.BLACK), 1, 1, 0, 0,
				BaseColor.WHITE, BaseColor.WHITE, 1);
		table.addCell(cell);

		cell = HelperItext.getPdfCell(titre, FontFactory.getFont("Times-Roman", 12.0F, 1, BaseColor.BLACK), 1, 1, 0, 2,
				BaseColor.WHITE, BaseColor.WHITE, 1);
		table.addCell(cell);

		cell = HelperItext.getPdfCell("", FontFactory.getFont("Times-Roman", 12.0F, 1, BaseColor.BLACK), 1, 1, 0, 0,
				BaseColor.WHITE, BaseColor.WHITE, 1);
		table.addCell(cell);

		cell = HelperItext.getPdfCell("\n", FontFactory.getFont("Times-Roman", 12.0F, 1, BaseColor.BLACK), 1, 1, 0, 4,
				BaseColor.WHITE, BaseColor.WHITE, 1);
		table.addCell(cell);

		return table;
	}

	private PdfPTable getTableJournal() throws DocumentException {
		PdfPTable tabInfo = new PdfPTable(8);
		int[] widthsInfo = { 3, 7, 6, 6, 10, 25, 8, 8 };
		tabInfo.setWidthPercentage(105.0F);
		tabInfo.setWidths(widthsInfo);
		Paragraph p = null;
		PdfPCell cell = new PdfPCell();

		double totDeb = 0.0D, totCrd = 0.0D, soldDb = 0.0D,soldCd = 0.0D;

		if (this.listeEcriture.size() > 0) {

			p = new Paragraph();
			p.add((Element) new Chunk(" ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Date", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Journal", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Compte ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Piece", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Libelle ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Debit", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Credit", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 15, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			tabInfo.setHeaderRows(1);

			int j = 0;

			for (Ecriture ecr : this.listeEcriture) {
				j++;
				p = new Paragraph();
				p.add((Element) new Chunk("" + j, FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(ecr.getPrintDate(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(ecr.getJrnl(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(ecr.getCpte(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(ecr.getPieceCpb(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 2, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(ecr.getLibelle(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(ecr.getPrintDebit(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(2);
				cell = HelperItext.getCellule((Element) p, 1, 0, 14, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(ecr.getPrintCredit(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(2);
				cell = HelperItext.getCellule((Element) p, 1, 0, 14, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				totCrd += ecr.getCredit();
				totDeb += ecr.getDebit();
				
			}

			if (totDeb > totCrd)
				soldDb = totDeb - totCrd;
			if (totCrd > totDeb)
				soldCd = totCrd - totDeb;
			if (totCrd == totDeb) {
				soldCd = 0;
				soldDb = 0;
			}
			p = new Paragraph();
			p.add((Element) new Chunk("Total ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(0);
			cell = HelperItext.getCellule((Element) p, 1, 0, 6, 6, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk(HelperC.decimalNumber(totDeb, 0, true), FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk(HelperC.decimalNumber(totCrd, 0, true), FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule((Element) p, 1, 2, 14, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);
			
			p = new Paragraph();
			p.add((Element) new Chunk("Solde ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(0);
			cell = HelperItext.getCellule((Element) p, 1, 0, 6, 6, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk(HelperC.decimalNumber(soldDb, 0, true), FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk(HelperC.decimalNumber(soldCd, 0, true), FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule((Element) p, 1, 2, 14, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);
		}

		return tabInfo;
	}
	
	private PdfPTable getTableSoldeCompte() throws DocumentException {
		PdfPTable tabInfo = new PdfPTable(6);
		int[] widthsInfo = { 10, 25,10, 10,10,10 };
		tabInfo.setWidthPercentage(105.0F);
		tabInfo.setWidths(widthsInfo);
		Paragraph p = null;
		PdfPCell cell = new PdfPCell();
	
		
		if (listSolde .size() > 0) {
				
			p = new Paragraph();
			p.add((Element) new Chunk("Compte ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

		
			p = new Paragraph();
			p.add((Element) new Chunk("Libelle ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Debit", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Debit", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Solde deb", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Solde cred ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 15, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			tabInfo.setHeaderRows(1);
			
		

			for (SoldeCompte sld :listSolde) {
				
				
				
				
				p = new Paragraph();
				p.add((Element) new Chunk(sld.getCode() , FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(sld.getLibelle(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);
				

				p = new Paragraph();
				p.add((Element) new Chunk(HelperC.decimalNumber(sld.getDebit(), 0, true), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(2);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(HelperC.decimalNumber(sld.getCredit(), 0, true), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(2);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(HelperC.decimalNumber(sld.getSoldeDbt(), 0, true), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(2);
				cell = HelperItext.getCellule((Element) p, 1, 0, 14, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);
				
				p = new Paragraph();
				p.add((Element) new Chunk(HelperC.decimalNumber(sld.getSoldeCdt(), 0, true), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(2);
				cell = HelperItext.getCellule((Element) p, 1, 0, 14, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);
				
				
			}

			
		}

		return tabInfo;
	}
	
	private PdfPTable getTableSoldeJournal() throws DocumentException {
		PdfPTable tabInfo = new PdfPTable(6);
		int[] widthsInfo = { 10, 25,10, 10,10,10 };
		tabInfo.setWidthPercentage(105.0F);
		tabInfo.setWidths(widthsInfo);
		Paragraph p = null;
		PdfPCell cell = new PdfPCell();
	
		
		if (listSolde .size() > 0) {
				
			p = new Paragraph();
			p.add((Element) new Chunk("Code ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

		
			p = new Paragraph();
			p.add((Element) new Chunk("Libelle ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Débit", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Crédit", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Solde déb", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Solde cred ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 15, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			tabInfo.setHeaderRows(1);
			
		

			for (SoldeCompte sld :listSolde) {
				
				
				p = new Paragraph();
				p.add((Element) new Chunk(sld.getCode() , FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(sld.getLibelle(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);
				

				p = new Paragraph();
				p.add((Element) new Chunk(HelperC.decimalNumber(sld.getDebit(), 0, true), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(2);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(HelperC.decimalNumber(sld.getCredit(), 0, true), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(2);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(HelperC.decimalNumber(sld.getSoldeDbt(), 0, true), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(2);
				cell = HelperItext.getCellule((Element) p, 1, 0, 14, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);
				
				p = new Paragraph();
				p.add((Element) new Chunk(HelperC.decimalNumber(sld.getSoldeCdt(), 0, true), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(2);
				cell = HelperItext.getCellule((Element) p, 1, 0, 14, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);
				
				
			}

			
		}

		return tabInfo;
	}
	
	
	
	

	@SuppressWarnings("deprecation")
	public void printJournalExcel() {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Journal");
		HSSFRow row = null;
		HSSFCellStyle cellStyle = null;
		HSSFCellStyle style = null, style1 = null;

		cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor((short) 29);
		cellStyle.setFillPattern((short) 1);

		style = wb.createCellStyle();

		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

		double solde = 0.0D, totCrd = 0.0D, totDeb = 0.0D;
		boolean bl = false;

		if (this.listeEcriture.size() > 0) {

			row = sheet.createRow(0);
			cellStyle = wb.createCellStyle();
			row.createCell(0).setCellValue("");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(0).setCellStyle(cellStyle);

			row.createCell(1).setCellValue("Date");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(1).setCellStyle(cellStyle);

			row.createCell(2).setCellValue("Compte");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(2).setCellStyle(cellStyle);

			row.createCell(3).setCellValue("Journal");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(3).setCellStyle(cellStyle);

			row.createCell(4).setCellValue("Piece");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(4).setCellStyle(cellStyle);

			row.createCell(5).setCellValue("Libellé");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(5).setCellStyle(cellStyle);

			row.createCell(6).setCellValue("Débit");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(6).setCellStyle(cellStyle);

			row.createCell(7).setCellValue("Crédit");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(7).setCellStyle(cellStyle);

			int i = 0;
			for (Ecriture ecr : this.listeEcriture) {

				i++;

				row = sheet.createRow(i);
				HSSFCell cellE = null;

				row.createCell(0).setCellValue(i);

				row.createCell(1).setCellValue(ecr.getPrintDate());

				row.createCell(2).setCellValue(ecr.getCpte());

				row.createCell(3).setCellValue(ecr.getJrnl());

				row.createCell(4).setCellValue(ecr.getPieceCpb());

				row.createCell(5).setCellValue(ecr.getLibelle());

				row.createCell(6).setCellValue(ecr.getDebit());
				cellE = row.getCell(6);
				cellE.setCellType(0);
				row.getCell(6).setCellStyle(style);

				row.createCell(7).setCellValue(ecr.getCredit());
				cellE = row.getCell(7);
				cellE.setCellType(0);
				row.getCell(7).setCellStyle(style);

				totCrd += ecr.getCredit();
				totDeb += ecr.getDebit();
			}

			HSSFCell cellT = null;
			style1 = wb.createCellStyle();
			style1.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
			style1.setFillForegroundColor((short) 22);
			style1.setFillPattern((short) 1);

			row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue("TOTAL :");
			sheet.addMergedRegion(new CellRangeAddress(i + 1, i + 1, 0, 5));
			row.getCell(0).setCellStyle(style1);

			row.createCell(6).setCellValue(totDeb);
			cellT = row.getCell(6);
			cellT.setCellType(0);
			row.getCell(6).setCellStyle(style1);

			row.createCell(7).setCellValue(totCrd);
			cellT = row.getCell(7);
			cellT.setCellType(0);
			row.getCell(7).setCellStyle(style1);
		}
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
		res.setContentType("application/vnd.ms-excel");
		res.setHeader("Content-disposition", "attachment;filename=journal.xls");

		try {
			ServletOutputStream out = res.getOutputStream();
			wb.write((OutputStream) out);
			out.flush();
			out.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void printSoldeCompteExcel() {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("solde");
		HSSFRow row = null;
		HSSFCellStyle cellStyle = null;
		HSSFCellStyle style = null, style1 = null;

		cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor((short) 29);
		cellStyle.setFillPattern((short) 1);

		style = wb.createCellStyle();

		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));


		if (listSolde.size() > 0) {

			row = sheet.createRow(0);
			cellStyle = wb.createCellStyle();
			
		
			row.createCell(0).setCellValue("Compte");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(0).setCellStyle(cellStyle);

		
			row.createCell(1).setCellValue("Libellé");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(1).setCellStyle(cellStyle);

			row.createCell(2).setCellValue("Débit");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(2).setCellStyle(cellStyle);

			row.createCell(3).setCellValue("Crédit");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(3).setCellStyle(cellStyle);
			
			row.createCell(4).setCellValue("Solde débiteur");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(4).setCellStyle(cellStyle);
			
			row.createCell(5).setCellValue("Solde créditeur");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(5).setCellStyle(cellStyle);

			int i = 1;
			for (SoldeCompte sc :listSolde) {

			

				row = sheet.createRow(i);
				HSSFCell cellE = null;

				row.createCell(0).setCellValue(sc.getCode());

				row.createCell(1).setCellValue(sc.getLibelle());

			
				row.createCell(2).setCellValue(sc.getDebit());
				cellE = row.getCell(2);
				cellE.setCellType(0);
				row.getCell(2).setCellStyle(style);

				row.createCell(3).setCellValue(sc.getCredit());
				cellE = row.getCell(3);
				cellE.setCellType(0);
				row.getCell(3).setCellStyle(style);
				
				row.createCell(4).setCellValue(sc.getSoldeDbt());
				cellE = row.getCell(4);
				cellE.setCellType(0);
				row.getCell(4).setCellStyle(style);

				row.createCell(5).setCellValue(sc.getSoldeCdt());
				cellE = row.getCell(5);
				cellE.setCellType(0);
				row.getCell(5).setCellStyle(style);
				i++;
				
			}

			
		}
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
		res.setContentType("application/vnd.ms-excel");
		res.setHeader("Content-disposition", "attachment;filename=soldeCompte.xls");

		try {
			ServletOutputStream out = res.getOutputStream();
			wb.write((OutputStream) out);
			out.flush();
			out.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void printSoldeJournalExcel() {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("soldeJrnl");
		HSSFRow row = null;
		HSSFCellStyle cellStyle = null;
		HSSFCellStyle style = null, style1 = null;

		cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor((short) 29);
		cellStyle.setFillPattern((short) 1);

		style = wb.createCellStyle();

		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));


		if (listSolde.size() > 0) {

			row = sheet.createRow(0);
			cellStyle = wb.createCellStyle();
			
		
			row.createCell(0).setCellValue("Code");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(0).setCellStyle(cellStyle);

		
			row.createCell(1).setCellValue("Libellé");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(1).setCellStyle(cellStyle);

			row.createCell(2).setCellValue("Débit");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(2).setCellStyle(cellStyle);

			row.createCell(3).setCellValue("Crédit");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(3).setCellStyle(cellStyle);
			
			row.createCell(4).setCellValue("Solde débiteur");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(4).setCellStyle(cellStyle);
			
			row.createCell(5).setCellValue("Solde créditeur");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(5).setCellStyle(cellStyle);

			int i = 1;
			for (SoldeCompte sc :listSolde) {

			

				row = sheet.createRow(i);
				HSSFCell cellE = null;

				row.createCell(0).setCellValue(sc.getCode());

				row.createCell(1).setCellValue(sc.getLibelle());

			
				row.createCell(2).setCellValue(sc.getDebit());
				cellE = row.getCell(2);
				cellE.setCellType(0);
				row.getCell(2).setCellStyle(style);

				row.createCell(3).setCellValue(sc.getCredit());
				cellE = row.getCell(3);
				cellE.setCellType(0);
				row.getCell(3).setCellStyle(style);
				
				row.createCell(4).setCellValue(sc.getSoldeDbt());
				cellE = row.getCell(4);
				cellE.setCellType(0);
				row.getCell(4).setCellStyle(style);

				row.createCell(5).setCellValue(sc.getSoldeCdt());
				cellE = row.getCell(5);
				cellE.setCellType(0);
				row.getCell(5).setCellStyle(style);
				i++;
				
			}

			
		}
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
		res.setContentType("application/vnd.ms-excel");
		res.setHeader("Content-disposition", "attachment;filename=soldeJournal.xls");

		try {
			ServletOutputStream out = res.getOutputStream();
			wb.write((OutputStream) out);
			out.flush();
			out.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	private double getTotMvt(String cpt, List<Object[]> listSolde,String tp) {
		double montant = 0;
		String cpte="";
		for (Object[] ob :listSolde) {
			cpte = ob[0].toString();
			
			if (cpte.startsWith(cpt)) {
				if(tp.equals("D"))
					montant += Double.valueOf(ob[1].toString()).doubleValue();
				if(tp.equals("C"))
					montant += Double.valueOf(ob[2].toString()).doubleValue();
			}
		}
		
		
		return montant;
	}
	
}
