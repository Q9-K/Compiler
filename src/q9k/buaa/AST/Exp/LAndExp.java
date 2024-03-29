package q9k.buaa.AST.Exp;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.BasicBlock;
import q9k.buaa.IR.ConstantInt;
import q9k.buaa.IR.Instructions.BranchInst;
import q9k.buaa.IR.Instructions.IcmpInst;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;
import q9k.buaa.Token.Token;
import q9k.buaa.Token.TokenType;

import java.io.IOException;

public class LAndExp implements Syntax {

    private Syntax eq_exp;
    private Token and_token;
    private Syntax l_and_exp;
    private SymbolTable symbolTable;


    public LAndExp(Syntax eq_exp, Token and_token, Syntax l_and_exp) {
        this.eq_exp = eq_exp;
        this.and_token = and_token;
        this.l_and_exp = l_and_exp;
    }

    @Override
    public void print() throws IOException {
        eq_exp.print();
        printAstName(LAndExp.class);
        if (and_token != null) {
            and_token.print();
            l_and_exp.print();
        }
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
        eq_exp.visit();
        if (l_and_exp != null) {
            l_and_exp.visit();
        }
    }

    @Override
    public int getLineNumber() {
        if (and_token == null) {
            return eq_exp.getLineNumber();
        }
        return l_and_exp.getLineNumber();
    }

    @Override
    public Value genIR() {
        if (and_token != null) {
            BasicBlock newBasicBlock = new BasicBlock();
            IRGenerator.getCurFunction().addBasicBlock(newBasicBlock);

            BasicBlock cur_trueBlock = IRGenerator.getTrueBasicBlock();
            IRGenerator.setTrueBasicBlock(newBasicBlock);
            Value value = eq_exp.genIR();
            IcmpInst icmpInst;
            if (!(value instanceof IcmpInst)) {
                icmpInst = new IcmpInst(value, ConstantInt.ZERO, TokenType.NEQ);
                IRGenerator.getCurBasicBlock().addInstruction(icmpInst);
            }
            else{
                icmpInst = (IcmpInst) value;
            }
            BranchInst branchInst = new BranchInst();
            branchInst.addOperand(icmpInst);
            branchInst.addTargetBlock(IRGenerator.getTrueBasicBlock());
            branchInst.addTargetBlock(IRGenerator.getFalseBasicBlock());
            IRGenerator.getCurBasicBlock().addInstruction(branchInst);
            IRGenerator.setTrueBasicBlock(cur_trueBlock);

            BasicBlock curBasicBlock = IRGenerator.getCurBasicBlock();
            IRGenerator.setCurBasicBlock(newBasicBlock);
            l_and_exp.genIR();
            IRGenerator.setCurBasicBlock(curBasicBlock);
        } else {
            Value value = eq_exp.genIR();
            IcmpInst icmpInst;
            if (!(value instanceof IcmpInst)) {
                icmpInst = new IcmpInst(value, ConstantInt.ZERO, TokenType.NEQ);
                IRGenerator.getCurBasicBlock().addInstruction(icmpInst);
            }
            else{
                icmpInst = (IcmpInst) value;
            }
            BranchInst branchInst = new BranchInst();
            branchInst.addOperand(icmpInst);
            branchInst.addTargetBlock(IRGenerator.getTrueBasicBlock());
            branchInst.addTargetBlock(IRGenerator.getFalseBasicBlock());
            IRGenerator.getCurBasicBlock().addInstruction(branchInst);
        }
        return null;
    }


    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(eq_exp.toString());
        if (and_token != null) {
            content.append(and_token.toString()).append(l_and_exp.toString());
        }
        return content.toString();
    }
}
