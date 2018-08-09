package br.com.leverton.shortenurl.app;

import br.com.leverton.shortenurl.routes.IRoutesDefinition;
import com.google.inject.Guice;
import com.google.inject.Injector;

import static spark.Spark.port;

public class App {


    private App() {

        port(getHerokuAssignedPort());
        Injector injector = Guice.createInjector(new Module());
        IRoutesDefinition routesDefinition = injector.getInstance(IRoutesDefinition.class);

    }

    private static int getHerokuAssignedPort() {
//         this will get the heroku assigned port in production
//         or return 8080 for use in local dev
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 8080; //return 8080 on localhost
    }


    public static void main(String[] args) {
        new App();
    }
}