package q9k.buaa.IR.Instructions;

import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Types.Type;
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
    public void translate() {

    }
}
