package br.com.nszandrew.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class ExceptionResponse {
    private Date data;
    private HttpStatus status;
    private String mensagem;
    private String detalhes;
}
