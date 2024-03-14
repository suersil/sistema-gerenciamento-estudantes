package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Estudante cadastrarEstudante(EstudanteCadastroDTO request) {
        Estudante estudante = modelMapper.map(request, Estudante.class);
      
        var novoEstudante = repositorioEstudante.save(estudante);

        return novoEstudante;

    }
    
    @Override
    public List<Estudante> listarTodosEstudantes() {
        List<Estudante> listaEstudantes = repositorioEstudante.findAll();
        
        if (listaEstudantes.isEmpty()) {
            throw new ResourceNotFoundException("lista de estudantes");
        }
        return repositorioEstudante.findAll();
      //  return ResponseEntity.status(HttpStatus.OK).body(repositorioEstudante.findAll());
    }

    @Override
    public List<Estudante> filtrarStatusEstudante(Boolean status){
        List<Estudante> statusEstudantesFiltrados;
        
        if (Boolean.TRUE.equals(status)) {
            statusEstudantesFiltrados = repositorioEstudante.findEstudantesByEstaAtivo(true);
        } else {
            statusEstudantesFiltrados = repositorioEstudante.findEstudantesByEstaAtivo(false);
        }
        
        if (!statusEstudantesFiltrados.isEmpty()) {
        //return ResponseEntity.status(HttpStatus.OK).body(statusEstudantesFiltrados);
        return statusEstudantesFiltrados;
        } else {
            throw new ResourceNotFoundException("lista de estudantes");
        }
    }

    @Override
    public Optional<Estudante> filtrarEstudanteId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("O id deve ser enviado");
        }
       
        Optional<Estudante> estudante = repositorioEstudante.findById(id);
        
        if (estudante.isEmpty()) {
            throw new ResourceNotFoundException("estudante", "id", id);
        }
        return estudante;
     
    }
   
    @Override
    public List<Estudante> filtrarEstudanteNome(String nomeAluno) {
        List<Estudante> estudantePorNome = repositorioEstudante.findByNomeAlunoQuery(nomeAluno);
        
        if (estudantePorNome.isEmpty()) {
            throw new ResourceNotFoundException("estudante", "nome ", nomeAluno);
        }
        return estudantePorNome;
      //  return ResponseEntity.status(HttpStatus.OK).body(repositorioEstudante.findByNomeAlunoQuery(nomeAluno));
    }

    @Override
    public Estudante editarTudoEstudante(Long id, EstudanteCadastroDTO request) {
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

       // return ResponseEntity.status(HttpStatus.OK).body(estudanteSalvo);
        return estudanteAtualizado;
    }
        

    @Override
    public Estudante atualizarEstudante(Long id, EstudanteRequest request) {
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
            return estudanteSalvo;
        } else {
            throw new ResourceNotFoundException("Estudante não encontrado com o id: " + id);
        }
    }
}


