package br.com.lucas.drogaria.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;

import br.com.lucas.drogaria.dao.FabricanteDAO;
import br.com.lucas.drogaria.domain.Fabricante;

//Json é uma forma textual de representar as coisas, ele gasta menos espaços
@Path("fabricante")
public class FabricanteService {
	
	//http://127.0.0.1:8081/Drogaria/rest/fabricante
	@GET //Retornar
	public String listar() {
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		List<Fabricante> fabricantes = fabricanteDAO.listar("descricao");
		
		Gson gson = new Gson();
		String json = gson.toJson(fabricantes);
		
		return json;
	}
	//http://127.0.0.1:8081/Drogaria/rest/fabricante/{codigo}
	// http://127.0.0.1:8081/Drogaria/rest/fabricante/10
	//@PathParam("{codigo}"): Estou amarrando objeto código ao parametro código
	@GET
	@Path("{codigo}")
	public String buscar(@PathParam("codigo") Long codigo){
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		Fabricante fabricante = fabricanteDAO.buscar(codigo);
		
		Gson gson = new Gson();
		String json = gson.toJson(fabricante);
		
		return json;
	}
	//http://127.0.0.1:8081/Drogaria/rest/fabricante
	//ERROR 500, erro de servidor tento fazer processamento ele no conseguiu
	/*204: ele fez uma chamada 200 sem sucesso, ele chamou salvar no retornou nada
	NO teve nenhum conteudo de retorno*/
	@POST
	public String salvar(String json) {
		Gson gson = new Gson();
		Fabricante fabricante = gson.fromJson(json, Fabricante.class);
		
		FabricanteDAO fabricanteDAO = new FabricanteDAO();
		fabricanteDAO.merge(fabricante);
		
		String jsonSaida = gson.toJson(fabricante);
		return jsonSaida;
	}
	
}
