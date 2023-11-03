package q9k.buaa.AST;

import q9k.buaa.Token.Token;
import q9k.buaa.Symbol.ArraySymbol;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.VarSymbol;
import q9k.buaa.Utils.Triple;
import java.io.IOException;
import java.util.List;

public class VarDef implements Syntax {
    private Syntax ident;
    private List<Triple<Token, Syntax, Token>> list;
    private Token assign_token;
    private Syntax init_val;
    private SymbolTable symbolTable;

    public VarDef(Syntax ident, List<Triple<Token, Syntax, Token>> list, Token assign_token, Syntax init_val) {
        this.ident = ident;
        this.list = list;
        this.assign_token = assign_token;
        this.init_val = init_val;
    }

    @Override
    public void print() throws IOException {
        ident.print();
        for(Triple<Token, Syntax, Token> item : list){
            item.first().print();
            item.second().print();
            item.third().print();
        }
        if(assign_token!=null){
            assign_token.print();
            init_val.print();
        }
        printAstName(VarDef.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTable.getCurrent();
        if(SymbolTable.checkDef(ident)){
            if(list.isEmpty()){
                SymbolTable.getCurrent().addSymbol(new VarSymbol(ident.toString()));
            }
            else if(list.size() == 1){
                SymbolTable.getCurrent().addSymbol(new ArraySymbol(ident.toString(),list.get(0).second(),null));
            }
            else {
                SymbolTable.getCurrent().addSymbol(new ArraySymbol(ident.toString(),list.get(0).second(),list.get(1).second()));
            }
        }
        for(Triple<Token, Syntax, Token> item : list){
            item.second().visit();
        }
        if(init_val!=null){
            init_val.visit();
        }
    }

    @Override
    public int getLineNumber() {
        if(init_val!=null){
            return init_val.getLineNumber();
        }
        else{
            if(list.isEmpty()){
                return ident.getLineNumber();
            }
            else{
                Triple<Token, Syntax, Token> item = list.get(list.size()-1);
                return item.third().getLineNumber();
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(ident.toString());
        for(Triple<Token, Syntax, Token> item : list){
            content.append(item.first().toString()).append(item.second().toString()).append(item.third().toString());
        }
        if(assign_token!=null){
            content.append(assign_token.toString()).append(init_val.toString());
        }
        return content.toString();
    }

}
