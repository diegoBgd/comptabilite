 package utils;
 
 import entite.Compte;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileNotFoundException;
 import java.io.IOException;
 import java.util.Iterator;
 import model.CompteModel;
 import org.apache.poi.ss.usermodel.Cell;
 import org.apache.poi.ss.usermodel.Row;
 import org.apache.poi.ss.usermodel.Sheet;
 import org.apache.poi.xssf.usermodel.XSSFWorkbook;
 import org.hibernate.SessionFactory;
 import persistances.DBConfiguration;
 
 
 
 public class DataRestore
 {
   static SessionFactory factory = DBConfiguration.getSessionFactory();
 
 
 
 
   
   public static void main(String[] args) {
     getAccountPlan();
   }
   public static void getAccountPlan() {
     File f = new File("D:\\VIRAGO\\COMPTAVIRAGO\\plancomptable.xlsx");
     try {
       FileInputStream file = new FileInputStream(f);
       XSSFWorkbook xSSFWorkbook = new XSSFWorkbook(file);
       Sheet s = xSSFWorkbook.getSheetAt(1);
       int rownumber = s.getLastRowNum() + 1;
 
       
       Compte cpt = null;
       String code = null, libelle = null;
       int tmp = 0, i = 0;
       
       Iterator<Row> rowIterator = s.iterator();
       while (rowIterator.hasNext()) {
         int j = 0;
         Row row = s.getRow(i);
         
         for (int k = 0; k <= 1; k++) {
           
           Cell cell = row.getCell(k);
           switch (cell.getCellType()) {
             case 1:
               libelle = cell.getStringCellValue();
               break;
             case 0:
               tmp = (int)cell.getNumericCellValue();
               j = tmp;
               break;
           } 
         } 
         cpt = (new CompteModel()).getCompteByCode(factory, String.valueOf(j));
         if (cpt == null) {
           
           cpt = new Compte();
           cpt.setCompteCod(String.valueOf(j));
           cpt.setLibelle(libelle);
         } 
         
         System.out.println(String.valueOf(j) + " - " + libelle);
         i++;
       } 
     } catch (FileNotFoundException e) {
       
       e.printStackTrace();
     } catch (IOException e) {
       
       e.printStackTrace();
     } 
   }
 }

