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
        }else if(this.equals(FUNCTION)){
            return "function";
        }else if(this.equals(MULTIARRAY)||this.equals(ARRAY)){
            return "array";
        }
        return "reversed";
    }
}
