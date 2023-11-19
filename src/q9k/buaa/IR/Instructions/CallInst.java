package q9k.buaa.IR.Instructions;

import q9k.buaa.IR.Function;
import q9k.buaa.IR.Instruction;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.IR.Types.VoidType;
import q9k.buaa.IR.Value;

import java.util.ArrayList;
import java.util.List;

public class CallInst extends Instruction {

    private List<Value> params;

    public CallInst(String name, Type type) {
        super(name, type);
        this.params = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        Type type = this.getType();
        Function function = (Function) this.getOperand(0);
        if (type.equals(VoidType.voidType)) {
            content.append("call ").append(type.toString()).append(" ").append(function.getName()).append("(");
        } else {
            content.append(this.getName()).append(" = ").append("call ").append(type.toString()).append(" ")
                    .append(function.getName()).append("(");
//            return this.getName() + " = " + "call " + type + " " + function.getName()+"()";
        }
        if(!params.isEmpty()){
            int index = 0;
            for (; index < params.size() - 1; index++) {
                Value param = params.get(index);
                content.append(param.getType()).append(" ").append(param.getName()).append(", ");
            }
            Value param = params.get(index);
            content.append(param.getType()).append(" ").append(param.getName());
        }
        content.append(")");
        return content.toString();
    }

    public void addParam(Value param) {
        this.params.add(param);
    }
}
