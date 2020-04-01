package br.com.lucas.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.lucas.drogaria.dao.ClienteDAO;
import br.com.lucas.drogaria.dao.PessoaDAO;
import br.com.lucas.drogaria.domain.Cliente;
import br.com.lucas.drogaria.domain.Pessoa;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable{
	private Cliente cliente;

	private List<Cliente> clientes;
	private List<Pessoa> pessoas;

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	@PostConstruct // É chamado logo depois construtor da classe
	public void listar() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();//Chamar
			clientes = clienteDAO.listar();//Receber
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar 'listar' os clientes");
			erro.printStackTrace();
		}
	}
	
	public void novo() {
		try {
			cliente = new Cliente();

			PessoaDAO pessoaDAO = new PessoaDAO();
			pessoas = pessoaDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar criar um novo cliente");
			erro.printStackTrace();
		}
	}
	public void salvar() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.merge(cliente);//merge inclui e salva
		
			cliente = new Cliente();//limpar cliente, apagar os dados que eu tinha editado anteriormente
			
			clientes = clienteDAO.listar("dataCadastro");
			
			PessoaDAO pessoaDAO = new PessoaDAO();//Recarregar
			pessoas = pessoaDAO.listar("nome");
			
			Messages.addGlobalInfo("Cliente salvo com sucesso");
		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar 'salvar' um novo cliente");
			erro.printStackTrace();
		}
		
	}
	
	public void excluir(ActionEvent evento) {
		try {
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");

			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.excluir(cliente);

			clientes = clienteDAO.listar();//Forçar ele recarregar os estados
			
			Messages.addGlobalInfo("Estado removido com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o 'cliente'");
			erro.printStackTrace();// Pilha de execução
		}
	}
	
}
