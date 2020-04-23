package br.com.lucas.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.lucas.drogaria.dao.FabricanteDAO;
import br.com.lucas.drogaria.dao.ProdutoDAO;
import br.com.lucas.drogaria.domain.Fabricante;
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
	private Produto produto;
	private Long codigoProduto;
	
	private List<Produto> produtos;
	private List<Fabricante> fabricantes;
	

	private FabricanteDAO fabricanteDAO;
	private ProdutoDAO produtoDAO;//No cria get e setters porque no é atributo de tela

	
	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Long getCodigoProduto() {
		return codigoProduto;
	}

	public void setCodigoProduto(Long codigoProduto) {
		this.codigoProduto = codigoProduto;
	}
	
	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}

	
	
	/*@PostConstruct:Ele roda antes da tela ser carregada;
	 * Como eu tenho mutiplas telas ele vai rodar varios momentos
	 * Instanciar o DAO, vantagem que usar o DAO em todos métodos, sem precisar ficar instanciando toda hora
	 *Ideal ele dar new nas coisas. Recuperar dados no banco no usar PostConstruct  
	 *
	*/
	/* metadata: Uma sequencias de comandos que eu posso fazer
	  f:viewParam: é feito apos post contruct ,vantagem que ele funciona como campo de entrada
	  O usuario pode tentar passar direto a tela edição sem ter passado para tela listagem-->*/
	

	@PostConstruct
	public void iniciar(){
		fabricanteDAO = new FabricanteDAO();
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
	
	public void carregarEdicao() {
		try {
			produto = produtoDAO.buscar(codigoProduto);
			
			fabricantes = fabricanteDAO.listar("descricao");//carregar os fabricantes
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar tentar carregar os dados para edição");
			erro.printStackTrace();
		}
	}

	


}
