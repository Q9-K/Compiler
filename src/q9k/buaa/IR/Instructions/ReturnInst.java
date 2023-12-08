package q9k.buaa.IR.Instructions;

import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Types.VoidType;
import q9k.buaa.Token.TokenType;

public class ReturnInst extends Instruction {

    public ReturnInst() {
        setType(VoidType.VoidType);
        setOpcode(TokenType.RETURNTK);
    }

    @Override
    public String toString() {
        if (getOperands().isEmpty()) {
            return "ret void";
        } else {
            return "ret " + getFirst().getType().toString() + " " + this.getFirst().getName();
        }
    }

    @Override
    public void translate() {

    }
}
