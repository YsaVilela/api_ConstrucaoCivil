package br.com.magnasistemas.construcaocivil.repository.dominio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.magnasistemas.construcaocivil.entity.dominio.Cidade;

public interface CidadesRepository extends JpaRepository <Cidade,Long> {
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM tb_cidade; ALTER SEQUENCE tb_cidade_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndResetSequence();
}
