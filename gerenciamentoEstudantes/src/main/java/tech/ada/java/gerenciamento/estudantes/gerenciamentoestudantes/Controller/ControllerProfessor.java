package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Professor;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.ProfessorRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioProfessor;

// TODO: 18/02/24 cadastrarProfessor()
// TODO: 18/02/24 listarTodos()
// TODO: 18/02/24 editarProfessor
// TODO: 18/02/24 checar modelMapper - implementacao do Estudante

@RestController
public class ControllerProfessor {

    private final RepositorioProfessor repositorioProfessor;
    private final ModelMapper modelMapper;

    @Autowired
    //injetando a dependencia via construtor com padrão inversao de dependencia
    public ControllerProfessor(RepositorioProfessor repositorioProfessor, ModelMapper modelMapper) {
        this.repositorioProfessor = repositorioProfessor;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/professor")
    public Professor cadastrarProfessor(@RequestBody ProfessorRequest professorRequest) {

        //converter a request que chegou no body para uma entidade Professor
        Professor professorConvertido = modelMapper.map(professorRequest, Professor.class);

        Professor novoProfessor = repositorioProfessor.save(professorConvertido);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoProfessor).getBody();

    }
}
