package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.TurmaDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.BadRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.ResourceNotFoundException;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.AlterarTurmaRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;

import java.util.List;
import java.util.Optional;


@Service
public class ServiceTurmaImpl implements ServiceTurma{

    private final RepositorioTurma repositorioTurma;
    private final ModelMapper modelMapper;


    public ServiceTurmaImpl(RepositorioTurma repositorioTurma, ModelMapper modelMapper) {
        this.repositorioTurma = repositorioTurma;
        this.modelMapper = modelMapper;
    }

    @Override
    public Turma cadastrarTurma(TurmaDTO turmaDTO) throws BadRequest {
        Turma turmaConvertida = modelMapper.map(turmaDTO, Turma.class);
        Turma novaTurma = repositorioTurma.save(turmaConvertida);
        return novaTurma;
    }

    @Override
    public List<Turma> buscarTurmas() {
        List<Turma> listaTurma = repositorioTurma.findAll();

        if(listaTurma.isEmpty()){
            throw new ResourceNotFoundException("lista de turmas");
        }
        return listaTurma;
    }

    @Override
    public Turma buscarTurmaEspecifica(Long id) throws Exception {

        Optional<Turma> optionalTurma = repositorioTurma.findById(id);

        if(optionalTurma.isEmpty()) {
            throw new ResourceNotFoundException("Turma", "ID", id);
        }
        Turma turmaExistente = optionalTurma.get();

        return turmaExistente;
    }

    @Override
    public Turma alterarTurma(Long id, AlterarTurmaRequest alterarTurmaRequest) throws Exception {

        Optional<Turma> optionalTurma = repositorioTurma.findById(id);

        if(optionalTurma.isEmpty()) {
            throw new ResourceNotFoundException("Turma", "ID", id);
        }

        Turma turmaModificada = optionalTurma.get();

        if(alterarTurmaRequest.estaAtiva()!= null) turmaModificada.setEstaAtiva(alterarTurmaRequest.estaAtiva());
        if(alterarTurmaRequest.nomeTurma() != null) turmaModificada.setNomeTurma(alterarTurmaRequest.nomeTurma());
        Turma turmaSalva =  repositorioTurma.save(turmaModificada);

        return turmaSalva;
    }

    @Override
    public Turma alteraTurmaCompleto(Long id, AlterarTurmaRequest alterarTurmaRequest) {

        Optional<Turma> optionalTurma = repositorioTurma.findById(id);

        if(optionalTurma.isEmpty()) {
            throw new ResourceNotFoundException("Turma", "ID", id);
        }

        Turma turmaModificada = optionalTurma.get();

        turmaModificada.setEstaAtiva(alterarTurmaRequest.estaAtiva());
        turmaModificada.setNomeTurma(alterarTurmaRequest.nomeTurma());
        Turma turmaSalva = repositorioTurma.save(turmaModificada);
        return turmaSalva;
    }

    @Override
    public List<Turma> findTurmaByEstaAtiva(Boolean status) {
        List<Turma> statusTurmaFiltrada;

        if(status){
            statusTurmaFiltrada = repositorioTurma.findTurmaByEstaAtiva(true);
        }else{
            statusTurmaFiltrada = repositorioTurma.findTurmaByEstaAtiva(false);
        }

        if(statusTurmaFiltrada.isEmpty()){
            throw new ResourceNotFoundException("lista de turmas");
        }

        return statusTurmaFiltrada;
    }
}
