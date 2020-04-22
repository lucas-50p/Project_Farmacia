package br.com.lucas.drogaria.dao;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.lucas.drogaria.domain.Usuario;
import br.com.lucas.drogaria.util.HibernateUtil;

public class UsuarioDAO extends GenericDAO<Usuario> {

	// Método autenticar no dar para ficar no generic, ele algo específico
	// Autenticar por Cpf e senha
	// Retorno vai ser tipo Usuario, metodo autenticar
	// Tipo da entidade é Usuario
	/*
	 * CriteriaQuery o método atual, mas não sei como implementá-lo para trazer um
	 * só registro, até conseguir para trazer vários, usando .getResultList(), 
	 
	 */
	public Usuario autenticar(String cpf, String senha) {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			@SuppressWarnings("deprecation")
			Criteria consulta = sessao.createCriteria(Usuario.class);
			consulta.createAlias("pessoa", "p");// Classe pessoa , vou chamar de p

			consulta.add(Restrictions.eqOrIsNull("p.cpf", cpf));

			SimpleHash hash = new SimpleHash("md5", senha);
			consulta.add(Restrictions.eq("senha", hash.toHex()));

			Usuario resultado = (Usuario) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}
