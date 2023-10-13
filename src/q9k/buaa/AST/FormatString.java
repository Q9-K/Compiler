package q9k.buaa.AST;

import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.Token.Token;

import java.io.IOException;

public class FormatString implements Syntax {
    private Token strcon_token;

    public FormatString(Token strcon_token) {
        this.strcon_token = strcon_token;
    }

    @Override
    public void print() throws IOException {
        strcon_token.print();
    }

    @Override
    public void visit() {
        String str = strcon_token.getContent();
        boolean flag = true;
        int length = str.length();
        if (str.charAt(0) != '"' || str.charAt(length - 1) != '"') {
            flag = false;
        }
        for (int i = 1; i < length - 1 && flag; ++i) {
            if (isLegalChar(str.charAt(i))) {
                if (str.charAt(i) == '\\') {
                    if (str.charAt(i + 1) != 'n') {
                        flag = false;
                    } else {
                        i++;
                    }
                }
            }else if(str.charAt(i) == '%'){
                if (str.charAt(i + 1) != 'd') {
                    flag = false;
                } else {
                    i++;
                }
            }
            else {
                flag = false;
            }
        }
        if (!flag) {
            ErrorHandler.getInstance().addError(new Error(ErrorType.ILLEGALSYMBOL, getLineNumber()));
        }
    }

    private boolean isLegalChar(char c) {
        return (int) c == 32 || (int) c == 33 || ((int) c >= 40 && (int) c <= 126);
    }

    @Override
    public int getLineNumber() {
        return strcon_token.getLineNumber();
    }


    public int getParamNum() {
        int count = 0;
        int last_index = 0;
        while (last_index != -1) {
            last_index = strcon_token.getContent().indexOf("%d", last_index);
            if (last_index != -1) {
                count++;
                last_index += 2;
            }
        }
        return count;
    }
}
