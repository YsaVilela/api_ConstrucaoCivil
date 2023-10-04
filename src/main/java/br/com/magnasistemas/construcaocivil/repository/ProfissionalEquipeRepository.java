package br.com.magnasistemas.construcaocivil.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.magnasistemas.construcaocivil.entity.ProfissionalEquipe;

public interface ProfissionalEquipeRepository extends JpaRepository<ProfissionalEquipe, Long> {

	@Query("select r from ProfissionalEquipe r where r.equipe.id = :idEquipe")
	Page<ProfissionalEquipe> findByIdEquipe(Long idEquipe, Pageable paginacao);

	@Query("select r from ProfissionalEquipe r where r.profissional.id = :idProfissional")
	Page<ProfissionalEquipe> findByIdProfissional(Long idProfissional, Pageable paginacao);

	@Modifying
	@Query("delete from ProfissionalEquipe r where r.equipe.id = :idEquipe")
	void deleteByIdEquipe(Long idEquipe);

	@Modifying
	@Query("""
			delete from ProfissionalEquipe r
			where r.profissional.id = :idProfissional
			""")
	void deleteByIdProfissional(Long idProfissional);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM tb_profissional_equipe; ALTER SEQUENCE tb_profissional_equipe_id_seq RESTART WITH 1", nativeQuery = true)
	void deleteAllAndResetSequence();

	@Modifying
	@Query("""
			delete from ProfissionalEquipe r
			where r.profissional.construtora.id = :idConstrutora
			""")
	void deleteByIdProfissionalConstrutora(Long idConstrutora);

	@Modifying
	@Query("""
			delete from ProfissionalEquipe r
			where r.equipe.construtora.id = :idConstrutora
			""")
	void deleteByIdEquipeConstrutora(Long idConstrutora);

	@Query("""
			select r from ProfissionalEquipe r
			where r.profissional.status = true AND  r.equipe.status = true
			""")
	Page<ProfissionalEquipe> findByEquipeProfissionalStatusTrue(Pageable paginacao);

}
