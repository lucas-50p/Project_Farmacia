package br.com.lucas.drogaria.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import br.com.lucas.drogaria.dao.CaixaDAO;
import br.com.lucas.drogaria.dao.FuncionarioDAO;
import br.com.lucas.drogaria.domain.Caixa;
import br.com.lucas.drogaria.domain.Funcionario;

@ManagedBean
@ViewScoped
public class CaixaBean {
	private Caixa caixa;
	
	private ScheduleModel caixas;
	private List<Funcionario> funcionarios;

	public Caixa getCaixa() {
		return caixa;
	}

	public void setCaixa(Caixa caixa) {
		this.caixa = caixa;
	}

	public ScheduleModel getCaixas() {
		return caixas;
	}

	public void setCaixas(ScheduleModel caixas) {
		this.caixas = caixas;
	}
	
	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	@PostConstruct
	public void listar() {
		caixas = new DefaultScheduleModel();
	}

	//SelectEvent: Serve para capturar informações do evento
	public void novo(SelectEvent evento) {
		
		caixa = new Caixa();
		
		caixa.setDataAbertura((Date) evento.getObject());
		
		FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
		funcionarios = funcionarioDAO.listar();
	}
	
	public void salvar() {
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(caixa.getDataAbertura());//Recebe a data
			calendar.add(Calendar.DATE, 1);
			caixa.setDataAbertura(calendar.getTime());//Caixa recebe o calendar
			
			CaixaDAO caixaDAO = new CaixaDAO();
			caixaDAO.salvar(caixa);
			Messages.addGlobalInfo("Caixa aberto com sucesso!");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar salvar");
			erro.printStackTrace();
		}
	
	}
}
