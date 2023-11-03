package q9k.buaa.AST.Stmt;

import q9k.buaa.AST.FormatString;
import q9k.buaa.AST.Syntax;
import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Token.*;
import q9k.buaa.Utils.Tuple;

import java.io.IOException;
import java.util.List;

public class PrintfStmt implements Stmt{
    private Token printf_token;
    private Token lparent_token;
    private Syntax format_string;
    private List<Tuple<Token, Syntax>> list;
    private Token rparent_token;
    private Token semicn_token;
    private SymbolTable symbolTable;

    public PrintfStmt(Token printf_token, Token lparent_token, Syntax format_string, List<Tuple<Token, Syntax>> list, Token rparent_token, Token semicn_token) {
        this.printf_token = printf_token;
        this.lparent_token = lparent_token;
        this.format_string = format_string;
        this.list = list;
        this.rparent_token = rparent_token;
        this.semicn_token = semicn_token;
    }

    @Override
    public void print() throws IOException {
        printf_token.print();
        lparent_token.print();
        format_string.print();
        for(Tuple<Token, Syntax> item : list){
            item.first().print();
            item.second().print();
        }
        rparent_token.print();
        semicn_token.print();
        printAstName(Stmt.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTable.getCurrent();
        int count = getParamNum();
        if(count!=list.size()){
            ErrorHandler.getInstance().addError(new Error(ErrorType.NOTPRINTFIT, getLineNumber()));
        }
        format_string.visit();
        for(Tuple<Token, Syntax> item : list){
            item.second().visit();
        }
    }

    @Override
    public int getLineNumber() {
        return printf_token.getLineNumber();
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(printf_token.toString()).append(lparent_token.toString()).append(format_string.toString());
        for(Tuple<Token, Syntax> item : list){
            content.append(item.first().toString()).append(item.second().toString());
        }
        content.append(rparent_token.toString()).append(semicn_token.toString());
        return content.toString();
    }


    private int getParamNum() {
        String content = format_string.toString();
        int count = 0;
        int last_index = 0;
        while (last_index != -1) {
            last_index = content.indexOf("%d", last_index);
            if (last_index != -1) {
                count++;
                last_index += 2;
            }
        }
        return count;
    }

}
