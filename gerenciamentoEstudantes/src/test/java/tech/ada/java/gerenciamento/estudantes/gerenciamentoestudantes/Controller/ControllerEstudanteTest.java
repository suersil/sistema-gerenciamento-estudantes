package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.MockitoJUnitRunner;


@ExtendWith(MockitoExtension.class)
public class ControllerEstudanteTest {
    @Mock
    private ServiceEstudante serviceEstudante;
    @Spy
    ModelMapper modelMapper=new ModelMapper();
    Estudante estudante;
    EstudanteCadastroDTO estudanteDTO;
    List<Estudante> estudantes;
    LocalDateTime data = LocalDateTime.now();
    Turma turma;

    @InjectMocks
    private ControllerEstudante controllerEstudante;
    private MockMvc mockMvc; 

    @BeforeEach
    public void setup() {
        turma = new Turma(); 
        estudante = new Estudante(true, "Joao","02.12.122",
         "Alguem", "99929929", data, turma); 

        estudanteDTO = new EstudanteCadastroDTO("Joao",
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
        Estudante estudante = modelMapper.map(estudanteDTO, Estudante.class);
        when(serviceEstudante.cadastrarEstudante(any(EstudanteCadastroDTO.class)))
                .thenReturn(ResponseEntity.ok(estudante));

        mockMvc.perform(MockMvcRequestBuilders.post("/estudante")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(estudanteDTO)))
                .andExpect(status().isOk());
    }

    @Test
    void retornarBadRequestCadastrandoEstudante() throws Exception {
        when(serviceEstudante.cadastrarEstudante(any())).thenThrow(new BadRequest("BAD REQUEST"));

        mockMvc.perform(MockMvcRequestBuilders.post("/estudante")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(estudanteDTO)))
                        .andExpect(status().isBadRequest()); // status 400 Bad Request.

        verify(serviceEstudante, times(1)).cadastrarEstudante(any());
    }
}
