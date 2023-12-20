package q9k.buaa.IR.Instructions;

import q9k.buaa.Backend.MipsGenerator;
import q9k.buaa.IR.*;
import q9k.buaa.IR.Types.ArrayType;
import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Types.PointerType;
import q9k.buaa.IR.Types.Type;

public class GEPInst extends Instruction {
    private Value pos1 = null;
    private Value pos2 = null;
    private Value pos3 = null;

    public GEPInst(Value source) {
        super.addOperand(source);
        setType(this.getType());
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();

        Value operand = getFirst();
        Type operand_type = operand.getType();
        Type target_type = ((PointerType) operand_type).getSourceType();

        content.append(this.getName()).append(" = getelementptr ")
                .append(target_type.toString()).append(", ")
                .append(operand_type.toString()).append(" ").append(operand.getName());


        if (pos1 != null) {
            content.append(", ").append("i32 ").append(pos1.getName());
        }
        if (pos2 != null) {
            content.append(", ").append("i32 ").append(pos2.getName());
        }
        if (pos3 != null) {
            content.append(", ").append("i32 ").append(pos3.getName());
        }
        return content.toString();
    }

    public void setPos1(Value pos1) {
        this.pos1 = pos1;
        pos1.getUses().add(new Use(this, pos1));
    }

    public void setPos2(Value pos2) {
        this.pos2 = pos2;
        pos2.getUses().add(new Use(this, pos2));
    }

    public void setPos3(Value pos3) {
        this.pos3 = pos3;
        pos3.getUses().add(new Use(this, pos3));
    }


    @Override
    public Type getType() {
        PointerType pointer_type = (PointerType) getFirst().getType();
        if (pos2 == null && pos3 == null) {
//            setType(pointer_type);
            return pointer_type;
        } else if (pos3 == null) {
            Type source_type = pointer_type.getSourceType();
            if (source_type instanceof ArrayType) {
                Type source_source_type = ((ArrayType) source_type).getElementType();
                //                setType(type);
                return new PointerType(source_source_type);
            } else {
//                setType(source_type);
                return source_type;
            }
        } else {
            //考虑SysY文法，此处答案是确定的
            //            setType(type);
            return new PointerType(IntegerType.i32);
        }
    }

    @Override
    public String genMips() {
        StringBuilder content = new StringBuilder();
        content.append("# ").append(toString()).append('\n');
        Value source = getFirst();

        Integer source_number;
        if (source instanceof GlobalVariable) {
            source_number = MipsGenerator.getRegNumber();
            content.append("\t").append("la $t").append(source_number).append(", ")
                    .append(source.getName().substring(1)).append('\n');
            if (((GlobalVariable) source).getSize() > 1) {
                content.append('\t').append("addu $t").append(source_number).append(", $t")
                        .append(source_number).append(", ")
                        .append((((GlobalVariable) source).getSize() - 1) * 4).append('\n');
            }
        } else {
            source_number = source.getRegNumber();
            if (source_number == null) {
                //意味着是Allocate
                source_number = MipsGenerator.getRegNumber();
                content.append('\t').append("subu $t").append(source_number)
                        .append(", $fp, ").append(source.getFramePointerNumber()).append('\n');
            }
        }

        PointerType pointerType = (PointerType) source.getType();
        Type sourceType = pointerType.getSourceType();
        Integer reg_number;

        if (pos1 instanceof ConstantInt) {
            reg_number = MipsGenerator.getRegNumber();
            content.append('\t').append("li $t")
                    .append(reg_number).append(", ")
                    .append(((ConstantInt) pos1).getValue())
                    .append('\n');
        } else {
            reg_number = pos1.getRegNumber();
        }
        if (sourceType instanceof IntegerType) {
            //一维数组参数
        } else {
            ArrayType arrayType = (ArrayType) sourceType;
            if (arrayType.getElementType() instanceof IntegerType) {
                //二维数组参数或者是一维数组
                content.append('\t').append("mul $t").append(reg_number)
                        .append(", $t").append(reg_number)
                        .append(", ").append(arrayType.getNumElements())
                        .append('\n');
                if (pos2 instanceof ConstantInt) {
                    content.append('\t').append("addu $t").append(reg_number)
                            .append(", $t").append(reg_number)
                            .append(", ").append(((ConstantInt) pos2).getValue())
                            .append('\n');
                } else {
                    content.append('\t').append("addu $t").append(reg_number)
                            .append(", $t").append(reg_number)
                            .append(", $t").append(pos2.getRegNumber())
                            .append('\n');
                }
            } else {
                //二维数组，可以确定的是第一位一直是0，不过按照规范还是算偏移吧
                ArrayType arrayType1 = (ArrayType) arrayType.getElementType();
                content.append('\t').append("mul $t").append(reg_number)
                        .append(", $t").append(reg_number).append(", ")
                        .append(arrayType.getNumElements()).append('\n');
                if (pos2 instanceof ConstantInt) {
                    content.append('\t').append("addu $t").append(reg_number)
                            .append(", $t").append(reg_number)
                            .append(", ").append(((ConstantInt) pos2).getValue())
                            .append('\n');
                } else {
                    content.append('\t').append("addu $t").append(reg_number)
                            .append(", $t").append(reg_number)
                            .append(", $t").append(pos2.getRegNumber())
                            .append('\n');
                }
                content.append('\t').append("mul $t").append(reg_number)
                        .append(", $t").append(reg_number).append(", ")
                        .append(arrayType1.getNumElements()).append('\n');
                if (pos3 != null) {
                    if (pos3 instanceof ConstantInt) {
                        content.append('\t').append("addu $t").append(reg_number)
                                .append(", $t").append(reg_number)
                                .append(", ").append(((ConstantInt) pos3).getValue())
                                .append('\n');
                    } else {
                        content.append('\t').append("addu $t").append(reg_number)
                                .append(", $t").append(reg_number)
                                .append(", $t").append(pos3.getRegNumber())
                                .append('\n');
                    }
                }
            }
        }
        content.append('\t').append("sll $t").append(reg_number)
                .append(", $t").append(reg_number).append(", 2")
                .append('\n');
        content.append('\t').append("subu $t").append(source_number)
                .append(", $t").append(source_number).append(", $t")
                .append(reg_number).append('\n');
        setRegNumber(source_number);
//        MipsGenerator.setRegNumberPoolFirst(reg_number);
        content.append(genCall());
        return content.toString();
    }

}
