package persistances;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import entite.*;

import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConfiguration {
    
    private static final Logger LOG = Logger.getLogger(DBConfiguration.class.getName());
    private static SessionFactory sessionFactoryDao;
    
    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactoryDao == null) {
            buildSessionFactory();
        }
        return sessionFactoryDao;
    }
    
    /**
     * Force la reconstruction de la SessionFactory.
     * À appeler après modification des paramètres de connexion.
     */
    public static synchronized void resetSessionFactory() {
        if (sessionFactoryDao != null && !sessionFactoryDao.isClosed()) {
            sessionFactoryDao.close();
        }
        sessionFactoryDao = null;
        buildSessionFactory();
    }
    
    /**
     * Teste une connexion avec des paramètres donnés SANS toucher au singleton.
     */
    public static boolean testConnection(String host, String user, String pwd, String db) {
        SessionFactory testFactory = null;
        try {
            Configuration config = new Configuration();
            config.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            config.setProperty("hibernate.connection.url",
                "jdbc:mysql://" + host + ":3306/" + db 
                + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
            config.setProperty("hibernate.connection.username", user);
            config.setProperty("hibernate.connection.password", pwd);
            config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            config.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
            
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySettings(config.getProperties())
                    .build();
            
            testFactory = config.buildSessionFactory(registry);
            
            // Vraie tentative de connexion
            testFactory.openSession().close();
            return true;
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Test de connexion échoué : " + e.getMessage(), e);
            return false;
        } finally {
            if (testFactory != null && !testFactory.isClosed()) {
                testFactory.close();
            }
        }
    }
    
    private static void buildSessionFactory() {
        try {
            Configuration config = new Configuration();
            ParametreConnectionC parameter = new ParametreConnectionC();
            
            if (!parameter.readChaine()) {
                LOG.warning("❌ Fichier de connexion introuvable : " + parameter.getUrl());
                return;  // ← NE PAS continuer
            }
            
            LOG.info("🔧 Init Hibernate → " + parameter.getNomHost() 
                     + "/" + parameter.getNomBaseDeDonnee());
            
            config.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
            config.setProperty("hibernate.connection.url",
                "jdbc:mysql://" + parameter.getNomHost() + ":3306/" 
                + parameter.getNomBaseDeDonnee() 
                + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
            config.setProperty("hibernate.connection.username", parameter.getNomUtilisateur());
            config.setProperty("hibernate.connection.password", parameter.getMotDePasse());
            config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            config.setProperty("hibernate.hbm2ddl.auto", "update");
            config.setProperty("show_sql", "false");
            config.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
            
            // C3P0
            config.setProperty("hibernate.c3p0.min_size", "5");
            config.setProperty("hibernate.c3p0.max_size", "20");
            config.setProperty("hibernate.c3p0.timeout", "1800");
            config.setProperty("hibernate.c3p0.max_statements", "100");
            config.setProperty("hibernate.c3p0.idle_test_period", "300");
            
            config.addAnnotatedClass(User.class);
				config.addAnnotatedClass(FonctionRole.class);
			    config.addAnnotatedClass(Exercice.class);
			    config.addAnnotatedClass(Journal.class);
			    config.addAnnotatedClass(Compte.class);
			    config.addAnnotatedClass(Ecriture.class);
			    config.addAnnotatedClass(Banque.class);
			    config.addAnnotatedClass(BankAccount.class);
			    config.addAnnotatedClass(Devise.class);
			    config.addAnnotatedClass(Clients.class);
			    config.addAnnotatedClass(Fournisseur.class);
			    config.addAnnotatedClass(TypeCharge.class);
			    config.addAnnotatedClass(TypeRecette.class);
			    config.addAnnotatedClass(Taxes.class);
			    config.addAnnotatedClass(CentreCout.class);
			    config.addAnnotatedClass(EtatFinancier.class);
			    config.addAnnotatedClass(CompteEFi.class);
			    config.addAnnotatedClass(RubriqueEFi.class);
			    config.addAnnotatedClass(Depense.class);
			    config.addAnnotatedClass(ReglementClient.class);
			    config.addAnnotatedClass(Immobilise.class);
			    config.addAnnotatedClass(Amortissement.class);
			    config.addAnnotatedClass(Partenaire.class);
			    config.addAnnotatedClass(Encaissement.class);
			    config.addAnnotatedClass(Direction.class);
			    config.addAnnotatedClass(Departement.class);
			    config.addAnnotatedClass(Service.class);
            
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySettings(config.getProperties())
                    .build();
            sessionFactoryDao = config.buildSessionFactory(registry);
            
            LOG.info("✅ SessionFactory créée avec succès");
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "💥 Erreur d'initialisation Hibernate", e);
            sessionFactoryDao = null;
        }
    }
}