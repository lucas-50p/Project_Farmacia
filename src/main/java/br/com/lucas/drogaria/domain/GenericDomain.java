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
	
	/*Para formatar o objeto*/
	@Override
	public String toString() {
		return String.format("%s[codigo=%d]", getClass().getSimpleName(), getCodigo());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}
	
	/*Diferenciar objetos
	 * equals: Ele usa metodo de igualdades
	 * Hash code: Usa Métodos matemáticos */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericDomain other = (GenericDomain) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	

	
	
}
