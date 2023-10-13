package q9k.buaa.Symbol;

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


    private Map<String, Symbol> symbolMap = new HashMap<>();

    public static SymbolTable getGlobal() {
        if (global == null) {
            global = new SymbolTable();
        }
        return global;
    }
    public static void clearTable(){
        global = null;
        current = null;
    }

    public static SymbolTable getCurrent() {
        return current;
    }

    public SymbolTable() {
        this.id = symbol_table_id++;
        this.father = current;
        if (this.father != null) {
            this.father.children.add(this);
            this.for_block = father.for_block;
            this.func_block = father.func_block;
        }
        children = new ArrayList<>();
    }

    public static void changeToTable(SymbolTable symbolTable) {
        current = symbolTable;
    }

    public int getId() {
        return id;
    }

    public static Symbol getSymbol(String token_content) {
        SymbolTable symbolTable = current;
        while (symbolTable != null) {
            if (symbolTable.symbolMap.containsKey(token_content)) {
                return symbolTable.symbolMap.get(token_content);
            }
            symbolTable = symbolTable.father;
        }
        return null;
    }

    public static Symbol getSymbolInCurrent(String token_content) {
        SymbolTable symbolTable = current;
        if (symbolTable.symbolMap.containsKey(token_content)) {
            return symbolTable.symbolMap.get(token_content);
        }
        return null;
    }

    public static void addSymbol(Symbol symbol) {
        current.symbolMap.put(symbol.getContent(), symbol);
    }
}
