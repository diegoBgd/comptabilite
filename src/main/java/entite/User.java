 package entite;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.JoinColumn;
 import javax.persistence.ManyToOne;
 import javax.persistence.Table;
 
 @Entity
 @Table(name = "tb_user")
 public class User
   implements Serializable
 {
   private static final long serialVersionUID = 9145205938273311326L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "user_id")
   private int id;
   @Column(name = "reference")
   private String userCod;
   @Column(name = "passeword")
   private String userPwd;
   @Column(name = "libelle")
   private String userName;
   @Column(name = "email")
   private String eMail;
   @Column(name = "actif")
   private boolean actif;
   @ManyToOne
   @JoinColumn(name = "role")
   private FonctionRole role;
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public String getUserCod() {
     return this.userCod;
   }
   
   public void setUserCod(String userCod) {
     this.userCod = userCod;
   }
   
   public String getUserPwd() {
     return this.userPwd;
   }
   
   public void setUserPwd(String userPwd) {
     this.userPwd = userPwd;
   }
   
   public String getUserName() {
     return this.userName;
   }
   
   public void setUserName(String userName) {
     this.userName = userName;
   }
   
   public boolean isActif() {
     return this.actif;
   }
   
   public void setActif(boolean actif) {
     this.actif = actif;
   }
   
   public String geteMail() {
     return this.eMail;
   }
   
   public void seteMail(String eMail) {
     this.eMail = eMail;
   }
   
   public FonctionRole getRole() {
     return this.role;
   }
   
   public void setRole(FonctionRole role) {
     this.role = role;
   }
 }


