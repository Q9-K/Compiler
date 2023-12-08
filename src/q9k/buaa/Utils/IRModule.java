package q9k.buaa.Utils;

import q9k.buaa.INIT.Config;
import q9k.buaa.IR.Constant;
import q9k.buaa.IR.Function;
import q9k.buaa.IR.GlobalValue;
import q9k.buaa.IR.GlobalVariable;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IRModule {

    private List<Constant> globalValues;
    private List<Function> functions;

    private SymbolTable symbolTable;
    private static IRModule irModule;

    private IRModule() {
        this.globalValues = new ArrayList<>();
        this.functions = new ArrayList<>();
        this.symbolTable = SymbolTableFactory.getInstance().getGlobal();

    }

    public static synchronized IRModule getInstance() {
        if (irModule == null) {
            irModule = new IRModule();
        }
        return irModule;
    }

    public List<Constant> getGlobalVars() {
        return globalValues;
    }

    public List<Function> getFunctions() {
        return functions;
    }


    public void addFunction(Function function) {
        this.functions.add(function);
    }

    public void addGlobalVar(Constant globalValue) {
        this.globalValues.add(globalValue);
    }

    public void print() throws IOException {
        Output output = Output.getInstance(Config.getLlvm_ir_path());
        output.write(irModule.toString());
    }


    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        for (Constant globalValue : globalValues) {
            content.append(globalValue.toString()).append('\n');
        }
        for (Function function : functions){
            content.append(function.toString()).append('\n');
        }
        return content.toString();
    }

    public void translate(){
        for(Constant globalValue : globalValues){

        }
    }
}
