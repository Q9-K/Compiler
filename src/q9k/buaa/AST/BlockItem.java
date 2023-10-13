package q9k.buaa.AST;

import java.io.IOException;

public class BlockItem implements Syntax {
    private Syntax decl;
    private Syntax stmt;

    public BlockItem(Syntax decl, Syntax stmt) {
        this.decl = decl;
        this.stmt = stmt;
    }

    @Override
    public void print() throws IOException {
        if (decl != null) {
            decl.print();
        } else {
            stmt.print();
        }
    }

    @Override
    public void visit() {
        if(decl != null){
            decl.visit();
        }
        else{
            stmt.visit();
        }
    }


    @Override
    public int getLineNumber() {
        if(decl!=null){
            return decl.getLineNumber();
        }
        else{
            return stmt.getLineNumber();
        }
    }

    public Class<?> getStmtType(){
        if(stmt!=null){
            return stmt.getClass();
        }
        else{
            return decl.getClass();
        }
    }
}
