package com.algaworks.curso.jpa2.dao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

import com.algaworks.curso.jpa2.jpaUtil.Transactional;
import com.algaworks.curso.jpa2.modelo.ModeloCarro;
import com.algaworks.curso.jpa2.service.NegocioException;

public class ModeloCarroDAO implements Serializable{
	
	@Inject
	private EntityManager em;
	
	public ModeloCarro buscarPeloCodigo(Long codigo) {
		return em.find(ModeloCarro.class, codigo);
	}
	
	public void salvar(ModeloCarro modeloCarro) {
		em.merge(modeloCarro);
	}
	
	public List<ModeloCarro> buscarTodos() {
		return em.createQuery("from ModeloCarro").getResultList();
	}
	
	@Transactional
	public void exclur(ModeloCarro modeloCarro) throws NegocioException{
		modeloCarro = buscarPeloCodigo(modeloCarro.getCodigo());
		try {
			em.remove(modeloCarro);
			em.flush();
		} catch (PersistenceException e) {
			throw new NegocioException("Este modelo não pode ser excluído.");
		}
	}

}
