package br.com.lucas.drogaria.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.omnifaces.util.Faces;

import br.com.lucas.drogaria.bean.AutenticacaoBean;
import br.com.lucas.drogaria.domain.Usuario;

@SuppressWarnings("serial")
public class AutenticacaoListener implements PhaseListener{
	
	/*Os PhaseListener são objetos que serão notificados no início e término de cada etapa.
		*Normalmente são usados como “interceptadores”, isto é, você quer interceptar uma requisição para realizar algo específico, 
		*como validar o login do usuário no sistema, por exemplo.
		*Quando você implementa o PhaseListener, 
		*você obrigatoriamente precisa obedecer o contrato no qual garante os métodos:*/

	
	// Manipula uma notificação que está procesando uma fase do ciclo de vida JSF quando este já completou.
	@Override
	public void afterPhase(PhaseEvent event) {
		//System.out.println("Depois da Fase: " + event.getPhaseId());
		String paginaAtual = Faces.getViewId();
		
		boolean ehPaginaDeAutenticacao = paginaAtual.contains("autenticacao.xhtml");
		
		/*Para ver se estou na tela publica ou bloqueada
		 * Eu pego bean da autenticação*/
		if (!ehPaginaDeAutenticacao) {//No é pagina de autenticação
			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
			
			 /*Forço usuario ir para tela de autenticação
			  * Se o bean da autenticação no foi criado, que dizer que eu nunca entrei na tela */
			if(autenticacaoBean == null){
				Faces.navigate("/pages/autenticacao.xhtml");
				return;
			}
			 /*Se o bean ja foi criado
			  * Eu pego o usuario
			  * Usuario igual nulo: eu nao conseguir logar
			  * Devolve para tela de autenticação*/
			Usuario usuario = autenticacaoBean.getUsuarioLogado();
			if(usuario == null){
				Faces.navigate("/pages/autenticacao.xhtml");
				return;
			}
		}
		 
		
		 
	}

	// Manipula uma notificação que está procesando uma fase do ciclo de vida JSF quando este está prestes a iniciar.
	@Override
	public void beforePhase(PhaseEvent event) {
		
	}

	// Retorna o Tipo de Fase no JSF que o objeto será notificado. Pode ser qualquer uma das 6 fases, ou todas as fases.[/code]
	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
