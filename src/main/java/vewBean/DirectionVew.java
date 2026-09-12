package vewBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;

import entite.Direction;
import entite.Exercice;
import entite.User;
import model.DirectionModel;
import model.ExerciceModel;
import model.UserModel;
import persistances.DBConfiguration;
import utils.HelperC;

@ManagedBean
@ViewScoped
public class DirectionVew implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7796296769171004698L;

	SessionFactory factory = DBConfiguration.getSessionFactory();
	
	private boolean disableMsg;
    private Direction selectedDirection;
    private List<Direction>listeDirection;
	private String code;
	private String designation;
	
	int idDir= 0;
	DirectionModel model;
	Exercice selecetdExercice;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;
	
	public DirectionVew() {
		
	}

	public boolean isDisableMsg() {
		return disableMsg;
	}

	public void setDisableMsg(boolean disableMsg) {
		this.disableMsg = disableMsg;
	}

	public Direction getSelectedDirection() {
		return selectedDirection;
	}

	public void setSelectedDirection(Direction selectedDirection) {
		this.selectedDirection = selectedDirection;
	}

	public List<Direction> getListeDirection() {
		return listeDirection;
	}

	public void setListeDirection(List<Direction> listeDirection) {
		this.listeDirection = listeDirection;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}
	@PostConstruct
	public void initialize() {
		this.model = new DirectionModel();
		this.disableMsg = true;
		chargementSession();
		chargerDirection();
	}

	private void chargementSession() {
		this.session = HelperC.getSession();
		if (this.session != null) {
			this.exerCode = (String) this.session.getAttribute("exercice");
			this.currUserCode = (String) this.session.getAttribute("operateur");

			if (this.exerCode != null) {
				this.selecetdExercice = (new ExerciceModel()).getExercByCode(this.factory, this.exerCode);
			}
			if (this.currUserCode != null) {
				this.currentUser = (new UserModel()).getUserByCode(this.factory, this.currUserCode);
			}

			if (this.currentUser == null || this.selecetdExercice == null) {

				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("/comptabilite/direction.xhtml");
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}
	}
	private void chargerDirection() {
		this.listeDirection = this.model.getListeDirection(this.factory);
	}

	public void searchDirection() {
		if (this.code != null && !this.code.equals("")) {

			this.selectedDirection = this.model.getDirection(this.factory, this.code);
			if (this.selectedDirection != null) {
				setDirectionValues();
			}
		}
	}
	private void setDirectionValues() {
		if(selectedDirection!=null) {
		this.idDir = this.selectedDirection.getId();
		this.code = this.selectedDirection.getCode();
		this.designation = this.selectedDirection.getLibelle();
		
		this.disableMsg = false;
		}
	}

	public void takeSelectedDirection() {
		setDirectionValues();
	}
	public void save() {
		if(selectedDirection==null)
			selectedDirection=new Direction();
		else
			selectedDirection.setId(idDir);
		selectedDirection.setCode(code);
		selectedDirection.setLibelle(designation);
		model.saveDirection(factory, selectedDirection);
		chargerDirection();
		initializeControls();
	}
	
	public void delete() {
		System.out.print(selectedDirection.getCode());
		if(selectedDirection!=null) {
			
			model.deleteDirection(factory, selectedDirection);
			chargerDirection();
			initializeControls();
		}
	}
	public void initializeControls() {
		idDir=0;
		code="";
		designation="";
		selectedDirection=null;
	}

}
