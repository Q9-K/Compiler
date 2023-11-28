package q9k.buaa.IR;

import q9k.buaa.IR.Types.Type;

public abstract class Constant extends User {


    public Constant() {
        super();
    }

    public Constant(String name) {
        super(name);
    }

    public Constant(Type type) {
        super(type);
    }

    public Constant(String name, Type type) {
        super(name, type);
    }
}
