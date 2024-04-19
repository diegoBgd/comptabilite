 package persistances;
		 import entite.*;

 import org.hibernate.SessionFactory;
 import org.hibernate.boot.registry.StandardServiceRegistry;
		 import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
 import org.hibernate.cfg.Configuration;
 import org.hibernate.service.ServiceRegistry;
 
 public class DBConfiguration {
   public static SessionFactory getSessionFactory() {
     if (sessionFactoryDao == null) {
       
       try {
         Configuration config = new Configuration();
         config.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
         
			
			
			
			  config.setProperty("hibernate.connection.url",
			  "jdbc:mysql://localhost:3306/comptavirago");
			  config.setProperty("hibernate.connection.username", "root");
			  config.setProperty("hibernate.connection.password", "123");
				  
				/*
				 * config.setProperty("hibernate.connection.url",
				 * "jdbc:mysql://localhost:3306/comptavirago");
				 * config.setProperty("hibernate.connection.username", "root");
				 * config.setProperty("hibernate.connection.password", "virago/2024");
				 * 
				 */
			config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
			config.setProperty("hibernate.hbm2ddl.auto", "update");
			config.setProperty("show_sql", "true");
			config.setProperty("hibernate.c3p0.max_size", "3");
			config.setProperty("hibernate.c3p0.min_size", "1");
			config.setProperty("hibernate.c3p0.timeout", "5000");
			config.setProperty("hibernate.c3p0.max_statements", "100");
			config.setProperty("hibernate.c3p0.idle_test_period", "300");
			config.setProperty("hibernate.c3p0.acquire_increment", "2");
			config.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
			config.setProperty("hibernate.generate_statistics", "true");
			config.setProperty("hibernate.cache.use_querry_cache", "false");
         
         config.addAnnotatedClass(User.class);
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
         config.addAnnotatedClass(ReglementFournisseur.class);
         config.addAnnotatedClass(FamilleProduit.class);
         config.addAnnotatedClass(SoufamilleProd.class);
         config.addAnnotatedClass(Produit.class);
         config.addAnnotatedClass(Immobilise.class);
         config.addAnnotatedClass(Amortissement.class);
         config.addAnnotatedClass(ParametreImmo.class);
         config.addAnnotatedClass(Facture.class);
         config.addAnnotatedClass(FactureDetail.class);
         config.addAnnotatedClass(ProduitTaxe.class);
         config.addAnnotatedClass(Encaissement.class);
         config.addAnnotatedClass(FonctionRole.class);
         config.addAnnotatedClass(ReglementClient.class);
         config.addAnnotatedClass(ParametreCompta.class);
         StandardServiceRegistry standardServiceRegistry = (new StandardServiceRegistryBuilder()).applySettings(config.getProperties()).build();
         sessionFactoryDao = config.buildSessionFactory((ServiceRegistry)standardServiceRegistry);
       }
       catch (Exception e) {
         
         System.out.println(String.valueOf(e.getMessage()) + ":" + e.toString());
       } 
     }
     return sessionFactoryDao;
   }
   
   private static SessionFactory sessionFactoryDao;
 }

