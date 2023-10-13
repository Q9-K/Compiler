package q9k.buaa.AST.Stmt;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Frontend.Token.TokenType;
import q9k.buaa.Symbol.SymbolTable;

import java.io.IOException;

public class Stmt3 implements Stmt{
    private Token action_token;
    private Token semicn_token;

    public Stmt3(Token action_token, Token semicn_token) {
        this.action_token = action_token;
        this.semicn_token = semicn_token;
    }

    @Override
    public void print() throws IOException {
        action_token.print();
        semicn_token.print();
        printAstName(Stmt.class);
    }

    @Override
    public void visit() {
        SymbolTable current = SymbolTable.getCurrent();
        if(!current.isFor_block()){
            ErrorHandler.getInstance().addError(new Error(ErrorType.USINGCYCLEBC,getLineNumber()));
        }
    }

    @Override
    public int getLineNumber() {
        return action_token.getLineNumber();
    }

}
