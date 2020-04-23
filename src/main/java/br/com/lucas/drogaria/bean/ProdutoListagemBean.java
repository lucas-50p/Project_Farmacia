package br.com.lucas.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.lucas.drogaria.dao.ProdutoDAO;
import br.com.lucas.drogaria.domain.Produto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProdutoListagemBean implements Serializable{
	/*
	 * Classe Bean Uma classe que contenha todos os atributos privados Possua
	 * getters e setters para seus atributos Usada para encapsular e abstrair uma
	 * entidade Implementa java.io.Serializable
	 */
	
	private List<Produto> produtos;
	
	
	private ProdutoDAO produtoDAO;//No cria get e setters porque no é atributo de tela

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}
	
	/*@PostConstruct:Ele roda antes da tela ser carregada;
	 * Como eu tenho mutiplas telas ele vai rodar varios momentos
	 * Instanciar o DAO, vantagem que usar o DAO em todos métodos, sem precisar ficar instanciando toda hora
	 *Ideal ele dar new nas coisas. Recuperar dados no banco no usar PostConstruct  
	 *
	*/
	
	
	@PostConstruct
	public void iniciar(){
		produtoDAO = new ProdutoDAO();
	}
	
	public void listar() {
		try {
			produtos = produtoDAO.listar("descricao");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os produtos");
			erro.printStackTrace();
		}
	}
}
