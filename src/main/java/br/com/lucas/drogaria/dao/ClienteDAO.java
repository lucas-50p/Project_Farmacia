package br.com.lucas.drogaria.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.lucas.drogaria.domain.Cliente;
import br.com.lucas.drogaria.util.HibernateUtil;

public class ClienteDAO extends GenericDAO<Cliente>{

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Cliente> listarOrdenado() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Cliente.class);
			consulta.createAlias("pessoa", "p");
			/*consulta.createAlias("p.cidade", "c");*/
			consulta.addOrder(Order.asc("p.nome"));
			List<Cliente> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}
