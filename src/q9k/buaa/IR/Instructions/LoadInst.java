package q9k.buaa.IR.Instructions;

import q9k.buaa.Backend.MipsGenerator;
import q9k.buaa.IR.GlobalVariable;
import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Types.PointerType;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.IR.Value;

public class LoadInst extends Instruction {
    public LoadInst(Value target) {
        addOperand(target);
        Type sourceType = ((PointerType) target.getType()).getSourceType();
        setType(sourceType);
    }

    @Override
    public String toString() {
        Value value = getFirst();
        return this.getName() + " = " + "load" + " " + this.getType().toString() + ", " + value.getType() + " " + value.getName();
    }


    @Override
    public String genMips() {
        StringBuilder content = new StringBuilder();
        Value source = getFirst();
        if (source instanceof AllocalInst) {
            setRegNumber(MipsGenerator.getRegNumber());
            content.append('\t').append("lw $t").append(getRegNumber())
                    .append(", ").append(source.getStackPointerNumber())
                    .append("($sp)").append('\n');

        } else if (source instanceof GEPInst) {
            setRegNumber(source.getRegNumber());
            content.append('\t').append("lw $t").append(getRegNumber())
                    .append(", ").append("($t").append(source.getRegNumber())
                    .append(")").append('\n');
        } else if(source instanceof GlobalVariable){
            setRegNumber(MipsGenerator.getRegNumber());
            content.append('\t').append("la $t").append(getRegNumber())
                    .append(", ").append(source.getName().substring(1)).append('\n');
            content.append('\t').append("lw $t").append(getRegNumber())
                    .append(", ").append("($t").append(getRegNumber())
                    .append(")").append('\n');
        }
        content.append(genCall());
        return content.toString();
    }

}
