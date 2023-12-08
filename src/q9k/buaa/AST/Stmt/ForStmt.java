package q9k.buaa.AST.Stmt;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Instructions.StoreInst;
import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.Symbol;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;
import q9k.buaa.Token.Token;

import java.io.IOException;

public class ForStmt implements Syntax {
    private Syntax l_val;
    private Token assign_token;
    private Syntax exp;
    private Symbol symbol;
    private SymbolTable symbolTable;
    

    public ForStmt(Syntax l_val, Token assign_token, Syntax exp) {
        this.l_val = l_val;
        this.assign_token = assign_token;
        this.exp = exp;
    }

    @Override
    public void print() throws IOException {
        l_val.print();
        assign_token.print();
        exp.print();
        printAstName(ForStmt.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
        l_val.visit();
        this.symbol = SymbolTableFactory.getInstance().getCurrent().getSymbol(l_val);
        if(symbol!=null){
            if(symbol.isConst()){
                ErrorHandler.getInstance().addError(new Error(ErrorType.CHANGECONST, l_val.getLineNumber()));
            }
        }
        exp.visit();
    }

    @Override
    public int getLineNumber() {
        return l_val.getLineNumber();
    }

    @Override
    public Value generateIR() {
        Instruction instruction = new StoreInst();
        instruction.addOperand(this.symbol.getIR());
        instruction.addOperand(exp.generateIR());
        IRGenerator.getCurBasicBlock().addInstruction(instruction);
        return null;
    }


    @Override
    public String toString() {
        return l_val.toString()+assign_token.toString()+exp.toString();
    }
}



