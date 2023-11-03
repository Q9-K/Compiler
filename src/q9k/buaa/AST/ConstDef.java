package q9k.buaa.AST;

import q9k.buaa.Token.Token;
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
    private SymbolTable symbolTable;


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
            item.first().print();
            item.second().print();
            item.third().print();
        }
        assign_token.print();
        const_init_val.print();
        printAstName(ConstDef.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTable.getCurrent();
        if(SymbolTable.checkDef(ident)){
            Symbol symbol;
            if(list.isEmpty()){
                symbol = new VarSymbol(ident.toString());
            }
            else if(list.size() == 1){
                symbol = new ArraySymbol(ident.toString(),list.get(0).second(),null);
            }
            else {
                symbol = new ArraySymbol(ident.toString(),list.get(0).second(),list.get(1).second());
            }
            symbol.setConst(true);
            SymbolTable.getCurrent().addSymbol(symbol);
        }
        for(Triple<Token, Syntax, Token> item : list){
            item.second().visit();
        }
        const_init_val.visit();
    }


    @Override
    public int getLineNumber() {
        return const_init_val.getLineNumber();
    }


    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(ident.toString());
        for(Triple<Token, Syntax, Token> item : list){
            content.append(item.first().toString()).append(item.second().toString()).append(item.third().toString());
        }
        content.append(assign_token.toString()).append(const_init_val.toString());
        return content.toString();
    }



}
