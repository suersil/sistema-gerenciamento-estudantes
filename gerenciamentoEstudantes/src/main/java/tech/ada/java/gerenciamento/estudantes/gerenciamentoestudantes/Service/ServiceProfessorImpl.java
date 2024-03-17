package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.ProfessorDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.ResourceNotFoundException;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.AtualizarProfessorRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Professor;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.ProfessorRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioProfessor;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceProfessorImpl implements ServiceProfessor{

    private final RepositorioProfessor repositorioProfessor;
    private final RepositorioTurma turmaRepositorio;
    private final ModelMapper modelMapper;


    @Autowired
    public ServiceProfessorImpl(RepositorioProfessor repositorioProfessor, ModelMapper modelMapper, RepositorioTurma turmaRepositorio) {
        this.repositorioProfessor = repositorioProfessor;
        this.turmaRepositorio = turmaRepositorio;
        this.modelMapper = modelMapper;
    }

    @Override
    public Professor cadastrarProfessor(ProfessorDTO professorDTO) {
        Professor professorConvertido = modelMapper.map(professorDTO, Professor.class);
        Professor novoProfessor = repositorioProfessor.save(professorConvertido);
        return novoProfessor;
    }

    @Override
    public List<Professor> listarTodos() {
        List<Professor> listaProfessores = repositorioProfessor.findAll();
        if(listaProfessores.isEmpty()){
            throw new ResourceNotFoundException("lista de professores");
        }

        return listaProfessores;
    }

    @Override
    public Professor editarParcialProfessor(Long id, ProfessorRequest professorRequest) throws Exception {
        Optional<Professor> optionalProfessor = repositorioProfessor.findById(id);
        if (optionalProfessor.isPresent()) {
            Professor professorModificado = optionalProfessor.get();
            // verificamos se um das tres variaveis que esperamos foi passada para ser atualizada
            if (professorRequest.nomeProfessor() != null) professorModificado.setNomeProfessor(professorRequest.nomeProfessor());
            if (professorRequest.email() != null) professorModificado.setEmail(professorRequest.email());
            if (professorRequest.disciplinaLecionada() != null) professorModificado.setDisciplinaLecionada(professorRequest.disciplinaLecionada());
            if (professorRequest.estaAtivo() != null) professorModificado.setEstaAtivo(professorRequest.estaAtivo());
            Long turmaId = professorRequest.turma_id();
            if (turmaId != null) {
                Optional<Turma> optionalTurma = turmaRepositorio.findById(turmaId);
                if(optionalTurma.isPresent()) {
                    professorModificado.AdicionarTurma(optionalTurma.get());
                } else{
                    throw new ResourceNotFoundException("turma registrada");
                }
            }
            Professor professorSalvo = repositorioProfessor.save(professorModificado);
            return professorSalvo;
        }
        else {
            throw new ResourceNotFoundException("professor encontrado");
        }
    }

    @Override
    public Professor atualizarProfessor(Long id, AtualizarProfessorRequest atualizarProfessorRequest) throws Exception {
        Optional<Professor> optionalProfessor = repositorioProfessor.findById(id);

        if(optionalProfessor.isEmpty()){
            throw new ResourceNotFoundException("Professor", "ID", id);
        }

        Professor professorExistente = optionalProfessor.get();

        professorExistente.setNomeProfessor(atualizarProfessorRequest.nomeProfessor());
        professorExistente.setEmail(atualizarProfessorRequest.email());
        professorExistente.setDisciplinaLecionada(atualizarProfessorRequest.disciplinaLecionada());
        professorExistente.setEstaAtivo(atualizarProfessorRequest.estaAtivo());

        Professor professorSalvo = repositorioProfessor.save(professorExistente);

        return professorSalvo;
    }

    @Override
    public List<Professor> filtrarProfessorPorNome(String nomeProfessor) {
        List<Professor> listaProfessores = repositorioProfessor.findProfessorsByNomeProfessor(nomeProfessor);

        if(listaProfessores.isEmpty()){
            throw new ResourceNotFoundException("professor por nome");
        }
        return listaProfessores;
    }
}
