import app.Application;

public class Main {


    public static void main(String[] args) throws Exception {
        Application application = new Application("/home/leno/export","localhost",8080);

        application.run();
    }
}
