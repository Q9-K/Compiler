package q9k.buaa.IR.Instructions;

import q9k.buaa.Backend.MipsGenerator;
import q9k.buaa.IR.ConstantInt;
import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Value;
import q9k.buaa.Token.TokenType;


public class IcmpInst extends Instruction {


    public IcmpInst(Value left, Value right, TokenType opcode) {
        addOperand(left);
        addOperand(right);
        setType(IntegerType.i1);
        setOpcode(opcode);
    }


    @Override
    public String toString() {
        return getName() + " = icmp " + getCode() + " " +
                getFirst().getType().toString() + " " +
                getFirst().getName() + ", " +
                getLast().getName();
    }


    @Override
    public String genMips() {
        StringBuilder content = new StringBuilder();
        String code = getCode();
        Value left = getFirst();
        Value right = getLast();
        int left_number, right_number;
        if (left instanceof ConstantInt) {
            left_number = MipsGenerator.getRegNumber();
            content.append('\t').append("li $t").append(left_number)
                    .append(", ").append(((ConstantInt) left).getValue())
                    .append('\n');
        } else {
            left_number = left.getRegNumber();
        }
        if (right instanceof ConstantInt) {
            right_number = MipsGenerator.getRegNumber();
            content.append('\t').append("li $t").append(right_number)
                    .append(", ").append(((ConstantInt) right).getValue())
                    .append('\n');
        } else {
            right_number = right.getRegNumber();
        }
        content.append('\t');
        setRegNumber(MipsGenerator.getRegNumber());
        switch (code) {
            case "slt" -> content.append("slt");
            case "sle" -> content.append("sle");
            case "sgt" -> content.append("sgt");
            case "sge" -> content.append("sge");
            case "eq" -> content.append("seq");
            case "ne" -> content.append("sne");
        }
        content.append(" $t").append(getRegNumber())
                .append(", $t").append(left_number)
                .append(", $t").append(right_number)
                .append('\n');
        return content.toString();
    }

}
