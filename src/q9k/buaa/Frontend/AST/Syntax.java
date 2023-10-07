package q9k.buaa.Frontend.AST;

import q9k.buaa.INIT.Output;

import java.io.IOException;

public interface Syntax {
    void print() throws IOException;//打印选项
    void handleError();//出错处理

    default void print_ast_name(Class<?> objectClass) throws IOException{
        Output.getInstance().write("<"+objectClass.getSimpleName()+">\n");
    }

}
