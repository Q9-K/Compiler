package q9k.buaa.AST.Decl;

import q9k.buaa.AST.Syntax;
import q9k.buaa.IR.Constant;
import q9k.buaa.IR.ConstantArray;
import q9k.buaa.IR.ConstantInt;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;
import q9k.buaa.Token.Token;
import q9k.buaa.Utils.Calculator;
import q9k.buaa.Utils.Tuple;

import java.io.IOException;
import java.util.ArrayList;
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
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
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

    public static int dim = 0;
    public static int pos1 = 0;
    public static int pos2 = 0;

    @Override
    public Value genIR() {
        if (exp != null) {
            return exp.genIR();
        }
        return null;
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

    public Constant getInitializer() {
        if (exp != null) {
            return new ConstantInt(Calculator.getInstance().calculate(exp, symbolTable));
        } else if (init_val != null) {
            List<Constant> constants = new ArrayList<>();
            Constant constant = ((InitVal) init_val).getInitializer();
            constants.add(constant);
            for (Tuple<Token, Syntax> item : list) {
                constant = ((InitVal) item.second()).getInitializer();
                constants.add(constant);
            }
            ConstantArray constantArray = new ConstantArray(constants);
            return constantArray;
        }
        return null;
    }

    public List<Syntax> getInitValList() {
        List<Syntax> initValList = new ArrayList<>();
        if (init_val != null) {
            initValList.add(init_val);
            for (Tuple<Token, Syntax> item : list) {
                initValList.add(item.second());
            }
        }
        return initValList;
    }
}
