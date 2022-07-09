package top.watilion.publisher.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.watilion.publisher.exception.BaseException;
import top.watilion.publisher.exception.DuplicateException;
import top.watilion.publisher.vo.Response;

import java.io.IOException;
import java.util.Objects;

/**
 * @author watilion
 * @date 2022/6/16 00:53
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<Void> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception){
        Response<Void> iepResponse = new Response<>();
        return iepResponse.fail(Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(IOException.class)
    public Response<Void> ioExceptionHandler(IOException ioException) {
        Response<Void> iepResponse = new Response<>();
        return iepResponse.fail(ioException.getMessage());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(DuplicateException.class)
    public Response<Void> duplicateExceptionHandler(DuplicateException duplicateException) {
        Response<Void> iepResponse = new Response<>();
        return iepResponse.fail(duplicateException.getMessage());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(BaseException.class)
    public Response<Void> BaseExceptionHandler(BaseException baseException) {
        Response<Void> iepResponse = new Response<>();
        return iepResponse.fail(baseException.getMessage());
    }
}
