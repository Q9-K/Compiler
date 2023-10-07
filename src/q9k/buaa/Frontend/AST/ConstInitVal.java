package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Utils.Tuple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConstInitVal implements Syntax{
    private Syntax const_exp;
    private Token lbrace_token;
    private Syntax const_init_val;
    private List<Tuple<Token, Syntax>> list;
    private Token rbrace_token;

    public ConstInitVal(Syntax const_exp, Token lbrace_token, Syntax const_init_val, List<Tuple<Token, Syntax>> list, Token rbrace_token) {
        this.const_exp = const_exp;
        this.lbrace_token = lbrace_token;
        this.const_init_val = const_init_val;
        this.list = list;
        this.rbrace_token = rbrace_token;
        handleError();
    }

    @Override
    public void print() throws IOException {
        if(const_exp != null){
            const_exp.print();
        }
        else{
            lbrace_token.print();
            if(const_init_val != null){
                const_init_val.print();
                for(Tuple<Token, Syntax> item : list){
                    item.getFirst().print();
                    item.getSecond().print();
                }
            }
            rbrace_token.print();
        }
        print_ast_name(ConstInitVal.class);
    }

    @Override
    public void handleError() {

    }
}
