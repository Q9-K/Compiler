package q9k.buaa.AST;

import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Instructions.LoadInst;
import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.Symbol;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Token.Token;
import q9k.buaa.Utils.Triple;

import java.io.IOException;
import java.util.List;

public class LVal implements Syntax {

    private Syntax ident;
    private List<Triple<Token, Syntax, Token>> list;
    private Symbol symbol;

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
        this.symbol = SymbolTable.getCurrent().checkVarInvoke(ident);
        for (Triple<Token, Syntax, Token> item : list) {
            item.second().visit();
        }
    }

    @Override
    public int getLineNumber() {
        return ident.getLineNumber();
    }

    @Override
    public Value generateIR() {
        if (list.isEmpty()) {
            Instruction instruction = new LoadInst(null, IntegerType.i32);
            instruction.addOperand(this.symbol.getIR());
            IRGenerator.getCurBasicBlock().addInstruction(instruction);
            return instruction;
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


}
