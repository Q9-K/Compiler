package q9k.buaa.IR;

import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Types.PointerType;
import q9k.buaa.IR.Types.Type;

public class GlobalVariable extends GlobalValue {

    private boolean isConst;
    private Constant initializer;

    public GlobalVariable(String name, Type type){
        super(name, type);
    }
    public GlobalVariable(String name ,Type type, boolean isConst){
        super(name, type);
        this.isConst = isConst;
    }

    @Override
    public String toString() {
        PointerType pointerType = (PointerType)this.getType();
        StringBuilder content = new StringBuilder();
        content.append(this.getName()).append(" = ").append("dso_local ");
        if(isConst){
            content.append("constant ");
        }
        else{
            content.append("global ");
        }
        content.append(pointerType.getSourceType().toString()).append(' ');
        if(this.initializer!=null){
            content.append(this.initializer.toString());
        }
        else{
            if(pointerType.getSourceType().equals(IntegerType.i32)){
                content.append(0);
            }
            else{
                content.append("zeroinitializer");
            }
        }
        return content.toString();
    }

    public void setInitializer(Constant initializer){
        this.initializer = initializer;
    }

    public Constant getInitializer(){
        return this.initializer;
    }

    public void setConst(boolean aConst) {
        isConst = aConst;
    }

    public boolean isConst() {
        return isConst;
    }

    @Override
    public void translate() {
        
    }
}
