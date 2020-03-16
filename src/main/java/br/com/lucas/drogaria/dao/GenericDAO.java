package br.com.lucas.drogaria.dao;

import java.lang.reflect.ParameterizedType;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.lucas.drogaria.util.HibernateUtil;


public class GenericDAO <Entidade>{
	
	private Class<Entidade> classe;

	@SuppressWarnings("unchecked")
	public GenericDAO() {
		this.classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}
	public void salvar(Entidade entidade) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;
		
		/*Se de erro em alguma linha entra no catch
		 * finally fechat uma sessao*/
		try {
			transacao = sessao.beginTransaction();
			sessao.save(entidade);
			transacao.commit();//comfirma ,Termina
		}catch(RuntimeException erro) {//verifica se a transacao e diferente de nulo
			if (transacao != null) {
				transacao.rollback();//reversão
			} 
			throw erro;//error message
		}finally {
			sessao.close();
		}
	}
}
