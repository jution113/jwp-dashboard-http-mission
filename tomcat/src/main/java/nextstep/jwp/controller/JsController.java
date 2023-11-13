package nextstep.jwp.controller;

import nextstep.jwp.Response.Response;
import nextstep.jwp.Response.ResponseBody;
import nextstep.jwp.Response.ResponseHeader;
import nextstep.jwp.request.Request;
import nextstep.jwp.util.ResourceFinder;
import org.apache.util.HttpResponseCode;

import java.io.IOException;

public class JsController {
    // url: /js
    public Response handleRequest(Request request) throws IOException {
        ResourceFinder resourceFinder = new ResourceFinder();
        ResponseBody responseBody = new ResponseBody(resourceFinder.getResource(request.getRequestHeader().getPath()));
        ResponseHeader responseHeader = new ResponseHeader(String.valueOf(HttpResponseCode.OK.getCode()),
                HttpResponseCode.OK.getReasonPhrase(),
                resourceFinder.getContentType(resourceFinder.getFileExtension(request.getRequestHeader().getPath())),
                responseBody.getLength());
        return new Response(responseHeader, responseBody);
    }
}
