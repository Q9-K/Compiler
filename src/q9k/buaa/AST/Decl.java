package q9k.buaa.AST;

import q9k.buaa.Symbol.SymbolTable;

import java.io.IOException;

public class Decl implements Syntax {
    private Syntax const_decl;
    private Syntax var_decl;

    private SymbolTable symbolTable;

    public Decl(Syntax const_decl, Syntax var_decl) {
        this.const_decl = const_decl;
        this.var_decl = var_decl;
    }

    @Override
    public void print() throws IOException {
        if(const_decl != null){
            const_decl.print();
        }
        else{
            var_decl.print();
        }
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTable.getCurrent();
        if(const_decl != null){
            const_decl.visit();
        }
        else{
            var_decl.visit();
        }
    }


    @Override
    public int getLineNumber() {
        if(var_decl!=null){
            return var_decl.getLineNumber();
        }
        else{
            return const_decl.getLineNumber();
        }
    }

    @Override
    public String toString() {
        if(var_decl!=null){
            return var_decl.toString();
        }
        return const_decl.toString();
    }

}
