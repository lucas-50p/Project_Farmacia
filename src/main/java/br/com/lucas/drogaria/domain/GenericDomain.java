package br.com.lucas.drogaria.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/*É uma classe vai ser por todas outras*/

@SuppressWarnings("serial")//ignora warning
@MappedSuperclass //Não corresponde uma tabela, vai ser por outros para gerar tabelas, compartilhar
public class GenericDomain implements Serializable{
	
	@Id//Chave primaria, que herdar, vai ter campo código
	@GeneratedValue(strategy = GenerationType.AUTO)//()-- ele vai ter propriedade,banco que vai controlar auto numbers no banco de dados
	private Long codigo;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	
	
}
