package q9k.buaa.IR;

import q9k.buaa.IR.Types.PointerType;
import q9k.buaa.IR.Types.Type;

public class Constant extends Instruction{

    private int initializer;
    public Constant(String name, Type type){
        super(name, type);
    }



    @Override
    public String toString() {
        PointerType pointerType = (PointerType)this.getType();
        return this.getName()+" = "+"dso_local constant "+pointerType.getTarget_type().toString()+" "+initializer;
    }
    public void setInitializer(int initializer){
        this.initializer = initializer;
    }

    public int getInitializer(){
        return this.initializer;
    }
}
