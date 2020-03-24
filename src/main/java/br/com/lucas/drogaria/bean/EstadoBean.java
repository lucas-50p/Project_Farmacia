package br.com.lucas.drogaria.bean;

import javax.faces.bean.ManagedBean;

import org.omnifaces.util.Messages;

/*Faces Mensagem:String encapsulado dentro de outra classe
 * FacesSumamary: seria o erro de forma resumida
 * FacesDetails: erro mais detalhado
 * FaceMensagens: String encapsulada dentro de outra classe,junto  com outras coisas jsf
 * Severity: é o tipo da mensagem, impactar no tipo da cor do primeFace exibi para nos
 * SEVERITY_INFO:Informação, na tela
 * SEVERITY_ERROR: na tela erro*/
@ManagedBean//tratar do controle e do modelo dentro da nossa aplicação,Dados que conversam com a tela
public class EstadoBean {
	public void salvar(){
		/*FacesMensagens ---
		 * String texto = "Programação Web com Java";
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, texto, texto);
		
		FacesContext contexto = FacesContext.getCurrentInstance();
		contexto.addMessage(null, message);*/
		
		Messages.addGlobalInfo("Programação Web com Java");
	}
}