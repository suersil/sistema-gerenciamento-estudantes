package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.EstudanteCadastroDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.BadRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Estudante;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Turma;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service.ServiceEstudante;

@ExtendWith(MockitoExtension.class)
//@MockitoSettings(strictness = Strictness.LENIENT)
public class ControllerEstudanteTest {
    @Mock
    private ServiceEstudante serviceEstudante;
    @Mock
    ModelMapper modelMapper;
    Estudante estudante;
    EstudanteCadastroDTO estudanteDto;
    List<Estudante> estudantes;
    LocalDateTime data = LocalDateTime.now();
    Turma turma;

    @InjectMocks
    private ControllerEstudante controllerEstudante;
    private MockMvc mockMvc; 

   @BeforeEach
    public void setup() {
      //  turma = new Turma();
        estudante = new Estudante(true, "Joao","02.12.122",
         "Alguem", "99929929",data, turma);

        estudanteDto = new EstudanteCadastroDTO("Joao",
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

/*     cadastrarEstudanteComSucessoHttpTest()
retornarBadRequestCadastrandoEstudante()
listarTodosComSucessoHttpTest()
     */

    @Test
    public void cadastrarEstudanteComSucessoHttpTest() throws Exception {
        Estudante estudante = modelMapper.map(estudanteDto, Estudante.class);

        mockMvc.perform(MockMvcRequestBuilders.post("/estudante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(estudanteDto)))
                        .andExpect(status().isCreated()); //Para corresponder ao status 201
    }

    @Test
    void retornarBadRequestCadastrandoEstudante() throws Exception {
        when(serviceEstudante.cadastrarEstudante(any())).thenThrow(new BadRequest("BAD REQUEST"));

        mockMvc.perform(MockMvcRequestBuilders.post("/estudante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(estudanteDto)))
                        .andExpect(status().isBadRequest()); // status 400 Bad Request.

        verify(serviceEstudante, times(1)).cadastrarEstudante(any());
    }

    @Test
    void listarTodosComSucessoHttpTest() throws Exception {

        Estudante estudante = new Estudante();
        estudante.setNomeAluno("Fulaninho");

        when(serviceEstudante.listarTodosEstudantes()).thenReturn(List.of(estudante));

        mockMvc.perform(MockMvcRequestBuilders.get("/estudante"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", equalTo(1)))
                .andExpect(jsonPath("$.[0].nomeAluno", equalTo("Fulaninho")));

        verify(serviceEstudante, times(1)).listarTodosEstudantes();
    }
}
