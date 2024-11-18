package br.com.bb.t99.rest;

import br.com.bb.t99.metrics.Metricas;
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

    @Inject
    Metricas metrics;

    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    public Response publicarPagamento(Pagamento pagamento){
        Pagamento pagamentoCriado = service.publicarPagamento(pagamento);
        metrics.incrementPagamentoCount();
        metrics.timePagamentoProcessing();
        metrics.meterPagamento();

        return Response.status(Response.Status.CREATED)
                .entity(pagamentoCriado.getId())
                .build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Pagamento> listar() {
        return service.listar();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Pagamento buscarPorId(@PathParam("id") int id) {
        return service.buscaPorId(id);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletarPagamento(@PathParam("id") int id) {
        service.deletarPagamento(id);
        return Response.status(Response.Status.OK).build();
    }

//    @PUT
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response editarPagamento(Pagamento pagamento, @PathParam("id") int id) {
//        service.editarPagamento(id);
//        return Response.status(Response.Status.OK).entity(service.editarPagamento(pagamento, id)).build();
//    }
}
