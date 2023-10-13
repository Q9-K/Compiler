package q9k.buaa.AST;

import q9k.buaa.Frontend.Token.Token;
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
            item.getFirst().print();
            item.getSecond().print();
            item.getThird().print();
        }
        if(assign_token!=null){
            assign_token.print();
            init_val.print();
        }
        printAstName(VarDef.class);
    }

    @Override
    public void visit() {
        Ident temp = (Ident) ident;
        if(temp.visitDef()){
            if(list.isEmpty()){
                SymbolTable.addSymbol(new VarSymbol(getContent()));
            }
            else if(list.size() == 1){
                SymbolTable.addSymbol(new ArraySymbol(getContent(),list.get(0).getSecond(),null));
            }
            else {
                SymbolTable.addSymbol(new ArraySymbol(getContent(),list.get(0).getSecond(),list.get(1).getSecond()));
            }
        }
        for(Triple<Token, Syntax, Token> item : list){
            item.getSecond().visit();
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
                return item.getThird().getLineNumber();
            }
        }
    }
    private String getContent(){
        return ((Ident)ident).getTokenContent();
    }
}
