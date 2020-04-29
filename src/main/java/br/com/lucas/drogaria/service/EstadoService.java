package br.com.lucas.drogaria.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import com.google.gson.Gson;

import br.com.lucas.drogaria.dao.EstadoDAO;
import br.com.lucas.drogaria.domain.Estado;

//Json é uma forma textual de representar as coisas, ele gasta menos espaços
@Path("estado")
public class EstadoService {

	//http://127.0.0.1:8081/Drogaria/rest/estado
		@GET //Retornar
		public String listar() {
			EstadoDAO estadoDAO = new EstadoDAO();
			List<Estado> estados = estadoDAO.listar("nome");
			
			Gson gson = new Gson();
			String json = gson.toJson(estados);
			
			return json;
		}
}
