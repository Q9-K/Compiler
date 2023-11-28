package q9k.buaa.IR;

import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Types.Type;

public class ConstantInt extends Constant{

    private int value;

    public ConstantInt(Integer value){
        this.value = value;
        setType(IntegerType.i32);
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public static ConstantInt ZERO = new ConstantInt(0);

    @Override
    public String getName() {
        return String.valueOf(value);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
