package services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import javax.persistence.Query;
import entite.EtatFinanciersStyleLigne;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class StyleService {

    private final SessionFactory factory;
    private Map<String, EtatFinanciersStyleLigne> cache;

    public StyleService(SessionFactory factory) {
        this.factory = factory;
        this.cache = new HashMap<String, EtatFinanciersStyleLigne>();
    }

    /**
     * Charge tous les styles en cache.
     */
    public void chargerStyles() {
        cache.clear();
        Session ss = null;
        try {
            ss = factory.openSession();
            ss.beginTransaction();
            Query q = ss.createQuery("SELECT S FROM EtatFinanciersStyleLigne S");
            @SuppressWarnings("unchecked")
            List<EtatFinanciersStyleLigne> list = q.getResultList();
            for (EtatFinanciersStyleLigne s : list) {
                cache.put(s.getTypeLigne().getLibelle(), s);
            }
            ss.getTransaction().commit();
        } catch (Exception ex) {
            if (ss != null) ss.getTransaction().rollback();
            ex.printStackTrace();
        } finally {
            if (ss != null) ss.close();
        }
    }

    /**
     * Retourne le style pour un type de ligne. Recharge le cache si vide.
     */
    public EtatFinanciersStyleLigne getStyle(String typeLigne) {
        if (cache.isEmpty()) chargerStyles();
        EtatFinanciersStyleLigne s = cache.get(typeLigne);
        if (s == null) s = cache.get("RUB"); // fallback
        return s;
    }

    /**
     * Génère la chaîne CSS correspondant à un style.
     */
    public String toCss(EtatFinanciersStyleLigne s) {
        if (s == null) return "";

        StringBuilder css = new StringBuilder();
        if (Boolean.TRUE.equals(s.getGras()))      css.append("font-weight:bold;");
        if (Boolean.TRUE.equals(s.getItalique()))  css.append("font-style:italic;");
        if (Boolean.TRUE.equals(s.getSouligne()))  css.append("text-decoration:underline;");
        if (Boolean.TRUE.equals(s.getMajuscule())) css.append("text-transform:uppercase;");

        css.append("font-size:").append(s.getTaillePolice()).append("px;");
        css.append("color:").append(s.getCouleurTexte()).append(";");
        css.append("background-color:").append(s.getCouleurFond()).append(";");
        css.append("padding-left:").append(s.getIndentation() * 15).append("px;");

        if ("D".equals(s.getAlignement()))      css.append("text-align:right;");
        else if ("C".equals(s.getAlignement())) css.append("text-align:center;");
        else                                    css.append("text-align:left;");

        if ("SIMPLE".equals(s.getBordureHaut())) css.append("border-top:1px solid #000;");
        if ("DOUBLE".equals(s.getBordureHaut())) css.append("border-top:3px double #000;");
        if ("SIMPLE".equals(s.getBordureBas()))  css.append("border-bottom:1px solid #000;");
        if ("DOUBLE".equals(s.getBordureBas()))  css.append("border-bottom:3px double #000;");

        return css.toString();
    }
}