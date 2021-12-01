package frontcontroller;

import io.javalin.Javalin;

public class FrontController {
    public FrontController(Javalin app) {
        new Dispatcher(app);
    }
}
