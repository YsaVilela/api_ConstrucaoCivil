package br.com.magnasistemas.construcaocivil.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.magnasistemas.construcaocivil.entity.Equipe;
import br.com.magnasistemas.construcaocivil.entity.Profissional;

public interface ProfissionalRepository extends JpaRepository<Profissional,Long>{

	Page<Profissional> findAllByStatusTrue(Pageable paginacao);

}
