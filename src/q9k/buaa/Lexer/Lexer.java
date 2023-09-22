package q9k.buaa.Lexer;

import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.INIT.LexerOutput;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Lexer {
    private int line_number = 1;
    private int number_value = 0;
    private StringBuffer token=new StringBuffer();
    private final StringBuffer file_content;
    private LexType lexType;
    private Character character = ' ';
    private int curpos = 0;

    private ErrorHandler errorHandler = ErrorHandler.getInstance();
    private LexerOutput lexerOutput = LexerOutput.getInstance("output.txt");
    private static Lexer lexer;

    private Lexer(StringBuffer file_content) throws IOException {
        file_content.append('\n');
        this.file_content = file_content;
    }

    //单例模式创建
    public static synchronized Lexer getLexer(StringBuffer file_content) throws IOException {
        if (lexer == null) {
            lexer = new Lexer(file_content);
        }
        return lexer;
    }


    public void run() throws IOException {
        while (!isEND()){
            next();
        }
        System.out.println("lexer analyzer finished!");
    }

    private int next() throws IOException {
        clearToken();
        clearCharacter();
        while (isBlank(character)&&!isEND()) {
            if (character.equals('\n')) {
                this.line_number++;
            }
            getchar();
        }
        if(isEND()) return 0;
        //处理注释
        if (character.equals('/')) {
            catToken();
            getchar();
            //多行注释
            if (character.equals('*')) {
                int count = 0;
                while(!token.toString().endsWith("*/")&&!isEND()){
                    if(character.equals('*')) count++;
                    if(character.equals('\n')){
                        this.line_number++;
                    }
                    catToken();
                    getchar();
                }
                retract();
                if(count<2){
                    errorHandler.handle(line_number);
                    return -1;
                }
                else{
                    return 0;
                }
            }
            //单行注释
            else if (character.equals('/')) {
                while (!character.equals('\n')) {
                    getchar();
                }
                retract();
                return 0;
            }
            //div
            else{
                retract();
                getLexType();
            }
        }
        //<,=,>情况
        else if (character.equals('<') || character.equals('>') ||
                character.equals('=') || character.equals('!') ||
                character.equals('|') || character.equals('&')
        ) {
            while (character.equals('<') || character.equals('>') ||
                    character.equals('=') || character.equals('!') ||
                    character.equals('|') || character.equals('&')
            ) {
                catToken();
                getchar();
            }
            retract();
            String token_string = token.toString();
            if (token_string.equals("=") || token_string.equals("==") ||
                    token_string.equals(">") || token_string.equals(">=") ||
                    token_string.equals("<") || token_string.equals("<=") ||
                    token_string.equals("!") || token_string.equals("!=") ||
                    token_string.equals("||") || token_string.equals("&&")
            ) {
                getLexType();
            } else {
                errorHandler.handle(line_number);
                return -1;
            }
        }
        //数字
        else if (Character.isDigit(character)) {
            while (Character.isDigit(character)) {
                catToken();
                getchar();
            }
            retract();
            getLexType();
            getNumber_value();
        }
        //标识符
        else if (Character.isLetter(character)||character.equals('_')) {
            while (Character.isLetterOrDigit(character)||character.equals('_')) {
                catToken();
                getchar();
            }
            retract();
            getLexType();
        }
        //单字符
        else if (isSingleSymbol(character)) {
            catToken();
            getLexType();
        }
        //字符串
        else if(character.equals('"')){
            catToken();
            getchar();
            while(character!='"'){
                catToken();
                getchar();
            }
            catToken();
            getLexType();
        }
        lexerOutput.write(new StringBuffer(this.lexType.name()).append(' ').append(getToken()).append('\n'));
        return 0;
    }

    private StringBuffer getToken() {
        return this.token;
    }

    private void clearCharacter(){
        this.character = ' ';
    }
    private void clearToken() {
        this.token.delete(0, this.token.length());
    }

    private void retract() {
        if (this.curpos > 0) {
            this.curpos--;
        }
    }

    public boolean isEND() {
        return this.curpos == (this.file_content.length());
    }

    private void catToken() {
        this.token.append(character);
    }

    private void catToken(Character character) {
        this.token.append(character);
    }

    private Character getchar() {
        if (!isEND()) {
            this.character = this.file_content.charAt(this.curpos);
            this.curpos++;
        } else {
            this.character = ' ';
        }
        return this.character;
    }

    private boolean isBlank(Character character) {
        if (character == ' ' || character == '\t' || character == '\n' || character == '\r') {
            return true;
        }
        return false;
    }

    private LexType getLexType() {
        if (!this.token.isEmpty()) {
            this.lexType = LexType.getLextype(this.token);
        }
        return this.lexType;
    }

    private int getNumber_value() {
        if (!token.isEmpty() && token.toString().matches("\\d+")) {
            this.number_value = Integer.parseInt(token.toString());
        }
        return this.number_value;
    }

    private boolean isSingleSymbol(Character character) {
        Character[] characters = {'(', ')', '[', ']', '{', '}', ',', ';', '+', '-', '*','%'};
        List<Character> list = Arrays.asList(characters);
        return list.contains(character);
    }
}
