package q9k.buaa.Token;

import q9k.buaa.Utils.Output;
import java.io.IOException;

public class Token {
    private final String content;
    private final int line_number;
    private TokenType tokenType = null;

    public Token(String content,int line_number){
        this.content = content;
        this.line_number = line_number;
        if (!this.content.isEmpty()) {
            this.tokenType = TokenType.getTokenType(this);
        }
    }

    public void print() throws IOException {
        Output output = Output.getInstance();
        output.write(new StringBuffer(this.tokenType.name()).append(' ').append(this.content).append('\n'));
    }

    public TokenType getTokenType(){
        return tokenType;
    }
    public int getLineNumber(){
        return line_number;
    }
    @Override
    public String toString(){
        return content;
    }
}
