package q9k.buaa.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Symbol.FuncSymbol;
import q9k.buaa.Symbol.Symbol;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolType;

import java.io.IOException;

public class Ident implements Syntax {
    private Token ident_token;

    public Ident(Token ident_token) {
        this.ident_token = ident_token;
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
    public void visit() {

    }

    public boolean visitDef(){
        Symbol symbol = SymbolTable.getSymbolInCurrent(getTokenContent());
        if(symbol != null){
            ErrorHandler.getInstance().addError(new Error(ErrorType.REPEAEDNAME, getLineNumber()));
            return false;
        }
        else{
            return true;
        }
    }

    public boolean visitInvoke(){

        Symbol symbol = SymbolTable.getSymbol(getTokenContent());
        if(symbol == null){
            ErrorHandler.getInstance().addError(new Error(ErrorType.NOTDEFNAME, getLineNumber()));
            return false;
        }
        return true;
    }

    public SymbolType getSymbolType(){
        Symbol symbol = SymbolTable.getSymbol(getTokenContent());
        if(symbol!=null){
            return symbol.getSymbolType();
        }
        return null;
    }

    public int getFuncParamNum(){
        Symbol symbol = SymbolTable.getSymbol(ident_token.getContent());
        FuncSymbol funcSymbol = (FuncSymbol) symbol;
        if (funcSymbol != null) {
            return funcSymbol.getParam_num();
        }
        return 0;
    }

    @Override
    public int getLineNumber() {
        return ident_token.getLineNumber();
    }

    public Symbol searchSymBol(){
        SymbolTable current = SymbolTable.getCurrent();
        return current.getSymbol(ident_token.getContent());
    }
    public Token getToken(){
        return ident_token;
    }
    public String getTokenContent(){
        return ident_token.getContent();
    }
}
