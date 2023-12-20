package q9k.buaa.AST.Stmt;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.Instructions.BranchInst;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTableFactory;
import q9k.buaa.Token.Token;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Token.TokenType;

import java.io.IOException;

public class ActionStmt implements Stmt{
    private Token action_token;
    private Token semicn_token;
    private SymbolTable symbolTable;

    public ActionStmt(Token action_token, Token semicn_token) {
        this.action_token = action_token;
        this.semicn_token = semicn_token;
    }

    @Override
    public void print() throws IOException {
        action_token.print();
        semicn_token.print();
        printAstName(Stmt.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
        SymbolTable current = SymbolTableFactory.getInstance().getCurrent();
        if(!current.isForBlock()){
            ErrorHandler.getInstance().addError(new Error(ErrorType.USINGCYCLEBC,getLineNumber()));
        }
    }

    @Override
    public int getLineNumber() {
        return action_token.getLineNumber();
    }

    @Override
    public Value genIR() {
        if(action_token.getTokenType().equals(TokenType.CONTINUETK)){
            if(!IRGenerator.getCurBasicBlock().isEnd()){
                BranchInst branchInst = new BranchInst();
                branchInst.addTargetBlock(IRGenerator.getStepBasicBlock());
                IRGenerator.getCurBasicBlock().addInstruction(branchInst);
            }
        }
        else if(action_token.getTokenType().equals(TokenType.BREAKTK)){
            if(!IRGenerator.getCurBasicBlock().isEnd()){
                BranchInst branchInst = new BranchInst();
                branchInst.addTargetBlock(IRGenerator.getLoopFollowBlock());
                IRGenerator.getCurBasicBlock().addInstruction(branchInst);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return action_token.toString()+semicn_token.toString();
    }


}
