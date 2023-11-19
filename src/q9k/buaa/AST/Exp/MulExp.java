package q9k.buaa.AST.Exp;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.Instructions.BinaryOperator;
import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Token.Token;

import java.io.IOException;

public class MulExp implements Syntax {
    private Syntax unary_exp;
    private Token op_token;
    private Syntax mul_exp;
    
    private Value pre_value;

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
    public Value generateIR() {
        if(op_token == null){
            return unary_exp.generateIR();
        }
        else {
            Instruction instruction = new BinaryOperator(null, IntegerType.i32);
            instruction.setOpcode(op_token);
            Value value_1;
            if(this.pre_value==null){
                value_1 = unary_exp.generateIR();
            }
            else{
                value_1 = pre_value;
            }
            MulExp temp = (MulExp) mul_exp;
            Value value_2;
            if(temp.op_token==null){
                value_2 = temp.generateIR();
                instruction.addOperand(value_1);
                instruction.addOperand(value_2);
                IRGenerator.getCurBasicBlock().addInstruction(instruction);
                return instruction;
            }
            else{
                temp.setPre_value(instruction);
                value_2 = temp.unary_exp.generateIR();
                instruction.addOperand(value_1);
                instruction.addOperand(value_2);
                IRGenerator.getCurBasicBlock().addInstruction(instruction);
                return temp.generateIR();
            }
        }
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

    public void setPre_value(Value pre_value) {
        this.pre_value = pre_value;
    }
}
