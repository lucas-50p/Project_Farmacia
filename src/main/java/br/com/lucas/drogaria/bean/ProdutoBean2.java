package br.com.lucas.drogaria.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.lucas.drogaria.dao.ProdutoDAO;
import br.com.lucas.drogaria.domain.Produto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ProdutoBean2 implements Serializable{
	private Produto produto;
	private Boolean exibiPainelDados;

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
	
	@PostConstruct//Quando a tela for carregada
	public void novo() {
	
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

}
