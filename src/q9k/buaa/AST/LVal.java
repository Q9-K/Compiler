package q9k.buaa.AST;

import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.ConstantInt;
import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Instructions.GEPInst;
import q9k.buaa.IR.Instructions.LoadInst;
import q9k.buaa.IR.Types.ArrayType;
import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Types.PointerType;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.Symbol;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;
import q9k.buaa.Token.Token;
import q9k.buaa.Utils.Triple;

import java.io.IOException;
import java.util.List;

public class LVal implements Syntax {

    private Syntax ident;
    private List<Triple<Token, Syntax, Token>> list;
    private Symbol symbol;
    private SymbolTable symbolTable;
    public LVal(Syntax ident, List<Triple<Token, Syntax, Token>> list) {
        this.ident = ident;
        this.list = list;
    }

    @Override
    public void print() throws IOException {
        ident.print();
        for (Triple<Token, Syntax, Token> item : list) {
            item.first().print();
            item.second().print();
            item.third().print();
        }
        printAstName(LVal.class);
    }


    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
        this.symbol = SymbolTableFactory.getInstance().getCurrent().checkVarInvoke(ident);
        for (Triple<Token, Syntax, Token> item : list) {
            item.second().visit();
        }
    }

    @Override
    public int getLineNumber() {
        return ident.getLineNumber();
    }

    @Override
    public Value genIR() {
        if (list.isEmpty()) {
            PointerType pointerType = (PointerType) this.symbol.getIR().getType();
            if (pointerType.getSourceType() instanceof ArrayType) {
                //数组
                GEPInst gepInst = new GEPInst(this.symbol.getIR());
                gepInst.setPos1(ConstantInt.ZERO);
                gepInst.setPos2(ConstantInt.ZERO);
                IRGenerator.getCurBasicBlock().addInstruction(gepInst);
                return gepInst;
            } else {
                //变量 or 数组参数
                LoadInst loadInst = new LoadInst(this.symbol.getIR());
                IRGenerator.getCurBasicBlock().addInstruction(loadInst);
                return loadInst;
            }
        } else if (list.size() == 1) {
            PointerType pointerType = (PointerType) this.symbol.getIR().getType();
            if (pointerType.getSourceType() instanceof ArrayType) {
                //数组
                Type elementType = ((ArrayType) pointerType.getSourceType()).getElementType();
                GEPInst gepInst = new GEPInst(this.symbol.getIR());
                gepInst.setPos1(ConstantInt.ZERO);
                gepInst.setPos2(list.get(0).second().genIR());
                IRGenerator.getCurBasicBlock().addInstruction(gepInst);
                if (elementType instanceof IntegerType) {
                    //一维数组
                    LoadInst loadInst = new LoadInst(gepInst);
                    loadInst.addOperand(gepInst);
                    IRGenerator.getCurBasicBlock().addInstruction(loadInst);
                    return loadInst;
                } else {
                    //二维数组
                    GEPInst gepInst1 = new GEPInst(gepInst);
                    gepInst1.setPos1(ConstantInt.ZERO);
                    gepInst1.setPos2(ConstantInt.ZERO);
                    IRGenerator.getCurBasicBlock().addInstruction(gepInst1);
                    return gepInst1;
                }
            } else {
                //数组参数
                Instruction instruction = new LoadInst(this.symbol.getIR());
                IRGenerator.getCurBasicBlock().addInstruction(instruction);

                //二维数组
                if (instruction.getType().getLevel() == 2) {
                    GEPInst gepInst = new GEPInst(instruction);
                    gepInst.setPos1(list.get(0).second().genIR());
                    gepInst.setPos2(ConstantInt.ZERO);
                    IRGenerator.getCurBasicBlock().addInstruction(gepInst);
//                LoadInst loadInst = new LoadInst(gepInst);
//                IRGenerator.getCurBasicBlock().addInstruction(loadInst);
                    return gepInst;
                }
                //一维数组
                else {
                    GEPInst gepInst = new GEPInst(instruction);
                    gepInst.setPos1(list.get(0).second().genIR());
                    IRGenerator.getCurBasicBlock().addInstruction(gepInst);
                    LoadInst loadInst = new LoadInst(gepInst);
                    IRGenerator.getCurBasicBlock().addInstruction(loadInst);
                    return loadInst;
                }
            }
        } else if (list.size() == 2) {
            PointerType pointerType = (PointerType) this.symbol.getIR().getType();
            if (pointerType.getSourceType() instanceof ArrayType) {
                //数组
                GEPInst gepInst = new GEPInst(this.symbol.getIR());
                gepInst.setPos1(ConstantInt.ZERO);
                gepInst.setPos2(list.get(0).second().genIR());
                gepInst.setPos3(list.get(1).second().genIR());
                IRGenerator.getCurBasicBlock().addInstruction(gepInst);

                LoadInst loadInst = new LoadInst(gepInst);
                IRGenerator.getCurBasicBlock().addInstruction(loadInst);
                return loadInst;
            } else {
                //数组参数
                Instruction instruction = new LoadInst(this.symbol.getIR());
                IRGenerator.getCurBasicBlock().addInstruction(instruction);

                GEPInst gepInst = new GEPInst(instruction);
                gepInst.setPos1(list.get(0).second().genIR());
                gepInst.setPos2(list.get(1).second().genIR());
                IRGenerator.getCurBasicBlock().addInstruction(gepInst);

                LoadInst loadInst = new LoadInst(gepInst);
                IRGenerator.getCurBasicBlock().addInstruction(loadInst);
                return loadInst;
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
        return content.toString();
    }


    public Value getAddress() {
        if (list.isEmpty()) {
            Value value = this.symbol.getIR();
            PointerType pointerType = (PointerType) value.getType();
            if (pointerType.getSourceType() instanceof IntegerType) {
                return this.symbol.getIR();
            } else {
                GEPInst gepInst = new GEPInst(value);
                gepInst.setPos1(ConstantInt.ZERO);
                gepInst.setPos2(ConstantInt.ZERO);
                IRGenerator.getCurBasicBlock().addInstruction(gepInst);
                return gepInst;
            }
        } else if (list.size() == 1) {
            Value value = this.symbol.getIR();
            PointerType pointerType = (PointerType) value.getType();
            if (pointerType.getSourceType() instanceof ArrayType) {
                GEPInst gepInst = new GEPInst(this.symbol.getIR());
                gepInst.setPos1(ConstantInt.ZERO);
                gepInst.setPos2(list.get(0).second().genIR());
                IRGenerator.getCurBasicBlock().addInstruction(gepInst);
                return gepInst;
            } else {
                LoadInst loadInst = new LoadInst(value);
                IRGenerator.getCurBasicBlock().addInstruction(loadInst);
                GEPInst gepInst = new GEPInst(loadInst);
                gepInst.setPos1(list.get(0).second().genIR());
                IRGenerator.getCurBasicBlock().addInstruction(gepInst);
                return gepInst;
            }
        } else if (list.size() == 2) {
            Value value = this.symbol.getIR();
            PointerType pointerType = (PointerType) value.getType();
            if (pointerType.getSourceType() instanceof ArrayType) {
                GEPInst gepInst = new GEPInst(this.symbol.getIR());
                gepInst.setPos1(ConstantInt.ZERO);
                gepInst.setPos2(list.get(0).second().genIR());
                gepInst.setPos3(list.get(1).second().genIR());
                IRGenerator.getCurBasicBlock().addInstruction(gepInst);
                return gepInst;
            } else {
                Instruction instruction = new LoadInst(value);
                IRGenerator.getCurBasicBlock().addInstruction(instruction);
                GEPInst gepInst = new GEPInst(instruction);
                gepInst.setPos1(list.get(0).second().genIR());
                gepInst.setPos2(list.get(1).second().genIR());
                IRGenerator.getCurBasicBlock().addInstruction(gepInst);
                return gepInst;
            }
        }
        return null;
    }
}
