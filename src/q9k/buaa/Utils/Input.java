package q9k.buaa.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Input {
    private String file_path;
    private StringBuffer character_stream;
    private static Input input;
    public Input(String file_path)
    {
        this.file_path = file_path;
    }
    public static synchronized Input getInstance(){
        if(input == null){
            System.out.println("something wrong happened at input init!");
            System.exit(-1);
        }
        return input;
    }
    public static synchronized Input getInstance(String file_path){
        if(input == null){
            input = new Input(file_path);
        }
        else if(!input.file_path.equals(file_path)){
            input = new Input(file_path);
        }
        return input;
    }

    public StringBuffer getCharacterStream() {
        try {
            this.character_stream = new StringBuffer(new String(Files.readAllBytes(Paths.get(this.file_path))));
        } catch (IOException e) {
            System.out.println("Input_file does not exist!");
        }
        return this.character_stream;
    }

}

