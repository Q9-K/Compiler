package q9k.buaa.INIT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class Output {
    private String file_path;
    private static Output output;

    private Output(String file_path) throws IOException {
        this.file_path = file_path;
        Files.write(Paths.get(this.file_path),new byte[0], StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING,StandardOpenOption.WRITE);
    }
    public static synchronized Output getInstance() throws IOException {
        if(output == null){
            System.out.println("something wrong happened at output init!");
            System.exit(-1);
        }
        return output;
    }
    public static synchronized Output getInstance(String file_path) throws IOException{
        if(output == null){
            output = new Output(file_path);
        }
        else if(!output.file_path.equals(file_path)){
            output = new Output(file_path);
        }
        return output;
    }
    public void write(StringBuffer log) throws IOException {
        //当文件不存在时创建，同时使用追加输出选项
        Files.write(Paths.get(this.file_path),log.toString().getBytes(), StandardOpenOption.APPEND);
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
