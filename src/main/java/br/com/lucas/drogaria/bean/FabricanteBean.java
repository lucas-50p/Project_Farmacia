package br.com.lucas.drogaria.bean;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.omnifaces.util.Messages;

import com.google.gson.Gson;

import br.com.lucas.drogaria.dao.FabricanteDAO;
import br.com.lucas.drogaria.domain.Fabricante;

@SuppressWarnings("serial")
@ManagedBean // tratar do controle e do modelo dentro da nossa aplicação,Dados que conversam
@ViewScoped // Tempo de tela, ficam vivos enquanto estou na tela de fabricante
public class FabricanteBean implements Serializable {

	private Fabricante fabricante;
	private List<Fabricante> fabricantes;

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public List<Fabricante> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(List<Fabricante> fabricantes) {
		this.fabricantes = fabricantes;
	}
	
	// Sempre que trabalhar com visão, vai usar try e catch
	@PostConstruct// É chamado logo depois construtor da classe
	public void listar() {
		try {
			//FabricanteDAO fabricanteDAO = new FabricanteDAO();
			//fabricantes = fabricanteDAO.listar();
			//Request:disparar um requição
			//Estou Chamando um serviço esse serviço esta chamando a minha camada dao
			Client cliente = ClientBuilder.newClient();
			WebTarget caminho = cliente.target("http://127.0.0.1:8081/Drogaria/rest/fabricante");
			String json = caminho.request().get(String.class);//Get(FabricanteServ...) e pega o tipo que é String
			
			Gson gson = new Gson();
			Fabricante[] vetor = gson.fromJson(json, Fabricante[].class);//Vai converter o resultado em um vector de fabricantes
			
			fabricantes = Arrays.asList(vetor);//converter para ArrayList
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os 'fabricantes");
			erro.printStackTrace();// imprimi pilha de execução o erros gravados em azul
		}
	}
	
	public void salvar(RuntimeException erro) {
		try {
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			fabricanteDAO.merge(fabricante);

			fabricante = new Fabricante();
			fabricantes = fabricanteDAO.listar();

			Messages.addGlobalInfo("Fabricante salvo com sucesso");
		} catch (Exception e) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar os 'fabricantes");
			erro.printStackTrace();// imprimi pilha de execução o erros gravados em azul
		}
	}
	
	
	public void novo() {
		fabricante = new Fabricante();
	}
	
	public void excluir(ActionEvent evento) {
		try {
			FabricanteDAO fabricanteDAO = new FabricanteDAO();
			
			fabricante = (Fabricante) evento.getComponent().getAttributes().get("fabricanteSelecionado");

			
			fabricanteDAO.merge(fabricante);
			

			fabricantes = fabricanteDAO.listar();

			Messages.addGlobalInfo("Fabricante removido com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar excluir os 'fabricantes");
			erro.printStackTrace();// imprimi pilha de execução o erros gravados em azul
		}
	}

	
	public void editar(ActionEvent evento) {
		fabricante = (Fabricante) evento.getComponent().getAttributes().get("fabricanteSelecionado");
	}
}
