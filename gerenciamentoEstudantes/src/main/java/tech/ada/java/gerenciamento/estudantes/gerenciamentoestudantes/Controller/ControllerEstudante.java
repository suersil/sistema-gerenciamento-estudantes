package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.EstudanteCadastroDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.*;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioEstudante;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Estudante;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service.ServiceEstudante;
import java.util.List;
import java.util.Optional;
@Validated
@RestController("/estudante")
public class ControllerEstudante {

    private final ServiceEstudante serviceEstudante;
    
    
    @Autowired
    public ControllerEstudante(ServiceEstudante serviceEstudante) {
        this.serviceEstudante = serviceEstudante;
        
    }
    
    @PostMapping("/estudante")
    public ResponseEntity<Estudante> cadastrarEstudante(@RequestBody @Valid EstudanteCadastroDTO request) {
        
        return serviceEstudante.cadastrarEstudante(request);
    
        
       /*  Estudante estudante = modelMapper.map(request, Estudante.class);
        if (repositorioEstudante.existsByEstudante(estudante)) {
            throw new Exception("Já existe um estudante com os mesmos detalhes.");
        }
        
        estudante.setEstaAtivo(request.estaAtivo());
        Estudante novoEstudante = repositorioEstudante.save(estudante);
        novoEstudante.setDataAtualizacao(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEstudante); */
    }
    
    
    @GetMapping("/estudantes")    
    public ResponseEntity<List<Estudante>> listarTodosEstudantes() {
       
        return serviceEstudante.listarTodosEstudantes();
    }
    
    
    @GetMapping(value = "/estudantes", params = "status")
    public ResponseEntity<List<Estudante>> filtrarStatusTurma(@RequestParam Boolean status) {
        
        return serviceEstudante.filtrarStatusEstudante(status);
    }
    
    
    @GetMapping(value = "/estudante/{id}")
    public ResponseEntity<Optional<Estudante>> filtrarEstudanteId(@PathVariable Long id) {
        return serviceEstudante.filtrarEstudanteId(id);
    }
    
    
    @GetMapping(value = "/estudante", params = {"nomeAluno"})
    public ResponseEntity<List<Estudante>> filtrarEstudanteNome(@RequestParam String nomeAluno) {
        
        return serviceEstudante.filtrarEstudanteNome(nomeAluno);
    }
    
    
    @PutMapping("/estudante/{id}")
    public ResponseEntity<Estudante> editarTudoEstudante
            (@PathVariable("id") Long id, @RequestBody EstudanteCadastroDTO atualizarEstudante) throws Exception {
      /*   Optional<Estudante> optionalEstudante = repositorioEstudante.findById(id);
        
        if (optionalEstudante.isEmpty()) {
            throw new ResourceNotFoundException("Estudante", "ID", id);
        }
        
        
        Estudante estudanteExistente = optionalEstudante.get();
        
        estudanteExistente.setEstaAtivo(atualizarEstudante.estaAtivo());
        estudanteExistente.setNomeAluno(atualizarEstudante.nomeAluno());
        estudanteExistente.setDataNascimento(atualizarEstudante.dataNascimento());
        estudanteExistente.setNomeResponsavel(atualizarEstudante.nomeResponsavel());
        estudanteExistente.setContatoResponsavel(atualizarEstudante.contatoResponsavel());
        estudanteExistente.setDataDeCadastro(estudanteExistente.getDataDeCadastro());
        
        Estudante estudanteSalvo = repositorioEstudante.save(estudanteExistente); */
        
        return serviceEstudante.editarTudoEstudante(id, atualizarEstudante);
    }
    
    
    @PatchMapping("/estudante/{id}")
    public ResponseEntity<Estudante> atualizarEstudante(
            @PathVariable Long id,
            @RequestBody EstudanteRequest request) throws Exception {
        
      /*   Optional<Estudante> optionalEstudante = repositorioEstudante.findById(id);
        
        if (optionalEstudante.isPresent()) {
            
            Estudante estudanteItemModificado = optionalEstudante.get();
            
            if (request.nomeAluno() != null) estudanteItemModificado.setNomeAluno(request.nomeAluno());
            if (request.nomeResponsavel() != null)
                estudanteItemModificado.setNomeResponsavel(request.nomeResponsavel());
            if (request.contatoResponsavel() != null)
                estudanteItemModificado.setContatoResponsavel(request.contatoResponsavel());
            
            Optional<Turma> optionalTurma;
            if (request.turma_id() != null) {
                optionalTurma = turmaRepositorio.findById(request.turma_id());
                if (optionalTurma.isPresent()) {
                    estudanteItemModificado.setTurma(optionalTurma.get());
                } else {
                    throw new ResourceNotFoundException("turma não encontrada");
                }
            }
            
            Estudante estudanteSalvo = repositorioEstudante.save(estudanteItemModificado); */
            return serviceEstudante.atualizarEstudante(id, request);
        }
   
}