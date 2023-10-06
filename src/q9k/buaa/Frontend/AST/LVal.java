package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Utils.Triple;

import java.util.List;

public class LVal implements Syntax{

    private Syntax ident;
    private List<Triple<Token, Syntax, Token>> list;

    public LVal(Syntax ident, List<Triple<Token, Syntax, Token>> list) {
        this.ident = ident;
        this.list = list;
        handleError();
    }

    @Override
    public void print() {
    }

    @Override
    public void handleError() {

    }
}
