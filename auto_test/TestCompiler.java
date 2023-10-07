package auto_test;
import q9k.buaa.Frontend.Lexer.LexerHandler;
import q9k.buaa.Frontend.Parse.ParseHandler;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.INIT.Config;
import q9k.buaa.INIT.Input;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class TestCompiler {
    public static void main(String[] args) throws IOException {
        String[] sub_dir_names = {"a","b","c"};
        String input_dir_root = "testfiles";
        String std_output_dir_root = "testfiles";
        String output_dir_root = "testfiles";
        int term = 1;
        for(String sub_dir_name : sub_dir_names){
            String input_dir = input_dir_root+"/"+sub_dir_name+"/"+"testfile";
            String output_dir = output_dir_root+"/"+sub_dir_name+"/"+"my_output";
            String std_output_dir = std_output_dir_root+"/"+sub_dir_name+"/"+"xyf";
            if(!Files.exists(Paths.get(output_dir))){
                Files.createDirectory(Paths.get(output_dir));
            }
            List<String> file_names = Files.walk(Paths.get(input_dir),1).filter(Files::isRegularFile).map(
                    path -> path.getFileName().toString()
            ).collect(Collectors.toList());
//            PrintStream printStream = System.out;
//            System.out.close();
            for(String file_name : file_names){
                String input_path = input_dir+"/"+file_name;
                String output_path = output_dir+"/"+"output"+file_name.replaceAll("testfile","");
                String std_output_path = std_output_dir+"/"+"output"+file_name.replaceAll("testfile","");
                System.out.println("--------"+"Compile "+term++ + "--------");
                System.out.println("--------"+"Compile file "+input_path+" now!--------");
                if(!Files.exists(Paths.get(output_path))){
                    Files.createFile(Paths.get(output_path));
                }
                try{
                    new TestCompiler().run(input_path,output_path);
                    LexerHandler.clearInstance();
                    ParseHandler.clearInstance();
                    List<String> list1 = Files.readAllLines(Paths.get(output_path));
                    List<String> list2 = Files.readAllLines(Paths.get(std_output_path));
                    List<String> finalList = list2.stream().filter(line ->
                            list1.stream().filter(line2 -> line2.equals(line)).count() == 0
                    ).collect(Collectors.toList());
                    if(finalList.size() == 0){
                        System.out.println("Compile result correct.Congratulations!");
                    }
                    else{
                        System.out.println("Wrong Answer!");
                        finalList.forEach(one -> System.out.println(one));
                    }
                }catch (RuntimeException e){
                    System.out.println("compile failed at file "+input_path+"!");
                    e.printStackTrace();
                }
            }

//            System.setOut(printStream);
        }
        System.exit(0);
    }
    public void run(String input_path, String output_path) throws IOException {
        //配置信息
        compilerInit(input_path, output_path);
        //获取字符流
        StringBuffer character_stream = Input.getInstance().getCharacterStream();
        //词法分析
        LexerHandler.getInstance(character_stream).run();
        //获取词法单元流
        List<Token> token_stream = LexerHandler.getInstance().getToken_stream();
        //语法分析
        ParseHandler.getInstance(token_stream).run();

        //错误处理
//        ErrorHandler.getInstance().run();
        //语法树
    }
    private void compilerInit() throws IOException {
        Config.init();
//        Config.setLexer_output_open(true);
        Config.setParser_output_open(true);
    }
    private void compilerInit(String input_path) throws IOException {
        Config.setInput_path(input_path);
        Config.init();
//        Config.setLexer_output_open(true);
        Config.setParser_output_open(true);
    }
    private void compilerInit(String input_path,String output_path) throws IOException {
        Config.setInput_path(input_path);
        Config.setOutput_path(output_path);
        Config.init();
//        Config.setLexer_output_open(true);
        Config.setParser_output_open(true);
    }
}
