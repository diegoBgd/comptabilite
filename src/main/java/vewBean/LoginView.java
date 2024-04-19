 package vewBean;
 
 import entite.Exercice;
 import entite.User;
 import java.io.IOException;
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.List;
 import javax.annotation.PostConstruct;
 import javax.faces.application.FacesMessage;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import javax.faces.context.FacesContext;
 import javax.faces.event.ValueChangeEvent;
 import javax.faces.model.SelectItem;
 import javax.servlet.http.HttpSession;
 import model.ExerciceModel;
 import model.UserModel;
 import org.hibernate.SessionFactory;
 import persistances.DBConfiguration;
 import utils.HelperC;
 
 
 
 
 @ManagedBean
 @ViewScoped
 public class LoginView
   implements Serializable
 {
   private static final long serialVersionUID = 4203162759561809161L;
   private String code;
   private String pwd;
   private String message;
   private String codeExercice;
   private boolean userExist;
   private int idExercice;
   private List<SelectItem> listExerc;
   UserModel model;
   HttpSession session;
   User utilisateur;
   Exercice exerc;
   static String exerCode;
   static String nameUser;
   SessionFactory factory = DBConfiguration.getSessionFactory();
 
 
 
 
   
   public String getCode() {
     return this.code;
   }
   
   public void setCode(String code) {
     this.code = code;
   }
   
   public String getPwd() {
     return this.pwd;
   }
   
   public void setPwd(String pwd) {
     this.pwd = pwd;
   }
   
   public String getMessage() {
     return this.message;
   }
   
   public void setMessage(String message) {
     this.message = message;
   }
   
   public boolean isUserExist() {
     return this.userExist;
   }
   
   public void setUserExist(boolean userExist) {
     this.userExist = userExist;
   }
   public String getCodeExercice() {
     return this.codeExercice;
   }
   
   public void setCodeExercice(String codeExercice) {
     this.codeExercice = codeExercice;
   }
   
   public List<SelectItem> getListExerc() {
     return this.listExerc;
   }
   
   public void setListExerc(List<SelectItem> listExerc) {
     this.listExerc = listExerc;
   }
   public int getIdExercice() {
     return this.idExercice;
   }
   
   public void setIdExercice(int idExercice) {
     this.idExercice = idExercice;
   }
 
   
   @PostConstruct
   public void initialize() {
     this.model = new UserModel();
     this.listExerc = new ArrayList<>();
     this.listExerc.add(new SelectItem(Integer.valueOf(0), ""));
     this.userExist = this.model.isUserExist(this.factory);
     List<Exercice> list = (new ExerciceModel()).getListExercice(this.factory);
     if (list != null && list.size() > 0)
     {
       for (Exercice ex : list) {
         this.listExerc.add(new SelectItem(Integer.valueOf(ex.getId()), ex.getExCode()));
       }
     }
   }
 
   
   public void changeExercice(ValueChangeEvent event) {
     if (event != null) {
       
       this.idExercice = ((Integer)event.getNewValue()).intValue();
       this.exerc = (new ExerciceModel()).getExerciceById(this.idExercice, this.factory);
       if (this.exerc != null) {
         
         setCodeExercice(this.exerc.getExCode());
         exerCode = getCodeExercice();
       } 
     } 
   }
   public void login() throws IOException {
     if (this.userExist) {
       
       if (getCode().trim().equals("") || getPwd().trim().equals(""))
       {
         HelperC.afficherMessage("Information", "Veuillez préciser votre code utilisateur et mot de passe !", FacesMessage.SEVERITY_ERROR);
       
       }
       else
       {
         this.utilisateur = this.model.getUSerByLogin(this.factory, this.code, this.pwd);
         
         if (this.utilisateur != null) {
           
           if (this.utilisateur.getId() > 0) {
             nameUser = this.utilisateur.getUserName();
             FacesContext context = FacesContext.getCurrentInstance();
             HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
             session.setAttribute("operateur", this.code);
             session.setAttribute("exercice", this.codeExercice);
             session.setAttribute("existUser", Boolean.valueOf(this.userExist));
             
             if (getCodeExercice() == null || getCodeExercice().equals("")) {
               
               HelperC.afficherMessage("Information", "Il préciser l'exercice!", FacesMessage.SEVERITY_ERROR);
               
               return;
             } 
             context.getExternalContext().redirect("/comptabilite/masterPage.jsf");
           }
           else {
             
             HelperC.afficherMessage("Avertissement", "Echéc d'authentification", FacesMessage.SEVERITY_ERROR);
           } 
         } else {
           
           HelperC.afficherMessage("Information", "L'utilisateur n'est pas reconnu par le système!", FacesMessage.SEVERITY_ERROR);
         }
       
       }
     
     } else {
       
       FacesContext context = FacesContext.getCurrentInstance();
       this.session = (HttpSession)context.getExternalContext().getSession(true);
       this.session.setAttribute("operateur", " ");
       this.session.setAttribute("exercice", " ");
       this.session.setAttribute("existUser", Boolean.valueOf(this.userExist));
       context.getExternalContext().redirect("/comptabilite/masterPage.jsf");
     } 
   }
   public void logOut() {
     FacesContext context = FacesContext.getCurrentInstance();
     HttpSession session = (HttpSession)context.getExternalContext().getSession(true);
     session.invalidate();
 
     
     try {
       context.getExternalContext().redirect("/comptabilite/login.xhtml");
     }
     catch (IOException e) {
       
       e.printStackTrace();
     } 
   }
 
   
   public static String GetProperties() {
     return nameUser;
   }
   public static String getNumero() {
     return exerCode;
   }
 }


