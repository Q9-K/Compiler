package q9k.buaa.IR;

import q9k.buaa.Backend.MipsGenerator;
import q9k.buaa.IR.Types.Type;

import java.util.ArrayList;
import java.util.List;

public abstract class Value {
    private String name;
    private Type type;
    private List<Use> uses;
    private Integer regNumber;
    private Integer stackPointerNumber;
    private Integer framePointerNumber;
    private Integer realParamNumber;
    private Integer formalParamNumber;
    private boolean isParam = false;
    private boolean live = true;

    public Value() {
        this.uses = new ArrayList<>();
    }

    public Value(String name) {
        this.name = name;
        this.uses = new ArrayList<>();
    }

    public Value(Type type) {
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

    public void setName(String name) {
        this.name = name;
    }


    public void replaceAllUsesWith(Value value) {

    }

    public List<Use> getUses() {
        return uses;
    }

    public abstract String genMips();

    public Integer getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(Integer regNumber) {
        this.regNumber = regNumber;
    }

    public Integer getStackPointerNumber() {
        return stackPointerNumber;
    }

    public void setStackPointerNumber(Integer stackPointerNumber) {
        this.stackPointerNumber = stackPointerNumber;
    }

    public Integer getFramePointerNumber() {
        return framePointerNumber;
    }

    public void setFramePointerNumber(Integer framePointerNumber) {
        this.framePointerNumber = framePointerNumber;
    }

    public void setRealParamNumber(Integer realParamNumber) {
        this.realParamNumber = realParamNumber;
    }

    public Integer getRealParamNumber() {
        return realParamNumber;
    }

    public boolean isParam() {
        return isParam;
    }

    public void setParam(boolean param) {
        isParam = param;
    }

    public Integer getFormalParamNumber() {
        return formalParamNumber;
    }

    public void setFormalParamNumber(Integer formalParamNumber) {
        this.formalParamNumber = formalParamNumber;
    }

    public String genCall() {
        StringBuilder content = new StringBuilder();
        if (isParam) {
            Integer stackPointerNumber = MipsGenerator.getStackPointerNumber();
            setStackPointerNumber(stackPointerNumber);
            content.append('\t').append("sw $t")
                    .append(getRegNumber())
                    .append(", ").append(stackPointerNumber)
                    .append("($sp)").append('\n');
        }
        return content.toString();
    }

    public abstract void optimize();

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isLive() {
        return live;
    }
}
