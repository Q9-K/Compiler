package q9k.buaa.AST.Function;

import q9k.buaa.AST.Block;
import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.BasicBlock;
import q9k.buaa.IR.Types.FunctionType;
import q9k.buaa.IR.Value;
import q9k.buaa.Utils.IRModule;
import q9k.buaa.IR.Function;
import q9k.buaa.Symbol.FuncSymbol;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolType;
import q9k.buaa.Token.Token;
import q9k.buaa.Token.TokenType;

import java.io.IOException;

public class FuncDef implements Syntax {

    private Syntax func_type;
    private Syntax ident;
    private Token lparent;
    private Syntax func_f_params;
    private Token rparent;
    private Syntax block;
    private SymbolType returnType;
    

    public FuncDef(Syntax func_type, Syntax ident, Token lparent, Syntax func_f_params, Token rparent, Syntax block) {
        this.func_type = func_type;
        this.ident = ident;
        this.lparent = lparent;
        this.func_f_params = func_f_params;
        this.rparent = rparent;
        this.block = block;
    }

    @Override
    public void print() throws IOException {
        func_type.print();
        ident.print();
        lparent.print();
        if (func_f_params != null) {
            func_f_params.print();
        }
        rparent.print();
        block.print();
        printAstName(FuncDef.class);
    }

    @Override
    public void visit() {
        
        if (SymbolTable.checkDef(ident)) {
            TokenType tokenType = TokenType.getTokenType(func_type.toString());
            if(tokenType.equals(TokenType.VOIDTK)){
                this.returnType = SymbolType.VOID;
            }
            else{
                this.returnType = SymbolType.VAR;
            }
            FuncSymbol funcSymbol = new FuncSymbol(ident.toString(), this.returnType);
            SymbolTable.getCurrent().addSymbol(funcSymbol);
            SymbolTable.changeTo(SymbolTable.getCurrent().createSymbolTable());
            if (func_f_params != null) {
                funcSymbol.setParam_type_list(((FuncFParams) func_f_params).getSymbolTypeList());
                func_f_params.visit();
            }
            if (this.returnType.equals(SymbolType.VOID)) {
                SymbolTable.getCurrent().setFunc_block(1);
            } else {
                SymbolTable.getCurrent().setFunc_block(2);
            }
            block.visit();
            ((Block) block).checkReturn();
            SymbolTable.changeToFather();
        }
    }

    @Override
    public int getLineNumber() {
        return ident.getLineNumber();
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(func_type.toString()).append(' ').append(ident.toString()).append(lparent.toString());
        if (func_f_params != null) {
            content.append(func_f_params.toString());
        }
        content.append(rparent.toString()).append(block.toString());
        return content.toString();
    }

    @Override
    public Value generateIR() {
        Function function = new Function(ident.toString(), FunctionType.functionType);
        IRModule.getInstance().addFunction(function);
        IRGenerator.setCurFunction(function);
        function.setReturnType(this.returnType);
        BasicBlock basicBlock = new BasicBlock(null, null);
        function.addBasicBlock(basicBlock);
        basicBlock.setParent(function);
        IRGenerator.setCurBasicBlock(basicBlock);
        if(func_f_params!=null){
            func_f_params.generateIR();
        }
        block.generateIR();

        return function;
    }
}