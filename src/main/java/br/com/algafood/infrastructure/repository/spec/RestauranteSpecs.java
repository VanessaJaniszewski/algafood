package br.com.algafood.infrastructure.repository.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import br.com.algafood.domain.entities.Restaurante;

public class RestauranteSpecs {


	public static Specification<Restaurante> comFreteGratis(){
		return (root, query, builder)->
		builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	}
//	Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder);
	
	
	public static Specification<Restaurante> comNomeSemelhante(String nome){
		return (root, query, builder)->
		builder.like(root.get("nome"), "%"+nome+"%");
	}

}
