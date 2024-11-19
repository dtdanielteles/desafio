package br.com.bb.t99.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import org.jboss.logmanager.Level;
import jakarta.ws.rs.ext.Provider;


import java.util.logging.Logger;

@Provider
public class NaoEncontradoExceptionMapper implements ExceptionMapper<NaoEncontradoException> {

    private static final Logger LOGGER = Logger.getLogger(NaoEncontradoExceptionMapper.class.getName());

    @Override
    public Response toResponse(NaoEncontradoException exception) {
        LOGGER.log(Level.ERROR, exception.getMessage(), exception);
        return Response.status(Response.Status.NOT_FOUND).entity(new ErrorResponse("Pagamento não encontrado", exception.getMessage())).build();
    }
}
