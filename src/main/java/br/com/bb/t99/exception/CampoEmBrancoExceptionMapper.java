package br.com.bb.t99.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logmanager.Level;

import java.util.logging.Logger;

@Provider
public class CampoEmBrancoExceptionMapper implements ExceptionMapper<CampoEmBrancoException> {

    private static final Logger LOGGER = Logger.getLogger(CampoEmBrancoExceptionMapper.class.getName());

    @Override
    public Response toResponse(CampoEmBrancoException exception) {
        LOGGER.log(Level.ERROR, exception.getMessage(), exception);
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Pagamento em branco", exception.getMessage())).build();
    }


}