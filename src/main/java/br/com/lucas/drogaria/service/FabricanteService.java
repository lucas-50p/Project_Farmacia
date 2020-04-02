package br.com.lucas.drogaria.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.google.gson.Gson;

import br.com.lucas.drogaria.dao.FabricanteDAO;
import br.com.lucas.drogaria.domain.Fabricante;

//Json é uma forma textual de representar as coisas, ele gasta menos espaços
//http://127.0.0.1:8081/Drogaria/rest/fabricante
@Path("fabricante")
public class FabricanteService {
	@GET //Retornar
	public String listar() {
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		List<Fabricante> fabricantes = fabricanteDAO.listar("descricao");
		
		Gson gson = new Gson();
		String json = gson.toJson(fabricantes);
		
		return json;
	}
	
}
