package q9k.buaa.IR.Instructions;

import q9k.buaa.Backend.MipsGenerator;
import q9k.buaa.IR.*;
import q9k.buaa.IR.Types.ArrayType;
import q9k.buaa.IR.Types.PointerType;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.IR.Types.VoidType;

import java.util.ArrayList;
import java.util.List;

public class CallInst extends Instruction {

    private List<Value> params;
    private Integer paramNumber;

    public CallInst(Type type) {
        setType(type);
        this.paramNumber = 0;
        this.params = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        Type type = this.getType();
        Function function = (Function) this.getOperand(0);
        if (type.equals(VoidType.VoidType)) {
            content.append("call ").append(type.toString()).append(" ").append(function.getName()).append("(");
        } else {
            content.append(this.getName()).append(" = ").append("call ").append(type.toString()).append(" ")
                    .append(function.getName()).append("(");
//            return this.getName() + " = " + "call " + type + " " + function.getName()+"()";
        }
        if (!params.isEmpty()) {
            int index = 0;
            for (; index < params.size() - 1; index++) {
                Value param = params.get(index);
                if (param.getType() instanceof ArrayType) {
                    content.append(new PointerType(((ArrayType) param.getType()).getElementType()));
                } else {
                    content.append(param.getType());
                }
                content.append(" ").append(param.getName()).append(", ");
            }
            Value param = params.get(index);
            if (param.getType() instanceof ArrayType) {
                content.append(new PointerType(((ArrayType) param.getType()).getElementType()));
            } else {
                content.append(param.getType());
            }
            content.append(" ").append(param.getName());
        }
        content.append(")");
        return content.toString();
    }

    public void addParam(Value param) {
        this.params.add(param);
        param.getUses().add(new Use(this, param));
        param.setFormalParamNumber(this.paramNumber++);
        param.setParam(true);
    }

    @Override
    public String getName() {
        Type type = this.getType();
        if (type.equals(VoidType.VoidType)) {
            return null;
        } else {
            return super.getName();
        }
    }

    @Override
    public String genMips() {
        StringBuilder content = new StringBuilder();
        Function function = (Function) this.getOperand(0);
        String name = function.getName();
        if (name.equals("@getint")) {
            content.append('\t').append("li $v0, 5\n");
            content.append('\t').append("syscall\n");
            setRegNumber(MipsGenerator.getRegNumber());
            content.append('\t').append("move $t").append(getRegNumber()).append(", $v0").append('\n');
        } else if (name.equals("@putint")) {
            Value value = this.params.get(0);
            if (value instanceof ConstantInt) {
                content.append('\t').append("li $a0, ").append(((ConstantInt) value).getValue()).append('\n');
            } else {
                content.append('\t').append("lw $a0, ").append(value.getStackPointerNumber()).append("($sp)").append('\n');
//                content.append('\t').append("move $a0, $t").append(value.getRegNumber()).append('\n');
            }
            content.append('\t').append("li $v0, 1\n");
            content.append('\t').append("syscall\n");
        } else if (name.equals("@putch")) {
            ConstantInt constantInt = (ConstantInt) this.params.get(0);
            content.append('\t').append("li $a0, ").append(constantInt.getValue()).append('\n');
            content.append('\t').append("li $v0, 11\n");
            content.append('\t').append("syscall\n");
        }
        //不使用putstr
/*        else if(name.equals("@putstr")){
            content.append('\t').append("li $v0, 4\n");
            content.append('\t').append("syscall\n");
        }*/
        else {
            content.append("# ").append(toString()).append('\n');
            for (int index = 0; index < 4 && index < params.size(); index++) {
                Value param = params.get(index);
                if (param instanceof ConstantInt) {
                    content.append('\t').append("li $a").append(index).append(", ")
                            .append(((ConstantInt) param).getValue()).append('\n');
                } else {
                    content.append('\t').append("lw $a").append(index).append(", ")
                            .append(param.getStackPointerNumber()).append("($sp)").append('\n');
//                    content.append('\t').append("move $a").append(index).append(", $t")
//                            .append(param.getRegNumber()).append('\n');
                }

            }
//            if(!params.isEmpty()){
//                MipsGenerator.setStackPointerNumber(params.get(0).getStackPointerNumber());
//            }
            Integer stackPointerNumber_ra = MipsGenerator.getStackPointerNumber();
            Integer stackPointerNumber_t0 = MipsGenerator.getStackPointerNumber();
            Integer stackPointerNumber_t1 = MipsGenerator.getStackPointerNumber();
            Integer stackPointerNumber_t2 = MipsGenerator.getStackPointerNumber();
            Integer stackPointerNumber_t3 = MipsGenerator.getStackPointerNumber();
            Integer stackPointerNumber_t4 = MipsGenerator.getStackPointerNumber();
            Integer stackPointerNumber_t5 = MipsGenerator.getStackPointerNumber();
            Integer stackPointerNumber_t6 = MipsGenerator.getStackPointerNumber();
            Integer stackPointerNumber_t7 = MipsGenerator.getStackPointerNumber();
            Integer stackPointerNumber = stackPointerNumber_t7;
//            MipsGenerator.setStackPointerNumber(stackPointerNumber);
            content.append('\t').append("sw $ra, ").append(stackPointerNumber_ra).append("($sp)").append('\n');
            content.append('\t').append("sw $t0, ").append(stackPointerNumber_t0).append("($sp)").append('\n');
            content.append('\t').append("sw $t1, ").append(stackPointerNumber_t1).append("($sp)").append('\n');
            content.append('\t').append("sw $t2, ").append(stackPointerNumber_t2).append("($sp)").append('\n');
            content.append('\t').append("sw $t3, ").append(stackPointerNumber_t3).append("($sp)").append('\n');
            content.append('\t').append("sw $t4, ").append(stackPointerNumber_t4).append("($sp)").append('\n');
            content.append('\t').append("sw $t5, ").append(stackPointerNumber_t5).append("($sp)").append('\n');
            content.append('\t').append("sw $t6, ").append(stackPointerNumber_t6).append("($sp)").append('\n');
            content.append('\t').append("sw $t7, ").append(stackPointerNumber_t7).append("($sp)").append('\n');
            content.append('\t').append("subu $sp, $sp, ").append(-(stackPointerNumber - 4)).append('\n');

            for (int index = 4; index < params.size(); index++) {
                Value param = params.get(index);
                if (param instanceof ConstantInt) {
                    content.append('\t').append("li $t8, ")
                            .append(((ConstantInt) param).getValue()).append('\n');
                    content.append('\t').append("sw $t8, ")
                            .append(-index * 4).append("($sp)").append('\n');
                } else {
                    content.append('\t').append("lw $t8, ").append(param.getStackPointerNumber()).append("($fp)").append('\n');
                    content.append('\t').append("sw $t8, ")
                            .append(-index * 4).append("($sp)").append('\n');
//                    content.append('\t').append("sw $t").append(param.getRegNumber()).append(", ")
//                            .append(-index * 4).append("($sp)").append('\n');
                }
            }

            content.append('\t').append("move $fp, $sp\n");
            content.append('\t').append("jal ").append(function.getName().substring(1)).append('\n');
            content.append('\t').append("addu $sp, $sp, ").append(-(stackPointerNumber - 4)).append('\n');
            content.append('\t').append("move $fp, $sp\n");
            content.append('\t').append("lw $ra, ").append(stackPointerNumber_ra).append("($sp)\n");
            content.append('\t').append("lw $t0, ").append(stackPointerNumber_t0).append("($sp)\n");
            content.append('\t').append("lw $t1, ").append(stackPointerNumber_t1).append("($sp)\n");
            content.append('\t').append("lw $t2, ").append(stackPointerNumber_t2).append("($sp)\n");
            content.append('\t').append("lw $t3, ").append(stackPointerNumber_t3).append("($sp)\n");
            content.append('\t').append("lw $t4, ").append(stackPointerNumber_t4).append("($sp)\n");
            content.append('\t').append("lw $t5, ").append(stackPointerNumber_t5).append("($sp)\n");
            content.append('\t').append("lw $t6, ").append(stackPointerNumber_t6).append("($sp)\n");
            content.append('\t').append("lw $t7, ").append(stackPointerNumber_t7).append("($sp)\n");

//            MipsGenerator.setStackPointerNumber(stackPointerNumber_ra);
            setRegNumber(MipsGenerator.getRegNumber());
            content.append('\t').append("move $t").append(getRegNumber()).append(", $v0").append('\n');
        }
        content.append(genCall());
        return content.toString();
    }

}
