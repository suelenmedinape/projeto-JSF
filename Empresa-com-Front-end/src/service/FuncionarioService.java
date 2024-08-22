package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import modelo.Funcionario;

@Stateless //não armazena informações entre as chamadas de métodos e pode ser reutilizado por diferentes clientes simultaneamente
public class FuncionarioService extends GenericService<Funcionario>{
	public FuncionarioService() {
		super(Funcionario.class);
	}
	
	public List<Funcionario> listarTodasFilias(Double valorMin, Double valorMax) {
		//constroi a consulta
		final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		//Define o tipo de resultado da consulta (neste caso, Funcionario).
		final CriteriaQuery<Funcionario> criteriaQuery = criteriaBuilder.createQuery(Funcionario.class);
		//define a entidade principal na consulta, no caso Funcionario
		final Root<Funcionario> funcionarioRoot = criteriaQuery.from(Funcionario.class);
		criteriaQuery.select(funcionarioRoot);
		//criteriaBuilder.between == SELECT filiais FROM Funcionario WHERE valorMin AND valorMax	
		criteriaQuery.where(criteriaBuilder.between(funcionarioRoot.get("salario"),valorMin, valorMax));
		//ordena os resultados em ordem decrescente
		criteriaQuery.orderBy(criteriaBuilder.desc(funcionarioRoot.get("salario")));
		//execulta a consulta e 
		List<Funcionario> resultado = getEntityManager().createQuery(criteriaQuery).getResultList();
		//retorna o resultado
		return resultado;
	}
	
	public List<Funcionario> listarPorFilial(String filial, Double valorMin, Double valorMax){
		final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Funcionario> criteriaQuery = criteriaBuilder.createQuery(Funcionario.class);
		final Root<Funcionario> funcionarioRoot = criteriaQuery.from(Funcionario.class);
		criteriaQuery.select(funcionarioRoot);
		
		//criteriaBuilder.and: Combina as condições de LIKE e BETWEEN usando AND
		criteriaQuery.where(criteriaBuilder.and(
				//criteriaBuilder.like usa o operador LIKE do SQL
				//Adiciona uma cláusula WHERE que filtra os funcionários cujo nome da filial contém a string filial
				criteriaBuilder.like(funcionarioRoot.get("filial").get("nome"), "%" +filial+ "%"),
				criteriaBuilder.between(funcionarioRoot.get("salario"), valorMin, valorMax)
				));
		//ordena em ordem decrescente
		criteriaQuery.orderBy(criteriaBuilder.desc(funcionarioRoot.get("salario")));
		List<Funcionario> resultado = getEntityManager().createQuery(criteriaQuery).getResultList();
		
		return resultado;
	}
	public List<Funcionario> listarPorCidade(String cidade){
		final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Funcionario> criteriaQuery = criteriaBuilder.createQuery(Funcionario.class);
		final Root<Funcionario> funcionarioRoot = criteriaQuery.from(Funcionario.class);
		criteriaQuery.select(funcionarioRoot);
		//Adiciona uma cláusula WHERE que filtra os funcionários cujo campo cidade no endereço é igual ao valor passado
		criteriaQuery.where(criteriaBuilder.equal(funcionarioRoot.get("endereco").get("cidade"), cidade));
		//Ordena os resultados em ordem crescente de nome
		criteriaQuery.orderBy(criteriaBuilder.asc(funcionarioRoot.get("nome")));
		List<Funcionario> resultado = getEntityManager().createQuery(criteriaQuery).getResultList();
		
		return resultado;
	}
	
}
