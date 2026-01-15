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
				
				ParametreConnectionC parameter=new ParametreConnectionC();
				if(parameter.readChaine())
				{
				
				config.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
				
			
				  
				  config.setProperty("hibernate.connection.url",
				  "jdbc:mysql://"+parameter.getNomHost()+":3306/"+parameter.getNomBaseDeDonnee());
				  config.setProperty("hibernate.connection.username", parameter.getNomUtilisateur());
				  config.setProperty("hibernate.connection.password", parameter.getMotDePasse());
				  
					
				/*
				 * 
				 * config.setProperty("hibernate.connection.url",
				 * "jdbc:mysql://localhost:3306/comptademo");
				 * config.setProperty("hibernate.connection.username", "root");
				 * config.setProperty("hibernate.connection.password", "gatech/2025");
				 */
				}
				config.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
				config.setProperty("hibernate.hbm2ddl.auto", "update");
				config.setProperty("show_sql", "true");
				config.setProperty("hibernate.c3p0.max_size", "20");
				config.setProperty("hibernate.c3p0.min_size", "5");
				config.setProperty("hibernate.c3p0.timeout", "1800");
				config.setProperty("hibernate.c3p0.max_statements", "100");
				config.setProperty("hibernate.c3p0.idle_test_period", "300");
				config.setProperty("hibernate.c3p0.acquire_increment", "5");
				config.setProperty("hibernate.temp.use_jdbc_metadata_defaults", "false");
				config.setProperty("hibernate.generate_statistics", "true");
				config.setProperty("hibernate.cache.use_query_cache", "false");
				config.setProperty("hibernate.c3p0.testConnectionOnCheckout", "true");
				config.setProperty("hibernate.c3p0.preferredTestQuery", "SELECT 1");

				
				
				StandardServiceRegistry standardServiceRegistry = (new StandardServiceRegistryBuilder())
						.applySettings(config.getProperties()).build();
				sessionFactoryDao = config.buildSessionFactory((ServiceRegistry) standardServiceRegistry);
			} catch (Exception e) {

				System.out.println(String.valueOf(e.getMessage()) + ":" + e.toString());
			}
		}
		return sessionFactoryDao;
	}

	private static SessionFactory sessionFactoryDao;
}
