package q9k.buaa.IR.Instructions;

import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.Token.TokenType;

public class ReturnInst extends Instruction {
    public ReturnInst(String name, Type type) {
        super(name, type);
    }

    @Override
    public String toString() {
        TokenType tokenType = this.getOpcode().getTokenType();
        String code = "";
        if (tokenType.equals(TokenType.RETURNTK)) {
            code = "ret";
        }
        if (this.getType()==null) {
            return code + " " + "void";
        } else {
            return code + " " + this.getType().toString() + " " + this.getOperand(0).getName();
        }
    }
}
