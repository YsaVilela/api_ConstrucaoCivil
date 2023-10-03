package br.com.magnasistemas.construcaocivil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.magnasistemas.construcaocivil.entity.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco,Long>{
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM tb_endereco; ALTER SEQUENCE tb_endereco_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndResetSequence();

}
