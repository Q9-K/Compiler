package q9k.buaa.Error;

public class ErrorHandler {
    private ErrorHandler(){

    }
    private static ErrorHandler errorHandler;
    public static synchronized ErrorHandler getInstance(){
        if(errorHandler==null){
            errorHandler = new ErrorHandler();
        }
        return errorHandler;
    }

    public void handle(){
        System.out.println("something wrong in your code");
    }
    public void handle(int line_number){
        System.out.println("something wrong in your code at line "+line_number);
    }
    public void handle(ErrorType errorType,int line_number){
        System.out.println("errorCode: "+errorType.getType());
        System.out.println("at line "+line_number);
    }


}
