package q9k.buaa.INIT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class LexerOutput {
    private final String file_path;
    private static LexerOutput lexerOutput;

    public LexerOutput(String file_path) throws IOException {
        this.file_path = file_path;
        Files.write(Paths.get(this.file_path),new byte[0], StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING,StandardOpenOption.WRITE);
    }
    public static synchronized LexerOutput getInstance(String file_path) throws IOException {
        if(lexerOutput == null){
            lexerOutput = new LexerOutput(file_path);
        }
        return lexerOutput;
    }
    public static synchronized LexerOutput getInstance() throws IOException {
        if(lexerOutput == null){
            lexerOutput = new LexerOutput("output.txt");
        }
        return lexerOutput;
    }
    public void write(StringBuffer log) throws IOException {
        //当文件不存在时创建，同时使用追加输出选项
        Files.write(Paths.get(this.file_path),log.toString().getBytes(), StandardOpenOption.APPEND);
    }

}
