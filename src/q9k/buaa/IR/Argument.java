package q9k.buaa.IR;

import q9k.buaa.Frontend.IRGenerator;
import q9k.buaa.IR.Types.Type;

public class Argument extends Value{
    private Function parent;

    public Argument(Type type){
        setType(type);
    }
    public Argument(String name, Type type){
        super(name, type);
        setParent(IRGenerator.getCurFunction());
    }

    public void setParent(Function parent) {
        this.parent = parent;
    }
    @Override
    public String getName() {
        if(super.getName()==null){
            super.setName("%t"+this.parent.number++);
        }
        return super.getName();
    }
    @Override
    public String toString() {
        return this.getType()+" "+this.getName();
    }

    @Override
    public String genMips() {
        return "";
    }

    @Override
    public void optimize() {

    }
}
