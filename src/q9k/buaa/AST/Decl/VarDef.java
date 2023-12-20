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
import java.util.List;

public class VarDef implements Syntax {
    private Syntax ident;
    private List<Triple<Token, Syntax, Token>> list;
    private Token assign_token;
    private Syntax init_val;

    private Symbol symbol;
    private SymbolTable symbolTable;

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
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
        if (SymbolTable.checkDef(ident)) {
            if (list.isEmpty()) {
                symbol = new VarSymbol(ident.toString());

            } else if (list.size() == 1) {
                symbol = new ArraySymbol(ident.toString(), list.get(0).second(), null);

            } else {
                symbol = new ArraySymbol(ident.toString(), list.get(0).second(), list.get(1).second());

            }
            for (Triple<Token, Syntax, Token> item : list) {
                item.second().visit();
            }
            if (init_val != null) {
                init_val.visit();
            }
            SymbolTableFactory.getInstance().getCurrent().addSymbol(symbol);
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
    public Value genIR() {
        if (IRGenerator.isGlobal()) {
            if (list.isEmpty()) {
                //变量
                GlobalVariable globalVariable = new GlobalVariable("@" + ident.toString(), new PointerType(IntegerType.i32));
                if (init_val != null) {
                    globalVariable.setInitializer(((InitVal) init_val).getInitializer());
                }
                this.symbol.setIR(globalVariable);
                IRModule.getInstance().addGlobalVar(globalVariable);

            } else if (list.size() == 1) {
                //一维数组
                int dim = Calculator.getInstance().calculate(list.get(0).second(), symbolTable);
                ArrayType arrayType = new ArrayType(IntegerType.i32, dim);
                GlobalVariable globalVariable = new GlobalVariable("@" + ident.toString(), new PointerType((arrayType)));
                if (init_val != null) {
                    globalVariable.setInitializer(((InitVal) init_val).getInitializer());
                }
                this.symbol.setIR(globalVariable);
                IRModule.getInstance().addGlobalVar(globalVariable);


            } else if (list.size() == 2) {
                //二维数组
                int dim1 = Calculator.getInstance().calculate(list.get(0).second(), symbolTable);
                int dim2 = Calculator.getInstance().calculate(list.get(1).second(), symbolTable);
                ArrayType arrayType1 = new ArrayType(IntegerType.i32, dim2);
                ArrayType arrayType2 = new ArrayType(arrayType1, dim1);
                GlobalVariable globalVariable = new GlobalVariable("@" + ident.toString(), new PointerType(arrayType2));
                if (init_val != null) {
                    globalVariable.setInitializer(((InitVal) init_val).getInitializer());
                }
                this.symbol.setIR(globalVariable);
                IRModule.getInstance().addGlobalVar(globalVariable);
            }
        } else {
            if (list.isEmpty()) {
                //变量
                Instruction instruction = new AllocalInst(IntegerType.i32);
                this.symbol.setIR(instruction);
                IRGenerator.getCurBasicBlock().addInstruction(instruction);
                if (init_val != null) {
                    Instruction storeInst = new StoreInst();
                    storeInst.addOperand(instruction);
                    storeInst.addOperand(init_val.genIR());
                    IRGenerator.getCurBasicBlock().addInstruction(storeInst);
                }
            } else if (list.size() == 1) {
                //一维数组
                int dim = Calculator.getInstance().calculate(list.get(0).second(), symbolTable);
                ArrayType arrayType = new ArrayType(IntegerType.i32, dim);
                Instruction instruction = new AllocalInst(arrayType);
                this.symbol.setIR(instruction);
                IRGenerator.getCurBasicBlock().addInstruction(instruction);

                if (init_val != null) {
                    List<Syntax> initVals = ((InitVal) init_val).getInitValList();
                    int index = 0;
                    for (Syntax init_val : initVals) {
                        GEPInst gepInst = new GEPInst(instruction);
                        gepInst.setPos1(ConstantInt.ZERO);
                        gepInst.setPos2(new ConstantInt(index));
                        //TODO:设置gepInst的位置
                        IRGenerator.getCurBasicBlock().addInstruction(gepInst);
                        Instruction storeInst = new StoreInst();
                        storeInst.addOperand(gepInst);
                        storeInst.addOperand(init_val.genIR());
                        IRGenerator.getCurBasicBlock().addInstruction(storeInst);
                        index++;
                    }
                }
            } else if (list.size() == 2) {
                //二维数组
                int dim1 = Calculator.getInstance().calculate(list.get(0).second(), symbolTable);
                int dim2 = Calculator.getInstance().calculate(list.get(1).second(), symbolTable);
                ArrayType arrayType1 = new ArrayType(IntegerType.i32, dim2);
                ArrayType arrayType2 = new ArrayType(arrayType1, dim1);
                Instruction instruction = new AllocalInst(arrayType2);
                this.symbol.setIR(instruction);
                IRGenerator.getCurBasicBlock().addInstruction(instruction);
                if (init_val != null) {
                    List<Syntax> initVals1 = ((InitVal) init_val).getInitValList();
                    int index1 = 0;
                    for (Syntax item1 : initVals1) {
                        int index2 = 0;
                        List<Syntax> initVals2 = ((InitVal) item1).getInitValList();
                        for (Syntax init_val : initVals2) {
                            GEPInst gepInst = new GEPInst(instruction);
                            gepInst.setPos1(ConstantInt.ZERO);
                            gepInst.setPos2(new ConstantInt(index1));
                            gepInst.setPos3(new ConstantInt(index2));
                            //TODO:设置gepInst的位置
                            IRGenerator.getCurBasicBlock().addInstruction(gepInst);
                            Instruction storeInst = new StoreInst();
                            storeInst.addOperand(gepInst);
                            storeInst.addOperand(init_val.genIR());
                            IRGenerator.getCurBasicBlock().addInstruction(storeInst);
                            index2++;
                        }
                        index1++;
                    }
                }
            }
        }
        return null;
    }
}
