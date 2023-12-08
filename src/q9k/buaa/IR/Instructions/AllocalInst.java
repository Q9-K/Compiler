package q9k.buaa.IR.Instructions;

import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Types.PointerType;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.IR.Value;

public class AllocalInst extends Instruction {

    Type source_type;

    public AllocalInst(Type source_type) {
        super();
        this.source_type = source_type;
        super.setType(new PointerType(source_type));
    }

    @Override
    public String toString() {
        return this.getName() + " = alloca " + source_type.toString();
    }

    @Override
    public void translate() {

    }
}
