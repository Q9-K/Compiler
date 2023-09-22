package q9k.buaa.INIT;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Input {
    private String file_path = "";
    private StringBuffer file_content = null;
    public Input(String file_path)
    {
        this.file_path = file_path;
    }
    public StringBuffer transferToStream() {
        try {
            this.file_content = new StringBuffer(new String(Files.readAllBytes(Paths.get(this.file_path))));
        } catch (IOException e) {
//            throw new RuntimeException(e);
            System.out.println("file_path not exists!");
        }
        return this.file_content;
    }

}

