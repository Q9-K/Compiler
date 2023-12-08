package q9k.buaa.AST.Stmt;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Instructions.ReturnInst;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.SymbolTableFactory;
import q9k.buaa.Token.Token;
import q9k.buaa.Symbol.SymbolTable;

import java.io.IOException;

public class ReturnStmt implements Stmt{

    Token return_token;
    Syntax exp;
    Token semicn_token;
    SymbolTable symbolTable;

    public ReturnStmt(Token return_token, Syntax exp, Token semicn_token) {
        this.return_token = return_token;
        this.exp = exp;
        this.semicn_token = semicn_token;
    }

    @Override
    public void print() throws IOException {
        return_token.print();
        if(exp!=null){
            exp.print();
        }
        semicn_token.print();
        printAstName(Stmt.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
        SymbolTable current = SymbolTableFactory.getInstance().getCurrent();
        if(current.getFuncBlock() == 0){
            ErrorHandler.getInstance().addError(new Error(ErrorType.REVERSERROR, getLineNumber()));
        }
        else{
            if(current.getFuncBlock() == 1 && exp != null){
                ErrorHandler.getInstance().addError(new Error(ErrorType.EXTRARETURNTYPE,getLineNumber()));
            }
            if(current.getFuncBlock() == 2 && exp==null){
                ErrorHandler.getInstance().addError(new Error(ErrorType.REVERSERROR, getLineNumber()));
            }
        }
        if(exp!=null){
            exp.visit();
        }
    }

    @Override
    public int getLineNumber() {
        return return_token.getLineNumber();
    }

    @Override
    public Value generateIR() {
        Instruction returnInstr = new ReturnInst();
        if(exp!=null){
            returnInstr.addOperand(exp.generateIR());
        }
        IRGenerator.getCurBasicBlock().addInstruction(returnInstr);
        return returnInstr;
    }

    @Override
    public String toString() {
        if(exp!=null){
            return return_token.toString()+' '+exp.toString()+semicn_token.toString();
        }
        else{
            return return_token.toString()+semicn_token.toString();
        }
    }

}
