package br.com.magnasistemas.construcaocivil.exception.tratamento;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.magnasistemas.construcaocivil.exception.EntidadeDesativadaException;

@RestControllerAdvice
public class TratarErrosEntidadeDesativada {
    @ExceptionHandler(EntidadeDesativadaException.class) 
    public ResponseEntity<List<DadosEntidadeDesativada>> tratarEntidadeDesativada(EntidadeDesativadaException ex) {
    	DadosEntidadeDesativada dadosEntidadeDesativada = new DadosEntidadeDesativada(ex.getMessage());
        return ResponseEntity.badRequest().body(List.of(dadosEntidadeDesativada));
    }

    public record DadosEntidadeDesativada(String mensagem) {
        public DadosEntidadeDesativada(FieldError erro) {
            this(erro.getDefaultMessage());
        }
    }

}
