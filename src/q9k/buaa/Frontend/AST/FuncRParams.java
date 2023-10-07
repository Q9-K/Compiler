package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Utils.Tuple;

import java.io.IOException;
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
    public void print() throws IOException {
        exp.print();
        for(Tuple<Token, Syntax> item : list){
            item.getFirst().print();
            item.getSecond().print();
        }
        print_ast_name(FuncRParams.class);
    }

    @Override
    public void handleError() {

    }
}
