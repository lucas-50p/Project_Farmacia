package br.com.lucas.drogaria.dao;

import org.junit.Test;

import br.com.lucas.drogaria.domain.Cidade;
import br.com.lucas.drogaria.domain.Estado;

public class CidadeDAOTest {

	@Test
	public void salvar() {
		EstadoDAO estadoDAO = new EstadoDAO();
		
		Estado estado = estadoDAO.buscar(2L);
		
		Cidade cidade = new Cidade();
		cidade.setNome("Santa Cruz");
		cidade.setEstado(estado);
	
		CidadeDAO cidadeDAO = new CidadeDAO();
		cidadeDAO.salvar(cidade);
	}
}
