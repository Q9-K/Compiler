package q9k.buaa.IR.Instructions;

import q9k.buaa.IR.ConstantInt;
import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Types.VoidType;
import q9k.buaa.IR.Value;
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
    public String genMips() {
        StringBuilder content = new StringBuilder();
        if(!getOperands().isEmpty()){
            Value value = getFirst();
            if (value instanceof ConstantInt constantInt){
                content.append('\t').append("li $v0, ").append(constantInt.getValue()).append('\n');
            }
            else{
                content.append('\t').append("move $v0, $t").append(value.getRegNumber()).append('\n');
            }
        }
        content.append('\t').append("jr $ra\n");
        return content.toString();
    }

}
