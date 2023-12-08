package q9k.buaa.IR.Instructions;

import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.IR.Value;

public class ResizeInst extends Instruction {

    private String opcode;

    public ResizeInst(Value source, Type target_type, String opcode) {
        super();
        super.addOperand(source);
        super.setType(target_type);
        this.opcode = opcode;
    }

    @Override
    public String toString() {
        Value value = getFirst();
        return this.getName() + " = " + opcode + " " + value.getType().toString() + " " + value.getName() + " to " + getType().toString();
    }

    @Override
    public void translate() {

    }
}
