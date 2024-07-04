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
import entite.CompteEFi;
import entite.EtatFinancier;
import entite.Exercice;
import entite.RubriqueEFi;
import entite.User;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
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
import model.EtatFinancierModel;
import model.ExerciceModel;
import model.RubriqueEFiModel;
import model.UserModel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.SessionFactory;
import org.primefaces.PrimeFaces;
import persistances.DBConfiguration;
import utils.HelperC;
import utils.HelperItext;
import utils.ItextFooterHelper;

@ManagedBean
@ViewScoped
public class EditionEtatFinancier implements Serializable {
	private static final long serialVersionUID = 6629207590720252955L;
	private RubriqueEFi selectedRubrique;
	private List<RubriqueEFi> listRubrique;
	private EtatFinancier selectedEfi;
	private List<EtatFinancier> listEFi;
	private String code;
	private String libelle;
	List<Object[]> listSolde;

	SessionFactory factory = DBConfiguration.getSessionFactory();

	RubriqueEFiModel model;

	Exercice selecetdExercice;
	HttpSession session;
	String formulgle = "";
	String exerCode;
	String currUserCode;
	User currentUser;
	Exercice exercicePcd;
	EcritureModel emodel;
	String codeEx = "", codeExPrcd = "";

	public RubriqueEFi getSelectedRubrique() {
		return this.selectedRubrique;
	}

	public void setSelectedRubrique(RubriqueEFi selectedRubrique) {
		this.selectedRubrique = selectedRubrique;
	}

	public List<RubriqueEFi> getListRubrique() {
		return this.listRubrique;
	}

	public void setListRubrique(List<RubriqueEFi> listRubrique) {
		this.listRubrique = listRubrique;
	}

	public EtatFinancier getSelectedEfi() {
		return this.selectedEfi;
	}

	public void setSelectedEfi(EtatFinancier selectedEfi) {
		this.selectedEfi = selectedEfi;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public List<EtatFinancier> getListEFi() {
		return this.listEFi;
	}

	public void setListEFi(List<EtatFinancier> listEFi) {
		this.listEFi = listEFi;
	}

	@PostConstruct
	public void initialize() {
		this.model = new RubriqueEFiModel();
		this.emodel = new EcritureModel();

		chargementSession();
	}

	private void chargementSession() {
		this.session = HelperC.getSession();
		if (this.session != null) {
			this.exerCode = (String) this.session.getAttribute("exercice");
			this.currUserCode = (String) this.session.getAttribute("operateur");

			if (this.exerCode != null) {
				this.selecetdExercice = (new ExerciceModel()).getExercByCode(this.factory, this.exerCode);
				if (this.selecetdExercice != null) {
					codeEx = exerCode;
					if (this.selecetdExercice.getExPrcdCode() != null
							&& !this.selecetdExercice.getExPrcdCode().equals(""))
						this.exercicePcd = (new ExerciceModel()).getExercByCode(this.factory,
								this.selecetdExercice.getExPrcdCode());
					if (exercicePcd != null)
						codeExPrcd = exercicePcd.getExCode();
				}
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

	public void chargerEtatFinancier() {
		this.listEFi = (new EtatFinancierModel()).getListEFi(this.factory);
	}

	public void searchEtatFinancier() {
		if (getCode() != null && !getCode().equals("")) {

			this.selectedEfi = (new EtatFinancierModel()).getEFiByCode(this.factory, getCode());
			if (this.selectedEfi != null) {

				getEFivalues();
				PrimeFaces.current().executeScript("PF('dlgEfi').hide();");
			}
		}
	}

	public void getSelectedEFiValue() {
		if (this.selectedEfi != null)
			getEFivalues();
		PrimeFaces.current().executeScript("PF('dlgEfi').hide();");
	}

	private void getEFivalues() {
		setCode(this.selectedEfi.getCode());
		setLibelle(this.selectedEfi.getLibelle());
	}

	public void printBilanPdf() {
		try {
			String nomFichier="";
			Image image = null;
			Document doc = new Document(PageSize.A4);
			ByteArrayOutputStream docMem = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(doc, docMem);
			doc.addAuthor("Gatech");
			doc.addProducer();
			doc.addCreationDate();
			doc.addTitle(this.libelle);
			doc.open();
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
					.getContext();
			image = Image
					.getInstance(String.valueOf(servletContext.getRealPath("/resources")) + "\\Images\\" + "logo.png");
			image.scaleAbsoluteHeight(90.0F);
			image.scaleAbsoluteWidth(90.0F);
			doc.add((Element) image);
			writer.setPageEvent((PdfPageEvent) new ItextFooterHelper(new Phrase(
					"Produit Gatech                          "
							+ HelperC.convertDateHeureMin(Calendar.getInstance().getTime()),
					new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 0))));

			switch (this.selectedEfi.getTypeEtat()) {
			case 1:
				chargerSolde();
				doc.add(pageHeader("BILAN"));
				nomFichier="BILAN";
				doc.add((Element) getBilanActif());
				doc.newPage();
				doc.add((Element) getBilanPassif());
				
				break;

			case 2:
				chargerSolde();
				nomFichier="COMPTE-RESULTAT";
				doc.add(pageHeader("COMPTE RESULTAT"));
				doc.add((Element) getTableau());
				break;
			case 3:
				chargerSolde();
				doc.add(pageHeader("TABLEAU DE FLUX DE  TRESORERIE"));
				nomFichier="FLUX-DE-TRESORERIE";
				doc.add((Element) getTableau());
				break;
			}

			doc.close();

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
			res.setHeader("Cache-Control", "Max-age=100");
			res.setContentType("application/pdf");
			res.setHeader("content-disposition", (new StringBuilder("online;filename="+nomFichier+".pdf")).toString());
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

		cell = HelperItext.getPdfCell(HelperC.convertDate(Calendar.getInstance().getTime()),
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

	private PdfPTable getBilanActif() throws DocumentException {
		PdfPTable tabInfo = new PdfPTable(5);
		int[] widthsInfo = { 20, 10, 10, 10, 10 };
		tabInfo.setWidthPercentage(105.0F);
		tabInfo.setWidths(widthsInfo);
		Paragraph p = null;
		PdfPCell cell = new PdfPCell();

		chargerRubrique();

		if (this.listRubrique.size() > 0) {

			p = new Paragraph();
			p.add((Element) new Chunk("  ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 1, 0, 0, 0.5F, 3.0F, false);
			cell.setVerticalAlignment(1);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("EXERCICE " + codeEx, FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 3, 0.5F, 3.0F, false);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("EXERCICE " + codeExPrcd, FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 15, 0, 0.5F, 3.0F, false);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk(" ACTIF ", FontFactory.getFont("Times", 12.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 1, 2, 0, 0.5F, 3.0F, false);
			cell.setVerticalAlignment(1);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Brut", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 14, 0, 0.5F, 3.0F, false);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Amortissements et provisions ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 10, 0, 0.5F, 3.0F, false);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Net", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 10, 0, 0.5F, 3.0F, false);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Net", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 10, 0, 0.5F, 3.0F, false);
			tabInfo.addCell(cell);

			tabInfo.setHeaderRows(1);

			int j = 0;

			float taille = 0.0F;
			for (RubriqueEFi rubriqueEFi : this.listRubrique) {
				if (rubriqueEFi.isTitreValeur()) {
					taille = 10.0F;
				} else {
					taille = 8.0F;
				}
				if (rubriqueEFi.getTypeValeur() == 1) {
					j++;
					p = new Paragraph();
					p.add((Element) new Chunk(rubriqueEFi.getLibelle(), FontFactory.getFont("Times", taille, 0)));
					p.setAlignment(0);
					cell = HelperItext.getCellule((Element) p, 1, 0, 12, 0, 0.5F, 3.0F, false);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add((Element) new Chunk(HelperC.decimalNumber(rubriqueEFi.getBrut(), 0, true),
							FontFactory.getFont("Times", taille, 0)));
					p.setAlignment(2);
					cell = HelperItext.getCellule((Element) p, 1, 0, 12, 0, 0.5F, 3.0F, false);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add((Element) new Chunk(HelperC.decimalNumber(rubriqueEFi.getAmortissemnt(), 0, true),
							FontFactory.getFont("Times", taille, 0)));
					p.setAlignment(2);
					cell = HelperItext.getCellule((Element) p, 1, 0, 12, 0, 0.5F, 3.0F, false);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add((Element) new Chunk(
							HelperC.decimalNumber(rubriqueEFi.getBrut() - rubriqueEFi.getAmortissemnt(), 0, true),
							FontFactory.getFont("Times", taille, 0)));
					p.setAlignment(2);
					cell = HelperItext.getCellule((Element) p, 1, 0, 12, 0, 0.5F, 3.0F, false);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add((Element) new Chunk(HelperC.decimalNumber(rubriqueEFi.getNetAprcd(), 0, true),
							FontFactory.getFont("Times", taille, 0)));
					p.setAlignment(2);
					cell = HelperItext.getCellule((Element) p, 1, 0, 8, 0, 0.5F, 3.0F, false);
					tabInfo.addCell(cell);
				}
			}

			p = new Paragraph();
			p.add((Element) new Chunk("", FontFactory.getFont("Times", 8.0F, 0)));
			p.setAlignment(0);
			cell = HelperItext.getCellule((Element) p, 1, 0, 14, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			for (int i = 0; i <= 4; i++) {
				p = new Paragraph();
				p.add((Element) new Chunk("", FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 0, 10, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);
			}
		}

		return tabInfo;
	}

	private PdfPTable getBilanPassif() throws DocumentException {
		PdfPTable tabInfo = new PdfPTable(3);
		int[] widthsInfo = { 20, 10, 10 };
		tabInfo.setWidthPercentage(105.0F);
		tabInfo.setWidths(widthsInfo);
		Paragraph p = null;
		PdfPCell cell = new PdfPCell();

		float taille = 0.0F;

		if (this.listRubrique.size() > 0) {

			p = new Paragraph();
			p.add((Element) new Chunk(" PASSIF \n", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 2, 0, 0.5F, 3.0F, false);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("EXERCICE " + codeEx, FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, false);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("EXERCICE " + codeExPrcd, FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 15, 0, 0.5F, 3.0F, false);
			tabInfo.addCell(cell);

			int j = 0;

			for (RubriqueEFi rubriqueEFi : this.listRubrique) {
				if (rubriqueEFi.isTitreValeur()) {
					taille = 10.0F;
				} else {
					taille = 8.0F;
				}
				if (rubriqueEFi.getTypeValeur() == 2) {

					j++;
					p = new Paragraph();
					p.add((Element) new Chunk(rubriqueEFi.getLibelle(), FontFactory.getFont("Times", taille, 0)));
					p.setAlignment(0);
					cell = HelperItext.getCellule((Element) p, 1, 0, 12, 0, 0.5F, 3.0F, false);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add((Element) new Chunk(HelperC.decimalNumber(rubriqueEFi.getBrut(), 0, true),
							FontFactory.getFont("Times", taille, 0)));
					p.setAlignment(2);
					cell = HelperItext.getCellule((Element) p, 1, 0, 8, 0, 0.5F, 3.0F, false);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add((Element) new Chunk(HelperC.decimalNumber(rubriqueEFi.getNetAprcd(), 0, true),
							FontFactory.getFont("Times", taille, 0)));
					p.setAlignment(2);
					cell = HelperItext.getCellule((Element) p, 1, 0, 8, 0, 0.5F, 3.0F, false);
					tabInfo.addCell(cell);
				}
			}

			p = new Paragraph();
			p.add((Element) new Chunk("", FontFactory.getFont("Times", 8.0F, 0)));
			p.setAlignment(0);
			cell = HelperItext.getCellule((Element) p, 1, 0, 14, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			for (int i = 0; i <= 2; i++) {
				p = new Paragraph();
				p.add((Element) new Chunk("", FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 0, 10, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);
			}
		}

		return tabInfo;
	}

	private PdfPTable getTableau() throws DocumentException {
		PdfPTable tabInfo = new PdfPTable(3);
		int[] widthsInfo = { 20, 10, 10 };
		tabInfo.setWidthPercentage(105.0F);
		tabInfo.setWidths(widthsInfo);
		Paragraph p = null;
		PdfPCell cell = new PdfPCell();

		chargerRubrique();
		float taille = 0.0F;

		if (this.listRubrique.size() > 0) {

			p = new Paragraph();
			p.add((Element) new Chunk("  ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 2, 0, 0.5F, 3.0F, false);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("EXERCICE " + codeEx, FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, false);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("EXERCICE " + codeExPrcd, FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 15, 0, 0.5F, 3.0F, false);
			tabInfo.addCell(cell);

			int j = 0;

			for (RubriqueEFi rubriqueEFi : this.listRubrique) {

				if (rubriqueEFi.isTitreValeur()) {
					taille = 10.0F;
				} else {
					taille = 8.0F;
				}
				j++;
				p = new Paragraph();
				p.add((Element) new Chunk(rubriqueEFi.getLibelle(), FontFactory.getFont("Times", taille, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 0, 12, 0, 0.5F, 3.0F, false);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(HelperC.decimalNumber(rubriqueEFi.getBrut(), 0, true),
						FontFactory.getFont("Times", taille, 0)));
				p.setAlignment(2);
				cell = HelperItext.getCellule((Element) p, 1, 0, 8, 0, 0.5F, 3.0F, false);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(HelperC.decimalNumber(rubriqueEFi.getNetAprcd(), 0, true),
						FontFactory.getFont("Times", taille, 0)));
				p.setAlignment(2);
				cell = HelperItext.getCellule((Element) p, 1, 0, 8, 0, 0.5F, 3.0F, false);
				tabInfo.addCell(cell);
			}

			p = new Paragraph();
			p.add((Element) new Chunk("", FontFactory.getFont("Times", 8.0F, 0)));
			p.setAlignment(0);
			cell = HelperItext.getCellule((Element) p, 1, 0, 14, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			for (int i = 0; i <= 2; i++) {
				p = new Paragraph();
				p.add((Element) new Chunk("", FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 0, 10, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);
			}
		}

		return tabInfo;
	}

	public void printExel() {
		switch (this.selectedEfi.getTypeEtat()) {
		case 1:
			chargerSolde();
			printBilanExcel();
			
			break;

		case 2:
			chargerSolde();
			printCompteResultatExcel();
			break;
		case 3:
			chargerSolde();
			
			break;
		}
	}
	private void printBilanExcel() {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Actif");
		HSSFRow row = null;
		HSSFCellStyle cellStyle = null;
		HSSFCellStyle style = null, style1 = null;

		chargerRubrique();
		
		cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor((short) 29);
		cellStyle.setFillPattern((short) 1);

		style = wb.createCellStyle();

		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

	

		double totDeb = 0.0D, totCrd = 0.0D, deb = 0.0D, crd = 0.0D, soldDb = 0.0D, soldCd = 0.0D, totSoldDb = 0.0D,
				totSoldCd = 0.0D;
		boolean bl = false;

		if (this.listRubrique.size() > 0) {

			row = sheet.createRow(0);

			row.createCell(0).setCellValue(" ACTIF ");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(0).setCellStyle(cellStyle);

			row.createCell(1).setCellValue("EXERCICE N");
			cellStyle.setFillForegroundColor((short) 13);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 3));
			cellStyle.setFillPattern((short) 1);		
			row.getCell(1).setCellStyle(cellStyle);

			row.createCell(4).setCellValue("EXERCICE N-1");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(4).setCellStyle(cellStyle);

			row = sheet.createRow(1);
			
			row.createCell(0).setCellValue(" ");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(0).setCellStyle(cellStyle);

			row.createCell(1).setCellValue("Brut");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(1).setCellStyle(cellStyle);

			row.createCell(2).setCellValue("Amortissements et provisions ");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(2).setCellStyle(cellStyle);

			row.createCell(3).setCellValue("Net");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(3).setCellStyle(cellStyle);
			
			row.createCell(4).setCellValue("Net");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(4).setCellStyle(cellStyle);

			int i = 2;

			for (RubriqueEFi rubriqueEFi : this.listRubrique) {

				if (rubriqueEFi.getTypeValeur() == 1) {
					i++;
					row = sheet.createRow(i);
					HSSFCell cellE = null;

					row.createCell(0).setCellValue(rubriqueEFi.getLibelle());

					row.createCell(1).setCellValue(rubriqueEFi.getBrut());
					cellE = row.getCell(1);
					cellE.setCellType(0);
					row.getCell(1).setCellStyle(style);

					row.createCell(2).setCellValue(rubriqueEFi.getAmortissemnt());
					cellE = row.getCell(2);
					cellE.setCellType(0);
					row.getCell(2).setCellStyle(style);

					row.createCell(3).setCellValue(rubriqueEFi.getNetAn());
					cellE = row.getCell(3);
					cellE.setCellType(0);
					row.getCell(3).setCellStyle(style);

					row.createCell(4).setCellValue(rubriqueEFi.getNetAprcd());
					cellE = row.getCell(4);
					cellE.setCellType(0);
					row.getCell(4).setCellStyle(style);
				}
				
					
				
			}
			
			HSSFSheet sheet2 = wb.createSheet("Passif");
			HSSFRow rowS2 = null;
			HSSFCellStyle cellStyleS2 = null;
			HSSFCellStyle styleS2 = null;
			
			cellStyleS2 = wb.createCellStyle();
			cellStyleS2.setFillForegroundColor((short) 29);
			cellStyleS2.setFillPattern((short) 1);

			styleS2 = wb.createCellStyle();

			styleS2.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

			rowS2 = sheet2.createRow(0);

			rowS2.createCell(0).setCellValue(" PASSIF ");
			cellStyleS2.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			rowS2.getCell(0).setCellStyle(cellStyleS2);

			rowS2.createCell(1).setCellValue("EXERCICE N");
			cellStyleS2.setFillForegroundColor((short) 13);
			cellStyleS2.setFillPattern((short) 1);		
			rowS2.getCell(1).setCellStyle(cellStyleS2);

			rowS2.createCell(2).setCellValue("EXERCICE N-1");
			cellStyleS2.setFillForegroundColor((short) 13);
			cellStyleS2.setFillPattern((short) 1);
			rowS2.getCell(2).setCellStyle(cellStyleS2);

			
			
			int k=2;
			
			for (RubriqueEFi rubriqueEFi : this.listRubrique) 
			{
				if (rubriqueEFi.getTypeValeur() == 2) {
					rowS2 = sheet2.createRow(k);
					HSSFCell cellE = null;

					rowS2.createCell(0).setCellValue(rubriqueEFi.getLibelle());

					rowS2.createCell(1).setCellValue(rubriqueEFi.getBrut());
					cellE = row.getCell(1);
					cellE.setCellType(0);
					rowS2.getCell(1).setCellStyle(styleS2);

					rowS2.createCell(2).setCellValue(rubriqueEFi.getNetAprcd());
					cellE = row.getCell(2);
					cellE.setCellType(0);
					rowS2.getCell(2).setCellStyle(styleS2);
					
					k++;
				}
			}
			
			

		
		}
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
		res.setContentType("application/vnd.ms-excel");
		res.setHeader("Content-disposition", "attachment;filename=bilan.xls");

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
	
	private void printCompteResultatExcel() {
		HSSFWorkbook wb = new HSSFWorkbook();
		
		chargerRubrique();
		
		
			HSSFSheet sheet2 = wb.createSheet("compteResultat");
			HSSFRow rowS2 = null;
			HSSFCellStyle cellStyleS2 = null;
			HSSFCellStyle styleS2 = null;
			
			cellStyleS2 = wb.createCellStyle();
			cellStyleS2.setFillForegroundColor((short) 29);
			cellStyleS2.setFillPattern((short) 1);

			styleS2 = wb.createCellStyle();

			styleS2.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

			rowS2 = sheet2.createRow(0);

			rowS2.createCell(0).setCellValue(" COMPTE RESULTAT ");
			cellStyleS2.setFillForegroundColor((short) 13);
			cellStyleS2.setFillPattern((short) 1);
			rowS2.getCell(0).setCellStyle(cellStyleS2);

			rowS2.createCell(1).setCellValue("EXERCICE N");
			cellStyleS2.setFillForegroundColor((short) 13);
			cellStyleS2.setFillPattern((short) 1);		
			rowS2.getCell(1).setCellStyle(cellStyleS2);

			rowS2.createCell(2).setCellValue("EXERCICE N-1");
			cellStyleS2.setFillForegroundColor((short) 13);
			cellStyleS2.setFillPattern((short) 1);
			rowS2.getCell(2).setCellStyle(cellStyleS2);

			
			
			int k=2;
			
			for (RubriqueEFi rubriqueEFi : this.listRubrique) 
			{
				
					rowS2 = sheet2.createRow(k);
					HSSFCell cellE = null;

					rowS2.createCell(0).setCellValue(rubriqueEFi.getLibelle());

					rowS2.createCell(1).setCellValue(rubriqueEFi.getBrut());
					cellE = rowS2.getCell(1);
					cellE.setCellType(0);
					rowS2.getCell(1).setCellStyle(styleS2);

					rowS2.createCell(2).setCellValue(rubriqueEFi.getNetAprcd());
					cellE = rowS2.getCell(2);
					cellE.setCellType(0);
					rowS2.getCell(2).setCellStyle(styleS2);
					
					k++;
				
			}
			
			

		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
		res.setContentType("application/vnd.ms-excel");
		res.setHeader("Content-disposition", "attachment;filename=compteResultat.xls");

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
	
	public void chargerRubrique() {
		this.listRubrique = this.model.getListRubrique(this.factory, this.selectedEfi);

		Long valeurCode = (long) 0, valeur = (long) 0, valeurAN = (long) 0, valeurAmrt = (long) 0, valeurCodePcd = (long) 0, amort = (long) 0;
		String formuleResult = "", formule = "", formulePcd = "", formuleAmrt = "";

		String compte = "";

		for (RubriqueEFi rubriqueEFi : this.listRubrique) {

			if (!rubriqueEFi.getFormule().trim().equals("")) {

				formulgle = rubriqueEFi.getFormule();

				//formule = chercherFormule(rubriqueEFi);
				formule=formulgle;
				formuleResult = HelperC.getElementFormule(this.formulgle);
				String[] codes = formuleResult.split(" ");
				formulePcd = this.formulgle;
				formuleAmrt = this.formulgle;

				for (int i = 0; i < codes.length; i++) {

					RubriqueEFi detail = GetEtatParametreDetail(codes[i].toString());

					if (detail != null) {

						if (detail.getListCompte().size() > 0) {
							for (CompteEFi ce : detail.getListCompte()) {

								compte = String.valueOf(compte) + ce.getCompteDeb();

								if (!ce.getCompteFin().equals("")) {
									compte = String.valueOf(compte) + "-" + ce.getCompteFin();
								} else {
									compte = String.valueOf(compte) + ",";
								}
								switch (ce.getColumnValue()) {

								case 0:
									if (!ce.isCalucule()) {
										if(ce.getSigne().equals("P"))
										valeurCode += getValeurCompte(ce, this.selecetdExercice);
										if(ce.getSigne().equals("M"))
											valeurCode -= getValeurCompte(ce, this.selecetdExercice);
										break;
									}
									if(ce.getSigne().equals("P"))
									valeurCode +=ce.getValeur(); 
									if(ce.getSigne().equals("M"))
										valeurCode -=ce.getValeur(); 
									break;
								case 1:
									if (!ce.isCalucule()) {
										if(ce.getSigne().equals("P"))
										valeurAmrt += getValeurCompte(ce, this.selecetdExercice);
										if(ce.getSigne().equals("M"))
											valeurAmrt -= getValeurCompte(ce, this.selecetdExercice);
										break;
									}
									if(ce.getSigne().equals("P"))
									valeurAmrt += ce.getValeur();
									if(ce.getSigne().equals("M"))
										valeurAmrt -= ce.getValeur();
									break;
								}
								if (this.exercicePcd != null) {
									valeurCodePcd = getValeurCompte(ce, this.exercicePcd);
								}
							}
						}
						
						  else {
						
						  if(!detail.isCalculated()) detail.setCalculated(true);
						  valeurCode+=(long)detail.getBrut();
						  valeurAmrt+=(long)detail.getAmortissemnt();
						  }
						 
						
						formule = formule.replace(codes[i], String.valueOf(valeurCode));
						formulePcd = formulePcd.replace(codes[i], String.valueOf(valeurCodePcd));
						formuleAmrt = formuleAmrt.replace(codes[i], String.valueOf(valeurAmrt));
						
					
						
						valeurCode = (long) 0.0D;
						valeurCodePcd = (long) 0.0D;
						valeurAmrt = (long) 0.0D;
					}
				}

				valeur = (long) HelperC.getFormulaValue(formule);
				valeurAN = (long) HelperC.getFormulaValue(formulePcd);
				amort = (long) HelperC.getFormulaValue(formuleAmrt);

				if (valeur == Double.NaN)
					valeur = (long) 0.0D;
				if (valeurAN == Double.NaN)
					valeurAN = (long) 0.0D;
				formule = "";
				formulePcd = "";
				
				if (rubriqueEFi.getSigne().equals("P"))
					rubriqueEFi.setBrut(valeur);
				if (rubriqueEFi.getSigne().equals("M"))
					rubriqueEFi.setBrut(-valeur);
				if (rubriqueEFi.getSigne().equals("N"))
					rubriqueEFi.setBrut(valeur);
				
				rubriqueEFi.setAmortissemnt(amort);
				rubriqueEFi.setNetAprcd(valeurCodePcd);
				
				rubriqueEFi.setCalculated(true);
				continue;
			}
			if (!rubriqueEFi.isCalculated() && rubriqueEFi.getListCompte().size() > 0) {

				for (CompteEFi ce : rubriqueEFi.getListCompte()) {

					compte = String.valueOf(compte) + ce.getCompteDeb();

					if (!ce.getCompteFin().equals("")) {
						compte = String.valueOf(compte) + "-" + ce.getCompteFin();
					} else {
						compte = String.valueOf(compte) + ",";
					}
					switch (ce.getColumnValue()) {

					case 0:
						if (!ce.isCalucule()) {
							if(ce.getSigne().equals("P"))
							valeurCode += getValeurCompte(ce, this.selecetdExercice);
							if(ce.getSigne().equals("M"))
								valeurCode -= getValeurCompte(ce, this.selecetdExercice);
							break;
						}
						if(ce.getSigne().equals("P"))
						valeurCode += ce.getValeur();
						if(ce.getSigne().equals("M"))
							valeurCode -= ce.getValeur();
						break;
					case 1:
						if (!ce.isCalucule()) {
							if(ce.getSigne().equals("P"))
							valeurAmrt += getValeurCompte(ce, this.selecetdExercice);
							if(ce.getSigne().equals("M"))
								valeurAmrt -= getValeurCompte(ce, this.selecetdExercice);
							break;
						}
						if(ce.getSigne().equals("P"))
						valeurAmrt += ce.getValeur();
						if(ce.getSigne().equals("M"))
							valeurAmrt -= ce.getValeur();
						break;
					}
					if (this.exercicePcd != null) {
						valeurCodePcd = getValeurCompte(ce, this.exercicePcd);
					}
					compte="";
				}
				
				if (rubriqueEFi.getSigne().equals("P"))
					rubriqueEFi.setBrut(valeurCode);
				if (rubriqueEFi.getSigne().equals("M"))
					rubriqueEFi.setBrut(-valeurCode);
				if (rubriqueEFi.getSigne().equals("N"))
					rubriqueEFi.setBrut(valeurCode);
			
				rubriqueEFi.setAmortissemnt(valeurAmrt);
				rubriqueEFi.setNetAprcd(valeurCodePcd);

				rubriqueEFi.setCalculated(true);
				valeurCode = (long) 0.0D;
				valeurCodePcd = (long) 0.0D;
				valeurAmrt = (long) 0.0D;
			}
			
		}
	}

	private String chercherFormule(RubriqueEFi rub) {
		String formulRes = "";

		if (rub != null && !rub.getFormule().equals("")) {

			formulRes = HelperC.getElementFormule(rub.getFormule());

			String[] codes = formulRes.split(" ");

			for (int i = 0; i < codes.length; i++) {

				RubriqueEFi detail = GetEtatParametreDetail(codes[i].toString());

				if (detail != null && !detail.getFormule().equals("")) {

					formulgle = formulgle.replace(codes[i], detail.getFormule());
					chercherFormule(detail);
				}
			}
		}

		return this.formulgle;
	}

	private RubriqueEFi GetEtatParametreDetail(String code) {
		RubriqueEFi rub = null;

		for (RubriqueEFi rubriqueEFi : this.listRubrique) {

			if (rubriqueEFi.getCode().equals(code)) {

				rub = rubriqueEFi;
				break;
			}
		}
		return rub;
	}

	private Long getValeurCompte(CompteEFi ce, Exercice ex) {
		double valeur = 0.0D;
		
		String cpteDb=ce.getCompteDeb();
		String cpteFn=ce.getCompteFin();
		
		  if(cpteFn.equals(null) || cpteFn.equals("")) {
			  cpteFn=HelperC.completerCompte(cpteDb); }
		 
		List<Compte> listCpt = new CompteModel().getPlanComptable(factory,cpteDb, cpteFn);
		if (listCpt.size() > 0)
			for (Compte compte : listCpt) {
				valeur+=getValue(compte.getCompteCod(), ce.getTypeSolde());
			}
		
	
		/*
		 * switch (ce.getTypeSolde()) { case 1:
		 * 
		 * valeur = (new EcritureModel()).getSoldeCompteBetween(this.factory,
		 * ex.getId(), ce.getCompteDeb(), ce.getCompteFin(), null, null); break;
		 * 
		 * case 2: valeur = (new
		 * EcritureModel()).getDebitCreditCompteBetween(this.factory, ex.getId(),
		 * ce.getCompteDeb(), ce.getCompteFin(), null, null, "D"); break;
		 * 
		 * case 3: valeur = (new
		 * EcritureModel()).getDebitCreditCompteBetween(this.factory, ex.getId(),
		 * ce.getCompteDeb(), ce.getCompteFin(), null, null, "C"); break; }
		 */

		ce.setCalucule(true);
		ce.setValeur(Long.valueOf(HelperC.decimalNumber(valeur, 0, false)));
	

		return 	Long.valueOf(HelperC.decimalNumber(valeur, 0, false));
	}

	private void chargerSolde() {

		listSolde = new EcritureModel().getSoldeCompte(factory, selecetdExercice.getId(), "", "", null, null);
		
	}

	private double getValue(String compte, int typeValue) {
		double value = 0.0D, db = 0, cd = 0;
		String cpt = "";
		switch (typeValue) {
		case 1:
	
			for (Object[] ob : this.listSolde) {
				cpt = ob[0].toString();
				
				if (cpt.startsWith(compte)) {

					db=0;cd=0;
					
					db = Double.valueOf(ob[1].toString()).doubleValue();
					cd = Double.valueOf(ob[2].toString()).doubleValue();
					
					if(db>cd)
						value += (db - cd);
					if(db<cd)
						value += (cd-db);
					if(db==cd)
						value +=0;
				}
			}

			break;

		case 2:
			for (Object[] ob : this.listSolde) {
				cpt = ob[0].toString();
				
				
				if (cpt.startsWith(compte)) {
					db=0;cd=0;
					
					db = Double.valueOf(ob[1].toString()).doubleValue();
					cd = Double.valueOf(ob[2].toString()).doubleValue();
					if(db>cd)
						value += (db - cd);
					else
						value +=0;
					
				}
			}
			break;

		case 3:
			for (Object[] ob : this.listSolde) {
				cpt = ob[0].toString();			
				
				if (cpt.startsWith(compte)) {

					db=0;cd=0;
					
					db = Double.valueOf(ob[1].toString()).doubleValue();
					cd = Double.valueOf(ob[2].toString()).doubleValue();
					
					if(db<cd)
						value += (cd-db);
					else
						value +=0;
				}
			}
			break;
		}

		return value;
	}

}
