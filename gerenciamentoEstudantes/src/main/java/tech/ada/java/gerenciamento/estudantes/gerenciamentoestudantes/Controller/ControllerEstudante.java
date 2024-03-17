package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.EstudanteCadastroDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.*;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service.ServiceEstudante;
import java.util.List;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("/estudante")
public class ControllerEstudante {
    
    private final ServiceEstudante serviceEstudante;
    
    @Autowired
    public ControllerEstudante(ServiceEstudante serviceEstudante) {
        
        this.serviceEstudante = serviceEstudante;
    }
    
    @PostMapping
    public ResponseEntity<Estudante> cadastrarEstudante(@RequestBody @Valid EstudanteCadastroDTO request) {
        var novoEstudante = serviceEstudante.cadastrarEstudante(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEstudante);
    }

    @GetMapping
    public ResponseEntity<List<Estudante>> listarTodosEstudantes() {
        List<Estudante> estudantes = serviceEstudante.listarTodosEstudantes();
        return ResponseEntity.status(HttpStatus.OK).body(estudantes);
    }

    @GetMapping(params = "status")
    public ResponseEntity<List<Estudante>> filtrarStatusEstudante(@RequestParam Boolean status) {
     List<Estudante>   estudantes = serviceEstudante.filtrarStatusEstudante(status);
     return ResponseEntity.status(HttpStatus.OK).body(estudantes);
    }
    
    @GetMapping(value = "/{id}")
    public ResponseEntity<Optional<Estudante>> filtrarEstudanteId(@PathVariable Long id) {
        Optional<Estudante> estudante = serviceEstudante.filtrarEstudanteId(id);
        return ResponseEntity.status(HttpStatus.OK).body(estudante);
    }
    
    @GetMapping(params = {"nomeAluno"})
    public ResponseEntity<List<Estudante>> filtrarEstudanteNome(@RequestParam String nomeAluno) {
        System.out.println("entrou");
        List<Estudante> estudantes = serviceEstudante.filtrarEstudanteNome(nomeAluno);
    return ResponseEntity.status(HttpStatus.OK).body(estudantes);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Estudante> editarTudoEstudante
            (@PathVariable("id") Long id, @RequestBody EstudanteCadastroDTO atualizarEstudante) throws Exception {
      
        Estudante estudanteModificado = serviceEstudante.editarTudoEstudante(id, atualizarEstudante);
      
        return ResponseEntity.status(HttpStatus.OK).body(estudanteModificado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Estudante> atualizarEstudante (
            @PathVariable Long id,
            @RequestBody EstudanteRequest request) throws Exception{
        
        Estudante estudanteAtualizado = serviceEstudante.atualizarEstudante(id,request);
        return ResponseEntity.ok(estudanteAtualizado);
    }
}
   
