package q9k.buaa.IR.Instructions;

import q9k.buaa.Backend.MipsGenerator;
import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Types.ArrayType;
import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Types.PointerType;
import q9k.buaa.IR.Types.Type;

public class AllocalInst extends Instruction {

    private Type source_type;
    private boolean isParam;


    public AllocalInst(Type source_type) {
        super();
        this.source_type = source_type;
        super.setType(new PointerType(source_type));
    }

    public AllocalInst(Type source_type, boolean isParam) {
        super();
        this.source_type = source_type;
        this.isParam = isParam;
        super.setType(new PointerType(source_type));
    }

    @Override
    public String toString() {
        return this.getName() + " = alloca " + source_type.toString();
    }

    @Override
    public String genMips() {
        if (!isParam) {
            if (source_type instanceof IntegerType) {
                setStackPointerNumber(MipsGenerator.getStackPointerNumber());
            } else if (source_type instanceof ArrayType) {
                int size;
                Type elementType = ((ArrayType) source_type).getElementType();
                if (elementType instanceof IntegerType) {
                    size = ((ArrayType) source_type).getNumElements();
                } else {
                    int num1 = ((ArrayType) source_type).getNumElements();
                    int num2 = ((ArrayType) elementType).getNumElements();
                    size = num1 * num2;
                }
                setFramePointerNumber(MipsGenerator.getFramePointerNumber(size));
            }
        } else {
            //形参声明
            setRealParamNumber(MipsGenerator.getParamNumber());
            setStackPointerNumber(MipsGenerator.getStackPointerNumber());
        }
        return "";
    }

    public boolean isParam() {
        return isParam;
    }

    public void setParam(boolean param) {
        isParam = param;
    }

}
