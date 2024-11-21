package br.com.bb.t99.rest;

import br.com.bb.t99.persistence.models.Pagamento;
import br.com.bb.t99.persistence.repository.PagamentoRepository;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class PagamentoResourceTest {

    @InjectMock
    PagamentoRepository pagamentoRepository;

    private Pagamento pagamento;

    @BeforeEach
    void setUp() {
        pagamento = new Pagamento();
        pagamento.setNumCartao();
        pagamento.setCpfCnpjCliente();
        pa
    }

    @Test
    void publicarPagamento() {
    }

    @Test
    void listar() {
        List<Pagamento> pagamentos = new ArrayList<>();
        Mockito.when(pagamentoRepository.listAll()).thenReturn(pagamentos);
    }

    @Test
    void buscarPorId() {
    }

    @Test
    void deletarPagamento() {
    }
}