package q9k.buaa.IR.Instructions;

import q9k.buaa.IR.BasicBlock;
import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Types.VoidType;
import q9k.buaa.IR.Value;

import java.util.ArrayList;
import java.util.List;

public class BranchInst extends Instruction {
    private List<BasicBlock> basicBlocks;


    public BranchInst() {
        setType(VoidType.VoidType);
        this.basicBlocks = new ArrayList<>();
    }

    @Override
    public String toString() {
        if (isUnConditional()) {
            return "br label " + "%" + this.getBasicBlock(0).getName();
        } else {
            Value value = this.getOperand(0);
            BasicBlock true_block = this.getBasicBlock(0);
            BasicBlock false_block = this.getBasicBlock(1);
            return "br " + value.getType().toString() + " " + value.getName() + ", " +
                    "label " + "%" + true_block.getName() + ", " +
                    "label " + "%" + false_block.getName();
        }
    }

    public boolean isConditional() {
        return this.basicBlocks.size() == 2;
    }

    public boolean isUnConditional() {
        return this.basicBlocks.size() == 1;
    }

    public void addTargetBlock(BasicBlock basicBlock) {
        this.basicBlocks.add(basicBlock);
    }

    public List<BasicBlock> getBasicBlocks() {
        return this.basicBlocks;
    }

    public BasicBlock getBasicBlock(int index) {
        return this.basicBlocks.get(index);
    }

    @Override
    public String genMips() {
        StringBuilder content = new StringBuilder();
        if (isUnConditional()) {
            BasicBlock basicBlock = this.getBasicBlock(0);
            content.append('\t').append("j ").append(basicBlock.getParent().getName().substring(1))
                    .append('_').append(basicBlock.getName()).append('\n');
        } else {
            IcmpInst icmpInst = (IcmpInst) getFirst();
            int reg_number = icmpInst.getRegNumber();
            BasicBlock trueBlock = getBasicBlock(0);
            BasicBlock falseBlock = getBasicBlock(1);
            content.append('\t').append("bne $t").append(reg_number).append(", $zero, ")
                    .append(trueBlock.getParent().getName().substring(1))
                    .append('_').append(trueBlock.getName()).append('\n');
            content.append('\t').append("beq $t").append(reg_number).append(", $zero, ")
                    .append(falseBlock.getParent().getName().substring(1))
                    .append('_').append(falseBlock.getName()).append('\n');
        }
        return content.toString();
    }

}
