package q9k.buaa.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;

import java.io.IOException;

public class BType implements Syntax {
    private Token int_token;
    public BType(Token int_token){
        this.int_token = int_token;
    }
    @Override
    public void print() throws IOException {
        int_token.print();
    }

    @Override
    public void visit() {
    }



    @Override
    public int getLineNumber() {
        return int_token.getLineNumber();
    }
}
