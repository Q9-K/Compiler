package q9k.buaa.Frontend.AST;

import java.io.IOException;

public class Decl implements Syntax {
    private Syntax const_decl;
    private Syntax var_decl;

    public Decl(Syntax const_decl, Syntax var_decl) {
        this.const_decl = const_decl;
        this.var_decl = var_decl;
        handleError();
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
    public void handleError() {

    }
}
