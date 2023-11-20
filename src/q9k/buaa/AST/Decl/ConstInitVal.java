package q9k.buaa.AST.Decl;

import q9k.buaa.AST.Syntax;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Token.Token;
import q9k.buaa.Utils.Tuple;

import java.io.IOException;
import java.util.List;

public class ConstInitVal implements Syntax {
    private Syntax const_exp;
    private Token lbrace_token;
    private Syntax const_init_val;
    private List<Tuple<Token, Syntax>> list;
    private Token rbrace_token;

    

    public ConstInitVal(Syntax const_exp, Token lbrace_token, Syntax const_init_val, List<Tuple<Token, Syntax>> list, Token rbrace_token) {
        this.const_exp = const_exp;
        this.lbrace_token = lbrace_token;
        this.const_init_val = const_init_val;
        this.list = list;
        this.rbrace_token = rbrace_token;
    }

    @Override
    public void print() throws IOException {
        if(const_exp != null){
            const_exp.print();
        }
        else{
            lbrace_token.print();
            if(const_init_val != null){
                const_init_val.print();
                for(Tuple<Token, Syntax> item : list){
                    item.first().print();
                    item.second().print();
                }
            }
            rbrace_token.print();
        }
        printAstName(ConstInitVal.class);
    }

    @Override
    public void visit()
    {
        
        if(const_exp != null){
            const_exp.visit();
        }
        else{
            if(const_init_val != null){
                const_init_val.visit();
                for(Tuple<Token, Syntax> item : list){
                    item.second().visit();
                }
            }
        }
    }


    @Override
    public int getLineNumber() {
        if(const_exp!=null){
            return const_exp.getLineNumber();
        }
        else{
            return const_init_val.getLineNumber();
        }
    }

    @Override
    public Value generateIR() {
        if(const_exp!=null){
            return const_exp.generateIR();
        }
        else{
            return null;
        }
    }

    @Override
    public String toString() {
        if(const_exp!=null){
            return const_exp.toString();
        }
        else{
            StringBuilder content = new StringBuilder();
            content.append(lbrace_token.toString());
            if(const_init_val!=null) {
                content.append(const_init_val.toString());
            }
            for(Tuple<Token, Syntax> item : list){
                content.append(item.first().toString()).append(item.second().toString());
            }
            content.append(rbrace_token.toString());
            return content.toString();
        }
    }

}
