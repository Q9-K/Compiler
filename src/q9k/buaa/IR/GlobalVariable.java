package q9k.buaa.IR;

import q9k.buaa.IR.Types.PointerType;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.IR.Constant;
import q9k.buaa.IR.GlobalValue;

public class GlobalVariable extends GlobalValue {
    private int initializer;

    public GlobalVariable(String name, Type type){
        super(name, type);
    }


    @Override
    public String toString() {
        PointerType pointerType = (PointerType)this.getType();
        return this.getName()+" = "+"dso_local global "+pointerType.getTarget_type().toString()+" "+initializer;
    }

    public void setInitializer(int initializer){
        this.initializer = initializer;
    }

    public int getInitializer(){
        return this.initializer;
    }
}
