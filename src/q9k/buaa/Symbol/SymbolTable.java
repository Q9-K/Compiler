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
    private static SymbolTable global;
    private static SymbolTable current = null;
    private int id;
    private List<SymbolTable> children;
    private SymbolTable father;
    private boolean for_block = false;
    private int func_block = 0;//0，非函数块; 1，无返回值; 2, 有返回值
    private Map<String, Symbol> symbolMap = new HashMap<>();


    public boolean isFor_block() {
        return for_block;
    }

    public void setFor_block(boolean for_block) {
        this.for_block = for_block;
    }

    public int isFunc_block() {
        return func_block;
    }

    public void setFunc_block(int func_block) {
        this.func_block = func_block;
    }


    public static SymbolTable getGlobal() {
        if (global == null) {
            global = new SymbolTable();
        }
        return global;
    }

    public static void clearTable() {
        global = null;
        current = null;
    }

    public static SymbolTable getCurrent() {
        return current;
    }

    private SymbolTable() {
        this.id = symbol_table_id++;
        children = new ArrayList<>();
    }

    public static void changeTo(SymbolTable symbolTable) {
        current = symbolTable;
    }

    public int getId() {
        return id;
    }


    public static Symbol getSymbol(Syntax syntax) {
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
        SymbolTable symbolTable = SymbolTable.current;
        while (symbolTable != null) {
            Symbol symbol = symbolTable.symbolMap.get(content);
            if (symbol != null) {
                return symbol;
            }
            symbolTable = symbolTable.father;
        }
        return null;
    }

    public SymbolTable createSymbolTable() {
        SymbolTable symbolTable = new SymbolTable();
        symbolTable.func_block = this.func_block;
        symbolTable.for_block = this.for_block;
        symbolTable.father = this;
        this.children.add(symbolTable);
        return symbolTable;
    }

    public void addSymbol(Symbol symbol) {
        this.symbolMap.put(symbol.toString(), symbol);
    }

    public static void changeToFather() {
        SymbolTable.current = SymbolTable.current.father;
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
        Symbol symbol = SymbolTable.getCurrent().symbolMap.get(content.substring(start, end));
        if (symbol != null) {
            ErrorHandler.getInstance().addError(new Error(ErrorType.REPEAEDNAME, syntax.getLineNumber()));
            return false;
        }
        return true;
    }

    public static boolean checkVarInvoke(Syntax syntax) {
        Symbol symbol = SymbolTable.getSymbol(syntax);
        if (symbol == null || symbol.getSymbolType().equals(SymbolType.FUNCTION)) {
            ErrorHandler.getInstance().addError(new Error(ErrorType.NOTDEFNAME, syntax.getLineNumber()));
            return false;
        }
        return true;
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
        Symbol symbol = SymbolTable.getGlobal().symbolMap.get(content);
        if (symbol == null || !symbol.getSymbolType().equals(SymbolType.FUNCTION)) {
            ErrorHandler.getInstance().addError(new Error(ErrorType.NOTDEFNAME, syntax.getLineNumber()));
            return false;
        }
        return true;
    }
}
