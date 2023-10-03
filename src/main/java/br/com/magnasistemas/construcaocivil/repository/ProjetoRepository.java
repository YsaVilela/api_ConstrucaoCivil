package br.com.magnasistemas.construcaocivil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.magnasistemas.construcaocivil.entity.Projeto;

public interface ProjetoRepository extends JpaRepository<Projeto,Long>{
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM tb_projeto; ALTER SEQUENCE tb_projeto_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndResetSequence();

}
