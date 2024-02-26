package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.BadRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.ResourceNotFoundException;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.AtualizarProfessorRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Professor;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.ProfessorDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.ProfessorRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioProfessor;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;

import java.util.*;


@RestController
public class ControllerProfessor {

    private final RepositorioProfessor repositorioProfessor;
    private final RepositorioTurma turmaRepositorio;
    private final ModelMapper modelMapper;

    @Autowired
   
    public ControllerProfessor(RepositorioProfessor repositorioProfessor, ModelMapper modelMapper, RepositorioTurma turmaRepositorio) {
        this.repositorioProfessor = repositorioProfessor;
        this.turmaRepositorio = turmaRepositorio;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/professor")
    public ResponseEntity<Professor> cadastrarProfessor(@RequestBody @Valid ProfessorDTO professorRequest) throws BadRequest {

       
        Professor professorConvertido = modelMapper.map(professorRequest, Professor.class);
        Professor novoProfessor = repositorioProfessor.save(professorConvertido);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProfessor);
    }

    @GetMapping("/professores")
    public ResponseEntity<List<Professor>> listarTodos() {
        List<Professor> listarProfessores = repositorioProfessor.findAll();
        if(listarProfessores.isEmpty()){
            throw new ResourceNotFoundException("lista de professores");
        }
        return ResponseEntity.status(HttpStatus.OK).body(listarProfessores);
    }

    @PutMapping("/professor/{id}")
    public ResponseEntity<Professor> editarTudoProfessor(
            @PathVariable("id") Long id,
            @RequestBody AtualizarProfessorRequest atualizarProfessorRequest) throws Exception {
        Optional<Professor> optionalProfessor = repositorioProfessor.findById(id);

        if(optionalProfessor.isEmpty()){
            throw new ResourceNotFoundException("Professor", "ID", id);
        }

        Professor professorExistente = optionalProfessor.get();

        professorExistente.setNomeProfessor(atualizarProfessorRequest.nomeProfessor());
        professorExistente.setEmail(atualizarProfessorRequest.email());
        professorExistente.setDisciplinaLecionada(atualizarProfessorRequest.disciplinaLecionada());
        professorExistente.setEstaAtivo(atualizarProfessorRequest.estaAtivo());

        Professor professorSalvo = repositorioProfessor.save(professorExistente);

        return ResponseEntity.ok(professorSalvo);
    }

    @PatchMapping("/professor/{id}")
    public ResponseEntity<Professor> atualizarProfessor(
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
            Optional<Turma> optionalTurma;
            if (professorRequest.turma_id() != null) {
                optionalTurma = turmaRepositorio.findById(professorRequest.turma_id());
                if(optionalTurma.isPresent()) {
                    professorModificado.AdicionarTurma(optionalTurma.get());
                } else{
                    throw new ResourceNotFoundException("turma registrada");
                }
            }
            Professor professorSalvo = repositorioProfessor.save(professorModificado);
            return ResponseEntity.ok(professorSalvo);
        }
        else {
            throw new ResourceNotFoundException("professor encontrado");
        }
    }

    /** Método para filtrar um professor pelo NOME.*/
     @GetMapping(value = "/professor", params = {"nomeProfessor"})
        public ResponseEntity<List<Professor>> filtrarProfessorPorNome(@RequestParam String nomeProfessor) {
        return ResponseEntity.status(HttpStatus.OK).body(repositorioProfessor.findProfessorsByNomeProfessor(nomeProfessor));
     }




}
