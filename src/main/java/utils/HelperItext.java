 package utils;
 
 import com.itextpdf.text.BadElementException;
 import com.itextpdf.text.BaseColor;
 import com.itextpdf.text.Document;
 import com.itextpdf.text.Element;
 import com.itextpdf.text.Font;
 import com.itextpdf.text.Image;
 import com.itextpdf.text.Paragraph;
 import com.itextpdf.text.Phrase;
 import com.itextpdf.text.Rectangle;
 import com.itextpdf.text.pdf.ColumnText;
 import com.itextpdf.text.pdf.PdfPCell;
 import com.itextpdf.text.pdf.PdfWriter;
 import java.io.Serializable;
 
 public class HelperItext
   implements Serializable
 {
   private static final long serialVersionUID = 4479545637647547695L;
   
   public static PdfPCell getCellulePdf(String label_cellule, Font format, int alignement_vertical, int alignement_horizontal, int bordure, int cellules_fusionnees, BaseColor couleur) throws BadElementException {
     PdfPCell cellule = null;
     Paragraph para = new Paragraph(label_cellule, format);
     cellule = new PdfPCell((Phrase)para);
     cellule.setVerticalAlignment(alignement_vertical);
     cellule.setHorizontalAlignment(alignement_horizontal);
     cellule.setBorder(bordure);
     cellule.setColspan(cellules_fusionnees);
     cellule.setBackgroundColor(couleur);
     cellule.setPaddingBottom(5.0F);
     return cellule;
   }
 
 
   
   public static PdfPCell getCellulePdfBarcode(Image label_cellule, int alignement_vertical, int alignement_horizontal, int bordure, int cellules_fusionnees, BaseColor couleur) throws BadElementException {
     PdfPCell cellule = null;
     cellule = new PdfPCell(label_cellule, true);
     cellule.setVerticalAlignment(alignement_vertical);
     cellule.setHorizontalAlignment(alignement_horizontal);
     cellule.setPadding(10.0F);
     cellule.setBorder(bordure);
     cellule.setColspan(cellules_fusionnees);
     cellule.setBackgroundColor(couleur);
     return cellule;
   }
 
 
   
   public static PdfPCell getPdfCellEntete(Element element, int alignmentVertical, int alignmentHorizontal, int border, int colspan, int borderwidth, BaseColor bordercolor, float cellPadding) throws BadElementException {
     PdfPCell cell = new PdfPCell();
     cell.addElement(element);
     if (border > 0) {
       
       cell.setBorder(border);
       cell.setBorderWidth(borderwidth);
       if (bordercolor != null)
       {
         cell.setBorderColor(bordercolor);
       }
     } else {
       
       cell.setBorder(0);
     } 
     cell.setVerticalAlignment(alignmentVertical);
     cell.setHorizontalAlignment(alignmentHorizontal);
     cell.setColspan(colspan);
     cell.setPadding(cellPadding);
     return cell;
   }
 
 
 
   
   public static PdfPCell getPdfCell(String label_cellule, Font font, int alignement_vertical, int alignement_horizontal, int bordure, int cellules_fusionnees, BaseColor couleur, BaseColor borderColor, int borderWidth) throws BadElementException {
     PdfPCell colonne = null;
     Paragraph para = new Paragraph(label_cellule, font);
     colonne = new PdfPCell((Phrase)para);
     if (alignement_vertical == 1) {
       
       colonne.setVerticalAlignment(1);
     }
     else if (alignement_vertical == 2) {
       
       colonne.setVerticalAlignment(0);
     }
     else if (alignement_vertical == 3) {
       
       colonne.setVerticalAlignment(2);
     } 
     if (alignement_horizontal == 1) {
       
       colonne.setHorizontalAlignment(1);
     }
     else if (alignement_horizontal == 2) {
       
       colonne.setHorizontalAlignment(0);
     }
     else if (alignement_horizontal == 3) {
       
       colonne.setHorizontalAlignment(2);
     } 
     colonne.setBorder(bordure);
     
     colonne.setColspan(cellules_fusionnees);
     colonne.setBackgroundColor(couleur);
     colonne.setPaddingBottom(5.0F);
     colonne.setBorderColor(borderColor);
     colonne.setBorderWidth(borderWidth);
     return colonne;
   }
 
   
   public static PdfPCell getCellule(Element element, int alignmentVertical, int alignmentHorizontal, int border, int colspan, float borderwidth, float cellPadding, boolean space) {
     PdfPCell cell = new PdfPCell();
     cell.addElement(element);
     if (border > 0) {
       
       cell.setBorder(border);
       cell.setBorderWidth(borderwidth);
     } else {
       
       cell.setBorder(0);
     } 
     cell.setUseAscender(space);
     if (alignmentVertical == 1) {
       
       cell.setVerticalAlignment(5);
     }
     else if (alignmentVertical == 2) {
       
       cell.setVerticalAlignment(0);
     }
     else if (alignmentVertical == 3) {
       
       cell.setVerticalAlignment(2);
     } 
     if (alignmentHorizontal == 1) {
       
       cell.setHorizontalAlignment(1);
     }
     else if (alignmentHorizontal == 2) {
       
       cell.setHorizontalAlignment(0);
     }
     else if (alignmentHorizontal == 3) {
       
       cell.setHorizontalAlignment(2);
     } 
     cell.setColspan(colspan);
     cell.setPadding(cellPadding);
     return cell;
   }
 
   
   public void onEndPage(PdfWriter writer, Document document) {
     Rectangle rect = writer.getBoxSize("art");
     ColumnText.showTextAligned(writer.getDirectContent(), 1, new Phrase("Bottom Left"), rect.getLeft(), rect.getBottom(), 0.0F);
     ColumnText.showTextAligned(writer.getDirectContent(), 1, new Phrase("Bottom Right"), rect.getRight(), rect.getBottom(), 0.0F);
   }
 }

