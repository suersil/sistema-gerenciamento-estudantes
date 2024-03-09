package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.EstudanteCadastroDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Estudante;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Repository.RepositorioEstudante;

import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ControllerEstudanteTest2 {
//    .
    @InjectMocks
    ControllerEstudante controllerEstudante;
    @Mock
    RepositorioEstudante repositorioEstudante;
    @Mock
    ModelMapper modelMapper;
    Estudante estudante;
    EstudanteCadastroDTO estudanteCadastroDTO;
    Turma turma;
    LocalDateTime data = LocalDateTime.now(ZoneId.of("America/Sao_Paulo"));

    private MockMvc mockMvc;
    @BeforeEach
    public void setup() {
        estudante = new Estudante(true,"Joao",
                "02.12.20",
                "Alguem",
                "123456789",data,turma);

        estudanteCadastroDTO = new EstudanteCadastroDTO("Joao",
                "Alguem",
                "02.12.20",
                "123456789");
        mockMvc = MockMvcBuilders.standaloneSetup(controllerEstudante).build();
    }
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void cadastrarEstudante() throws Exception{
        when(repositorioEstudante.save(Mockito.any())).thenReturn(estudante);
        mockMvc.perform(MockMvcRequestBuilders.post("/estudante").
                contentType(MediaType.APPLICATION_JSON).
                content(asJsonString(estudante))).andExpect(status().isCreated());
        verify(repositorioEstudante, times(1)).save(Mockito.any());
    }
}