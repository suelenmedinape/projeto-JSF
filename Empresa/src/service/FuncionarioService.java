package service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import modelo.Funcionario;

@Stateless
public class FuncionarioService extends GenericService<Funcionario>{
	public FuncionarioService() {
		super(Funcionario.class);
	}
	
	public List<Funcionario> listarTodasFilias(Double valorMin, Double valorMax) {

		final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Funcionario> criteriaQuery = criteriaBuilder.createQuery(Funcionario.class);
		final Root<Funcionario> funcionarioRoot = criteriaQuery.from(Funcionario.class);
		criteriaQuery.select(funcionarioRoot);
			
		criteriaQuery.where(criteriaBuilder.between(funcionarioRoot.get("salario"),valorMin, valorMax));
		criteriaQuery.orderBy(criteriaBuilder.desc(funcionarioRoot.get("salario")));
		List<Funcionario> resultado = getEntityManager().createQuery(criteriaQuery).getResultList();
		
		return resultado;
	}
	
	public List<Funcionario> listarPorFilial(String filial, Double valorMin, Double valorMax){
		final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Funcionario> criteriaQuery = criteriaBuilder.createQuery(Funcionario.class);
		final Root<Funcionario> funcionarioRoot = criteriaQuery.from(Funcionario.class);
		criteriaQuery.select(funcionarioRoot);
		

		criteriaQuery.where(criteriaBuilder.and(
				criteriaBuilder.like(funcionarioRoot.get("filial").get("nome"), "%" +filial+ "%"),
				criteriaBuilder.between(funcionarioRoot.get("salario"), valorMin, valorMax)
				));

		criteriaQuery.where(criteriaBuilder.and(
				criteriaBuilder.like(funcionarioRoot.get("filial").get("nome"), "%" +filial+ "%"),
				criteriaBuilder.between(funcionarioRoot.get("salario"), valorMin, valorMax)
				));


		criteriaQuery.orderBy(criteriaBuilder.desc(funcionarioRoot.get("salario")));
		List<Funcionario> resultado = getEntityManager().createQuery(criteriaQuery).getResultList();
		
		return resultado;
	}

	public List<Funcionario> listarPorCidade(String cidade){
		final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Funcionario> criteriaQuery = criteriaBuilder.createQuery(Funcionario.class);
		final Root<Funcionario> funcionarioRoot = criteriaQuery.from(Funcionario.class);
		criteriaQuery.select(funcionarioRoot);
	
		criteriaQuery.where(criteriaBuilder.equal(funcionarioRoot.get("endereco").get("cidade"), cidade));

		criteriaQuery.orderBy(criteriaBuilder.asc(funcionarioRoot.get("nome")));
		List<Funcionario> resultado = getEntityManager().createQuery(criteriaQuery).getResultList();
		
		return resultado;
	}

}
