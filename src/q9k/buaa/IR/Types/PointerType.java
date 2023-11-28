package q9k.buaa.IR.Types;

public class PointerType implements Type{
    private Type sourceType;
    public PointerType(Type sourceType){
        this.sourceType = sourceType;
    }

    @Override
    public String toString() {
        StringBuilder content = new StringBuilder();
        content.append(sourceType.toString());
        if(!(sourceType instanceof IntegerType)){
            content.append(" ");
        }
        content.append("*");
        return content.toString();
    }

    public Type getSourceType(){
        return this.sourceType;
    }

    @Override
    public int getLevel() {
        return sourceType.getLevel()+1;
    }
}
