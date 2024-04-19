 package entite;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
 
 
 
 
 @Entity
 @Table(name = "tb_fonction_role")
 public class FonctionRole
   implements Serializable
 {
   private static final long serialVersionUID = -834065564303947934L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "rl_id")
   private int id;
   @Column(name = "libelle")
   private String libelleRole;
   @Column(name = "ref_role")
   private String codeRole;
   @Column(name = "acces")
   private String acces;
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public String getLibelleRole() {
     return this.libelleRole;
   }
   
   public void setLibelleRole(String libelleRole) {
     this.libelleRole = libelleRole;
   }
   
   public String getCodeRole() {
     return this.codeRole;
   }
   
   public void setCodeRole(String codeRole) {
     this.codeRole = codeRole;
   }
   
   public String getAcces() {
     return this.acces;
   }
   
   public void setAcces(String acces) {
     this.acces = acces;
   }
 }


