package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.BadRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.ResourceNotFoundException;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.AlterarTurmaRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.TurmaDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service.ServiceTurma;

import java.util.List;
import java.util.Optional;

@RestController("/turma")
public class ControllerTurma {

    private final ServiceTurma serviceTurma;

    @Autowired
    public ControllerTurma(ServiceTurma serviceTurma) {
        this.serviceTurma = serviceTurma;
    }


    @PostMapping("/turma")
    public ResponseEntity<Turma> cadastrarTurma(@RequestBody @Valid TurmaDTO turmaRequest) throws BadRequest {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(serviceTurma.cadastrarTurma(turmaRequest));
    }

    @GetMapping("/turmas")
    public ResponseEntity<List<Turma>> buscarTurmas(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(serviceTurma.buscarTurmas());

    }

    @GetMapping("/turma/{id}")
    public ResponseEntity<Turma> buscarTurmaEspecifica(@PathVariable Long id) throws Exception{
        return ResponseEntity.ok(serviceTurma.buscarTurmaEspecifica(id));
    }

    @PatchMapping("/turma/{id}")
    public ResponseEntity<Turma> alterarTurma(
            @PathVariable Long id,
            @RequestBody AlterarTurmaRequest turmaRequest) throws Exception {

            return ResponseEntity.ok(serviceTurma.alterarTurma(id, turmaRequest));
    }

    @PutMapping("/turma/{id}")
    public ResponseEntity<Turma> alteraTurmaCompleto(
            @PathVariable Long id,
            @RequestBody AlterarTurmaRequest turmaRequest
    ) {

            return ResponseEntity.ok(serviceTurma.alteraTurmaCompleto(id, turmaRequest));
    }

    @GetMapping(value = "/turmas", params = "estaAtiva")
    public ResponseEntity<List<Turma>> filtrarStatusTurma(@RequestParam Boolean estaAtiva){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(serviceTurma.findTurmaByEstaAtiva(estaAtiva));

    }
}
