package q9k.buaa.AST.Exp;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Token.Token;
import q9k.buaa.Symbol.SymbolType;

import java.io.IOException;

public class RelExp implements Syntax {
    private Syntax add_exp;
    private Token op_token;
    private Syntax rel_exp;
    private SymbolTable symbolTable;


    public RelExp(Syntax add_exp, Token op_token, Syntax rel_exp) {
        this.add_exp = add_exp;
        this.op_token = op_token;
        this.rel_exp = rel_exp;
    }

    @Override
    public void print() throws IOException {
        add_exp.print();
        printAstName(RelExp.class);
        if(op_token != null){
            op_token.print();
            rel_exp.print();
        }
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTable.getCurrent();
        add_exp.visit();
        if(rel_exp!=null){
            rel_exp.visit();
        }
    }

    @Override
    public int getLineNumber() {
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(add_exp.toString());
        if(op_token != null){
            content.append(op_token.toString()).append(rel_exp.toString());
        }
        return content.toString();
    }
}
