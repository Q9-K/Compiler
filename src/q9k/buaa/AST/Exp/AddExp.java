package q9k.buaa.AST.Exp;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.Instructions.BinaryOperator;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;
import q9k.buaa.Token.Token;

import java.io.IOException;

public class AddExp implements Syntax {
    private Syntax mul_exp;
    private Token op_token;
    private Syntax add_exp;
    private Value pre_value;
    private SymbolTable symbolTable;


    public AddExp(Syntax mul_exp, Token op_token, Syntax add_exp) {
        this.mul_exp = mul_exp;
        this.op_token = op_token;
        this.add_exp = add_exp;
    }


    ////加减表达式 AddExp → MulExp | AddExp ('+' | '−') MulExp
    //    改为 AddExp → MulExp | MulExp ('+' | '−') AddExp
    @Override
    public void print() throws IOException {
        mul_exp.print();
        printAstName(AddExp.class);
        if (op_token != null) {
            op_token.print();
            add_exp.print();
        }
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
        mul_exp.visit();
        if (op_token != null) {
            add_exp.visit();
        }
    }

    @Override
    public int getLineNumber() {
        if (op_token == null) {
            return mul_exp.getLineNumber();
        }
        return add_exp.getLineNumber();
    }

    @Override
    public Value generateIR() {
        if (op_token == null) {
            return mul_exp.generateIR();
        } else {
            Value left;
            if (this.pre_value == null) {
                left = mul_exp.generateIR();
            } else {
                left = pre_value;
            }
            AddExp temp = (AddExp) add_exp;
            if (temp.op_token == null) {
                BinaryOperator binaryOperator = new BinaryOperator(left, temp.generateIR(), op_token.getTokenType());
                IRGenerator.getCurBasicBlock().addInstruction(binaryOperator);
                return binaryOperator;
            } else {
                BinaryOperator binaryOperator = new BinaryOperator(left, temp.mul_exp.generateIR(), op_token.getTokenType());
                temp.pre_value = binaryOperator;
                IRGenerator.getCurBasicBlock().addInstruction(binaryOperator);
                return temp.generateIR();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(mul_exp.toString());
        if (op_token != null) {
            content.append(op_token.toString()).append(add_exp.toString());
        }
        return content.toString();
    }



}
