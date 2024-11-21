package br.com.bb.t99.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Metricas {

    private final Counter pagamentoCounter;
    private final Counter pagamentoDeletadoCounter;

    @Inject
    public Metricas(MeterRegistry registry) {
        pagamentoCounter = Counter.builder("pagamento_publicado_total")
                .description("Total de pagamentos publicados")
                .register(registry);

        pagamentoDeletadoCounter = Counter.builder("pagamento_deletado_total")
                .description("Total de pagamentos deletados")
                .register(registry);
    }

    public void incrementPagamentoCount() {
        pagamentoCounter.increment();
    }

    public void incrementPagamentoDeletadoCount() {
        pagamentoDeletadoCounter.increment();
    }
}