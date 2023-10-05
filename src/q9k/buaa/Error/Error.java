package q9k.buaa.Error;

public class Error {
    private final int line_number;
    private final ErrorType errorType;

    public Error(ErrorType errorType, int line_number){
        this.errorType = errorType;
        this.line_number = line_number;
    }

    public int getLine_number() {
        return line_number;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
