package br.com.magnasistemas.construcaocivil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.magnasistemas.construcaocivil.entity.Equipe;

public interface EquipeRepository extends JpaRepository<Equipe,Long>{

	Page<Equipe> findAllByStatusTrue(Pageable paginacao);

}
