package q9k.buaa.AST.Decl;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;
import q9k.buaa.Token.Token;
import q9k.buaa.Token.TokenType;
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
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
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

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(b_type.toString()).append(' ').append(var_def.toString());
        for(Tuple<Token, Syntax> item : list){
            content.append(item.first().toString()).append(item.second().toString());
        }
        content.append(semicn_token.toString());
        return content.toString();
    }

    @Override
    public Value genIR() {
        if(TokenType.getTokenType(b_type.toString()).equals(TokenType.INTCON)){
            IRGenerator.setCur_type(IntegerType.i32);
        }
        var_def.genIR();
        for(Tuple<Token, Syntax> item:list){
            item.second().genIR();
        }
        return null;
    }
}
