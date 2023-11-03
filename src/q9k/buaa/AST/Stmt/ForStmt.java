package q9k.buaa.AST.Stmt;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Token.Token;
import q9k.buaa.Symbol.SymbolTable;

import java.io.IOException;

public class ForStmt implements Stmt {
    private Token for_token;
    private Token lparent_token;
    private Syntax for_stmt1;
    private Token semicn_token1;
    private Syntax cond;
    private Token semicn_token2;
    private Syntax for_stmt2;
    private Token rparent_token;
    private Syntax stmt;
    private SymbolTable symbolTable;

    public ForStmt(Token for_token, Token lparent_token, Syntax for_stmt1, Token semicn_token1, Syntax cond, Token semicn_token2, Syntax for_stmt2, Token rparent_token, Syntax stmt) {
        this.for_token = for_token;
        this.lparent_token = lparent_token;
        this.for_stmt1 = for_stmt1;
        this.semicn_token1 = semicn_token1;
        this.cond = cond;
        this.semicn_token2 = semicn_token2;
        this.for_stmt2 = for_stmt2;
        this.rparent_token = rparent_token;
        this.stmt = stmt;
    }

    @Override
    public void print() throws IOException {
        for_token.print();
        lparent_token.print();
        if(for_stmt1!=null){
            for_stmt1.print();
        }
        semicn_token1.print();
        if(cond!=null){
            cond.print();
        }
        semicn_token2.print();
        if(for_stmt2!=null){
            for_stmt2.print();
        }
        rparent_token.print();
        stmt.print();
        printAstName(Stmt.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTable.getCurrent();
        if(for_stmt1!=null){
            for_stmt1.visit();
        }
        if(cond!=null){
            cond.visit();
        }
        if(for_stmt2!=null){
            for_stmt2.visit();
        }
        SymbolTable.changeTo(SymbolTable.getCurrent().createSymbolTable());
        SymbolTable.getCurrent().setFor_block(true);
        stmt.visit();
        SymbolTable.changeToFather();
    }

    @Override
    public int getLineNumber() {
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(for_token.toString()).append(lparent_token.toString());
        if(for_stmt1!=null){
            content.append(for_stmt1.toString());
        }
        content.append(semicn_token1.toString());
        if(cond!=null){
            content.append(cond.toString());
        }
        content.append(semicn_token2.toString());
        if(for_stmt2!=null){
            content.append(for_stmt2.toString());
        }
        content.append(rparent_token.toString()).append(stmt.toString());
        return content.toString();
    }

}
