package br.com.bb.t99.services;

import br.com.bb.t99.persistence.dao.PagamentoDao;
import br.com.bb.t99.persistence.models.Pagamento;
import br.com.bb.t99.persistence.repository.PagamentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@ApplicationScoped
public class PagamentoService {

    @Inject
    PagamentoRepository pagamentoRepository;

    void validarPagamento(Pagamento pagamento) {

    }

    public void publicarPagamento(Pagamento pagamento){
        if (validarNumCartao(pagamento.getNumCartao())){
            pagamento.setNumCartao(tratarNumCartao(pagamento.getNumCartao()));
        }
        validarTipoPessoa(pagamento.getTipoPessoa());
        validarMesVencCartao(pagamento.getMesVencCartao(), pagamento.getAnoVencCartao());
        validarAnoVencCartao(pagamento.getAnoVencCartao());
        validarCvv(pagamento.getCvv());
        if (validarValorPagamento(pagamento.getValorPagamento())){
            pagamento.setValorPagamento(tratarValorPagamento(pagamento.getValorPagamento()));
        }

        pagamentoRepository.persist(pagamento);
    }

    private boolean validarValorPagamento (BigDecimal valorPagamento) {
        if (valorPagamento.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Valor do pagamento inválido");
        }
        return true;
    }

    private BigDecimal tratarValorPagamento(BigDecimal valorPagamento) {
        valorPagamento = valorPagamento.setScale(2, RoundingMode.HALF_UP);
        return valorPagamento;
    }

    private void validarCvv(String cvv) {
        if (cvv == null || cvv.length() > 4 || (cvv != null && !(cvv.matches("\\d+")))) {
            throw new RuntimeException("Formato do CVV inválido");
        }
    }

    private void validarAnoVencCartao(int anoVencCartao) {
        if (anoVencCartao < LocalDate.now().getYear()) {
            throw new RuntimeException("Cartão vencido");
        } else if (anoVencCartao > 9999 ) {
            throw new RuntimeException("Ano de vencimento inválido");
        }
    }

    private void validarMesVencCartao(int mesVencCartao, int anoVencCartao) {
        if (mesVencCartao < 1 || mesVencCartao > 12) {
            throw new RuntimeException("Mês de vencimento inválido");
        } else if (mesVencCartao < LocalDate.now().getMonthValue() && anoVencCartao < LocalDate.now().getYear()) {
            throw new RuntimeException("Cartão vencido");
        }
    }

    private void validarTipoPessoa(int tipoPessoa) {
        if (tipoPessoa < 1 || tipoPessoa > 2) {
            throw new RuntimeException("Tipo de pessoa inválido");
        }
    }

    private String tratarNumCartao (String numCartao){
        return numCartao.replaceAll("[^\\d]", "");
    }

    private static boolean validarNumCartao(String numCartao) {
        // Remover todos os caracteres não numéricos
        String numLimpo = numCartao.replaceAll("[^\\d]", "");

        // Verificar se o número tem entre 13 e 19 dígitos
        if (numLimpo.length() < 13 || numLimpo.length() > 19) {
            throw new RuntimeException("Número do cartão inválido");
        }

        // Aplicar o algoritmo de Luhn
        int soma = 0;
        boolean alternar = false;
        for (int i = numLimpo.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(numLimpo.substring(i, i + 1));
            if (alternar) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            soma += n;
            alternar = !alternar;
        }

        // Verificar se a soma é múltiplo de 10
        if (soma % 10 == 0) {
            return true;
        } else {
            throw new RuntimeException("Numero do Cartão Inválido");
        }
    }

    public List<Pagamento> listar() {
        return pagamentoRepository.listAll();
    }
}
