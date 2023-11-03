package q9k.buaa.Symbol;

public abstract class Symbol {
    public static int symbol_id = 1;
    private int id;
    private int table_id;
    private String content;
    private SymbolType symbolType;
    private boolean is_const;

    public Symbol(int table_id, String content) {
        this.table_id = table_id;
        this.content = content;
        this.id = symbol_id++;
    }

    public Symbol(String content) {
        this.table_id = SymbolTable.getCurrent().getId();
        this.content = content;
        this.id = symbol_id++;
    }


    @Override
    public String toString() {
        return content;
    }

    public SymbolType getSymbolType() {
        return symbolType;
    }

    public void setSymbolType(SymbolType symbolType) {
        this.symbolType = symbolType;
    }
    public boolean isConst(){
        return this.is_const;
    }
    public void setConst(boolean is_const){
        this.is_const = is_const;
    }
}
