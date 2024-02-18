package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import lombok.Getter;

@Getter
public class ProfessorRequest {

    private String nomeProfessor;
    private String email;
    private String disciplinaLecionada;

    //Transformar a request em entidade
    public Professor paraEntidade(){
        return new Professor(nomeProfessor, email, disciplinaLecionada);
    }
}
