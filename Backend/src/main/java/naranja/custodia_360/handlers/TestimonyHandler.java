package naranja.custodia_360.handlers;

import naranja.custodia_360.models.TestimonyContext;

public abstract class TestimonyHandler {
    protected TestimonyHandler nextHandler;

    public void setNext(TestimonyHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public void process(TestimonyContext context) {
        // Ejecuta la lógica analítica del handler actual
        analyze(context);

        // Si existe un siguiente eslabón configurado, le transfiere el contexto
        if (nextHandler != null) {
            nextHandler.process(context);
        }
    }

    protected abstract void analyze(TestimonyContext context);
}
