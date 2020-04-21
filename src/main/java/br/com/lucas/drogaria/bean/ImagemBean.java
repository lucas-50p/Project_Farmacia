package br.com.lucas.drogaria.bean;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@RequestScoped//Imagem vai ser recarregada a cada click
public class ImagemBean {
	
	/*
	 * Classe Bean Uma classe que contenha todos os atributos privados Possua
	 * getters e setters para seus atributos Usada para encapsular e abstrair uma
	 * entidade Implementa java.io.Serializable
	 */
	
	@ManagedProperty("#{param.caminho}")//Conexão com produto.xhtml, o paramentro do caminho
	private String caminho;
	private StreamedContent foto;

	public String getCaminho() {
		return caminho;
	}

	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}

	public StreamedContent getFoto() throws IOException {
		if (caminho == null || caminho.isEmpty()) {
			Path path = Paths.get("C:\\ws-delfino-upload/branco.png");
			InputStream stream = Files.newInputStream(path);
			foto = new DefaultStreamedContent(stream);
		}else {
			Path path = Paths.get(caminho);
			InputStream stream = Files.newInputStream(path);
			foto = new DefaultStreamedContent(stream);
		}
		
		return foto;
	}

	public void setFoto(StreamedContent foto) {
		this.foto = foto;
	}

}
