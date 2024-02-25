package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequest extends RuntimeException{
    public BadRequest(String mensagem){
        super(mensagem);
    }
}
