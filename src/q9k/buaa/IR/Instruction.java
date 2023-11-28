package q9k.buaa.IR;

import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.IR.Types.VoidType;
import q9k.buaa.Token.TokenType;

public abstract class Instruction extends User {
    private BasicBlock parent;
    private TokenType opcode;

    public Instruction() {
        super();
        setParent(IRGenerator.getCurBasicBlock());
    }

    public Instruction(String name) {
        super(name);
        setParent(IRGenerator.getCurBasicBlock());
    }

    public Instruction(Type type) {
        super(type);
        setParent(IRGenerator.getCurBasicBlock());
    }


    public Instruction(String name, Type type) {
        super(name, type);
        setParent(IRGenerator.getCurBasicBlock());
    }

    private void setParent(BasicBlock parent) {
        this.parent = parent;
    }

    @Override
    public String getName() {
        if(getType().equals(VoidType.VoidType)){
            return null;
        }
        if (super.getName() == null) {
            super.setName("%t" + this.parent.getParent().number++);
        }
        return super.getName();
    }

    public TokenType getOpcode() {
        return this.opcode;
    }

    public void setOpcode(TokenType opcode) {
        this.opcode = opcode;
    }

    public String getCode() {
        TokenType tokenType = getOpcode();
        String code = "";
        if (tokenType.equals(TokenType.PLUS)) {
            code = "add";
        } else if (tokenType.equals(TokenType.MINU)) {
            code = "sub";
        } else if (tokenType.equals(TokenType.MULT)) {
            code = "mul";
        } else if (tokenType.equals(TokenType.DIV)) {
            code = "sdiv";
        } else if (tokenType.equals(TokenType.MOD)) {
            code = "srem";
        } else if (tokenType.equals(TokenType.RETURNTK)) {
            code = "ret";
        } else if (tokenType.equals(TokenType.LSS)) {
            code = "slt";
        } else if (tokenType.equals(TokenType.LEQ)) {
            code = "sle";
        } else if (tokenType.equals(TokenType.GRE)) {
            code = "sgt";
        } else if (tokenType.equals(TokenType.GEQ)) {
            code = "sge";
        } else if (tokenType.equals(TokenType.EQL)) {
            code = "eq";
        } else if (tokenType.equals(TokenType.NEQ)) {
            code = "ne";
        } else if (tokenType.equals(TokenType.NOT)) {
            code = "ne";
        }

        return code;
    }

}
