package q9k.buaa.AST;

import q9k.buaa.IR.Value;
import q9k.buaa.Utils.Output;

import java.io.IOException;

public interface Syntax {
    void print() throws IOException;//打印选项
    void visit();//语义分析，符号表建立
    int getLineNumber();//便于出错处理记录信息
    Value generateIR();
    default void printAstName(Class<?> objectClass) throws IOException{
        Output.getInstance().write("<"+objectClass.getSimpleName()+">\n");
    }

}
