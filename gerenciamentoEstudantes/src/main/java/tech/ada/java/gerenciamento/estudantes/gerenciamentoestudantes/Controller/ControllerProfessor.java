package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.AtualizarProfessorRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Professor;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.ProfessorRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioProfessor;

import java.util.List;
import java.util.Optional;


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
    public ResponseEntity<Professor> cadastrarProfessor(@RequestBody ProfessorDTO professorRequest) {

        //converter a request que chegou no body para uma entidade Professor
        Professor professorConvertido = modelMapper.map(professorRequest, Professor.class);

        Professor novoProfessor = repositorioProfessor.save(professorConvertido);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoProfessor);

    }

    @GetMapping("/professor")
    public ResponseEntity<List<Professor>> listarTodos() {
        List<Professor> listarProfessores = repositorioProfessor.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(listarProfessores);
    }

    @PutMapping("/professor/{id}")
    public ResponseEntity<Professor> editarProfessor(
            @PathVariable("id") Long id,
            @RequestBody AtualizarProfessorRequest atualizarProfessorRequest) throws Exception {
        Optional<Professor> optionalProfessor = repositorioProfessor.findById(id);

        if (optionalProfessor.isPresent()) {
            Professor professorExistente = optionalProfessor.get();

            professorExistente.setNomeProfessor(atualizarProfessorRequest.nomeProfessor());
            professorExistente.setEmail(atualizarProfessorRequest.email());
            professorExistente.setDisciplinaLecionada(atualizarProfessorRequest.disciplinaLecionada());
            professorExistente.setEstaAtivo(atualizarProfessorRequest.estaAtivo());

            Professor professorSalvo = repositorioProfessor.save(professorExistente);

            return ResponseEntity.ok(professorSalvo);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/professor/{id}")
    public ResponseEntity<Professor> alterarProfessor(
            @PathVariable("id") Long id,
            @RequestBody ProfessorRequest professorRequest) throws Exception {
        Optional<Professor> optionalProfessor = repositorioProfessor.findById(id);

        if (optionalProfessor.isPresent()) {
            Professor professorModificado = optionalProfessor.get();

            // verificamos se um das tres variaveis que esperamos foi passada para ser atualizada
            if (professorRequest.nomeProfessor() != null) professorModificado.setNomeProfessor(professorRequest.nomeProfessor());
            if (professorRequest.email() != null) professorModificado.setEmail(professorRequest.email());
            if (professorRequest.disciplinaLecionada() != null) professorModificado.setDisciplinaLecionada(professorRequest.disciplinaLecionada());
            if (professorRequest.estaAtivo() != null) professorModificado.setEstaAtivo(professorRequest.estaAtivo());

            Professor professorSalvo = repositorioProfessor.save(professorModificado);

            return ResponseEntity.ok(professorSalvo);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/professor", params = {"nomeProfessor"})
    public ResponseEntity<List<Professor>> filtrarProfessorPorNome(@RequestParam String nomeProfessor) throws Exception {
        List<Professor> professoresEncontrados = repositorioProfessor.findProfessorsByNomeProfessor(nomeProfessor);
      
        return ResponseEntity.status(HttpStatus.OK).body(professoresEncontrados);
       
        }
}


