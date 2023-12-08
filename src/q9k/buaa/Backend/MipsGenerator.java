package q9k.buaa.Backend;

import q9k.buaa.INIT.Config;
import q9k.buaa.Utils.IRModule;
import q9k.buaa.Utils.Output;

import java.io.IOException;

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
    public void run() throws IOException {
        Output output = Output.getInstance(Config.getMips_path());
        IRModule irModule = IRModule.getInstance();
        irModule.translate();
        System.out.println("Mips generate finished!");
    }
}
