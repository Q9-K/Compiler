package q9k.buaa.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Symbol.ArraySymbol;
import q9k.buaa.Symbol.Symbol;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.VarSymbol;
import q9k.buaa.Utils.Triple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConstDef implements Syntax {
    private Syntax ident;
    private List<Triple<Token, Syntax,Token>> list = new ArrayList<>();
    private Token assign_token;
    private Syntax const_init_val;


    public ConstDef(Syntax ident, List<Triple<Token, Syntax, Token>> list, Token assign_token, Syntax const_init_val) {
        this.ident = ident;
        this.list = list;
        this.assign_token = assign_token;
        this.const_init_val = const_init_val;
    }

    @Override
    public void print() throws IOException {
        ident.print();
        for(Triple<Token, Syntax, Token> item : list){
            item.getFirst().print();
            item.getSecond().print();
            item.getThird().print();
        }
        assign_token.print();
        const_init_val.print();
        printAstName(ConstDef.class);
    }

    @Override
    public void visit() {
        Ident temp = (Ident) ident;
        if(temp.visitDef()){
            Symbol symbol;
            if(list.isEmpty()){
                symbol = new VarSymbol(getContent());
            }
            else if(list.size() == 1){
                symbol = new ArraySymbol(getContent(),list.get(0).getSecond(),null);
            }
            else {
                symbol = new ArraySymbol(getContent(),list.get(0).getSecond(),list.get(1).getSecond());
            }
            symbol.setConst(true);
            SymbolTable.addSymbol(symbol);
        }
        for(Triple<Token, Syntax, Token> item : list){
            item.getSecond().visit();
        }
        const_init_val.visit();
    }


    @Override
    public int getLineNumber() {
        return const_init_val.getLineNumber();
    }

    private String getContent(){
        return ((Ident)ident).getTokenContent();
    }
}
