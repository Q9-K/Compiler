package q9k.buaa.Utils;

import q9k.buaa.INIT.Config;
import q9k.buaa.IR.Constant;
import q9k.buaa.IR.Function;
import q9k.buaa.IR.GlobalValue;
import q9k.buaa.Symbol.SymbolTable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IRModule {
    public static String GLOBAL_PREFIX = "%";
    public static String LOCAL_PREFIX = "@";
    public static String GLOBAL_NAME_PREFIX = "g_";
    public static String LOCAL_NAME_PREFIX = "v";
    public static String FPARAM_NAME_PREFIX = "f";

    private List<Constant> globalValues;
    private List<Function> functions;

    private SymbolTable symbolTable;
    private static IRModule irModule;

    private IRModule() {
        this.globalValues = new ArrayList<>();
        this.functions = new ArrayList<>();
        this.symbolTable = SymbolTable.getGlobal();

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

    public Function getCurFunction() {
        if (!functions.isEmpty()) {
            return functions.get(functions.size() - 1);
        }
        return null;
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

    public void removeGlobalVar(GlobalValue globalValue) {
        this.globalValues.remove(globalValue);
    }

    public void insertFunction(Function function) {
        this.functions.add(function);
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
}
