package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import lombok.Getter;
import lombok.Setter;

public record EstudanteStatusRequest
        (Boolean estaAtivo, String nomeAluno, String nomeResponsavel, String contatoResponsavel){ }

