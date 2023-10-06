package q9k.buaa.Frontend.Token;

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

    public static TokenType getTokenType(String token) {
        if (token_table.containsKey(token)) {
            return token_table.get(token);
        } else {
            if (token.startsWith("\"")&&token.endsWith("\"")) {
                return TokenType.STRCON;
            } else if (isINTCON(token)) {
                return TokenType.INTCON;
            }
        }
        return TokenType.IDENFR;
    }

    private static boolean isINTCON(String token) {
        return token.matches("\\d+");
    }

}
