package br.com.lucas.drogaria.bean;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.lucas.drogaria.dao.HistoricoDAO;
import br.com.lucas.drogaria.dao.ProdutoDAO;
import br.com.lucas.drogaria.domain.Historico;
import br.com.lucas.drogaria.domain.Produto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class HistoricoBean implements Serializable {

	/*
	 * Classe Bean Uma classe que contenha todos os atributos privados Possua
	 * getters e setters para seus atributos Usada para encapsular e abstrair uma
	 * entidade Implementa java.io.Serializable
	 */
	private Produto produto;
	private Boolean exibiPainelDados;
	private Historico historico;

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Boolean getExibiPainelDados() {
		return exibiPainelDados;
	}

	public void setExibiPainelDados(Boolean exibiPainelDados) {
		this.exibiPainelDados = exibiPainelDados;
	}
	
	public Historico getHistorico() {
		return historico;
	}

	public void setHistorico(Historico historico) {
		this.historico = historico;
	}


	@PostConstruct // Quando a tela for carregada
	public void novo() {
		historico = new Historico();//Criar para no dar null exception
		produto = new Produto();
		exibiPainelDados = false;
	}

	public void buscar() {
		try {
			ProdutoDAO produtoDAO = new ProdutoDAO();
			Produto resultado = produtoDAO.buscar(produto.getCodigo());

			if (resultado == null) {
				exibiPainelDados = false;
				Messages.addGlobalWarn("Não existe produto cadastrado para o código");
			} else {
				exibiPainelDados = true;
				produto = resultado;
			}

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar o produto");
			erro.printStackTrace();
		}
	}
	
	public void salvar() {
		try {
			historico.setHorario(new Date());
			historico.setProduto(produto);
			
			HistoricoDAO historicoDAO = new HistoricoDAO();
			historicoDAO.salvar(historico);
			
			
			Messages.addGlobalInfo("Histórico salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o histórico");
			erro.printStackTrace();
		}
	}
}
