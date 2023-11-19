package q9k.buaa.IR.Instructions;

import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.Token.TokenType;

public class BinaryOperator extends Instruction {
    public BinaryOperator(String name, Type type){
        super(name, type);
    }

    @Override
    public String toString() {
        TokenType tokenType = this.getOpcode().getTokenType();
        String code="";
        if(tokenType.equals(TokenType.PLUS)){
            code = "add";
        }
        else if(tokenType.equals(TokenType.MINU)){
            code = "sub";
        }
        else if(tokenType.equals(TokenType.MULT)){
            code = "mul";
        }
        else if(tokenType.equals(TokenType.DIV)){
            code = "sdiv";
        }
        else if(tokenType.equals(TokenType.MOD)){
            code="srem";
        }
        return this.getName()+" = "+code+
                " "+this.getType().toString()+" "+
                this.getOperand(0).getName()+", "+this.getOperand(1).getName();
    }
}
