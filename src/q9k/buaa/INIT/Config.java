package q9k.buaa.INIT;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Symbol.Symbol;
import q9k.buaa.Symbol.SymbolTable;
import q9k.buaa.Utils.Input;
import q9k.buaa.Utils.Output;

import java.io.IOException;

public class Config {
    private static String input_path="testfile.txt";
    private static String output_path="output.txt";
    private static String error_path="error.txt";
    private static String llvm_ir_path="llvm_ir.txt";
    private static String mips_path = "mips.txt";
    public static boolean lexer_output_open = false;
    public static boolean parser_output_open = false;
    public static boolean error_output_open = false;
    public static boolean llvm_ir_output_open = true;
    public static boolean mips_output_open = false;

    public static void setInput_path(String input_path) {
        Config.input_path = input_path;
    }

    public static String getOutput_path() {
        return output_path;
    }

    public static void setOutput_path(String output_path) {
        Config.output_path = output_path;
    }

    public static String getError_path() {
        return error_path;
    }

    public static void setError_path(String error_path) {
        Config.error_path = error_path;
    }
    public static String getLlvm_ir_path(){
        return Config.llvm_ir_path;
    }
    public static void setLlvm_ir_path(String llvm_ir_path){
        Config.llvm_ir_path = llvm_ir_path;
    }
    public static String getMips_path(){
        return Config.mips_path;
    }
    public static void setMips_path(String mips_path){
        Config.mips_path = mips_path;
    }
    public static void init() throws IOException {
        Input input = Input.getInstance(input_path);//input初始化
        Output output = Output.getInstance(output_path);//output初始化
        ErrorHandler errorHandler = ErrorHandler.getInstance(error_path);//错误处理初始化
        SymbolTable.clearTable();
    }
    public static void setLexer_output_open(boolean lexer_output_open) {
        Config.lexer_output_open = lexer_output_open;
    }
    public static void setParser_output_open(boolean parser_output_open) {
        Config.parser_output_open = parser_output_open;
    }
    public static void setError_output_open(boolean error_output_open){
        Config.error_output_open = error_output_open;
    }

    public static void printInfo(){
        System.out.println("当前编译器配置:");
        System.out.println("词法分析输出: "+(lexer_output_open?"开":"关")+" >> "+(lexer_output_open?getOutput_path():""));
        System.out.println("语法分析输出: "+(parser_output_open?"开":"关")+" >> "+(parser_output_open?getOutput_path():""));
        System.out.println("错误处理输出: "+(error_output_open?"开":"关")+" >> "+(error_output_open?getError_path():""));
        System.out.println("llvm_ir输出: "+(llvm_ir_output_open?"开":"关")+" >> "+(llvm_ir_output_open?getLlvm_ir_path():""));
        System.out.println("mips输出: "+(mips_output_open?"开":"关")+" >> "+(mips_output_open?getMips_path():""));
    }

}
