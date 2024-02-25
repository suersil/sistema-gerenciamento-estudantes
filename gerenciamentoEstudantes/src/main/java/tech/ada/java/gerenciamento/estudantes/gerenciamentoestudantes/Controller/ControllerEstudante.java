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
    throws BadRequest {

        Estudante estudante = modelMapper.map(request, Estudante.class);

        estudante.setEstaAtivo(request.estaAtivo());
        Estudante novoEstudante = repositorioEstudante.save(estudante);
        novoEstudante.setDataAtualizacao(null);  //Devolvendo Null ao criar cadastro
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEstudante);
    }

    /*** Get ALL */
    @GetMapping("/estudante")
    public ResponseEntity<List<Estudante>> listarTodosEstudantes() {
        List<Estudante> listaEstudantes = repositorioEstudante.findAll();

        if(listaEstudantes.isEmpty()){
            throw new ResourceNotFoundException("lista de estudantes");
        }
        return ResponseEntity.status(HttpStatus.OK).body(repositorioEstudante.findAll());
    }

    /*** Método para filtrar um estudante pelo STATUS*/
    @GetMapping(value = "/estudante", params = "estaAtivo")
    public ResponseEntity<List<Estudante>> filtrarStatusTurma(@RequestParam Boolean estaAtivo) {
        List<Estudante> statusEstudantesFiltrados;

        if (estaAtivo) {
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

    /*** Método para filtrar um estudante pelo ID.*/
    @GetMapping(value = "/estudante/{id}")
    public ResponseEntity<Optional<Estudante>> filtrarEstudanteId(@PathVariable Long id){

        Optional<Estudante> estudantePorID = repositorioEstudante.findById(id);

        if(estudantePorID.isEmpty()){
            throw new ResourceNotFoundException("Estudante", "ID", id);
        }


        return ResponseEntity.status(HttpStatus.OK).body(repositorioEstudante.findById(id));
    }

    /*** Método para filtrar um estudante pelo NOME.*/
    @GetMapping(value = "/estudante", params = {"nomeAluno"})
    public ResponseEntity<List<Estudante>> filtrarEstudanteNome(@RequestParam String nomeAluno) {
        List<Estudante> estudantePorNome = repositorioEstudante.findByNomeAlunoQuery(nomeAluno);

        if(estudantePorNome.isEmpty()){
            throw new ResourceNotFoundException("estudante", "nome ", nomeAluno);
        }
        return ResponseEntity.status(HttpStatus.OK).body(repositorioEstudante.findByNomeAlunoQuery(nomeAluno));
    }

    /*** Método para Atualizar TUDO de um estudante.*/
    @PutMapping("/estudante/{id}")
    public ResponseEntity<Estudante> editarTudoEstudante
    (@PathVariable("id") Long id, @RequestBody AtualizarEstudanteRequest atualizarEstudante) throws Exception {
        Optional<Estudante> optionalEstudante = repositorioEstudante.findById(id);

        //Primeiro checar cadastro existente
        if (optionalEstudante.isEmpty()) {
            throw new ResourceNotFoundException("Estudante", "ID", id);
        }

        // Se existir vamos fazer o get(by ID)
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

    /*** Método para Atualizar STATUS e outras info. de um estudante.*/
    @PatchMapping("/estudante/{id}")
    public ResponseEntity<Estudante> atualizarEstudante(
            @PathVariable Long id,
            @RequestBody EstudanteRequest request) throws Exception {
        // Buscar pelo metodo findById que retorna um Optional<Estudante>
        Optional<Estudante> optionalEstudante = repositorioEstudante.findById(id);

        // Verificamos se existe valor dentro do Optional
        if (optionalEstudante.isPresent()) {
            // Se existir - fazer o get()
            Estudante estudanteItemModificado = optionalEstudante.get();
            // verificamos se um das tres variaveis que esperamos foi passada para ser atualizada
            if (request.estaAtivo()) estudanteItemModificado.setEstaAtivo(request.estaAtivo());
            if (request.nomeAluno() != null) estudanteItemModificado.setNomeAluno(request.nomeAluno());
            if (request.nomeResponsavel() != null)
                estudanteItemModificado.setNomeResponsavel(request.nomeResponsavel());
            if (request.contatoResponsavel() != null)
                estudanteItemModificado.setContatoResponsavel(request.contatoResponsavel());
//            if (request.turma_id() != null)
            Optional<Turma> optionalTurma;
            if (request.turma_id() != null) {
                optionalTurma = turmaRepositorio.findById(request.turma_id());
                if(optionalTurma.isPresent()) { estudanteItemModificado.setTurma(optionalTurma.get()); }
                else{
                    throw new ResourceNotFoundException("turma nao encontrada");
                }
            }
            //Depois de atualizar - Salvando
            Estudante estudanteSalvo = repositorioEstudante.save(estudanteItemModificado);
            return ResponseEntity.ok(estudanteSalvo);
        }
        //Retornar o codigo 404 - se nao encontrado
        throw new ResourceNotFoundException("Estudante", "ID", id);
    }
}
