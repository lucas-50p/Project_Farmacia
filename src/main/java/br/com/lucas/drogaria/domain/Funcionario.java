package br.com.lucas.drogaria.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@SuppressWarnings("serial")
@Entity
public class Funcionario extends GenericDomain{

	@Column(length=15 ,nullable = false)
	private String carteiraTrabalho;
	
	@Column(nullable = false)
	private Date dataAdimissao;
	
	@OneToOne
	@JoinColumn(nullable = false)
	private Pessoa pessoa;

	public String getCarteiraTrabalho() {
		return carteiraTrabalho;
	}

	public void setCarteiraTrabalho(String carteiraTrabalho) {
		this.carteiraTrabalho = carteiraTrabalho;
	}

	public Date getDataAdimissao() {
		return dataAdimissao;
	}

	public void setDataAdimissao(Date dataAdimissao) {
		this.dataAdimissao = dataAdimissao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	
}
