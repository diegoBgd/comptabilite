package reportEdition;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
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
import org.primefaces.PrimeFaces;

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
import entite.User;
import model.CompteModel;
import model.EcritureModel;
import model.ExerciceModel;
import model.UserModel;
import persistances.DBConfiguration;
import utils.HelperItext;
import utils.ItextFooterHelper;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class GrandLivre implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3472362688369787198L;

	private Compte selectedCpteDb, selectedCpteFn;
	private List<Compte> listeCpte;
	private Date dateDebut;
	private Date dateFin;
	private String rechDateDeb;
	private String rechDateFin;
	private String compteDeb, compteFin, rechCodCpt, rechLblCpt, libelleCptDb, libelleCptFin, infoMsg;

	SessionFactory factory = DBConfiguration.getSessionFactory();
	EcritureModel model;
	Exercice selecetdExercice;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;

	public GrandLivre() {

	}

	public Compte getSelectedCpteDb() {
		return selectedCpteDb;
	}

	public void setSelectedCpteDb(Compte selectedCpteDb) {
		this.selectedCpteDb = selectedCpteDb;
	}

	public Compte getSelectedCpteFn() {
		return selectedCpteFn;
	}

	public void setSelectedCpteFn(Compte selectedCpteFn) {
		this.selectedCpteFn = selectedCpteFn;
	}

	public List<Compte> getListeCpte() {
		return listeCpte;
	}

	public void setListeCpte(List<Compte> listeCpte) {
		this.listeCpte = listeCpte;
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

	public String getCompteDeb() {
		return compteDeb;
	}

	public void setCompteDeb(String compteDeb) {
		this.compteDeb = compteDeb;
	}

	public String getCompteFin() {
		return compteFin;
	}

	public void setCompteFin(String compteFin) {
		this.compteFin = compteFin;
	}

	public String getLibelleCptDb() {
		return libelleCptDb;
	}

	public void setLibelleCptDb(String libelleCptDb) {
		this.libelleCptDb = libelleCptDb;
	}

	public String getLibelleCptFin() {
		return libelleCptFin;
	}

	public void setLibelleCptFin(String libelleCptFin) {
		this.libelleCptFin = libelleCptFin;
	}

	public String getInfoMsg() {
		return infoMsg;
	}

	public void setInfoMsg(String infoMsg) {
		this.infoMsg = infoMsg;
	}

	public String getRechCodCpt() {
		return rechCodCpt;
	}

	public void setRechCodCpt(String rechCodCpt) {
		this.rechCodCpt = rechCodCpt;
	}

	public String getRechLblCpt() {
		return rechLblCpt;
	}

	public void setRechLblCpt(String rechLblCpt) {
		this.rechLblCpt = rechLblCpt;
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

	public void searchCptDeb() {
		if (compteDeb != null && !compteDeb.equals("")) {

			selectedCpteDb = (new CompteModel()).getCompteByCode(this.factory, compteDeb);
			if (selectedCpteDb != null) {
				getCptDbValues();
			} else {
				HelperC.afficherAttention("Attention", "Ce compte n'existe pas !");

			}
		}
	}

	public void searchCptFn() {
		if (compteFin != null && !compteFin.equals("")) {

			selectedCpteFn = (new CompteModel()).getCompteByCode(this.factory, compteDeb);
			if (selectedCpteDb != null) {
				getCptFnValues();
			} else {
				HelperC.afficherAttention("Attention", "Ce compte n'existe pas !");

			}
		}
	}

	private void getCptFnValues() {
		compteFin = selectedCpteFn.getCompteCod();
		libelleCptFin = selectedCpteFn.getLibelle();

	}

	private void getCptDbValues() {
		compteDeb = selectedCpteDb.getCompteCod();
		libelleCptDb = selectedCpteDb.getLibelle();

	}

	public void chargerCompte() {
		this.listeCpte = new CompteModel().getListCompte(factory,rechLblCpt,rechCodCpt);
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

	public void getSelecedCptDbValue() {
		if (selectedCpteDb != null) {

			getCptDbValues();
			PrimeFaces.current().executeScript("PF('dlgCptDb').hide();");
		}
	}

	public void getSelecedCptFnValue() {
		if (selectedCpteFn != null) {

			getCptFnValues();
			PrimeFaces.current().executeScript("PF('dlgCptFn').hide();");
		}
	}

	@SuppressWarnings("deprecation")
	public void printExcel() {

		List<Compte> listCompte = new CompteModel().getPlanComptable(factory, compteDeb, compteFin);

		if (listCompte.size() > 0) {
			HSSFWorkbook wb = new HSSFWorkbook();
			HSSFSheet sheet = wb.createSheet("Grand livre");
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

			row.createCell(0).setCellValue("Date");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(0).setCellStyle(cellStyle);

			row.createCell(1).setCellValue("Journal");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(1).setCellStyle(cellStyle);

			row.createCell(2).setCellValue("Pièce");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(2).setCellStyle(cellStyle);

			row.createCell(3).setCellValue("Libellé");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(3).setCellStyle(cellStyle);

			row.createCell(4).setCellValue("Débit");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(4).setCellStyle(cellStyle);

			row.createCell(5).setCellValue("Crédit");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(5).setCellStyle(cellStyle);

			double subTotDeb = 0, subTotCrd = 0, totCred = 0, totDeb = 0;
			int i = 1, k = 0;

			List<Ecriture> listeEcriture = model.getListEcritureCompteBetween(factory, selecetdExercice.getId(),
					compteDeb, compteFin, dateDebut, dateFin);

			for (Compte cpte : listCompte) {

			
				subTotDeb = 0;
				subTotCrd = 0;
				if (compteUsed(cpte.getCompteCod(), listeEcriture)) {
					
					row = sheet.createRow(i);
					row.createCell(0).setCellValue(cpte.getCompteCod() + " " + cpte.getLibelle());
					sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 5));
                    row.getCell(0).setCellStyle(style2);
                    
					if (listeEcriture.size() > 0) {

						for (Ecriture ecr : listeEcriture) {

							if (ecr.getCpte().equals(cpte.getCompteCod())) {
								i++;

								row = sheet.createRow(i);
								HSSFCell cellE = null;

								row.createCell(0).setCellValue(HelperC.convertDate(ecr.getDateOperation()));

								row.createCell(1).setCellValue(ecr.getJrnl());

								row.createCell(2).setCellValue(ecr.getPieceCpb());

								row.createCell(3).setCellValue(ecr.getLibelle());

								row.createCell(4).setCellValue(ecr.getDebit());
								cellE = row.getCell(4);
								cellE.setCellType(0);
								row.getCell(4).setCellStyle(style);

								row.createCell(5).setCellValue(ecr.getCredit());
								cellE = row.getCell(5);
								cellE.setCellType(0);
								row.getCell(5).setCellStyle(style);

								subTotCrd += ecr.getCredit();
								subTotDeb += ecr.getDebit();

								totCred += subTotCrd;
								totDeb += subTotDeb;
							}
						}

						i++;

						

						row = sheet.createRow(i);
						row.createCell(0).setCellValue("Total pour le compte " + cpte.getCompteCod());
						sheet.addMergedRegion(new CellRangeAddress(i, i, 0, 3));
						row.getCell(0).setCellStyle(style2);

						row.createCell(4).setCellValue(subTotDeb);
						// row.getCell(3).setCellType(0);
						row.getCell(4).setCellStyle(style2);

						row.createCell(5).setCellValue(subTotCrd);
						// row.getCell(3).setCellType(0);
						row.getCell(5).setCellStyle(style2);

						i++;
					}
				}
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

			row.createCell(4).setCellValue(totDeb);
			cellT = row.getCell(4);
			cellT.setCellType(0);
			row.getCell(4).setCellStyle(style1);

			row.createCell(5).setCellValue(totCred);
			cellT = row.getCell(5);
			cellT.setCellType(0);
			row.getCell(5).setCellStyle(style1);

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
			res.setContentType("application/vnd.ms-excel");
			res.setHeader("Content-disposition", "attachment;filename=grandLivre.xls");

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

		String period="",cpte="";
		
		if(dateDebut!=null)
			period+=" Date debut :"+HelperC.convertDate(dateDebut);
		if(dateFin!=null)
			period+="  Date fin :"+HelperC.convertDate(dateFin);
		
		if(!compteDeb.equals(""))
			cpte+=" Compte début : "+compteDeb;
		if(!compteFin.equals(""))
			cpte+="  Compte fin : "+compteFin;
		
		cell = HelperItext.getPdfCell(period,
				FontFactory.getFont("Times-Roman", 8.0F, 0, BaseColor.BLACK), 1, 3, 0, 4, BaseColor.WHITE,
				BaseColor.WHITE, 1);
		table.addCell(cell);

		cell = HelperItext.getPdfCell(cpte, FontFactory.getFont("Times-Roman", 8.0F, 0, BaseColor.BLACK), 1, 3, 0, 4,
				BaseColor.WHITE, BaseColor.WHITE, 1);
		table.addCell(cell);

		cell = HelperItext.getPdfCell("", FontFactory.getFont("Times-Roman", 12.0F, 1, BaseColor.BLACK), 1, 1, 0, 4,
				BaseColor.WHITE, BaseColor.WHITE, 1);
		table.addCell(cell);

		cell = HelperItext.getPdfCell(titre+"\n\n", FontFactory.getFont("Times-Roman", 12.0F, 1, BaseColor.BLACK), 1, 1, 0, 2,
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
			Document doc = new Document(PageSize.A4);
			ByteArrayOutputStream docMem = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(doc, docMem);
			doc.addAuthor("Gatech");
			doc.addProducer();
			doc.addCreationDate();
			doc.addTitle("grandLivre");
			doc.open();
			ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
					.getContext();
			image = Image.getInstance(String.valueOf(servletContext.getRealPath("/resources")) + "\\Images\\" + "logo.png");
			
			image.scaleAbsoluteHeight(90.0F);
			image.scaleAbsoluteWidth(90.0F);
			doc.add((Element) image);
			writer.setPageEvent((PdfPageEvent) new ItextFooterHelper(
					new Phrase("Produit Gatech                          "+HelperC.convertDateHeureMin(Calendar.getInstance().getTime()), new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 0))));

			doc.add((Element) pageHeader("GRAND LIVRE \n"));
			doc.add((Element) grandLivre());

			doc.close();

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
			res.setHeader("Cache-Control", "Max-age=100");
			res.setContentType("application/pdf");
			res.setHeader("content-disposition", (new StringBuilder("online;filename=GrandLivre.pdf")).toString());
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
	private PdfPTable grandLivre() throws DocumentException {
		
		PdfPTable tabInfo = new PdfPTable(6);
		int widthsInfo[] = { 4, 4, 5,15,8,8 };
		tabInfo.setWidthPercentage(105F);
		tabInfo.setWidths(widthsInfo);
		
		List<Compte> listCompte = new CompteModel().getPlanComptable(factory, compteDeb, compteFin);
		List<Ecriture> listeEcriture = model.getListEcritureCompteBetween(factory, selecetdExercice.getId(),
				compteDeb, compteFin, dateDebut, dateFin);
		
		double subTotDeb = 0, subTotCrd = 0, totCred = 0, totDeb = 0,soldeDb=0,soldeCrd=0,totSoldDeb=0,totSoldCrd=0;
		int nbr = 0;
		Phrase phrase = null;
		Paragraph p = null;
		PdfPCell cell = new PdfPCell();

		

		if (listCompte.size() > 0) {
			phrase = new Phrase();
			phrase.add(new Chunk("Date ", FontFactory.getFont("Times", 8F, 1)));
			cell = HelperItext.getCellule(phrase, 1, 0, 7, 0, 0.5F, 3F,true);
			tabInfo.addCell(cell);

			phrase = new Phrase();
			phrase.add(new Chunk("Journal ", FontFactory.getFont("Times", 8F, 1)));
			cell = HelperItext.getCellule(phrase, 1, 0, 7, 0, 0.5F, 3F,true);
			tabInfo.addCell(cell);

			phrase = new Phrase();
			phrase.add(new Chunk("Pièce", FontFactory.getFont("Times", 8F, 1)));
			cell = HelperItext.getCellule(phrase, 1, 0, 7, 0, 0.5F, 3F,true);
			tabInfo.addCell(cell);

			phrase = new Phrase();
			phrase.add(new Chunk("Libellé ", FontFactory.getFont("Times", 8F, 1)));
			cell = HelperItext.getCellule(phrase, 1, 0, 7, 0, 0.5F, 3F,true);
			tabInfo.addCell(cell);

			phrase = new Phrase();
			phrase.add(new Chunk("Débit ", FontFactory.getFont("Times", 8F, 1)));
			cell = HelperItext.getCellule(phrase, 1, 0, 7, 0, 0.5F, 3F,true);
			tabInfo.addCell(cell);

			phrase = new Phrase();
			phrase.add(new Chunk("Crédit ", FontFactory.getFont("Times", 8F, 1)));
			cell = HelperItext.getCellule(phrase, 1, 0, 15, 0, 0.5F, 3F,true);
			tabInfo.addCell(cell);

			tabInfo.setHeaderRows(1);

			for (Compte cpte : listCompte) {
				
				subTotDeb = 0;
				subTotCrd = 0;
				soldeDb=0;
				soldeCrd=0;
				
				if (compteUsed(cpte.getCompteCod(), listeEcriture)) {

					p = new Paragraph();
					p.add(new Chunk(cpte.getCompteCod()+" "+cpte.getLibelle(), FontFactory.getFont("Times", 8F, 1)));
					cell = HelperItext.getCellule(p, 1, 0, 14, 6, 0.5F, 3F,true);
					tabInfo.addCell(cell);

					for (Ecriture ecr : listeEcriture) {

						if (ecr.getCpte().equals(cpte.getCompteCod())) {
							
							subTotCrd += ecr.getCredit();
							subTotDeb += ecr.getDebit();

							
							
								p = new Paragraph();
								p.add(new Chunk(HelperC.convertDate(ecr.getDateOperation()), FontFactory.getFont("Times", 8F, 0)));
								cell = HelperItext.getCellule(p, 1, 0, 6, 0, 0.5F, 3F,true);
								tabInfo.addCell(cell);

								p = new Paragraph();
								p.add(new Chunk(ecr.getJrnl(), FontFactory.getFont("Times", 8F, 0)));
								cell = HelperItext.getCellule(p, 1, 0, 6, 0, 0.5F, 3F,true);
								tabInfo.addCell(cell);

								p = new Paragraph();
								p.add(new Chunk(ecr.getPieceCpb(), FontFactory.getFont("Times", 8F, 0)));
								cell = HelperItext.getCellule(p, 1, 0, 6, 0, 0.5F, 3F,true);
								tabInfo.addCell(cell);

								p = new Paragraph();
								p.add(new Chunk(ecr.getLibelle(),
										FontFactory.getFont("Times", 8F, 0)));
						
								cell = HelperItext.getCellule(p, 1, 0, 6, 0, 0.5F, 3F,true);
								tabInfo.addCell(cell);
								
								p = new Paragraph();
								p.add(new Chunk(HelperC.decimalNumber(ecr.getDebit(), 0, true),
										FontFactory.getFont("Times", 8F, 0)));
								p.setAlignment(2);
								cell = HelperItext.getCellule(p, 1, 0, 14, 0, 0.5F, 3F,true);
								tabInfo.addCell(cell);
								
								p = new Paragraph();
								p.add(new Chunk(HelperC.decimalNumber(ecr.getCredit(), 0, true),
										FontFactory.getFont("Times", 8F, 0)));
								p.setAlignment(2);
								cell = HelperItext.getCellule(p, 1, 0, 14, 0, 0.5F, 3F,true);
								tabInfo.addCell(cell);
							}
						}
					
					if(subTotDeb >=subTotCrd)
						soldeDb=subTotDeb-subTotCrd;
					
					if(subTotDeb <subTotCrd)
						soldeCrd=subTotCrd-subTotDeb;
					
					totCred += subTotCrd;
					totDeb += subTotDeb;
					
					p = new Paragraph();
					p.add(new Chunk("Total " + cpte.getCompteCod()+" "+cpte.getLibelle(), FontFactory.getFont("Times", 8F, 1)));
					cell = HelperItext.getCellule(p, 1, 0, 6, 4, 0.5F, 3F,true);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add(new Chunk(HelperC.decimalNumber(subTotDeb, 0, true), FontFactory.getFont("Times", 8F, 1)));
					p.setAlignment(2);
					cell = HelperItext.getCellule(p, 1, 0, 6, 0, 0.5F, 3F,true);
					tabInfo.addCell(cell);
					
					p = new Paragraph();
					p.add(new Chunk(HelperC.decimalNumber(subTotCrd, 0, true), FontFactory.getFont("Times", 8F, 1)));
					p.setAlignment(2);
					cell = HelperItext.getCellule(p, 1, 0,14, 0, 0.5F, 3F,true);
					tabInfo.addCell(cell);
						
					
					p = new Paragraph();
					p.add(new Chunk("Solde " , FontFactory.getFont("Times", 8F, 1)));
					cell = HelperItext.getCellule(p, 1, 0, 6, 4, 0.5F, 3F,true);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add(new Chunk(HelperC.decimalNumber(soldeDb, 0, true), FontFactory.getFont("Times", 8F, 1)));
					p.setAlignment(2);
					cell = HelperItext.getCellule(p, 1, 0, 6, 0, 0.5F, 3F,true);
					tabInfo.addCell(cell);
					
					p = new Paragraph();
					p.add(new Chunk(HelperC.decimalNumber(soldeCrd, 0, true), FontFactory.getFont("Times", 8F, 1)));
					p.setAlignment(2);
					cell = HelperItext.getCellule(p, 1, 0,14, 0, 0.5F, 3F,true);
					tabInfo.addCell(cell);
						
					}

					
				}
			
			if(totDeb>totCred)
				totSoldDeb=totDeb-totCred;
			if(totCred>totDeb)
				totSoldCrd=totCred-totDeb;
			if(totSoldDeb==totSoldCrd)
			{
				totSoldCrd=0;
				totSoldDeb=0;
			}
			p = new Paragraph();
			p.add(new Chunk("Total général ", FontFactory.getFont("Times", 8F, 1)));
			cell = HelperItext.getCellule(p, 1, 0, 6, 4, 0.5F, 3F,true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add(new Chunk(HelperC.decimalNumber(totDeb, 0, true), FontFactory.getFont("Times", 8F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule(p, 1, 0, 6, 0, 0.5F, 3F,true);
			tabInfo.addCell(cell);
			
			p = new Paragraph();
			p.add(new Chunk(HelperC.decimalNumber(totCred, 0, true), FontFactory.getFont("Times", 8F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule(p, 1, 0,14, 0, 0.5F, 3F,true);
			tabInfo.addCell(cell);
				
			
			p = new Paragraph();
			p.add(new Chunk("Solde " , FontFactory.getFont("Times", 8F, 1)));
			cell = HelperItext.getCellule(p, 1, 0, 6, 4, 0.5F, 3F,true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add(new Chunk(HelperC.decimalNumber(totSoldCrd, 0, true), FontFactory.getFont("Times", 8F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule(p, 1, 0, 6, 0, 0.5F, 3F,true);
			tabInfo.addCell(cell);
			
			p = new Paragraph();
			p.add(new Chunk(HelperC.decimalNumber(totSoldDeb, 0, true), FontFactory.getFont("Times", 8F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule(p, 1, 0,14, 0, 0.5F, 3F,true);
			tabInfo.addCell(cell);
				
				
			}
			
			
		
			 
			return tabInfo;
		}
		
	
	
	private boolean compteUsed(String cpt, List<Ecriture> list) {
		boolean used = false;
		for (Ecriture ecr : list) {
			if (ecr.getCpte().equals(cpt)) {
				used = true;
				break;
			}
		}
		return used;
	}
}
