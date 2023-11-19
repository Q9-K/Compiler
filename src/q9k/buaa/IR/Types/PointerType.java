package q9k.buaa.IR.Types;

public class PointerType implements Type{
    private Type target_type;
    public PointerType(Type target_type){
        this.target_type = target_type;
    }

    @Override
    public String toString() {
        return target_type.toString()+"*";
    }

    public Type getTarget_type(){
        return this.target_type;
    }
}
