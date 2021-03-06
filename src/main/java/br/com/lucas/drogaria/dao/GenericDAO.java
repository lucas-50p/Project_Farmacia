package br.com.lucas.drogaria.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

import br.com.lucas.drogaria.util.HibernateUtil;

public class GenericDAO<Entidade> {

	private Class<Entidade> classe;

	@SuppressWarnings("unchecked")
	public GenericDAO() {
		this.classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	
	public void salvar(Entidade entidade) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		/*
		 * Se de erro em alguma linha entra no catch finally fechat uma sessao
		 */
		try {
			transacao = sessao.beginTransaction();
			sessao.save(entidade);
			transacao.commit();// comfirma ,Termina
		} catch (RuntimeException erro) {// verifica se a transacao e diferente de nulo
			if (transacao != null) {
				transacao.rollback();// reversão
			}
			throw erro;// error message
		} finally {
			sessao.close();
		}
	}

	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Entidade> listar() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(classe);
			List<Entidade> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Entidade> listar(String campoOrdenacao) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(classe);
			consulta.addOrder(Order.asc(campoOrdenacao));
			List<Entidade> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	
	public Entidade buscar(Long codigo) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Entidade resultado = null;
		try {
			resultado = sessao.find(classe, codigo);
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	
	public void excluir(Entidade entidade) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		/*
		 * Se de erro em alguma linha entra no catch finally fecha uma sessao
		 */
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(entidade);
			transacao.commit();// confirma ,Termina
		} catch (RuntimeException erro) {// verifica se a transacao e diferente de nulo
			if (transacao != null) {
				transacao.rollback();// reversão
			}
			throw erro;// error message
		} finally {
			sessao.close();
		}
	}
	
	public void editar(Entidade entidade) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		/*
		 * Se de erro em alguma linha entra no catch finally fecha uma sessao
		 */
		try {
			transacao = sessao.beginTransaction();
			sessao.update(entidade);
			transacao.commit();// comfirma ,Termina
		} catch (RuntimeException erro) {// verifica se a transacao e diferente de nulo
			if (transacao != null) {
				transacao.rollback();// reversão
			}
			throw erro;// error message
		} finally {
			sessao.close();
		}
	}
	//merge: fusão
	@SuppressWarnings("unchecked")
	public Entidade merge(Entidade entidade) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction transacao = null;

		/*
		 * Se de erro em alguma linha entra no catch finally fecha uma sessao
		 */
		try {
			
			
			transacao = sessao.beginTransaction();
			Entidade retorno = (Entidade) sessao.merge(entidade);
			transacao.commit();// confirma ,Termina
			return retorno;
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