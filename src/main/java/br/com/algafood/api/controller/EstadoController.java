package br.com.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import br.com.algafood.domain.entities.Estado;
import br.com.algafood.domain.exception.EntidadeEmUsoException;
import br.com.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.algafood.domain.repository.EstadoRepository;
import br.com.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping(value = "/estados")
public class EstadoController {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	
	@GetMapping
	public List<Estado> listar() {
		return estadoRepository.findAll();
		}
	
	
	
	@GetMapping("/{estado_Id}")
	public ResponseEntity<Estado> buscar(@PathVariable Long estado_Id) {
		Optional<Estado> estado = estadoRepository.findById(estado_Id);
		if(estado!=null) {
		return ResponseEntity.ok(estado.get());}
		else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody Estado estado) {
		return cadastroEstado.merge(estado);
	}
	
	@PutMapping("/{estadoId}")
	public ResponseEntity<Estado> atualizar(@PathVariable Long estadoId,
			@RequestBody Estado estado) {
		Estado estadoAtual = estadoRepository.findById(estadoId).orElse(null);
		
		if (estadoAtual != null) {
			BeanUtils.copyProperties(estado, estadoAtual, "id");
			
			estadoAtual = cadastroEstado.merge(estadoAtual);
			return ResponseEntity.ok(estadoAtual);
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{estado_Id}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long estado_Id){
	try {
		cadastroEstado.excluir(estado_Id);
		return ResponseEntity.noContent().build();
	}catch(EntidadeEmUsoException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
	
	}
	
	
}
