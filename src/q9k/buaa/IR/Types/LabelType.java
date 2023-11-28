package q9k.buaa.IR.Types;

public class LabelType implements Type{
    public static final LabelType LabelType = new LabelType();

    @Override
    public int getLevel() {
        return 0;
    }
}
