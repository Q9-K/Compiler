package q9k.buaa.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Symbol.Symbol;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Token.Token;
import q9k.buaa.Symbol.SymbolType;
import q9k.buaa.Utils.Triple;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class LVal implements Syntax {

    private Syntax ident;
    private List<Triple<Token, Syntax, Token>> list;
    private SymbolTable symbolTable;

    public LVal(Syntax ident, List<Triple<Token, Syntax, Token>> list) {
        this.ident = ident;
        this.list = list;
    }

    @Override
    public void print() throws IOException {
        ident.print();
        for(Triple<Token, Syntax, Token> item : list){
            item.first().print();
            item.second().print();
            item.third().print();
        }
        printAstName(LVal.class);
    }


    @Override
    public void visit() {
        this.symbolTable = SymbolTable.getCurrent();
        SymbolTable.checkVarInvoke(ident);
        for(Triple<Token, Syntax, Token> item : list){
            item.second().visit();
        }
    }

    @Override
    public int getLineNumber() {
       return ident.getLineNumber();
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(ident.toString());
        for(Triple<Token, Syntax, Token> item : list){
            content.append(item.first().toString()).append(item.second().toString()).append(item.third().toString());
        }
        return content.toString();
    }



}
