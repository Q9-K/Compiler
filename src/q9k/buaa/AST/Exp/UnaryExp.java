package q9k.buaa.AST.Exp;

import q9k.buaa.AST.FuncRParams;
import q9k.buaa.AST.Ident;
import q9k.buaa.AST.Syntax;
import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Symbol.FuncSymbol;
import q9k.buaa.Symbol.Symbol;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UnaryExp implements Syntax, Type {
    private Syntax primary_exp;
    private Syntax ident;
    private Token lparent;
    private Syntax func_r_params;
    private Token rparent;
    private Syntax unary_op;
    private Syntax unary_exp;

    public UnaryExp(Syntax primary_exp, Syntax ident, Token lparent, Syntax func_r_params, Token rparent, Syntax unary_op, Syntax unary_exp) {
        this.primary_exp = primary_exp;
        this.ident = ident;
        this.lparent = lparent;
        this.func_r_params = func_r_params;
        this.rparent = rparent;
        this.unary_op = unary_op;
        this.unary_exp = unary_exp;
    }

    @Override
    public void print() throws IOException {
        if (primary_exp != null) {
            primary_exp.print();
        } else if (ident != null) {
            ident.print();
            lparent.print();
            if (func_r_params != null) {
                func_r_params.print();
            }
            rparent.print();
        } else if (unary_op != null) {
            unary_op.print();
            unary_exp.print();
        }
        printAstName(UnaryExp.class);
    }

    @Override
    public void visit() {
        if (primary_exp != null) {

            primary_exp.visit();
        } else if (ident != null) {
            Ident temp1 = (Ident) ident;
            if (temp1.visitInvoke()) {
                if (!temp1.getSymbolType().equals(SymbolType.FUNCTION)) {
                    ErrorHandler.getInstance().addError(new Error(ErrorType.NOTDEFNAME, ident.getLineNumber()));
                } else {
                    FuncSymbol funcSymbol = (FuncSymbol) SymbolTable.getSymbol(temp1.getTokenContent());
                    List<Syntax> list = new ArrayList<>();
                    FuncRParams temp2 = (FuncRParams) func_r_params;
                    if (temp2 != null) {
                        list = temp2.getSymbolTypeList();
                    }
                    if (funcSymbol != null) {
                        if (funcSymbol.getParam_num() != list.size()) {
                            ErrorHandler.getInstance().addError(new Error(ErrorType.NOTPARAMNUMFIT, getLineNumber()));
                        } else {

                            List<SymbolType> symbolTypeList = funcSymbol.getParam_type_list();
                            for (int i = 0; i < list.size(); ++i) {
                                Exp exp = (Exp) list.get(i);
                                if (exp.getSymbolType() != null) {
                                    if (!symbolTypeList.get(i).equals(exp.getSymbolType())) {
                                        ErrorHandler.getInstance().addError(new Error(ErrorType.NOTPARAMTYPEFIT, getLineNumber()));
                                        break;
                                    }
                                } else {
                                    ErrorHandler.getInstance().addError(new Error(ErrorType.NOTDEFNAME, getLineNumber()));
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        } else if (unary_op != null) {
            unary_exp.visit();
        }
    }

    @Override
    public int getLineNumber() {
        if (primary_exp != null) {
            return primary_exp.getLineNumber();
        } else if (ident != null) {
            return ident.getLineNumber();
        } else {
            return unary_exp.getLineNumber();
        }
    }

    @Override
    public SymbolType getSymbolType() {
        if (primary_exp != null) {
            return ((PrimaryExp) primary_exp).getSymbolType();
        } else if(ident!=null){
            Symbol symbol = SymbolTable.getSymbol(((Ident) ident).getTokenContent());
            if (symbol == null) {
                return null;
            } else {
                SymbolType symbolType = symbol.getSymbolType();
                if (symbolType.equals(SymbolType.FUNCTION)) {
                    FuncSymbol funcSymbol = (FuncSymbol) SymbolTable.getSymbol(((Ident) ident).getTokenContent());
                    if (funcSymbol == null) {
                        return null;
                    }
                    symbolType = funcSymbol.getReturn_type();
                }
                return symbolType;
            }
        }
        else{
            return ((UnaryExp)unary_exp).getSymbolType();
        }
    }
}
