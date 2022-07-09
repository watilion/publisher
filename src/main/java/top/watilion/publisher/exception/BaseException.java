package top.watilion.publisher.exception;

/**
 * @author watilion
 * @date 2022/7/9 18:58
 */
public class BaseException extends RuntimeException{

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }
}
