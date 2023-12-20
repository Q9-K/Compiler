package q9k.buaa.AST.Decl;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.ConstantInt;
import q9k.buaa.IR.GlobalVariable;
import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Instructions.AllocalInst;
import q9k.buaa.IR.Instructions.GEPInst;
import q9k.buaa.IR.Instructions.StoreInst;
import q9k.buaa.IR.Types.ArrayType;
import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Types.PointerType;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.*;
import q9k.buaa.Token.Token;
import q9k.buaa.Utils.Calculator;
import q9k.buaa.IR.IRModule;
import q9k.buaa.Utils.Triple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConstDef implements Syntax {
    private Syntax ident;
    private List<Triple<Token, Syntax, Token>> list = new ArrayList<>();
    private Token assign_token;
    private Syntax const_init_val;
    private Symbol symbol;
    private SymbolTable symbolTable;


    public ConstDef(Syntax ident, List<Triple<Token, Syntax, Token>> list, Token assign_token, Syntax const_init_val) {
        this.ident = ident;
        this.list = list;
        this.assign_token = assign_token;
        this.const_init_val = const_init_val;
    }

    @Override
    public void print() throws IOException {
        ident.print();
        for (Triple<Token, Syntax, Token> item : list) {
            item.first().print();
            item.second().print();
            item.third().print();
        }
        assign_token.print();
        const_init_val.print();
        printAstName(ConstDef.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
        if (SymbolTable.checkDef(ident)) {
            if (list.isEmpty()) {
                symbol = new VarSymbol(ident.toString());
            } else if (list.size() == 1) {
                symbol = new ArraySymbol(ident.toString(), list.get(0).second(), null);
            } else {
                symbol = new ArraySymbol(ident.toString(), list.get(0).second(), list.get(1).second());
            }
            symbol.setConst(true);
            for (Triple<Token, Syntax, Token> item : list) {
                item.second().visit();
            }
            const_init_val.visit();
            SymbolTableFactory.getInstance().getCurrent().addSymbol(symbol);
        }
    }


    @Override
    public int getLineNumber() {
        return const_init_val.getLineNumber();
    }

    @Override
    public Value genIR() {
        if (IRGenerator.isGlobal()) {
            if (list.isEmpty()) {
                GlobalVariable globalVariable = new GlobalVariable("@" + ident.toString(), new PointerType(IntegerType.i32), true);
                if (const_init_val != null) {
                    globalVariable.setInitializer(((ConstInitVal) const_init_val).getInitializer());
                }
                this.symbol.setIR(globalVariable);
                this.symbol.setCalValue(globalVariable);
                IRModule.getInstance().addGlobalVar(globalVariable);
            } else if (list.size() == 1) {
                int dim = Calculator.getInstance().calculate(list.get(0).second(), symbolTable);
                ArrayType arrayType = new ArrayType(IntegerType.i32, dim);
                GlobalVariable globalVariable = new GlobalVariable("@" + ident.toString(), new PointerType((arrayType)), true);
                if (const_init_val != null) {
                    globalVariable.setInitializer(((ConstInitVal) const_init_val).getInitializer());
                }
                this.symbol.setIR(globalVariable);
                this.symbol.setCalValue(globalVariable);
                IRModule.getInstance().addGlobalVar(globalVariable);
            } else if (list.size() == 2) {
                int dim1 = Calculator.getInstance().calculate(list.get(0).second(), symbolTable);
                int dim2 = Calculator.getInstance().calculate(list.get(1).second(), symbolTable);
                ArrayType arrayType1 = new ArrayType(IntegerType.i32, dim2);
                ArrayType arrayType2 = new ArrayType(arrayType1, dim1);
                GlobalVariable globalVariable = new GlobalVariable("@" + ident.toString(), new PointerType(arrayType2));
                if (const_init_val != null) {
                    globalVariable.setInitializer(((ConstInitVal) const_init_val).getInitializer());
                }
                this.symbol.setIR(globalVariable);
                this.symbol.setCalValue(globalVariable);
                IRModule.getInstance().addGlobalVar(globalVariable);
            }
        } else {
            if (list.isEmpty()) {
                AllocalInst allocalInst = new AllocalInst(IntegerType.i32);
                this.symbol.setIR(allocalInst);
                IRGenerator.getCurBasicBlock().addInstruction(allocalInst);
                if (const_init_val != null) {
                    Instruction storeInst = new StoreInst();
                    storeInst.addOperand(allocalInst);
                    storeInst.addOperand(const_init_val.genIR());
                    IRGenerator.getCurBasicBlock().addInstruction(storeInst);
                }
                GlobalVariable globalVariable = new GlobalVariable("@" + ident.toString(), new PointerType(IntegerType.i32), true);
                if (const_init_val != null) {
                    globalVariable.setInitializer(((ConstInitVal) const_init_val).getInitializer());
                }
                this.symbol.setCalValue(globalVariable);
            } else if (list.size() == 1) {
                int dim = Calculator.getInstance().calculate(list.get(0).second(), symbolTable);
                ArrayType arrayType = new ArrayType(IntegerType.i32, dim);
                Instruction instruction = new AllocalInst(arrayType);
                this.symbol.setIR(instruction);
                IRGenerator.getCurBasicBlock().addInstruction(instruction);

                if (const_init_val != null) {
                    List<Syntax> constInitVals = ((ConstInitVal) const_init_val).getConstInitValList();
                    int index = 0;
                    for (Syntax const_init_val : constInitVals) {
                        GEPInst gepInst = new GEPInst(instruction);
                        gepInst.setPos1(ConstantInt.ZERO);
                        gepInst.setPos2(new ConstantInt(index));
                        //TODO:设置gepInst的位置
                        IRGenerator.getCurBasicBlock().addInstruction(gepInst);
                        Instruction storeInst = new StoreInst();
                        storeInst.addOperand(gepInst);
                        storeInst.addOperand(const_init_val.genIR());
                        IRGenerator.getCurBasicBlock().addInstruction(storeInst);
                        index++;
                    }
                }
                GlobalVariable globalVariable = new GlobalVariable("@" + ident.toString(), new PointerType((arrayType)), true);
                if (const_init_val != null) {
                    globalVariable.setInitializer(((ConstInitVal) const_init_val).getInitializer());
                }
                this.symbol.setCalValue(globalVariable);
            } else if (list.size() == 2) {
                int dim1 = Calculator.getInstance().calculate(list.get(0).second(), symbolTable);
                int dim2 = Calculator.getInstance().calculate(list.get(1).second(), symbolTable);
                ArrayType arrayType1 = new ArrayType(IntegerType.i32, dim2);
                ArrayType arrayType2 = new ArrayType(arrayType1, dim1);
                Instruction instruction = new AllocalInst(arrayType2);
                this.symbol.setIR(instruction);
                IRGenerator.getCurBasicBlock().addInstruction(instruction);
                if (const_init_val != null) {
                    List<Syntax> constInitVals1 = ((ConstInitVal) const_init_val).getConstInitValList();
                    int index1 = 0;
                    for (Syntax item1 : constInitVals1) {
                        int index2 = 0;
                        List<Syntax> constInitVals2 = ((ConstInitVal) item1).getConstInitValList();
                        for (Syntax const_init_val : constInitVals2) {
                            GEPInst gepInst = new GEPInst(instruction);
                            gepInst.setPos1(ConstantInt.ZERO);
                            gepInst.setPos2(new ConstantInt(index1));
                            gepInst.setPos3(new ConstantInt(index2));
                            //TODO:设置gepInst的位置
                            IRGenerator.getCurBasicBlock().addInstruction(gepInst);
                            Instruction storeInst = new StoreInst();
                            storeInst.addOperand(gepInst);
                            storeInst.addOperand(const_init_val.genIR());
                            IRGenerator.getCurBasicBlock().addInstruction(storeInst);
                            index2++;
                        }
                        index1++;
                    }
                }
                GlobalVariable globalVariable = new GlobalVariable("@" + ident.toString(), new PointerType(arrayType2));
                if (const_init_val != null) {
                    globalVariable.setInitializer(((ConstInitVal) const_init_val).getInitializer());
                }
                this.symbol.setCalValue(globalVariable);
            }
        }
        return null;
    }


    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(ident.toString());
        for (Triple<Token, Syntax, Token> item : list) {
            content.append(item.first().toString()).append(item.second().toString()).append(item.third().toString());
        }
        content.append(assign_token.toString()).append(const_init_val.toString());
        return content.toString();
    }


}
