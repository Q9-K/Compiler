package q9k.buaa.Error;

public enum ErrorType {
    ILLEGALSYMBOL("a");
    private String type;
    private ErrorType(){
        this.type = null;
    }
    private ErrorType(String type){
        this.type = type;
    }

    public String getType(){
        return this.type;
    }
}
