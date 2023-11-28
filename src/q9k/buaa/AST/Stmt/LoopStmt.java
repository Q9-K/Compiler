package q9k.buaa.AST.Stmt;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.BasicBlock;
import q9k.buaa.IR.Instructions.BranchInst;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Token.Token;

import java.io.IOException;

public class LoopStmt implements Stmt {
    private Token for_token;
    private Token lparent_token;
    private Syntax for_stmt1;
    private Token semicn_token1;
    private Syntax cond;
    private Token semicn_token2;
    private Syntax for_stmt2;
    private Token rparent_token;
    private Syntax stmt;


    public LoopStmt(Token for_token, Token lparent_token, Syntax for_stmt1, Token semicn_token1, Syntax cond, Token semicn_token2, Syntax for_stmt2, Token rparent_token, Syntax stmt) {
        this.for_token = for_token;
        this.lparent_token = lparent_token;
        this.for_stmt1 = for_stmt1;
        this.semicn_token1 = semicn_token1;
        this.cond = cond;
        this.semicn_token2 = semicn_token2;
        this.for_stmt2 = for_stmt2;
        this.rparent_token = rparent_token;
        this.stmt = stmt;
    }

    @Override
    public void print() throws IOException {
        for_token.print();
        lparent_token.print();
        if (for_stmt1 != null) {
            for_stmt1.print();
        }
        semicn_token1.print();
        if (cond != null) {
            cond.print();
        }
        semicn_token2.print();
        if (for_stmt2 != null) {
            for_stmt2.print();
        }
        rparent_token.print();
        stmt.print();
        printAstName(Stmt.class);
    }

    @Override
    public void visit() {

        if (for_stmt1 != null) {
            for_stmt1.visit();
        }
        if (cond != null) {
            cond.visit();
        }
        if (for_stmt2 != null) {
            for_stmt2.visit();
        }
        SymbolTable.changeTo(SymbolTable.getCurrent().createSymbolTable());
        SymbolTable.getCurrent().setFor_block(true);
        stmt.visit();
        SymbolTable.changeToFather();
    }

    @Override
    public int getLineNumber() {
        return 0;
    }

    @Override
    public Value generateIR() {
        if (for_stmt1 != null) {
            for_stmt1.generateIR();
        }


        BasicBlock condBlock = new BasicBlock();
        BasicBlock trueBasicBlock = new BasicBlock();
        BasicBlock stepBlock = new BasicBlock();
        BasicBlock nextBasicBlock = new BasicBlock();

        BranchInst branchInst = new BranchInst();
        branchInst.addTargetBlock(condBlock);
        IRGenerator.getCurBasicBlock().addInstruction(branchInst);

        BasicBlock cur_stepBasicBlock = IRGenerator.getStepBasicBlock();
        BasicBlock cur_loopFollowBlock = IRGenerator.getLoopFollowBlock();

        IRGenerator.setStepBasicBlock(stepBlock);
        IRGenerator.setLoopFollowBlock(nextBasicBlock);

        IRGenerator.setTrueBasicBlock(trueBasicBlock);
        IRGenerator.setFalseBasicBlock(nextBasicBlock);
        IRGenerator.getCurFunction().addBasicBlock(condBlock);
        IRGenerator.setCurBasicBlock(condBlock);
        if (cond != null) {
            cond.generateIR();
        }
        else{
            BranchInst branchInst1 = new BranchInst();
            branchInst1.addTargetBlock(trueBasicBlock);
            IRGenerator.getCurBasicBlock().addInstruction(branchInst1);
        }


        IRGenerator.getCurFunction().addBasicBlock(trueBasicBlock);
        IRGenerator.setCurBasicBlock(trueBasicBlock);
        stmt.generateIR();
        BranchInst branchInst1 = new BranchInst();
        branchInst1.addTargetBlock(stepBlock);
        IRGenerator.getCurBasicBlock().addInstruction(branchInst1);

        IRGenerator.getCurFunction().addBasicBlock(stepBlock);
        IRGenerator.setCurBasicBlock(stepBlock);
        if (for_stmt2 != null) {
            for_stmt2.generateIR();
        }
        BranchInst branchInst2 = new BranchInst();
        branchInst2.addTargetBlock(condBlock);
        IRGenerator.getCurBasicBlock().addInstruction(branchInst2);

        IRGenerator.setStepBasicBlock(cur_stepBasicBlock);
        IRGenerator.setLoopFollowBlock(cur_loopFollowBlock);

        IRGenerator.setCurBasicBlock(nextBasicBlock);
        IRGenerator.getCurFunction().addBasicBlock(nextBasicBlock);

        return null;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(for_token.toString()).append(lparent_token.toString());
        if (for_stmt1 != null) {
            content.append(for_stmt1.toString());
        }
        content.append(semicn_token1.toString());
        if (cond != null) {
            content.append(cond.toString());
        }
        content.append(semicn_token2.toString());
        if (for_stmt2 != null) {
            content.append(for_stmt2.toString());
        }
        content.append(rparent_token.toString()).append(stmt.toString());
        return content.toString();
    }

}
