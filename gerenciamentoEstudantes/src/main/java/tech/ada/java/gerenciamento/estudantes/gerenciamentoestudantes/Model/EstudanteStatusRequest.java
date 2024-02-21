package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Model;

import lombok.Getter;
import lombok.Setter;

public record EstudanteStatusRequest
        (boolean estaAtivo, String nomeAluno, String nomeResponsavel, String contatoResponsavel){ }

