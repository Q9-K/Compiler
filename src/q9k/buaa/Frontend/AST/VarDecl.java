package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Frontend.Token.TokenType;
import q9k.buaa.Utils.Tuple;

import java.util.List;

public class VarDecl implements Syntax {
    private Syntax b_type;
    private Syntax var_def;
    private List<Tuple<Token,Syntax>> list;
    private Token rbrace;

    public VarDecl(Syntax b_type, Syntax var_def, List<Tuple<Token, Syntax>> list, Token rbrace) {
        this.b_type = b_type;
        this.var_def = var_def;
        this.list = list;
        this.rbrace = rbrace;
        handleError();
    }

    @Override
    public void print() {

    }

    @Override
    public void handleError() {

    }
}
