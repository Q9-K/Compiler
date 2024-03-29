package q9k.buaa.Frontend;

import q9k.buaa.AST.*;
import q9k.buaa.AST.Decl.*;
import q9k.buaa.AST.Exp.*;
import q9k.buaa.AST.Number;
import q9k.buaa.AST.Function.*;
import q9k.buaa.AST.Stmt.LoopStmt;
import q9k.buaa.AST.Stmt.*;
import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.INIT.Config;
import q9k.buaa.Token.Token;
import q9k.buaa.Token.TokenType;
import q9k.buaa.Utils.Triple;
import q9k.buaa.Utils.Tuple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private int index = 0;
    private Token cur_token;
    private final List<Token> token_stream;
    private Syntax ast;//语法树根节点

    public Syntax getAst() {
        return ast;
    }

    public static Parser parser;

    private Parser(List<Token> token_stream) {
        this.token_stream = token_stream;
    }

    public static synchronized Parser getInstance(List<Token> token_stream) {
        if (parser == null) {
            parser = new Parser(token_stream);
        }
        return parser;
    }

    public static synchronized Parser getInstance() {
        if (parser == null) {
            System.out.println("Something wrong happened at parser init!");
            System.exit(-1);
        }
        return parser;
    }

    public static void clearInstance() {
        parser = null;
    }

    public void run() throws IOException {
        getToken();
        ast = parseCompUnit();
        System.out.println("Parser analyze finished!");
        if (Config.parser_output_open) {
            ast.print();
        }
    }

    public void back() {
        cur_token = getTokenForward(-1);
        index--;
    }

    private Token getToken() {
        if (!isEND()) {
            cur_token = token_stream.get(index);
        } else {
            cur_token = null;
        }
        index++;
        return cur_token;
    }

    private boolean isEND() {
        return index >= token_stream.size();
    }

    private Token getTokenForward(int offset) {
        if (index + offset - 1 < token_stream.size()) {
            return token_stream.get(index + offset - 1);
        }
        return null;
    }


//    parse程序要求
//    1. 一个子程序在调用其他子程序前，需要调用词法分析器来预读一个单词
//    2. 一个子程序在退出时，需要调用词法分析器来预读一个单词
//    即是：
//    1. 若节点调用了叶节点，需要自行预读
//    2. 若节点调用了其他节点，无需自行预读


    //加减表达式 AddExp → MulExp | AddExp ('+' | '−') MulExp
    //改为 AddExp → MulExp | MulExp ('+' | '−') AddExp
    private Syntax parseAddExp() {
        Syntax mul_exp;
        Token op_token = null;
        Syntax add_exp = null;

        mul_exp = parseMulExp();
        if (cur_token.getTokenType().equals(TokenType.PLUS) || cur_token.getTokenType().equals(TokenType.MINU)) {
            op_token = cur_token;
            getToken();
            add_exp = parseAddExp();
        }
        return new AddExp(mul_exp, op_token, add_exp);
    }

    //语句块 Block → '{' { BlockItem } '}'
    private Syntax parseBlock() {
        Token lbrace_token;
        List<Syntax> block_items = new ArrayList<>();
        Token rbrace_token;

        lbrace_token = cur_token;
        getToken();
        while (!cur_token.getTokenType().equals(TokenType.RBRACE)) {
            Syntax block_item;
            block_item = parseBlockItem();
            block_items.add(block_item);
        }
        rbrace_token = cur_token;
        getToken();
        return new Block(lbrace_token, block_items, rbrace_token);
    }

    //语句块项 BlockItem → Decl | Stmt
    private Syntax parseBlockItem() {
        Syntax decl = null;
        Syntax stmt = null;

        if (cur_token.getTokenType().equals(TokenType.CONSTTK) || cur_token.getTokenType().equals(TokenType.INTTK)) {
            decl = parseDecl();
        } else {
            stmt = parseStmt();
        }
        return new BlockItem(decl, stmt);
    }

    //基本类型 BType → 'int'
    private Syntax parseBType() {
        Token int_token;
        int_token = cur_token;
        getToken();
        return new BType(int_token);
    }

    //编译单元 CompUnit → {Decl} {FuncDef} MainFuncDef
    private Syntax parseCompUnit() {
        List<Syntax> decls = new ArrayList<>();
        List<Syntax> func_defs = new ArrayList<>();
        Syntax main_func_def;
        while (!getTokenForward(1).getTokenType().equals(TokenType.MAINTK) && !getTokenForward(2).getTokenType().equals(TokenType.LPARENT)) {
            Syntax decl;
            decl = parseDecl();
            decls.add(decl);
        }


        while (!getTokenForward(1).getTokenType().equals(TokenType.MAINTK)) {
            Syntax func_def;
            func_def = parseFuncDef();
            func_defs.add(func_def);
        }
        main_func_def = parseMainFuncDef();
        return new CompUnit(decls, func_defs, main_func_def);
    }

    //条件表达式 Cond → LOrExp
    private Syntax parseCond() {
        Syntax l_or_exp;

        l_or_exp = parseLOrExp();
        return new Cond(l_or_exp);
    }

    //常量声明 ConstDecl → 'const' BType ConstDef { ',' ConstDef } ';'
    private Syntax parseConstDecl() {
        Token const_token;
        Syntax b_type;
        Syntax const_def;
        List<Tuple<Token, Syntax>> list = new ArrayList<>();
        Token semicn_token;

        const_token = cur_token;
        getToken();
        b_type = parseBType();
        const_def = parseConstDef();
        while (cur_token.getTokenType().equals(TokenType.COMMA)) {
            Token token_item = cur_token;
            getToken();
            Syntax const_def_item = parseConstDef();
            list.add(new Tuple<>(token_item, const_def_item));
        }
        semicn_token = cur_token;
        getToken();
        int line_number = const_def.getLineNumber();
        if (!list.isEmpty()) {
            line_number = list.get(list.size() - 1).second().getLineNumber();
        }
        handleMissing(semicn_token, TokenType.SEMICN, line_number);
        return new ConstDecl(const_token, b_type, const_def, list, semicn_token);
    }

    //常数定义 ConstDef → Ident { '[' ConstExp ']' } '=' ConstInitVal
    private Syntax parseConstDef() {
        Syntax ident;
        List<Triple<Token, Syntax, Token>> list = new ArrayList<>();
        Token eq_token;
        Syntax const_init_val;

        ident = parseIdent();
        while (cur_token.getTokenType().equals(TokenType.LBRACK)) {
            Token lbrack_item = cur_token;
            getToken();
            Syntax const_exp_item = parseConstExp();
            Token rbrack_item = cur_token;
            getToken();
            handleMissing(rbrack_item, TokenType.RBRACK, ident.getLineNumber());
            list.add(new Triple<>(lbrack_item, const_exp_item, rbrack_item));
        }
        eq_token = cur_token;
        getToken();
        const_init_val = parseConstInitVal();
        return new ConstDef(ident, list, eq_token, const_init_val);
    }

    //常量表达式 ConstExp → AddExp
    private Syntax parseConstExp() {
        Syntax add_exp;

        add_exp = parseAddExp();
        return new ConstExp(add_exp);
    }

    //常量初值 ConstInitVal → ConstExp
    //| '{' [ ConstInitVal { ',' ConstInitVal } ] '}'
    private Syntax parseConstInitVal() {
        Syntax const_exp = null;
        Token lbrace_token = null;
        Syntax const_init_val = null;
        List<Tuple<Token, Syntax>> list = new ArrayList<>();
        Token rbrace_token = null;
        if (!cur_token.getTokenType().equals(TokenType.LBRACE)) {
            const_exp = parseConstExp();
        } else {
            lbrace_token = cur_token;
            getToken();
            if (!cur_token.getTokenType().equals(TokenType.RBRACE)) {
                const_init_val = parseConstInitVal();
                while (cur_token.getTokenType().equals(TokenType.COMMA)) {
                    Token comma_item = cur_token;
                    getToken();
                    Syntax const_init_val_item = parseConstInitVal();
                    list.add(new Tuple<>(comma_item, const_init_val_item));
                }
            }
            rbrace_token = cur_token;
            getToken();
        }

        return new ConstInitVal(const_exp, lbrace_token, const_init_val, list, rbrace_token);
    }


    //声明 Decl → ConstDecl | VarDecl
    private Syntax parseDecl() {
        Syntax const_decl = null;
        Syntax var_decl = null;

        if (cur_token.getTokenType().equals(TokenType.CONSTTK)) {
            const_decl = parseConstDecl();
        } else if (cur_token.getTokenType().equals(TokenType.INTTK)) {
            var_decl = parseVarDecl();
        }
        return new Decl(const_decl, var_decl);
    }

    //相等性表达式 EqExp → RelExp | EqExp ('==' | '!=') RelExp
    //改为 EqExp → RelExp | RelExp ('==' | '!=') EqExp
    private Syntax parseEqExp() {
        Syntax rel_exp;
        Token op_token = null;
        Syntax eq_exp = null;

        rel_exp = parseRelExp();
        if (cur_token.getTokenType().equals(TokenType.EQL) || cur_token.getTokenType().equals(TokenType.NEQ)) {
            op_token = cur_token;
            getToken();
            eq_exp = parseEqExp();
        }
        return new EqExp(rel_exp, op_token, eq_exp);
    }

    //表达式 Exp → AddExp
    private Syntax parseExp() {
        Syntax add_exp;
        add_exp = parseAddExp();
        return new Exp(add_exp);
    }

    //FormatString为格式字符串终结符
    private Syntax parseFormatString() {
        Token strcon_token;

        strcon_token = cur_token;
        getToken();
        return new FormatString(strcon_token);
    }

    //语句 ForStmt → LVal '=' Exp
    private Syntax parseForStmt() {
        Syntax l_val;
        Token assign_token;
        Syntax exp;

        l_val = parseLVal();
        assign_token = cur_token;
        getToken();
        exp = parseExp();
        return new ForStmt(l_val, assign_token, exp);
    }

    //函数定义 FuncDef → FuncType Ident '(' [FuncFParams] ')' Block
    private Syntax parseFuncDef() {
        Syntax func_type;
        Syntax ident;
        Token lparent;
        Syntax func_f_params = null;
        Token rparent;
        Syntax block;

        func_type = parseFuncType();
        ident = parseIdent();
        lparent = cur_token;
        getToken();
        if (cur_token.getTokenType().equals(TokenType.INTTK)) {
            func_f_params = parseFuncFParms();
        }
        rparent = cur_token;
        getToken();
        int line_number = ident.getLineNumber();
        if (func_f_params != null) {
            line_number = func_f_params.getLineNumber();
        }
        handleMissing(rparent, TokenType.RPARENT, line_number);
        block = parseBlock();
        return new FuncDef(func_type, ident, lparent, func_f_params, rparent, block);
    }

    //函数形参 FuncFParam → BType Ident ['[' ']' { '[' ConstExp ']' }]
    private Syntax parseFuncFParm() {
        Syntax b_type;
        Syntax ident;
        Token lbrack = null;
        Token rbrack = null;
        List<Triple<Token, Syntax, Token>> list = new ArrayList<>();

        b_type = parseBType();
        ident = parseIdent();
        if (cur_token.getTokenType().equals(TokenType.LBRACK)) {
            lbrack = cur_token;
            getToken();
            rbrack = cur_token;
            getToken();
            handleMissing(rbrack, TokenType.RBRACK, ident.getLineNumber());
            while (cur_token.getTokenType().equals(TokenType.LBRACK)) {
                Token lbrack_item = cur_token;
                getToken();
                Syntax const_exp_item = parseConstExp();
                Token rbrack_item = cur_token;
                getToken();
                handleMissing(rbrack_item, TokenType.RBRACK, const_exp_item.getLineNumber());
                list.add(new Triple<>(lbrack_item, const_exp_item, rbrack_item));
            }
        }
        return new FuncFParam(b_type, ident, lbrack, rbrack, list);
    }

    //函数形参表 FuncFParams → FuncFParam { ',' FuncFParam }
    private Syntax parseFuncFParms() {
        Syntax func_f_param;
        List<Tuple<Token, Syntax>> list = new ArrayList<>();

        func_f_param = parseFuncFParm();
        while (cur_token.getTokenType().equals(TokenType.COMMA)) {
            Token comma_item = cur_token;
            getToken();
            Syntax func_f_param_item = parseFuncFParm();
            list.add(new Tuple<>(comma_item, func_f_param_item));
        }
        return new FuncFParams(func_f_param, list);
    }

    //函数实参表 FuncRParams → Exp { ',' Exp }
    private Syntax parseFuncRParms() {
        Syntax exp;
        List<Tuple<Token, Syntax>> list = new ArrayList<>();

        exp = parseExp();
        while (cur_token.getTokenType().equals(TokenType.COMMA)) {
            Token comma_item = cur_token;
            getToken();
            Syntax exp_item = parseExp();
            list.add(new Tuple<>(comma_item, exp_item));
        }
        return new FuncRParams(exp, list);
    }

    //函数类型 FuncType → 'void' | 'int'
    private Syntax parseFuncType() {
        Token func_type;

        func_type = cur_token;
        getToken();
        return new FuncType(func_type);
    }


    //标识符Ident为终结符
    private Syntax parseIdent() {
        Token ident_token;

        ident_token = cur_token;
        getToken();
        return new Ident(ident_token);
    }

    //变量初值 InitVal → Exp | '{' [ InitVal { ',' InitVal } ] '}'
    private Syntax parseInitVal() {
        Syntax exp = null;
        Token lbrace = null;
        Syntax init_val = null;
        List<Tuple<Token, Syntax>> list = new ArrayList<>();
        Token rbrace = null;

        if (cur_token.getTokenType().equals(TokenType.LBRACE)) {
            lbrace = cur_token;
            getToken();
            if (!cur_token.getTokenType().equals(TokenType.RBRACE)) {
                init_val = parseInitVal();
                while (cur_token.getTokenType().equals(TokenType.COMMA)) {
                    Token comma_item = cur_token;
                    getToken();
                    Syntax init_val_item = parseInitVal();
                    list.add(new Tuple<>(comma_item, init_val_item));
                }
            }
            rbrace = cur_token;
            getToken();
        } else {
            exp = parseExp();
        }
        return new InitVal(exp, lbrace, init_val, list, rbrace);
    }

    //标识符IntConst为数字常量终结符
    private Syntax parseIntConst() {
        Token intcont_token;

        intcont_token = cur_token;
        getToken();
        return new IntConst(intcont_token);
    }


    //逻辑与表达式 LAndExp → EqExp | LAndExp '&&' EqExp
    //改为 LAndExp → EqExp | EqExp '&&' LAndExp
    private Syntax parseLAndExp() {
        Syntax eq_exp;
        Token and_token = null;
        Syntax l_and_exp = null;

        eq_exp = parseEqExp();
        if (cur_token.getTokenType().equals(TokenType.AND)) {
            and_token = cur_token;
            getToken();
            l_and_exp = parseLAndExp();
        }
        return new LAndExp(eq_exp, and_token, l_and_exp);
    }

    //逻辑或表达式 LOrExp → LAndExp | LOrExp '||' LAndExp
    //改为 LOrExp → LAndExp | LAndExp '||' LOrExp
    private Syntax parseLOrExp() {
        Syntax l_and_exp;
        Token or_token = null;
        Syntax l_or_exp = null;

        l_and_exp = parseLAndExp();
        if (cur_token.getTokenType().equals(TokenType.OR)) {
            or_token = cur_token;
            getToken();
            l_or_exp = parseLOrExp();
        }
        return new LOrExp(l_and_exp, or_token, l_or_exp);
    }

    //左值表达式 LVal → Ident {'[' Exp ']'}
    private Syntax parseLVal() {
        Syntax ident;
        List<Triple<Token, Syntax, Token>> list = new ArrayList<>();

        ident = parseIdent();
        while (cur_token.getTokenType().equals(TokenType.LBRACK)) {
            Token lbrack_item = cur_token;
            getToken();
            Syntax exp_item = parseExp();
            Token rbrack_item = cur_token;
            getToken();
            handleMissing(rbrack_item, TokenType.RBRACK, exp_item.getLineNumber());
            list.add(new Triple<>(lbrack_item, exp_item, rbrack_item));
        }
        return new LVal(ident, list);
    }

    //主函数定义 MainFuncDef → 'int' 'main' '(' ')' Block
    private Syntax parseMainFuncDef() {
        Token int_token;
        Token main_token;
        Token lparent_token;
        Token rparent_token;
        Syntax block;

        int_token = cur_token;
        getToken();
        main_token = cur_token;
        getToken();
        lparent_token = cur_token;
        getToken();
        rparent_token = cur_token;
        getToken();
        handleMissing(rparent_token, TokenType.RPARENT, main_token.getLineNumber());
        block = parseBlock();
        return new MainFuncDef(int_token, main_token, lparent_token, rparent_token, block);
    }

    //乘除模表达式 MulExp → UnaryExp | MulExp ('*' | '/' | '%') UnaryExp
    //改为 MulExp → UnaryExp | UnaryExp ('*' | '/' | '%') MulExp
    private Syntax parseMulExp() {
        Syntax unary_exp;
        Token op_token = null;
        Syntax mul_exp = null;

        unary_exp = parseUnaryExp();
        if (cur_token.getTokenType().equals(TokenType.MULT)
                || cur_token.getTokenType().equals(TokenType.DIV)
                || cur_token.getTokenType().equals(TokenType.MOD)
        ) {
            op_token = cur_token;
            getToken();
            mul_exp = parseMulExp();
        }
        return new MulExp(unary_exp, op_token, mul_exp);
    }

    //数值 Number → IntConst
    private Syntax parseNumber() {
        Syntax int_const;

        int_const = parseIntConst();
        return new Number(int_const);
    }


    //基本表达式 PrimaryExp → '(' Exp ')' | LVal | Number
    private Syntax parsePrimaryExp() {
        Token lparent = null;
        Syntax exp = null;
        Token rparent = null;
        Syntax l_val = null;
        Syntax number = null;
        if (cur_token.getTokenType().equals(TokenType.LPARENT)) {
            lparent = cur_token;
            getToken();
            exp = parseExp();
            rparent = cur_token;
            getToken();
            handleMissing(rparent, TokenType.RPARENT, exp.getLineNumber());
        } else if (cur_token.getTokenType().equals(TokenType.INTCON)) {
            number = parseNumber();
        } else {
            l_val = parseLVal();
        }

        return new PrimaryExp(lparent, exp, rparent, l_val, number);
    }

    //关系表达式 RelExp → AddExp | RelExp ('<' | '>' | '<=' | '>=') AddExp
    //改为 RelExp → AddExp | AddExp ('<' | '>' | '<=' | '>=') RelExp
    private Syntax parseRelExp() {
        Syntax add_exp;
        Token op_token = null;
        Syntax rel_exp = null;

        add_exp = parseAddExp();
        if (cur_token.getTokenType().equals(TokenType.LSS)
                || cur_token.getTokenType().equals(TokenType.GRE)
                || cur_token.getTokenType().equals(TokenType.LEQ)
                || cur_token.getTokenType().equals(TokenType.GEQ)
        ) {
            op_token = cur_token;
            getToken();
            rel_exp = parseRelExp();
        }
        return new RelExp(add_exp, op_token, rel_exp);
    }

    /**
     * 语句 Stmt → LVal '=' Exp ';' // 每种类型的语句都要覆盖
     * | [Exp] ';' //有无Exp两种情况
     * | Block
     * | 'if' '(' Cond ')' Stmt [ 'else' Stmt ] // 1.有else 2.无else
     * | 'for' '(' [ForStmt] ';' [Cond] ';' [ForStmt] ')' Stmt // 1. 无缺省 2. 缺省第一个ForStmt 3. 缺省Cond 4. 缺省第二个ForStmt
     * | 'break' ';' | 'continue' ';'
     * | 'return' [Exp] ';' // 1.有Exp 2.无Exp
     * | LVal '=' 'getint''('')'';'
     * | 'printf''('FormatString{','Exp}')'';' // 1.有Exp 2.无Exp
     **/
    private Syntax parseStmt() {
        //'if' '(' Cond ')' Stmt [ 'else' Stmt ]
        if (cur_token.getTokenType().equals(TokenType.IFTK)) {
            Token if_token;
            Token lparent_token;
            Syntax cond;
            Token rparent_token;
            Syntax stmt1;
            Token else_token = null;
            Syntax stmt2 = null;

            if_token = cur_token;
            getToken();
            lparent_token = cur_token;
            getToken();
            cond = parseCond();
            rparent_token = cur_token;
            getToken();
            handleMissing(rparent_token, TokenType.RPARENT, cond.getLineNumber());
            stmt1 = parseStmt();
            if (cur_token.getTokenType().equals(TokenType.ELSETK)) {
                else_token = cur_token;
                getToken();
                stmt2 = parseStmt();
            }
            return new IfStmt(if_token, lparent_token, cond, rparent_token, stmt1, else_token, stmt2);
        }
        //'for' '(' [ForStmt] ';' [Cond] ';' [ForStmt] ')' Stmt
        else if (cur_token.getTokenType().equals(TokenType.FORTK)) {
            Token for_token;
            Token lparent_token;
            Syntax for_stmt1 = null;
            Token semicn_token1;
            Syntax cond = null;
            Token semicn_token2;
            Syntax for_stmt2 = null;
            Token rparent_token;
            Syntax stmt;

            for_token = cur_token;
            getToken();
            lparent_token = cur_token;
            getToken();
            if (!cur_token.getTokenType().equals(TokenType.SEMICN)) {
                for_stmt1 = parseForStmt();
            }
            semicn_token1 = cur_token;
            getToken();
            if (!cur_token.getTokenType().equals(TokenType.SEMICN)) {
                cond = parseCond();
            }
            semicn_token2 = cur_token;
            getToken();
            if (!cur_token.getTokenType().equals(TokenType.RPARENT)) {
                for_stmt2 = parseForStmt();
            }
            rparent_token = cur_token;
            getToken();
            handleMissing(rparent_token, TokenType.RPARENT, for_token.getLineNumber());
            stmt = parseStmt();

            return new LoopStmt(for_token, lparent_token, for_stmt1, semicn_token1, cond, semicn_token2, for_stmt2, rparent_token, stmt);
        }
        //'break' ';' | 'continue' ';'
        else if (cur_token.getTokenType().equals(TokenType.BREAKTK) || cur_token.getTokenType().equals(TokenType.CONTINUETK)) {
            Token action_token;
            Token semicn_token;
            action_token = cur_token;
            getToken();
            semicn_token = cur_token;
            getToken();
            handleMissing(semicn_token, TokenType.SEMICN, action_token.getLineNumber());
            return new ActionStmt(action_token, semicn_token);
        }
        //'return' [Exp] ';'
        else if (cur_token.getTokenType().equals(TokenType.RETURNTK)) {
            Token return_token;
            Syntax exp = null;
            Token semicn_token;

            return_token = cur_token;
            getToken();
            if (isExpFirst(cur_token)) {
                exp = parseExp();
            }
            semicn_token = cur_token;
            getToken();
            int line_number = return_token.getLineNumber();
            if (exp != null) {
                line_number = exp.getLineNumber();
            }
            handleMissing(semicn_token, TokenType.SEMICN, line_number);
            return new ReturnStmt(return_token, exp, semicn_token);
        }
        //'printf''('FormatString{','Exp}')'';'
        else if (cur_token.getTokenType().equals(TokenType.PRINTFTK)) {
            Token printf_token;
            Token lparent_token;
            Syntax format_string;
            List<Tuple<Token, Syntax>> list = new ArrayList<>();
            Token rparent_token;
            Token semicn_token;

            printf_token = cur_token;
            getToken();
            lparent_token = cur_token;
            getToken();
            format_string = parseFormatString();
            while (cur_token.getTokenType().equals(TokenType.COMMA)) {
                Token comma_item = cur_token;
                getToken();
                Syntax exp_item = parseExp();
                list.add(new Tuple<>(comma_item, exp_item));
            }
            rparent_token = cur_token;
            int line_number = format_string.getLineNumber();
            if (!list.isEmpty()) {
                line_number = list.get(list.size() - 1).second().getLineNumber();
            }
            handleMissing(rparent_token, TokenType.RPARENT, line_number);
            getToken();
            semicn_token = cur_token;
            getToken();
            if (rparent_token.getTokenType().equals(TokenType.RPARENT)) {
                line_number = rparent_token.getLineNumber();
            }
            handleMissing(semicn_token, TokenType.SEMICN, line_number);
            return new PrintfStmt(printf_token, lparent_token, format_string, list, rparent_token, semicn_token);
        }
        //Block
        else if (cur_token.getTokenType().equals(TokenType.LBRACE)) {
            Syntax block;
            block = parseBlock();
            return new BlockStmt(block);
        }
        // First为Ident
        else if (cur_token.getTokenType().equals(TokenType.IDENFR)) {
            Syntax l_val;
            Token assign_token;
            Syntax exp = null;
            Token getint_token = null;
            Token lparent_token = null;
            Token rparent_token = null;
            Token semicn_token;

            int i = 0;
            boolean is_l_val = false;
            while (!getTokenForward(i).getTokenType().equals(TokenType.SEMICN) && !getTokenForward(i).getTokenType().equals(TokenType.RBRACE)) {
                if (getTokenForward(i++).getTokenType().equals(TokenType.ASSIGN)) {
                    is_l_val = true;
                    break;
                }
            }
            if (is_l_val) {
                l_val = parseLVal();
                assign_token = cur_token;
                getToken();
                //LVal '=' Exp ';'
                if (!cur_token.getTokenType().equals(TokenType.GETINTTK)) {
                    exp = parseExp();
                    semicn_token = cur_token;
                    getToken();
                    handleMissing(semicn_token, TokenType.SEMICN, exp.getLineNumber());
                }
                //LVal '=' 'getint''('')'';'
                else {
                    getint_token = cur_token;
                    getToken();
                    lparent_token = cur_token;
                    getToken();
                    rparent_token = cur_token;
                    getToken();
                    semicn_token = cur_token;
                    getToken();
                    handleMissing(semicn_token, TokenType.SEMICN, getint_token.getLineNumber());
                }
                return new AssignStmt(l_val, assign_token, exp, getint_token, lparent_token, rparent_token, semicn_token);
            } else {
                //[Exp] ';'
                if (isExpFirst(cur_token)) {
                    exp = parseExp();
                }
                semicn_token = cur_token;
                getToken();
                if (exp != null) {
                    handleMissing(semicn_token, TokenType.SEMICN, exp.getLineNumber());
                }
                return new ExpStmt(exp, semicn_token);
            }
        }
        //[Exp] ';'
        else {
            Syntax exp = null;
            Token semicn_token;
            if (isExpFirst(cur_token)) {
                exp = parseExp();
            }
            semicn_token = cur_token;
            getToken();
            if (exp != null) {
                handleMissing(semicn_token, TokenType.SEMICN, exp.getLineNumber());
            }
            return new ExpStmt(exp, semicn_token);
        }
    }

    private boolean isNumberFirst(Token curToken) {
        return curToken.getTokenType().equals(TokenType.MINU) || curToken.getTokenType().equals(TokenType.PLUS) || curToken.getTokenType().equals(TokenType.NOT);
    }

    //一元表达式 UnaryExp → PrimaryExp | Ident '(' [FuncRParams] ')' | UnaryOp UnaryExp
    private Syntax parseUnaryExp() {
        Syntax primary_exp = null;
        Syntax ident = null;
        Token lparent = null;
        Syntax func_r_params = null;
        Token rparent = null;
        Syntax unary_op = null;
        Syntax unary_exp = null;
        if (cur_token.getTokenType().equals(TokenType.PLUS)
                || cur_token.getTokenType().equals(TokenType.MINU)
                || cur_token.getTokenType().equals(TokenType.NOT)
        ) {
            unary_op = parseUnaryOp();
            unary_exp = parseUnaryExp();
        } else if (cur_token.getTokenType().equals(TokenType.IDENFR)) {
            if (getTokenForward(1).getTokenType().equals(TokenType.LPARENT)) {
                ident = parseIdent();
                lparent = cur_token;
                getToken();
                if (!cur_token.getTokenType().equals(TokenType.RPARENT) && !cur_token.getTokenType().equals(TokenType.SEMICN)) {
                    func_r_params = parseFuncRParms();
                }
                rparent = cur_token;
                int line_number = ident.getLineNumber();
                if (func_r_params != null) {
                    line_number = func_r_params.getLineNumber();
                }
                handleMissing(rparent, TokenType.RPARENT, line_number);
                getToken();
            } else {
                primary_exp = parsePrimaryExp();
            }
        } else {
            primary_exp = parsePrimaryExp();
        }
        return new UnaryExp(primary_exp, ident, lparent, func_r_params, rparent, unary_op, unary_exp);
    }

    //单目运算符 UnaryOp → '+' | '−' | '!'
    private Syntax parseUnaryOp() {
        Token op_token;

        op_token = cur_token;
        getToken();
        return new UnaryOp(op_token);
    }


    //变量声明 VarDecl → BType VarDef { ',' VarDef } ';'
    private Syntax parseVarDecl() {
        Syntax b_type;
        Syntax var_def;
        List<Tuple<Token, Syntax>> list = new ArrayList<>();
        Token semicn_token;

        b_type = parseBType();
        var_def = parseVarDef();
        while (cur_token.getTokenType().equals(TokenType.COMMA)) {
            Token comma_item = cur_token;
            getToken();
            Syntax var_def_item = parseVarDef();
            list.add(new Tuple<>(comma_item, var_def_item));
        }
        semicn_token = cur_token;
        getToken();
        int line_number = var_def.getLineNumber();
        if (!list.isEmpty()) {
            line_number = list.get(list.size() - 1).second().getLineNumber();
        }
        handleMissing(semicn_token, TokenType.SEMICN, line_number);
        return new VarDecl(b_type, var_def, list, semicn_token);
    }

    //变量定义 VarDef → Ident { '[' ConstExp ']' }
    private Syntax parseVarDef() {
        Syntax ident;
        List<Triple<Token, Syntax, Token>> list = new ArrayList<>();
        Token assign_token = null;
        Syntax init_val = null;
        ident = parseIdent();
        while (cur_token.getTokenType().equals(TokenType.LBRACK)) {
            Token lbrack_item = cur_token;
            getToken();
            Syntax const_exp_item = parseConstExp();
            Token rbrack_item = cur_token;
            getToken();
            handleMissing(rbrack_item, TokenType.RBRACK, ident.getLineNumber());
            list.add(new Triple<>(lbrack_item, const_exp_item, rbrack_item));
        }
        if (cur_token.getTokenType().equals(TokenType.ASSIGN)) {
            assign_token = cur_token;
            getToken();
            init_val = parseInitVal();
        }
        return new VarDef(ident, list, assign_token, init_val);
    }

    private void handleMissing(Token token, TokenType tokenType, int line_number) {
        if (token == null || !token.getTokenType().equals(tokenType)) {
            if (tokenType.equals(TokenType.SEMICN)) {
                ErrorHandler.getInstance().addError(new Error(ErrorType.MISSINGSEMICN, line_number));
            } else if (tokenType.equals(TokenType.RBRACK)) {
                ErrorHandler.getInstance().addError(new Error(ErrorType.MISSINGRBRACK, line_number));
            } else if (tokenType.equals(TokenType.RPARENT)) {
                ErrorHandler.getInstance().addError(new Error(ErrorType.MISSINGRPARENT, line_number));
            }
            back();
        }
    }

    private boolean isExpFirst(Token token) {
        if (token == null) return false;
        TokenType tokenType = token.getTokenType();
        return tokenType.equals(TokenType.LPARENT)
                || tokenType.equals(TokenType.IDENFR)
                || tokenType.equals(TokenType.INTCON)
                || tokenType.equals(TokenType.PLUS)
                || tokenType.equals(TokenType.MINU);
    }

}
