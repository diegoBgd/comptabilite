 package utils;
 
 import com.lowagie.text.BadElementException;
 import com.lowagie.text.Element;
 import com.lowagie.text.Font;
 import com.lowagie.text.Image;
 import com.lowagie.text.Paragraph;
 import com.lowagie.text.Phrase;
 import com.lowagie.text.pdf.BarcodePDF417;
 import com.lowagie.text.pdf.PdfPCell;
 import entite.Exercice;
 import java.awt.Color;
 import java.io.File;
 import java.io.FileOutputStream;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.text.DecimalFormat;
 import java.text.DecimalFormatSymbols;
 import java.text.ParseException;
 import java.text.SimpleDateFormat;
 import java.time.LocalDate;
 import java.time.temporal.ChronoUnit;
 import java.util.ArrayList;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.GregorianCalendar;
 import java.util.List;
 import java.util.Properties;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import javax.faces.application.FacesMessage;
 import javax.faces.context.FacesContext;
 import javax.mail.Address;
 import javax.mail.Message;
 import javax.mail.MessagingException;
 import javax.mail.Session;
 import javax.mail.Transport;
 import javax.mail.internet.InternetAddress;
 import javax.mail.internet.MimeMessage;
 import javax.script.ScriptEngine;
 import javax.script.ScriptEngineManager;
 import javax.script.ScriptException;
 import javax.servlet.ServletContext;
 import javax.servlet.http.HttpSession;
 import org.primefaces.event.FileUploadEvent;
 
 
 
 
 
 
 public class HelperC
 {
   public static class TraitementMontant
   {
     private static String unites(int unite) {
       String unit = "";
       switch (unite) {
         
         case 0:
           unit = "zéro";
           break;
         
         case 1:
           unit = "un";
           break;
         
         case 2:
           unit = "deux";
           break;
         
         case 3:
           unit = "trois";
           break;
         
         case 4:
           unit = "quatre";
           break;
         
         case 5:
           unit = "cinq";
           break;
         
         case 6:
           unit = "six";
           break;
         
         case 7:
           unit = "sept";
           break;
         
         case 8:
           unit = "huit";
           break;
         
         case 9:
           unit = "neuf";
           break;
         
         case 10:
           unit = "dix";
           break;
         
         case 11:
           unit = "onze";
           break;
         
         case 12:
           unit = "douze";
           break;
         
         case 13:
           unit = "treize";
           break;
         
         case 14:
           unit = "quatorze";
           break;
         
         case 15:
           unit = "quinze";
           break;
         
         case 16:
           unit = "seize";
           break;
         
         case 17:
           unit = "dix-sept";
           break;
         
         case 18:
           unit = "dix-huit";
           break;
         
         case 19:
           unit = "dix-neuf";
           break;
       } 
       return unit;
     }
 
 
     
     private static String dizaines(int dizaine) {
       String _dizaine = "";
       switch (dizaine) {
         
         case 2:
           _dizaine = "vingt";
           break;
         
         case 3:
           _dizaine = "trente";
           break;
         
         case 4:
           _dizaine = "quarante";
           break;
         
         case 5:
           _dizaine = "cinquante";
           break;
         
         case 6:
           _dizaine = "soixante";
           break;
         
         case 7:
           _dizaine = "soixante";
           break;
         
         case 8:
           _dizaine = "quatre-vingt";
           break;
         
         case 9:
           _dizaine = "quatre-vingt";
           break;
       } 
       return _dizaine;
     }
 
     
     private static String getDizaines(double montant, boolean sup) {
       String _montant = "";
       if (montant < 20.0D) {
         
         _montant = unites((int)montant);
       } else {
         
         int div = (int)(montant / 10.0D);
         int mod = (int)(montant % 10.0D);
         _montant = dizaines(div);
         if (div == 7 || div == 9) {
           
           int diz = 10 + mod;
           _montant = String.valueOf(_montant) + "-" + unites(diz);
         }
         else if (mod == 0 && div == 8) {
           
           if (!sup)
           {
             _montant = String.valueOf(_montant) + "s";
           }
         }
         else if (mod == 1) {
           
           _montant = String.valueOf(_montant) + " et un";
         }
         else if (mod > 1) {
           
           _montant = String.valueOf(_montant) + "-" + unites(mod);
         } 
       } 
       return _montant;
     }
 
     
     private static String getCentaines(double montant, boolean sup) {
       String _montant = "";
       int div = (int)(montant / 100.0D);
       int mod = (int)(montant % 100.0D);
       if (div == 1) {
         
         _montant = "cent";
       } else {
         
         _montant = String.valueOf(unites(div)) + " cent";
       } 
       if (mod == 0) {
         
         if (div > 1 && !sup)
         {
           _montant = String.valueOf(_montant) + "s";
         }
       } else {
         
         _montant = String.valueOf(_montant) + " " + getDizaines(mod, sup);
       } 
       return _montant;
     }
 
     
     private static String getMille(double montant) {
       String _montant = "";
       int div = (int)(montant / 1000.0D);
       int mod = (int)(montant % 1000.0D);
       if (div > 99) {
         
         _montant = String.valueOf(getCentaines(div, true)) + " mille";
       }
       else if (div > 1) {
         
         _montant = String.valueOf(getDizaines(div, true)) + " mille";
       }
       else if (div > 0) {
         
         _montant = "mille";
       } 
       if (mod > 99) {
         
         _montant = String.valueOf(_montant) + " " + getCentaines(mod, false);
       }
       else if (mod > 0) {
         
         _montant = String.valueOf(_montant) + " " + getDizaines(mod, false);
       } 
       return _montant;
     }
 
     
     private static String getMillion(double montant) {
       String _montant = "";
       int div = (int)(montant / 1000000.0D);
       int mod = (int)(montant % 1000000.0D);
       if (div > 99) {
         
         _montant = String.valueOf(getCentaines(div, false)) + " millions";
       }
       else if (div > 1) {
         
         _montant = String.valueOf(getDizaines(div, false)) + " millions";
       } else {
         
         _montant = "un million";
       } 
       if (mod > 999) {
         
         _montant = String.valueOf(_montant) + " " + getMille(mod);
       }
       else if (mod > 99) {
         
         _montant = String.valueOf(_montant) + " " + getCentaines(mod, false);
       }
       else if (mod > 1) {
         
         _montant = String.valueOf(_montant) + " " + getDizaines(mod, false);
       } 
       return _montant;
     }
 
     
     private static String getMilliard(double montant) {
       String _montant = "";
       int div = (int)(montant / 1.0E9D);
       int mod = (int)(montant % 1.0E9D);
       if (div > 99) {
         
         _montant = String.valueOf(getCentaines(div, false)) + " milliards";
       }
       else if (div > 1) {
         
         _montant = String.valueOf(getDizaines(div, false)) + " milliards";
       } else {
         
         _montant = "un milliard";
       } 
       if (mod > 999999) {
         
         _montant = String.valueOf(_montant) + " " + getMillion(mod);
       }
       else if (mod > 999) {
         
         _montant = String.valueOf(_montant) + " " + getMille(mod);
       }
       else if (mod > 99) {
         
         _montant = String.valueOf(_montant) + " " + getCentaines(mod, false);
       }
       else if (mod > 1) {
         
         _montant = String.valueOf(_montant) + " " + getDizaines(mod, false);
       } 
       return _montant;
     }
 
     
     public static String getMontantEnLettre(double montant) {
       String _montant = "";
       if (montant > 9.99999999999E11D) {
         
         _montant = "Ce montant ne peut pas àªtre traité";
       }
       else if (montant <= 9.99999999999E11D && montant > 9.99999999E8D) {
         
         _montant = getMilliard(montant);
       }
       else if (montant <= 9.99999999E8D && montant > 999999.0D) {
         
         _montant = getMillion(montant);
       }
       else if (montant <= 999999.0D && montant > 999.0D) {
         
         _montant = getMille(montant);
       }
       else if (montant <= 999.0D && montant > 99.0D) {
         
         _montant = getCentaines(montant, false);
       }
       else if (montant <= 99.0D && montant >= 0.0D) {
         
         _montant = getDizaines(montant, false);
       } else {
         
         _montant = "Le montant négatif ne peut pas àªtre traité";
       } 
       return _montant;
     }
 
 
     
     public static String getMontantEnLettreDevise(double montant, int nbreCentime, String devise) {
       List<Double> montants = getValeurs(montant, nbreCentime);
       String _montant = "";
       if (((Double)montants.get(1)).doubleValue() > 0.0D) {
         
         _montant = String.valueOf(getMontantEnLettre(((Double)montants.get(0)).doubleValue())) + " " + devise + " " + getMontantEnLettre(((Double)montants.get(1)).doubleValue()) + " centimes";
       }
       else if (((Double)montants.get(1)).doubleValue() == 0.0D) {
         
         _montant = String.valueOf(getMontantEnLettre(((Double)montants.get(0)).doubleValue())) + " " + devise;
       } 
       return _montant;
     }
 
     
     private static List getValeurs(double somme, int nbreDecimal) {
       long _somme = (new Double(somme * 1000.0D)).longValue();
       somme = Double.valueOf(_somme).doubleValue() / 1000.0D;
       List<Double> sommes = new ArrayList();
       long e = (new Double(somme)).longValue();
       sommes.add(Double.valueOf(e));
       if (nbreDecimal > 0) {
         
         DecimalFormat df = new DecimalFormat();
         df.setGroupingUsed(false);
         df.setMaximumFractionDigits(nbreDecimal);
         String str = df.format(somme);
         char separator = df.getDecimalFormatSymbols().getDecimalSeparator();
         str = str.substring(str.indexOf(separator) + 1);
         if (str.trim().length() == 1)
         {
           str = String.valueOf(str) + "0";
         }
         double dec = Double.parseDouble(str);
         if (dec == Double.valueOf(somme).doubleValue())
         {
           dec = 0.0D;
         }
         sommes.add(Double.valueOf(dec));
       } else {
         
         sommes.add(Double.valueOf(0.0D));
       } 
       return sommes;
     }
 
     
     public static String getMontantFormate(double montant, int nbrCentime) {
       String montantString = "";
       List<Double> montants = getValeurs(montant, nbrCentime);
       if (montant > 0.0D) {
         
         if (((Double)montants.get(0)).doubleValue() == 0.0D) {
           
           if (nbrCentime > 0) {
             
             montantString = "0." + HelperC.decimalNumber(((Double)montants.get(1)).doubleValue(), 0, true);
           } else {
             
             montantString = HelperC.decimalNumber(montant, 0, true);
           }
         
         } else if (nbrCentime > 0) {
           
           if (((Double)montants.get(1)).doubleValue() == 0.0D) {
             
             montantString = HelperC.decimalNumber(montant, 0, true);
           } else {
             
             montantString = HelperC.decimalNumber(montant, nbrCentime, true);
           }
         
         } else if (nbrCentime == 0) {
           
           montantString = HelperC.decimalNumber(montant, nbrCentime, true);
         } 
         String[] m = montantString.trim().split(",");
         double _dec = 0.0D;
         
         try {
           _dec = Double.valueOf(m[1].trim()).doubleValue();
         }
         catch (Exception exception) {}
         if (_dec > 0.0D)
         {
           switch (nbrCentime) {
             
             case 2:
               if (m[1].trim().length() == 1)
               {
                 montantString = String.valueOf(montantString) + "0";
               }
               break;
             
             case 3:
               if (m[1].trim().length() == 1) {
                 
                 montantString = String.valueOf(montantString) + "00"; break;
               } 
               if (m[1].trim().length() == 2)
               {
                 montantString = String.valueOf(montantString) + "0";
               }
               break;
             
             case 4:
               if (m[1].trim().length() == 1) {
                 
                 montantString = String.valueOf(montantString) + "000"; break;
               } 
               if (m[1].trim().length() == 2) {
                 
                 montantString = String.valueOf(montantString) + "00"; break;
               } 
               if (m[1].trim().length() == 3)
               {
                 montantString = String.valueOf(montantString) + "0";
               }
               break;
           } 
         
         }
       } else {
         montantString = "0";
       } 
       return montantString;
     }
   }
 
 
   public static Date stringTodate(String dte) {
     Date d = null;
     SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyy");
     
     try {
       d = formatter.parse(dte);
     }
     catch (ParseException e) {
       
       e.printStackTrace();
     } 
     return d;
   }
 
   
   public static Date toDate(int year, int month, int day) {
     Calendar cal = Calendar.getInstance();
     cal.set(1, year);
     cal.set(2, month - 1);
     cal.set(5, day);
     return cal.getTime();
   }
 
   
   public static String convertDate(Date date, boolean formatfr) {
     SimpleDateFormat fmt = null;
     if (formatfr) {
       
       fmt = new SimpleDateFormat("dd-MM-yyyy");
     } else {
       
       fmt = new SimpleDateFormat("yyyy-MM-dd");
     } 
     if (date != null) {
       
       String myDate = fmt.format(date);
       return myDate;
     } 
     
     return "";
   }
 
 
   
   public static String convertToSqlDate(Date date) {
     SimpleDateFormat fmt = null;
     fmt = new SimpleDateFormat("yyyyMMdd");
     if (date != null) {
       
       String myDate = fmt.format(date);
       return myDate;
     } 
     
     return "";
   }
 
 
   
   public static String convertDate(Date date) {
     SimpleDateFormat fmt = null;
     fmt = new SimpleDateFormat("dd/MM/yyyy");
     if (date != null) {
       
       String myDate = fmt.format(date);
       return myDate;
     } 
     
     return "";
   }
 
 
   
   public static HttpSession getSession() {
     HttpSession session = null;
     try {
       FacesContext cont = FacesContext.getCurrentInstance();
       session = (HttpSession)cont.getExternalContext().getSession(true);
     } catch (Exception e) {
       System.out.println(e.getMessage());
       e.printStackTrace();
     } 
     
     return session;
   }
 
   
   public static String convertDat(Date date) {
     SimpleDateFormat fmt = null;
     fmt = new SimpleDateFormat("yyyy-MM-dd");
     if (date != null) {
       
       String myDate = fmt.format(date);
       return myDate;
     } 
     
     return "";
   }
 
 
   
   public static String convertDateHeureMin(Date date) {
     SimpleDateFormat fmt = null;
     fmt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
     if (date != null) {
       
       String myDate = fmt.format(date);
       return myDate;
     } 
     
     return "";
   }
 
 
   
   public static String convertHeureMin(Date date) {
     SimpleDateFormat fmt = null;
     fmt = new SimpleDateFormat("HH:mm:ss");
     if (date != null) {
       
       String myDate = fmt.format(date);
       return myDate;
     } 
     
     return "";
   }
 
 
   
   public static int getMonthFromDate(Date date) {
     String mois = convertDate(date, true).split("-")[1];
     int m = Integer.valueOf(mois).intValue();
     return m;
   }
   
   public static int getDayFromDate(Date date) {
     String mois = convertDate(date, true).split("-")[0];
     int m = Integer.valueOf(mois).intValue();
     return m;
   }
   
   public static String getLetter(String referenceLetter, boolean next, boolean before) {
     String ltr = "";
     String[] lettre = { 
         "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", 
         "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", 
         "U", "V", "W", "X", "Y", "Z" };
     
     if (referenceLetter != "")
     {
       for (int i = 0; i < lettre.length; i++) {
         
         if (referenceLetter.equals(lettre[i])) {
           
           if (next && i + 1 < lettre.length)
           {
             ltr = lettre[i + 1];
           }
           if (before && i - 1 >= 0)
           {
             ltr = lettre[i - 1];
           }
         } 
       } 
     }
     
     return ltr;
   }
 
   
   public static String getMoisDateEnTouteLettre(String date) {
     String myDate = "";
     if (date != null) {
       
       String[] dates = date.split("-");
       int v = Integer.parseInt(dates[1]);
       switch (v) {
         
         case 1:
           myDate = String.valueOf(dates[0]) + "-Janvier-" + dates[2];
           break;
         
         case 2:
           myDate = String.valueOf(dates[0]) + "-Février-" + dates[2];
           break;
         
         case 3:
           myDate = String.valueOf(dates[0]) + "-Mars-" + dates[2];
           break;
         
         case 4:
           myDate = String.valueOf(dates[0]) + "-Avril-" + dates[2];
           break;
         
         case 5:
           myDate = String.valueOf(dates[0]) + "-Mai-" + dates[2];
           break;
         
         case 6:
           myDate = String.valueOf(dates[0]) + "-Juin-" + dates[2];
           break;
         
         case 7:
           myDate = String.valueOf(dates[0]) + "-Juillet-" + dates[2];
           break;
         
         case 8:
           myDate = String.valueOf(dates[0]) + "-Aoà»t-" + dates[2];
           break;
         
         case 9:
           myDate = String.valueOf(dates[0]) + "-Septembre-" + dates[2];
           break;
         
         case 10:
           myDate = String.valueOf(dates[0]) + "-Octobre-" + dates[2];
           break;
         
         case 11:
           myDate = String.valueOf(dates[0]) + "-Novembre-" + dates[2];
           break;
         
         case 12:
           myDate = String.valueOf(dates[0]) + "-Décembre-" + dates[2];
           break;
       } 
     } 
     return myDate;
   }
 
   
   public static String getMoisEnTouteLettre(int mois) {
     String myMois = "";
     switch (mois) {
       
       case 1:
         myMois = "Janvier";
         break;
       
       case 2:
         myMois = "Février";
         break;
       
       case 3:
         myMois = "Mars";
         break;
       
       case 4:
         myMois = "Avril";
         break;
       
       case 5:
         myMois = "Mai";
         break;
       
       case 6:
         myMois = "Juin";
         break;
       
       case 7:
         myMois = "Juillet";
         break;
       
       case 8:
         myMois = "Aoà»t";
         break;
       
       case 9:
         myMois = "Septembre";
         break;
       
       case 10:
         myMois = "Octobre";
         break;
       
       case 11:
         myMois = "Novembre";
         break;
       
       case 12:
         myMois = "Décembre";
         break;
     } 
     return myMois;
   }
 
   
   public static int GetIntValueByBoolean(Boolean val) {
     int a = 0;
     if (val.booleanValue())
     {
       a = 1;
     }
     return a;
   }
 
   
   public static int GetInt(String pays) {
     return 0;
   }
 
   
   public static String FiltreCode(String codedebut, String codefin, String tableName, String champcode) {
     String sqlRequest = "";
     if (!codedebut.equals(""))
     {
       sqlRequest = String.valueOf(sqlRequest) + " AND " + tableName + "." + champcode + ">='" + codedebut + "'";
     }
     if (!codefin.equals(""))
     {
       sqlRequest = String.valueOf(sqlRequest) + " AND " + tableName + "." + champcode + "<='" + codefin + "'";
     }
     return sqlRequest;
   }
 
   
   public static String FiltreDate(Date datedebut, Date datefin, String tableName, String champcode) {
     String sqlRequest = "";
     if (datedebut != null)
     {
       sqlRequest = String.valueOf(sqlRequest) + " AND " + tableName + "." + champcode + ">='" + convertDate(datedebut, false) + "'";
     }
     if (datefin != null)
     {
       sqlRequest = String.valueOf(sqlRequest) + " AND " + tableName + "." + champcode + "<='" + convertDate(datefin, false) + " 23:59:59'";
     }
     return sqlRequest;
   }
 
   
   public static String FiltreDate(String datedebut, String datefin, String tableName, String champcode) {
     String sqlRequest = "";
     if (datedebut != null && datedebut.trim() != "")
     {
       sqlRequest = String.valueOf(sqlRequest) + " AND " + tableName + "." + champcode + ">='" + datedebut + "'";
     }
     if (datefin != null && datefin.trim() != "")
     {
       sqlRequest = String.valueOf(sqlRequest) + " AND " + tableName + "." + champcode + "<='" + datefin + "'";
     }
     return sqlRequest;
   }
 
   
   public static long daysBetween(Date datedebut, Date datefin) {
     long days = 0L;
     long diff = datefin.getTime() - datedebut.getTime();
     days = diff / 86400000L;
     return days;
   }
 
   
   public static Date getDateAdded(Date datetomodify, int intadd, int positiontoadd) {
     Calendar ctest = Calendar.getInstance();
     ctest.setTime(datetomodify);
     switch (positiontoadd) {
       
       case 0:
         ctest.add(5, intadd);
         break;
       
       case 1:
         ctest.add(2, intadd);
         break;
       
       case 2:
         ctest.add(1, intadd);
         break;
     } 
     Date dateadded = ctest.getTime();
     return dateadded;
   }
 
   
   public static int getDataInDate(Date datedata, int position) {
     int t = 0;
     Calendar date = new GregorianCalendar();
     date.setTime(datedata);
     switch (position) {
       
       case 0:
         t = date.get(5);
         break;
       
       case 1:
         t = date.get(2);
         break;
       
       case 2:
         t = date.get(1);
         break;
     } 
     return t;
   }
 
   
   public static int getDaysinMoth(Date datedata) {
     int days = 0;
     Calendar datefin = new GregorianCalendar();
     datefin.setTime(datedata);
     Calendar c = Calendar.getInstance();
     c.set(1, datefin.get(1));
     c.set(2, datefin.get(2));
     days = c.getActualMaximum(5);
     return days;
   }
 
   
   public static void afficherInformation(String titre, String message) {
     FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, titre, message);
     FacesContext.getCurrentInstance().addMessage(null, msg);
   }
 
   
   public static void afficherErreur(String titre, String message) {
     FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, titre, message);
     FacesContext.getCurrentInstance().addMessage(null, msg);
   }
 
   
   public static void afficherAttention(String titre, String message) {
     FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, titre, message);
     FacesContext.getCurrentInstance().addMessage(null, msg);
   }
 
   
   public static String completerCompte(String compte) {
     String _compte = "";
     if (compte == null || compte.trim().equals("")) {
       
       _compte = "0000000";
     }
     else if (compte.trim().length() == 1) {
       
       _compte = String.valueOf(compte) + "000000";
     }
     else if (compte.trim().length() == 2) {
       
       _compte = String.valueOf(compte) + "00000";
     }
     else if (compte.trim().length() == 3) {
       
       _compte = String.valueOf(compte) + "0000";
     }
     else if (compte.trim().length() == 4) {
       
       _compte = String.valueOf(compte) + "000";
     }
     else if (compte.trim().length() == 5) {
       
       _compte = String.valueOf(compte) + "00";
     }
     else if (compte.trim().length() == 6) {
       
       _compte = String.valueOf(compte) + "0";
     }
     else if (compte.trim().length() == 7) {
       
       _compte = compte;
     } 
     return _compte;
   }
   
   public static String completerChrono(int chrono) {
     String i = null;
     String _chrono = "";
     if (chrono < 10) {
       
       _chrono = "0000" + chrono;
     }
     else if (chrono < 100) {
       
       _chrono = "000" + chrono;
     }
     else if (chrono < 1000) {
       
       _chrono = "00" + chrono;
     }
     else if (chrono < 10000) {
       
       _chrono = "0" + chrono;
     } else {
       
       i =String.valueOf(chrono) ;
     } 
     return i;
   }
 
   
   public static String determinerClefControle(String chrono, Exercice exercice) {
     String clef = "";
     int a1 = Integer.valueOf(exercice.getExCode().substring(2, 3)).intValue();
     int a2 = Integer.valueOf(exercice.getExCode().substring(3, 4)).intValue();
     int a3 = Integer.valueOf(chrono.substring(0, 1)).intValue();
     int a4 = Integer.valueOf(chrono.substring(1, 2)).intValue();
     int a5 = Integer.valueOf(chrono.substring(2, 3)).intValue();
     int a6 = Integer.valueOf(chrono.substring(3, 4)).intValue();
     int a7 = Integer.valueOf(chrono.substring(4, 5)).intValue();
     int b1 = a1 * 7;
     int b2 = a2 * 8;
     int b3 = a3 * 9;
     int b4 = a4 * 10;
     int b5 = a5 * 11;
     int b6 = a6 * 12;
     int b7 = a7 * 13;
     int somme = b1 + b2 + b3 + b4 + b5 + b6 + b7;
     int mod = somme % 24;
     switch (mod) {
       
       case 0:
         clef = "A";
         break;
       
       case 1:
         clef = "B";
         break;
       
       case 2:
         clef = "C";
         break;
       
       case 3:
         clef = "D";
         break;
       
       case 4:
         clef = "E";
         break;
       
       case 5:
         clef = "F";
         break;
       
       case 6:
         clef = "G";
         break;
       
       case 7:
         clef = "H";
         break;
       
       case 8:
         clef = "J";
         break;
       
       case 9:
         clef = "K";
         break;
       
       case 10:
         clef = "L";
         break;
       
       case 11:
         clef = "M";
         break;
       
       case 12:
         clef = "N";
         break;
       
       case 13:
         clef = "P";
         break;
       
       case 14:
         clef = "Q";
         break;
       
       case 15:
         clef = "R";
         break;
       
       case 16:
         clef = "S";
         break;
       
       case 17:
         clef = "T";
         break;
       
       case 18:
         clef = "U";
         break;
       
       case 19:
         clef = "V";
         break;
       
       case 20:
         clef = "W";
         break;
       
       case 21:
         clef = "X";
         break;
       
       case 22:
         clef = "Y";
         break;
       
       case 23:
         clef = "Z";
         break;
     } 
     return clef;
   }
 
   
   public static boolean verifierClefControle(String nif) {
     boolean valide = false;
     if (nif == null || nif.trim().length() != 8) {
       
       afficherMessage("Attention!!", "NIF Invalide");
     } else {
       
       String clef = "";
       int a1 = Integer.valueOf(nif.substring(0, 1)).intValue();
       int a2 = Integer.valueOf(nif.substring(1, 2)).intValue();
       int a3 = Integer.valueOf(nif.substring(2, 3)).intValue();
       int a4 = Integer.valueOf(nif.substring(3, 4)).intValue();
       int a5 = Integer.valueOf(nif.substring(4, 5)).intValue();
       int a6 = Integer.valueOf(nif.substring(5, 6)).intValue();
       int a7 = Integer.valueOf(nif.substring(6, 7)).intValue();
       int b1 = a1 * 7;
       int b2 = a2 * 8;
       int b3 = a3 * 9;
       int b4 = a4 * 10;
       int b5 = a5 * 11;
       int b6 = a6 * 12;
       int b7 = a7 * 13;
       int somme = b1 + b2 + b3 + b4 + b5 + b6 + b7;
       int mod = somme % 24;
       switch (mod) {
         
         case 0:
           clef = "A";
           break;
         
         case 1:
           clef = "B";
           break;
         
         case 2:
           clef = "C";
           break;
         
         case 3:
           clef = "D";
           break;
         
         case 4:
           clef = "E";
           break;
         
         case 5:
           clef = "F";
           break;
         
         case 6:
           clef = "G";
           break;
         
         case 7:
           clef = "H";
           break;
         
         case 8:
           clef = "J";
           break;
         
         case 9:
           clef = "K";
           break;
         
         case 10:
           clef = "L";
           break;
         
         case 11:
           clef = "M";
           break;
         
         case 12:
           clef = "N";
           break;
         
         case 13:
           clef = "P";
           break;
         
         case 14:
           clef = "Q";
           break;
         
         case 15:
           clef = "R";
           break;
         
         case 16:
           clef = "S";
           break;
         
         case 17:
           clef = "T";
           break;
         
         case 18:
           clef = "U";
           break;
         
         case 19:
           clef = "V";
           break;
         
         case 20:
           clef = "W";
           break;
         
         case 21:
           clef = "X";
           break;
         
         case 22:
           clef = "Y";
           break;
         
         case 23:
           clef = "Z";
           break;
       } 
       if (nif.substring(7, 8).trim().equals(clef.trim())) {
         
         valide = true;
       } else {
         
         afficherMessage("Attention!!", "NIF Invalide");
       } 
     } 
     return valide;
   }
 
   
   public static String nbreDecimalSansSeparateur(String nombre) {
     String valeur = "";
     if (nombre != null && !nombre.trim().equals("")) {
       
       String[] tab = NbreDecimal(nombre, 3, Boolean.valueOf(false)).split(",");
       valeur = String.valueOf(tab[0]) + "." + tab[1];
     } 
     return valeur;
   }
 
   
   public static String NbreDecimal(String Nombre, int NbrePositionsDecimales, Boolean SeparateurMilliers) {
     double mount = Double.parseDouble(Nombre);
     return decimalNumber(mount, NbrePositionsDecimales, SeparateurMilliers.booleanValue());
   }
 
   
   public static String decimalNumber(double nombre, int nbrDc, boolean separat) {
     DecimalFormat format = null;
     String nbr = "0";
     String fmt = "";
     if (nombre != 0.0D) {
 
       
       if (nbrDc > 0) {
         
         fmt = String.valueOf(fmt) + ".0";
         for (int i = 1; i < nbrDc; i++)
         {
           fmt = String.valueOf(fmt) + "#";
         }
       } 
       
       if (separat) {
         
         format = new DecimalFormat("###,###,###" + fmt);
         DecimalFormatSymbols s = format.getDecimalFormatSymbols();
         s.setGroupingSeparator(' ');
         format.setDecimalFormatSymbols(s);
       } else {
         
         format = new DecimalFormat("######" + fmt);
       } 
       nbr = format.format(nombre);
     } 
     return nbr;
   }
 
   
   public static String NoSepartorNumber(String number) {
     String nb = "";
     String[] mots = null;
     String decimal = "";
     for (int k = 0; k < number.length() - 1; k++) {
       
       if (number.charAt(k) == ',') {
         
         mots = number.split(",");
         decimal = mots[1];
       } 
     } 
     
     DecimalFormat formater = new DecimalFormat("###,###,###,###,##0");
     
     try {
       nb = formater.parse(number).toString();
       if (decimal != "")
       {
         nb = String.valueOf(nb) + "." + mots[1];
       }
     }
     catch (ParseException e) {
       
       e.printStackTrace();
     } 
     return nb;
   }
 
   
   public static String getdayOfWeek(Date dte) {
     Calendar c = Calendar.getInstance();
     c.setTime(dte);
     int dayOfWeek = c.get(7) - 1;
     String jour = "";
     switch (dayOfWeek)
     
     { case 1:
         jour = "LUNDI";
       return jour;case 2: jour = "MARDI"; return jour;case 3: jour = "MERCREDI"; return jour;case 4: jour = "JEUDI"; return jour;case 5: jour = "VENDREDI"; return jour;case 6: jour = "SAMEDI"; return jour;case 7: jour = "DIMANCHE"; return jour; }  jour = "0"; return jour;
   }
 
   
   public static String changeDateFormate(Date dt) {
     SimpleDateFormat fmt = null;
     fmt = new SimpleDateFormat("dd/MM/yyyy");
     if (dt != null) {
       
       String myDate = fmt.format(dt);
       return myDate;
     } 
     
     return "";
   }
 
 
   
   public static String changeStringFormat(String dt) {
     String dte = "";
     String[] mot = dt.split("-");
     dte = String.valueOf(mot[2]) + "-" + mot[1] + "-" + mot[0];
     return dte;
   }
 
   
   public static long differenceDate(Date date1, Date date2) {
     long UNE_HEURE = 3600000L;
     long diff = 0L;
     if (date1 != null && date2 != null)
     {
       diff = (date2.getTime() - date1.getTime() + UNE_HEURE) / UNE_HEURE * 24L;
     }
     return diff;
   }
 
   
   public static String moisEnLettres(int s) {
     String mois = "";
     switch (s) {
       
       case 1:
         mois = "Janvier";
         break;
       
       case 2:
         mois = "Février";
         break;
       
       case 3:
         mois = "Mars";
         break;
       
       case 4:
         mois = "Avril";
         break;
       
       case 5:
         mois = "Mais";
         break;
       
       case 6:
         mois = "Juin";
         break;
       
       case 7:
         mois = "Juillet";
         break;
       
       case 8:
         mois = "Aout";
         break;
       
       case 9:
         mois = "Septembre";
         break;
       
       case 10:
         mois = "Octobre";
         break;
       
       case 11:
         mois = "Novembrer";
         break;
       
       case 12:
         mois = "Décembre";
         break;
     } 
     return mois;
   }
 
   
   public static Date addYear(Date date, int YearsNumber) {
     Calendar cal = Calendar.getInstance();
     cal.set(1, Integer.valueOf(convertDate(date, true).split("-")[2]).intValue());
     cal.set(2, Integer.valueOf(convertDate(date, true).split("-")[1]).intValue() - 1);
     cal.set(5, Integer.valueOf(convertDate(date, true).split("-")[0]).intValue());
     cal.add(1, YearsNumber);
     return cal.getTime();
   }
 
   
   public static Date addMonth(Date date, int monthsNumber) {
     Calendar cal = Calendar.getInstance();
     cal.set(1, Integer.valueOf(convertDate(date, true).split("-")[2]).intValue());
     cal.set(2, Integer.valueOf(convertDate(date, true).split("-")[1]).intValue() - 1);
     cal.set(5, Integer.valueOf(convertDate(date, true).split("-")[0]).intValue());
     cal.add(2, monthsNumber);
     return cal.getTime();
   }
 
   
   public static Date addDay(Date date, int daysNumber) {
     Calendar cal = Calendar.getInstance();
     cal.set(1, Integer.valueOf(convertDate(date, true).split("-")[2]).intValue());
     cal.set(2, Integer.valueOf(convertDate(date, true).split("-")[1]).intValue() - 1);
     cal.set(5, Integer.valueOf(convertDate(date, true).split("-")[0]).intValue());
     cal.add(5, daysNumber);
     return cal.getTime();
   }
 
   
   public static Date addDate(Date date, int daysNumber, int monthsNumber, int YearsNumber) {
     Calendar cal = Calendar.getInstance();
     cal.set(1, Integer.valueOf(convertDate(date, true).split("-")[2]).intValue());
     cal.set(2, Integer.valueOf(convertDate(date, true).split("-")[1]).intValue() - 1);
     cal.set(5, Integer.valueOf(convertDate(date, true).split("-")[0]).intValue());
     cal.add(5, daysNumber);
     cal.add(5, daysNumber);
     cal.add(2, monthsNumber);
     return cal.getTime();
   }
 
   
   public static String convertDateWithHourMinutesSec(Date date, boolean formatfr) {
     SimpleDateFormat fmt = null;
     if (formatfr) {
       
       fmt = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
     } else {
       
       fmt = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
     } 
     if (date != null) {
       
       String myDate = fmt.format(date);
       return myDate;
     } 
     
     return "";
   }
 
 
   
   public static double getElapsedTime(Date date1, Date date2) {
     double durationInDays = 0.0D;
     double durationInSec = 0.0D;
     if (date1 != null && date2 != null) {
       
       if (date2.after(date1))
       {
         durationInSec = ((date2.getTime() - date1.getTime()) / 1000L);
       }
       durationInDays = durationInSec / 86400.0D;
     } 
     return durationInDays;
   }
 
   
   public static double arrondissementDouble(double a, int n) {
     double p = Math.pow(10.0D, n);
     return Math.floor(a * p + 0.5D) / p;
   }
 
   
   public static double calculEcart(double dureePrevue, double dureeRealisee) {
     double ecart = 0.0D;
     ecart = dureePrevue - dureeRealisee;
     return ecart;
   }
 
   
   public static double ecartPourcentage(double dureePrevue, double dureeRealisee) {
     double ecartPourc = 0.0D;
     ecartPourc = dureeRealisee * 100.0D / dureePrevue;
     return ecartPourc;
   }
 
   
   public static String GetBooleanValueByString(String val) {
     if (val.compareTo("true") == 0)
     {
       return "Oui";
     }
     
     return "Non";
   }
 
 
   
   public static boolean GetBooleanValueByInt(int val) {
     boolean b = false;
     switch (val) {
       
       case 0:
         b = false;
         break;
       
       case 1:
         b = true;
         break;
     } 
     return b;
   }
 
   
   public static String motCripte(String mot) {
     String crpt = "";
     String pos = "";
     for (int i = 0; i < mot.length(); i++) {
       
       pos = getPosition(String.valueOf(mot.charAt(i)));
       
       if (!pos.equals(" ")) {
         
         crpt = String.valueOf(crpt) + getPosition(String.valueOf(mot.charAt(i)));
       } else {
         
         crpt = String.valueOf(crpt) + "S" + String.valueOf(mot.charAt(i)) + "S ";
       } 
     } 
     
     return crpt;
   }
 
   
   private static String getPosition(String s) {
     String value = " ";
     String size = "";
     String[][] tble = {
         {
           "A", "B", "C", "1", "D", "E"
         
         }, { "F", "2", "G", "H", "3", "I"
         },
         { "J", "K", "4", "5", "L", "M"
         },
         { "N", "O", "6", "7", "P", "Q"
         },
         { "R", "8", "S", "T", "9", "U"
         },
         { "V", "W", "X", "0", "Y", "Z" }
       };
     
     size = typeChar(s);
     for (int i = 0; i < tble.length; i++) {
       
       for (int j = 0; j < (tble[i]).length; j++) {
         
         if (s.toUpperCase().trim().equals(tble[i][j])) {
           
           value = String.valueOf(i) + size + j + " ";
           return value;
         } 
       } 
     } 
 
     
     return value;
   }
 
   
   public static String decodeWord(String[] chaine) {
     String mot = "";
     int k = 0;
     int j = 0;
     char typ = Character.MIN_VALUE;
     for (int i = 0; i < chaine.length; i++) {
       
       if (chaine[i].length() > 2)
       {
 
         
         if (chaine[i].charAt(0) != 'S')
         
         { 
           
           try { typ = chaine[i].charAt(1);
             k = Integer.valueOf(chaine[i].split(String.valueOf(typ))[0]).intValue();
             j = Integer.valueOf(chaine[i].split(String.valueOf(typ))[1]).intValue();
             if (j <= 5 && k <= 5)
             
             { if (typ == 'U')
               {
                 mot = String.valueOf(mot) + getletter(k, j).toUpperCase();
               }
               if (typ == 'L')
               {
                 mot = String.valueOf(mot) + getletter(k, j).toLowerCase();
               }
               if (typ == 'N')
               {
                 mot = String.valueOf(mot) + getletter(k, j);
 
               
               }
                }
             
             else
             
             { 
               return null; }  } catch (NumberFormatException e) { return null; }
            }
         else { typ = chaine[i].charAt(1);
           if (typ == 'E') {
             
             mot = String.valueOf(mot) + " ";
           } else {
             
             mot = String.valueOf(mot) + chaine[i].charAt(1);
           }  }
          } 
     } 
     return mot;
   }
 
   
   private static String getletter(int i, int j) {
     String mot = "";
     String[][] tble = {
         {
           "A", "B", "C", "1", "D", "E"
         
         }, { "F", "2", "G", "H", "3", "I"
         },
         { "J", "K", "4", "5", "L", "M"
         },
         { "N", "O", "6", "7", "P", "Q"
         },
         { "R", "8", "S", "T", "9", "U"
         },
         { "V", "W", "X", "0", "Y", "Z" }
       };
     
     mot = tble[i][j];
     return mot;
   }
 
   
   private static String typeChar(String s) {
     String type = "";
     Pattern pU = Pattern.compile("[A-Z]");
     Pattern pL = Pattern.compile("[a-z]");
     Pattern pN = Pattern.compile("[0-9]");
     Matcher matcherU = pU.matcher(s);
     Matcher matcherL = pL.matcher(s);
     Matcher matcherN = pN.matcher(s);
     if (matcherU.find())
     {
       type = "U";
     }
     if (matcherL.find())
     {
       type = "L";
     }
     if (matcherN.find())
     {
       type = "N";
     }
     if (s.equals(" "))
     {
       type = "E";
     }
     return type;
   }
 
   
   public static long getdifferenceJours(Date date1, Date date2) {
     long durationInDays = 0L;
     long durationInSec = 0L;
     if (date1 != null && date2 != null) {
       
       if (date2.after(date1))
       {
         durationInSec = (date2.getTime() - date1.getTime()) / 1000L;
       }
       durationInDays = durationInSec / 86400L;
     } 
     return durationInDays;
   }
 
   
   public static Date validerDate(String myDate) {
     Date dateValide = null;
     String[] tDate = myDate.split("/");
     boolean verify = true;
     
     try {
       int day = Integer.valueOf(tDate[0]).intValue();
       int month = Integer.valueOf(tDate[1]).intValue();
       int year = Integer.valueOf(tDate[2]).intValue();
       
       if (month < 0 || month > 12) {
         verify = false;
       }
       switch (month) {
         
         case 2:
           if (day > 29)
             verify = false; 
           break;
         case 1:
         case 3:
         case 5:
         case 7:
         case 8:
         case 10:
         case 12:
           if (day > 31)
             verify = false; 
           break;
         case 4:
         case 6:
         case 9:
         case 11:
           if (day > 30)
             verify = false; 
           break;
       } 
       if (verify) {
         dateValide = toDate(year, month, day);
       }
     } catch (Exception e) {
       
       dateValide = null;
     } 
     return dateValide;
   }
 
   
   public static Date validerHeure(String myHour) {
     Date dateValide = null;
     String[] tDate = myHour.split(":");
     
     try {
       int hour = Integer.valueOf(tDate[0]).intValue();
       int minute = Integer.valueOf(tDate[1]).intValue();
       int seconde = Integer.valueOf(tDate[2]).intValue();
       dateValide = toHour(hour, minute, seconde);
     }
     catch (Exception e) {
       
       dateValide = null;
     } 
     return dateValide;
   }
 
   
   public static Date toHour(int hour, int minute, int seconde) {
     Calendar cal = Calendar.getInstance();
     cal.set(10, hour);
     cal.set(12, minute);
     cal.set(13, seconde);
     return cal.getTime();
   }
 
 
 
 
 
 
 
   
   public static void sendEmail(String serverSmtp, String user, String origin, String pwdOrgine, String destination, String txtMsg, String objectMsg) {
     Properties properties = new Properties();
     properties.setProperty("mail.transport.protocol", "smtp");
     properties.setProperty("mail.smtp.host", serverSmtp);
     properties.setProperty("mail.smtp.user", origin);
     properties.setProperty("mail.from", origin);
     properties.setProperty("mail.smtp.starttls.enable", "true");
     properties.setProperty("mail.smtp.ssl.trust", serverSmtp);
 
 
     
     properties.setProperty("mail.smtp.auth", "true");
     
     try {
       Session session = Session.getInstance(properties);
       MimeMessage message = new MimeMessage(session);
       message.setSubject(objectMsg);
       message.setContent(txtMsg, "text/html");
       message.addRecipients(Message.RecipientType.TO, destination);
       Transport transport = null;
       transport = session.getTransport("smtp");
       transport.connect(origin, pwdOrgine);
       InternetAddress adresse = new InternetAddress(destination);
       InternetAddress[] listeAdresse = new InternetAddress[1];
       listeAdresse[0] = adresse;
       
       transport.sendMessage((Message)message, (Address[])listeAdresse);
       if (transport != null) {
         transport.close();
       }
       System.out.println("Sent message successfully....");
     } catch (MessagingException mex) {
       mex.printStackTrace();
     } 
   }
 
 
 
   
   public static String[] changeDoubleInString(String mntStrng) {
     double mnt = 0.0D;
     String[] mntRetuned = new String[2];
     
     try {
       mnt = Double.valueOf(mntStrng.replace(" ", "").replace(",", ".")).doubleValue();
       mntRetuned[0] =String.valueOf(mnt) ;
     
     }
     catch (Exception e) {
       
       mnt = 0.0D;
       mntRetuned[0] = "0";
     } 
     if (mnt < 0.0D)
     {
       mntRetuned[0] = "0";
     }
     if (mnt > 0.0D) {
       
       mntRetuned[1] = TraitementMontant.getMontantFormate(mnt, 0);
       mntRetuned[0] =  Double.valueOf(mntRetuned[1].replace(" ", "").replace(",", ".").trim()).toString();
     } else {
       
       mntRetuned[0] = "0";
       mntRetuned[1] = "";
     } 
     
     if (mnt < 0.0D)
     {
       mntRetuned[0] = "0";
     }
     if (mnt > 0.0D) {
       
       mntRetuned[1] = TraitementMontant.getMontantFormate(mnt, 0);
       mntRetuned[0] = Double.valueOf(mntRetuned[1].replace(" ", "").replace(",", ".").trim()).toString();
     } else {
        
       mntRetuned[0] = "0";
       mntRetuned[1] = "";
     } 
     
     if (mnt < 0.0D)
     {
       mntRetuned[0] = "0";
     }
     if (mnt > 0.0D) {
       
       mntRetuned[1] = TraitementMontant.getMontantFormate(mnt, 0);
       mntRetuned[0] = Double.valueOf(mntRetuned[1].replace(" ", "").replace(",", ".").trim()).toString();
     } else {
       
       mntRetuned[0] = "0";
       mntRetuned[1] = "";
     } 
     return mntRetuned;
   }
 
 
 
   
   public static String[] changeIntInString(String nombreStrng) {
     int nombre = 0;
     String[] mntRetuned = new String[2];
     
     try {
       nombre = Integer.parseInt(nombreStrng.replace(" ", "").replace(",", "."));
       mntRetuned[0] =String.valueOf(nombre) ;
     
     }
     catch (Exception e) {
       
       nombre = 0;
       mntRetuned[0] = "0";
     } 
     if (nombre < 0)
     {
       mntRetuned[0] = "0";
     }
     if (nombre > 0) {
       
       mntRetuned[1] = TraitementMontant.getMontantFormate(nombre, 0);
       mntRetuned[0] = mntRetuned[1].replace(" ", "").replace(",", ".").trim();
     } else {
       
       mntRetuned[0] = "0";
       mntRetuned[1] = "";
     } 
     
     if (nombre < 0)
     {
       mntRetuned[0] = "0";
     }
     if (nombre > 0) {
       
       mntRetuned[1] = TraitementMontant.getMontantFormate(nombre, 0);
       mntRetuned[0] = mntRetuned[1].replace(" ", "").replace(",", ".").trim();
     } else {
       
       mntRetuned[0] = "0";
       mntRetuned[1] = "";
     } 
     
     if (nombre < 0)
     {
       mntRetuned[0] = "0";
     }
     if (nombre > 0) {
       
       mntRetuned[1] = TraitementMontant.getMontantFormate(nombre, 0);
       mntRetuned[0] = mntRetuned[1].replace(" ", "").replace(",", ".").trim();
     } else {
       
       mntRetuned[0] = "0";
       mntRetuned[1] = "";
     } 
     return mntRetuned;
   }
 
   
   public static String FormterNombre(String txt, int length) {
     String p = String.valueOf(length);
     if (txt.trim().equals("")) {
       
       txt = "00000000";
     }
     else if (txt.trim().length() < length) {
       
       txt = String.format("%" + p + "s", new Object[] {
             txt.trim()
           }).replace(' ', '0');
     } 
     return txt;
   }
 
   
   public static void afficherMessage(String titre, String message) {
     FacesMessage msg = new FacesMessage(titre, message);
     FacesContext.getCurrentInstance().addMessage(null, msg);
   }
   
   public static void afficherDeleteMessage() {
     FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Info", "il faut préciser l'élément à  supprimer");
     FacesContext.getCurrentInstance().addMessage(null, msg);
   }
   
   public static void afficherMessage(String titre, String message, FacesMessage.Severity severity) {
     FacesMessage msg = new FacesMessage(severity, titre, message);
     FacesContext.getCurrentInstance().addMessage(null, msg);
   }
 
   
   public static String getExtension(FileUploadEvent file) {
     String extension = "";
     
     try {
       if (file != null)
       {
         if (file.getFile().getFileName().contains(".docx"))
         {
           extension = ".docx";
         }
         else if (file.getFile().getFileName().contains(".doc"))
         {
           extension = ".doc";
         }
         else if (file.getFile().getFileName().contains(".xlsx"))
         {
           extension = ".xlsx";
         }
         else if (file.getFile().getFileName().contains(".xls"))
         {
           extension = ".xls";
         }
         else if (file.getFile().getFileName().contains(".csv"))
         {
           extension = ".csv";
         }
         else if (file.getFile().getFileName().contains(".jpg"))
         {
           extension = ".jpg";
         }
         else if (file.getFile().getFileName().contains(".gif"))
         {
           extension = ".gif";
         }
         else if (file.getFile().getFileName().contains(".png"))
         {
           extension = ".png";
         }
         else if (file.getFile().getFileName().contains(".jpeg"))
         {
           extension = ".jpeg";
         }
         else if (file.getFile().getFileName().contains(".pdf"))
         {
           extension = ".pdf";
         }
       
       }
     } catch (Exception e) {
       
       System.out.println(e.getMessage());
     } 
     return extension;
   }
 
 
   
   public static void creerRepertoire(String nom) {
     try {
       ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
       String url = String.valueOf(servletContext.getRealPath("/resources")) + "\\" + nom;
       File directory = new File(url);
       if (!directory.exists())
       {
         directory.mkdir();
       }
     }
     catch (Exception e) {
       
       System.out.println(e.getMessage());
     } 
   }
 
   
   public static String creerFichier(FileUploadEvent event, String repertoire, String nomFichier) {
     String url = "";
     
     try {
       InputStream in = event.getFile().getInputstream();
       ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
       url = String.valueOf(servletContext.getRealPath("/resources")) + "\\" + repertoire + "\\" + nomFichier;
       OutputStream out = new FileOutputStream(new File(url));
       int read = 0;
       byte[] bytes = new byte[1024];
       while ((read = in.read(bytes)) != -1)
       {
         out.write(bytes, 0, read);
       }
       in.close();
       out.flush();
       out.close();
     }
     catch (Exception e) {
       
       System.out.println(e.getMessage());
     } 
     return url;
   }
 
   
   public static String getURL(String repertoire, String nomFichier) {
     String url = "";
     
     try {
       ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
       url = String.valueOf(servletContext.getRealPath("/resources")) + "\\" + repertoire + "\\" + nomFichier;
     }
     catch (Exception e) {
       
       System.out.println(e.getMessage());
     } 
     return url;
   }
   public static int daysbetween(Date datedebut, Date datefin) {
     long days = 0L;
     
     LocalDate dateBefore = LocalDate.of(getYearFromDate(datedebut), getMonthFromDate(datedebut), getDayFromDate(datedebut));
     LocalDate dateAfter = LocalDate.of(getYearFromDate(datefin), getMonthFromDate(datefin), getDayFromDate(datefin));
     days = ChronoUnit.DAYS.between(dateBefore, dateAfter);
     return (int)days;
   }
 
   
   public static int getYearFromDate(Date date) {
     Calendar cal = GregorianCalendar.getInstance();
     cal.setTime(date);
     return cal.get(1);
   }
 
   
   public static String HeureValide(String heure) {
     String hour = "";
     String H = heure.split(":")[0].replaceAll("_", "");
     String M = heure.split(":")[1].replaceAll("_", "");
     String S = heure.split(":")[2].replaceAll("_", "");
     int h = 0;
     int m = 0;
     int s = 0;
     if (H.length() == 1)
     {
       H = "0" + H;
     }
     if (M.length() == 1)
     {
       M = "0" + M;
     }
     if (S.length() == 1)
     {
       S = "0" + S;
     }
     heure = String.valueOf(H) + ":" + M + ":" + S;
     if (heure != null) {
       
       if (H.equals("")) {
         
         h = 0;
         H = "0";
       } else {
         
         h = Integer.parseInt(H);
         if (h > 24)
         {
           return null;
         }
       } 
       if (M.equals("")) {
         
         m = 0;
         M = "0";
       } else {
         
         m = Integer.parseInt(M);
         if (m > 59) {
           
           h = m / 60;
           m -= 60;
           H = String.valueOf(h);
         } 
       } 
       if (S.equals("")) {
         
         s = 0;
         S = "0";
       } else {
         
         s = Integer.parseInt(S);
         if (s > 59) {
           
           m += s / 60;
           s -= 60;
           M = String.valueOf(m);
           S = String.valueOf(s);
         } 
       } 
       if (H.length() == 1)
       {
         H = "0" + H;
       }
       if (M.length() == 1)
       {
         M = "0" + M;
       }
       if (S.length() == 1)
       {
         S = "0" + S;
       }
       hour = String.valueOf(H) + ":" + M + ":" + S;
     } 
     return hour;
   }
 
   
   public static String differenceHeure(String heureDeb, String heureFin) {
     String valeur = "00:00:00";
     
     try {
       int minutes = 0;
       int hours = 0;
       int sec = 0;
       int hDeb = Integer.valueOf(heureDeb.split(":")[0]).intValue();
       int mDeb = Integer.valueOf(heureDeb.split(":")[1]).intValue();
       int sDeb = Integer.valueOf(heureDeb.split(":")[2]).intValue();
       int hFin = Integer.valueOf(heureFin.split(":")[0]).intValue();
       int mFin = Integer.valueOf(heureFin.split(":")[1]).intValue();
       int sFin = Integer.valueOf(heureFin.split(":")[2]).intValue();
       if (hFin == 0)
       {
         hFin = 24;
       }
       if (hDeb == 0)
       {
         hDeb = 24;
       }
       if (sFin >= sDeb) {
         
         sec = sFin - sDeb;
       } else {
         
         sec = 60 + sFin - sDeb;
         if (mFin > 0)
         {
           mFin--;
         }
         if (mFin == 0) {
           
           mFin = 60;
           hFin--;
         } 
       } 
       if (mFin >= mDeb) {
         
         minutes = mFin - mDeb;
       } else {
         
         minutes = 60 + mFin - mDeb;
         hFin--;
       } 
       hours = hFin - hDeb;
       valeur = HeureValide(String.valueOf(hours) + ":" + minutes + ":" + sec);
     }
     catch (Exception e) {
       
       e.printStackTrace();
     } 
     return valeur;
   }
 
   
   public static String sommHeure(String heureDeb, String heureFin) {
     String valeur = "00:00:00";
     SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
     
     try {
       Date deb = sdf.parse(heureDeb);
       Date fin = sdf.parse(heureFin);
       int minutes = 0;
       int hours = 0;
       int sec = 0;
       sec += fin.getSeconds() + deb.getSeconds();
       if (sec >= 60) {
         
         minutes = sec / 60;
         sec -= 60;
       } 
       minutes += fin.getMinutes() + deb.getMinutes();
       if (minutes >= 60) {
         
         hours = minutes / 60;
         minutes -= 60;
       } 
       hours += fin.getHours() + deb.getHours();
       valeur = String.valueOf(hours) + ":" + minutes + ":" + sec;
     }
     catch (Exception e) {
       
       e.printStackTrace();
     } 
     return valeur;
   }
 
   
   public static String heuresPreste(int heure, int min, int sec) {
     String valeur = "";
     if (sec > 59) {
       
       min += sec / 60;
       sec -= 60;
     } 
     if (min > 59) {
       
       heure += min / 60;
       min -= 60;
     } 
     valeur = String.valueOf(heure) + " Heures " + min + " Min " + sec + " Sec";
     return valeur;
   }
   
   public static boolean periodeValide(Date ref, Date dateDb, Date dateFn) {
     boolean b = true;
     String pattern = "dd-MM-yyyy";
     SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
     String date = simpleDateFormat.format(ref);
     try {
       Date dateRef = simpleDateFormat.parse(date);
       if (dateRef.before(dateDb))
         b = false; 
       if (dateRef.after(dateFn))
         b = false; 
     } catch (ParseException e) {
       
       e.printStackTrace();
     } 
     
     return b;
   }
 
   
   public static String getElementFormule(String formule) {
     String formResult = "";
     String formModify = "";
     formule = formule.replaceAll(" ", "");
     formModify = formule.replace('(', ' ');
     formModify = formModify.replace(')', ' ');
     formModify = formModify.replaceAll(" ", "");
     formResult = formModify.replace('+', ' ');
     formResult = formResult.replace('-', ' ');
     formResult = formResult.replace('/', ' ');
     formResult = formResult.replace('*', ' ');
     return formResult;
   }
 
   
	public static double getFormulaValue(String formule) {
		double valeur = 0.0D;
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine engine = mgr.getEngineByName("JavaScript");

		try {
			if (formule != null && !formule.equals(""))
				valeur = Double.valueOf(engine.eval(formule).toString()).doubleValue();
		} catch (ScriptException e) {

			e.printStackTrace();
		}
		return valeur;
	}
 
   
   public static PdfPCell getImmageCellule(String text, int colspan, int border) {
     PdfPCell colonne = null;
     Image imgCode = null;
     BarcodePDF417 code = new BarcodePDF417();
     code.setText(text);
     
     try {
       imgCode = code.getImage();
       colonne = new PdfPCell();
       colonne.setColspan(colspan);
       colonne.setVerticalAlignment(2);
       colonne.setHorizontalAlignment(2);
       colonne.setBorder(border);
       colonne.addElement((Element)imgCode);
     }
     catch (BadElementException e) {
       
       e.printStackTrace();
     } 
     return colonne;
   }
 
 
   
   public static PdfPCell getCellule(String label, Font font, int alignmentVertical, int alignmentHorizontal, int border, int colspan, Color backgroundColor, Color bordercolor, float borderwidth, float paddingWidth) {
     Paragraph par = new Paragraph(label, font);
     PdfPCell cell = new PdfPCell((Phrase)par);
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
     if (backgroundColor != null)
     {
       cell.setBackgroundColor(backgroundColor);
     }
     cell.setPadding(paddingWidth);
     return cell;
   }
 
   
   public static PdfPCell getCellule(Element element, int alignmentVertical, int alignmentHorizontal, int border, int colspan, float borderwidth, Color bordercolor, float cellPadding) {
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
 
   
   public static boolean testerPassword(String password) {
     boolean isValide = false;
     if (password != null && !password.replace(" ", "").trim().equals("") && password.replace(" ", "").trim().length() >= 8) {
       
       Pattern pattern1 = Pattern.compile("\\W");
       Pattern pattern2 = Pattern.compile("[A-Z]");
       Pattern pattern3 = Pattern.compile("[0-9]");
       Matcher matcher = pattern1.matcher(password.replace(" ", "").trim());
       if (matcher.find()) {
         
         matcher = pattern2.matcher(password.replace(" ", "").trim());
         if (matcher.find()) {
           
           matcher = pattern3.matcher(password.replace(" ", "").trim());
           if (matcher.find())
           {
             isValide = true;
           }
         } 
       } 
     } 
     return isValide;
   }
 }


