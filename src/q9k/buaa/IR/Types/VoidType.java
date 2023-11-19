package q9k.buaa.IR.Types;

public class VoidType implements Type{
    public static VoidType voidType = new VoidType();

    @Override
    public String toString() {
        return "void";
    }
}
