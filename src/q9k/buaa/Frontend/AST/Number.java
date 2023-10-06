package q9k.buaa.Frontend.AST;

public class Number implements Syntax{

    private Syntax int_const;

    public Number(Syntax int_const) {
        this.int_const = int_const;
        handleError();
    }

    @Override
    public void print() {

    }

    @Override
    public void handleError() {

    }
}
