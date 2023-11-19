package q9k.buaa.AST.Stmt;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.IR.Value;
import q9k.buaa.Token.Token;
import q9k.buaa.Symbol.SymbolTable;

import java.io.IOException;

public class ActionStmt implements Stmt{
    private Token action_token;
    private Token semicn_token;
    

    public ActionStmt(Token action_token, Token semicn_token) {
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

    @Override
    public Value generateIR() {
        return null;
    }

    @Override
    public String toString() {
        return action_token.toString()+semicn_token.toString();
    }


}
