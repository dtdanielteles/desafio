package br.com.bb.t99.rest;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.when;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(PagamentoResource.class)
public class PagamentoResourceTestIT {

    @Test
    public void testPagamentoEndpoint() {
        when().get()
                .then()
                .statusCode(404)
                .body(is("Pagamento não encontrado"));
    }

}
