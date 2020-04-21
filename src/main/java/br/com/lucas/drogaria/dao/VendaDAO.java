package br.com.lucas.drogaria.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.lucas.drogaria.domain.ItemVenda;
import br.com.lucas.drogaria.domain.Produto;
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
				
				sessao.save(itemVenda);//Salvo
				
				//Variavel quantidade, está no produto(qtd Estoque) e item venda(qtd Vendida)
				//(produto.getQuantidade() - itemVenda.getQuantidade()) : Está sendo inteiro
				//(produto.getQuantidade() - itemVenda.getQuantidade():Primeiro ele subtrai
				//+ "": Depois ele converte para String
				//new Short: Por fim ele cria um Short
				Produto produto = itemVenda.getProduto();
				int quantidade = produto.getQuantidade() - itemVenda.getQuantidade();
				
				if (quantidade >= 0) {
					produto.setQuantidade(new Short(quantidade + ""));
					sessao.update(produto);//Atualizo
				} else {
					throw new RuntimeException("Quantidade insuficiente em estoque");//throw new cai no else,vai ver se tem catch no RunTime; catch vai ser capturado catch VendaBean
				}
			
			}
			
			transacao.commit();// config return retorno;
		} catch (RuntimeException erro) {// verifica se a transacao e diferente de nulo
			if (transacao != null) {
				transacao.rollback();// reversão, ele vai  desfazer updates em cima do produto, desfazer insert da itemVenda
			}
			throw erro;// error message
		} finally {
			sessao.close();
		}
	}
}
