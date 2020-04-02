package br.com.lucas.drogaria.util;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;


//http://localhost:8080/Drogaria/[NomeRest]
@ApplicationPath("rest")
public class DrogariaResourceConfig extends ResourceConfig{
	public DrogariaResourceConfig() {
		packages("br.com.lucas.drogaria.service");
	}
}
