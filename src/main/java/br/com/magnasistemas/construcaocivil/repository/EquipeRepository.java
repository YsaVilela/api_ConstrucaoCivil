package br.com.magnasistemas.construcaocivil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.magnasistemas.construcaocivil.entity.Equipe;

public interface EquipeRepository extends JpaRepository<Equipe,Long>{

	Page<Equipe> findAllByStatusTrue(Pageable paginacao);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM tb_equipe; ALTER SEQUENCE tb_equipe_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndResetSequence();

}
