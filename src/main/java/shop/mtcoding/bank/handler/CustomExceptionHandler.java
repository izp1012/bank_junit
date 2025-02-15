package shop.mtcoding.bank.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.xml.sax.helpers.DefaultHandler;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.handler.ex.CustomApiException;
import shop.mtcoding.bank.handler.ex.CustomValidationException;

@RestControllerAdvice
public class CustomExceptionHandler extends DefaultHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e){
        logger.error(e.getMessage());
        return new ResponseEntity<>(new ResponseDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?>validationException(CustomValidationException e){
        logger.error(e.getMessage());
        return new ResponseEntity<>(new ResponseDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }
}
