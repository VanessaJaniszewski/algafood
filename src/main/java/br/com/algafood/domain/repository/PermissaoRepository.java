package br.com.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.algafood.domain.entities.Cozinha;

public interface PermissaoRepository extends JpaRepository<Cozinha, Long>{

//	
//	List<Permissao> listar();
//	Permissao buscar (int id);
//	Permissao merge(Permissao permissao);
//	void remover(Permissao permissao);
	

}
