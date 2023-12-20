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

public class ConstInitVal implements Syntax {
    private Syntax const_exp;
    private Token lbrace;
    private Syntax const_init_val;
    private List<Tuple<Token, Syntax>> list;
    private Token rbrace;
    private SymbolTable symbolTable;


    public ConstInitVal(Syntax const_exp, Token lbrace, Syntax const_init_val, List<Tuple<Token, Syntax>> list, Token rbrace) {
        this.const_exp = const_exp;
        this.lbrace = lbrace;
        this.const_init_val = const_init_val;
        this.list = list;
        this.rbrace = rbrace;
    }

    @Override
    public void print() throws IOException {
        if (const_exp != null) {
            const_exp.print();
        } else {
            lbrace.print();
            if (const_init_val != null) {
                const_init_val.print();
                for (Tuple<Token, Syntax> item : list) {
                    item.first().print();
                    item.second().print();
                }
            }
            rbrace.print();
        }
        printAstName(ConstInitVal.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
        if (const_exp != null) {
            const_exp.visit();
        } else {
            if (const_init_val != null) {
                const_init_val.visit();
                for (Tuple<Token, Syntax> item : list) {
                    item.second().visit();
                }
            }
        }
    }


    @Override
    public int getLineNumber() {
        if (const_exp != null) {
            return const_exp.getLineNumber();
        } else {
            return rbrace.getLineNumber();
        }
    }

    @Override
    public Value genIR() {
        if (const_exp != null) {
            return const_exp.genIR();
        } else {
            if (const_init_val != null) {
                const_init_val.genIR();
                for (Tuple<Token, Syntax> item : list) {
                    item.second().genIR();
                }
            }

        }
        return null;
    }

    @Override
    public String toString() {
        if (const_exp != null) {
            return const_exp.toString();
        } else {
            StringBuilder content = new StringBuilder();
            content.append(lbrace.toString());
            if (const_init_val != null) {
                content.append(const_init_val.toString());
            }
            for (Tuple<Token, Syntax> item : list) {
                content.append(item.first().toString()).append(item.second().toString());
            }
            content.append(rbrace.toString());
            return content.toString();
        }
    }

    public Constant getInitializer() {
        if (const_exp != null) {
            return new ConstantInt(Calculator.getInstance().calculate(const_exp, symbolTable));
        } else if (const_init_val != null) {
            List<Constant> constants = new ArrayList<>();
            Constant constant = ((ConstInitVal) const_init_val).getInitializer();
            constants.add(constant);

            for (Tuple<Token, Syntax> item : list) {
                constant = ((ConstInitVal) item.second()).getInitializer();
                constants.add(constant);
            }
            ConstantArray constantArray = new ConstantArray(constants);
            return constantArray;
        }
        return null;
    }


    public List<Syntax> getConstInitValList() {
        List<Syntax> constInitValList = new ArrayList<>();
        if (const_init_val != null) {
            constInitValList.add(const_init_val);
            for (Tuple<Token, Syntax> item : list) {
                constInitValList.add(item.second());
            }
        }
        return constInitValList;
    }
}
