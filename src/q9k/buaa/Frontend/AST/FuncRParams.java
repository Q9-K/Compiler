package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Utils.Tuple;

import java.util.List;

public class FuncRParams implements Syntax{
    private Syntax exp;
   private List<Tuple<Token,Syntax>> list;

    public FuncRParams(Syntax exp, List<Tuple<Token, Syntax>> list) {
        this.exp = exp;
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
