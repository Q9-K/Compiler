package q9k.buaa.AST.Exp;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.Instructions.BranchInst;
import q9k.buaa.IR.Instructions.IcmpInst;
import q9k.buaa.IR.Instructions.ResizeInst;
import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;
import q9k.buaa.Token.Token;

import java.io.IOException;

public class EqExp implements Syntax {
    private Syntax rel_exp;
    private Token op_token;
    private Syntax eq_exp;
    private Value pre_value;
    private SymbolTable symbolTable;


    public EqExp(Syntax rel_exp, Token op_token, Syntax eq_exp) {
        this.rel_exp = rel_exp;
        this.op_token = op_token;
        this.eq_exp = eq_exp;
    }

    @Override
    public void print() throws IOException {
        rel_exp.print();
        printAstName(EqExp.class);
        if (op_token != null) {
            op_token.print();
            eq_exp.print();
        }
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
        rel_exp.visit();
        if (eq_exp != null) {
            eq_exp.visit();
        }
    }


    @Override
    public int getLineNumber() {
        if (op_token == null) {
            return rel_exp.getLineNumber();
        }
        return eq_exp.getLineNumber();
    }

    @Override
    public Value generateIR() {
        if (op_token != null) {
            Value left;
            if(this.pre_value == null){
                left= rel_exp.generateIR();
            }
            else{
                left = pre_value;
            }
            if(left.getType().equals(IntegerType.i1)){
                ResizeInst resizeInst = new ResizeInst(left, IntegerType.i32, "zext");
                IRGenerator.getCurBasicBlock().addInstruction(resizeInst);
                left = resizeInst;
            }
            EqExp temp = (EqExp) eq_exp;
            if(temp.op_token ==null){
                Value right = temp.generateIR();
                if(right.getType().equals(IntegerType.i1)){
                    ResizeInst resizeInst = new ResizeInst(right, IntegerType.i32, "zext");
                    IRGenerator.getCurBasicBlock().addInstruction(resizeInst);
                    right = resizeInst;
                }
                IcmpInst icmpInst = new IcmpInst(left, right, op_token.getTokenType());
                IRGenerator.getCurBasicBlock().addInstruction(icmpInst);
                return icmpInst;
            }
            else{
                Value right = temp.rel_exp.generateIR();
                if(right.getType().equals(IntegerType.i1)){
                    ResizeInst resizeInst = new ResizeInst(right, IntegerType.i32, "zext");
                    IRGenerator.getCurBasicBlock().addInstruction(resizeInst);
                    right = resizeInst;
                }
                IcmpInst icmpInst = new IcmpInst(left, right, op_token.getTokenType());
                temp.pre_value = icmpInst;
                IRGenerator.getCurBasicBlock().addInstruction(icmpInst);
                return temp.generateIR();
            }
        } else {
            return rel_exp.generateIR();
        }
    }


    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(rel_exp.toString());
        if (op_token != null) {
            content.append(op_token.toString()).append(eq_exp.toString());
        }
        return content.toString();
    }
}
