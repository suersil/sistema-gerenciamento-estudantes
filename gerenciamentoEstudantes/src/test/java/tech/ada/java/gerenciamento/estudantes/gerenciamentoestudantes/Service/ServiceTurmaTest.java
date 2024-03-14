package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.TurmaDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioTurma;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceTurmaTest {
    @InjectMocks
    ServiceTurmaImpl serviceTurma;

    @Mock
    RepositorioTurma repositorioTurma;
    @Mock
    ModelMapper modelMapper;

    Turma turma;
    TurmaDTO turmaDTO;

    String nomeTurma= "5oAnoC";
    Boolean estaAtiva = true;

    @BeforeEach
    public void setup() {
        turma = new Turma(nomeTurma, estaAtiva);
        turmaDTO = new TurmaDTO(nomeTurma, estaAtiva);
    }


    @Test
    void cadastrarTurma() {
    }

    @Test
    void buscarTurmas() {
    }

    @Test
    void buscarTurmaEspecifica() {
    }

    @Test
    void alterarTurma() {
        when(repositorioTurma.findById(anyLong())).thenReturn(Optional.of(turma));

    }

    @Test
    void alteraTurmaCompleto() {
    }

    @Test
    void findTurmaByEstaAtiva() {
    }
}