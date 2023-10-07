package q9k.buaa.Frontend.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Frontend.Token.TokenType;
import q9k.buaa.INIT.Output;

import java.io.IOException;
import java.util.List;

public class Block implements Syntax {
    private Token lbrace_token;
    private List<Syntax> block_items;
    private Token rbrace_token;

    public Block(Token lbrace_token, List<Syntax> block_items, Token rbrace_token) {
        this.lbrace_token = lbrace_token;
        this.block_items = block_items;
        this.rbrace_token = rbrace_token;
        handleError();
    }

    @Override
    public void print() throws IOException {
        lbrace_token.print();
        for (Syntax block_item : block_items) {
            block_item.print();
        }
        rbrace_token.print();
        print_ast_name(Block.class);
    }

    @Override
    public void handleError() {
        if (!lbrace_token.getTokenType().equals(TokenType.LBRACE)) {
            ErrorHandler.getInstance().addError(new Error(ErrorType.REVERSERROR, lbrace_token.getLine_number()));
        }
        if (!rbrace_token.getTokenType().equals(TokenType.RBRACE)) {
            ErrorHandler.getInstance().addError(new Error(ErrorType.REVERSERROR, rbrace_token.getLine_number()));
        }
    }
}
