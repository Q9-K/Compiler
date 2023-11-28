package q9k.buaa.IR;

import q9k.buaa.IR.Types.ArrayType;

import java.util.List;

public class ConstantArray extends Constant {

    private List<Constant> constants;

    public ConstantArray(List<Constant> constants) {
        this.constants = constants;
        setType(new ArrayType(constants.get(0).getType(), constants.size()));
    }

    public void setConstants(List<Constant> constants) {
        this.constants = constants;
    }

    public List<Constant> getConstants() {
        return constants;
    }

    @Override
    public String toString() {
        if (constants.isEmpty()) {
            return "zeroinitializer";
        } else {
            if (constants.get(0) instanceof ConstantInt) {
                boolean allZero = true;
                for (Constant constant : constants) {
                    ConstantInt constantInt = (ConstantInt) constant;
                    if (constantInt.getValue() != 0) {
                        allZero = false;
                        break;
                    }
                }
                if (allZero) {
                    return "zeroinitializer";
                }
                StringBuilder content = new StringBuilder();
                content.append("[");
                int index = 0;
                for (; index < constants.size() - 1; index++) {
                    Constant constant = constants.get(index);
                    content.append("i32 ").append(constant.toString()).append(", ");
                }
                Constant constant = constants.get(index);
                content.append("i32 ").append(constant.toString());
                content.append("]");
                return content.toString();
            } else if (constants.get(0) instanceof ConstantArray) {
                StringBuilder content = new StringBuilder();
                content.append("[");
                int index = 0;
                for (; index < constants.size() - 1; index++) {
                    Constant constant = constants.get(index);
                    ConstantArray constantArray = (ConstantArray) constant;
                    content.append("[").append(constantArray.getConstants().size())
                            .append(" x i32] ").append(constantArray.toString()).append(", ");
                }
                Constant constant = constants.get(index);
                ConstantArray constantArray = (ConstantArray) constant;
                content.append("[").append(constantArray.getConstants().size())
                        .append(" x i32] ").append(constantArray.toString());
                content.append("]");
                return content.toString();
            }
        }
        return null;
    }
}
