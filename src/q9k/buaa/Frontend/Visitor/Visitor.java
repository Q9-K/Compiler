package q9k.buaa.Frontend.Visitor;

import q9k.buaa.AST.Syntax;
import q9k.buaa.Error.ErrorHandler;
import q9k.buaa.INIT.Config;

import java.io.IOException;

public class Visitor {
    private Syntax root;
    private static Visitor visitor;

    public Visitor(Syntax root) {
        this.root = root;
    }

    public static Visitor getInstance() {
        if (visitor == null) {
            System.out.println("something wrong happened at visitor init!");
            System.exit(-1);
        }
        return visitor;
    }

    public static Visitor getInstance(Syntax root) {
        if (visitor == null) {
            visitor = new Visitor(root);
        } else if (!visitor.root.equals(root)) {
            visitor = new Visitor(root);
        }
        return visitor;
    }

    public void run() throws IOException {
        root.visit();
        if(Config.error_output_open){
            ErrorHandler.getInstance().print();
        }
        System.out.println("visitor analyze finished!");
    }
}
