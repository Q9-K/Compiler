package q9k.buaa.Symbol;

public class VarSymbol extends Symbol{
    public VarSymbol(int table_id, String content ){
        super(table_id, content);
        setSymbolType(SymbolType.VAR);
    }
    public VarSymbol(String content)
    {
        super(content);
        setSymbolType(SymbolType.VAR);
    }

}
