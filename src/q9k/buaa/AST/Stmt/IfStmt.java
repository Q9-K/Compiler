package q9k.buaa.AST.Stmt;

import q9k.buaa.AST.Syntax;
import q9k.buaa.IR.Value;
import q9k.buaa.Token.Token;
import q9k.buaa.Symbol.SymbolTable;

import java.io.IOException;

public class IfStmt implements Stmt{
    private Token if_token;
    private Token lparent_token;
    private Syntax cond;
    private Token rparent_token;
    private Syntax stmt1;
    private Token else_token;
    private Syntax stmt2;
    
    public IfStmt(Token if_token, Token lparent_token, Syntax cond, Token rparent_token, Syntax stmt1, Token else_token, Syntax stmt2) {
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
        SymbolTable.changeTo(SymbolTable.getCurrent().createSymbolTable());
        stmt1.visit();
        SymbolTable.changeToFather();
        if(else_token!=null){
            SymbolTable.changeTo(SymbolTable.getCurrent().createSymbolTable());
            stmt2.visit();
            SymbolTable.changeToFather();
        }
    }

    @Override
    public int getLineNumber() {
        return 0;
    }

    @Override
    public Value generateIR() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(if_token.toString()).append(lparent_token.toString())
                .append(cond.toString()).append(rparent_token.toString()).append(stmt1.toString());
        if(else_token!=null){
            content.append(else_token.toString()).append(stmt2.toString());
        }
        return content.toString();
    }

}
