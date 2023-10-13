package q9k.buaa.AST;

import q9k.buaa.Symbol.SymbolTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompUnit implements Syntax {
    private List<Syntax> decls = new ArrayList<>();
    private List<Syntax> func_defs = new ArrayList<>();
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
        SymbolTable current = SymbolTable.getCurrent();
        SymbolTable symbolTable = SymbolTable.getGlobal();
        SymbolTable.changeToTable(symbolTable);
        for(Syntax item : decls){
            item.visit();
        }
        for(Syntax item : func_defs){
            item.visit();
        }
        main_func_def.visit();
        SymbolTable.changeToTable(current);
    }


    @Override
    public int getLineNumber() {
        return 0;
    }
}
