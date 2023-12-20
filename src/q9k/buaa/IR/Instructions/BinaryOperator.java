package q9k.buaa.IR.Instructions;

import q9k.buaa.Backend.MipsGenerator;
import q9k.buaa.INIT.Config;
import q9k.buaa.IR.ConstantInt;
import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Value;
import q9k.buaa.Token.TokenType;

public class BinaryOperator extends Instruction {

    public BinaryOperator(Value left, Value right, TokenType opcode) {
        addOperand(left);
        addOperand(right);
        setOpcode(opcode);
        setType(right.getType());
    }

    @Override
    public String toString() {

        return getName() + " = " + getCode() +
                " " + getType().toString() + " " +
                getFirst().getName() + ", " + getLast().getName();
    }

    @Override
    public String genMips() {
        StringBuilder content = new StringBuilder();

        Value left = getFirst();
        Value right = getLast();
        String code = getCode();
        Integer left_number;
        if (left instanceof ConstantInt) {
            left_number = MipsGenerator.getRegNumber();
            Integer value = ((ConstantInt) left).getValue();
            content.append('\t').append("li $t").append(left_number)
                    .append(", ").append(value).append('\n');
        } else {
            left_number = left.getRegNumber();
        }

        if (right instanceof ConstantInt) {
            setRegNumber(left_number);
            Integer value = ((ConstantInt) right).getValue();
            if (!Config.OPTIMIZED) {
                switch (code) {
                    case "add" -> content.append('\t').append("addu").append(" $t").append(left_number)
                            .append(", $t").append(left_number).append(", ")
                            .append(value).append('\n');
                    case "sub" -> content.append('\t').append("subu").append(" $t").append(left_number)
                            .append(", $t").append(left_number).append(", ")
                            .append(value).append('\n');
                    case "mul" -> content.append('\t').append("mul").append(" $t").append(left_number)
                            .append(", $t").append(left_number).append(", ")
                            .append(value).append('\n');
                    case "sdiv" -> content.append('\t').append("div").append(" $t").append(left_number)
                            .append(", $t").append(left_number).append(", ")
                            .append(value).append('\n');
                    case "srem" -> content.append('\t').append("rem").append(" $t").append(left_number)
                            .append(", $t").append(left_number).append(", ")
                            .append(value).append('\n');
                }
            } else {
                //乘除法优化
                if (code.equals("add")) {
                    content.append('\t').append("addu").append(" $t").append(left_number)
                            .append(", $t").append(left_number).append(", ")
                            .append(value).append('\n');
                } else if (code.equals("sub")) {
                    content.append('\t').append("subu").append(" $t").append(left_number)
                            .append(", $t").append(left_number).append(", ")
                            .append(value).append('\n');
                } else if (code.equals("mul")) {
                    if (value == 0) {
                        content.append('\t').append("move $t").append(left_number)
                                .append(", $zero").append('\n');
                    } else if (false) {
                        int log2 = (int) (Math.log(value) / Math.log(2));
                        content.append('\t').append("sll").append(" $t").append(left_number)
                                .append(", $t").append(left_number).append(", ")
                                .append(log2).append('\n');
                    } else {
                        content.append('\t').append("mul").append(" $t").append(left_number)
                                .append(", $t").append(left_number).append(", ")
                                .append(value).append('\n');
                    }
                } else if (code.equals("sdiv")) {
                    if (false) {
                        int log2 = (int) (Math.log(value) / Math.log(2));
                        content.append('\t').append("sra").append(" $t").append(left_number)
                                .append(", $t").append(left_number).append(", ")
                                .append(log2).append('\n');
                    } else {
                        content.append('\t').append("div").append(" $t").append(left_number)
                                .append(", $t").append(left_number).append(", ")
                                .append(value).append('\n');
                    }
                } else if (code.equals("srem")) {
                    if (false) {
                        content.append('\t').append("andi $t").append(left_number)
                                .append(", $t").append(left_number).append(", ")
                                .append(value - 1).append('\n');
                    } else {
                        content.append('\t').append("rem").append(" $t").append(left_number)
                                .append(", $t").append(left_number).append(", ")
                                .append(value).append('\n');
                    }
                }
            }
        } else {
            Integer right_number = right.getRegNumber();
            setRegNumber(left_number);
            switch (code) {
                case "add" -> content.append('\t').append("addu").append(" $t").append(left_number)
                        .append(", $t").append(left_number).append(", $t")
                        .append(right_number).append('\n');
                case "sub" -> content.append('\t').append("subu").append(" $t").append(left_number)
                        .append(", $t").append(left_number).append(", $t")
                        .append(right_number).append('\n');
                case "mul" -> content.append('\t').append("mul").append(" $t").append(left_number)
                        .append(", $t").append(left_number).append(", $t")
                        .append(right_number).append('\n');
                case "sdiv" -> content.append('\t').append("div").append(" $t").append(left_number)
                        .append(", $t").append(left_number).append(", $t")
                        .append(right_number).append('\n');
                case "srem" -> content.append('\t').append("rem").append(" $t").append(left_number)
                        .append(", $t").append(left_number).append(", $t")
                        .append(right_number).append('\n');
            }
            MipsGenerator.setRegNumberPoolFirst(right_number);
        }
        MipsGenerator.setRegNumberPoolLast(left_number);
        content.append(genCall());
        return content.toString();
    }

    private boolean isPowerOfTwo(Integer n) {
        return (n > 0) && ((n & (n - 1)) == 0);
    }

}
