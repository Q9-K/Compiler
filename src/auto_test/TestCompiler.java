package auto_test;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Frontend.Lexer.LexerHandler;
import q9k.buaa.Frontend.Parse.ParseHandler;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.Frontend.Visitor.Visitor;
import q9k.buaa.INIT.Config;
import q9k.buaa.INIT.Input;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestCompiler {
    public static void main(String[] args) throws IOException {
        String[] sub_dir_names = {"A","B","C"};
        String input_dir_root = "testfiles";
        String std_output_dir_root = "testfiles";
        String output_dir_root = "testfiles";
        String error_dir_root = "testfiles";
        int term = 1;
        for (String sub_dir_name : sub_dir_names) {
            String input_dir = input_dir_root + "/" + sub_dir_name + "/" + "testfile";
            String output_dir = output_dir_root + "/" + sub_dir_name + "/" + "my_output";
            String error_dir = error_dir_root + "/" + sub_dir_name + "/" + "error_output";
            String std_output_dir = std_output_dir_root + "/" + sub_dir_name + "/" + "output";
            Path dir = Paths.get(output_dir);
            if (!Files.exists(dir)) {
                Files.createDirectory(dir);
            }
            Path path1 = Paths.get(error_dir);
            if(!Files.exists(path1)){
                Files.createDirectory(path1);
            }
            List<String> file_names = Files.walk(Paths.get(input_dir), 1).filter(Files::isRegularFile).map(
                    path -> path.getFileName().toString()
            ).toList();
//            PrintStream printStream = System.out;
//            System.out.close();
            for (String file_name : file_names) {
                String input_path = input_dir + "/" + file_name;
                String output_path = output_dir + "/" + "output" + file_name.replaceAll("testfile", "");
                String error_path = error_dir + "/" + "error" + file_name.replaceAll("testfile", "");
                String std_output_path = std_output_dir + "/" + "output" + file_name.replaceAll("testfile", "");
                System.out.println("--------" + "Compile " + term++ + "--------");
                System.out.println("--------" + "Compile file " + input_path + " now!--------");
                Path path = Paths.get(error_path);
                if (!Files.exists(path)) {
                    Files.createFile(path);
                }
                try {
                    LexerHandler.clearInstance();
                    ParseHandler.clearInstance();
                    ErrorHandler.clearInstance();
                    new TestCompiler().run(input_path, output_path, error_path);
                    List<String> list1 = Files.readAllLines(path);
                    List<String> list2 = Files.readAllLines(Paths.get(std_output_path));
                    List<String> mismatchedLines = new ArrayList<>();
//                    while (list1.get(list1.size() - 1).isEmpty()) {
//                        list1.remove(list1.size() - 1);
//                    }
//                    while (list2.get(list1.size() - 1).isEmpty()) {
//                        list2.remove(list1.size() - 1);
//                    }
//                    if (list1.size() != list2.size()) {
//                        System.out.println("输出行数有误！");
//                        System.out.println("你的输出行数为"+list1.size());
//                        System.out.println("答案输出行数为"+list2.size());
//                    } else {
//
//                        for (int i = 0; i < list1.size(); i++) {
//                            if (!list1.get(i).equals(list2.get(i))) {
//                                mismatchedLines.add("Line " + (i + 1) + ":");
//                                mismatchedLines.add("Your_answer:    " + list1.get(i));
//                                mismatchedLines.add("Correct_answer: " + list2.get(i));
//                            }
//                        }
//                        if (mismatchedLines.isEmpty()) {
//                            System.out.println("Compile result correct. Congratulations!");
//                        } else {
//                            System.out.println("Wrong Answer!");
//
//                            for (String line : mismatchedLines) {
//                                System.out.println(line);
//                            }
//                        }
//                    }
                    if(!list1.isEmpty()){
                        System.out.println("WA!");
                    }
                    else{
                        System.out.println("AC!");
                    }

                } catch (RuntimeException e) {
                    System.out.println("compile failed at file " + input_path + "!");
                    System.out.println(Arrays.toString(e.getStackTrace()));
                }
            }

//            System.setOut(printStream);
        }
        System.exit(0);
    }

    public void run(String input_path, String output_path, String error_path) throws IOException {
        //配置信息
        compilerInit(input_path, output_path, error_path);
        //获取字符流
        StringBuffer character_stream = Input.getInstance().getCharacterStream();
        //词法分析
        LexerHandler.getInstance(character_stream).run();
        //获取词法单元流
        List<Token> token_stream = LexerHandler.getInstance().getToken_stream();
        //语法分析
        ParseHandler.getInstance(token_stream).run();
        //语法树
        Syntax root = ParseHandler.getInstance().getAst();
        //语义分析
        Visitor.getInstance(root).run();
        //语法树
    }

    private void compilerInit() throws IOException {
        Config.init();
        Config.setError_output_open(true);
//        Config.setLexer_output_open(true);
//        Config.setParser_output_open(true);

    }

    private void compilerInit(String input_path) throws IOException {
        Config.setInput_path(input_path);
        Config.setError_output_open(true);
        Config.init();
//        Config.setLexer_output_open(true);
//        Config.setParser_output_open(true);


    }

    private void compilerInit(String input_path, String output_path) throws IOException {
        Config.setInput_path(input_path);
        Config.setOutput_path(output_path);
        Config.setError_output_open(true);
        Config.init();
//        Config.setLexer_output_open(true);
//        Config.setParser_output_open(true);

    }

    private void compilerInit(String input_path, String output_path, String error_path) throws IOException {
        Config.setInput_path(input_path);
        Config.setOutput_path(output_path);
        Config.setError_path(error_path);
        Config.setError_output_open(true);
        Config.init();

//        Config.setLexer_output_open(true);
//        Config.setParser_output_open(true);
    }

}
