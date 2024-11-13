package br.com.bb.t99.rest;

import br.com.bb.t99.persistence.models.Pagamento;
import br.com.bb.t99.services.PagamentoService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/pagamento")
public class PagamentoResource {

    @Inject
    PagamentoService service;

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response publicarPagamento(Pagamento pagamento){
        return Response.status(Response.Status.CREATED)
                .entity(service.publicarPagamento(pagamento))
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pagamento> listar() {
        return service.listar();
    }
}
