package hello.controller;

/**
 * Created by yuxij on 7/23/17.
 */
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class ExceptionControllerAdvice {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        logger.info("controller advice: init binder");
    }


    @ExceptionHandler(Exception.class)
    public String exception(Exception e) {
        logger.error("controller advice: exception Handler");
        logger.error(e.getMessage());
        return "error";
    }

    @ModelAttribute
    public void modelAttribute(){
        logger.info("controller advice:model Attribute");
    }
}
