import q9k.buaa.AST.Syntax;
import q9k.buaa.Backend.MipsGenerator;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.Frontend.Lexer;
import q9k.buaa.Frontend.Parser;
import q9k.buaa.Frontend.Visitor;
import q9k.buaa.INIT.Config;
import q9k.buaa.Token.Token;
import q9k.buaa.Utils.IRModule;
import q9k.buaa.Utils.Input;

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

    public void run() throws IOException {
        try {

            //配置信息
            compilerInit();
            //获取字符流
            StringBuffer character_stream = Input.getInstance().getCharacterStream();
            //前端部分开始
            //词法分析
            Lexer.getInstance(character_stream).run();
            //获取词法单元流
            List<Token> token_stream = Lexer.getInstance().getTokenStream();
            //语法分析
            Parser.getInstance(token_stream).run();
            //语法树
            Syntax ast = Parser.getInstance().getAst();
            //语义分析
            Visitor.getInstance(ast).run();
            //错误
            ErrorHandler.getInstance().run();
            if (!ErrorHandler.getInstance().hasError()) {
                //生成中间代码llvm_ir
                IRGenerator irGenerator = IRGenerator.getInstance(ast);
                irGenerator.run();
                //中端部分开始

                //后端部分开始
                if (Config.mips_output_open) {
                    MipsGenerator mipsGenerator = MipsGenerator.getInstance(IRModule.getInstance());
                    mipsGenerator.run();
                }
            } else {
                System.out.println("There is something wrong in your code. View error.txt to look for more info.");
            }
            System.exit(0);
        } catch (RuntimeException e) {
            if (Config.DEBUG) {
                System.out.println("You see the following message because DEBUG mode open!");
                e.printStackTrace();
            }
            System.exit(-1);
        }
    }

    private void compilerInit() throws IOException {
        Config.init();
        Config.setError_output_open(true);
        Config.setLlvm_ir_output_open(true);
        Config.setMips_output_open(true);
        Config.printInfo();
    }
}
