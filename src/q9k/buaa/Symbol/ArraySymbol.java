package q9k.buaa.Symbol;

import q9k.buaa.AST.Syntax;

public class ArraySymbol extends Symbol{
    private Syntax dim1;
    private Syntax dim2;

    public ArraySymbol(int table_id, String content, Syntax dim1, Syntax dim2) {
        super(table_id, content);
        this.dim1 = dim1;
        this.dim2 = dim2;
        if(dim2==null){
            setSymbolType(SymbolType.ARRAY);
        }
        else{
            setSymbolType(SymbolType.MULTIARRAY);
        }
    }

    public ArraySymbol(String content, Syntax dim1, Syntax dim2) {
        super(content);
        this.dim1 = dim1;
        this.dim2 = dim2;
        if(dim2==null){
            setSymbolType(SymbolType.ARRAY);
        }
        else{
            setSymbolType(SymbolType.MULTIARRAY);
        }
    }


}
