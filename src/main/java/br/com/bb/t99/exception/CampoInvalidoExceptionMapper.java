package br.com.bb.t99.exception;

import br.com.bb.t99.services.PagamentoService;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logmanager.Level;

import java.util.logging.Logger;

@Provider
public class CampoInvalidoExceptionMapper implements ExceptionMapper<CampoInvalidoException> {

    private static final Logger LOGGER = Logger.getLogger(CampoInvalidoExceptionMapper.class.getName());

    @Override
    public Response toResponse(CampoInvalidoException exception) {
        LOGGER.log(Level.ERROR, exception.getMessage(), exception);
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse("Falha no pagamento", exception.getMessage())).build();
    }


}
