package q9k.buaa.IR.Instructions;

import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.IR.Types.VoidType;
import q9k.buaa.IR.Value;

public class StoreInst extends Instruction {

    public StoreInst(){
        super(VoidType.VoidType);
    }

    @Override
    public String toString() {
        Value value_1 = this.getOperand(0);
        Value value_2 = this.getOperand(1);
        return "store"+" "+value_2.getType()+" "+value_2.getName()+", "+value_1.getType()+" "+value_1.getName();
    }

    @Override
    public void translate() {

    }
}
