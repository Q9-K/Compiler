package q9k.buaa.AST;

import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;

import java.io.IOException;
import java.util.List;

public class CompUnit implements Syntax {
    private List<Syntax> decls;
    private List<Syntax> func_defs;
    private Syntax main_func_def;
    

    public CompUnit(List<Syntax> decls, List<Syntax> func_defs, Syntax main_func_def) {
        this.decls = decls;
        this.func_defs = func_defs;
        this.main_func_def = main_func_def;
    }

    @Override
    public void print() throws IOException {
        for (Syntax decl : decls) {
            decl.print();
        }
        for (Syntax func_def : func_defs) {
            func_def.print();
        }
        main_func_def.print();
        printAstName(CompUnit.class);
    }

    @Override
    public void visit() {
        for (Syntax item : decls) {
            item.visit();
        }
        for (Syntax item : func_defs) {
            item.visit();
        }
        main_func_def.visit();
    }


    @Override
    public int getLineNumber() {
        return 1;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        for (Syntax item : decls) {
            content.append(item.toString());
        }
        for (Syntax item : func_defs) {
            content.append(item.toString());
        }
        content.append(main_func_def.toString());
        return content.toString();
    }

    @Override
    public Value generateIR() {
        IRGenerator.setGlobal(true);
        for (Syntax item : decls) {
            item.generateIR();
        }
        IRGenerator.setGlobal(false);
        for(Syntax item: func_defs){
            item.generateIR();
        }
        main_func_def.generateIR();
        return null;
    }
}
