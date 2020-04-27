package br.com.lucas.drogaria.dao;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Ignore;
import org.junit.Test;

import br.com.lucas.drogaria.domain.Pessoa;
import br.com.lucas.drogaria.domain.Usuario;

public class UsuarioDAOTest {
	
	@Test
	@Ignore
	public void salvar(){
		PessoaDAO pessoaDAO = new PessoaDAO();
		Pessoa pessoa = pessoaDAO.buscar(83L);
		
		System.out.println("Pessoa Encontrada");
		System.out.println("Nome: " + pessoa.getNome());
		System.out.println("CPF: " + pessoa.getCpf());
		
		
		Usuario usuario = new Usuario();
		usuario.setAtivo(true);
		usuario.setPessoa(pessoa);
		usuario.setSenhaSemCriptografia("q1w2e3r4");
		
		SimpleHash hash = new SimpleHash("md5", usuario.getSenhaSemCriptografia());//Nome do algoritmo
		usuario.setSenha(hash.toHex());//Vai Criptografa toHex gerar:(https://www.md5hashgenerator.com/)
		
		usuario.setTipo('G');//Tipo 'A' administrador
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		usuarioDAO.salvar(usuario);
		
		System.out.println("Usuário salvo com sucesso.");
	}
	
	@Test
	@Ignore
	public void autenticar() {
		String cpf= "151.354.543-54";
		String senha = "q1w2e3r4";
		
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		Usuario usuario = usuarioDAO.autenticar(cpf, senha);
		
		System.out.println("Usuário autenticar: " + usuario);
	}
}