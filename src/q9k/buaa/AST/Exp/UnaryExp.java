package q9k.buaa.AST.Exp;

import q9k.buaa.AST.Function.FuncRParams;
import q9k.buaa.AST.Syntax;
import q9k.buaa.Error.Error;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.Error.ErrorType;
import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.ConstantInt;
import q9k.buaa.IR.Function;
import q9k.buaa.IR.IRModule;
import q9k.buaa.IR.Instructions.BinaryOperator;
import q9k.buaa.IR.Instructions.CallInst;
import q9k.buaa.IR.Instructions.IcmpInst;
import q9k.buaa.IR.Types.FunctionType;
import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.IR.Types.VoidType;
import q9k.buaa.IR.Value;
import q9k.buaa.Symbol.*;
import q9k.buaa.Token.Token;
import q9k.buaa.Token.TokenType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class UnaryExp implements Syntax {
    private Syntax primary_exp;
    private Syntax ident;
    private Token lparent;
    private Syntax func_r_params;
    private Token rparent;
    private Syntax unary_op;
    private Syntax unary_exp;

    private FuncSymbol funcSymbol;
    private SymbolTable symbolTable;

    public UnaryExp(Syntax primary_exp, Syntax ident, Token lparent, Syntax func_r_params, Token rparent, Syntax unary_op, Syntax unary_exp) {
        this.primary_exp = primary_exp;
        this.ident = ident;
        this.lparent = lparent;
        this.func_r_params = func_r_params;
        this.rparent = rparent;
        this.unary_op = unary_op;
        this.unary_exp = unary_exp;
    }

    @Override
    public void print() throws IOException {
        if (primary_exp != null) {
            primary_exp.print();
        } else if (ident != null) {
            ident.print();
            lparent.print();
            if (func_r_params != null) {
                func_r_params.print();
            }
            rparent.print();
        } else if (unary_op != null) {
            unary_op.print();
            unary_exp.print();
        }
        printAstName(UnaryExp.class);
    }

    @Override
    public void visit() {
        this.symbolTable = SymbolTableFactory.getInstance().getCurrent();
        if (primary_exp != null) {
            primary_exp.visit();
        } else if (ident != null) {
            if (SymbolTable.checkFuncInvoke(ident)) {
                this.funcSymbol = (FuncSymbol) SymbolTableFactory.getInstance().getGlobal().getSymbol(ident);
                List<Syntax> list = new ArrayList<>();
                if (func_r_params != null) {
                    func_r_params.visit();
                    list = ((FuncRParams) func_r_params).getSymbolTypeList();
                }
                if (funcSymbol != null) {
                    if (funcSymbol.getParam_num() != list.size()) {
                        ErrorHandler.getInstance().addError(new Error(ErrorType.NOTPARAMNUMFIT, getLineNumber()));
                    } else {
                        List<SymbolType> symbolTypeList = funcSymbol.getParam_type_list();
                        for (int i = 0; i < list.size(); ++i) {
                            Exp exp = (Exp) list.get(i);
                            SymbolType symbolType = getSymbolType(exp);
                            if (symbolType != null) {
                                if (!symbolTypeList.get(i).equals(symbolType)) {
                                    ErrorHandler.getInstance().addError(new Error(ErrorType.NOTPARAMTYPEFIT, getLineNumber()));
                                    break;
                                }
                            } else {
                                exp.visit();
                            }
                        }
                    }
                }
            }
        } else if (unary_op != null) {
            unary_exp.visit();
        }
    }

    @Override
    public int getLineNumber() {
        if (primary_exp != null) {
            return primary_exp.getLineNumber();
        } else if (ident != null) {
            return ident.getLineNumber();
        } else {
            return unary_exp.getLineNumber();
        }
    }

    @Override
    public Value genIR() {
        if (primary_exp != null) {
            return primary_exp.genIR();
        } else if (ident != null) {
            SymbolType symbolType = funcSymbol.getReturn_type();
            Type type;
            if (symbolType == SymbolType.VOID) {
                type = VoidType.VoidType;
            } else {
                type = IntegerType.i32;
            }
            CallInst callInst = new CallInst(type);
            CallInst curCallInst = IRGenerator.getCurCallInst();
            IRGenerator.setCurCallInst(callInst);
            if (func_r_params != null) {
                func_r_params.genIR();
            }
//            callInst.addOperand(new Function("@" + ident.toString(), FunctionType.FunctionType));
            callInst.addOperand(IRModule.getInstance().getFunctionMap().get("@"+ident.toString()));
            IRGenerator.getCurBasicBlock().addInstruction(callInst);
            IRGenerator.setCurCallInst(curCallInst);
            return callInst;
        } else {
            Token op_token = ((UnaryOp) unary_op).getOp_token();
            if (op_token.getTokenType().equals(TokenType.PLUS)) {
                return unary_exp.genIR();
            } else if (op_token.getTokenType().equals(TokenType.MINU)) {

                UnaryExp temp = (UnaryExp) unary_exp;
                BinaryOperator binaryOperator = new BinaryOperator(ConstantInt.ZERO, temp.genIR(), op_token.getTokenType());

                IRGenerator.getCurBasicBlock().addInstruction(binaryOperator);
                return binaryOperator;
            } else if (op_token.getTokenType().equals(TokenType.NOT)) {
                IcmpInst icmpInst = new IcmpInst(unary_exp.genIR(), ConstantInt.ZERO, TokenType.EQL);
                IRGenerator.getCurBasicBlock().addInstruction(icmpInst);
                return icmpInst;
            }
            return null;
        }
    }

    @Override
    public String toString() {
        if (primary_exp != null) {
            return primary_exp.toString();
        } else if (ident != null) {
            StringBuilder content = new StringBuilder();
            content.append(ident.toString()).append(lparent.toString());
            if (func_r_params != null) {
                content.append(func_r_params.toString());
            }
            content.append(rparent.toString());
            return content.toString();
        } else {
            Token op_token = ((UnaryOp) unary_op).getOp_token();
            if (op_token.getTokenType().equals(TokenType.NOT)) {
                return unary_op.toString() + unary_exp.toString();
            } else {
                String content = "(0"+unary_op.toString()+unary_exp.toString()+")";
//                System.out.println(content);
                return content;
            }
        }
    }

    private SymbolType getSymbolType(Exp exp) {
        String content = exp.toString();
        int index = 0;
        while ((index < content.length()) && !(Character.isLetterOrDigit(content.charAt(index)) || content.charAt(index) == '_')) {
            index++;
        }
        int start = index;
        while ((index < content.length()) && (Character.isLetterOrDigit(content.charAt(index)) || content.charAt(index) == '_')) {
            index++;
        }
        int end = index;
        int lbrack_count = 0;
        while (index < content.length()) {
            if (content.charAt(index) == '[') {
                lbrack_count++;
            }
            index++;
        }
        content = content.substring(start, end);
        if (TokenType.getTokenType(content).equals(TokenType.IDENFR)) {
            Symbol symbol = SymbolTableFactory.getInstance().getCurrent().getSymbol(exp);
            if (symbol == null) {
                return null;
            } else {
                SymbolType symbolType = symbol.getSymbolType();
                if (symbolType.equals(SymbolType.FUNCTION)) {
                    return ((FuncSymbol) symbol).getReturn_type();
                } else if (symbolType.equals(SymbolType.ARRAY)) {
                    if (lbrack_count == 1) {
                        return SymbolType.VAR;
                    } else if (lbrack_count == 0) {
                        return SymbolType.ARRAY;
                    } else {
                        return null;
                    }
                } else if (symbolType.equals(SymbolType.MULTIARRAY)) {
                    if (lbrack_count == 0) {
                        return SymbolType.MULTIARRAY;
                    } else if (lbrack_count == 1) {
                        return SymbolType.ARRAY;
                    } else if (lbrack_count == 2) {
                        return SymbolType.VAR;
                    } else {
                        return null;
                    }
                } else {
                    return symbolType;
                }
            }
        } else if (TokenType.getTokenType(content).equals(TokenType.INTCON)) {
            return SymbolType.VAR;
        }
        return null;
    }
}
