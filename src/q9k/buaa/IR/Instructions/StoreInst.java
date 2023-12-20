package q9k.buaa.IR.Instructions;

import q9k.buaa.Backend.MipsGenerator;
import q9k.buaa.IR.ConstantInt;
import q9k.buaa.IR.GlobalVariable;
import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Types.VoidType;
import q9k.buaa.IR.Value;

public class StoreInst extends Instruction {

    public StoreInst() {
        super(VoidType.VoidType);
    }

    @Override
    public String toString() {
        Value value_1 = this.getOperand(0);
        Value value_2 = this.getOperand(1);
        return "store" + " " + value_2.getType() + " " + value_2.getName() + ", " + value_1.getType() + " " + value_1.getName();
    }

    @Override
    public String genMips() {
        StringBuilder content = new StringBuilder();
//        content.append("# ").append(toString()).append('\n');
        Value value_1 = this.getOperand(0);
        Value value_2 = this.getOperand(1);
        Integer reg_number = null;
        if (value_2 instanceof ConstantInt) {
            Integer value = ((ConstantInt) value_2).getValue();
            if (value != 0) {
                reg_number = MipsGenerator.getRegNumber();
                content.append('\t').append("li $t").append(reg_number)
                        .append(", ").append(value).append('\n');
            }
        } else if (value_2 instanceof CallInst) {
            reg_number = value_2.getRegNumber();
        } else {
            reg_number = value_2.getRegNumber();
            if (reg_number == null) {
                //说明是形参
                reg_number = MipsGenerator.getRegNumber();
                Integer paramNumber = value_1.getRealParamNumber();
                if (paramNumber < 4) {
                    content.append('\t').append("move $t").append(reg_number).append(", $a")
                            .append(paramNumber).append('\n');
                } else {
//                    value_1.setStackPointerNumber(-paramNumber * 4);
                    return "";
                }
            }
        }
        if (reg_number != null) {
            if (value_1 instanceof AllocalInst) {
                content.append('\t').append("sw $t").append(reg_number).append(", ")
                        .append(value_1.getStackPointerNumber()).append("($sp)").append('\n');
            } else if (value_1 instanceof GEPInst) {
                content.append('\t').append("sw $t").append(reg_number).append(", ")
                        .append("($t").append(value_1.getRegNumber()).append(")\n");
            } else if (value_1 instanceof GlobalVariable) {
                content.append('\t').append("la $t9, ")
                        .append(value_1.getName().substring(1)).append('\n');
                content.append('\t').append("sw $t").append(reg_number).append(", ")
                        .append("($t9").append(")\n");
            }
        } else {
            if (value_1 instanceof AllocalInst) {
                content.append('\t').append("sw $zero, ")
                        .append(value_1.getStackPointerNumber()).append("($sp)").append('\n');
            } else if (value_1 instanceof GEPInst) {
                content.append('\t').append("sw $zero, ")
                        .append("($t").append(value_1.getRegNumber()).append(")\n");
            } else if (value_1 instanceof GlobalVariable) {
                content.append('\t').append("la $t9, ")
                        .append(value_1.getName().substring(1)).append('\n');
                content.append('\t').append("sw $zero, ")
                        .append("($t9").append(")\n");
            }
        }
        content.append('\n');
        return content.toString();
    }

}
