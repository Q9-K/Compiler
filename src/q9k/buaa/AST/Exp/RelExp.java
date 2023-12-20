package q9k.buaa.AST.Exp;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.Instructions.IcmpInst;
import q9k.buaa.IR.Instructions.ResizeInst;
import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;
import q9k.buaa.Token.Token;

import java.io.IOException;

public class RelExp implements Syntax {
    private Syntax add_exp;
    private Token op_token;
    private Syntax rel_exp;
    private Value pre_value;
    private SymbolTable symbolTable;


    public RelExp(Syntax add_exp, Token op_token, Syntax rel_exp) {
        this.add_exp = add_exp;
        this.op_token = op_token;
        this.rel_exp = rel_exp;
    }

    @Override
    public void print() throws IOException {
        add_exp.print();
        printAstName(RelExp.class);
        if (op_token != null) {
            op_token.print();
            rel_exp.print();
        }
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
        add_exp.visit();
        if (rel_exp != null) {
            rel_exp.visit();
        }
    }

    @Override
    public int getLineNumber() {
        if (op_token == null) {
            return add_exp.getLineNumber();
        } else {
            return rel_exp.getLineNumber();
        }
    }

    @Override
    public Value genIR() {
        if (op_token != null) {
            Value left;
            if(this.pre_value == null){
                left = add_exp.genIR();
            }
            else{
                left = pre_value;
            }
            if(left.getType().equals(IntegerType.i1)){
                ResizeInst resizeInst = new ResizeInst(left, IntegerType.i32, "zext");
                IRGenerator.getCurBasicBlock().addInstruction(resizeInst);
                left = resizeInst;
            }
            RelExp temp = (RelExp) rel_exp;
            if(temp.op_token == null){
                Value right = temp.genIR();
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
                Value right = temp.add_exp.genIR();
                if(right.getType().equals(IntegerType.i1)){
                    ResizeInst resizeInst = new ResizeInst(right, IntegerType.i32, "zext");
                    IRGenerator.getCurBasicBlock().addInstruction(resizeInst);
                    right = resizeInst;
                }
                IcmpInst icmpInst = new IcmpInst(left, right, op_token.getTokenType());
                temp.pre_value = icmpInst;
                IRGenerator.getCurBasicBlock().addInstruction(icmpInst);
                return temp.genIR();
            }

        } else {
            return add_exp.genIR();
        }
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(add_exp.toString());
        if (op_token != null) {
            content.append(op_token.toString()).append(rel_exp.toString());
        }
        return content.toString();
    }
}
