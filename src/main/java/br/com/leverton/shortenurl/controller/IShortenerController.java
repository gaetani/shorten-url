package br.com.leverton.shortenurl.controller;

import spark.Request;
import spark.Response;

/**
 * Created by gaetani on 11/01/17.
 */
public interface IShortenerController {
    String shortIt(Request request, Response response) throws Exception;

    String getAlias(Request request, Response response) throws Exception;
}
