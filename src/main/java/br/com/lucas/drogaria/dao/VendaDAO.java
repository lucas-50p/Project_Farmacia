package br.com.lucas.drogaria.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.lucas.drogaria.domain.ItemVenda;
import br.com.lucas.drogaria.domain.Venda;
import br.com.lucas.drogaria.util.HibernateUtil;

public class VendaDAO extends GenericDAO<Venda> {
	public void salvar(Venda venda, List<ItemVenda> itensVenda) {
		/*
		 * Se de erro em alguma linha entra no catch finally fecha uma sessao
		 */
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		try {
			transacao = sessao.beginTransaction();//
			
			sessao.save(venda);
			
			for (int posicao = 0; posicao < itensVenda.size(); posicao++) {
				ItemVenda itemVenda = itensVenda.get(posicao);
				itemVenda.setVenda(venda);
				
				sessao.save(itemVenda);
			}
			
			transacao.commit();// confireturn retorno;
		} catch (RuntimeException erro) {// verifica se a transacao e diferente de nulo
			if (transacao != null) {
				transacao.rollback();// reversão
			}
			throw erro;// error message
		} finally {
			sessao.close();
		}
	}
}
