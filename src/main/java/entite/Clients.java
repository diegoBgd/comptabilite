 package entite;
 
 import java.io.Serializable;
 import javax.persistence.Column;
 import javax.persistence.Entity;
 import javax.persistence.GeneratedValue;
 import javax.persistence.GenerationType;
 import javax.persistence.Id;
 import javax.persistence.Table;
 
 
 
 @Entity
 @Table(name = "tb_client")
 public class Clients
   implements Serializable
 {
   private static final long serialVersionUID = 786328857731517968L;
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "clt_id")
   private int id;
   @Column(name = "reference")
   private String codeClt;
   @Column(name = "libelle")
   private String nameClt;
   @Column(name = "compte")
   private String codeCpbl;
   
   public int getId() {
     return this.id;
   }
   
   public void setId(int id) {
     this.id = id;
   }
   
   public String getCodeClt() {
     return this.codeClt;
   }
   
   public void setCodeClt(String codeClt) {
     this.codeClt = codeClt;
   }
   
   public String getNameClt() {
     return this.nameClt;
   }
   
   public void setNameClt(String nameClt) {
     this.nameClt = nameClt;
   }
   
   public String getCodeCpbl() {
     return this.codeCpbl;
   }
   
   public void setCodeCpbl(String codeCpbl) {
     this.codeCpbl = codeCpbl;
   }
 }


