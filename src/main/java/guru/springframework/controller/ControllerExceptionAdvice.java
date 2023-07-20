package guru.springframework.controller;

import exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class ControllerExceptionAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFound(Exception exception){
        ModelAndView modelAndView=new ModelAndView();
        log.error(exception.getMessage());
        modelAndView.addObject("exception",exception);
        modelAndView.setViewName("404error");
        return modelAndView;
    }
    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleNumberFormatException(Exception exception){
        ModelAndView modelAndView=new ModelAndView();
        log.error(exception.getMessage());
        modelAndView.addObject("exception",exception);
        modelAndView.setViewName("400error");
        return modelAndView;
    }
}
