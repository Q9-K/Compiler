package q9k.buaa.AST.Decl;

import q9k.buaa.AST.Syntax;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;
import q9k.buaa.Token.*;
import q9k.buaa.Utils.Tuple;

import java.io.IOException;
import java.util.List;

public class ConstDecl implements Syntax {
    private Token const_token;
    private Syntax b_type;
    private Syntax const_def;
    private List<Tuple<Token, Syntax>> list;
    private Token semicn_token;
    private SymbolTable symbolTable;
    

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
            item.first().print();
            item.second().print();
        }
        semicn_token.print();
        printAstName(ConstDecl.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
        b_type.visit();
        const_def.visit();
        for (Tuple<Token, Syntax> item : list) {
            item.second().visit();
        }
    }



    @Override
    public int getLineNumber() {
        if(list.isEmpty()){
            return const_def.getLineNumber();
        }
        else{
            Tuple<Token, Syntax> item = list.get(list.size()-1);
            return item.second().getLineNumber();
        }
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(b_type.toString());
        content.append(const_def.toString());
        for (Tuple<Token, Syntax> item : list) {
            content.append(item.first().toString()).append(item.second().toString());
        }
        content.append(semicn_token.toString());
        return content.toString();
    }

    @Override
    public Value genIR() {
        const_def.genIR();
        for (Tuple<Token, Syntax> item : list) {
            item.second().genIR();
        }
        return null;
    }
}
