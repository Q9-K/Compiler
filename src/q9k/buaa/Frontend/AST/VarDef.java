package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Utils.Triple;

import java.io.IOException;
import java.util.List;

public class VarDef implements Syntax{

    private Syntax ident;
    private List<Triple<Token, Syntax, Token>> list;
    private Token assign_token;
    private Syntax init_val;

    public VarDef(Syntax ident, List<Triple<Token, Syntax, Token>> list, Token assign_token, Syntax init_val) {
        this.ident = ident;
        this.list = list;
        this.assign_token = assign_token;
        this.init_val = init_val;
        handleError();
    }

    @Override
    public void print() throws IOException {
        ident.print();
        for(Triple<Token, Syntax, Token> item : list){
            item.getFirst().print();
            item.getSecond().print();
            item.getThird().print();
        }
        if(assign_token!=null){
            assign_token.print();
            init_val.print();
        }
        print_ast_name(VarDef.class);
    }

    @Override
    public void handleError() {

    }
}
