package kimjang.toolkit.solsol.exception;

public class UnauthorizedException extends RuntimeException{
    public UnauthorizedException(String message, Throwable throwable){
        super(message, throwable);
    }

    public UnauthorizedException(String message){
        super(message);
    }
}
