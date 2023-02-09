package br.com.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.algafood.domain.entities.Cozinha;
import br.com.algafood.domain.entities.Restaurante;
import br.com.algafood.domain.repository.CozinhaRepository;
import br.com.algafood.domain.repository.RestauranteRepository;
import br.com.algafood.infrastructure.repository.RestauranteRepositoryImpl;

@RestController
@RequestMapping("/teste")
public class TesteController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private RestauranteRepositoryImpl restauranteRepositoryImpl;
	
	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> cozinhasPorNome(String nome) {
		return cozinhaRepository.findTodasByNomeContaining(nome);
	}
	
	@GetMapping("/cozinhas/unica-por-nome")
	public Optional<Cozinha> cozinhaPorNome(String nome) {
		return cozinhaRepository.findByNome(nome);
	}
	
	@GetMapping("/cozinhas/exists")
	public boolean cozinhaExists(String nome) {
		return cozinhaRepository.existsByNome(nome);
	}
	
	@GetMapping("/restaurantes/por-taxa-frete")
	public List<Restaurante> restaurantesPorTaxaFrete(
			BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.queryByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	@GetMapping("/restaurantes/por-nome")
	public List<Restaurante> restaurantesPorTaxaFrete(
			String nome, Integer cozinhaId) {
		return restauranteRepository.consultarPorNome(nome, cozinhaId);
	}
	
	@GetMapping("/restaurantes/primeiro-por-nome")
	public Optional<Restaurante> restaurantePrimeiroPorNome(String nome) {
		return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
	}
	
	@GetMapping("/restaurantes/top2-por-nome")
	public List<Restaurante> restaurantesTop2PorNome(String nome) {
		return restauranteRepository.findTop2ByNomeContaining(nome);
	}
	
	@GetMapping("/restaurantes/por-nome-e-frete")
	public List<Restaurante> restaurantesPorNomeFrete(String nome,
			BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
	}
	
	
	@GetMapping("/restaurantes/count-por-cozinha")
	public int restaurantesCountPorCozinha(Integer cozinhaId) {
		return restauranteRepository.countByCozinhaId(cozinhaId);
	}
	
	
	// Padrão Specification:
	@GetMapping("/restaurantes/com-frete-gratis")
	public List<Restaurante> restaurantesComFrenteGratis(String nome) {		
		return restauranteRepositoryImpl.findComFrenteGratis(nome);
}
	@GetMapping("/restaurantes/primeiro")
	public Optional<Restaurante>restaurantePrimeiro(){
		return restauranteRepository.buscarPrimeiro();
	}
	
	@GetMapping("/cozinhas/primeiro")
	public Optional<Cozinha>cozinhaPrimeiro(){
		return cozinhaRepository.buscarPrimeiro();
	}


}
	
	
	
	
