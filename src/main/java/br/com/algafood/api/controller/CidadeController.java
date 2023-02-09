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
import org.springframework.web.bind.annotation.RestController;

import br.com.algafood.domain.entities.Cidade;
import br.com.algafood.domain.entities.Cozinha;
import br.com.algafood.domain.exception.EntidadeEmUsoException;
import br.com.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.algafood.domain.repository.CidadeRepository;
import br.com.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	
	@GetMapping
	public List<Cidade> listar() {
		return cidadeRepository.findAll();
		}
	
	
	
	@GetMapping("/{cidade_Id}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long cidade_Id) {
		Optional<Cidade> cidade = cidadeRepository.findById(cidade_Id);
	if(cidade!=null) {
		return ResponseEntity.ok(cidade.get());}
	else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
		try{
			cidade = cadastroCidade.salvar(cidade);
			return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		}catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
	}}
	
	@PutMapping("/{cidade_Id}")
	public ResponseEntity<?> atualizar(@PathVariable Long cidade_Id,
			@RequestBody Cidade cidade){
		try {
		Cidade cidadeRep = cidadeRepository.findById(cidade_Id).orElse(null);
			if(cidadeRep != null) {
				BeanUtils.copyProperties(cidade, cidadeRep, "id");
		//ultimo parâmetro é ignorado - ignora o ID para que ele continue o mesmo e n nullo
		cidadeRep = cadastroCidade.salvar(cidadeRep);
			}	return ResponseEntity.ok(cidadeRep);
		}catch (EntidadeNaoEncontradaException e) {
	return ResponseEntity.badRequest().body(e.getMessage());
	}}
	
	@DeleteMapping("/{cidade_Id}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long cidade_Id){
	try {
		cadastroCidade.excluir(cidade_Id);
		return ResponseEntity.noContent().build();
	}catch(EntidadeEmUsoException e) {
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}
	
	}
	
	
}
