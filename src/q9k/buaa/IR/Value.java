package q9k.buaa.IR;

import q9k.buaa.IR.Types.Type;
import q9k.buaa.Utils.Output;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Value {
    private String name;
    private Type type;
    private List<Use> uses;
    private Integer value;

    public Value(String name, Type type) {
        this.name = name;
        this.type = type;
        this.uses = new ArrayList<>();
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        if(this.value!=null){
            return this.value.toString();
        }
        else if(this.name!=null){
            return this.name;
        }
        return null;
    }

    public void setName(String name){
        this.name = name;
    }

    public void replaceAllUsesWith(Value value) {

    }
    public void setValue(Integer value){
        this.value = value;
    }
    public Integer getValue(){
        return this.value;
    }

    @Override
    public String toString() {
        if(this.value == null){
            return this.type.toString()+" "+this.name;
        }
        else{
            return this.type.toString()+" "+this.value.toString();
        }
    }
}
