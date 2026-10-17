package entite;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_parametre_journaux")
public class ParametreJournaux implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1045502683589209949L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "parm_jrn_id")
	private int id;
	
	@Column(name = "code_journal")
	private String codeJournal;
	
	@Column(name = "type_operation")
	@Enumerated(EnumType.STRING)
	private TypeEcriture typeOperation;

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodeJournal() {
		return codeJournal;
	}

	public void setCodeJournal(String codeJournal) {
		this.codeJournal = codeJournal;
	}

	public TypeEcriture getTypeOperation() {
		return typeOperation;
	}

	public void setTypeOperation(TypeEcriture typeOperation) {
		this.typeOperation = typeOperation;
	}

}
