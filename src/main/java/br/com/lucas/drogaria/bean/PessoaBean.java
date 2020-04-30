package br.com.lucas.drogaria.bean;

import java.io.Serializable;
import java.util.ArrayList;
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

import br.com.lucas.drogaria.dao.CidadeDAO;
import br.com.lucas.drogaria.dao.EstadoDAO;
import br.com.lucas.drogaria.dao.PessoaDAO;
import br.com.lucas.drogaria.domain.Cidade;
import br.com.lucas.drogaria.domain.Estado;
import br.com.lucas.drogaria.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PessoaBean implements Serializable {
	
	/*
	 * Classe Bean Uma classe que contenha todos os atributos privados Possua
	 * getters e setters para seus atributos Usada para encapsular e abstrair uma
	 * entidade Implementa java.io.Serializable
	 */
	
	private Pessoa pessoa;//A cidade está dentro da pessoa
	private List<Pessoa> pessoas;
	
	private Estado estado;
	private List<Estado> estados;
	private List<Cidade> cidades;

	public Pessoa getPessoa() {
		return pessoa;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}
	
	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	//Mudança que por tras no está usando o dao direto; Ele está usando agora o Web Service
	public void novo() {
		try {
			pessoa = new Pessoa();
			
			estado = new Estado();
			
			//Essas 6 linhas para carregamento do estado
			Client cliente = ClientBuilder.newClient();//Criou um cliente
			WebTarget caminho = cliente.target("http://127.0.0.1:8081/Drogaria/rest/estado");//Conectou com web service
			String json = caminho.request().get(String.class);//Mandei executar o comando(resultado gson); Get(FabricanteServ...) e pega o tipo que é String
			Gson gson = new Gson();//Usando essa api
			Estado[] vetor = gson.fromJson(json, Estado[].class);//converto para retorno json;(Vetor de estado)Vai converter o resultado em um vector de estado
			estados= Arrays.asList(vetor);//Vetor de estados convertido para array list, converter para ArrayList

			cidades = new ArrayList<Cidade>();// Instanciando a listagem da Cidade vazia, aparecer quando click no estado
												
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar gerar uma pessoa nova");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		  try {
		   CidadeDAO cidadeDao = new CidadeDAO();
		   cidades = cidadeDao.listar();
		   
		   EstadoDAO estadoDao = new EstadoDAO();
		   estados = estadoDao.listar();
		   
		   pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionada");
		      
		   estado = pessoa.getCidade().getEstado();
		   
		   Messages.addGlobalInfo("Pessoa editada salva com sucesso");
		  }catch(RuntimeException erro) {
		   Messages.addGlobalError("Ocorreu um erro ao tentar editar a pessoa!");
		   erro.printStackTrace();
		  }
		 }

	public void salvar() {
		//pessoas = pessoaDAO.listar();
		try {
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoaDAO.merge(pessoa);
			
			pessoas = pessoaDAO.listar();
			
			pessoa = new Pessoa();
			
			estado = new Estado();

			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar();//Recarregar as pessoas

			cidades = new ArrayList<>();
			
			Messages.addGlobalInfo("Pessoa salva com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a pessoa");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			pessoa = (Pessoa) evento.getComponent().getAttributes().get("pessoaSelecionada");
			
			  PessoaDAO pessoaDAO = new PessoaDAO();
			  pessoaDAO.excluir(this.pessoa);
			  
			  pessoas = pessoaDAO.listar();
			  Messages.addGlobalInfo("Pessoa excluída com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar 'excluir' a pessoa");
			erro.printStackTrace();
		}
	}
	//Polular as cidades com base no estado
	public void popular() {
		//System.out.println("Código: " + estado.getCodigo() + "\nNome: " + estado.getNome());
		//System.out.println("Total: " + cidades.size());
		//cidades = new ArrayList<Cidade>();: No diamante pode colocar cidade ou no(escolha)
		try {
				if (estado != null) {
					Client cliente = ClientBuilder.newClient();//Criou um cliente
					WebTarget caminho = cliente.target("http://127.0.0.1:8081/Drogaria/rest/cidade/{estadoCodigo}").resolveTemplate("estadoCodigo",estado.getCodigo());//Na esquerda o nome do parametro,Pega o @Path(parametro) - Cidade service,Na direita value do object
					String json = caminho.request().get(String.class);//Mandei executar o comando(resultado gson); Get(FabricanteServ...) e pega o tipo que é String
					Gson gson = new Gson();//Usando essa api
					Cidade[] vetor = gson.fromJson(json, Cidade[].class);//converto para retorno json;(Vetor de estado)Vai converter o resultado em um vector de estado
					cidades = Arrays.asList(vetor);//Vetor de estados convertido para array list, converter para ArrayList

					//cidades = new ArrayList<Cidade>();// Instanciando a listagem da Cidade vazia, aparecer quando click no estado
					
					
					//Antigo
					//CidadeDAO cidadeDAO = new CidadeDAO();
					//cidades = cidadeDAO.buscarPorEstado(estado.getCodigo());//value do object
				}else {
					cidades = new ArrayList<>();
				}
				
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as cidades");
			erro.printStackTrace();
		}
	}

	@PostConstruct // Chamar quando a tela criada
	public void listar() {
		try {
			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as pessoas");
			erro.printStackTrace();
		}
	}
}
