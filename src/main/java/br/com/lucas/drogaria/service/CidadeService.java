package br.com.lucas.drogaria.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;

import br.com.lucas.drogaria.dao.CidadeDAO;
import br.com.lucas.drogaria.domain.Cidade;

@Path("cidade") // http://127.0.0.1:8081/Drogaria/rest/cidade
public class CidadeService {

	@GET // Retornar
	@Path("{estadoCodigo}")//Dentro de chaves é parametro;http://127.0.0.1:8081/Drogaria/rest/cidade/{estadoCodigo}
	public String buscarPorEstado(@PathParam("estadoCodigo") long estadoCodigo) {
		CidadeDAO cidadeDAO = new CidadeDAO();
		List<Cidade> cidades = cidadeDAO.buscarPorEstado(estadoCodigo);

		Gson gson = new Gson();
		String json = gson.toJson(cidades);

		return json;
	}
}
