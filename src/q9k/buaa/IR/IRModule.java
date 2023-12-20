package q9k.buaa.IR;

import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Symbol.SymbolTableFactory;
import q9k.buaa.Utils.Output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IRModule {

    private List<Constant> globalValues;
    private List<Function> functions;
    private Map<String, Function> functionMap;

    private SymbolTable symbolTable;
    private static IRModule irModule;

    private IRModule() {
        this.globalValues = new ArrayList<>();
        this.functions = new ArrayList<>();
        this.functionMap = new HashMap<>();
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

    public Map<String, Function> getFunctionMap() {
        return functionMap;
    }

    public void addFunction(Function function) {
        this.functions.add(function);
        this.functionMap.put(function.getName(), function);
    }

    public void addGlobalVar(Constant globalValue) {
        this.globalValues.add(globalValue);
    }

    public void print(String file_path) throws IOException {
        Output output = Output.getInstance(file_path);
        output.write(irModule.toString());
    }


    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        for (Constant globalValue : globalValues) {
            if (globalValue.isLive()) {
                content.append(globalValue.toString()).append('\n');
            }
        }
        for (Function function : functions) {
            if (function.isLive()) {
                content.append(function.toString()).append('\n');
            }
        }
        return content.toString();
    }

    public String genMips() {
        StringBuilder content = new StringBuilder();
        content.append(".data\n");
        for (Constant globalValue : globalValues) {
            if(globalValue.isLive()){
                content.append(globalValue.genMips()).append('\n');
            }
        }
        content.append(".text\n").append("move $fp, $sp\n").append("jal main\n");
        content.append("li $v0, 10\n");
        content.append("syscall\n");
//        int index = 0;
//        for (; index < functions.size() - 1; ++index) {
//            Function function = functions.get(index);
//            content.append(function.genMips()).append('\n');
//        }
//        Function function = functions.get(index);
//        content.append(function.genMips()).append('\n');
        for (Function function : functions) {
            if (function.isLive()) {
                content.append(function.genMips()).append('\n');
            }
        }
        return content.toString();
    }

    public void optimize() {
        for (Constant constant : globalValues) {
            constant.optimize();
        }
        for (int index = 0; index < functions.size(); index++) {
            Function function = functions.get(index);
            function.optimize();
        }
    }
}
