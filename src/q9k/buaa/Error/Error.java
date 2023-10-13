package q9k.buaa.Error;

public class Error implements Comparable<Error>{
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

    @Override
    public int compareTo(Error o) {
        return this.getLine_number() - o.getLine_number();
    }

    @Override
    public String toString() {
        return line_number + " " + errorType.getName() + '\n';
    }
}
