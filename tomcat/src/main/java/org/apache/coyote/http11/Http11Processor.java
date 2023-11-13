package org.apache.coyote.http11;

import nextstep.jwp.Response.Response;
import nextstep.jwp.exception.UncheckedServletException;
import nextstep.jwp.request.Request;
import nextstep.jwp.util.ParsingUtil;
import nextstep.jwp.util.ResourceFinder;
import org.apache.coyote.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.Socket;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final Socket connection;

    private final ControllerMapper controllerMapper = new ControllerMapper();
    private final ResourceFinder resourceFinder = new ResourceFinder();
    private final ParsingUtil parsingUtil = new ParsingUtil();

    public Http11Processor(final Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        process(connection);
    }

    @Override
    public void process(final Socket connection) {
        try (final var inputStream = connection.getInputStream();
             final var outputStream = connection.getOutputStream()) {
            Request request = parsingUtil.parseRequest(inputStream);

            Response response = controllerMapper.findController(request);

            outputStream.write(response.toString().getBytes());
            outputStream.flush();
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }
}