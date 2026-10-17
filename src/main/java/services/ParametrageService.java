package services;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.persistence.Query;

import entite.EtatFinanciers;
import entite.EtatFinanciersCompte;
import entite.EtatFinanciersRubrique;
import entite.MappingExclusion;
import entite.MappingInclusion;

public class ParametrageService {

    private final SessionFactory factory;

    public ParametrageService(SessionFactory factory) {
        this.factory = factory;
    }

    /**
     * Crée ou met à jour un état financier complet (rubriques + mappings).
     */
    public EtatFinanciers saveEtatComplet(EtatFinanciers etat) {
        Session ss = null;
        try {
            ss = factory.openSession();
            ss.beginTransaction();

            if (etat.getIdEtat() == 0) {
                ss.save(etat);
            } else {
                ss.update(etat);
            }

            ss.getTransaction().commit();
            return etat;

        } catch (Exception ex) {
            if (ss != null) ss.getTransaction().rollback();
            ex.printStackTrace();
            return null;
        } finally {
            if (ss != null) ss.close();
        }
    }

    /**
     * Duplique un état existant vers un nouvel état (rubriques + mappings + inclusions + exclusions).
     */
    public EtatFinanciers dupliquerEtat(int idSource, String nouveauCode, String nouveauLibelle) {
        Session ss = null;
        try {
            ss = factory.openSession();
            ss.beginTransaction();

            // 1. Charger l'état source
            EtatFinanciers source = (EtatFinanciers) ss.get(EtatFinanciers.class, Integer.valueOf(idSource));
            if (source == null) {
                ss.getTransaction().commit();
                return null;
            }

            // 2. Créer le nouvel état
            EtatFinanciers copie = new EtatFinanciers();
            copie.setCodeEtat(nouveauCode);
            copie.setLibelleEtat(nouveauLibelle);
            copie.setTypeEtat(source.getTypeEtat());
            copie.setModeCalcul(source.getModeCalcul());
            copie.setActif(true);
            ss.save(copie);

            // 3. Charger les rubriques source
            Query qRub = ss.createQuery(
                "SELECT R FROM EtatFinanciersRubrique R WHERE R.etat.idEtat = :id ORDER BY R.ordreAffichage");
            qRub.setParameter("id", idSource);
            @SuppressWarnings("unchecked")
            List<EtatFinanciersRubrique> rubriquesSource = qRub.getResultList();

            for (EtatFinanciersRubrique rSrc : rubriquesSource) {

                EtatFinanciersRubrique rCible = new EtatFinanciersRubrique();
                rCible.setEtat(copie);
                rCible.setCodeRubrique(rSrc.getCodeRubrique());
                rCible.setLibelleRubrique(rSrc.getLibelleRubrique());
                rCible.setSection(rSrc.getSection());
                rCible.setNiveau(rSrc.getNiveau());
                rCible.setTypeLigne(rSrc.getTypeLigne());
                rCible.setOrdreAffichage(rSrc.getOrdreAffichage());
                rCible.setTypeCalcul(rSrc.getTypeCalcul());
                rCible.setFormule(rSrc.getFormule());
                rCible.setSensNormal(rSrc.getSensNormal());
                rCible.setSigne(rSrc.getSigne());
                rCible.setAfficheAmortissements(rSrc.getAfficheAmortissements());
                rCible.setTypeValeur(rSrc.getTypeValeur());
                rCible.setVisible(rSrc.getVisible());
                rCible.setImprimable(rSrc.getImprimable());
                ss.save(rCible);

                // 4. Copier les mappings
                Query qMap = ss.createQuery(
                    "SELECT M FROM EtatFinanciersCompte M WHERE M.rubrique.idRubrique = :id");
                qMap.setParameter("id", rSrc.getIdRubrique());
                @SuppressWarnings("unchecked")
                List<EtatFinanciersCompte> mappings = qMap.getResultList();

                for (EtatFinanciersCompte mSrc : mappings) {
                    EtatFinanciersCompte mCible = new EtatFinanciersCompte();
                    mCible.setRubrique(rCible);
                    mCible.setLibelleRegle(mSrc.getLibelleRegle());
                    mCible.setTypeMouvement(mSrc.getTypeMouvement());
                    mCible.setNatureValeur(mSrc.getNatureValeur());
                    mCible.setSens(mSrc.getSens());
                    mCible.setCoefficient(mSrc.getCoefficient());
                    mCible.setOperateurLogique(mSrc.getOperateurLogique());
                    mCible.setConditionSql(mSrc.getConditionSql());
                    mCible.setOrdre(mSrc.getOrdre());
                    ss.save(mCible);

                    // 5. Copier inclusions
                    Query qInc = ss.createQuery(
                        "SELECT I FROM MappingInclusion I WHERE I.mapping.idMapping = :id");
                    qInc.setParameter("id", mSrc.getIdMapping());
                    @SuppressWarnings("unchecked")
                    List<MappingInclusion> inclusions = qInc.getResultList();

                    for (MappingInclusion iSrc : inclusions) {
                        MappingInclusion iCible = new MappingInclusion();
                        iCible.setMapping(mCible);
                        iCible.setCompteDebut(iSrc.getCompteDebut());
                        iCible.setCompteFin(iSrc.getCompteFin());
                        iCible.setTypePlage(iSrc.getTypePlage());
                        ss.save(iCible);
                    }

                    // 6. Copier exclusions
                    Query qExc = ss.createQuery(
                        "SELECT E FROM MappingExclusion E WHERE E.mapping.idMapping = :id");
                    qExc.setParameter("id", mSrc.getIdMapping());
                    @SuppressWarnings("unchecked")
                    List<MappingExclusion> exclusions = qExc.getResultList();

                    for (MappingExclusion eSrc : exclusions) {
                        MappingExclusion eCible = new MappingExclusion();
                        eCible.setMapping(mCible);
                        eCible.setCompteDebut(eSrc.getCompteDebut());
                        eCible.setCompteFin(eSrc.getCompteFin());
                        eCible.setTypePlage(eSrc.getTypePlage());
                        eCible.setMotif(eSrc.getMotif());
                        ss.save(eCible);
                    }
                }
            }

            ss.getTransaction().commit();
            return copie;

        } catch (Exception ex) {
            if (ss != null) ss.getTransaction().rollback();
            ex.printStackTrace();
            return null;
        } finally {
            if (ss != null) ss.close();
        }
    }

    /**
     * Supprime un état et toutes ses dépendances (cascade via orphanRemoval ou manuellement).
     */
    public boolean supprimerEtat(int idEtat) {
        Session ss = null;
        try {
            ss = factory.openSession();
            ss.beginTransaction();

            EtatFinanciers etat = (EtatFinanciers) ss.get(EtatFinanciers.class, Integer.valueOf(idEtat));
            if (etat != null) {
                ss.delete(etat);
            }

            ss.getTransaction().commit();
            return true;

        } catch (Exception ex) {
            if (ss != null) ss.getTransaction().rollback();
            ex.printStackTrace();
            return false;
        } finally {
            if (ss != null) ss.close();
        }
    }
}