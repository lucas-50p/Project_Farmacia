package br.com.lucas.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.lucas.drogaria.dao.EstadoDAO;
import br.com.lucas.drogaria.domain.Estado;

/*Faces Mensagem:String encapsulado dentro de outra classe
 * FacesSumamary: seria o erro de forma resumida
 * FacesDetails: erro mais detalhado
 * FaceMensagens: String encapsulada dentro de outra classe,junto  com outras coisas jsf
 * Severity: é o tipo da mensagem, impactar no tipo da cor do primeFace exibi para nos
 * SEVERITY_INFO:Informação, na tela
 * SEVERITY_ERROR: na tela erro*/
@SuppressWarnings("serial")
@ManagedBean // tratar do controle e do modelo dentro da nossa aplicação,Dados que conversam
				// com a tela
@ViewScoped // Tempo de tela, ficam vivos enquanto estou na tela de estado
public class EstadoBean implements Serializable {
	private Estado estado;
	private List <Estado> estados;

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	//Sempre que trabalhar com visão, vai usar try e catch
	@PostConstruct//É chamado logo depois construtor da classe
	public void listar() {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar();
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os estado");
			erro.printStackTrace();// imprimi pilha de execução o erros gravados em azul
		}
	}
	
	public void novo() {
		estado = new Estado();
	}

	public void salvar() {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.salvar(estado);

			novo();

			Messages.addGlobalInfo("Estado salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o estado");
			erro.printStackTrace();// imprimi pilha de execução o erros gravados em azul
		}

		/*
		 * FacesMensagens --- String texto = "Programação Web com Java"; FacesMessage
		 * message = new FacesMessage(FacesMessage.SEVERITY_ERROR, texto, texto);
		 * 
		 * FacesContext contexto = FacesContext.getCurrentInstance();
		 * contexto.addMessage(null, message);
		 */

	}
}