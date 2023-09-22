package q9k.buaa.Lexer;

import java.util.HashMap;

/**
 * @author Q9K
 * @date 2023/09/20
 */
public enum LexType {
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


    private static final HashMap<String, LexType> reverseWords = new HashMap<>();
    private final String name;

    static {
        for (LexType lextype : LexType.values()) {
            if(lextype.name!=null){
                reverseWords.put(lextype.name, lextype);
            }
        }
    }

    LexType() {
        this.name = null;
    }

    LexType(String name) {
        this.name = name;
    }

    public static LexType getLextype(StringBuffer token) {
        String key = token.toString();
        if (reverseWords.containsKey(key)) {
            return reverseWords.get(key);
        } else {
            if (key.startsWith("\"")&&key.endsWith("\"")) {
                return LexType.STRCON;
            } else if (isINTCON(key)) {
                return LexType.INTCON;
            }
        }
        return LexType.IDENFR;
    }

    private static boolean isINTCON(String token) {
        return token.matches("\\d+");
    }

}
