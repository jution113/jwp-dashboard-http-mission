package org.apache.coyote.http11;

import nextstep.jwp.Response.Response;
import nextstep.jwp.Response.ResponseBody;
import nextstep.jwp.Response.ResponseHeader;
import nextstep.jwp.controller.*;
import nextstep.jwp.request.Request;
import nextstep.jwp.util.ResourceFinder;
import org.apache.util.HttpResponseCode;

import java.io.IOException;

public class ControllerMapper {
    public Response findController(Request request) throws IOException {
        String path = request.getRequestHeader().getPath();

        if(path.startsWith("/index")) {
            IndexController indexController = new IndexController();
            return indexController.handleRequest(request);

        } else if(path.startsWith("/login")) {
            LoginController loginController = new LoginController();
            return loginController.handleRequest(request);

        } else if(path.startsWith("/register")) {
            return new Response();

        } else if(path.startsWith("/css")) {
            CssController cssController = new CssController();
            return cssController.handleRequest(request);

        } else if(path.startsWith("/js")) {
            JsController jsController = new JsController();
            return jsController.handleRequest(request);

        } else if(path.startsWith("/assets")) {
            AssetsController assetsController = new AssetsController();
            return assetsController.handleRequest(request);

        } else {
            ResourceFinder resourceFinder = new ResourceFinder();
            ResponseBody responseBody = new ResponseBody(resourceFinder.getResource(request.getRequestHeader().getPath()));
            ResponseHeader responseHeader = new ResponseHeader(String.valueOf(HttpResponseCode.NOT_FOUND.getCode()),
                    HttpResponseCode.NOT_FOUND.getReasonPhrase(),
                    resourceFinder.getContentType(resourceFinder.getFileExtension(request.getRequestHeader().getPath())),
                    responseBody.getLength());

            return new Response(responseHeader, responseBody);
        }
    }
}
