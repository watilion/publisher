package top.watilion.publisher.exception;

/**
 * @author watilion
 * @date 2022/7/9 18:58
 */
public class DuplicateException extends RuntimeException{

    public DuplicateException() {
    }

    public DuplicateException(String message) {
        super(message);
    }
}
