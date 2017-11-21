package com.algaworks.curso.jpa2.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.algaworks.curso.jpa2.jpaUtil.Transactional;
import com.algaworks.curso.jpa2.modelo.Aluguel;
import com.algaworks.curso.jpa2.service.NegocioException;

public class AluguelDAO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager em;

	public void salvar(Aluguel aluguel) {
		em.merge(aluguel);
	}

	@SuppressWarnings("unchecked")
	public List<Aluguel> buscarTodos() {
		return em.createQuery("from Aluguel").getResultList();
	}

	public Aluguel buscarPeloCodigo(Long codigo) {
		return em.find(Aluguel.class, codigo);
	}

	@Transactional
	public void excluir(Aluguel aluguel) throws NegocioException {
		aluguel = buscarPeloCodigo(aluguel.getCodigo());
		try {
			em.remove(aluguel);
			em.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("O aluguel não pode ser excluído.");
		}
	}

}
