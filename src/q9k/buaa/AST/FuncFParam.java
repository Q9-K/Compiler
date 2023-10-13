package q9k.buaa.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Symbol.ArraySymbol;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolType;
import q9k.buaa.Symbol.VarSymbol;
import q9k.buaa.Utils.Triple;

import java.io.IOException;
import java.util.List;

public class FuncFParam implements Syntax {
    private Syntax b_type;
    private Syntax ident;
    private Token lbrack = null;
    private Token rbrack = null;
    private List<Triple<Token, Syntax, Token>> list;

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
            item.getFirst().print();
            item.getSecond().print();
            item.getThird().print();
        }
        printAstName(FuncFParam.class);
    }

    @Override
    public void visit() {
        if (SymbolTable.getSymbolInCurrent(((Ident) ident).getTokenContent()) != null) {
            ErrorHandler.getInstance().addError(new Error(ErrorType.REPEAEDNAME, getLineNumber()));
        } else {
            if (lbrack == null) {
                SymbolTable.addSymbol(new VarSymbol(((Ident) ident).getTokenContent()));
            } else if (list.isEmpty()) {
                SymbolTable.addSymbol(new ArraySymbol(((Ident) ident).getTokenContent(), null, null));
            } else {
                SymbolTable.addSymbol(new ArraySymbol(((Ident) ident).getTokenContent(), null, list.get(0).getSecond()));
            }
        }
    }

    @Override
    public int getLineNumber() {
        return ident.getLineNumber();
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
