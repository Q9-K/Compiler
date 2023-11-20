package q9k.buaa.AST.Decl;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.Constant;
import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Instructions.AllocalInst;
import q9k.buaa.IR.Instructions.StoreInst;
import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Types.PointerType;
import q9k.buaa.IR.Value;
import q9k.buaa.Token.Token;
import q9k.buaa.Symbol.ArraySymbol;
import q9k.buaa.Symbol.Symbol;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.VarSymbol;
import q9k.buaa.Utils.Calculator;
import q9k.buaa.Utils.IRModule;
import q9k.buaa.Utils.Triple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConstDef implements Syntax {
    private Syntax ident;
    private List<Triple<Token, Syntax,Token>> list = new ArrayList<>();
    private Token assign_token;
    private Syntax const_init_val;
    private Symbol symbol;
    


    public ConstDef(Syntax ident, List<Triple<Token, Syntax, Token>> list, Token assign_token, Syntax const_init_val) {
        this.ident = ident;
        this.list = list;
        this.assign_token = assign_token;
        this.const_init_val = const_init_val;
    }

    @Override
    public void print() throws IOException {
        ident.print();
        for(Triple<Token, Syntax, Token> item : list){
            item.first().print();
            item.second().print();
            item.third().print();
        }
        assign_token.print();
        const_init_val.print();
        printAstName(ConstDef.class);
    }

    @Override
    public void visit() {
        
        if(SymbolTable.checkDef(ident)){
            if(list.isEmpty()){
                symbol = new VarSymbol(ident.toString());
            }
            else if(list.size() == 1){
                symbol = new ArraySymbol(ident.toString(),list.get(0).second(),null);
            }
            else {
                symbol = new ArraySymbol(ident.toString(),list.get(0).second(),list.get(1).second());
            }
            symbol.setConst(true);
            for(Triple<Token, Syntax, Token> item : list){
                item.second().visit();
            }
            const_init_val.visit();
            SymbolTable.getCurrent().addSymbol(symbol);
        }
    }


    @Override
    public int getLineNumber() {
        return const_init_val.getLineNumber();
    }

    @Override
    public Value generateIR() {
        if(IRGenerator.isGlobal()){
            Constant constant = new Constant("@"+ident.toString(), new PointerType(IntegerType.i32));
            constant.setInitializer(Calculator.getInstance().calculate(const_init_val));
            this.symbol.setIR(constant);
            IRModule.getInstance().addGlobalVar(constant);
            return constant;
        }
        else{
            Instruction instruction = new AllocalInst(null, new PointerType(IntegerType.i32));
            this.symbol.setIR(instruction);
            IRGenerator.getCurBasicBlock().addInstruction(instruction);
            Instruction storeInst = new StoreInst(null, null);
            storeInst.addOperand(instruction);
            storeInst.addOperand(const_init_val.generateIR());
            IRGenerator.getCurBasicBlock().addInstruction(storeInst);
        }
        return null;
    }


    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(ident.toString());
        for(Triple<Token, Syntax, Token> item : list){
            content.append(item.first().toString()).append(item.second().toString()).append(item.third().toString());
        }
        content.append(assign_token.toString()).append(const_init_val.toString());
        return content.toString();
    }



}
