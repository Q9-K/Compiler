package q9k.buaa.IR.Instructions;

import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Types.ArrayType;
import q9k.buaa.IR.Types.PointerType;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.IR.Value;

public class LoadInst extends Instruction {
    public LoadInst(Value target){
        addOperand(target);
        Type sourceType = ((PointerType)target.getType()).getSourceType();
        if(sourceType instanceof ArrayType){
            Type type = ((ArrayType)sourceType).getElementType();
            setType(new PointerType(type));
        }
        else{
            setType(sourceType);
        }
    }

    @Override
    public String toString() {
        Value value = getFirst();
        return this.getName()+" = "+"load"+" "+this.getType().toString()+", "+value.getType()+" "+value.getName();
    }


    @Override
    public void translate() {

    }
}
