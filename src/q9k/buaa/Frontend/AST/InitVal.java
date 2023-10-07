package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Utils.Tuple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InitVal implements Syntax{
    private Syntax exp;
    private Token lbrace;
    private Syntax init_val;
    private List<Tuple<Token, Syntax>> list;
    private Token rbrace;

    public InitVal(Syntax exp, Token lbrace, Syntax init_val, List<Tuple<Token, Syntax>> list, Token rbrace) {
        this.exp = exp;
        this.lbrace = lbrace;
        this.init_val = init_val;
        this.list = list;
        this.rbrace = rbrace;
        handleError();
    }

    @Override
    public void print() throws IOException {
        if(exp != null){
            exp.print();
        }
        else {
            lbrace.print();
            if(init_val != null){
                init_val.print();
                for(Tuple<Token, Syntax> item : list){
                    item.getFirst().print();
                    item.getSecond().print();
                }
            }
            rbrace.print();
        }
        print_ast_name(InitVal.class);
    }

    @Override
    public void handleError() {

    }
}
