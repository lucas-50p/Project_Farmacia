package br.com.lucas.drogaria.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

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
		System.out.println("Depois da Fase: " + event.getPhaseId()); 
		
	}

	// Manipula uma notificação que está procesando uma fase do ciclo de vida JSF quando este está prestes a iniciar.
	@Override
	public void beforePhase(PhaseEvent event) {
		System.out.println("Antes da Fase: " + event.getPhaseId());
	}

	// Retorna o Tipo de Fase no JSF que o objeto será notificado. Pode ser qualquer uma das 6 fases, ou todas as fases.[/code]
	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}

}
