package br.com.magnasistemas.construcaocivil.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.magnasistemas.construcaocivil.entity.Profissional;

public interface ProfissionalRepository extends JpaRepository<Profissional,Long>{

	Page<Profissional> findAllByStatusTrue(Pageable paginacao);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM tb_profissional; ALTER SEQUENCE tb_profissional_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndResetSequence();

	@Modifying
	@Query("""
			delete from Profissional r
			where r.construtora.id = :idConstrutora 
			""")
	void deleteByIdConstrutora(Long idConstrutora);

	
	@Query("""
			select r from Profissional r
			where r.construtora.id = :idConstrutora 
			""")
	Profissional findByIdConstrutora(Long idConstrutora);

	@Query("""
			select r from Profissional r
			where r.cpf = :cpf 
			""")
	Profissional findByCpf(String cpf);

	@Query("""
			select r from Profissional r
			where r.telefone = :telefone 
			""")
	Profissional findByTelefone(String telefone);

}
