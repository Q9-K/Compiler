package q9k.buaa.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Parse.ParseHandler;
import q9k.buaa.Frontend.Token.Token;
import q9k.buaa.INIT.Output;

import java.io.IOException;

public interface Syntax {
    void print() throws IOException;//打印选项
    void visit();//出错处理

    int getLineNumber();

    default void printAstName(Class<?> objectClass) throws IOException{
        Output.getInstance().write("<"+objectClass.getSimpleName()+">\n");
    }



}
