package q9k.buaa.IR;

import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.BasicBlock;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.IR.User;
import q9k.buaa.Token.Token;

public abstract class Instruction extends User {
    private BasicBlock parent;
    private Token opcode;

    public Instruction(String name, Type type){
        super(name, type);
        setParent(IRGenerator.getCurBasicBlock());
    }

    private void setParent(BasicBlock parent){
        this.parent = parent;
    }

    @Override
    public String getName() {
        if(super.getName()==null){
            super.setName("%t"+this.parent.getParent().number++);
        }
        return super.getName();
    }

    public Token getOpcode(){
        return this.opcode;
    }
    public void setOpcode(Token opcode){
        this.opcode = opcode;
    }
    public boolean mayWriteToMemory(){
        return false;
    }

}
