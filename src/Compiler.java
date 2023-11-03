import q9k.buaa.AST.Syntax;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.Frontend.Visitor;
import q9k.buaa.INIT.Config;
import q9k.buaa.Utils.Input;
import q9k.buaa.Frontend.Lexer;
import q9k.buaa.Frontend.Parser;
import q9k.buaa.Token.Token;

import java.io.IOException;
import java.util.List;

/**
 * @author Q9K
 * @date 2023/09/19
 */
public class Compiler {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to use Q9K Compiler!");
        new Compiler().run();
    }

    public void run() throws IOException{
        //配置信息
        compilerInit();
        //获取字符流
        StringBuffer character_stream = Input.getInstance().getCharacterStream();
        //前端部分开始
        //词法分析
        Lexer.getInstance(character_stream).run();
        //获取词法单元流
        List<Token> token_stream = Lexer.getInstance().getToken_stream();
        //语法分析
        Parser.getInstance(token_stream).run();
        //语法树
        Syntax ast = Parser.getInstance().getAst();
        //语义分析
        Visitor.getInstance(ast).run();
        //错误
        if(ErrorHandler.getInstance().hasError()){
            ErrorHandler.getInstance().run();
        }
        else{
            System.out.println("Raw content:");
            System.out.println(ast.toString());
            //生成中间代码llvm_ir
//            IRGenerator irGenerator = IRGenerator.getInstance(ast);
//            irGenerator.run();
            //中端部分开始

            //后端部分开始
        }
        System.exit(0);
    }
    private void compilerInit() throws IOException {
        Config.init();
        Config.setError_output_open(true);
        Config.printInfo();
    }
}
