package q9k.buaa.Frontend.AST;

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
        handleError();
    }

    @Override
    public void print() {

    }

    @Override
    public void handleError() {

    }
}
