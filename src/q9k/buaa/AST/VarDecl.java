package q9k.buaa.AST;

import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Token.Token;
import q9k.buaa.Utils.Tuple;

import java.io.IOException;
import java.util.List;

public class VarDecl implements Syntax {
    private Syntax b_type;
    private Syntax var_def;
    private List<Tuple<Token, Syntax>> list;
    private Token semicn_token;

    private SymbolTable symbolTable;

    public VarDecl(Syntax b_type, Syntax var_def, List<Tuple<Token, Syntax>> list, Token semicn_token) {
        this.b_type = b_type;
        this.var_def = var_def;
        this.list = list;
        this.semicn_token = semicn_token;
    }

    @Override
    public void print() throws IOException {
        b_type.print();
        var_def.print();
        for(Tuple<Token, Syntax> item : list){
            item.first().print();
            item.second().print();
        }
        semicn_token.print();
        printAstName(VarDecl.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTable.getCurrent();
        var_def.visit();
        for(Tuple<Token, Syntax> item : list){
            item.second().visit();
        }
    }

    @Override
    public int getLineNumber() {
        if(list.isEmpty()){
            return var_def.getLineNumber();
        }
        else{
            Tuple<Token, Syntax> item = list.get(list.size()-1);
            return item.second().getLineNumber();
        }
    }

}
