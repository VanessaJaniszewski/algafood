package br.com.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.algafood.domain.entities.Cozinha;
import br.com.algafood.domain.entities.Estado;
import br.com.algafood.domain.exception.EntidadeEmUsoException;
import br.com.algafood.domain.exception.EntidadeNaoEncontradaException;
import br.com.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	
	public Estado merge(Estado estado) {
	return estadoRepository.save(estado);
	}
	
	public void excluir(Long estado_Id) {
		try {
			estadoRepository.deleteById(estado_Id);
		}catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de Estado com o código %d",estado_Id));
		}catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Estado de código %d não poder ser removida, "
							+ "pois está em uso", estado_Id));
		}
	}
	
	
}
