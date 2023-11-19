package q9k.buaa.Symbol;

public enum SymbolType {
    VAR,
    ARRAY,
    MULTIARRAY,
    FUNCTION,
    VOID;

    @Override
    public String toString() {
        if(this.equals(VAR)){
            return "i32";
        }
        else if(this.equals(VOID)){
            return "void";
        }
        return "reversed";
    }
}
