package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName;
    private String campoNome;
    private Object campoValor;

    public ResourceNotFoundException(String resourceName, String campoNome, String campoValor){
        super(String.format("%s não foi encontrado com: %s= '%s", resourceName, campoNome, campoValor));
        this.resourceName = resourceName;
        this.campoNome = campoNome;
        this.campoValor = campoValor;
    }

    public ResourceNotFoundException(String resourceName){
        super(String.format("Não há registros de %s no sistema.", resourceName));
        this.resourceName = resourceName;
    }
}
