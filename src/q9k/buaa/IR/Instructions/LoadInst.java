package q9k.buaa.IR.Instructions;

import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.IR.Value;

public class LoadInst extends Instruction {
    public LoadInst(String name, Type type){
        super(name, type);
    }

    @Override
    public String toString() {
        Value value = this.getOperand(0);
        return this.getName()+" = "+"load"+" "+this.getType().toString()+", "+value.getType()+" "+value.getName();
    }
}
