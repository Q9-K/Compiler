package q9k.buaa.IR.Types;

public class VoidType implements Type{
    public static final VoidType VoidType = new VoidType();

    @Override
    public String toString() {
        return "void";
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
