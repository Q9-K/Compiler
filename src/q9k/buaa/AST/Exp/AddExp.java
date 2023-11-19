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

public class AddExp implements Syntax {
    private Syntax mul_exp;
    private Token op_token;
    private Syntax add_exp;
    private Value pre_value;
    

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
        if(op_token!=null){
            op_token.print();
            add_exp.print();
        }
    }

    @Override
    public void visit() {
        
        mul_exp.visit();
        if(op_token!=null){
            add_exp.visit();
        }
    }

    @Override
    public int getLineNumber() {
        if(op_token==null){
            return mul_exp.getLineNumber();
        }
        return add_exp.getLineNumber();
    }

    @Override
    public Value generateIR() {
        if(op_token==null){
            return mul_exp.generateIR();
        }
        else{
            Instruction instruction = new BinaryOperator(null, IntegerType.i32);
            instruction.setOpcode(op_token);
            Value value_1;
            if(this.pre_value==null){
                value_1= mul_exp.generateIR();
            }
            else{
                value_1 = pre_value;
            }
            AddExp temp = (AddExp) add_exp;
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
                value_2 = temp.mul_exp.generateIR();
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
        content.append(mul_exp.toString());
        if(op_token!=null){
            content.append(op_token.toString()).append(add_exp.toString());
        }
        return content.toString();
    }

    public void setPre_value(Value pre_value){
        this.pre_value = pre_value;
    }


}
