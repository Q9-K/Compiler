package q9k.buaa.AST.Exp;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Symbol.SymbolType;

import java.io.IOException;

public class MulExp implements Syntax,Type {
    private Syntax unary_exp;
    private Token op_token;
    private Syntax mul_exp;

    public MulExp(Syntax unary_exp, Token op_token, Syntax mul_exp) {
        this.unary_exp = unary_exp;
        this.op_token = op_token;
        this.mul_exp = mul_exp;
    }

    @Override
    public void print() throws IOException {
        unary_exp.print();
        printAstName(MulExp.class);
        if(op_token != null){
            op_token.print();
            mul_exp.print();
        }
    }

    @Override
    public void visit() {
        unary_exp.visit();
        if(mul_exp!=null){
            mul_exp.visit();
        }
    }

    @Override
    public int getLineNumber() {
        if(op_token==null){
            return unary_exp.getLineNumber();
        }
        return mul_exp.getLineNumber();
    }

    @Override
    public SymbolType getSymbolType() {
        return ((UnaryExp)unary_exp).getSymbolType();
    }
}
