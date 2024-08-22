package service;

import javax.ejb.Stateless;

import modelo.Filial;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import modelo.Filial;
import modelo.Funcionario;


@Stateless
public class FilialService extends GenericService<Filial>{
	
	public FilialService() {
		super(Filial.class);
	}
	
	public List<Filial> listarPorFilias(String nomeFilial) {
		final CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Filial> criteriaQuery = criteriaBuilder.createQuery(Filial.class);
		final Root<Filial> filialRoot = criteriaQuery.from(Filial.class);
		criteriaQuery.select(filialRoot);
			
		//a % faz com o que a busca contenha o que foi digitado em qualquer posi��o
		criteriaQuery.where(
				criteriaBuilder.like(filialRoot.get("nome"), "%" +nomeFilial+ "%")
				);
		//ordem crescente
		criteriaQuery.orderBy(criteriaBuilder.asc(filialRoot.get("endereco").get("cidade")));
		//execulta a consulta no banco de dados
		List<Filial> resultado = getEntityManager().createQuery(criteriaQuery).getResultList();
		
		return resultado;
	}
	
}
