package q9k.buaa.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;
import q9k.buaa.Token.Token;
import q9k.buaa.Token.TokenType;

import java.io.IOException;
import java.util.List;

public class Block implements Syntax {
    private Token lbrace_token;
    private List<Syntax> block_items;
    private Token rbrace_token;
    private SymbolTable symbolTable;

    public Block(Token lbrace_token, List<Syntax> block_items, Token rbrace_token) {
        this.lbrace_token = lbrace_token;
        this.block_items = block_items;
        this.rbrace_token = rbrace_token;
    }

    @Override
    public void print() throws IOException {
        lbrace_token.print();
        for (Syntax block_item : block_items) {
            block_item.print();
        }
        rbrace_token.print();
        printAstName(Block.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
        for (Syntax item : block_items) {
            item.visit();
        }
    }

    public void checkReturn() {
        if (SymbolTableFactory.getInstance().getCurrent().getFuncBlock() == 2) {
            if(block_items.isEmpty()||!block_items.get(block_items.size()-1).toString().startsWith(TokenType.RETURNTK.getName())){
                ErrorHandler.getInstance().addError(new Error(ErrorType.LACKOFRETURN, getLineNumber()));
            }
        }
    }

    @Override
    public int getLineNumber() {
        return rbrace_token.getLineNumber();
    }
    @Override
    public Value genIR() {
        for (Syntax block_item : block_items) {
            block_item.genIR();
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(lbrace_token.toString());
        for (Syntax block_item : block_items) {
            content.append(block_item.toString());
        }
        content.append(rbrace_token.toString());
        return content.toString();
    }
}
