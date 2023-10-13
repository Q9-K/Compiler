package q9k.buaa.AST;

import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Symbol.SymbolType;
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
            item.getFirst().print();
            item.getSecond().print();
        }
        printAstName(FuncRParams.class);
    }

    @Override
    public void visit() {

    }

    @Override
    public int getLineNumber() {
        return exp.getLineNumber();
    }

    public Syntax getExp() {
        return exp;
    }

    public List<Syntax> getSymbolTypeList() {
        List<Syntax> list1 = new ArrayList<>();
        list1.add(exp);
        for(Tuple<Token, Syntax> item : list){
            list1.add(item.getSecond());
        }
        return list1;
    }

}
