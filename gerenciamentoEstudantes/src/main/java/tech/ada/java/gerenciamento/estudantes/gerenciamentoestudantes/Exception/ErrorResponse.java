package tech.ada.java.gerenciamento.estudantes.gerenciamentoestudantes.Exception;

import lombok.*;
import org.springframework.http.HttpStatus;

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




