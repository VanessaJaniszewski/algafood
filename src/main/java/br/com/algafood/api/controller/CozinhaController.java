package br.com.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.algafood.domain.entities.Cozinha;
import br.com.algafood.domain.exception.EntidadeEmUsoException;
import br.com.algafood.domain.repository.CozinhaRepository;
import br.com.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	
	@GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
		}
	
	
	
	@GetMapping("/{cozinha_Id}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinha_Id) {
		Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinha_Id);
		if(cozinha.isPresent()) {
		return ResponseEntity.ok(cozinha.get());}
		
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cadastroCozinha.merge(cozinha);
	}
	
	@PutMapping("/{cozinha_Id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinha_Id,
			@RequestBody Cozinha cozinha){
		Optional<Cozinha> cozinhaRep = cozinhaRepository.findById(cozinha_Id);
		
		if(cozinhaRep.isPresent()) {
//		cozinhaRep.setNome(cozinha.getNome());
		BeanUtils.copyProperties(cozinha, cozinhaRep.get(), "id");
		//ultimo parâmetro é ignorado - ignora o ID para que ele continue o mesmo e n nullo
		Cozinha cozinhaSalva = cadastroCozinha.merge(cozinhaRep.get());
		return ResponseEntity.ok(cozinhaRep.get());
	}
	return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{cozinha_Id}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long cozinha_Id){
	try {
		cadastroCozinha.excluir(cozinha_Id);
		return ResponseEntity.noContent().build();
	}catch(EntidadeEmUsoException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
	
	}
	
	
}
