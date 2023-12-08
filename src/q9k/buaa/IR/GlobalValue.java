package q9k.buaa.IR;

import q9k.buaa.Utils.IRModule;
import q9k.buaa.IR.Types.Type;
import q9k.buaa.Utils.Output;

import java.io.IOException;

public abstract class GlobalValue extends Constant {

    public GlobalValue(String name, Type type){
        super(name, type);
    }

}
