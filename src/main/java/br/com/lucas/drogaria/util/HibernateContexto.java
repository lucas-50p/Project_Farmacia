package br.com.lucas.drogaria.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

//Servlet: tem um metodo quando tomcat é ligado e desligado
public class HibernateContexto implements ServletContextListener{
	
	/*Quando voçê liga o tomcat*/
	@Override
	public void contextInitialized(ServletContextEvent event) {
		HibernateUtil.getFabricaDeSessoes();
		
	}

	/*Quando voçê desliga o tomcat*/
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		HibernateUtil.getFabricaDeSessoes().close();
		
	}

}
