package q9k.buaa.frontend.Parse;

public class ParseHandler {
    public static ParseHandler parseHandler;
    private ParseHandler(){

    }
    public static synchronized ParseHandler getInstance(){
        if(parseHandler == null){
            parseHandler = new ParseHandler();
        }
        return parseHandler;
    }

//    private
}
