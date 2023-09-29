package br.com.magnasistemas.construcaocivil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.magnasistemas.construcaocivil.entity.ProfissionalEquipe;

public interface ProfissionalEquipeRepository extends JpaRepository<ProfissionalEquipe,Long>{

	@Query("""
			select r from ProfissionalEquipe r where (r.equipe.id = :idEquipe)
			""")
	Page<ProfissionalEquipe> findByIdEquipe( Long idEquipe, Pageable pageable);
	
	
	@Query("""
			select r from ProfissionalEquipe r where (r.profissional.id = :idProfissional)
			""")
	Page<ProfissionalEquipe> findByIdProfissional(Long idProfissional, Pageable pageable);
}
