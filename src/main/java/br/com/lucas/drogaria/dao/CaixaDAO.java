package br.com.lucas.drogaria.dao;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.lucas.drogaria.domain.Caixa;
import br.com.lucas.drogaria.util.HibernateUtil;

public class CaixaDAO extends GenericDAO<Caixa>{

	//Metodo de consulta, no precisar roll back(no faço commit)
	@SuppressWarnings("deprecation")
	public Caixa buscar(Date dataAbertura) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		
		try {
			//dataAbertura é do caixa que vai na esquerda, direita vai o parametro(dataAbertura)
			//Resultado: For nulo que dizer no tem caixa pra aquele dia;Diferente de nulo já existe caixa no posso deixar usuario abrir outro
			//uniqueResult: retorna um unico resultado
			Criteria consulta = sessao.createCriteria(Caixa.class);
			consulta.add(Restrictions.eq("dataAbertura", dataAbertura));
			Caixa resultado = (Caixa) consulta.uniqueResult();
			return resultado;
			
		} catch (RuntimeException erro) {
			throw erro;//Quem chamar , o bean vai ser obrigado aa tratar
		}finally {//Fechar sessão
			sessao.close();
		}
	}
}
