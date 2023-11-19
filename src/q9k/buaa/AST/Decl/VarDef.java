package q9k.buaa.AST.Decl;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.GlobalVariable;
import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Instructions.AllocalInst;
import q9k.buaa.IR.Instructions.StoreInst;
import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Types.PointerType;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.ArraySymbol;
import q9k.buaa.Symbol.Symbol;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.VarSymbol;
import q9k.buaa.Token.Token;
import q9k.buaa.Utils.Calculator;
import q9k.buaa.Utils.IRModule;
import q9k.buaa.Utils.Triple;

import java.io.IOException;
import java.util.List;

public class VarDef implements Syntax {
    private Syntax ident;
    private List<Triple<Token, Syntax, Token>> list;
    private Token assign_token;
    private Syntax init_val;

    private Symbol symbol;

    public VarDef(Syntax ident, List<Triple<Token, Syntax, Token>> list, Token assign_token, Syntax init_val) {
        this.ident = ident;
        this.list = list;
        this.assign_token = assign_token;
        this.init_val = init_val;
    }

    @Override
    public void print() throws IOException {
        ident.print();
        for (Triple<Token, Syntax, Token> item : list) {
            item.first().print();
            item.second().print();
            item.third().print();
        }
        if (assign_token != null) {
            assign_token.print();
            init_val.print();
        }
        printAstName(VarDef.class);
    }

    @Override
    public void visit() {

        if (SymbolTable.checkDef(ident)) {
            if (list.isEmpty()) {
                symbol = new VarSymbol(ident.toString());
//                SymbolTable.getCurrent().addSymbol(new VarSymbol(ident.toString()));
            } else if (list.size() == 1) {
                symbol = new ArraySymbol(ident.toString(), list.get(0).second(), null);
//                SymbolTable.getCurrent().addSymbol(new ArraySymbol(ident.toString(),list.get(0).second(),null));
            } else {
                symbol = new ArraySymbol(ident.toString(), list.get(0).second(), list.get(1).second());
//                SymbolTable.getCurrent().addSymbol(new ArraySymbol(ident.toString(),list.get(0).second(),list.get(1).second()));
            }
            SymbolTable.getCurrent().addSymbol(symbol);
        }
        for (Triple<Token, Syntax, Token> item : list) {
            item.second().visit();
        }
        if (init_val != null) {
            init_val.visit();
        }
    }

    @Override
    public int getLineNumber() {
        if (init_val != null) {
            return init_val.getLineNumber();
        } else {
            if (list.isEmpty()) {
                return ident.getLineNumber();
            } else {
                Triple<Token, Syntax, Token> item = list.get(list.size() - 1);
                return item.third().getLineNumber();
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(ident.toString());
        for (Triple<Token, Syntax, Token> item : list) {
            content.append(item.first().toString()).append(item.second().toString()).append(item.third().toString());
        }
        if (assign_token != null) {
            content.append(assign_token.toString()).append(init_val.toString());
        }
        return content.toString();
    }

    @Override
    public Value generateIR() {
        if (IRGenerator.isGlobal()) {
            GlobalVariable globalVariable = new GlobalVariable("@" + ident.toString(), new PointerType(IntegerType.i32));
//            globalVariable.setInitializer();
            globalVariable.setInitializer(Calculator.getInstance().calculate(init_val));
            this.symbol.setIR(globalVariable);
            IRModule.getInstance().addGlobalVar(globalVariable);
            return globalVariable;
        } else {
            Instruction instruction = new AllocalInst(null, new PointerType(IntegerType.i32));
            this.symbol.setIR(instruction);
            IRGenerator.getCurBasicBlock().addInstruction(instruction);
            if (init_val != null) {
                Instruction storeInst = new StoreInst(null, null);
                storeInst.addOperand(instruction);
                storeInst.addOperand(init_val.generateIR());
                IRGenerator.getCurBasicBlock().addInstruction(storeInst);
            }
        }
        return null;
    }
}
