package q9k.buaa.IR;

import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.Instructions.*;
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

    public BasicBlock getParent() {
        return parent;
    }


    @Override
    public String getName() {
        if (getType().equals(VoidType.VoidType)) {
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

    public boolean isEnd() {
        if (this instanceof BranchInst && ((BranchInst) this).isUnConditional()) {
            return true;
        } else if (this instanceof ReturnInst) {
            return true;
        }
        return false;
    }

    @Override
    public void optimize() {
//        System.out.println(this.getClass());
//        System.out.println(this.getUses().size());
        if (this.getUses().isEmpty()) {
            if (this instanceof AllocalInst || this instanceof GEPInst ||
                    this instanceof BinaryOperator || this instanceof LoadInst) {
                this.setLive(false);
            }
        }
    }
}
