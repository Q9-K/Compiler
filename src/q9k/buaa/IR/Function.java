package q9k.buaa.IR;

import q9k.buaa.IR.Instructions.BinaryOperator;
import q9k.buaa.IR.Instructions.ReturnInst;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.Symbol.SymbolType;
import q9k.buaa.Token.Token;
import q9k.buaa.Utils.IRModule;

import java.util.ArrayList;
import java.util.List;

public class Function extends GlobalValue {
    public int number = 0;
    private boolean isExternal;
    private List<BasicBlock> basicBlocks;
    private SymbolType returnType;
    private List<Argument> arguments;

    public Function(String name, Type type) {
        super(name, type);
        this.basicBlocks = new ArrayList<>();
        this.arguments = new ArrayList<>();
    }



    public Function(String name, Type type, boolean isExternal) {
        super(name, type);
        this.isExternal = isExternal;
        this.basicBlocks = new ArrayList<>();
        this.arguments = new ArrayList<>();
    }


    public BasicBlock getEntryBlock() {
        if (!basicBlocks.isEmpty()) {
            return this.basicBlocks.get(0);
        }
        return null;
    }

    public void addArgument(Argument argument) {
        this.arguments.add(argument);
    }

    public void addBasicBlock(BasicBlock basicBlock) {
        this.basicBlocks.add(basicBlock);
    }


    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        if (isExternal) {
            content.append("declare ").append(returnType.toString()).
                    append(" ").append(this.getName()).append("(");
            if (!arguments.isEmpty()) {
                int index = 0;
                for (; index < arguments.size() - 1; ++index) {
                    content.append(arguments.get(index).getType().toString()).append(", ");
                }
                content.append(arguments.get(index).getType().toString());
            }
            content.append(")");
        } else {
            content.append("define dso_local ").append(returnType.toString()).
                    append(" ").append(this.getName()).append("(");
            if (!arguments.isEmpty()) {
                int index = 0;
                for (; index < arguments.size() - 1; ++index) {
                    content.append(arguments.get(index).getType().toString()).append(" ").append(arguments.get(index).getName()).append(", ");
                }
                content.append(arguments.get(index).getType().toString()).append(" ").append(arguments.get(index).getName());
            }
            content.append(")").append(" {").append("\n");
            int index = 0;
            for(BasicBlock basicBlock : basicBlocks){
                basicBlock.getName();
                for(Instruction instruction: basicBlock.getInstructions()){
                    instruction.getName();
                }
            }
            for (; index < basicBlocks.size() - 1; index++) {
                BasicBlock basicBlock = basicBlocks.get(index);
//                content.append(";<label>:").append(this.number++).append("\n");
                if (index > 0) {
                    content.append(basicBlock.getName()).append(":").append("\n");
                }
                content.append(basicBlock.toString());
            }
            BasicBlock basicBlock = basicBlocks.get(index);
            Instruction instruction = basicBlock.getTerminator();
//            System.out.println(instruction.getClass());
            if (!(instruction instanceof ReturnInst)) {
                ReturnInst returnInst = new ReturnInst();
                basicBlock.addInstruction(returnInst);
            }
            if (index > 0) {
                content.append(basicBlock.getName()).append(":").append("\n");
            }
            content.append(basicBlock.toString());
            content.append("}").append("\n");
        }

        return content.toString();
    }

    public void setReturnType(SymbolType returnType) {
        this.returnType = returnType;
    }

    public SymbolType getReturnType() {
        return this.returnType;
    }


    @Override
    public void translate() {

    }
}
