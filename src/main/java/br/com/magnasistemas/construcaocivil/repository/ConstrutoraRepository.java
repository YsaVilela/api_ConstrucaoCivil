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

	@Query("""
			select r from Construtora r
			where r.cnpj = :cnpj 
			""")
	Construtora findByCpf(String cnpj);

	@Query("""
			select r from Construtora r
			where r.telefone = :telefone 
			""")
	Construtora findByTelefone(String telefone);

	@Query("""
			select r from Construtora r
			where r.email = :email 
			""")
	Construtora findByEmail(String email);
	
	
}
