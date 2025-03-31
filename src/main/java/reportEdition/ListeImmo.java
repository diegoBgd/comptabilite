package reportEdition;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.SessionFactory;

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

import entite.Amortissement;
import entite.CalculAmortissement;
import entite.Compte;
import entite.Ecriture;
import entite.Exercice;
import entite.Immobilise;
import entite.SoldeCompte;
import entite.User;
import model.AmortisementModel;
import model.CompteModel;
import model.ExerciceModel;
import model.ImmobiliseModel;
import model.UserModel;
import persistances.DBConfiguration;
import utils.HelperC;
import utils.HelperItext;
import utils.ItextFooterHelper;

@ManagedBean
@ViewScoped
public class ListeImmo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7749281940812373448L;
	SessionFactory factory = DBConfiguration.getSessionFactory();
	private List<Amortissement> listAmrt;
	private int progressValue;

	List<Immobilise> listImmo;
	Exercice selecetdExercice;
	HttpSession session;
	AmortisementModel model;
	User currentUser;
	String exerCode;
	String currUserCode;
	CalculAmortissement calAmrt;
	Amortissement amort;

	public List<Amortissement> getListAmrt() {
		return listAmrt;
	}

	public void setListAmrt(List<Amortissement> listAmrt) {
		this.listAmrt = listAmrt;
	}

	public int getProgressValue() {
		return progressValue;
	}

	public void setProgressValue(int progressValue) {
		this.progressValue = progressValue;
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
			} else {
				model = new AmortisementModel();
			}
		}
	}

	private void chargement() {
		listImmo = new ImmobiliseModel().getListImmo(factory, "");

	}

	public void calculAmortissement() throws InterruptedException {
		this.listAmrt = new ArrayList<>();
		int i = 0;
		chargement();
		calAmrt = new CalculAmortissement();
		if (this.listImmo.size() > 0) {

			this.progressValue = 0;

			for (Immobilise immo : this.listImmo) {

				this.progressValue = i * 100 / this.listImmo.size();

				this.calAmrt.tableauAmortissemet(immo, immo.getNbrAnne(), this.selecetdExercice);
				this.amort = this.calAmrt.amortissementEncours(this.selecetdExercice.getExCode());
				immo.setAmortEncour(this.amort.getAmortExercice());
				this.amort.setImmo(immo);
				this.amort.setExercice(this.selecetdExercice);

				i++;
				this.amort.setNumero(i);
				this.listAmrt.add(this.amort);
				Thread.sleep(60L);

			}
		}
	}

	@SuppressWarnings("deprecation")
	public void printExcel() {

		if (listAmrt.size() > 0) {

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Immobilises");
			HSSFRow row = null;
			HSSFCellStyle cellStyle = null;
			HSSFCellStyle style = null, style1 = null, style2;
			HSSFFont font = wb.createFont();
			font.setBold(true);

			cellStyle = wb.createCellStyle();
			cellStyle.setFillForegroundColor((short) 29);
			cellStyle.setFillPattern((short) 1);

			style = wb.createCellStyle();
			style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

			style2 = wb.createCellStyle();
			style2.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
			style2.setFont(font);
			style2.setFillPattern((short) 1);
			style2.setFillForegroundColor(IndexedColors.SKY_BLUE.index);

			row = sheet.createRow(0);
			cellStyle = wb.createCellStyle();
			cellStyle.setFont(font);

			row.createCell(0).setCellValue("Code");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(0).setCellStyle(cellStyle);

			row.createCell(1).setCellValue("Libellé");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(1).setCellStyle(cellStyle);

			row.createCell(2).setCellValue("date d'acquisition");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(2).setCellStyle(cellStyle);

			row.createCell(3).setCellValue("Durée de vie");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(3).setCellStyle(cellStyle);

			row.createCell(4).setCellValue("Valeur d'acquisition");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(4).setCellStyle(cellStyle);

			row.createCell(5).setCellValue("Valeur amortie");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(5).setCellStyle(cellStyle);

			row.createCell(6).setCellValue("Valeur net comptable");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(6).setCellStyle(cellStyle);

			double totVacq = 0, totVamrt = 0, totVnc = 0;
			int i = 1;

			for (Amortissement amrt : listAmrt) {

				
				totVacq+=amrt.getImmo().getMontantAcq();
				totVamrt+=amrt.getCumul();
				totVnc+=amrt.getVnc();
				
				row = sheet.createRow(i);
				row.createCell(0).setCellValue(amrt.getImmo().getCodeImmo());
				row.getCell(0).setCellStyle(style);

				row.createCell(1).setCellValue(amrt.getImmo().getLibelle());
				row.getCell(1).setCellStyle(style);

				row.createCell(2).setCellValue(amrt.getDateAcq());
				//row.getCell(2).setCellStyle(style);
				
				row.createCell(3).setCellValue(amrt.getImmo().getNbrAnne()+" ans");
				//row.getCell(4).setCellStyle(style);

				row.createCell(4).setCellValue(amrt.getImmo().getMontantAcq());
				row.getCell(4).setCellStyle(style);
				
				row.createCell(5).setCellValue(amrt.getCumul());
				row.getCell(5).setCellStyle(style);

				row.createCell(6).setCellValue(amrt.getVnc());
				row.getCell(6).setCellStyle(style);

				i++;

			}

			HSSFCell cellT = null;
			style1 = wb.createCellStyle();
			style1.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
			style1.setFillForegroundColor((short) 22);
			style1.setFillPattern((short) 1);
			style1.setFont(font);

			row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue("TOTAL :");
			sheet.addMergedRegion(new CellRangeAddress(i + 1, i + 1, 0, 3));
			row.getCell(0).setCellStyle(style1);

			row.createCell(4).setCellValue(totVacq);
			cellT = row.getCell(4);
			cellT.setCellType(0);
			row.getCell(4).setCellStyle(style1);
			
			row.createCell(5).setCellValue(totVamrt);
			cellT = row.getCell(5);
			cellT.setCellType(0);
			row.getCell(5).setCellStyle(style1);

			row.createCell(6).setCellValue(totVnc);
			cellT = row.getCell(6);
			cellT.setCellType(0);
			row.getCell(6).setCellStyle(style1);

			
			
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
			res.setContentType("application/vnd.ms-excel");
			res.setHeader("Content-disposition", "attachment;filename=listImmo.xls");

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

	public void printPdf() {
		try {
			Image image = null;
			Document doc = new Document(PageSize.A4.rotate());
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

			doc.add((Element) pageHeader("LISTE DES IMMOBILISES "));
			doc.add((Element) getTableImmo());

			doc.close();

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
			res.setHeader("Cache-Control", "Max-age=100");
			res.setContentType("application/pdf");
			res.setHeader("content-disposition", (new StringBuilder("online;filename=listImmobilises.pdf")).toString());
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
	private PdfPTable getTableImmo() throws DocumentException {
		PdfPTable tabInfo = new PdfPTable(8);
		int[] widthsInfo = {5,10,30, 10, 10, 10, 10, 10 };
		tabInfo.setWidthPercentage(105.0F);
		tabInfo.setWidths(widthsInfo);
		Paragraph p = null;
		PdfPCell cell = new PdfPCell();

		if (listAmrt.size() > 0) {

			p = new Paragraph();
			p.add((Element) new Chunk("N°", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Code ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Libellé ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("date d'acquisition", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Durée de vie", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Valeur d'acquisition", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Valeur amortie ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 15, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Valeur net comptable", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 15, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			tabInfo.setHeaderRows(1);

			double totVacq = 0, totVamrt = 0, totVnc = 0;
			int i = 1;
			
			for (Amortissement amort : listAmrt) {

				totVacq+=amort.getImmo().getMontantAcq();
				totVamrt+=amort.getCumul();
				totVnc+=amort.getVnc();
				
				p = new Paragraph();
				p.add((Element) new Chunk("" + i, FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(amort.getImmo().getCodeImmo(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(amort.getImmo().getLibelle(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(amort.getDateAcq(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(1);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(amort.getImmo().getNbrAnne() + " ans",
						FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(1);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(amort.getImmo().getPrintAcqValue(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(2);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(amort.getMontantCumul(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(2);
				cell = HelperItext.getCellule((Element) p, 1, 0, 14, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(amort.getMontantVnc(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(2);
				cell = HelperItext.getCellule((Element) p, 1, 0, 14, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

			}
			
			p = new Paragraph();
			p.add((Element) new Chunk("Total ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(0);
			cell = HelperItext.getCellule((Element) p, 1, 0, 6, 5, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk(HelperC.decimalNumber(totVacq, 0, true), FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk(HelperC.decimalNumber(totVamrt, 0, true), FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule((Element) p, 1, 0, 14, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);
			
		
			p = new Paragraph();
			p.add((Element) new Chunk(HelperC.decimalNumber(totVnc, 0, true), FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule((Element) p, 1, 0, 14, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			
		}

		return tabInfo;
	}
	
}
