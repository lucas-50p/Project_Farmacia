package br.com.lucas.drogaria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.lucas.drogaria.dao.ClienteDAO;
import br.com.lucas.drogaria.domain.Cliente;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable{
	private List<Cliente> clientes;

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
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
	
}
