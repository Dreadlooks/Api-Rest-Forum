package br.com.alura.forum.configuracao.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class ValidacaoHandler {

    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<FormErrors> handle(MethodArgumentNotValidException exception) {

        List<FormErrors> erros = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(fieldError -> {
            FormErrors erro = new FormErrors(fieldError.getField(), messageSource.getMessage(fieldError, LocaleContextHolder.getLocale()));
            erros.add(erro);
        });

        return erros;
    }
}
