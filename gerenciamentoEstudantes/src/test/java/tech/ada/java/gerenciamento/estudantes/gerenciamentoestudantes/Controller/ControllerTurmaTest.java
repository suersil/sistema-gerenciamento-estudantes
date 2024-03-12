package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.TurmaDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Estudante;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service.ServiceTurma;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ControllerTurmaTest {
    @InjectMocks
    ControllerTurma controllerTurma;

    @Mock
    ServiceTurma serviceTurma;

    Turma turma;
    TurmaDTO turmaDTO;

    private MockMvc mockMvc;

    String nomeTurma= "5oAnoC";
    Boolean estaAtiva = true;
    @BeforeEach
    public void setup() {
        turma = new Turma(nomeTurma, estaAtiva);
        turmaDTO = new TurmaDTO(nomeTurma, estaAtiva);
        mockMvc = MockMvcBuilders.standaloneSetup(controllerTurma).build();
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deveCadastrarTurmaComSucessoHttpTest() throws Exception {
        when(serviceTurma.cadastrarTurma(any(TurmaDTO.class))).thenReturn(turmaDTO.toEntity());

        mockMvc.perform(MockMvcRequestBuilders.post("/turma")
               .contentType(MediaType.APPLICATION_JSON)
               .content(asJsonString(turmaDTO)))
               .andExpect(status().isCreated())
               .andExpect(jsonPath("$.nomeTurma", equalTo("5oAnoC")));

        verify(serviceTurma, times(1)).cadastrarTurma(any());
    }

    @Test
    void buscarTurmas() {
    }

    @Test
    void buscarTurmaEspecifica() {
    }

    @Test
    void alterarTurma() {
    }

    @Test
    void alteraTurmaCompleto() {
    }

    @Test
    void filtrarStatusTurma() {
    }
}