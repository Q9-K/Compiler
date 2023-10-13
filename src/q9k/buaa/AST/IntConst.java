package q9k.buaa.AST;

import q9k.buaa.Frontend.Token.Token;

import java.io.IOException;

public class IntConst implements Syntax {

    private Token intcon_token;

    public IntConst(Token intcon_token) {
        this.intcon_token = intcon_token;
    }

    @Override
    public void print() throws IOException {
        intcon_token.print();
    }

    @Override
    public void visit() {

    }

    @Override
    public int getLineNumber() {
        return intcon_token.getLineNumber();
    }

}
