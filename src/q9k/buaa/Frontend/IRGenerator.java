package q9k.buaa.Frontend;

import q9k.buaa.AST.Syntax;
import q9k.buaa.INIT.Config;
import q9k.buaa.IR.Argument;
import q9k.buaa.IR.BasicBlock;
import q9k.buaa.IR.Instructions.CallInst;
import q9k.buaa.IR.Types.FunctionType;
import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Types.PointerType;
import q9k.buaa.Symbol.SymbolType;
import q9k.buaa.Utils.IRModule;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.IR.Function;
import q9k.buaa.Utils.Output;
import java.io.IOException;

public class IRGenerator {
    public Syntax ast;
    private static IRGenerator irGenerator;
    private static Type cur_type;
    private static BasicBlock cur_basicblock;
    private static Function cur_function;

    private static CallInst cur_callInst;

    private static BasicBlock trueBasicBlock;
    private static BasicBlock falseBasicBlock;

    private static BasicBlock stepBasicBlock;
    private static BasicBlock loopFollowBlock;


    private static boolean global;




    private IRGenerator(Syntax ast){
        this.ast = ast;
    }
    public static synchronized IRGenerator getInstance(){
        if(irGenerator==null){
            System.out.println("Something wrong happened at irgenerator init!");
            System.exit(-1);
        }
        return irGenerator;
    }
    public static synchronized IRGenerator getInstance(Syntax ast){
        if(irGenerator==null||!irGenerator.ast.equals(ast)){
            irGenerator = new IRGenerator(ast);
        }
        return irGenerator;
    }

    public void run() throws IOException {
        IRModule irModule = IRModule.getInstance();
//        loadExternalFunction();
        ast.generateIR();
        System.out.println("IRModule generate finished!");
        if(Config.llvm_ir_output_open){
            Output output = Output.getInstance(Config.getLlvm_ir_path());
            output.write("declare i32 @getint()\n");
            output.write("declare void @putint(i32)\n");
            output.write("declare void @putch(i32)\n");
            output.write("declare void @putstr(i8*)\n");
            irModule.print();
        }
    }

    public static void setCurBasicBlock(BasicBlock basicBlock){
        IRGenerator.cur_basicblock = basicBlock;
    }

    public static BasicBlock getCurBasicBlock(){
        return IRGenerator.cur_basicblock;
    }

    public static void setCurFunction(Function function){
        IRGenerator.cur_function = function;
    }
    public static Function getCurFunction(){
        return IRGenerator.cur_function;
    }

    public static Type getCur_type(){
        return IRGenerator.cur_type;
    }
    public static void setCur_type(Type cur_type) {
        IRGenerator.cur_type = cur_type;
    }

    private void loadExternalFunction(){
        IRModule irModule = IRModule.getInstance();
        Function function = new Function("getint", FunctionType.FunctionType, true);
        function.setReturnType(SymbolType.VAR);
        irModule.addFunction(function);

        function = new Function("putint", FunctionType.FunctionType, true);
        function.setReturnType(SymbolType.VOID);
        function.addArgument(new Argument(null, IntegerType.i32));
        irModule.addFunction(function);

        function = new Function("putch", FunctionType.FunctionType, true);
        function.setReturnType(SymbolType.VOID);
        function.addArgument(new Argument(null, IntegerType.i32));
        irModule.addFunction(function);

        function = new Function("putstr",FunctionType.FunctionType, true);
        function.setReturnType(SymbolType.VOID);
        function.addArgument(new Argument(null, new PointerType(IntegerType.i8)));
        irModule.addFunction(function);
    }

    public static boolean isGlobal() {
        return global;
    }

    public static void setGlobal(boolean global) {
        IRGenerator.global = global;
    }

    public static void setCurCallInst(CallInst cur_callInst){
        IRGenerator.cur_callInst = cur_callInst;
    }
    public static CallInst getCurCallInst(){
        return IRGenerator.cur_callInst;
    }

    public static BasicBlock getTrueBasicBlock() {
        return trueBasicBlock;
    }

    public static void setTrueBasicBlock(BasicBlock trueBasicBlock) {
        IRGenerator.trueBasicBlock = trueBasicBlock;
    }

    public static BasicBlock getFalseBasicBlock() {
        return falseBasicBlock;
    }

    public static void setFalseBasicBlock(BasicBlock falseBasicBlock) {
        IRGenerator.falseBasicBlock = falseBasicBlock;
    }

    public static BasicBlock getStepBasicBlock() {
        return stepBasicBlock;
    }

    public static void setStepBasicBlock(BasicBlock stepBasicBlock) {
        IRGenerator.stepBasicBlock = stepBasicBlock;
    }

    public static BasicBlock getLoopFollowBlock() {
        return loopFollowBlock;
    }

    public static void setLoopFollowBlock(BasicBlock loopFollowBlock) {
        IRGenerator.loopFollowBlock = loopFollowBlock;
    }

}
