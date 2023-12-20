package q9k.buaa.AST.Function;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.Argument;
import q9k.buaa.IR.BasicBlock;
import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Instructions.AllocalInst;
import q9k.buaa.IR.Instructions.StoreInst;
import q9k.buaa.IR.Types.ArrayType;
import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Types.PointerType;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.*;
import q9k.buaa.Token.Token;
import q9k.buaa.Utils.Calculator;
import q9k.buaa.Utils.Triple;

import java.io.IOException;
import java.util.List;

public class FuncFParam implements Syntax {
    private Syntax b_type;
    private Syntax ident;
    private Token lbrack;
    private Token rbrack;
    private List<Triple<Token, Syntax, Token>> list;

    private Symbol symbol;
    private SymbolTable symbolTable;
    

    public FuncFParam(Syntax b_type, Syntax ident, Token lbrack, Token rbrack, List<Triple<Token, Syntax, Token>> list) {
        this.b_type = b_type;
        this.ident = ident;
        this.lbrack = lbrack;
        this.rbrack = rbrack;
        this.list = list;
    }

    @Override
    public void print() throws IOException {
        b_type.print();
        ident.print();
        if (lbrack != null) {
            lbrack.print();
            rbrack.print();
        }
        for (Triple<Token, Syntax, Token> item : list) {
            item.first().print();
            item.second().print();
            item.third().print();
        }
        printAstName(FuncFParam.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
        if (SymbolTable.checkDef(ident)) {
            if (lbrack == null) {
                symbol = new VarSymbol(ident.toString());
//                SymbolTable.getCurrent().addSymbol(new VarSymbol(ident.toString()));
            } else if (list.isEmpty()) {
                symbol = new ArraySymbol(ident.toString(), null, null);
//                SymbolTable.getCurrent().addSymbol(new ArraySymbol(ident.toString(), null, null));
            } else {
                symbol = new ArraySymbol(ident.toString(), null, list.get(0).second());
//                SymbolTable.getCurrent().addSymbol(new ArraySymbol(ident.toString(), null, list.get(0).second()));
            }
            SymbolTableFactory.getInstance().getCurrent().addSymbol(symbol);
        }
    }

    @Override
    public int getLineNumber() {
        return ident.getLineNumber();
    }

    @Override
    public Value genIR() {
        if(lbrack == null){
            Argument argument = new Argument(null, IntegerType.i32);
            IRGenerator.getCurFunction().addArgument(argument);

            Instruction instruction = new AllocalInst(argument.getType(), true);
            this.symbol.setIR(instruction);
            IRGenerator.getCurBasicBlock().addInstruction(instruction);

            Instruction storeInst = new StoreInst();
            storeInst.addOperand(instruction);
            storeInst.addOperand(argument);
            IRGenerator.getCurBasicBlock().addInstruction(storeInst);
            return argument;
        }
        else if(list.isEmpty()){
            Argument argument = new Argument(null, new PointerType(IntegerType.i32));
            IRGenerator.getCurFunction().addArgument(argument);

            Instruction instruction = new AllocalInst(argument.getType(), true);
            this.symbol.setIR(instruction);
            IRGenerator.getCurBasicBlock().addInstruction(instruction);
            Instruction storeInst = new StoreInst();
            storeInst.addOperand(instruction);
            storeInst.addOperand(argument);
            IRGenerator.getCurBasicBlock().addInstruction(storeInst);
            return argument;
        }
        else if(list.size()==1){
            int numElements = Calculator.getInstance().calculate(list.get(0).second(), symbolTable);
            Argument argument = new Argument(null, new PointerType((new ArrayType(IntegerType.i32, numElements))));
            IRGenerator.getCurFunction().addArgument(argument);

            Instruction instruction = new AllocalInst(argument.getType(), true);
            this.symbol.setIR(instruction);
            IRGenerator.getCurBasicBlock().addInstruction(instruction);
            Instruction storeInst = new StoreInst();
            storeInst.addOperand(instruction);
            storeInst.addOperand(argument);
            IRGenerator.getCurBasicBlock().addInstruction(storeInst);
            return argument;
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(b_type.toString()).append(' ').append(ident.toString());
        if (lbrack != null) {
            content.append(lbrack.toString()).append(rbrack.toString());
            for (Triple<Token, Syntax, Token> item : list) {
                content.append(item.first().toString()).append(item.second().toString()).append(item.third().toString());
            }
        }
        return content.toString();
    }

    public SymbolType getSymbolType() {
        if (lbrack == null) {
            return SymbolType.VAR;
        } else if (list.isEmpty()) {
            return SymbolType.ARRAY;
        } else {
            return SymbolType.MULTIARRAY;
        }
    }

}
