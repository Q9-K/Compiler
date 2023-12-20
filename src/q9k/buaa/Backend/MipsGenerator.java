package q9k.buaa.Backend;

import q9k.buaa.INIT.Config;
import q9k.buaa.IR.IRModule;
import q9k.buaa.Utils.Output;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class MipsGenerator {

    private IRModule irModule;
    private static MipsGenerator mipsGenerator;
    private static Integer regNumber = 0;
    private static Integer stackPointerNumber = 0;
    private static Integer framePointerNumber = 0;
    private static Integer paramNumber = 0;
    private static LinkedList<Integer> linkedList = new LinkedList<>();

    static {
        linkedList.addAll(Arrays.asList(0, 1, 2, 3, 5, 6, 7));
    }

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
        output.write(irModule.genMips());
        System.out.println("Mips generate finished!");
        Config.setOPTIMIZED(true);
        output = Output.getInstance(Config.getMips_optimized_path());
        output.write(irModule.genMips());
        System.out.println("Mips optimize finished!");
    }


    public static Integer getRegNumber() {
        Integer temp = linkedList.getFirst();
        linkedList.removeFirst();
        linkedList.addLast(temp);
        return temp;
    }

    public static Integer getStackPointerNumber() {
        Integer temp = stackPointerNumber;
        stackPointerNumber -= 4;
        framePointerNumber += 4;
        return temp;
    }

    public static Integer getFramePointerNumber(int size) {
        Integer temp = framePointerNumber;
        framePointerNumber += size * 4;
        stackPointerNumber -= size * 4;
        return temp;
    }

    public static void setFramePointerNumber(Integer framePointerNumber) {
        MipsGenerator.framePointerNumber = framePointerNumber;
    }

    public static void setStackPointerNumber(Integer stackPointerNumber) {
        MipsGenerator.stackPointerNumber = stackPointerNumber;
    }

    public static void setRegNumberPoolLast(Integer regNumber) {
        for (Integer item : linkedList) {
            if (item.equals(regNumber)) {
                linkedList.remove(item);
                linkedList.addLast(item);
                break;
            }
        }
    }

    public static void setRegNumberPoolFirst(Integer regNumber) {
        for (Integer item : linkedList) {
            if (item.equals(regNumber)) {
                linkedList.remove(item);
                linkedList.addFirst(item);
                break;
            }
        }
    }

    public static void resetNumber() {
        Collections.sort(linkedList);
        MipsGenerator.stackPointerNumber = 0;
        MipsGenerator.framePointerNumber = 0;
        MipsGenerator.paramNumber = 0;
    }

    public static void setParamNumber(Integer paramNumber) {
        MipsGenerator.paramNumber = paramNumber;
    }

    public static Integer getParamNumber() {
        Integer temp = paramNumber;
        paramNumber++;
        return temp;
    }
}
