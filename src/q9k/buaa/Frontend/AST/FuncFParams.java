package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Utils.Tuple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FuncFParams implements Syntax{
    private Syntax func_f_param;
    private List<Tuple<Token,Syntax>> list;

    public FuncFParams(Syntax func_f_param, List<Tuple<Token, Syntax>> list) {
        this.func_f_param = func_f_param;
        this.list = list;
        handleError();
    }

    @Override
    public void print() throws IOException {
        func_f_param.print();
        for(Tuple<Token, Syntax> item : list){
            item.getFirst().print();
            item.getSecond().print();
        }
        print_ast_name(FuncFParams.class);
    }

    @Override
    public void handleError() {

    }
}
