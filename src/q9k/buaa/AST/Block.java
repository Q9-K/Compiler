package q9k.buaa.AST;

import q9k.buaa.AST.Stmt.Stmt4;
import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Symbol.SymbolTable;

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
        for(Syntax item : block_items){
            item.visit();
        }

    }

    public void visitReturn(){
        if(SymbolTable.getCurrent().isFunc_block()==2){
            Syntax syntax = null;
            if(!block_items.isEmpty()){
                syntax = block_items.get(block_items.size()-1);
            }
            if(syntax == null || !((BlockItem)syntax).getStmtType().equals(Stmt4.class)){
                ErrorHandler.getInstance().addError(new Error(ErrorType.LACKOFRETURN,getLineNumber()));
            }
        }
    }

    @Override
    public int getLineNumber() {
        return rbrace_token.getLineNumber();
    }
}
