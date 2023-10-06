import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.INIT.Config;
import q9k.buaa.INIT.Input;
import q9k.buaa.Frontend.Lexer.LexerHandler;
import q9k.buaa.Frontend.Parse.ParseHandler;
import q9k.buaa.Frontend.Token.Token;

import java.io.IOException;
import java.util.List;

/**
 * @author Q9K
 * @date 2023/09/19
 */
public class Compiler {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to use Q9K Compiler");
        //配置信息
        Config.init();
        Config.setLexer_output_open(true);
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
        System.exit(0);
    }
}
