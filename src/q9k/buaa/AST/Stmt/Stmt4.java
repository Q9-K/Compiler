package q9k.buaa.AST.Stmt;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Symbol.SymbolTable;

import java.io.IOException;

public class Stmt4 implements Stmt{

    Token return_token;
    Syntax exp;
    Token semicn_token;

    public Stmt4(Token return_token, Syntax exp, Token semicn_token) {
        this.return_token = return_token;
        this.exp = exp;
        this.semicn_token = semicn_token;
    }

    @Override
    public void print() throws IOException {
        return_token.print();
        if(exp!=null){
            exp.print();
        }
        semicn_token.print();
        printAstName(Stmt.class);
    }

    @Override
    public void visit() {
        SymbolTable current = SymbolTable.getCurrent();
        if(current.isFunc_block() == 0){
            ErrorHandler.getInstance().addError(new Error(ErrorType.REVERSERROR, getLineNumber()));
        }
        else{
            if(current.isFunc_block() == 1 && exp != null){
                ErrorHandler.getInstance().addError(new Error(ErrorType.EXTRARETURNTYPE,getLineNumber()));
            }
            if(current.isFunc_block() == 2 && exp==null){
                ErrorHandler.getInstance().addError(new Error(ErrorType.REVERSERROR, getLineNumber()));
            }
        }


    }

    @Override
    public int getLineNumber() {
        return return_token.getLineNumber();
    }

}
