package q9k.buaa.Frontend.Token;

import q9k.buaa.INIT.Output;

import java.io.IOException;

public class Token {
    private final String content;
    private final int line_number;
    private TokenType tokenType = null;

    public Token(String content,int line_number){
        this.content = content;
        this.line_number = line_number;
        if (!this.content.isEmpty()) {
            this.tokenType = TokenType.getTokenType(content);
        }
    }


    public void write() throws IOException {
        Output output = Output.getInstance();
        output.write(new StringBuffer(this.tokenType.name()).append(' ').append(this.content).append('\n'));
    }

    public TokenType getTokenType(){
        return tokenType;
    }
    public int getLine_number(){
        return line_number;
    }
    public String getContent(){
        return content.toString();
    }
}
