package br.com.lucas.drogaria.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import com.google.gson.Gson;

import br.com.lucas.drogaria.dao.ProdutoDAO;
import br.com.lucas.drogaria.domain.Produto;

//http://127.0.0.1:8081/Drogaria/rest/produto
@Path("produto")
public class ProdutoService {
	
	@GET
	public String listar() {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		List<Produto> produtos = produtoDAO.listar("descricao");
		
		Gson gson = new Gson();
		String json = gson.toJson(produtos);
		
		return json;
	}
	
	//http://127.0.0.1:8081/Drogaria/rest/produto
	@POST
	public String salvar (String json) {
		Gson gson = new Gson();
		Produto produto = gson.fromJson(json, Produto.class);//String é o json, tipo: Produto.class; vai converter o formato text(json) para o produto
	
		ProdutoDAO produtoDAO = new ProdutoDAO();
		produto = produtoDAO.merge(produto);//Update(Editar) e insert(salvar)
		
		String jsonSaida = gson.toJson(produto);
		return jsonSaida;
	}
}
