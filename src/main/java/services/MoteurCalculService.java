package services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import dto.LigneEtatDTO;
import entite.Ecriture;
import entite.EtatFinanciers;
import entite.EtatFinanciersCompte;
import entite.EtatFinanciersRubrique;
import entite.MappingExclusion;
import entite.MappingInclusion;
import enumeration.NatureValeur;
import enumeration.TypeCalcul;
import enumeration.TypeValeur;

public class MoteurCalculService {

	private final SessionFactory factory;

	public MoteurCalculService(SessionFactory factory) {
		this.factory = factory;
	}

	// ============================================================
	// MÉTHODE PRINCIPALE
	// ============================================================
	public List<LigneEtatDTO> calculerEtat(Long idEtat, int exercice) {

		List<LigneEtatDTO> resultat = new ArrayList<LigneEtatDTO>();
		Session ss = null;

		try {
			ss = factory.openSession();
			ss.beginTransaction();

			// 1. Charger l'état
			EtatFinanciers etat = (EtatFinanciers) ss.get(EtatFinanciers.class, idEtat);
			if (etat == null) {
				ss.getTransaction().commit();
				return resultat;
			}

			// 2. Charger les rubriques triées
			Query qRub = ss.createQuery("SELECT DISTINCT R FROM EtatFinanciersRubrique R "
					+ "WHERE R.etat.idEtat = :idEtat ORDER BY R.ordreAffichage ASC");
			qRub.setParameter("idEtat", idEtat);
			@SuppressWarnings("unchecked")
			List<EtatFinanciersRubrique> rubriques = qRub.getResultList();

			// 3. Stockage des valeurs
			Map<String, BigDecimal> valeursN = new HashMap<String, BigDecimal>();
			Map<String, BigDecimal> valeursN1 = new HashMap<String, BigDecimal>();

			// ============================================================
			// PASSE 1 : Rubriques DIRECTES
			// ============================================================
			for (EtatFinanciersRubrique r : rubriques) {
				if (r.getTypeCalcul() == TypeCalcul.DIRECT) {
					calculerRubriqueDirecte(ss, r, exercice, valeursN, valeursN1);
				}
				// Sécurité : on s'assure que TOUTES les rubriques sont dans la map (même à 0)
				valeursN.putIfAbsent(r.getCodeRubrique(), BigDecimal.ZERO);
				valeursN1.putIfAbsent(r.getCodeRubrique(), BigDecimal.ZERO);

				valeursN.putIfAbsent(r.getCodeRubrique() + "_BRUT", BigDecimal.ZERO);
				valeursN.putIfAbsent(r.getCodeRubrique() + "_AMORT", BigDecimal.ZERO);
				valeursN.putIfAbsent(r.getCodeRubrique() + "_NET", BigDecimal.ZERO);
				valeursN1.putIfAbsent(r.getCodeRubrique() + "_NET", BigDecimal.ZERO);
			}

			// ============================================================
			// PASSE 2 : AGREGE et FORMULE
			// ============================================================
			for (EtatFinanciersRubrique r : rubriques) {
				if (r.getTypeCalcul() == TypeCalcul.AGREGE || r.getTypeCalcul() == TypeCalcul.FORMULE) {

					BigDecimal valN = BigDecimal.ZERO;
					BigDecimal valN1 = BigDecimal.ZERO;

					if (r.getTypeCalcul() == TypeCalcul.FORMULE && r.getFormule() != null
							&& !r.getFormule().isEmpty()) {
						// Calcul par Formule (ex: AI1+AI2+AI3)
						valN = evaluerFormule(r.getFormule(), valeursN);
						valN1 = evaluerFormule(r.getFormule(), valeursN1);
					} else {
						// Calcul par Agrégation (via Rubrique Parente)
						Query qEnfants = ss.createQuery(
								"SELECT R FROM EtatFinanciersRubrique R WHERE R.rubriqueParent.idRubrique = :id");
						qEnfants.setParameter("id", r.getIdRubrique());
						@SuppressWarnings("unchecked")
						List<EtatFinanciersRubrique> enfants = qEnfants.getResultList();

						for (EtatFinanciersRubrique enfant : enfants) {
							BigDecimal vN = valeursN.get(enfant.getCodeRubrique());
							BigDecimal vN1 = valeursN1.get(enfant.getCodeRubrique());
							if (vN != null)
								valN = valN.add(vN);
							if (vN1 != null)
								valN1 = valN1.add(vN1);
						}
					}

					// Sauvegarde
					valeursN.put(r.getCodeRubrique(), valN);
					valeursN1.put(r.getCodeRubrique(), valN1);

					// Gestion des colonnes Brut / Amort / Net pour les totaux (ex: TAI)
					if (r.getAfficheAmortissements() != null && r.getAfficheAmortissements()) {
						BigDecimal totalBrutN = BigDecimal.ZERO;
						BigDecimal totalAmortN = BigDecimal.ZERO;
						BigDecimal totalNetN = BigDecimal.ZERO;
						BigDecimal totalNetN1 = BigDecimal.ZERO;

						if (r.getTypeCalcul() == TypeCalcul.FORMULE && r.getFormule() != null
								&& !r.getFormule().isEmpty()) {
							Map<String, BigDecimal> mapBrut = getMapAvecSuffixe(valeursN, "_BRUT");
							Map<String, BigDecimal> mapAmort = getMapAvecSuffixe(valeursN, "_AMORT");
							Map<String, BigDecimal> mapNet = getMapAvecSuffixe(valeursN, "_NET");
							Map<String, BigDecimal> mapNetN1 = getMapAvecSuffixe(valeursN1, "_NET");

							totalBrutN = evaluerFormule(r.getFormule(), mapBrut);
							totalAmortN = evaluerFormule(r.getFormule(), mapAmort);
							totalNetN = evaluerFormule(r.getFormule(), mapNet);
							totalNetN1 = evaluerFormule(r.getFormule(), mapNetN1);
						} else {
							Query qEnfants = ss.createQuery(
									"SELECT R FROM EtatFinanciersRubrique R WHERE R.rubriqueParent.idRubrique = :id");
							qEnfants.setParameter("id", r.getIdRubrique());
							@SuppressWarnings("unchecked")
							List<EtatFinanciersRubrique> enfants = qEnfants.getResultList();

							for (EtatFinanciersRubrique enfant : enfants) {
								totalBrutN = totalBrutN.add(
										valeursN.getOrDefault(enfant.getCodeRubrique() + "_BRUT", BigDecimal.ZERO));
								totalAmortN = totalAmortN.add(
										valeursN.getOrDefault(enfant.getCodeRubrique() + "_AMORT", BigDecimal.ZERO));
								totalNetN = totalNetN
										.add(valeursN.getOrDefault(enfant.getCodeRubrique() + "_NET", BigDecimal.ZERO));
								totalNetN1 = totalNetN1.add(
										valeursN1.getOrDefault(enfant.getCodeRubrique() + "_NET", BigDecimal.ZERO));
							}
						}

						valeursN.put(r.getCodeRubrique() + "_BRUT", totalBrutN);
						valeursN.put(r.getCodeRubrique() + "_AMORT", totalAmortN);
						valeursN.put(r.getCodeRubrique() + "_NET", totalNetN);
						valeursN1.put(r.getCodeRubrique() + "_NET", totalNetN1);
					}
				} else if (r.getTypeCalcul() == TypeCalcul.SAISIE) {
					valeursN.putIfAbsent(r.getCodeRubrique(), BigDecimal.ZERO);
					valeursN1.putIfAbsent(r.getCodeRubrique(), BigDecimal.ZERO);
				}
			}

			// ============================================================
			// PASSE 3 : Construction du DTO
			// ============================================================
			for (EtatFinanciersRubrique r : rubriques) {
				if (r.getVisible() != null && !r.getVisible())
					continue;

				LigneEtatDTO dto = new LigneEtatDTO();
				dto.setCode(r.getCodeRubrique());
				dto.setLibelle(r.getLibelleRubrique());
				dto.setTypeLigne(r.getTypeLigne());
				dto.setNiveau(r.getNiveau());
				dto.setVisible(true);
				dto.setAfficheAmortissements(r.getAfficheAmortissements() != null && r.getAfficheAmortissements());
				dto.setTypeValeur(r.getTypeValeur());

				BigDecimal valN = valeursN.get(r.getCodeRubrique());
				BigDecimal valN1 = valeursN1.get(r.getCodeRubrique());

				dto.setMontantN(valN != null ? valN : BigDecimal.ZERO);
				dto.setMontantN1(valN1 != null ? valN1 : BigDecimal.ZERO);
				dto.setVariation(dto.getMontantN().subtract(dto.getMontantN1()));

				if (dto.isAfficheAmortissements()) {
					dto.setBrutN(valeursN.getOrDefault(r.getCodeRubrique() + "_BRUT", BigDecimal.ZERO));
					dto.setAmortN(valeursN.getOrDefault(r.getCodeRubrique() + "_AMORT", BigDecimal.ZERO));
					dto.setNetN(valeursN.getOrDefault(r.getCodeRubrique() + "_NET", BigDecimal.ZERO));
					dto.setNetN1(valeursN1.getOrDefault(r.getCodeRubrique() + "_NET", BigDecimal.ZERO));

					// On force la valeur principale à être le Net
					dto.setMontantN(dto.getNetN());
					dto.setMontantN1(dto.getNetN1());
				}

				Map<String, BigDecimal> mapMontants = new HashMap<String, BigDecimal>();
				mapMontants.put("NET_N", dto.getMontantN());
				mapMontants.put("NET_N1", dto.getMontantN1());
				mapMontants.put("VARIATION", dto.getVariation());
				mapMontants.put("BRUT_N", dto.getBrutN());
				mapMontants.put("AMORT_N", dto.getAmortN());
				dto.setMontantsParColonne(mapMontants);

				resultat.add(dto);
			}

			ss.getTransaction().commit();

		} catch (Exception ex) {
			if (ss != null && ss.getTransaction().isActive())
				ss.getTransaction().rollback();
			ex.printStackTrace();
		} finally {
			if (ss != null)
				ss.close();
		}

		return resultat;
	}

	// ============================================================
	// CALCUL D'UNE RUBRIQUE DIRECTE
	// ============================================================
	private void calculerRubriqueDirecte(Session ss, EtatFinanciersRubrique r, int exercice,
			Map<String, BigDecimal> valeursN, Map<String, BigDecimal> valeursN1) {

		boolean afficheAmort = r.getAfficheAmortissements() != null && r.getAfficheAmortissements();

		BigDecimal totalBrutN = BigDecimal.ZERO, totalBrutN1 = BigDecimal.ZERO;
		BigDecimal totalAmortN = BigDecimal.ZERO, totalAmortN1 = BigDecimal.ZERO;
		BigDecimal totalNetN = BigDecimal.ZERO, totalNetN1 = BigDecimal.ZERO;

		Query qMap = ss.createQuery(
				"SELECT M FROM EtatFinanciersCompte M WHERE M.rubrique.idRubrique = :idRubrique ORDER BY M.ordre ASC");
		qMap.setParameter("idRubrique", r.getIdRubrique());
		@SuppressWarnings("unchecked")
		List<EtatFinanciersCompte> mappings = qMap.getResultList();

		for (EtatFinanciersCompte m : mappings) {
			BigDecimal montantN = executerMapping(ss, m, exercice);
			BigDecimal montantN1 = executerMapping(ss, m, exercice - 1);

			BigDecimal coef = m.getCoefficient() != null ? m.getCoefficient() : BigDecimal.ONE;
			BigDecimal signe = BigDecimal.valueOf(r.getSigne() != null ? r.getSigne() : 1);

			montantN = montantN.multiply(coef).multiply(signe);
			montantN1 = montantN1.multiply(coef).multiply(signe);

			NatureValeur nature = m.getNatureValeur() != null ? m.getNatureValeur() : NatureValeur.NET;

			if (afficheAmort) {
				if (nature == NatureValeur.BRUT) {
					totalBrutN = totalBrutN.add(montantN);
					totalBrutN1 = totalBrutN1.add(montantN1);
				} else if (nature == NatureValeur.AMORT) {
					totalAmortN = totalAmortN.add(montantN);
					totalAmortN1 = totalAmortN1.add(montantN1);
				} else {
					totalNetN = totalNetN.add(montantN);
					totalNetN1 = totalNetN1.add(montantN1);
				}
			} else {
				totalNetN = totalNetN.add(montantN);
				totalNetN1 = totalNetN1.add(montantN1);
			}
		}

		if (afficheAmort) {
			if (totalNetN.compareTo(BigDecimal.ZERO) == 0)
				totalNetN = totalBrutN.subtract(totalAmortN);
			if (totalNetN1.compareTo(BigDecimal.ZERO) == 0)
				totalNetN1 = totalBrutN1.subtract(totalAmortN1);

			valeursN.put(r.getCodeRubrique() + "_BRUT", totalBrutN);
			valeursN.put(r.getCodeRubrique() + "_AMORT", totalAmortN);
			valeursN.put(r.getCodeRubrique() + "_NET", totalNetN);
			valeursN1.put(r.getCodeRubrique() + "_BRUT", totalBrutN1);
			valeursN1.put(r.getCodeRubrique() + "_AMORT", totalAmortN1);
			valeursN1.put(r.getCodeRubrique() + "_NET", totalNetN1);
		}

		TypeValeur typeValeur = r.getTypeValeur() != null ? r.getTypeValeur() : TypeValeur.NET;
		BigDecimal principaleN, principaleN1;

		if (afficheAmort) {
			if (typeValeur == TypeValeur.BRUT) {
				principaleN = totalBrutN;
				principaleN1 = totalBrutN1;
			} else if (typeValeur == TypeValeur.AMORT) {
				principaleN = totalAmortN;
				principaleN1 = totalAmortN1;
			} else {
				principaleN = totalNetN;
				principaleN1 = totalNetN1;
			}
		} else {
			principaleN = totalNetN;
			principaleN1 = totalNetN1;
		}

		valeursN.put(r.getCodeRubrique(), principaleN);
		valeursN1.put(r.getCodeRubrique(), principaleN1);
	}

	// ============================================================
	// EVALUATION DE FORMULE
	// ============================================================
	private BigDecimal evaluerFormule(String formule, Map<String, BigDecimal> valeurs) {
		if (formule == null || formule.trim().isEmpty())
			return BigDecimal.ZERO;

		String expr = formule;

		// On trie les clés par longueur décroissante pour être sûr
		List<String> cles = new ArrayList<String>(valeurs.keySet());
		Collections.sort(cles, new Comparator<String>() {
			public int compare(String s1, String s2) {
				return s2.length() - s1.length();
			}
		});

		for (String code : cles) {
			BigDecimal val = valeurs.get(code);
			if (val != null) {
				// ⚡ LA CORRECTION EST ICI : On utilise Pattern.quote() pour sécuriser
				// et on exige que le code soit isolé par des frontières de mots (\b)
				expr = expr.replaceAll("\\b" + java.util.regex.Pattern.quote(code) + "\\b", val.toPlainString());
			}
		}

		// Sécurité au cas où une rubrique n'aurait pas été trouvée (pour éviter le
		// crash)
		expr = expr.replaceAll("[a-zA-Z_]", "0");
		// 4. Nettoyage des opérateurs (ex: si on a "0+0+0")
	    expr = expr.replaceAll("\\+\\+", "+")
	               .replaceAll("--", "+")
	               .replaceAll("\\+-", "-")
	               .replaceAll("-\\+", "-");
	    
		if (expr.endsWith("+") || expr.endsWith("-"))
			expr = expr.substring(0, expr.length() - 1);
		if (expr.startsWith("+") || expr.startsWith("-"))
			expr = "0" + expr;

		
		return evaluerExpressionSimple(expr);
	}

	private BigDecimal evaluerExpressionSimple(String expr) {
		try {
			String cleaned = expr.replace(" ", "");
			BigDecimal resultat = BigDecimal.ZERO;
			char operateur = '+';
			StringBuilder nombre = new StringBuilder();

			for (int i = 0; i < cleaned.length(); i++) {
				char c = cleaned.charAt(i);
				if (Character.isDigit(c) || c == '.') {
					nombre.append(c);
				} else if (c == '+' || c == '-') {
					if (nombre.length() > 0) {
						BigDecimal val = new BigDecimal(nombre.toString());
						resultat = (operateur == '+') ? resultat.add(val) : resultat.subtract(val);
						nombre.setLength(0);
					}
					operateur = c;
				}
			}
			if (nombre.length() > 0) {
				BigDecimal val = new BigDecimal(nombre.toString());
				resultat = (operateur == '+') ? resultat.add(val) : resultat.subtract(val);
			}
			return resultat;
		} catch (Exception e) {
			return BigDecimal.ZERO;
		}
	}

	// ============================================================
	// EXECUTION DU MAPPING
	// ============================================================
	private BigDecimal executerMapping(Session ss, EtatFinanciersCompte m, int exercice) {
		List<MappingInclusion> inclusions = ss
				.createQuery("SELECT I FROM MappingInclusion I WHERE I.mapping.idMapping = :id", MappingInclusion.class)
				.setParameter("id", m.getIdMapping()).getResultList();

		if (inclusions == null || inclusions.isEmpty())
			return BigDecimal.ZERO;

		List<MappingExclusion> exclusions = ss
				.createQuery("SELECT E FROM MappingExclusion E WHERE E.mapping.idMapping = :id", MappingExclusion.class)
				.setParameter("id", m.getIdMapping()).getResultList();

		StringBuilder hql = new StringBuilder(
				"SELECT SUM(e.debit) - SUM(e.credit) FROM Ecriture e WHERE e.idExercise = :ex AND (");

		for (int i = 0; i < inclusions.size(); i++) {
			MappingInclusion inc = inclusions.get(i);
			if (i > 0)
				hql.append(" OR ");
			if (inc.getCompteFin() == null || inc.getCompteFin().equals(inc.getCompteDebut())) {
				hql.append("e.cpte = '").append(inc.getCompteDebut()).append("'");
			} else {
				hql.append("e.cpte BETWEEN '").append(inc.getCompteDebut()).append("' AND '").append(inc.getCompteFin())
						.append("'");
			}
		}
		hql.append(") ");

		if (exclusions != null) {
			for (MappingExclusion exc : exclusions) {
				if (exc.getCompteFin() == null || exc.getCompteFin().equals(exc.getCompteDebut())) {
					hql.append(" AND e.cpte <> '").append(exc.getCompteDebut()).append("' ");
				} else {
					hql.append(" AND e.cpte NOT BETWEEN '").append(exc.getCompteDebut()).append("' AND '")
							.append(exc.getCompteFin()).append("' ");
				}
			}
		}

		Query q = ss.createQuery(hql.toString());
		q.setParameter("ex", exercice);

		Object res = q.getSingleResult();
		return res != null ? new BigDecimal(res.toString()) : BigDecimal.ZERO;
	}

	// ============================================================
	// UTILITAIRE : MAP AVEC SUFFIXE
	// ============================================================
	private Map<String, BigDecimal> getMapAvecSuffixe(Map<String, BigDecimal> valeursOriginales, String suffixe) {
		Map<String, BigDecimal> mapSuffixee = new HashMap<String, BigDecimal>();
		for (Map.Entry<String, BigDecimal> entry : valeursOriginales.entrySet()) {
			if (entry.getKey().endsWith(suffixe)) {
				String codeSansSuffixe = entry.getKey().substring(0, entry.getKey().length() - suffixe.length());
				mapSuffixee.put(codeSansSuffixe, entry.getValue());
			}
		}
		return mapSuffixee;
	}
}