package br.com.magnasistemas.construcaocivil.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.magnasistemas.construcaocivil.entity.Construtora;

public interface ConstrutoraRepository extends JpaRepository<Construtora,Long> {

	Page<Construtora> findAllByStatusTrue(Pageable paginacao);
}
