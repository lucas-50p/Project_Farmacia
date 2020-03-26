package br.com.lucas.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

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

/*
 * FacesMensagens --- String texto = "Programação Web com Java"; FacesMessage
 * message = new FacesMessage(FacesMessage.SEVERITY_ERROR, texto, texto);
 * 
 * FacesContext contexto = FacesContext.getCurrentInstance();
 * contexto.addMessage(null, message);
 */

@SuppressWarnings("serial")
@ManagedBean // tratar do controle e do modelo dentro da nossa aplicação,Dados que conversam
				// com a tela
@ViewScoped // Tempo de tela, ficam vivos enquanto estou na tela de estado
public class EstadoBean implements Serializable {
	private Estado estado;
	private List<Estado> estados;

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

	// Sempre que trabalhar com visão, vai usar try e catch
	@PostConstruct // É chamado logo depois construtor da classe
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
			estadoDAO.merge(estado);

			estado = new Estado();// Instanciar o estado
			estados = estadoDAO.listar();// Recarregar a pesquisa de estado

			Messages.addGlobalInfo("Estado salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o estado");
			erro.printStackTrace();// imprimi pilha de execução o erros gravados em azul
		}

	}

	public void excluir(ActionEvent evento) {
		try {
			estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");

			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.excluir(estado);

			estados = estadoDAO.listar();//Forçar ele recarregar os estados
			
			Messages.addGlobalInfo("Estado removido com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o estado");
			erro.printStackTrace();// Pilha de execução
		}
	}
	
	public void editar(ActionEvent evento){
		estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");
	}
	
	
}