package q9k.buaa.AST.Stmt;

import q9k.buaa.AST.Syntax;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;
import q9k.buaa.Token.Token;

import java.io.IOException;

public class ExpStmt implements Stmt{
    private Syntax exp;
    private Token semicn_token;
    private SymbolTable symbolTable;
    

    public ExpStmt(Syntax exp, Token semicn_token) {
        this.exp = exp;
        this.semicn_token = semicn_token;
    }

    @Override
    public void print() throws IOException {
        if(exp!=null){
            exp.print();
        }
        semicn_token.print();
        printAstName(Stmt.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
        if(exp!=null){
            exp.visit();
        }
    }

    @Override
    public int getLineNumber() {
        return exp.getLineNumber();
    }

    @Override
    public Value genIR() {
        if(exp!=null){
            exp.genIR();
        }
        return null;
    }

    @Override
    public String toString() {
        if(exp!=null){
            return exp.toString()+semicn_token.toString();
        }
        return semicn_token.toString();
    }

}
