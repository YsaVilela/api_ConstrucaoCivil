package br.com.magnasistemas.construcaocivil.exception.tratamento;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.magnasistemas.construcaocivil.exception.BuscarException;

@RestControllerAdvice
public class TratarErrosBuscaException {
    @ExceptionHandler(BuscarException.class) 
    public ResponseEntity<List<DadosErroBusca>> tratarBuscar(BuscarException ex) {
        DadosErroBusca dadosErroBusca = new DadosErroBusca(ex.getMessage());
        return ResponseEntity.badRequest().body(List.of(dadosErroBusca));
    }

    public record DadosErroBusca(String mensagem) {
        public DadosErroBusca(FieldError erro) {
            this(erro.getDefaultMessage());
        }
    }
}