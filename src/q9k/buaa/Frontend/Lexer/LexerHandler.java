package q9k.buaa.Frontend.Lexer;

import q9k.buaa.INIT.Config;
import q9k.buaa.INIT.Output;
import q9k.buaa.Frontend.Token.Token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LexerHandler {
    private int line_number = 1;
    private int number_value = 0;
    private final StringBuffer token_content =new StringBuffer();
    private final StringBuffer file_content;
    private Character character = ' ';
    private int cur_pos = 0;

    private List<Token> token_stream = new ArrayList<>();
    private static LexerHandler lexerHandler;

    private LexerHandler(StringBuffer file_content) throws IOException {
        file_content.append('\n');
        this.file_content = file_content;
    }
    public static synchronized LexerHandler getInstance() throws IOException {
        if (lexerHandler == null) {
            System.out.println("something wrong happened at lexer init!");
            System.exit(-1);
        }
        return lexerHandler;
    }

    public static synchronized LexerHandler getInstance(StringBuffer file_content) throws IOException {
        if (lexerHandler == null) {
            lexerHandler = new LexerHandler(file_content);
        }
        return lexerHandler;
    }

    public List<Token> getToken_stream(){
        return this.token_stream;
    }

    public void run() throws IOException {
        while (!isEND()){
            next();
        }
        if(Config.lexer_output_open){
            Output output = Output.getInstance();
            for (Token token : token_stream) {
                token.write();
            }
        }
        System.out.println("lexer analyzer finished!");
    }

    private void next() throws IOException {
        clearToken();
        clearCharacter();
        while (isBlank(character)&&!isEND()) {
            if (character.equals('\n')) {
                this.line_number++;
            }
            getchar();
        }
        if(isEND()) return;
        //处理注释
        if (character.equals('/')) {
            getchar();
            //多行注释
            if (character.equals('*')) {
                getchar();
                do{
                    catToken();
                    getchar();
                    if(character.equals('\n')){
                        this.line_number++;
                    }
                } while(!token_content.toString().endsWith("*/")&&!isEND());
                retract();
                return;
            }
            //单行注释
            else if (character.equals('/')) {
                while (!character.equals('\n')) {
                    getchar();
                }
                retract();
                return;
            }
            //div
            else{
                retract();
                catToken('/');
            }
        }
        //<,=,>情况
        else if (character.equals('<') || character.equals('>') ||
                character.equals('=') || character.equals('!') ||
                character.equals('|') || character.equals('&')
        ) {
            catToken();
            getchar();
            if (character.equals('<') || character.equals('>') ||
                    character.equals('=') || character.equals('!') ||
                    character.equals('|') || character.equals('&')
            ) {
                catToken();
            }
            else{
                retract();
            }
        }
        //数字
        else if (Character.isDigit(character)) {
            while (Character.isDigit(character)) {
                catToken();
                getchar();
            }
            retract();
            getNumber_value();
        }
        //标识符
        else if (Character.isLetter(character)||character.equals('_')) {
            while (Character.isLetterOrDigit(character)||character.equals('_')) {
                catToken();
                getchar();
            }
            retract();
        }
        //单字符
        else if (isSingleSymbol(character)) {
            catToken();
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
        }
        Token token = new Token(token_content.toString(), line_number);
        token_stream.add(token);
    }

    private Character getchar() {
        if (!isEND()) {
            this.character = this.file_content.charAt(this.cur_pos);
            this.cur_pos++;
        } else {
            this.character = ' ';
        }
        return this.character;
    }

    private void clearCharacter(){
        this.character = ' ';
    }
    private void clearToken() {
        this.token_content.delete(0, this.token_content.length());
    }

    private void retract() {
        if (this.cur_pos > 0) {
            this.cur_pos--;
        }
    }

    private boolean isEND() {
        return this.cur_pos == (this.file_content.length());
    }

    private void catToken() {
        this.token_content.append(character);
    }
    private void catToken(Character character) {
        this.token_content.append(character);
    }

    private boolean isBlank(Character character) {
        if (character == ' ' || character == '\t' || character == '\n' || character == '\r') {
            return true;
        }
        return false;
    }

    private int getNumber_value() {
        if (!token_content.isEmpty() && token_content.toString().matches("\\d+")) {
            this.number_value = Integer.parseInt(token_content.toString());
        }
        return this.number_value;
    }

    private boolean isSingleSymbol(Character character) {
        Character[] characters = {'(', ')', '[', ']', '{', '}', ',', ';', '+', '-', '*','%'};
        List<Character> list = Arrays.asList(characters);
        return list.contains(character);
    }
}
