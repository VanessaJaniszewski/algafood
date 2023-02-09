package br.com.algafood.infrastructure.repository;

import static br.com.algafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static br.com.algafood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import br.com.algafood.domain.entities.Restaurante;
import br.com.algafood.domain.repository.RestauranteRepository;
import br.com.algafood.domain.repository.RestauranteRepositoryQueries;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries{

	@PersistenceContext
	private EntityManager manager;
	
	@Autowired @Lazy
	private RestauranteRepository restauranteRepository;
	
	@Override
	public List<Restaurante> find(String nome, 
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){

		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<Restaurante> criteriaQuery = criteriaBuilder.createQuery(
				Restaurante.class);
		Root<Restaurante> root = criteriaQuery.from(Restaurante.class);
		
		var predicates = new ArrayList<Predicate>();
		if(StringUtils.hasText(nome)) {
			Predicate nomePredicate = criteriaBuilder.like(root.get("nome"), "%"+nome+"%");
			predicates.add(nomePredicate);
		}if(taxaFreteInicial!=null) {
			Predicate taxaInicialPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial);
			predicates.add(taxaInicialPredicate);
		}if(taxaFreteFinal!= null) {
			Predicate taxaFinalPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal);
			predicates.add(taxaFinalPredicate);
		}
		
		for (Predicate predicate : predicates) {
			criteriaQuery.where(predicates.toArray(new Predicate[0]));
		}
		TypedQuery<Restaurante> query = manager.createQuery(criteriaQuery);
		return query.getResultList();

	}

	@Override
	public List<Restaurante> findComFrenteGratis(String nome) {
		return restauranteRepository.findAll(comFreteGratis().
				and(comNomeSemelhante(nome)));}
	
	
//	public List<Restaurante> find(String nome, 
//			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){
//		var jpql = new StringBuilder();
//		var parametros = new HashMap<String, Object>();
//		
//		jpql.append("from Restaurante where 0=0 ");
//			if(!nome.isEmpty()) {
//				jpql.append("and nome like: nome ");
//				parametros.put("nome", "%"+nome+"%");
//			}if(taxaFreteInicial!=null) {
//				jpql.append("and taxaFrete >= :taxaInicial ");
//				parametros.put("taxaInicial", taxaFreteInicial);
//			}if(taxaFreteFinal!=null) {
//				jpql.append("and taxaFrete <= :taxaFinal");
//				parametros.put("taxaFinal", taxaFreteFinal);
//			}	
//		TypedQuery<Restaurante> query = 
//				manager.createQuery(jpql.toString(), Restaurante.class);
//			
//		parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
//		return query.getResultList();}
}

