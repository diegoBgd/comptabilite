 package entite;
 
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import utils.HelperC;
  
 
 public class CalculAmortissement
   implements Serializable
 {
   private static final long serialVersionUID = 6169389925041588711L;
   private List<Amortissement> listAmort;
   private Amortissement amortissement;
   private List<Ecriture> listEcriture;
   
   public List<Amortissement> getListAmort() {
     return this.listAmort;
   }
   
   public void setListAmort(List<Amortissement> listAmort) {
     this.listAmort = listAmort;
   }
   
   public Amortissement getAmortissement() {
     return this.amortissement;
   }
   
   public void setAmortissement(Amortissement amortissement) {
     this.amortissement = amortissement;
   }
   
   public List<Ecriture> getListEcriture() {
     return this.listEcriture;
   }
   
   public void setListEcriture(List<Ecriture> listEcriture) {
     this.listEcriture = listEcriture;
   }
 
   
   public List<Amortissement> tableauAmortissemet(Immobilise immo, int nbDec, Exercice exercice) {
     this.listAmort = new ArrayList<>();
     
     int dureeAmort = 0, daysAmort = 0, year = 0;
     double taux = 0.0D, baseAmort = 0.0D, tauxDegrs = 0.0D, cumul = 0.0D;
     Date dateFinAmort = HelperC.addYear(immo.getDateAcquisition(), immo.getNbrAnne());
     Date yearEnd = HelperC.toDate(HelperC.getYearFromDate(immo.getDateAcquisition()), 12, 31);
     
     baseAmort = immo.getMontantAcq();
     
     year = HelperC.getYearFromDate(immo.getDateAcquisition());
 
     
     for (int i = 0; i <= immo.getNbrAnne(); i++) {
       
       this.amortissement = new Amortissement();
       dureeAmort = immo.getNbrAnne() - i;
       
       if (immo.getNbrAnne() > 0)
         taux = (100 / immo.getNbrAnne()); 
       if (dureeAmort > 0) {
         tauxDegrs = (100 / dureeAmort);
       }
       
       if (i == 0) {
 
         
         daysAmort = HelperC.daysbetween(immo.getDateAcquisition(), yearEnd);
         
         this.amortissement.setAmortExercice(calculAnnuite(daysAmort, baseAmort, taux, 360, nbDec));
         
         cumul += this.amortissement.getAmortExercice();
         
         this.amortissement.setCumul(cumul);
         this.amortissement.setVnc(immo.getMontantAcq() - this.amortissement.getCumul());
       } 
       
       if (i > 0 && year != HelperC.getYearFromDate(dateFinAmort)) {
         
         if (immo.getTypeAmort() == 1) {
           
           baseAmort = immo.getMontantAcq() - cumul;
           if (tauxDegrs > taux) {
             taux = tauxDegrs;
           }
         } 
         this.amortissement.setAmortExercice(calculAnnuite(360, baseAmort, taux, 360, nbDec));
         cumul += this.amortissement.getAmortExercice();
         this.amortissement.setCumul(cumul);
         this.amortissement.setVnc(immo.getMontantAcq() - this.amortissement.getCumul());
       } 
 
       
       if (i > 0 && year == HelperC.getYearFromDate(dateFinAmort)) {
         
         this.amortissement.setAmortExercice(immo.getMontantAcq() - cumul);
         cumul += this.amortissement.getAmortExercice();
         this.amortissement.setCumul(cumul);
         this.amortissement.setVnc(immo.getMontantAcq() - this.amortissement.getCumul());
       } 
 
       
       this.amortissement.setBaseAmortissement(baseAmort);
       this.amortissement.setAmrtAnterieur(immo.getMontantAcq() - this.amortissement.getVnc() + this.amortissement.getAmortExercice());
       this.amortissement.setTauxAmrt(taux);
       this.amortissement.setNumExercice(String.valueOf(year));
       year++;
       if (this.amortissement.getAmortExercice() > 0.0D)
         this.listAmort.add(this.amortissement); 
     } 
     cumul = 0.0D;
     return this.listAmort;
   }
 
   
   private double calculAnnuite(int nbJrs, double baseAmort, double taux, int nbrJrAnnee, int nbrDecimal) {
     double valeur = 0.0D;
     if (nbrJrAnnee > 0)
       valeur = baseAmort * nbJrs * taux / nbrJrAnnee / 100.0D; 
     return Math.round(valeur);
   }
 
   
   public void listEcriture(Immobilise immo, Journal jrnl, int idExercice, String libelle, Date dateEcr, User user) {
     Ecriture ecr = null;
     this.listEcriture = new ArrayList<>();
     ecr = new Ecriture();
     ecr.setCpte(immo.getCompteDotAmrt());
     ecr.setJrnl(jrnl.getCodeJrnl());
     ecr.setDateOperation(dateEcr);
     ecr.setDebit(immo.getAmortEncour());
     ecr.setCredit(0.0D);
     ecr.setIdExercise(idExercice);
     ecr.setLibelle(libelle);
     ecr.setTypeOperation(TypeEcriture.amortissement.ordinal());
     ecr.setPieceCpb("");
     ecr.setSourceOperation(0);
     this.listEcriture.add(ecr);
     
     ecr = new Ecriture();
     ecr.setCpte(immo.getCompteAmrt());
     ecr.setJrnl(jrnl.getCodeJrnl());
     ecr.setDateOperation(dateEcr);
     ecr.setDebit(0.0D);
     ecr.setCredit(immo.getAmortEncour());
     ecr.setIdExercise(idExercice);
     ecr.setLibelle(libelle);
     ecr.setTypeOperation(TypeEcriture.amortissement.ordinal());
     ecr.setPieceCpb("");
     ecr.setSourceOperation(0);
     this.listEcriture.add(ecr);
   }
   
   public Amortissement amortissementEncours(String year) {
     Amortissement amrt = null;
     for (Amortissement am : this.listAmort) {
       
       if (am.getNumExercice().equals(year)) {
         
         amrt = am;
         break;
       } 
     } 
     return amrt;
   }
 }


