package reportEdition;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
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


import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;

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


import entite.Cession;
import entite.Exercice;
import entite.User;

import model.CessionModel;
import model.ExerciceModel;
import model.UserModel;
import persistances.DBConfiguration;
import utils.HelperC;
import utils.HelperItext;
import utils.ItextFooterHelper;

@ManagedBean
@ViewScoped
public class ListeCession implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3736566535206775854L;
	SessionFactory factory = DBConfiguration.getSessionFactory();
	
	
	Exercice selecetdExercice;
	HttpSession session;
	User currentUser;
	String exerCode;
	String currUserCode;
	CessionModel model;
	private int progressValue;
	private List<Cession>listCession;
	
	
	public List<Cession> getListCession() {
		return listCession;
	}

	public void setListCession(List<Cession> listCession) {
		this.listCession = listCession;
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
				model = new CessionModel();
				
			}
		}
	}
	public void chargerment() {
		listCession=model.getListeCession(factory);
		int i=0;
		this.progressValue = 0;
		
		for (Cession cession : listCession) {
			
			this.progressValue = i * 100 / this.listCession.size();
			
			i++;
			cession.setId(i);
			try {
				Thread.sleep(60L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
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

			doc.add((Element) pageHeader("LISTE DES IMMOBILISES CEDES "));
			doc.add((Element) getTableImmo());

			doc.close();

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
			res.setHeader("Cache-Control", "Max-age=100");
			res.setContentType("application/pdf");
			res.setHeader("content-disposition", (new StringBuilder("online;filename=listCession.pdf")).toString());
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

		if (listCession.size() > 0) {

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
			p.add((Element) new Chunk("date cession", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Type cession", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);
			
			p = new Paragraph();
			p.add((Element) new Chunk("Valeur d'acquisition", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			
			p = new Paragraph();
			p.add((Element) new Chunk("Valeur net comptable", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 15, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Montant cession ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 15, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			tabInfo.setHeaderRows(1);

			
			int i = 1;
			
			for (Cession ces : listCession) {


				ces.setId(i);
				
				p = new Paragraph();
				p.add((Element) new Chunk("" + i, FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(ces.getImmo().getCodeImmo(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(ces.getImmo().getLibelle(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(0);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(ces.getPrintDateCession(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(1);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(ces.getTypeCession().toString(),
						FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(1);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(ces.getImmo().getPrintAcqValue(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(2);
				cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(ces.getPrintVnc(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(2);
				cell = HelperItext.getCellule((Element) p, 1, 0, 14, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

				p = new Paragraph();
				p.add((Element) new Chunk(ces.getPrintMontant(), FontFactory.getFont("Times", 8.0F, 0)));
				p.setAlignment(2);
				cell = HelperItext.getCellule((Element) p, 1, 0, 14, 0, 0.5F, 3.0F, true);
				tabInfo.addCell(cell);

			}
			
			

			
		}

		return tabInfo;
	}
	
	@SuppressWarnings("deprecation")
	public void printExcel() {

		if (listCession.size() > 0) {

			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Cession");
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

			row.createCell(2).setCellValue("date cession");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(2).setCellStyle(cellStyle);

			row.createCell(3).setCellValue("Type cession");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(3).setCellStyle(cellStyle);

			row.createCell(4).setCellValue("Valeur d'acquisition");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(4).setCellStyle(cellStyle);

			row.createCell(5).setCellValue("Valeur net comptable");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(5).setCellStyle(cellStyle);

			row.createCell(6).setCellValue("Montant cession");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(6).setCellStyle(cellStyle);

		
			int i = 1;

			for (Cession ces : listCession) {

				
				ces.setId(i);
				
				row = sheet.createRow(i);
				row.createCell(0).setCellValue(ces.getImmo().getCodeImmo());
				row.getCell(0).setCellStyle(style);

				row.createCell(1).setCellValue(ces.getImmo().getLibelle());
				row.getCell(1).setCellStyle(style);

				row.createCell(2).setCellValue(ces.getPrintDateCession());
				//row.getCell(2).setCellStyle(style);
				
				row.createCell(3).setCellValue(ces.getTypeCession().toString());
				//row.getCell(4).setCellStyle(style);

				row.createCell(4).setCellValue(ces.getImmo().getMontantAcq());
				row.getCell(4).setCellStyle(style);
				
				row.createCell(5).setCellValue(ces.getVnc());
				row.getCell(5).setCellStyle(style);

				row.createCell(6).setCellValue(ces.getMontantCession());
				row.getCell(6).setCellStyle(style);

				i++;

			}

			
			
			
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
			res.setContentType("application/vnd.ms-excel");
			res.setHeader("Content-disposition", "attachment;filename=listCession.xls");

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
}
