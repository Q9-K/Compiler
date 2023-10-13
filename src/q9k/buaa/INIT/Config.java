package q9k.buaa.INIT;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Symbol.Symbol;
import q9k.buaa.Symbol.SymbolTable;

import java.io.IOException;

public class Config {
    private static String input_path="testfile.txt";
    private static String output_path="output.txt";
    private static String error_path="error.txt";
    public static boolean lexer_output_open = false;
    public static boolean parser_output_open = false;
    public static boolean error_output_open = false;
    public static Symbol root;
    public static System cur_symbol;
    public static String getInput_path() {
        return input_path;
    }

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

}
