package org.apache.coyote.http11;

import nextstep.jwp.Response.Response;
import nextstep.jwp.Response.ResponseBody;
import nextstep.jwp.Response.ResponseHeader;
import nextstep.jwp.controller.IndexController;
import nextstep.jwp.controller.LoginController;
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

        } else {
            ResourceFinder resourceFinder = new ResourceFinder();

            ResponseBody responseBody = new ResponseBody(resourceFinder.getResource(path));
            ResponseHeader responseHeader = new ResponseHeader(HttpResponseCode.OK.toString(),
                    HttpResponseCode.OK.getReasonPhrase(),
                    resourceFinder.getContentType(resourceFinder.getFileExtension(path)),
                    responseBody.getLength());
            return new Response(responseHeader, responseBody);
        }
    }
}
