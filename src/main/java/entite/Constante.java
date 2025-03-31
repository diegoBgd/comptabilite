package entite;

import java.io.Serializable;

public class Constante implements Serializable {
	private static final long serialVersionUID = 3243517419620766126L;

	public enum Fonction {
		GestionAcces, ParametreGeneraux, ParametreEfi, Journal, PlanComptable, SaisieEcritures, EditionEfi,
		EditionHistoriques, SaisieImmo, Amortissement, Cession, ParametrageFinance, EntreeFond, ReglementClient,
		FactureFourn, ReglementFournisseur, AutresDepenses;
	}

	public static String getLibelle(Fonction fx) {
		String valeur = "";
		switch (fx) {
		case GestionAcces:
			valeur = "Drois d'acc�s";
			break;
		case ParametreGeneraux:
			valeur = "Param�tres g�n�raux";
			break;
		case ParametreEfi:
			valeur = "Param�trage des �tats financiers";
			break;
		case Journal:
			valeur = "Param�trage des journaux";
			break;
		case PlanComptable:
			valeur = "Param�trage du plan comptable";
			break;
		case SaisieEcritures:
			valeur = "Saisie des �critures comptables";
			break;
		case EditionEfi:
			valeur = "Edition des �tats financiers";
			break;
		case EditionHistoriques:
			valeur = "Editions des op�rations comptables";
			break;
		case SaisieImmo:
			valeur = "Saisie des immobilis�s";
			break;
		case Amortissement:
			valeur = "G�rer les amortissement";
			break;
		case Cession:
			valeur = "Cession des immo";
			break;
		case ParametrageFinance:
			valeur = "Param�trage des gestions financi�res";
			break;
		case EntreeFond:
			valeur = "Saisie des entr�es de fonds";
			break;
		case ReglementClient:
			valeur = "Saisie des r�glement clients";
			break;
		case FactureFourn:
			valeur = "Saisie des factures fournisseurs";
			break;
		case ReglementFournisseur:
			valeur = "Saisie des r�glements fournisseurs";
			break;
		case AutresDepenses:
			valeur = "Autres d�penses";
			break;
		}
		return valeur;
	}

	public static Fonction getRole(int index) {
		Fonction valeur = null;
		switch (index) {
		case 0:
			valeur = Fonction.GestionAcces;
			break;
		case 1:
			valeur = Fonction.ParametreGeneraux;
			break;
		case 2:
			valeur = Fonction.ParametreEfi;
			break;
		case 3:
			valeur = Fonction.Journal;
			break;
		case 4:
			valeur = Fonction.PlanComptable;
			break;
		case 5:
			valeur = Fonction.SaisieEcritures;
			break;
		case 6:
			valeur = Fonction.EditionEfi;
			break;
		case 7:
			valeur = Fonction.EditionHistoriques;
			break;
		case 8:
			valeur = Fonction.SaisieImmo;
			break;
		case 9:
			valeur = Fonction.Amortissement;
			break;
		case 10:
			valeur = Fonction.Cession;
			break;
		case 11:
			valeur = Fonction.ParametrageFinance;
			break;
		case 12:
			valeur = Fonction.EntreeFond;
			break;
		case 13:
			valeur = Fonction.ReglementClient;
			break;
		case 14:
			valeur = Fonction.FactureFourn;
			break;
		case 15:
			valeur = Fonction.ReglementFournisseur;
			break;
		case 16:
			valeur = Fonction.AutresDepenses;
			break;
		}

		return valeur;
	}
}
