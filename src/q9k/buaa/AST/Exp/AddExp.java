package q9k.buaa.AST.Exp;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Symbol.SymbolType;

import java.io.IOException;

public class AddExp implements Syntax,Type {
    private Syntax mul_exp;
    private Token op_token;
    private Syntax add_exp;

    public AddExp(Syntax mul_exp, Token op_token, Syntax add_exp) {
        this.mul_exp = mul_exp;
        this.op_token = op_token;
        this.add_exp = add_exp;
    }


    ////加减表达式 AddExp → MulExp | AddExp ('+' | '−') MulExp
    //    //改为 AddExp → MulExp | MulExp ('+' | '−') AddExp
    @Override
    public void print() throws IOException {
        mul_exp.print();
        printAstName(AddExp.class);
        if(op_token!=null){
            op_token.print();
            add_exp.print();
        }
    }

    @Override
    public void visit() {
        mul_exp.visit();
        if(op_token!=null){
            add_exp.visit();
        }
    }

    @Override
    public int getLineNumber() {
        if(op_token==null){
            return mul_exp.getLineNumber();
        }
        return add_exp.getLineNumber();
    }

    @Override
    public SymbolType getSymbolType() {
        return ((MulExp)mul_exp).getSymbolType();
    }
}
