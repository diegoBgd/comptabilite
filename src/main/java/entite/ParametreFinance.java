package entite;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_parametre_finance")
public class ParametreFinance implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5032340674940476757L;

	 @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   @Column(name = "id_parm")
	   private int idParam;
	 
	   @Column(name = "compte_cheq_enc")
	   private String compteChqEnc;
	 
	   @Column(name = "compte_cheq_em")
	   private String compteChqEm;
	 
	   @Column(name = "compte_cash")
	   private String compteCash;
	 
	   @Column(name = "compte_virement")
	   private String compteVirInt;
	 
	   
		public ParametreFinance() {
			
		}


		public int getIdParam() {
			return idParam;
		}


		public void setIdParam(int idParam) {
			this.idParam = idParam;
		}


		public String getCompteChqEnc() {
			return compteChqEnc;
		}


		public void setCompteChqEnc(String compteChqEnc) {
			this.compteChqEnc = compteChqEnc;
		}


		public String getCompteChqEm() {
			return compteChqEm;
		}


		public void setCompteChqEm(String compteChqEm) {
			this.compteChqEm = compteChqEm;
		}


		public String getCompteCash() {
			return compteCash;
		}


		public void setCompteCash(String compteCash) {
			this.compteCash = compteCash;
		}


		public String getCompteVirInt() {
			return compteVirInt;
		}


		public void setCompteVirInt(String compteVirInt) {
			this.compteVirInt = compteVirInt;
		}
	

}
