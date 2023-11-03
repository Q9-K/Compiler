package q9k.buaa.Token;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;

import java.util.HashMap;

/**
 * @author Q9K
 * @date 2023/09/20
 */
public enum TokenType {
    //TK means token
    MAINTK("main"),
    CONSTTK("const"),
    INTTK("int"),
    BREAKTK("break"),
    CONTINUETK("continue"),
    IFTK("if"),
    ELSETK("else"),
    NOT("!"),
    AND("&&"),
    OR("||"),
    FORTK("for"),
    GETINTTK("getint"),
    PRINTFTK("printf"),
    RETURNTK("return"),
    PLUS("+"),
    MINU("-"),
    VOIDTK("void"),
    MULT("*"),
    DIV("/"),
    MOD("%"),
    LSS("<"),
    LEQ("<="),
    GRE(">"),
    GEQ(">="),
    EQL("=="),
    NEQ("!="),
    ASSIGN("="),
    SEMICN(";"),
    COMMA(","),
    LPARENT("("),
    RPARENT(")"),
    LBRACK("["),
    RBRACK("]"),
    LBRACE("{"),
    RBRACE("}"),
    IDENFR,
    INTCON,
    STRCON;

    public String getName(){
        return this.name;
    }

    private static final HashMap<String, TokenType> token_table = new HashMap<>();
    public static final HashMap<String, TokenType> reverse_words = new HashMap<>();
    private final String name;

    static {
        for (TokenType tokenType : TokenType.values()) {
            if(tokenType.name!=null){
                token_table.put(tokenType.name, tokenType);
            }
            if(tokenType.name!=null&&tokenType.name.matches("\\w*")){
                reverse_words.put(tokenType.name,tokenType);
            }
        }
    }

    TokenType() {
        this.name = null;
    }

    TokenType(String name) {
        this.name = name;
    }


    public static TokenType getTokenType(Token token) {
        String content = token.toString();
        if (token_table.containsKey(content)) {
            return token_table.get(content);
        } else {
            if (content.startsWith("\"")&&content.endsWith("\"")) {
                handleError(token);
                return TokenType.STRCON;
            } else if (isINTCON(content)) {
                return TokenType.INTCON;
            }
        }
        return TokenType.IDENFR;
    }
    public static TokenType getTokenType(String content){
        if (token_table.containsKey(content)) {
            return token_table.get(content);
        } else {
            if (content.startsWith("\"")&&content.endsWith("\"")) {
                return TokenType.STRCON;
            } else if (isINTCON(content)) {
                return TokenType.INTCON;
            }
        }
        return TokenType.IDENFR;
    }

    private static boolean isINTCON(String content) {
        return content.matches("\\d+");
    }
    private static void handleError(Token token)
    {
        String content = token.toString();
        boolean flag = true;
        int length = content.length();
        if (content.charAt(0) != '"' || content.charAt(length - 1) != '"') {
            flag = false;
        }
        for (int i = 1; i < length - 1 && flag; ++i) {
            if (isLegalChar(content.charAt(i))) {
                if (content.charAt(i) == '\\') {
                    if (content.charAt(i + 1) != 'n') {
                        flag = false;
                    } else {
                        i++;
                    }
                }
            }else if(content.charAt(i) == '%'){
                if (content.charAt(i + 1) != 'd') {
                    flag = false;
                } else {
                    flag = false;
                    i++;
                }
            }
            else {
                flag = false;
            }
        }
        if (!flag) {
            ErrorHandler.getInstance().addError(new Error(ErrorType.ILLEGALSYMBOL, token.getLineNumber()));
        }
    }
    private static boolean isLegalChar(char c) {
        return (int) c == 32 || (int) c == 33 || ((int) c >= 40 && (int) c <= 126);
    }
}
