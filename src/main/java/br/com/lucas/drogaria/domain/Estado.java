package br.com.lucas.drogaria.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

//Herdando da chave primaria
@SuppressWarnings("serial")
@Entity//Ele vai gerar uma tabela nome igual = estado, colunas nomes = atributos 
public class Estado extends GenericDomain{
	
	//nullable = false: que não pode ser vazio o text, campo obrigatorios
	@Column(length = 2, nullable = false)//mudar var char de 250 para 2
	private String sigla;
	@Column(length = 50, nullable = false)//mudar var char de 250 para 50, gasto de memória
	private String nome;
	
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
