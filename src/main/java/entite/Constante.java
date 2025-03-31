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
			valeur = "Drois d'accès";
			break;
		case ParametreGeneraux:
			valeur = "Paramètres généraux";
			break;
		case ParametreEfi:
			valeur = "Paramétrage des états financiers";
			break;
		case Journal:
			valeur = "Paramétrage des journaux";
			break;
		case PlanComptable:
			valeur = "Paramétrage du plan comptable";
			break;
		case SaisieEcritures:
			valeur = "Saisie des écritures comptables";
			break;
		case EditionEfi:
			valeur = "Edition des états financiers";
			break;
		case EditionHistoriques:
			valeur = "Editions des opérations comptables";
			break;
		case SaisieImmo:
			valeur = "Saisie des immobilisés";
			break;
		case Amortissement:
			valeur = "Gérer les amortissement";
			break;
		case Cession:
			valeur = "Cession des immo";
			break;
		case ParametrageFinance:
			valeur = "Paramétrage des gestions financières";
			break;
		case EntreeFond:
			valeur = "Saisie des entrées de fonds";
			break;
		case ReglementClient:
			valeur = "Saisie des règlement clients";
			break;
		case FactureFourn:
			valeur = "Saisie des factures fournisseurs";
			break;
		case ReglementFournisseur:
			valeur = "Saisie des règlements fournisseurs";
			break;
		case AutresDepenses:
			valeur = "Autres dépenses";
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
