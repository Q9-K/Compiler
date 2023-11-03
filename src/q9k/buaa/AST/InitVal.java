package q9k.buaa.AST;

import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Token.Token;
import q9k.buaa.Utils.Tuple;

import java.io.IOException;
import java.util.List;

public class InitVal implements Syntax {
    private Syntax exp;
    private Token lbrace;
    private Syntax init_val;
    private List<Tuple<Token, Syntax>> list;
    private Token rbrace;
    private SymbolTable symbolTable;

    public InitVal(Syntax exp, Token lbrace, Syntax init_val, List<Tuple<Token, Syntax>> list, Token rbrace) {
        this.exp = exp;
        this.lbrace = lbrace;
        this.init_val = init_val;
        this.list = list;
        this.rbrace = rbrace;
    }

    @Override
    public void print() throws IOException {
        if (exp != null) {
            exp.print();
        } else {
            lbrace.print();
            if (init_val != null) {
                init_val.print();
                for (Tuple<Token, Syntax> item : list) {
                    item.first().print();
                    item.second().print();
                }
            }
            rbrace.print();
        }
        printAstName(InitVal.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTable.getCurrent();
        if (exp != null) {
            exp.visit();
        } else {
            if (init_val != null) {
                init_val.visit();
                for (Tuple<Token, Syntax> item : list) {
                    item.second().visit();
                }
            }
        }
    }

    @Override
    public int getLineNumber() {
        if (exp != null) {
            return exp.getLineNumber();
        }
        return rbrace.getLineNumber();
    }

    @Override
    public String toString() {
        if (exp != null) {
            return exp.toString();
        } else {
            StringBuilder content = new StringBuilder();
            content.append(lbrace.toString());
            if (init_val != null) {
                content.append(init_val.toString());
                for (Tuple<Token, Syntax> item : list) {
                    content.append(item.first().toString()).append(item.second().toString());
                }
            }
            content.append(rbrace.toString());
            return content.toString();
        }
    }
}
