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
import entite.ParametreCompta;
import entite.User;
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
import model.CompteModel;
import model.EcritureModel;
import model.ExerciceModel;
import model.ParametreComptaModel;
import model.UserModel;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.hibernate.SessionFactory;
import persistances.DBConfiguration;
import utils.HelperC;
import utils.HelperItext;
import utils.ItextFooterHelper;

@ManagedBean
@ViewScoped
public class Balance implements Serializable {
	private int typeBalance, typeAffichage;
	private String rechDateDeb;
	private String rechDateFin;
	private String infoMsg;
	private Date dateDebut;
	private Date dateFin;
	private static final long serialVersionUID = -5644443502787775036L;
	SessionFactory factory = DBConfiguration.getSessionFactory();

	Exercice selecetdExercice;

	HttpSession session;

	String exerCode;

	String currUserCode;

	User currentUser;
	List<Object[]> listSolde;
	List<Object[]> listSoldeAn;
	List<Compte> listeCpte;
	ParametreCompta prmCpt;
	String codeJAN,codeJC;
	String resultAccount="";
	
	public int getTypeBalance() {
		return this.typeBalance;
	}

	public void setTypeBalance(int typeBalance) {
		this.typeBalance = typeBalance;
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

	public String getInfoMsg() {
		return this.infoMsg;
	}

	public void setInfoMsg(String infoMsg) {
		this.infoMsg = infoMsg;
	}

	public int getTypeAffichage() {
		return typeAffichage;
	}

	public void setTypeAffichage(int typeAffichage) {
		this.typeAffichage = typeAffichage;
	}

	@PostConstruct
	public void initialize() {
		chargementSession();
		this.prmCpt = (new ParametreComptaModel()).getParameter(this.factory);
		if (this.prmCpt != null) {
			codeJAN = this.prmCpt.getJournalAN();
			resultAccount=this.prmCpt.getCompteRs();
			codeJC=prmCpt.getJournalOD();
		} else {
			codeJAN = "";
			resultAccount="";
		}
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
					FacesContext.getCurrentInstance().getExternalContext().redirect("/gestionCompta/login.xhtml");
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}
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
		
		/*
		 * if(!compteDeb.equals("")) cpte+=" Compte début : "+compteDeb;
		 * if(!compteFin.equals("")) cpte+="  Compte fin : "+compteFin;
		 */
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

	public void printBalancePdf() {
		try {
			Image image = null;
			Document doc = new Document(PageSize.A4.rotate());
			ByteArrayOutputStream docMem = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(doc, docMem);
			doc.addAuthor("Gatech");
			doc.addProducer();
			doc.addCreationDate();
			doc.addTitle("Balance");
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

			doc.add((Element) pageHeader("BALANCE : EXERCICE "+selecetdExercice.getExCode()));
			switch (this.typeBalance) {
			case 1:
				doc.add((Element) getBalance4());
				break;

			case 2:
				doc.add((Element) getBalance6());
				break;
			}

			doc.close();

			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
			res.setHeader("Cache-Control", "Max-age=100");
			res.setContentType("application/pdf");
			res.setHeader("content-disposition", (new StringBuilder("online;filename=Balance.pdf")).toString());
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

	public void printeBalanceExcel() {
		switch (this.typeBalance) {
		case 1:
			printBalance4Excel();
			break;

		case 2:
			printBalance6Excel();
			break;
		}
	}

	private PdfPTable getBalance4() throws DocumentException {
		PdfPTable tabInfo = new PdfPTable(6);
		int[] widthsInfo = {8, 30, 10, 10, 10, 10 };
		tabInfo.setWidthPercentage(100.0F);
		tabInfo.setWidths(widthsInfo);
		Paragraph p = null;
		PdfPCell cell = new PdfPCell();

		double totDeb = 0.0D, totCrd = 0.0D, deb = 0.0D, crd = 0.0D, soldDb = 0.0D, soldCd = 0.0D, totSoldDb = 0.0D,
				totSoldCd = 0.0D,result=0.0D;
		chargerCompte();
		chargementSolde();

		if (this.listeCpte.size() > 0 && this.listSolde.size() > 0) {

			
			  p = new Paragraph(); p.add((Element) new Chunk("",
			  FontFactory.getFont("Times", 10.0F, 1)));
			  p.setAlignment(1); 
			  cell = HelperItext.getCellule((Element) p, 1, 0, 0, 2, 0.5F, 3.0F, true);
			  tabInfo.addCell(cell);
			 
			p = new Paragraph();
			p.add((Element) new Chunk("Mouvement periode", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 13, 2, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Soldes", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 9, 2, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);
			/*
			 * p = new Paragraph(); p.add((Element) new Chunk("",
			 * FontFactory.getFont("Times", 10.0F, 1))); p.setAlignment(1); cell =
			 * HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			 * tabInfo.addCell(cell);
			 */
			p = new Paragraph();
			p.add((Element) new Chunk("Compte", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Libellé", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Débit", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Crédit ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Débit", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Crédit ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 15, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			tabInfo.setHeaderRows(2);

			int j = 0;

			for (Compte cpte : this.listeCpte) {
				j++;

				if(cpte.getCompteCod().contentEquals(resultAccount))
				{
					result=calculResultat();
					if(result>0)
						deb=result;
					if(result<0)
						crd=Math.abs(result);
				}
				else 
				{
				deb = getDebit(cpte.getCompteCod());
				crd = getCredit(cpte.getCompteCod());
				}
				
				if (deb > crd)
					soldDb = deb - crd;
				if (crd > deb)
					soldCd = crd - deb;
				if (deb == crd) {

					soldCd = deb - crd;
					soldDb = deb - crd;
				}

				if (deb > 0.0D || crd > 0.0D) {
					/*
					 * p = new Paragraph(); p.add((Element) new Chunk("" + j,
					 * FontFactory.getFont("Times", 8.0F, 0))); p.setAlignment(0); cell =
					 * HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
					 * tabInfo.addCell(cell);
					 */

					p = new Paragraph();
					p.add((Element) new Chunk(cpte.getCompteCod(), FontFactory.getFont("Times", 8.0F, 0)));
					p.setAlignment(0);
					cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add((Element) new Chunk(cpte.getLibelle(), FontFactory.getFont("Times", 8.0F, 0)));
					p.setAlignment(0);
					cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add((Element) new Chunk(HelperC.decimalNumber(deb, 0, true),
							FontFactory.getFont("Times", 8.0F, 0)));
					p.setAlignment(2);
					cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add((Element) new Chunk(HelperC.decimalNumber(crd, 0, true),
							FontFactory.getFont("Times", 8.0F, 0)));
					p.setAlignment(2);
					cell = HelperItext.getCellule((Element) p, 1, 2, 6, 0, 0.5F, 3.0F, true);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add((Element) new Chunk(HelperC.decimalNumber(soldDb, 0, true),
							FontFactory.getFont("Times", 8.0F, 0)));
					p.setAlignment(2);
					cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add((Element) new Chunk(HelperC.decimalNumber(soldCd, 0, true),
							FontFactory.getFont("Times", 8.0F, 0)));
					p.setAlignment(2);
					cell = HelperItext.getCellule((Element) p, 1, 0, 14, 0, 0.5F, 3.0F, true);
					tabInfo.addCell(cell);
					
					if(compteUsed(cpte.getCompteCod(),listSolde))
					{
						
					totCrd += crd;
					totDeb += deb;
					
					totSoldDb += soldDb;
					totSoldCd += soldCd;
					}
				}

				
				deb = 0.0D;
				crd = 0.0D;
				soldCd = 0.0D;
				soldDb = 0.0D;
			}

			p = new Paragraph();
			p.add((Element) new Chunk("Total ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(0);
			cell = HelperItext.getCellule((Element) p, 1, 0, 6, 2, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk(HelperC.decimalNumber(totDeb, 0, true), FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk(HelperC.decimalNumber(totCrd, 0, true), FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule((Element) p, 1, 2, 6, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk(HelperC.decimalNumber(totSoldDb, 0, true),
					FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk(HelperC.decimalNumber(totSoldCd, 0, true),
					FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule((Element) p, 1, 2, 14, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);
		}

		return tabInfo;
	}

	private PdfPTable getBalance6() throws DocumentException {
		PdfPTable tabInfo = new PdfPTable(8);
		int[] widthsInfo = {7,25, 10, 10, 10, 10, 10, 10 };
		tabInfo.setWidthPercentage(105.0F);
		tabInfo.setWidths(widthsInfo);
		Paragraph p = null;
		PdfPCell cell = new PdfPCell();

		double totDeb = 0.0D, totCrd = 0.0D, deb = 0.0D, crd = 0.0D, soldDb = 0.0D, soldCd = 0.0D;
		double totSoldDb = 0.0D, totSoldCd = 0.0D, credEnt = 0.0D, debEntre = 0.0D;
		double totCrdE = 0.0D, totDebE = 0.0D,result=0;
		boolean used=false;
		chargerCompte();
		chargerSoldeEntree();
		chargerSoldePeriode();

		if (this.listeCpte.size() > 0 && this.listSolde.size() > 0) {

			
			  p = new Paragraph(); p.add((Element) new Chunk("",
			  FontFactory.getFont("Times", 10.0F, 1))); 
			  p.setAlignment(1); 
			  cell = HelperItext.getCellule((Element) p, 1, 0, 0, 2, 0.5F, 3.0F, true);
			  tabInfo.addCell(cell);
			 
			p = new Paragraph();
			p.add((Element) new Chunk("Solde d'entree", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 13, 2, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Mouvement periode", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 9, 2, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Soldes", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 9, 2, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			/*
			 * p = new Paragraph(); p.add((Element) new Chunk("",
			 * FontFactory.getFont("Times", 10.0F, 1))); p.setAlignment(1); cell =
			 * HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			 * tabInfo.addCell(cell);
			 */

			p = new Paragraph();
			p.add((Element) new Chunk("Compte", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Libellé", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Débit", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Crédit ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Débit", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Crédit ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Débit", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 7, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk("Crédit ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(1);
			cell = HelperItext.getCellule((Element) p, 1, 0, 15, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			tabInfo.setHeaderRows(2);

			int j = 0;

			for (Compte cpte : this.listeCpte) {
				j++;

				if(cpte.getCompteCod().contentEquals(resultAccount))
				{
					result=calculResultat();
					if(result>0)
						deb=result;
					if(result<0)
						crd=Math.abs(result);
				}
				else 
				{
				deb = getDebit(cpte.getCompteCod());
				crd = getCredit(cpte.getCompteCod());
				}
				debEntre = getDebitEntree(cpte.getCompteCod());
				credEnt = getCreditEntree(cpte.getCompteCod());

				if (deb + debEntre > crd + credEnt)
					soldDb = (deb + debEntre) - (crd + credEnt);
				if (deb + debEntre < crd + credEnt) {
					soldCd = (crd + credEnt) - (deb + debEntre);
				}
				if (deb + debEntre == crd + credEnt) {

					soldCd = 0.0D;
					soldDb = 0.0D;
				}

				if (deb > 0.0D || crd > 0.0D || debEntre > 0.0D || credEnt > 0.0D||soldCd>0||soldDb>0) {

					/*
					 * p = new Paragraph(); p.add((Element) new Chunk("" + j,
					 * FontFactory.getFont("Times", 8.0F, 0))); p.setAlignment(0); cell =
					 * HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
					 * tabInfo.addCell(cell);
					 */
					p = new Paragraph();
					p.add((Element) new Chunk(cpte.getCompteCod(), FontFactory.getFont("Times", 8.0F, 0)));
					p.setAlignment(0);
					cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add((Element) new Chunk(cpte.getLibelle(), FontFactory.getFont("Times", 8.0F, 0)));
					p.setAlignment(0);
					cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add((Element) new Chunk(HelperC.decimalNumber(debEntre, 0, true),
							FontFactory.getFont("Times", 8.0F, 0)));
					p.setAlignment(2);
					cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add((Element) new Chunk(HelperC.decimalNumber(credEnt, 0, true),
							FontFactory.getFont("Times", 8.0F, 0)));
					p.setAlignment(2);
					cell = HelperItext.getCellule((Element) p, 1, 2, 6, 0, 0.5F, 3.0F, true);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add((Element) new Chunk(HelperC.decimalNumber(deb, 0, true),
							FontFactory.getFont("Times", 8.0F, 0)));
					p.setAlignment(2);
					cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add((Element) new Chunk(HelperC.decimalNumber(crd, 0, true),
							FontFactory.getFont("Times", 8.0F, 0)));
					p.setAlignment(2);
					cell = HelperItext.getCellule((Element) p, 1, 2, 6, 0, 0.5F, 3.0F, true);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add((Element) new Chunk(HelperC.decimalNumber(soldDb, 0, true),
							FontFactory.getFont("Times", 8.0F, 0)));
					p.setAlignment(2);
					cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
					tabInfo.addCell(cell);

					p = new Paragraph();
					p.add((Element) new Chunk(HelperC.decimalNumber(soldCd, 0, true),
							FontFactory.getFont("Times", 8.0F, 0)));
					p.setAlignment(2);
					cell = HelperItext.getCellule((Element) p, 1, 0, 14, 0, 0.5F, 3.0F, true);
					tabInfo.addCell(cell);
					
					if(compteUsed(cpte.getCompteCod(),listSolde))
					{
					totCrd += crd;
					totDeb += deb;
					used=true;
					}
					if(compteUsed(cpte.getCompteCod(),listSoldeAn))
					{
					totDebE += debEntre;
					totCrdE += credEnt;
					used=true;
					}
					if(used)
					{
					totSoldDb += soldDb;
					totSoldCd += soldCd;
					}
				}
				
				
				used=false;
				deb = 0.0D;
				crd = 0.0D;
				debEntre = 0.0D;
				credEnt = 0.0D;
				soldCd = 0.0D;
				soldDb = 0.0D;
			}

			p = new Paragraph();
			p.add((Element) new Chunk("Total ", FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(0);
			cell = HelperItext.getCellule((Element) p, 1, 0, 6, 2, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk(HelperC.decimalNumber(totDebE, 0, true), FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk(HelperC.decimalNumber(totCrdE, 0, true), FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule((Element) p, 1, 2, 6, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk(HelperC.decimalNumber(totDeb, 0, true), FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk(HelperC.decimalNumber(totCrd, 0, true), FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule((Element) p, 1, 2, 6, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk(HelperC.decimalNumber(totSoldDb, 0, true),
					FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule((Element) p, 1, 0, 6, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);

			p = new Paragraph();
			p.add((Element) new Chunk(HelperC.decimalNumber(totSoldCd, 0, true),
					FontFactory.getFont("Times", 10.0F, 1)));
			p.setAlignment(2);
			cell = HelperItext.getCellule((Element) p, 1, 2, 14, 0, 0.5F, 3.0F, true);
			tabInfo.addCell(cell);
		}

		return tabInfo;
	}

	private void printBalance4Excel() {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Balance4c");
		HSSFRow row = null;
		HSSFCellStyle cellStyle = null;
		HSSFCellStyle style = null, style1 = null;

		cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor((short) 29);
		cellStyle.setFillPattern((short) 1);

		style = wb.createCellStyle();

		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

		chargerCompte();
		chargementSolde();

		double totDeb = 0.0D, totCrd = 0.0D, deb = 0.0D, crd = 0.0D, soldDb = 0.0D, soldCd = 0.0D, totSoldDb = 0.0D,
				totSoldCd = 0.0D,result=0;
		boolean bl = false;

		if (this.listeCpte.size() > 0 && this.listSolde.size() > 0) {

			row = sheet.createRow(0);

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

			row.createCell(4).setCellValue("solde débiteur ");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(4).setCellStyle(cellStyle);

			row.createCell(5).setCellValue("Solde créditeur");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(5).setCellStyle(cellStyle);

			int i = 0;

			for (Compte cpte : this.listeCpte) {

				if(cpte.getCompteCod().contentEquals(resultAccount))
				{
					result=calculResultat();
					if(result>0)
						deb=result;
					if(result<0)
						crd=Math.abs(result);
				}
				else 
				{
				deb = getDebit(cpte.getCompteCod());
				crd = getCredit(cpte.getCompteCod());
				}
				if (deb > crd)
					soldDb = deb - crd;
				if (crd > deb)
					soldCd = crd - deb;
				if (deb == crd) {

					soldCd = deb - crd;
					soldDb = deb - crd;
				}

				if (deb > 0.0D || crd > 0.0D) {

					i++;
					row = sheet.createRow(i);
					HSSFCell cellE = null;

					row.createCell(0).setCellValue(cpte.getCompteCod());

					row.createCell(1).setCellValue(cpte.getLibelle());

					row.createCell(2).setCellValue(deb);
					cellE = row.getCell(2);
					cellE.setCellType(0);
					row.getCell(2).setCellStyle(style);

					row.createCell(3).setCellValue(crd);
					cellE = row.getCell(3);
					cellE.setCellType(0);
					row.getCell(3).setCellStyle(style);

					row.createCell(4).setCellValue(soldDb);
					cellE = row.getCell(4);
					cellE.setCellType(0);
					row.getCell(4).setCellStyle(style);

					row.createCell(5).setCellValue(soldCd);
					cellE = row.getCell(5);
					cellE.setCellType(0);
					row.getCell(5).setCellStyle(style);

					if(compteUsed(cpte.getCompteCod(),listSolde))
					{
					totCrd += crd;
					totDeb += deb;
					totSoldDb += soldDb;
					totSoldCd += soldCd;
					}
					
				}
				deb = 0.0D;
				crd = 0.0D;
				soldCd = 0.0D;
				soldDb = 0.0D;
			}

			HSSFCell cellT = null;
			style1 = wb.createCellStyle();
			style1.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
			style1.setFillForegroundColor((short) 22);
			style1.setFillPattern((short) 1);

			row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue("TOTAL :");
			sheet.addMergedRegion(new CellRangeAddress(i + 1, i + 1, 0, 1));
			row.getCell(0).setCellStyle(style1);

			row.createCell(2).setCellValue(totDeb);
			cellT = row.getCell(2);
			cellT.setCellType(0);
			row.getCell(2).setCellStyle(style1);

			row.createCell(3).setCellValue(totCrd);
			cellT = row.getCell(3);
			cellT.setCellType(0);
			row.getCell(3).setCellStyle(style1);

			row.createCell(4).setCellValue(totSoldDb);
			cellT = row.getCell(4);
			cellT.setCellType(0);
			row.getCell(4).setCellStyle(style1);

			row.createCell(5).setCellValue(totSoldCd);
			cellT = row.getCell(5);
			cellT.setCellType(0);
			row.getCell(5).setCellStyle(style1);
		}
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
		res.setContentType("application/vnd.ms-excel");
		res.setHeader("Content-disposition", "attachment;filename=balance4.xls");

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

	private void printBalance6Excel() {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("Balance6c");
		HSSFRow row = null;
		HSSFCellStyle cellStyle = null;
		HSSFCellStyle style = null, style1 = null;

		cellStyle = wb.createCellStyle();
		cellStyle.setFillForegroundColor((short) 29);
		cellStyle.setFillPattern((short) 1);

		style = wb.createCellStyle();

		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));

		chargerCompte();
		chargerSoldePeriode();
		chargerSoldeEntree();

		double totDeb = 0.0D, totCrd = 0.0D, deb = 0.0D, crd = 0.0D, soldDb = 0.0D, soldCd = 0.0D;
		double totSoldDb = 0.0D, totSoldCd = 0.0D, credEnt = 0.0D, debEntre = 0.0D;
		double totCrdE = 0.0D, totDebE = 0.0D,result=0;

		boolean used=false;

		if (this.listeCpte.size() > 0 && this.listSolde.size() > 0) {

			row = sheet.createRow(0);

			row.createCell(0).setCellValue("Compte");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(0).setCellStyle(cellStyle);

			row.createCell(1).setCellValue("Libellé");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(1).setCellStyle(cellStyle);

			row.createCell(2).setCellValue("Entrée Débit");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(2).setCellStyle(cellStyle);

			row.createCell(3).setCellValue("Entrée Crédit");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(3).setCellStyle(cellStyle);

			row.createCell(4).setCellValue("Mouvement Débit");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(4).setCellStyle(cellStyle);

			row.createCell(5).setCellValue("Mouvement Crédit");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(5).setCellStyle(cellStyle);

			row.createCell(6).setCellValue("solde débiteur ");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(6).setCellStyle(cellStyle);

			row.createCell(7).setCellValue("Solde créditeur");
			cellStyle.setFillForegroundColor((short) 13);
			cellStyle.setFillPattern((short) 1);
			row.getCell(7).setCellStyle(cellStyle);

			int i = 0;

			for (Compte cpte : this.listeCpte) {

				if(cpte.getCompteCod().contentEquals(resultAccount))
				{
					result=calculResultat();
					if(result>0)
						deb=result;
					if(result<0)
						crd=Math.abs(result);
				}
				else 
				{
				deb = getDebit(cpte.getCompteCod());
				crd = getCredit(cpte.getCompteCod());
				}
				debEntre = getDebitEntree(cpte.getCompteCod());
				credEnt = getCreditEntree(cpte.getCompteCod());

				if (deb + debEntre > crd + credEnt)
					soldDb = (deb + debEntre) - (crd + credEnt);
				if (deb + debEntre < crd + credEnt) {
					soldCd = (crd + credEnt) - (deb + debEntre);
				}
				if (deb + debEntre == crd + credEnt) {

					soldCd = 0.0D;
					soldDb = 0.0D;
				}

				if (deb > 0.0D || crd > 0.0D || debEntre > 0.0D || credEnt > 0.0D||soldCd>0||soldDb>0) {

					i++;
					row = sheet.createRow(i);
					HSSFCell cellE = null;

					row.createCell(0).setCellValue(cpte.getCompteCod());

					row.createCell(1).setCellValue(cpte.getLibelle());

					row.createCell(2).setCellValue(debEntre);
					cellE = row.getCell(2);
					cellE.setCellType(0);
					row.getCell(2).setCellStyle(style);

					row.createCell(3).setCellValue(credEnt);
					cellE = row.getCell(3);
					cellE.setCellType(0);
					row.getCell(3).setCellStyle(style);

					row.createCell(4).setCellValue(deb);
					cellE = row.getCell(4);
					cellE.setCellType(0);
					row.getCell(4).setCellStyle(style);

					row.createCell(5).setCellValue(crd);
					cellE = row.getCell(5);
					cellE.setCellType(0);
					row.getCell(5).setCellStyle(style);

					row.createCell(6).setCellValue(soldDb);
					cellE = row.getCell(6);
					cellE.setCellType(0);
					row.getCell(6).setCellStyle(style);

					row.createCell(7).setCellValue(soldCd);
					cellE = row.getCell(7);
					cellE.setCellType(0);
					row.getCell(7).setCellStyle(style);

					if(compteUsed(cpte.getCompteCod(),listSolde))
					{
					totCrd += crd;
					totDeb += deb;
					used=true;
				
					}
					if(compteUsed(cpte.getCompteCod(),listSoldeAn))
					{
					totDebE += debEntre;
					totCrdE += credEnt;
					used=true;
					}
					
					if(used)
					{
						totSoldDb += soldDb;
						totSoldCd += soldCd;
					}
				}
				deb = 0.0D;
				crd = 0.0D;
				debEntre = 0.0D;
				credEnt = 0.0D;
				soldCd = 0.0D;
				soldDb = 0.0D;
				used=false;
			}

			HSSFCell cellT = null;
			style1 = wb.createCellStyle();
			style1.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0"));
			style1.setFillForegroundColor((short) 22);
			style1.setFillPattern((short) 1);

			row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue("TOTAL :");
			sheet.addMergedRegion(new CellRangeAddress(i + 1, i + 1, 0, 1));
			row.getCell(0).setCellStyle(style1);

			row.createCell(2).setCellValue(totDebE);
			cellT = row.getCell(2);
			cellT.setCellType(0);
			row.getCell(2).setCellStyle(style1);

			row.createCell(3).setCellValue(totCrdE);
			cellT = row.getCell(3);
			cellT.setCellType(0);
			row.getCell(3).setCellStyle(style1);

			row.createCell(4).setCellValue(totDeb);
			cellT = row.getCell(4);
			cellT.setCellType(0);
			row.getCell(4).setCellStyle(style1);

			row.createCell(5).setCellValue(totCrd);
			cellT = row.getCell(5);
			cellT.setCellType(0);
			row.getCell(5).setCellStyle(style1);

			row.createCell(6).setCellValue(totSoldDb);
			cellT = row.getCell(6);
			cellT.setCellType(0);
			row.getCell(6).setCellStyle(style1);

			row.createCell(7).setCellValue(totSoldCd);
			cellT = row.getCell(7);
			cellT.setCellType(0);
			row.getCell(7).setCellStyle(style1);
		}
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletResponse res = (HttpServletResponse) context.getExternalContext().getResponse();
		res.setContentType("application/vnd.ms-excel");
		res.setHeader("Content-disposition", "attachment;filename=balance6.xls");

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

	public void chargerCompte() {
		listeCpte=null;
		this.listeCpte = new CompteModel().getListCompte(this.factory);
	}

	private void chargementSolde() {
		this.listSolde = (new EcritureModel()).getListEcriture(this.factory, this.selecetdExercice.getId(), "", "", "");
	}

	private void chargerSoldeEntree() {
		if (!this.codeJAN.equals(""))
			this.listSoldeAn = (new EcritureModel()).getBalanceEntree(this.factory, this.selecetdExercice.getId(),
					this.codeJAN, "1", "6");
	}

	private void chargerSoldePeriode() {
		this.listSolde = (new EcritureModel()).getBalancePeriode(this.factory, this.selecetdExercice.getId(),
				this.codeJAN, "", "");
	}

	private double getCredit(String compte) {
		double crd = 0.0D;
		String cpt = "";
		switch (typeAffichage) {
		case 1:
			for (Object[] ob : this.listSolde) {
				cpt = ob[0].toString();

				if (cpt.startsWith(compte)) {

					crd += Double.valueOf(ob[2].toString()).doubleValue();
				}
			}
			break;

		case 2:
			for (Object[] ob : this.listSolde) {
				cpt = ob[0].toString();

				if (cpt.equals(compte)) {

					crd += Double.valueOf(ob[2].toString()).doubleValue();
					break;
				}
			}
			break;
		}
		
		
		return crd;
	}

	private double getCreditEntree(String compte) {
		double crd = 0.0D;
		String cpt = "";
		switch (typeAffichage) {
		case 1:
			for (Object[] ob : this.listSoldeAn) {
				cpt = ob[0].toString();
				if (cpt.startsWith(compte)) {

					crd += Double.valueOf(ob[2].toString()).doubleValue();
					
				}
			}
			break;
		case 2:
			for (Object[] ob : this.listSoldeAn) {
				cpt = ob[0].toString();
				if (cpt.equals(compte)) {

					crd += Double.valueOf(ob[2].toString()).doubleValue();
					break;
				}
			}
			break;
		
		
		}
		return crd;
	}

	private double getDebit(String compte) {
		double deb = 0.0D;
		String cpt = "";
		switch (typeAffichage) {
		
		case 1:
			for (Object[] ob : this.listSolde) {
				cpt = ob[0].toString();

				if (cpt.startsWith(compte)) {

					deb += Double.valueOf(ob[1].toString()).doubleValue();
					
				}
			}
			break;
		case 2:
			for (Object[] ob : this.listSolde) {
				cpt = ob[0].toString();

				if (cpt.equals(compte)) {

					deb += Double.valueOf(ob[1].toString()).doubleValue();
					break;
				}
			}
			break;
		}
		
		return deb;
	}

	private double getDebitEntree(String compte) {
		double deb = 0.0D;
		String cpt = "";
		switch (typeAffichage) {
		case 1:
			for (Object[] ob : this.listSoldeAn) {
				cpt = ob[0].toString();

				if (cpt.startsWith(compte)) {

					deb += Double.valueOf(ob[1].toString()).doubleValue();
				
				}
			}
			break;
		case 2:
			for (Object[] ob : this.listSoldeAn) {
				cpt = ob[0].toString();

				if (cpt.equals(compte)) {

					deb += Double.valueOf(ob[1].toString()).doubleValue();
					break;
				}
			}
			break;
		}
		
		return deb;
	}
	
	private boolean compteUsed(String cpt, List<Object[]>list) {
		boolean used = false;
		
		for (Object[] ob : list) {
			if (cpt.equals(ob[0].toString())) {
				used = true;
				break;
			}
		}
		return used;
	}
	public double calculResultat() {
		
		List<Object[]> listeSolde = new EcritureModel().getListEcriture(this.factory, this.selecetdExercice.getId(), "", "6",
				"8");
	
		double solde = 0.0D, totPrd = 0.0D, totChg = 0.0D;
		double deb=0,crd=0;
		if (listeSolde.size() > 0) {

			
			for (Object[] objects : listeSolde) {

				if (objects[0].toString().startsWith("6")) {
					deb=Double.valueOf(objects[1].toString()).doubleValue();
					crd=Double.valueOf(objects[2].toString()).doubleValue();
					
					totChg =deb-crd;
				}
				if (objects[0].toString().startsWith("7")) {
					
					deb=Double.valueOf(objects[1].toString()).doubleValue();
					crd=Double.valueOf(objects[2].toString()).doubleValue();
					
					totPrd = crd-deb;
				}
				crd=0;deb=0;
				solde += totPrd - totChg;

				

				

				totChg = 0.0D;
				totPrd = 0.0D;
			}

			
			
		}
		return solde;
	}
}
