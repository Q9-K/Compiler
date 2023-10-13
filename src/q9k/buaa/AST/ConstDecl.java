package q9k.buaa.AST;

import q9k.buaa.Frontend.Token.*;
import q9k.buaa.Utils.Tuple;

import java.io.IOException;
import java.util.List;

public class ConstDecl implements Syntax {
    private Token const_token;
    private Syntax b_type;
    private Syntax const_def;
    private List<Tuple<Token, Syntax>> list;
    private Token semicn_token;

    public ConstDecl(Token const_token, Syntax b_type, Syntax const_def, List<Tuple<Token, Syntax>> list, Token semicn_token) {
        this.const_token = const_token;
        this.b_type = b_type;
        this.const_def = const_def;
        this.list = list;
        this.semicn_token = semicn_token;
    }

    @Override
    public void print() throws IOException {
        const_token.print();
        b_type.print();
        const_def.print();
        for (Tuple<Token, Syntax> item : list) {
            item.getFirst().print();
            item.getSecond().print();
        }
        semicn_token.print();
        printAstName(ConstDecl.class);
    }

    @Override
    public void visit() {
        b_type.visit();
        const_def.visit();
        for (Tuple<Token, Syntax> item : list) {
            item.getSecond().visit();
        }
    }



    @Override
    public int getLineNumber() {
        if(list.isEmpty()){
            return const_def.getLineNumber();
        }
        else{
            Tuple<Token, Syntax> item = list.get(list.size()-1);
            return item.getSecond().getLineNumber();
        }
    }
}
