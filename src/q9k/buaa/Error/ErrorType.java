package q9k.buaa.Error;

import java.util.HashMap;

public enum ErrorType {
    ILLEGALSYMBOL("a"),//符号非法
    MISSINGSEMICN("i"),
    MISSINGRPARENT("k"),
    MISSINGRBRACK("k"),
    REVERSERROR("reverse_error_code");

    public static final HashMap<String, ErrorType> error_table = new HashMap<>();
    private String name;

    static {
        for (ErrorType errorType : ErrorType.values()){
            if(errorType.name!=null){
                error_table.put(errorType.name, errorType);
            }
        }
    }

    private ErrorType(){
        this.name = null;
    }
    private ErrorType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public static ErrorType getErrorType(String type){
        if(error_table.containsKey(type)){
            return error_table.get(type);
        }
        return REVERSERROR;
    }

}
