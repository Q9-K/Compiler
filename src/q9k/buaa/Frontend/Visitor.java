package q9k.buaa.Frontend;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Symbol.SymbolTable;

public class Visitor {
    private Syntax ast;
    private static Visitor visitor;

    public Visitor(Syntax ast) {
        this.ast = ast;
    }

    public static Visitor getInstance() {
        if (visitor == null) {
            System.out.println("Something wrong happened at visitor init!");
            System.exit(-1);
        }
        return visitor;
    }

    public static Visitor getInstance(Syntax root) {
        if (visitor == null) {
            visitor = new Visitor(root);
        } else if (!visitor.ast.equals(root)) {
            visitor = new Visitor(root);
        }
        return visitor;
    }

    public void run() {
        SymbolTable symbolTable = SymbolTable.getGlobal();
        SymbolTable.changeTo(symbolTable);
        ast.visit();
        System.out.println("Visitor analyze finished!");
    }
}
