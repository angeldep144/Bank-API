import frontcontroller.FrontController;
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(9000); // localhost:9000
        new FrontController(app);

    }
}
