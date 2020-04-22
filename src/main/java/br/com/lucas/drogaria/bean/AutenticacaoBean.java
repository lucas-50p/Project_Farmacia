package br.com.lucas.drogaria.bean;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@PostConstruct
	public void iniciar() {
		usuario = new Usuario();
		usuario.setPessoa(new Pessoa());
	}
	
	public void autenticar() {
		try {
			Faces.redirect("./pages/principal.xhtml");//direcionar para pag principal

		} catch (IOException erro) {
			erro.printStackTrace();
			Messages.addGlobalError(erro.getMessage());
		}
	}
}
