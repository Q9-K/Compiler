package q9k.buaa.INIT;

import q9k.buaa.frontend.Lexer.LexerHandler;

import java.io.IOException;

public class Config {
    private static String input_path="testfile.txt";
    private static String output_path="output.txt";
    private static String error_path="error.txt";
    public static boolean lexer_output_open = false;
    public static boolean parser_output_open = false;
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
        Input input = Input.getInstance(input_path);
        Output output = Output.getInstance(output_path);
    }
    public static void init(String input_path) throws IOException {
        Input input = Input.getInstance(input_path);
        Output output = Output.getInstance(output_path);
    }
    public static void init(String input_path,String output_path) throws IOException {
        Input input = Input.getInstance(input_path);
        Output output = Output.getInstance(output_path);
        LexerHandler lexerHandler = LexerHandler.getInstance(input.transferToStream());
    }
    public static void setLexer_output_open(boolean lexer_output_open) {
        Config.lexer_output_open = lexer_output_open;
    }
    public static void setParser_output_open(boolean parser_output_open) {
        Config.parser_output_open = parser_output_open;
    }
}
