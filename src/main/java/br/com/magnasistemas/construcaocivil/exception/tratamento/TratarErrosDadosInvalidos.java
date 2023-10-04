package br.com.magnasistemas.construcaocivil.exception.tratamento;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.magnasistemas.construcaocivil.exception.DadosInvalidosException;

@RestControllerAdvice
public class TratarErrosDadosInvalidos {
    @ExceptionHandler(DadosInvalidosException.class) 
    public ResponseEntity<List<DadosInvalidos>> tratarDadosInvalidos(DadosInvalidosException ex) {
        DadosInvalidos dadosInvalidos = new DadosInvalidos(ex.getMessage());
        return ResponseEntity.badRequest().body(List.of(dadosInvalidos));
    }

    public record DadosInvalidos(String mensagem) {
        public DadosInvalidos(FieldError erro) {
            this(erro.getDefaultMessage());
        }
    }

}
