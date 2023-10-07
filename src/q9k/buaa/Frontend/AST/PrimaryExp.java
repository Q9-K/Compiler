package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;

import java.io.IOException;

public class PrimaryExp implements Syntax{

    private Token lparent;
    private Syntax exp;
    private Token rparent;
    private Syntax l_val;
    private Syntax number;

    public PrimaryExp(Token lparent, Syntax exp, Token rparent, Syntax l_val, Syntax number) {
        this.lparent = lparent;
        this.exp = exp;
        this.rparent = rparent;
        this.l_val = l_val;
        this.number = number;
        handleError();
    }

    @Override
    public void print() throws IOException {
        if(exp != null){
            lparent.print();
            exp.print();
            rparent.print();
        }
        else if(l_val != null){
            l_val.print();
        }
        else if(number!=null){
            number.print();
        }
        print_ast_name(PrimaryExp.class);

    }

    @Override
    public void handleError() {

    }
}
