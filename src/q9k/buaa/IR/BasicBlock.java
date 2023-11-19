package q9k.buaa.IR;

import q9k.buaa.IR.Types.Type;

import java.util.ArrayList;
import java.util.List;

public class BasicBlock extends Value {
    private Function parent;
    private List<Instruction> instructions;

    public BasicBlock(String name, Type type) {
        super(name, type);
        this.instructions = new ArrayList<>();
    }


    public void addInstruction(Instruction instruction) {
        this.instructions.add(instruction);
    }
    public void addInstruction(int index, Instruction instruction){
        this.instructions.add(index, instruction);
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        for(Instruction instruction : instructions){
            content.append("\t").append(instruction.toString()).append("\n");
        }
        return content.toString();
    }
    public void setParent(Function parent){
        this.parent = parent;
    }
    public Function getParent(){
        return this.parent;
    }

    public List<Instruction> getInstructions(){
        return this.instructions;
    }
    public Instruction getTerminator(){
        return instructions.get(instructions.size()-1);
    }
}
