package br.com.lucas.drogaria.bean;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.com.lucas.drogaria.dao.UsuarioDAO;
import br.com.lucas.drogaria.domain.Pessoa;
import br.com.lucas.drogaria.domain.Usuario;

/*@ViewScoped:O usario fica vivo durante a tela, mudei tela o usuario é destruido
 * @RequestMapping:Cada click eu vou logar, eu click o usuario é destruido
 *@SessionScoped:É criado no momento que eu logo, e so destruido no momento encerro a sessao
 * nullExeption: Object no instanciado ou no* foi preenchido
 * IOException: erro ao editar arquivo errado, arquivo no existir*/
@ManagedBean
@SessionScoped
public class AutenticacaoBean {

	private Usuario usuario;
	private Usuario usuarioLogado;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	@PostConstruct
	public void iniciar() {
		usuario = new Usuario();
		usuario.setPessoa(new Pessoa());
	}

	/*
	 * Todo variavel criado dentro do metodo é utilizado so no metodo, a partir do
	 * momento em que sair do metodo ela será excluida, já as variaveis criadas na
	 * classe, ficam vivam até desligar o servidor
	 */
	public void autenticar() {
		try {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			usuarioLogado = usuarioDAO.autenticar(usuario.getPessoa().getCpf(), usuario.getSenha());

			if (usuarioLogado == null) {
				Messages.addGlobalError("CPF e/ou senha incorretos");
				return;
			}

			Faces.redirect("./pages/principal.xhtml");// direcionar para pag principal
		} catch (IOException erro) {
			erro.printStackTrace();
			Messages.addGlobalError(erro.getMessage());
		}
	}
	
	/*o atributo rendered recebe um boolean que diz se o componente será renderizado.
		* Se você setar true o componente será renderizado. *
		* Se você setar false não será.*/
	
	/*Uma permissão por vez da lista de permissões
	 * Verificar, usuario Logado, eqauls(igual) a permissão
	 * return true
	 * Se ele sai do for e retorno false*/
	public boolean temPermissoes(List<String> permissoes) {
		for(String permissao : permissoes) {
			if (usuarioLogado.getTipo() == permissao.charAt(0)) {
				return true;
			}
		}
		return false;
	}

}
