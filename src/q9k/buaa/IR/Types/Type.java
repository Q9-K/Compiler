package q9k.buaa.IR.Types;

public interface Type {
    default boolean isIntgerType(Type type){
        return type instanceof IntegerType;
    }

    int getLevel();
}
