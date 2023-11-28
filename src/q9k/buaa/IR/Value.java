package q9k.buaa.IR;

import q9k.buaa.IR.Types.IntegerType;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.IR.Types.VoidType;

import java.util.ArrayList;
import java.util.List;

public abstract class Value {
    private String name;
    private Type type;
    private List<Use> uses;

    public Value(){
        this.uses = new ArrayList<>();
    }

    public Value(String name){
        this.name = name;
        this.uses = new ArrayList<>();
    }

    public Value(Type type){
        this.type = type;
        this.uses = new ArrayList<>();
    }

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
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }


    public void replaceAllUsesWith(Value value) {

    }
}
