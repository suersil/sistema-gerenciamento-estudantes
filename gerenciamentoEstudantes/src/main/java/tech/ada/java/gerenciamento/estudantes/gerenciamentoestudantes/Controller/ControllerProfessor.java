package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.AtualizarProfessorRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Professor;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.ProfessorDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.ProfessorRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioProfessor;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
public class ControllerProfessor {

    private final RepositorioProfessor repositorioProfessor;
    private final RepositorioTurma turmaRepositorio;
    private final ModelMapper modelMapper;

    @Autowired
    //injetando a dependencia via construtor com padrão inversao de dependencia
    public ControllerProfessor(RepositorioProfessor repositorioProfessor, ModelMapper modelMapper, RepositorioTurma turmaRepositorio) {
        this.repositorioProfessor = repositorioProfessor;
        this.turmaRepositorio = turmaRepositorio;
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
            Optional<Turma> optionalTurma;
            if (professorRequest.turma_id() != null) {
                optionalTurma = turmaRepositorio.findById(professorRequest.turma_id());
//                listaTurma
                if(optionalTurma.isPresent()) { professorModificado.AdicionarTurma(optionalTurma.get()); }
            }
            Professor professorSalvo = repositorioProfessor.save(professorModificado);

            return ResponseEntity.ok(professorSalvo);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/professor", params = {"nomeProfessor"})
    public ResponseEntity<List<String>> filtrarProfessorPorNome(@RequestParam String nomeProfessor) {
        List<Professor> professoresEncontrados = repositorioProfessor.findProfessorsByNomeProfessor(nomeProfessor);
        if (!professoresEncontrados.isEmpty()) {
            List<String> nomesDosProfessores = new ArrayList<>();
            for (Professor professor : professoresEncontrados) {
                nomesDosProfessores.add(professor.getNomeProfessor());
            }
            return ResponseEntity.status(HttpStatus.OK).body(nomesDosProfessores);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }




}
