package br.com.magnasistemas.construcaocivil.repository.dominio;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.magnasistemas.construcaocivil.entity.dominio.Cidade;

public interface CidadesRepository extends JpaRepository <Cidade,Long> {

}
