package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;

import java.io.IOException;

public class Ident implements Syntax{
    private Token ident_token;

    public Ident(Token ident_token) {
        this.ident_token = ident_token;
        handleError();
    }

    @Override
    public void print() throws IOException {
        ident_token.print();
    }
    //TODO:
    //identifier → identifier-nondigit
    //| identifier identifier-nondigit
    //| identifier digit
    //其中，identifier-nondigit为下划线或大小写字母，digit为0到9的数字
    @Override
    public void handleError() {

    }
}
