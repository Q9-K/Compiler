import q9k.buaa.INIT.Input;
import q9k.buaa.Lexer.Lexer;

import java.io.IOException;

/**
 * @author Q9K
 * @date 2023/09/19
 */
public class Compiler {
    public static void main(String[] args) throws IOException {
        String file_path = "testfile.txt";
        StringBuffer source_code;
        Lexer lexer;
        if(args.length>0){
            file_path=args[0];
        }
        Input input = new Input(file_path);
        source_code = input.transferToStream();
        lexer = Lexer.getLexer(source_code);
        lexer.run();
        System.exit(0);
    }
}
