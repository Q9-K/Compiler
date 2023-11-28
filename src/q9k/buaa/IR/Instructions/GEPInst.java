package q9k.buaa.IR.Instructions;

import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Types.ArrayType;
import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Types.PointerType;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.IR.Value;

public class GEPInst extends Instruction {
    private Value pos1 = null;
    private Value pos2 = null;
    private Value pos3 = null;

    public GEPInst(Value source) {
        addOperand(source);
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();

        Value operand = this.getOperand(0);
        Type operand_type = operand.getType();
        Type target_type = ((PointerType) operand_type).getSourceType();

        content.append(this.getName()).append(" = getelementptr ")
                .append(target_type.toString()).append(", ")
                .append(operand_type.toString()).append(" ").append(operand.getName());


        if (pos1 != null) {
            content.append(", ").append("i32 ").append(pos1.getName());
        }
        if (pos2 != null) {
            content.append(", ").append("i32 ").append(pos2.getName());
        }
        if (pos3 != null) {
            content.append(", ").append("i32 ").append(pos3.getName());
        }
        return content.toString();
    }

    public void setPos1(Value pos1) {
        this.pos1 = pos1;
    }

    public void setPos2(Value pos2) {
        this.pos2 = pos2;
    }

    public void setPos3(Value pos3) {
        this.pos3 = pos3;
    }

    @Override
    public void addOperand(Value value) {
        super.addOperand(value);
    }


    @Override
    public Type getType() {
        if (super.getType() != null) {
            return super.getType();
        }
        PointerType source_type = (PointerType) getFirst().getType();
        if (pos2 == null && pos3 == null) {
            setType(source_type);
            return source_type;
        } else if (pos3 == null) {
            Type source_source_type = source_type.getSourceType();
            if (source_source_type instanceof ArrayType) {
                Type source_source_source_type = ((ArrayType) source_source_type).getElementType();
                Type type = new PointerType(source_source_source_type);
                setType(type);
                return type;
            } else {
                setType(source_source_type);
                return source_source_type;
            }
        } else {
            //考虑SysY文法，此处答案是确定的
            Type type = new PointerType(IntegerType.i32);
            setType(type);
            return type;
        }
    }
}
