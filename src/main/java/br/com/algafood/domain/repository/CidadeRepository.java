package br.com.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.algafood.domain.entities.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Long>{

//	
//	List<Cidade> listar();
//	Cidade buscar (Integer id);
//	Cidade merge(Cidade cidade);
//	void remover(Integer cidade_Id);
//	

}
