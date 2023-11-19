package q9k.buaa.AST.Function;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.Value;
import q9k.buaa.Token.Token;
import q9k.buaa.Utils.Tuple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FuncRParams implements Syntax {
    private Syntax exp;
   private List<Tuple<Token, Syntax>> list;
   

    public FuncRParams(Syntax exp, List<Tuple<Token, Syntax>> list) {
        this.exp = exp;
        this.list = list;
    }

    @Override
    public void print() throws IOException {
        exp.print();
        for(Tuple<Token, Syntax> item : list){
            item.first().print();
            item.second().print();
        }
        printAstName(FuncRParams.class);
    }

    @Override
    public void visit() {
        exp.visit();
        for(Tuple<Token, Syntax> item : list){
            item.second().visit();
        }
    }

    @Override
    public int getLineNumber() {
        return exp.getLineNumber();
    }

    @Override
    public Value generateIR() {
        IRGenerator.getCurCallInst().addParam(exp.generateIR());
        for(Tuple<Token, Syntax> item : list){
            IRGenerator.getCurCallInst().addParam(item.second().generateIR());
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(exp.toString());
        for(Tuple<Token, Syntax> item : list){
            content.append(item.first().toString()).append(item.second().toString());
        }
        return content.toString();
    }


    public List<Syntax> getSymbolTypeList() {
        List<Syntax> list1 = new ArrayList<>();
        list1.add(exp);
        for(Tuple<Token, Syntax> item : list){
            list1.add(item.second());
        }
        return list1;
    }

}
