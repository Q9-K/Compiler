package q9k.buaa.Backend;

import q9k.buaa.Utils.IRModule;

public class MipsGenerator {
    private IRModule irModule;
    private static MipsGenerator mipsGenerator;

    public MipsGenerator(IRModule irModule) {
        this.irModule = irModule;
    }

    public static synchronized MipsGenerator getInstance() {
        if (mipsGenerator == null) {
            System.out.println("Something wrong happened at MipsGenerator init!");
            System.exit(-1);
        }
        return mipsGenerator;
    }

    public static synchronized MipsGenerator getInstance(IRModule irModule) {
        if (mipsGenerator == null || mipsGenerator.irModule != irModule) {
            mipsGenerator = new MipsGenerator(irModule);
        }
        return mipsGenerator;
    }
    public void run(){
        System.out.println("保留接口!");
    }
}
