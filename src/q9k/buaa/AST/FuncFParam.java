package q9k.buaa.AST;

import q9k.buaa.Symbol.ArraySymbol;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolType;
import q9k.buaa.Symbol.VarSymbol;
import q9k.buaa.Token.Token;
import q9k.buaa.Utils.Triple;

import java.io.IOException;
import java.util.List;

public class FuncFParam implements Syntax {
    private Syntax b_type;
    private Syntax ident;
    private Token lbrack = null;
    private Token rbrack = null;
    private List<Triple<Token, Syntax, Token>> list;

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
        this.symbolTable = SymbolTable.getCurrent();
        if (SymbolTable.checkDef(ident)) {
            if (lbrack == null) {
                SymbolTable.getCurrent().addSymbol(new VarSymbol(ident.toString()));
            } else if (list.isEmpty()) {
                SymbolTable.getCurrent().addSymbol(new ArraySymbol(ident.toString(), null, null));
            } else {
                SymbolTable.getCurrent().addSymbol(new ArraySymbol(ident.toString(), null, list.get(0).second()));
            }
        }
    }

    @Override
    public int getLineNumber() {
        return ident.getLineNumber();
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
