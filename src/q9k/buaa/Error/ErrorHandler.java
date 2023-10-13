package q9k.buaa.Error;

import q9k.buaa.INIT.Output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ErrorHandler {
    private String error_path;
    private final List<Error> errors = new ArrayList<>();

    private ErrorHandler(String error_path) {
        this.error_path = error_path;
    }

    private static ErrorHandler errorHandler;

    public static synchronized ErrorHandler getInstance() {
        if (errorHandler == null) {
            System.out.println("something wrong happened at error handler init!");
            System.exit(-1);
        }
        return errorHandler;
    }

    public static synchronized ErrorHandler getInstance(String error_path) {
        if (errorHandler == null) {
            errorHandler = new ErrorHandler(error_path);
        } else if (!errorHandler.error_path.equals(error_path)) {
            errorHandler = new ErrorHandler(error_path);
        }
        return errorHandler;
    }
    public static void clearInstance(){
        errorHandler = null;
    }


    public boolean hasError() {
        return !errors.isEmpty();
    }

    public void addError(Error error) {
        errors.add(error);
    }

    public void print() throws IOException {
        Output output = Output.getInstance(error_path);
        Collections.sort(errors);
        for (Error error : errors) {
            output.write(error.toString());
        }
    }
}
