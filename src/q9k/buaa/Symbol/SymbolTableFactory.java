package q9k.buaa.Symbol;

public class SymbolTableFactory {
    private static SymbolTableFactory symbolTableFactory;
    private SymbolTable current;
    private SymbolTable global;

    public static synchronized SymbolTableFactory getInstance() {
        if (symbolTableFactory == null) {
            symbolTableFactory = new SymbolTableFactory();
        }
        return symbolTableFactory;
    }

    public SymbolTable getCurrent() {
        return current;
    }

    public void setCurrent(SymbolTable current) {
        this.current = current;
    }

    public SymbolTable getGlobal() {
        if(global == null){
            global = new SymbolTable();
        }
        return global;
    }

    public void setGlobal(SymbolTable global) {
        this.global = global;
    }

    public SymbolTable createSymbolTable() {
        SymbolTable symbolTable = new SymbolTable();
        symbolTable.setFuncBlock(this.current.getFuncBlock());
        symbolTable.setForBlock(this.current.isForBlock());
        symbolTable.setFather(this.current);
        this.current.getChildren().add(symbolTable);
        return symbolTable;
    }
}
