package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Errors;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Data
public class ErrorResponse {
    private Date data = new Date();
    private String message;
    private String url;

    public ErrorResponse(String message, String url) {
        this.message = message;
        this.url = url.replace("uri=", "");
    }
}
