package br.com.magnasistemas.construcaocivil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.magnasistemas.construcaocivil.entity.Construtora;

public interface ConstrutoraRepository extends JpaRepository<Construtora,Long> {

	Page<Construtora> findAllByStatusTrue(Pageable paginacao);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM tb_construtora; ALTER SEQUENCE tb_construtora_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndResetSequence();
}
