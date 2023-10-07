package q9k.buaa.Frontend.AST;

import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Utils.Tuple;

import java.io.IOException;
import java.util.List;

public class Stmt5 implements Stmt{
    private Token printf_token;
    private Token lparent_token;
    private Syntax format_string;
    private List<Tuple<Token,Syntax>> list;
    private Token rparent_token;
    private Token semicn_token;

    public Stmt5(Token printf_token, Token lparent_token, Syntax format_string, List<Tuple<Token, Syntax>> list, Token rparent_token, Token semicn_token) {
        this.printf_token = printf_token;
        this.lparent_token = lparent_token;
        this.format_string = format_string;
        this.list = list;
        this.rparent_token = rparent_token;
        this.semicn_token = semicn_token;
        handleError();
    }

    @Override
    public void print() throws IOException {
        printf_token.print();
        lparent_token.print();
        format_string.print();
        for(Tuple<Token, Syntax> item : list){
            item.getFirst().print();
            item.getSecond().print();
        }
        rparent_token.print();
        semicn_token.print();
        print_ast_name(Stmt.class);

    }

    @Override
    public void handleError() {

    }
}
