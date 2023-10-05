package q9k.buaa.frontend;

import q9k.buaa.INIT.Output;

import java.io.IOException;

public class Token {
    private final StringBuffer content;
    private final int line_number;
    private boolean has_error = false;
    private TokenType tokenType;

    public Token(StringBuffer content,int line_number){
        this.content = content;
        this.line_number = line_number;
        if (!this.content.isEmpty()) {
            this.tokenType = TokenType.getTokenType(this.content);
        }
    }

    public Token(StringBuffer content,int line_number, boolean has_error){
        this.content = content;
        this.line_number = line_number;
        if (!this.content.isEmpty()) {
            this.tokenType = TokenType.getTokenType(this.content);
        }
        this.has_error = has_error;
    }

    public void write() throws IOException {
        Output output = Output.getInstance();
        output.write(new StringBuffer(this.tokenType.name()).append(' ').append(this.content).append('\n'));
    }
}
