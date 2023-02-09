package br.com.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import br.com.algafood.domain.entities.Restaurante;

public interface RestauranteRepositoryQueries {

	
	List<Restaurante> find(String nome,
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);
	
	
	List<Restaurante> findComFrenteGratis(String nome);
	
}
