package q9k.buaa.IR;

import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.Types.LabelType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BasicBlock extends Value {
    private Function parent;
    private List<Instruction> instructions;

    public BasicBlock() {
        super();
        setType(LabelType.LabelType);
        setParent(IRGenerator.getCurFunction());
        this.instructions = new ArrayList<>();
    }


    public void addInstruction(Instruction instruction) {
        this.instructions.add(instruction);
    }

    public void addInstruction(int index, Instruction instruction) {
        this.instructions.add(index, instruction);
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        for (Instruction instruction : instructions) {
            if(instruction.isLive()){
                content.append("\t").append(instruction.toString()).append("\n");
            }
        }
        return content.toString();
    }

    public void setParent(Function parent) {
        this.parent = parent;
    }

    public Function getParent() {
        return this.parent;
    }

    public List<Instruction> getInstructions() {
        return this.instructions;
    }

    public Instruction getTerminator() {
        if (!instructions.isEmpty()) {
            return instructions.get(instructions.size() - 1);
        }
        return null;
    }

    @Override
    public String getName() {
        if (super.getName() == null) {
            super.setName("t" + this.parent.number++);
        }
        return super.getName();
    }

    @Override
    public String genMips() {
//        MipsGenerator.resetNumber();
        StringBuilder content = new StringBuilder();
        for (Instruction instruction : instructions) {
            if(instruction.isLive()){
                content.append(instruction.genMips());
            }
        }
        return content.toString();
    }

    public boolean isEnd() {
        Instruction terminator = getTerminator();
        if (terminator == null) {
            return false;
        } else return terminator.isEnd();
    }

    @Override
    public void optimize() {
        Iterator<Instruction> instructionIterator = instructions.iterator();
        while(instructionIterator.hasNext()){
            Instruction instruction = instructionIterator.next();
            instruction.optimize();
        }
    }
}
