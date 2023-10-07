package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;

import java.io.IOException;


public class UnaryExp implements Syntax{
    private Syntax primary_exp;
    private Syntax ident;
    private Token lparent;
    private Syntax func_r_params;
    private Token rparent;
    private Syntax unary_op;
    private Syntax unary_exp;

    public UnaryExp(Syntax primary_exp, Syntax ident, Token lparent, Syntax func_r_params, Token rparent, Syntax unary_op, Syntax unary_exp) {
        this.primary_exp = primary_exp;
        this.ident = ident;
        this.lparent = lparent;
        this.func_r_params = func_r_params;
        this.rparent = rparent;
        this.unary_op = unary_op;
        this.unary_exp = unary_exp;
        handleError();
    }

    @Override
    public void print() throws IOException {
        if(primary_exp != null){
            primary_exp.print();
        }
        else if(ident!=null){
            ident.print();
            lparent.print();
            if(func_r_params!=null){
                func_r_params.print();
            }
            rparent.print();
        }
        else if(unary_op!=null){
            unary_op.print();
            unary_exp.print();
        }
        print_ast_name(UnaryExp.class);
    }

    @Override
    public void handleError() {

    }
}
