package br.com.leverton.shortenurl.routes;

import br.com.leverton.shortenurl.controller.IShortenerController;
import br.com.leverton.shortenurl.exception.BusinessException;
import br.com.leverton.shortenurl.handler.RequestLogHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

import static spark.Spark.*;

public class RoutesDefinition implements IRoutesDefinition {


    private static final Logger LOGGER = LoggerFactory.getLogger(RoutesDefinition.class);

    private IShortenerController shortenerController;

    @Inject
    public RoutesDefinition(IShortenerController shortenerController) {
        this.shortenerController = shortenerController;
    }

    private void beforeRoutes() {
        //ensure user is logged in to have access to protected routes
        before((req, res) -> RequestLogHandler.logRequest(req, res));
    }


    private void configureRoutes() {

        get("/create", "application/json", shortenerController::shortIt);

        //Gson gson = new Gson();
        get("/:alias", "application/json", shortenerController::getAlias);

    }

    private void configureExceptions() {
        exception(BusinessException.class, (exception, request, response) -> {
            // Handle the exception hereJsonObject timeTaken = new JsonObject();
            BusinessException businessException = (BusinessException) exception;
            JsonObject exceptionObject = new JsonObject();
            exceptionObject.addProperty("alias", businessException.getAlias());
            exceptionObject.addProperty("err_code", businessException.getCodeError().getCode());
            exceptionObject.addProperty("description", businessException.getCodeError().getDescription());
            response.status(businessException.getCodeError().getHttpCode());
            response.body(new Gson().toJson(exceptionObject));
        });

        exception(Exception.class, (exception, request, response) -> {
            // Handle the exception hereJsonObject timeTaken = new JsonObject();
            exception.printStackTrace();
            LOGGER.error("Exception nao tratada", exception);
        });

        notFound((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Custom 404\"}";
        });

        internalServerError((req, res) -> {
            res.type("application/json");
            return "{\"message\":\"Custom 500 handling\"}";
        });

    }


    private void afterRoutes() {
        after((req, res) -> {
            //Nothing yet
        });
    }

    private static void enableCORS(final String origin, final String methods, final String headers) {

        options("/*", (request, response) -> {

            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            response.header("Access-Control-Allow-Origin", origin);
            response.header("Access-Control-Request-Method", methods);
            response.header("Access-Control-Allow-Headers", headers);
            // Note: this may or may not be necessary in your particular application
            response.type("application/json");
        });
    }


    @PostConstruct
    public void init() {
        beforeRoutes();
        configureRoutes();
        afterRoutes();
        configureExceptions();
    }
}
