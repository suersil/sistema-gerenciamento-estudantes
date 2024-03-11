package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.BadRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.AtualizarProfessorRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Professor;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.ProfessorDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.ProfessorRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioProfessor;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service.ServiceProfessor;

import java.util.*;


@RestController
public class ControllerProfessor {

    private final ServiceProfessor serviceProfessor;

    @Autowired
    public ControllerProfessor(ServiceProfessor serviceProfessor) {;
        this.serviceProfessor = serviceProfessor;
    }

    @PostMapping("/professor")
    public ResponseEntity<Professor> cadastrarProfessor(@RequestBody @Valid ProfessorDTO professorRequest) throws BadRequest {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serviceProfessor.cadastrarProfessor(professorRequest));

    }

    @GetMapping("/professores")
    public ResponseEntity<List<Professor>> listarTodos() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(serviceProfessor.listarTodos());
    }

    @PutMapping("/professor/{id}")
    public ResponseEntity<Professor> editarTudoProfessor(
            @PathVariable("id") Long id,
            @RequestBody AtualizarProfessorRequest atualizarProfessorRequest) throws Exception {
        return ResponseEntity.ok
                (serviceProfessor.atualizarProfessor(id, atualizarProfessorRequest));

    }

    @PatchMapping("/professor/{id}")
    public ResponseEntity<Professor> atualizarProfessor(
            @PathVariable("id") Long id,
            @RequestBody ProfessorRequest professorRequest) throws Exception {

        return ResponseEntity.ok
                (serviceProfessor.editarParcialProfessor(id, professorRequest));

    }

    /** Método para filtrar um professor pelo NOME.*/
     @GetMapping(value = "/professor", params = {"nomeProfessor"})
        public ResponseEntity<List<Professor>> filtrarProfessorPorNome(@RequestParam String nomeProfessor) {

         return ResponseEntity.status(HttpStatus.OK).body(serviceProfessor.filtrarProfessorPorNome(nomeProfessor));

     }




}
