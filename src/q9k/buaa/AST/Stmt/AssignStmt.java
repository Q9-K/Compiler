package q9k.buaa.AST.Stmt;

import q9k.buaa.AST.LVal;
import q9k.buaa.AST.Syntax;
import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.Function;
import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Instructions.CallInst;
import q9k.buaa.IR.Instructions.StoreInst;
import q9k.buaa.IR.Types.FunctionType;
import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.Symbol;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;
import q9k.buaa.Token.Token;

import java.io.IOException;

public class AssignStmt implements Stmt {
    private Syntax l_val;
    private Token assign_token;
    private Syntax exp;
    private Token getint_token;
    private Token lparent_token;
    private Token rparent_token;
    private Token semicn_token;

    private Symbol symbol;
    private SymbolTable symbolTable;

    public AssignStmt(Syntax l_val, Token assign_token, Syntax exp, Token getint_token, Token lparent_token, Token rparent_token, Token semicn_token) {
        this.l_val = l_val;
        this.assign_token = assign_token;
        this.exp = exp;
        this.getint_token = getint_token;
        this.lparent_token = lparent_token;
        this.rparent_token = rparent_token;
        this.semicn_token = semicn_token;
    }

    @Override
    public void print() throws IOException {
        l_val.print();
        assign_token.print();
        if (exp != null) {
            exp.print();
            semicn_token.print();
        } else {
            getint_token.print();
            lparent_token.print();
            rparent_token.print();
            semicn_token.print();
        }
        printAstName(Stmt.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
        l_val.visit();
        this.symbol = SymbolTableFactory.getInstance().getCurrent().getSymbol(l_val);
        if (symbol != null) {
            if (symbol.isConst()) {
                ErrorHandler.getInstance().addError(new Error(ErrorType.CHANGECONST, l_val.getLineNumber()));
            }
        }
        if (exp != null) {
            exp.visit();
        }
    }

    @Override
    public int getLineNumber() {
        if (exp != null) {
            return exp.getLineNumber();
        } else {
            return rparent_token.getLineNumber();
        }
    }

    @Override
    public Value genIR() {
        if (exp != null) {
            Instruction instruction = new StoreInst();
            Value value = exp.genIR();
            Value address = ((LVal)l_val).getAddress();
            instruction.addOperand(address);
            instruction.addOperand(value);
            IRGenerator.getCurBasicBlock().addInstruction(instruction);
        } else {
            Instruction instruction = new CallInst(IntegerType.i32);
            instruction.addOperand(new Function("@getint", FunctionType.FunctionType));
            IRGenerator.getCurBasicBlock().addInstruction(instruction);
            Instruction storeInst = new StoreInst();
            storeInst.addOperand(((LVal) l_val).getAddress());
            storeInst.addOperand(instruction);
            IRGenerator.getCurBasicBlock().addInstruction(storeInst);
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(l_val.toString()).append(assign_token.toString());
        if (exp != null) {
            content.append(exp.toString()).append(semicn_token.toString());
        } else {
            content.append(getint_token.toString()).append(lparent_token.toString())
                    .append(rparent_token.toString()).append(semicn_token.toString());
        }
        return content.toString();
    }

}
