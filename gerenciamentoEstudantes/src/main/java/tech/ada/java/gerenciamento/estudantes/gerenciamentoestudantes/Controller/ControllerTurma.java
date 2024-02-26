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
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.TurmaRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;

import java.util.List;
import java.util.Optional;

@RestController("/turma")
public class ControllerTurma {

    private final RepositorioTurma turmaRepositorio;
    private final ModelMapper modelMapper;

    @Autowired
    public ControllerTurma(RepositorioTurma turmaRepositorio, ModelMapper modelMapper) {
        this.turmaRepositorio = turmaRepositorio;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/turma")
    public ResponseEntity<Turma> cadastrarTurma(@RequestBody @Valid TurmaRequest request) throws BadRequest {
        Turma turmaConvertida = modelMapper.map(request, Turma.class);
        Turma novaTurma = turmaRepositorio.save(turmaConvertida);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaTurma);
    }


    @GetMapping("/turmas")
    public ResponseEntity<List<Turma>> buscarTurmas(){
        List<Turma> listaTurma = turmaRepositorio.findAll();

        if(listaTurma.isEmpty()){
            throw new ResourceNotFoundException("lista de turmas");
        }
        return ResponseEntity.status(HttpStatus.OK).body(listaTurma);

    }
    @GetMapping("/turma/{id}")
    public ResponseEntity<Turma> buscarTurmaEspecifica(@PathVariable Long id) throws Exception{
        Optional<Turma> optionalTurma = turmaRepositorio.findById(id);
        if(optionalTurma.isPresent()) {
            return ResponseEntity.ok(optionalTurma.get());
        } else {
            throw new ResourceNotFoundException("Turma", "ID", id);
        }
    }
    @PatchMapping("/turma/{id}")
    public ResponseEntity<Turma> alterarTurma(
            @PathVariable Long id,
            @RequestBody AlterarTurmaRequest request) throws Exception {
        Optional<Turma> optionalTurma = turmaRepositorio.findById(id);

        if(optionalTurma.isPresent()) {

            Turma turmaModificada = optionalTurma.get();

            if(request.estaAtiva()!= null) turmaModificada.setEstaAtiva(request.estaAtiva());
            if(request.nomeTurma() != null) turmaModificada.setNomeTurma(request.nomeTurma());
            Turma turmaSalva =  turmaRepositorio.save(turmaModificada);
            return ResponseEntity.ok(turmaSalva);
        } else {
            throw new ResourceNotFoundException("Turma", "ID", id);
        }


    }

    @PutMapping("/turma/{id}")
    public ResponseEntity<Turma> alteraTurmaCompleto(
            @PathVariable Long id,
            @RequestBody AlterarTurmaRequest request
    ) {
        Optional<Turma> optionalTurma = turmaRepositorio.findById(id);
        if(optionalTurma.isPresent()) {
            Turma turmaModificada = optionalTurma.get();
            turmaModificada.setEstaAtiva(request.estaAtiva());
            turmaModificada.setNomeTurma(request.nomeTurma());
            Turma turmaSalva = turmaRepositorio.save(turmaModificada);
            return ResponseEntity.ok(turmaSalva);
        } else {
            throw new ResourceNotFoundException("Turma", "ID", id);
        }
    }

    @GetMapping(value = "/turmas", params = "estaAtiva")
    public ResponseEntity<List<Turma>> filtrarStatusTurma(@RequestParam Boolean estaAtiva){
        List<Turma> statusTurmaFiltrada;

        if(estaAtiva){
            statusTurmaFiltrada = turmaRepositorio.findTurmaByEstaAtiva(true);
        }else{
            statusTurmaFiltrada = turmaRepositorio.findTurmaByEstaAtiva(false);
        }

        if(!statusTurmaFiltrada.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(statusTurmaFiltrada);
        } else {
            throw new ResourceNotFoundException("lista de turmas");
        }
    }
}
