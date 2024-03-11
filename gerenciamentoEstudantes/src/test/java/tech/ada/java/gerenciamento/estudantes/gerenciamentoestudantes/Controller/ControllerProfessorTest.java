package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.DTOS.ProfessorDTO;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors.BadRequest;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model.Professor;
import tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Service.ServiceProfessor;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ControllerProfessorTest {

    @Mock
    private ServiceProfessor serviceProfessor;
    //instanciar a classe que usamos como retorno em alguns metodos
    Professor professor;
    ProfessorDTO professorDTO;
    List<Professor> listaProfessores;

    @InjectMocks
    private ControllerProfessor controllerProfessor;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        professor = new Professor("Brunno Nogueira",
                "brunno@ada.com.br",
                "Progamacao Web",
                true);

        professorDTO = new ProfessorDTO("Brunno Nogueira",
                "brunno@ada.com.br",
                "Progamacao Web",
                true);
        mockMvc = MockMvcBuilders.standaloneSetup(controllerProfessor).build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void cadastrarProfessorComSucessoHttpTest() throws Exception {
        // Simular o comportamento do método serviceProfessor.cadastrarProfessor. Retornar o professorDTO convertido em uma entidade Professor.
        when(serviceProfessor.cadastrarProfessor(any())).thenReturn(professorDTO.paraEntidade());

        // Realizar requisição POST simulada usando MockMvc.
        // O corpo da requisição é definido para o objeto 'professor' convertido para o formato JSON usando asJsonString().
        mockMvc.perform(MockMvcRequestBuilders.post("/professor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(professor)))
                        .andExpect(status().isCreated()) // Esperamos que o código de status da resposta seja 201 Created
                        .andExpect(jsonPath("$.nomeProfessor", equalTo("Brunno Nogueira"))); //verificar se a resposta JSON possui um campo "nomeProfessor" igual a "Brunno Nogueira".

        // verificar se o método serviceProfessor.cadastrarProfessor foi chamado pelo menos uma vez com qualquer argumento
        verify(serviceProfessor, times(1)).cadastrarProfessor(any());
    }

    @Test
    void retornarBadRequestCadastrandoProfessor() throws Exception {
        // Simulamr o comportamento do método serviceProfessor.cadastrarProfessor para lançar uma exceção BadRequest: cenário de falha na validação dos dados do professor.
        when(serviceProfessor.cadastrarProfessor(any())).thenThrow(new BadRequest(""));

        mockMvc.perform(MockMvcRequestBuilders.post("/professor")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(professor)))
                .andExpect(status().isBadRequest()); //Esperamos que a resposta tenha o código de status 400 Bad Request.

        verify(serviceProfessor, times(1)).cadastrarProfessor(any());
    }

    @Test
    void listarTodos() {
    }

    @Test
    void editarTudoProfessor() {
    }

    @Test
    void atualizarProfessor() {
    }

    @Test
    void filtrarProfessorPorNome() {
    }
}