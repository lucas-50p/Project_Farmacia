package br.com.lucas.drogaria.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/*Get:Chama quando quero consultar*/
//http://localhost:8081/Drogaria/rest/drogaria
@Path("drogaria")
public class DrogariaService {
	@GET
	public String exibir() {
		return "Curso de Java";
	}
	
	
}
