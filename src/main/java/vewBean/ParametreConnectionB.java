package vewBean;

import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import entite.ParametreConnectionC;
import persistances.DBConfiguration;


@ManagedBean
@ViewScoped
public class ParametreConnectionB implements Serializable {
    
    private static final long serialVersionUID = -6756194751191247346L;
    
    private String user;
    private String pwd;
    private String serveur;
    private String dataBase;
    private String infoMsg;
    private boolean showMsg;
    private boolean testSuccess;
    
    // ⚠️ NE PAS étendre ParametreConnectionC
    // On utilise la composition, pas l'héritage
    private ParametreConnectionC parametre;
    
    @PostConstruct
    public void init() {  // ← doit être public !
        this.parametre = new ParametreConnectionC();
        chargerParameter();
    }
    
    private void chargerParameter() {
        try {
            if (this.parametre.readChaine()) {
                this.serveur = this.parametre.getNomHost();
                this.dataBase = this.parametre.getNomBaseDeDonnee();
                this.user = this.parametre.getNomUtilisateur();
                this.pwd = this.parametre.getMotDePasse();
            }
        } catch (Exception e) {
            // Fichier inexistant → on laisse les champs vides
        }
    }
    
    public void testConnection() {
        this.showMsg = false;
        if (isBlank(serveur) || isBlank(user) || isBlank(pwd) || isBlank(dataBase)) {
            this.infoMsg = "Veuillez remplir tous les champs !";
            this.testSuccess = false;
            this.showMsg = true;
            return;
        }
        
        // Test réel avec les valeurs du formulaire
        boolean ok = DBConfiguration.testConnection(serveur, user, pwd, dataBase);
        if (ok) {
            this.infoMsg = "✅ Connexion réussie !";
            this.testSuccess = true;
        } else {
            this.infoMsg = "❌ Connexion échouée. Vérifiez vos paramètres.";
            this.testSuccess = false;
        }
        this.showMsg = true;
    }
    
    public void save() {
        this.showMsg = false;
        
        if (isBlank(serveur) || isBlank(user) || isBlank(pwd) || isBlank(dataBase)) {
            this.infoMsg = "Veuillez remplir tous les champs !";
            this.showMsg = true;
            return;
        }
        
        this.parametre.setNomHost(this.serveur);
        this.parametre.setNomUtilisateur(this.user);
        this.parametre.setMotDePasse(this.pwd);
        this.parametre.setNomBaseDeDonnee(this.dataBase);
        
        this.infoMsg = this.parametre.enregistrer();
        
        // 🔑 RÉINITIALISER la SessionFactory avec la nouvelle config
        DBConfiguration.resetSessionFactory();
        
        if (DBConfiguration.getSessionFactory() != null) {
            this.infoMsg += " Connexion à la base établie !";
            this.testSuccess = true;
        } else {
            this.infoMsg += " Mais la connexion a échoué.";
            this.testSuccess = false;
        }
        this.showMsg = true;
    }
    
    public void returnLogin() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            context.getExternalContext().redirect(
                context.getExternalContext().getRequestContextPath() + "/login.jsf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
    
    // --- Getters / Setters ---
    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
    public String getPwd() { return pwd; }
    public void setPwd(String pwd) { this.pwd = pwd; }
    public String getServeur() { return serveur; }
    public void setServeur(String serveur) { this.serveur = serveur; }
    public String getDataBase() { return dataBase; }
    public void setDataBase(String dataBase) { this.dataBase = dataBase; }
    public String getInfoMsg() { return infoMsg; }
    public void setInfoMsg(String infoMsg) { this.infoMsg = infoMsg; }
    public boolean isShowMsg() { return showMsg; }
    public void setShowMsg(boolean showMsg) { this.showMsg = showMsg; }
    public boolean isTestSuccess() { return testSuccess; }
    public void setTestSuccess(boolean testSuccess) { this.testSuccess = testSuccess; }
}