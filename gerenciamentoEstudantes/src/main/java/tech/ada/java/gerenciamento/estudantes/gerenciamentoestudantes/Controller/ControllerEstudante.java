package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.EstudanteCadastroDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.BadRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.ResourceNotFoundException;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.*;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioEstudante;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.EstudanteCadastroDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Estudante;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioEstudante;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;


import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.List;
import java.util.Optional;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;
@Validated
@RestController("/estudante")
public class ControllerEstudante {
    private final RepositorioEstudante repositorioEstudante;
    private final RepositorioTurma turmaRepositorio;
    
    private final ModelMapper modelMapper;
    
    @Autowired
    public ControllerEstudante(RepositorioEstudante repositorioEstudante, RepositorioTurma turmaRepositorio, ModelMapper modelMapper) {
        this.repositorioEstudante = repositorioEstudante;
        this.turmaRepositorio = turmaRepositorio;
        this.modelMapper = modelMapper;
    }
    
    @PostMapping("/estudante")
    public ResponseEntity<Estudante> cadastrarEstudante(@RequestBody @Valid EstudanteCadastroDTO request)
            throws Exception {
        
        Estudante estudante = modelMapper.map(request, Estudante.class);
        if (repositorioEstudante.existsByEstudante(estudante)) {
            throw new Exception("Já existe um estudante com os mesmos detalhes.");
        }
        
        estudante.setEstaAtivo(request.estaAtivo());
        Estudante novoEstudante = repositorioEstudante.save(estudante);
        novoEstudante.setDataAtualizacao(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEstudante);
    }
    
    
    @GetMapping("/estudantes")
    
    public ResponseEntity<List<Estudante>> listarTodosEstudantes() {
        List<Estudante> listaEstudantes = repositorioEstudante.findAll();
        
        if (listaEstudantes.isEmpty()) {
            throw new ResourceNotFoundException("lista de estudantes");
        }
        return ResponseEntity.status(HttpStatus.OK).body(repositorioEstudante.findAll());
    }
    
    
    @GetMapping(value = "/estudantes", params = "status")
    public ResponseEntity<List<Estudante>> filtrarStatusTurma(@RequestParam Boolean status) {
        List<Estudante> statusEstudantesFiltrados;
        
        if (status) {
            statusEstudantesFiltrados = repositorioEstudante.findEstudantesByEstaAtivo(true);
        } else {
            statusEstudantesFiltrados = repositorioEstudante.findEstudantesByEstaAtivo(false);
        }
        
        if (!statusEstudantesFiltrados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(statusEstudantesFiltrados);
        } else {
            throw new ResourceNotFoundException("lista de estudantes");
        }
    }
    
    
    @GetMapping(value = "/estudante/{id}")
    public ResponseEntity<Optional<Estudante>> filtrarEstudanteId(@PathVariable Long id) {
        
        Optional<Estudante> estudantePorID = repositorioEstudante.findById(id);
        
        if (estudantePorID.isEmpty()) {
            throw new ResourceNotFoundException("Estudante", "ID", id);
        }
        
        
        return ResponseEntity.status(HttpStatus.OK).body(repositorioEstudante.findById(id));
    }
    
    
    @GetMapping(value = "/estudante", params = {"nomeAluno"})
    public ResponseEntity<List<Estudante>> filtrarEstudanteNome(@RequestParam String nomeAluno) {
        List<Estudante> estudantePorNome = repositorioEstudante.findByNomeAlunoQuery(nomeAluno);
        
        if (estudantePorNome.isEmpty()) {
            throw new ResourceNotFoundException("estudante", "nome ", nomeAluno);
        }
        return ResponseEntity.status(HttpStatus.OK).body(repositorioEstudante.findByNomeAlunoQuery(nomeAluno));
    }
    
    
    @PutMapping("/estudante/{id}")
    public ResponseEntity<Estudante> editarTudoEstudante
            (@PathVariable("id") Long id, @RequestBody AtualizarEstudanteRequest atualizarEstudante) throws Exception {
        Optional<Estudante> optionalEstudante = repositorioEstudante.findById(id);
        
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
        
        Estudante estudanteSalvo = repositorioEstudante.save(estudanteExistente);
        
        return ResponseEntity.ok(estudanteSalvo);
    }
    
    
    @PatchMapping("/estudante/{id}")
    public ResponseEntity<Estudante> atualizarEstudante(
            @PathVariable Long id,
            @RequestBody EstudanteRequest request) throws Exception {
        
        Optional<Estudante> optionalEstudante = repositorioEstudante.findById(id);
        
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
            
            Estudante estudanteSalvo = repositorioEstudante.save(estudanteItemModificado);
            return ResponseEntity.ok(estudanteSalvo);
        }
        
        // Retornar o código 404 se não encontrado
        throw new ResourceNotFoundException("Estudante", "ID", id);
    }
}