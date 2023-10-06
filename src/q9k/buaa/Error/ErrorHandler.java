package q9k.buaa.Error;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {
    private ErrorHandler(){

    }
    private final List<Error> errors = new ArrayList<>();
    private static ErrorHandler errorHandler;
    public static synchronized ErrorHandler getInstance(){
        if(errorHandler==null){
            errorHandler = new ErrorHandler();
        }
        return errorHandler;
    }

    public boolean hasError(){
        return errors.isEmpty();
    }

    public void addError(Error error){
        errors.add(error);
    }

    public void run(){
        System.out.println("reverse interface for error_handle");
    }
}
