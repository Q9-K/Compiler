package q9k.buaa.AST;

import java.io.IOException;

public class Decl implements Syntax {
    private Syntax const_decl;
    private Syntax var_decl;

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
}
