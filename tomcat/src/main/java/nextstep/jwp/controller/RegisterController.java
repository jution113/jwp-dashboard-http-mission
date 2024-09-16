package nextstep.jwp.controller;

import nextstep.jwp.Response.Response;
import nextstep.jwp.Response.ResponseBody;
import nextstep.jwp.Response.ResponseHeader;
import nextstep.jwp.request.Request;
import nextstep.jwp.service.LoginService;
import nextstep.jwp.util.ResourceFinder;
import org.apache.util.HttpResponseCode;

import java.io.IOException;

public class RegisterController {
    private LoginService loginService = new LoginService();
    private ResourceFinder resourceFinder = new ResourceFinder();
    private ResponseHeader responseHeader = new ResponseHeader();
    private ResponseBody responseBody = new ResponseBody();
    public Response handleRequest(Request request) throws IOException {
        ResourceFinder resourceFinder = new ResourceFinder();
        ResponseBody responseBody = new ResponseBody(resourceFinder.getResource("/register.html"));
        ResponseHeader responseHeader = new ResponseHeader(String.valueOf(HttpResponseCode.OK.getCode()),
                HttpResponseCode.OK.getReasonPhrase(),
                resourceFinder.getContentType(resourceFinder.getFileExtension("/register.html")),
                responseBody.getLength());
        return new Response(responseHeader, responseBody);
    }
}
