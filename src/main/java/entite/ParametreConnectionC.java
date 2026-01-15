package entite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public class ParametreConnectionC implements Serializable {
	private String nomUtilisateur;
	private String nomHost;
	private String nomBaseDeDonnee;

	public ParametreConnectionC() {
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext()
				.getContext();
		this.url = servletContext.getRealPath("/resources/dbc/connectionBD.txt");
	}

	private String motDePasse;
	private static final long serialVersionUID = 5659574157386814683L;
	private String content;
	private String url;

	public String getNomUtilisateur() {
		return this.nomUtilisateur;
	}

	public void setNomUtilisateur(String nomUtilisateur) {
		this.nomUtilisateur = nomUtilisateur;
	}

	public String getNomHost() {
		return this.nomHost;
	}

	public void setNomHost(String nomHost) {
		this.nomHost = nomHost;
	}

	public String getNomBaseDeDonnee() {
		return this.nomBaseDeDonnee;
	}

	public void setNomBaseDeDonnee(String nomBaseDeDonnee) {
		this.nomBaseDeDonnee = nomBaseDeDonnee;
	}

	public String getMotDePasse() {
		return this.motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String enregistrer() {
		String msg = "";
		if (!this.nomUtilisateur.equalsIgnoreCase("") && !this.motDePasse.equalsIgnoreCase("")
				&& !this.nomHost.equalsIgnoreCase("") && !this.nomBaseDeDonnee.equalsIgnoreCase("")) {

			try {

				this.content = String.valueOf(this.nomHost.trim()) + " " + this.nomUtilisateur.trim() + " "
						+ this.motDePasse.trim() + " " + this.nomBaseDeDonnee.trim();

				File file = new File(this.url);
				if (!file.exists())
					file.createNewFile();
				FileWriter fw = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(this.content);
				bw.close();
				msg = "Opération réussie !";
			} catch (Exception e) {

				msg = "Opération échouée=>" + e.getMessage();
			}
		}
		return msg;
	}

	public boolean readChaine() {
		boolean ch = false;
		try {
			File file = new File(this.url);
			this.content = "";

			if (file.exists()) {

				Scanner sc = new Scanner(file);

				if (sc.hasNextLine()) {
					this.content = sc.nextLine();
				}
				String[] info = this.content.split(" ");
				setNomHost(info[0]);
				setNomUtilisateur(info[1]);
				setMotDePasse(info[2]);
				setNomBaseDeDonnee(info[3]);
				ch = true;
			}

		} catch (IOException e) {
			ch = false;
			e.printStackTrace();
		}
		return ch;
	}

}
