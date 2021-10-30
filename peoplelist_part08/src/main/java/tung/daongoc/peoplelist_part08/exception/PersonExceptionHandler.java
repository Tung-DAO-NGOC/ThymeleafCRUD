package tung.daongoc.peoplelist_part08.exception;

import java.io.IOException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class PersonExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EmptyResultDataAccessException.class)
    public String handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
            Model model) {
        model.addAttribute("errorCause", ex.getClass().toString());
        model.addAttribute("errorMes", "The id is not found!");
        return "errorPage";
    }

    @ExceptionHandler(IOException.class)
    public String handleIOException(IOException ex,
            Model model) {
        model.addAttribute("errorCause", ex.getClass().toString());
        model.addAttribute("errorMes", "Uploading File Error!");
        return "errorPage";
    }

}
