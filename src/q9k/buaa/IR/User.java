package q9k.buaa.IR;

import q9k.buaa.IR.Types.Type;

import java.util.ArrayList;
import java.util.List;

public abstract class User extends Value {
    private List<Value> operands;

    public User() {
        super();
        this.operands = new ArrayList<>();
    }

    public User(String name) {
        super(name);
        this.operands = new ArrayList<>();
    }

    public User(Type type) {
        super(type);
        this.operands = new ArrayList<>();
    }

    public User(String name, Type type) {
        super(name, type);
        this.operands = new ArrayList<>();
    }


    public Value getOperand(int index) {
        return operands.get(index);
    }

    public void addOperand(Value value) {
        this.operands.add(value);
    }

    public List<Value> getOperands() {
        return this.operands;
    }

    public Value getFirst() {
        if (!operands.isEmpty()) {
            return operands.get(0);
        }
        return null;
    }

    public Value getLast() {
        if (!operands.isEmpty()) {
            return operands.get(operands.size() - 1);
        }
        return null;
    }
}
