package q9k.buaa.AST;

import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Token.Token;
import q9k.buaa.Symbol.SymbolType;
import q9k.buaa.Utils.Tuple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FuncFParams implements Syntax {
    private Syntax func_f_param;
    private List<Tuple<Token, Syntax>> list;
    private SymbolTable symbolTable;

    public FuncFParams(Syntax func_f_param, List<Tuple<Token, Syntax>> list) {
        this.func_f_param = func_f_param;
        this.list = list;
    }

    @Override
    public void print() throws IOException {
        func_f_param.print();
        for(Tuple<Token, Syntax> item : list){
            item.first().print();
            item.second().print();
        }
        printAstName(FuncFParams.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTable.getCurrent();
        func_f_param.visit();
        for(Tuple<Token, Syntax> item : list){
            item.second().visit();
        }
    }

    @Override
    public int getLineNumber() {
        return func_f_param.getLineNumber();
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(func_f_param.toString());
        for(Tuple<Token, Syntax> item : list){
            content.append(item.first().toString()).append(item.second().toString());
        }
        return content.toString();
    }


    public List<SymbolType> getSymbolTypeList(){
        List<SymbolType> symbolTypeList = new ArrayList<>();
        symbolTypeList.add(((FuncFParam)func_f_param).getSymbolType());
        for(Tuple<Token, Syntax> item : list){
            FuncFParam funcFParam = (FuncFParam) item.second();
            symbolTypeList.add(funcFParam.getSymbolType());
        }
        return symbolTypeList;
    }
}
