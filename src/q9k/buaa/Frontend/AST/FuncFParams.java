package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Utils.Tuple;

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
    public void print() {

    }

    @Override
    public void handleError() {

    }
}
