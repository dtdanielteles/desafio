package br.com.bb.t99.exception;

import com.fasterxml.jackson.core.JsonParseException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logmanager.Level;

import java.util.logging.Logger;

@Provider
public class JsonParseExceptionMapper implements ExceptionMapper<JsonParseException> {

    private static final Logger LOGGER = Logger.getLogger(JsonParseExceptionMapper.class.getName());

    @Override
    public Response toResponse(JsonParseException exception) {
        LOGGER.log(Level.ERROR, exception.getMessage(), exception);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("Erro ao analisar JSON. Por favor, verifique o formato do JSON.")
                .build();
    }
}
