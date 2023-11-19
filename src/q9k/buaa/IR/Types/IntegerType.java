package q9k.buaa.IR.Types;

public class IntegerType implements Type{
    private int bits;
    private IntegerType(int bits){
        this.bits = bits;
    }
    public static final IntegerType i1 = new IntegerType(1);
    public static final IntegerType i8 = new IntegerType(8);
    public static final IntegerType i32 = new IntegerType(32);

    @Override
    public String toString() {
        return "i"+String.valueOf(bits);
    }
}
