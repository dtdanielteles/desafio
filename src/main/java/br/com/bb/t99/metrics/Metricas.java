package br.com.bb.t99.metrics;

import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Metricas {

    @Inject
    MetricRegistry registry;

    @Counted(name = "pagamentoCount", description = "Contador de pagamentos")
    public void incrementPagamentoCount() {
        // Logic to increment the counter
    }

    @Timed(name = "pagamentoTimer", description = "Tempo de processamento de pagamentos")
    public void timePagamentoProcessing() {
        // Logic to time the processing
    }

    @Metered(name = "pagamentoMeter", description = "Taxa de pagamentos")
    public void meterPagamento() {
        // Logic to meter the payments
    }

    @Gauge(name = "pagamentoGauge", unit = "none", description = "Gauge de pagamentos")
    public int pagamentoGauge() {
        // Logic to return the gauge value
        return 42; // Example value
    }
}
