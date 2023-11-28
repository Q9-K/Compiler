package q9k.buaa.IR.Types;

public class ArrayType implements Type {
    private Type elementType;
    private int numElements;

    public ArrayType(Type elementType, int numElements) {
        this.elementType = elementType;
        this.numElements = numElements;
    }

    @Override
    public String toString() {
        if(this.numElements>0){
            return "["+this.numElements+" x "+this.elementType.toString()+"]";
        }
        else{
            return this.elementType.toString()+"*";
        }
    }

    public Type getElementType() {
        return elementType;
    }

    public void setElementType(Type elementType) {
        this.elementType = elementType;
    }

    public int getNumElements() {
        return numElements;
    }

    public void setNumElements(int numElements) {
        this.numElements = numElements;
    }

    @Override
    public int getLevel() {
        return elementType.getLevel()+1;
    }
}
