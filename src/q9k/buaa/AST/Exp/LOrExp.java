package q9k.buaa.AST.Exp;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Token.Token;
import q9k.buaa.Symbol.SymbolType;

import java.io.IOException;

public class LOrExp implements Syntax {
    private Syntax l_and_exp;
    private Token or_token;
    private Syntax l_or_exp;
    private SymbolTable symbolTable;

    public LOrExp(Syntax l_and_exp, Token or_token, Syntax l_or_exp) {
        this.l_and_exp = l_and_exp;
        this.or_token = or_token;
        this.l_or_exp = l_or_exp;
    }

    @Override
    public void print() throws IOException {
        l_and_exp.print();
        printAstName(LOrExp.class);
        if(or_token != null){
            or_token.print();
            l_or_exp.print();
        }
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTable.getCurrent();
        l_and_exp.visit();
        if(l_or_exp!=null){
            l_or_exp.visit();
        }
    }

    @Override
    public int getLineNumber() {
        if(or_token==null){
            return l_and_exp.getLineNumber();
        }
        return l_or_exp.getLineNumber();
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(l_and_exp.toString());
        if(or_token != null){
            content.append(or_token.toString()).append(l_or_exp.toString());
        }
        return content.toString();
    }
}
