package q9k.buaa.IR.Instructions;

import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Types.PointerType;
import q9k.buaa.IR.Types.Type;

public class AllocalInst extends Instruction {
    public AllocalInst(String name, Type type){
        super(name, type);
    }

    @Override
    public String toString() {
        PointerType pointerType = (PointerType) this.getType();
        return this.getName()+" = alloca "+pointerType.getTarget_type().toString();
    }
}
