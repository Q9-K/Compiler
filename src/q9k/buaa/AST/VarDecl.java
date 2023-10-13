package q9k.buaa.AST;

import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Utils.Tuple;

import java.io.IOException;
import java.util.List;

public class VarDecl implements Syntax {
    private Syntax b_type;
    private Syntax var_def;
    private List<Tuple<Token, Syntax>> list;
    private Token semicn_token;

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
            item.getFirst().print();
            item.getSecond().print();
        }
        semicn_token.print();
        printAstName(VarDecl.class);
    }

    @Override
    public void visit() {
        var_def.visit();
        for(Tuple<Token, Syntax> item : list){
            item.getSecond().visit();
        }
    }

    @Override
    public int getLineNumber() {
        if(list.isEmpty()){
            return var_def.getLineNumber();
        }
        else{
            Tuple<Token, Syntax> item = list.get(list.size()-1);
            return item.getSecond().getLineNumber();
        }
    }

}
