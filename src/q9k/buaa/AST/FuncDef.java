package q9k.buaa.AST;

import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Frontend.Token.TokenType;
import q9k.buaa.Symbol.FuncSymbol;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolType;

import java.io.IOException;

public class FuncDef implements Syntax {

    private Syntax func_type;
    private Syntax ident;
    private Token lparent;
    private Syntax func_f_params;
    private Token rparent;
    private Syntax block;

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
        if(func_f_params != null){
            func_f_params.print();
        }
        rparent.print();
        block.print();
        printAstName(FuncDef.class);
    }

    @Override
    public void visit() {
        Ident temp = (Ident) ident;
        if(temp.visitDef()){
            FuncSymbol funcSymbol = new FuncSymbol(((Ident)ident).getTokenContent(),((FuncType)func_type).getFunc_type());
            SymbolTable.addSymbol(funcSymbol);
            SymbolTable current = SymbolTable.getCurrent();
            SymbolTable symbolTable = new SymbolTable();
            SymbolTable.changeToTable(symbolTable);
            if(func_f_params!=null){
                funcSymbol.setParam_type_list(((FuncFParams)func_f_params).getSymbolTypeList());
                func_f_params.visit();
            }
            SymbolType type = ((FuncType)func_type).getFunc_type();
            if(type.equals(SymbolType.VOID)){
                symbolTable.setFunc_block(1);
            }
            else{
                symbolTable.setFunc_block(2);
            }
            block.visit();
            ((Block)block).visitReturn();
            SymbolTable.changeToTable(current);
        }
    }

    @Override
    public int getLineNumber() {
        return ident.getLineNumber();
    }

}
