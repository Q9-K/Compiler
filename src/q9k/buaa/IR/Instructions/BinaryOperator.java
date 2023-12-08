package q9k.buaa.IR.Instructions;

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
    public void translate() {

    }
}
