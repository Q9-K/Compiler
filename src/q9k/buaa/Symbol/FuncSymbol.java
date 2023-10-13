package q9k.buaa.Symbol;

import q9k.buaa.Frontend.Token.*;

import java.util.ArrayList;
import java.util.List;

public class FuncSymbol extends Symbol{
    private SymbolType return_type;
    private List<SymbolType> param_type_list = new ArrayList<>();


    public FuncSymbol(int table_id, String content, SymbolType return_type) {
        super(table_id, content);
        this.return_type = return_type;
        setSymbolType(SymbolType.FUNCTION);
    }

    public FuncSymbol(String content, SymbolType return_type) {
        super(content);
        this.return_type = return_type;
        setSymbolType(SymbolType.FUNCTION);
    }

    public SymbolType getReturn_type() {
        return return_type;
    }

    public void setReturn_type(SymbolType return_type) {
        this.return_type = return_type;
    }

    public int getParam_num() {
        return param_type_list.size();
    }


    public List<SymbolType> getParam_type_list() {
        return param_type_list;
    }

    public void setParam_type_list(List<SymbolType> param_type_list) {
        this.param_type_list = param_type_list;
    }

    @Override
    public boolean isConst() {
        return false;
    }
}
