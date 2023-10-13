package q9k.buaa.AST.Stmt;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolType;

import java.io.IOException;

public class Stmt1 implements Stmt{
    private Token if_token;
    private Token lparent_token;
    private Syntax cond;
    private Token rparent_token;
    private Syntax stmt1;
    private Token else_token;
    private Syntax stmt2;
    public Stmt1(Token if_token, Token lparent_token, Syntax cond, Token rparent_token, Syntax stmt1, Token else_token, Syntax stmt2) {
        this.if_token = if_token;
        this.lparent_token = lparent_token;
        this.cond = cond;
        this.rparent_token = rparent_token;
        this.stmt1 = stmt1;
        this.else_token = else_token;
        this.stmt2 = stmt2;
    }

    @Override
    public void print() throws IOException {
        if_token.print();
        lparent_token.print();
        cond.print();
        rparent_token.print();
        stmt1.print();
        if(else_token!=null){
            else_token.print();
            stmt2.print();
        }
        printAstName(Stmt.class);
    }

    @Override
    public void visit() {
        cond.visit();
        SymbolTable current = SymbolTable.getCurrent();
        SymbolTable symbolTable = new SymbolTable();
        SymbolTable.changeToTable(symbolTable);
        stmt1.visit();
        SymbolTable.changeToTable(current);
        if(else_token!=null){
            symbolTable = new SymbolTable();
            SymbolTable.changeToTable(symbolTable);
            stmt2.visit();
            SymbolTable.changeToTable(current);
        }
    }

    @Override
    public int getLineNumber() {
        return 0;
    }

}
