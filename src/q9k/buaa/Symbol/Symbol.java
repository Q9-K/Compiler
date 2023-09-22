//package q9k.buaa.Symbol;
//
//import q9k.buaa.Lexer.LexType;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class Symbol {
//    private ArrayList<Symbol> subSymbol;
//    private Symbol Father;
//    private HashMap<String, LexType> table;
//    public Symbol(Symbol Father){
//        this.Father = Father;
//    }
//    public Symbol(){
//        this.Father = null;
//    }
//
//    public LexType indentifySymbol(String token ){
//        Symbol symbol = this;
//        while(symbol!=null){
//            if(symbol.table.containsKey(token)){
//                return symbol.table.get(token);
//            }
//            else{
//                symbol = symbol.Father;
//            }
//        }
//    }
//}
