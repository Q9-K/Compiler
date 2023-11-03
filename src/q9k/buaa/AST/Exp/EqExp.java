package q9k.buaa.AST.Exp;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Token.Token;
import q9k.buaa.Symbol.SymbolType;

import java.io.IOException;

public class EqExp implements Syntax {
    private Syntax rel_exp;
    private Token op_token ;
    private Syntax eq_exp ;
    private SymbolTable symbolTable;

    public EqExp(Syntax rel_exp, Token op_token, Syntax eq_exp) {
        this.rel_exp = rel_exp;
        this.op_token = op_token;
        this.eq_exp = eq_exp;
    }

    @Override
    public void print() throws IOException {
        rel_exp.print();
        printAstName(EqExp.class);
        if(op_token != null){
            op_token.print();
            eq_exp.print();
        }
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTable.getCurrent();
        rel_exp.visit();
        if(eq_exp!=null){
            eq_exp.visit();
        }
    }


    @Override
    public int getLineNumber() {
        if(op_token==null){
            return rel_exp.getLineNumber();
        }
        return eq_exp.getLineNumber();
    }


    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(rel_exp.toString());
        if(op_token != null){
            content.append(op_token.toString()).append(eq_exp.toString());
        }
        return content.toString();
    }
}
