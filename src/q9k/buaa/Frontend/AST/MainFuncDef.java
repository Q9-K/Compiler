package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;

public class MainFuncDef implements Syntax {
    private Token int_token;
    private Token main_token;
    private Token lparent_token;
    private Token rparent_token;
    private Syntax block;

    public MainFuncDef(Token int_token, Token main_token, Token lparent_token, Token rparent_token, Syntax block) {
        this.int_token = int_token;
        this.main_token = main_token;
        this.lparent_token = lparent_token;
        this.rparent_token = rparent_token;
        this.block = block;
        handleError();
    }

    @Override
    public void print() {

    }

    @Override
    public void handleError() {

    }
}
