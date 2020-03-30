package br.com.lucas.drogaria.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.lucas.drogaria.domain.Cidade;
import br.com.lucas.drogaria.util.HibernateUtil;

//Funcionalidade específica da Cidade
//(Long estadoCodigo):chave primaria do estado vem do GenericDomain(ver classe) e do tipo Long
//Restrictions.idEq: Comparar chave primaria
//Restrictions.eq: Comparar chave estrangeira
public class CidadeDAO extends GenericDAO<Cidade> {
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Cidade> buscarPorEstado(Long estadoCodigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();//Sessão aberta
		try {
			Criteria consulta = sessao.createCriteria(Cidade.class);//Criterio
			consulta.add(Restrictions.eq("estado.codigo", estadoCodigo));	
			consulta.addOrder(Order.asc("nome"));
			List<Cidade> resultado = consulta.list();
			return resultado;//Captura o erro
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}
