package q9k.buaa.IR.Types;

public class FunctionType implements Type {
    public static final FunctionType FunctionType = new FunctionType();

    @Override
    public int getLevel() {
        return 0;
    }
}
