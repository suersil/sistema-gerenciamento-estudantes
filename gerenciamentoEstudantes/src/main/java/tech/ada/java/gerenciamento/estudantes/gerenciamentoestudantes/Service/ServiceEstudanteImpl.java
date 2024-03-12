package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service;

import java.util.List;
import java.util.Optional;

import org.aspectj.apache.bcel.generic.RET;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.model.Model;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.EstudanteCadastroDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.ResourceNotFoundException;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Estudante;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.EstudanteRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioEstudante;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;


@Service 
public class ServiceEstudanteImpl implements ServiceEstudante {

    @Autowired
    RepositorioEstudante repositorioEstudante;

    @Autowired
    RepositorioTurma turmaRepositorio;

    ModelMapper modelMapper = new ModelMapper();

    public ServiceEstudanteImpl(RepositorioEstudante repositorioEstudante, ModelMapper modelMapper) { 
        this.repositorioEstudante = repositorioEstudante;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<Estudante> cadastrarEstudante(EstudanteCadastroDTO request) {
        Estudante estudante = modelMapper.map(request, Estudante.class);
      /*   estudante.setNomeAluno(request.getNomeAluno());
        estudante.setNomeResponsavel(request.getNomeResponsavel());
        estudante.setDataNascimento(request.getDataNascimento());
        estudante.setContatoResponsavel(request.getContatoResponsavel());
        estudante.setEstaAtivo(true); */

        var novoEstudante = repositorioEstudante.save(estudante);

        return ResponseEntity.status(HttpStatus.CREATED).body(novoEstudante);

    }

    @Override
    public ResponseEntity<List<Estudante>> listarTodosEstudantes() {
        List<Estudante> listaEstudantes = repositorioEstudante.findAll();
        
        if (listaEstudantes.isEmpty()) {
            throw new ResourceNotFoundException("lista de estudantes");
        }
        return ResponseEntity.status(HttpStatus.OK).body(repositorioEstudante.findAll());
    }

    @Override
    public ResponseEntity<List<Estudante>> filtrarStatusEstudante(Boolean status){
        List<Estudante> statusEstudantesFiltrados;
        
        if (Boolean.TRUE.equals(status)) {
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

    @Override
    public ResponseEntity<Optional<Estudante>> filtrarEstudanteId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("O id deve ser enviado");
        }
       
        Optional<Estudante> estudante = repositorioEstudante.findById(id);
        
        if (estudante.isEmpty()) {
            throw new ResourceNotFoundException("estudante", "id", id);
        }
        return ResponseEntity.status(HttpStatus.OK).body(repositorioEstudante.findById(id));
    }
   
    @Override
    public ResponseEntity<List<Estudante>> filtrarEstudanteNome(String nomeAluno) {
        List<Estudante> estudantePorNome = repositorioEstudante.findByNomeAlunoQuery(nomeAluno);
        
        if (estudantePorNome.isEmpty()) {
            throw new ResourceNotFoundException("estudante", "nome ", nomeAluno);
        }
        return ResponseEntity.status(HttpStatus.OK).body(repositorioEstudante.findByNomeAlunoQuery(nomeAluno));
    }

    @Override
    public ResponseEntity<Estudante> editarTudoEstudante(Long id, EstudanteCadastroDTO request) {
        Optional<Estudante> estudante = repositorioEstudante.findById(id);
        
        if (estudante.isEmpty()) {
            throw new ResourceNotFoundException("estudante", "id", id);
        }
       
        Estudante estudanteAtualizado = estudante.get();
        estudanteAtualizado.setNomeAluno(request.getNomeAluno());
        estudanteAtualizado.setNomeResponsavel(request.getNomeResponsavel());
        estudanteAtualizado.setDataNascimento(request.getDataNascimento());
        estudanteAtualizado.setContatoResponsavel(request.getContatoResponsavel());
        estudanteAtualizado.setEstaAtivo(request.getEstaAtivo());

        Estudante estudanteSalvo = repositorioEstudante.save(estudanteAtualizado);

        return ResponseEntity.status(HttpStatus.OK).body(estudanteSalvo);
    }
        

    @Override
    public ResponseEntity<Estudante> atualizarEstudante(Long id, EstudanteRequest request) {
        Optional<Estudante> optionalEstudante = repositorioEstudante.findById(id);

        if (optionalEstudante.isPresent()) {
            Estudante estudanteItemModificado = optionalEstudante.get();

            if (request.nomeAluno() != null) {
                estudanteItemModificado.setNomeAluno(request.nomeAluno());
            }
            if (request.nomeResponsavel() != null) {
                estudanteItemModificado.setNomeResponsavel(request.nomeResponsavel());
            }
            if (request.contatoResponsavel() != null) {
                estudanteItemModificado.setContatoResponsavel(request.contatoResponsavel());
            }
            if(request.estaAtivo() != null) {
                estudanteItemModificado.setEstaAtivo(request.estaAtivo());
            }

            if (request.turma_id() != null) {
                Turma turma = turmaRepositorio.findById(request.turma_id())
                        .orElseThrow(() -> new ResourceNotFoundException("Turma não encontrada"));

                estudanteItemModificado.setTurma(turma);
            }
            Estudante estudanteSalvo = repositorioEstudante.save(estudanteItemModificado);
            return ResponseEntity.ok(estudanteSalvo);
        } else {
            throw new ResourceNotFoundException("Estudante não encontrado com o id: " + id);
        }
    }
}


