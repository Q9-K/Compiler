package q9k.buaa.AST.Exp;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Token.Token;

import java.io.IOException;

public class MulExp implements Syntax {
    private Syntax unary_exp;
    private Token op_token;
    private Syntax mul_exp;
    private SymbolTable symbolTable;

    public MulExp(Syntax unary_exp, Token op_token, Syntax mul_exp) {
        this.unary_exp = unary_exp;
        this.op_token = op_token;
        this.mul_exp = mul_exp;
    }

    @Override
    public void print() throws IOException {
        unary_exp.print();
        printAstName(MulExp.class);
        if (op_token != null) {
            op_token.print();
            mul_exp.print();
        }
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTable.getCurrent();
        unary_exp.visit();
        if (mul_exp != null) {
            mul_exp.visit();
        }
    }

    @Override
    public int getLineNumber() {
        if (op_token == null) {
            return unary_exp.getLineNumber();
        }
        return mul_exp.getLineNumber();
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(unary_exp.toString());
        if (op_token != null) {
            content.append(op_token.toString()).append(mul_exp.toString());
        }
        return content.toString();
    }
}
