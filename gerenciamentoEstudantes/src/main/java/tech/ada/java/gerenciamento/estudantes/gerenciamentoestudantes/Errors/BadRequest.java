package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors;

public class BadRequest extends RuntimeException{
    public BadRequest(String mensagem){
        super(mensagem);
    }
}
