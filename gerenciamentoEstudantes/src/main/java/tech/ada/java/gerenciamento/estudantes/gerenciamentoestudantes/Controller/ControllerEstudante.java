package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.EstudanteCadastroDTO;
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


import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.List;
import java.util.Optional;


@RestController("estudante")
public class ControllerEstudante {
    
    private final RepositorioEstudante repositorioEstudante;
    private final ModelMapper modelMapper;
    
    @Autowired
    public ControllerEstudante(RepositorioEstudante repositorioEstudante, ModelMapper modelMapper) {
        this.repositorioEstudante = repositorioEstudante;
        this.modelMapper = modelMapper;
    }
    
    @PostMapping("/estudante")
    public ResponseEntity<Estudante> cadastrarEstudante(@RequestBody @Valid EstudanteCadastroDTO request) {
        
        Estudante estudante = modelMapper.map(request, Estudante.class);
        estudante.setDataDeCadastro(LocalDateTime.now(ZoneId.of("UTC")));
        Estudante novoEstudante = repositorioEstudante.save(estudante);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEstudante);
    }
    
    /**
     * ALL
     */
    @GetMapping("/estudante")
    public ResponseEntity<List<Estudante>> listarTodosEstudantes() {
        return ResponseEntity.status(HttpStatus.OK).body(repositorioEstudante.findAll());
    }
    
    
    @GetMapping(value = "/estudantes", params = "estaAtiva")
    public ResponseEntity<List<Estudante>> filtrarStatusTurma(@RequestParam Boolean estaAtiva) {
        List<Estudante> statusEstudantesFiltrados;
        
        if (estaAtiva) {
            statusEstudantesFiltrados = repositorioEstudante.findEstudantesByEstaAtivo(true);
        } else {
            statusEstudantesFiltrados = repositorioEstudante.findEstudantesByEstaAtivo(false);
        }
        
        if (!statusEstudantesFiltrados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(statusEstudantesFiltrados);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    
    /**
     * Método para filtrar um estudante pelo ID.
     */
    @GetMapping(value = "/estudante", params = {"id"})
    public ResponseEntity<Optional<Estudante>> filtrarEstudanteId(@RequestParam Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(repositorioEstudante.findById(id));
    }
    
    /**
     * Método para filtrar um estudante pelo NOME.
     */
    @GetMapping(value = "/estudante", params = {"nomeAluno"})
    public ResponseEntity<List<Estudante>> filtrarEstudanteNome(@RequestParam String nomeAluno) {
        return ResponseEntity.status(HttpStatus.OK).body(repositorioEstudante.findByNomeAluno(nomeAluno));
    }
    
    @PutMapping("/estudante/{id}") //AtualizandoTudo
    public ResponseEntity<Estudante> editarEstudante(@PathVariable("id") Long id, @RequestBody AtualizarEstudanteRequest atualizarEstudante) throws Exception {
        Optional<Estudante> optionalEstudante = repositorioEstudante.findById(id);
        
        //Primeiro checar cadastro existente
        if (!optionalEstudante.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        // Se existir vamos fazer o get(by ID)
        Estudante estudanteExistente = optionalEstudante.get();
        
        estudanteExistente.setEstaAtivo(atualizarEstudante.estaAtivo());
        estudanteExistente.setNomeAluno(atualizarEstudante.nomeAluno());
        estudanteExistente.setDataNascimento(atualizarEstudante.dataNascimento());
        estudanteExistente.setNomeResponsavel(atualizarEstudante.nomeResponsavel());
        estudanteExistente.setContatoResponsavel(atualizarEstudante.contatoResponsavel());
        estudanteExistente.setDataAtualizacaoCadastro(LocalDateTime.now(ZoneId.of("UTC")));
        
        Estudante estudanteSalvo = repositorioEstudante.save(estudanteExistente);
        
        return ResponseEntity.ok(estudanteSalvo);
    }

    @PatchMapping("/estudante/{id}") //Status Request e outros
    public ResponseEntity<Estudante> alteraStatus(
            @PathVariable Long id,
            @RequestBody EstudanteStatusRequest request) {
        try {
            Optional<Estudante> optionalEstudante = repositorioEstudante.findById(id);

            if (optionalEstudante.isPresent()) {
                Estudante estudanteItemModificado = optionalEstudante.get();

                if (request.estaAtivo() != null) {
                    estudanteItemModificado.setEstaAtivo(request.estaAtivo());
                }
                if (request.nomeAluno() != null && !request.nomeAluno().isEmpty()) {
                    estudanteItemModificado.setNomeAluno(request.nomeAluno());
                }
                if (request.nomeResponsavel() != null && !request.nomeResponsavel().isEmpty()) {
                    estudanteItemModificado.setNomeResponsavel(request.nomeResponsavel());
                }
                if (request.contatoResponsavel() != null && !request.contatoResponsavel().isEmpty()) {
                    estudanteItemModificado.setContatoResponsavel(request.contatoResponsavel());
                }

                Estudante estudanteSalvo = repositorioEstudante.save(estudanteItemModificado);
                return ResponseEntity.ok(estudanteSalvo);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
