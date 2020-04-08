package br.com.lucas.drogaria.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.lucas.drogaria.dao.FuncionarioDAO;
import br.com.lucas.drogaria.dao.ProdutoDAO;
import br.com.lucas.drogaria.domain.Cliente;
import br.com.lucas.drogaria.domain.Funcionario;
import br.com.lucas.drogaria.domain.ItemVenda;
import br.com.lucas.drogaria.domain.Produto;
import br.com.lucas.drogaria.domain.Venda;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class VendasBean implements Serializable {
	private Venda venda;

	private List<Produto> produtos;
	private List<ItemVenda> itensVenda;
	private List<Cliente> clientes;
	private List<Funcionario> funcionarios;

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}
	
	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	@PostConstruct
	public void novo() {
		try {
			venda = new Venda();
			venda.setPrecoTotal(new BigDecimal("0.00"));//Começar o valor 0
			
			ProdutoDAO produtoDAO = new ProdutoDAO();
			produtos = produtoDAO.listar("descricao");

			itensVenda = new ArrayList<>();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar carregar a tela de vendas");
			erro.printStackTrace();
		}
	}

	public void adicionar(ActionEvent evento) {
		Produto produto = (Produto) evento.getComponent().getAttributes().get("produtoSelecionado");

		int achou = -1;// -1 não achou
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			if (itensVenda.get(posicao).getProduto().equals(produto)) {
				achou = posicao;
			}
		}

		if (achou < 0) {
			ItemVenda itemVenda = new ItemVenda();
			itemVenda.setPrecoParcial(produto.getPreco());
			itemVenda.setProduto(produto);
			itemVenda.setQuantidade(new Short("1"));

			itensVenda.add(itemVenda);

		} else {
			ItemVenda itemVenda = itensVenda.get(achou);
			itemVenda.setQuantidade(new Short(itemVenda.getQuantidade() + 1 + ""));/* qualquer valor numerico que voçê some no java inteiro, ele manda para int*/
			itemVenda.setPrecoParcial(produto.getPreco().multiply(new BigDecimal(itemVenda.getQuantidade())));/* Converter para big decimal*/																							// quantidade
		}
		
		calcular();
	}

	public void remover(ActionEvent evento) {
		ItemVenda itemVenda = (ItemVenda) evento.getComponent().getAttributes().get("itemSelecionado");

		int achou = -1;
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			if (itensVenda.get(posicao).getProduto().equals(itemVenda.getProduto())) {
				achou = posicao;
			}
		}

		if (achou > -1) {
			itensVenda.remove(achou);
		}
		
		calcular();
	}
	
	public void calcular() {
		//Aculmulador
		venda.setPrecoTotal(new BigDecimal("0.00"));//Zerar
		
		for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
			ItemVenda itemVenda = itensVenda.get(posicao);
			venda.setPrecoTotal(venda.getPrecoTotal().add(itemVenda.getPrecoParcial()));
			
		}
	}
	
	public void finalizar() {
		try {
			FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
			funcionarios = funcionarioDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar finalizar");
			erro.printStackTrace();
		}
	}
}