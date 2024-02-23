package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Turma> cadastrarTurma(@RequestBody @Valid TurmaRequest request) {
        Turma turmaConvertida = modelMapper.map(request, Turma.class);
        Turma novaTurma = turmaRepositorio.save(turmaConvertida);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaTurma);
    }

    @GetMapping("/turma")
    public List<Turma> buscarTurmas(){
        return turmaRepositorio.findAll();
    }
    @GetMapping("/turma/{id}")
    public ResponseEntity<Turma> buscarTurmaEspecifica(@PathVariable Long id) throws Exception{
        Optional<Turma> optionalTurma = turmaRepositorio.findById(id);
        if(optionalTurma.isPresent()) {
            return ResponseEntity.ok(optionalTurma.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping("/turma/{id}")
    public ResponseEntity<Turma> alterarTurma(
            @PathVariable Long id,
            @RequestBody AlterarTurmaRequest request) throws Exception {
        Optional<Turma> optionalTurma = turmaRepositorio.findById(id);
        if(optionalTurma.isPresent()) {
            Turma turmaModificada = optionalTurma.get();
            if(request.turmaAtiva() != null) turmaModificada.setEstaAtiva(request.turmaAtiva());
            if(request.nomeTurma() != null) turmaModificada.setNomeTurma(request.nomeTurma());
            Turma turmaSalva =  turmaRepositorio.save(turmaModificada);
            return ResponseEntity.ok(turmaSalva);
        } else {
            return ResponseEntity.notFound().build();
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
            turmaModificada.setEstaAtiva(request.turmaAtiva());
            turmaModificada.setNomeTurma(request.nomeTurma());
            Turma turmaSalva = turmaRepositorio.save(turmaModificada);
            return ResponseEntity.ok(turmaSalva);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/turma", params = "estaAtiva")
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
