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
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.BadRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.ResourceNotFoundException;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.AlterarTurmaRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Estudante;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service.ServiceTurma;

import java.util.ArrayList;
import java.util.List;

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

    @Mock
    AlterarTurmaRequest alterarTurmaRequest;

    Turma turma;
    TurmaDTO turmaDTO;

    private MockMvc mockMvc;

    Long turmaID = 1L;
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
               .andExpect(jsonPath("$.nomeTurma").value("5oAnoC"));

        verify(serviceTurma, times(1)).cadastrarTurma(any());
    }

    @Test
    void retornarBadRequestCadastrandoTurma() throws Exception {
        when(serviceTurma.cadastrarTurma(any(TurmaDTO.class))).thenThrow(new BadRequest(""));

        mockMvc.perform(MockMvcRequestBuilders.post("/turma")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(turmaDTO)))
                .andExpect(status().isBadRequest());

        verify(serviceTurma, times(1)).cadastrarTurma(any());
    }

    @Test
    void listarTodasTurmasComSucessoHttpTest() throws Exception {
        when(serviceTurma.buscarTurmas ()).thenReturn(List.of(turmaDTO.toEntity()));

        mockMvc.perform(MockMvcRequestBuilders.get("/turmas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", equalTo(1)))  // Assert list size
                .andExpect(jsonPath("$.[0].nomeTurma", equalTo("5oAnoC")));  // Assert specific field

        verify(serviceTurma, times(1)).buscarTurmas();
    }

    @Test
    void retornarBadRequestListarTodasTurmas() throws Exception {
        when(serviceTurma.buscarTurmas()).thenThrow(new BadRequest(""));

        mockMvc.perform(MockMvcRequestBuilders.get("/turmas"))
                .andExpect(status().isBadRequest());

        verify(serviceTurma, times(1)).buscarTurmas();
    }
    @Test
    void buscarTurmaPorIDComSucessoHttpTest() throws Exception {
        when(serviceTurma.buscarTurmaEspecifica(any())).thenReturn(turmaDTO.toEntity());
        mockMvc.perform(MockMvcRequestBuilders.get("/turma/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeTurma", equalTo("5oAnoC")));

        verify(serviceTurma, times(1)).buscarTurmaEspecifica(1L);
    }

    @Test
    void retornarBadRequestBuscarTurmaPorIDComSucessoHttpTest() throws Exception {
        when(serviceTurma.buscarTurmaEspecifica(any())).thenThrow(new BadRequest(""));
        mockMvc.perform(MockMvcRequestBuilders.get("/turma/1"))
                .andExpect(status().isBadRequest());

        verify(serviceTurma, times(1)).buscarTurmaEspecifica(1L);
    }

    @Test
    void alterarTurmaComSucessoHttpTest() throws Exception {
        when(serviceTurma.alterarTurma(any(Long.class), any(AlterarTurmaRequest.class))).thenReturn(turmaDTO.toEntity());
        mockMvc.perform(MockMvcRequestBuilders.patch("/turma/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new AlterarTurmaRequest(estaAtiva, nomeTurma))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeTurma", equalTo(nomeTurma)))
                .andExpect(jsonPath("$.estaAtiva").value(estaAtiva));

        verify(serviceTurma, times(1)).alterarTurma (1L, new AlterarTurmaRequest(estaAtiva, nomeTurma));
    }
    //em implementação
    @Test
    void retornarNotFoundAlterarTurma() throws Exception {
//        when(serviceTurma.alterarTurma(any(Long.class), any(AlterarTurmaRequest.class))).thenThrow(new ResourceNotFoundException("Turma", "ID", 1L));
//        mockMvc.perform(MockMvcRequestBuilders.patch("/turma/1")
////                        .param("estaAtiva", String.valueOf(false))
//                        .param("nomeTurma", "3oAnoC"))
//                .andExpect(status().isNotFound());
    }

    //em implementação
    @Test
    void alteraTurmaCompleto() throws Exception{
        when(serviceTurma.alteraTurmaCompleto(any(Long.class), any(AlterarTurmaRequest.class))).thenReturn(turmaDTO.toEntity());
        mockMvc.perform(MockMvcRequestBuilders.put("/turma/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new AlterarTurmaRequest(estaAtiva, nomeTurma))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeTurma", equalTo(nomeTurma)))
                .andExpect(jsonPath("$.estaAtiva").value(estaAtiva));

        verify(serviceTurma, times(1)).alteraTurmaCompleto (1L, new AlterarTurmaRequest(estaAtiva, nomeTurma));
    }
    //em implementação
    @Test
    void retornarNotFoundAlterarTurmaCompleto() throws Exception {
//        when(serviceTurma.alteraTurmaCompleto(any(Long.class), any(AlterarTurmaRequest.class))).thenThrow(new ResourceNotFoundException("lista de turmas"));
//        mockMvc.perform(MockMvcRequestBuilders.put("/turma/1")
//                        .param("estaAtiva", String.valueOf(true)))
//                .andExpect(status().isNotFound());
    }

    @Test
    void filtrarStatusTurmaComSucessoHttpTest() throws Exception {
        when(serviceTurma.findTurmaByEstaAtiva (any())).thenReturn(List.of(turmaDTO.toEntity()));

        mockMvc.perform(MockMvcRequestBuilders.get("/turmas")
                        .param("estaAtiva", "true"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()" , equalTo(1)))
                .andExpect(jsonPath("$.[0].estaAtiva", equalTo(true)));

        verify(serviceTurma, times(1)).findTurmaByEstaAtiva (true);
    }

    @Test
    void retornarNotFoundFiltrarStatusTurma() throws Exception {
        when(serviceTurma.findTurmaByEstaAtiva(any())).thenThrow(new ResourceNotFoundException("lista de turmas"));
        mockMvc.perform(MockMvcRequestBuilders.get("/turmas")
                        .param("estaAtiva", String.valueOf(true)))
                .andExpect(status().isNotFound());
    }
}