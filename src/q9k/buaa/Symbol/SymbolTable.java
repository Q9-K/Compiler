package q9k.buaa.Symbol;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymbolTable {
    private static int symbol_table_id = 0;
    private int id;
    private List<SymbolTable> children;
    private SymbolTable father;
    private boolean for_block = false;
    private int func_block = 0;//0，非函数块; 1，无返回值; 2, 有返回值
    private Map<String, Symbol> symbolMap = new HashMap<>();


    public boolean isForBlock() {
        return for_block;
    }

    public void setForBlock(boolean for_block) {
        this.for_block = for_block;
    }

    public int getFuncBlock() {
        return func_block;
    }

    public void setFuncBlock(int func_block) {
        this.func_block = func_block;
    }

    public void setFather(SymbolTable father){
        this.father = father;
    }

    public SymbolTable getFather() {
        return father;
    }

    public void setChildren(List<SymbolTable> children) {
        this.children = children;
    }

    public List<SymbolTable> getChildren() {
        return children;
    }

    public SymbolTable() {
        this.id = symbol_table_id++;
        children = new ArrayList<>();
    }

    public int getId() {
        return id;
    }


    public Symbol getSymbol(String content) {
        SymbolTable symbolTable = this;
        while (symbolTable != null) {
            Symbol symbol = symbolTable.symbolMap.get(content);
            if (symbol != null) {
                return symbol;
            }
            symbolTable = symbolTable.father;
        }
        return null;
    }

    public Symbol getSymbol(Syntax syntax) {
        String content = syntax.toString();
        int index = 0;
        while ((index < content.length()) && !(Character.isLetterOrDigit(content.charAt(index)) || content.charAt(index) == '_')) {
            index++;
        }
        int start = index;
        while ((index < content.length()) && (Character.isLetterOrDigit(content.charAt(index)) || content.charAt(index) == '_')) {
            index++;
        }
        int end = index;
        content = content.substring(start, end);
        return getSymbol(content);
    }

    public void addSymbol(Symbol symbol) {
        this.symbolMap.put(symbol.toString(), symbol);
    }


    public static boolean checkDef(Syntax syntax) {
        String content = syntax.toString();
        int index = 0;
        while ((index < content.length()) && !(Character.isLetterOrDigit(content.charAt(index)) || content.charAt(index) == '_')) {
            index++;
        }
        int start = index;
        while ((index < content.length()) && (Character.isLetterOrDigit(content.charAt(index)) || content.charAt(index) == '_')) {
            index++;
        }
        int end = index;
        Symbol symbol = SymbolTableFactory.getInstance().getCurrent().symbolMap.get(content.substring(start, end));
        if (symbol != null) {
            ErrorHandler.getInstance().addError(new Error(ErrorType.REPEAEDNAME, syntax.getLineNumber()));
            return false;
        }
        return true;
    }

    public static Symbol checkVarInvoke(Syntax syntax) {
        Symbol symbol = SymbolTableFactory.getInstance().getCurrent().getSymbol(syntax);
        if (symbol == null || symbol.getSymbolType().equals(SymbolType.FUNCTION)) {
            ErrorHandler.getInstance().addError(new Error(ErrorType.NOTDEFNAME, syntax.getLineNumber()));
            return null;
        }
        return symbol;
    }

    public static boolean checkFuncInvoke(Syntax syntax) {
        String content = syntax.toString();
        int index = 0;
        while ((index < content.length()) && !(Character.isLetterOrDigit(content.charAt(index)) || content.charAt(index) == '_')) {
            index++;
        }
        int start = index;
        while ((index < content.length()) && (Character.isLetterOrDigit(content.charAt(index)) || content.charAt(index) == '_')) {
            index++;
        }
        int end = index;
        content = content.substring(start, end);
        Symbol symbol = SymbolTableFactory.getInstance().getGlobal().symbolMap.get(content);
        if (symbol == null || !symbol.getSymbolType().equals(SymbolType.FUNCTION)) {
            ErrorHandler.getInstance().addError(new Error(ErrorType.NOTDEFNAME, syntax.getLineNumber()));
            return false;
        }
        return true;
    }
}
