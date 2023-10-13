package q9k.buaa.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Symbol.Symbol;
import q9k.buaa.Symbol.SymbolType;
import q9k.buaa.Utils.Triple;

import java.io.IOException;
import java.util.List;

public class LVal implements Syntax {

    private Syntax ident;
    private List<Triple<Token, Syntax, Token>> list;

    public LVal(Syntax ident, List<Triple<Token, Syntax, Token>> list) {
        this.ident = ident;
        this.list = list;
    }

    @Override
    public void print() throws IOException {
        ident.print();
        for(Triple<Token, Syntax, Token> item : list){
            item.getFirst().print();
            item.getSecond().print();
            item.getThird().print();
        }
        printAstName(LVal.class);
    }

    private boolean has_def;
    @Override
    public void visit() {
        Ident temp = (Ident) ident;
        has_def = temp.visitInvoke();
        if(has_def){
            if(temp.getSymbolType().equals(SymbolType.FUNCTION)){
                ErrorHandler.getInstance().addError(new Error(ErrorType.NOTDEFNAME, getLineNumber()));
            }
        }
        for(Triple<Token, Syntax, Token> item : list){
            item.getSecond().visit();
        }
    }

    public void visitConst(){
        if(has_def){
            if(isConst()){
                ErrorHandler.getInstance().addError(new Error(ErrorType.CHANGECONST,getLineNumber()));
            }
        }
    }

    @Override
    public int getLineNumber() {
       return ident.getLineNumber();
    }


    public boolean isConst(){
        Symbol symbol = ((Ident)ident).searchSymBol();
        return symbol.isConst();
    }

    public SymbolType getSymbolType(){
        Symbol symbol = ((Ident)ident).searchSymBol();
        SymbolType symbolType = symbol.getSymbolType();
        if(list.isEmpty()){
            return symbolType;
        }
        else if(list.size()==1){
            if(symbolType.equals(SymbolType.ARRAY)){
                return SymbolType.VAR;
            }
            else if(symbolType.equals(SymbolType.MULTIARRAY)){
                return SymbolType.ARRAY;
            }
            return symbolType;
        }
        else{
            if(symbolType.equals(SymbolType.MULTIARRAY)){
                return SymbolType.VAR;
            }
            return null;
        }
    }

}
