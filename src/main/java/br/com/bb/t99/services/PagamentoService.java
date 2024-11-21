package br.com.bb.t99.services;

import br.com.bb.t99.exception.CampoEmBrancoException;
import br.com.bb.t99.exception.CampoInvalidoException;
import br.com.bb.t99.exception.NaoAutorizadoException;
import br.com.bb.t99.exception.NaoEncontradoException;
import br.com.bb.t99.persistence.models.Pagamento;
import br.com.bb.t99.persistence.repository.PagamentoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@ApplicationScoped
public class PagamentoService {

    private static final Logger LOGGER = Logger.getLogger(PagamentoService.class.getName());

    @Inject
    PagamentoRepository pagamentoRepository;

    public List<Pagamento> listar() {
        List<Pagamento> lista = pagamentoRepository.listAll();

        if (lista.isEmpty()) {
            throw new NaoEncontradoException("Não existem pagamentos registrados");
        } else {
            LOGGER.info("Listando pagamentos");
            return lista;
        }
    }

    public Pagamento buscaPorId(int id) {
        Pagamento pagamento = pagamentoRepository.findById((long) id);

        if (pagamento != null){
            LOGGER.info("Pagamento listado");
            return pagamento;
        } else {
            throw new NaoEncontradoException("Não encontrado pagamento com o id especificado");
        }
    }

    @Transactional
    public boolean deletarPagamento(int id) {
        boolean persiste = pagamentoRepository.deleteById((long) id);
        if (persiste) {
            LOGGER.info("Pagamento deletado.");
            return persiste;
        } else {
            throw new NaoEncontradoException("Não encontrado pagamento com o id especificado");        }
    }

    @Transactional
    public Pagamento publicarPagamento(Pagamento pagamento){

        if (pagamento == null) {
            throw new CampoEmBrancoException("Informe os dados do pagamento");
        }

        validarTipoPessoa(pagamento.getTipoPessoa());

        if (pagamento.getTipoPessoa() == 1) {
            validarCpf(pagamento.getCpfCnpjCliente());
            pagamento.setCpfCnpjCliente(tratarCpf(pagamento.getCpfCnpjCliente()));
        } else if (pagamento.getTipoPessoa() == 2) {
            validarCnpj(pagamento.getCpfCnpjCliente());
            pagamento.setCpfCnpjCliente(tratarCnpj(pagamento.getCpfCnpjCliente()));
        }


        pagamento.setNumCartao(validarNumCartao(pagamento.getNumCartao()));

        validarAnoVencCartao(pagamento.getAnoVencCartao());
        validarMesVencCartao(pagamento.getMesVencCartao(), pagamento.getAnoVencCartao());
        validarCvv(pagamento.getCvv());

        if (validarValorPagamento(pagamento.getValorPagamento())) {
            pagamento.setValorPagamento(tratarValorPagamento(pagamento.getValorPagamento()));
        }

        pagamentoRepository.persist(pagamento);
        LOGGER.info("Pagamento enviado com sucesso");
        return pagamento;

    }

    private void validarCnpj(String cpfCnpjCliente) {
        // Remover pontuação
        cpfCnpjCliente = cpfCnpjCliente.replaceAll("\\D", "");

        // Verificar se o cpfCnpjCliente tem 14 dígitos
        if (cpfCnpjCliente.length() != 14) {
            throw new CampoInvalidoException("CNPJ inválido.");
        }

        // Calcular os dígitos verificadores

        char dig13, dig14;
        int sm, i, r, num, peso;

        // Calcular o primeiro dígito verificador
        sm = 0;
        peso = 2;
        for (i = 11; i >= 0; i--) {
            num = (int) (cpfCnpjCliente.charAt(i) - 48);
            sm += (num * peso);
            peso = (peso == 9) ? 2 : peso + 1;
        }

        r = sm % 11;
        dig13 = (r == 0 || r == 1) ? '0' : (char) ((11 - r) + 48);

        // Calcular o segundo dígito verificador
        sm = 0;
        peso = 2;
        for (i = 12; i >= 0; i--) {
            num = (int) (cpfCnpjCliente.charAt(i) - 48);
            sm += (num * peso);
            peso = (peso == 9) ? 2 : peso + 1;
        }

        r = sm % 11;
        dig14 = (r == 0 || r == 1) ? '0' : (char) ((11 - r) + 48);

        if (!(dig13 == cpfCnpjCliente.charAt(12) && dig14 == cpfCnpjCliente.charAt(13))) {
            throw new CampoInvalidoException("CNPJ inválido");
        }
    }

    private String tratarCnpj(String cpfCnpjCliente) {
        // Remover pontuação
        cpfCnpjCliente = cpfCnpjCliente.replaceAll("\\D", "");

        // Formatar o cpfCnpjCliente
        return cpfCnpjCliente.substring(0, 2) + "." + cpfCnpjCliente.substring(2, 5) + "." + cpfCnpjCliente.substring(5, 8) + "/" + cpfCnpjCliente.substring(8, 12) + "-" + cpfCnpjCliente.substring(12, 14);
    }

    private void validarCpf(String cpfCnpjCliente) {
        // Remover pontuação
        cpfCnpjCliente = cpfCnpjCliente.replaceAll("\\D", "");

        // Verificar se o cpfCnpjCliente tem 11 dígitos
        if (cpfCnpjCliente.length() != 11) {
            throw new CampoInvalidoException("CPF inválido.");
        }

        // Verificar se todos os dígitos são iguais
        if (cpfCnpjCliente.matches("(\\d)\\1{10}")) {
            throw new CampoInvalidoException("CPF inválido.");
        }

        // Calcular os dígitos verificadores
        int sm = 0;
        int peso = 10;
        for (int i = 0; i < 9; i++) {
            int num = (int) (cpfCnpjCliente.charAt(i) - 48);
            sm += (num * peso);
            peso -= 1;
        }

        int r = 11 - (sm % 11);
        char dig10 = (r == 10 || r == 11) ? '0' : (char) (r + 48);

        sm = 0;
        peso = 11;
        for (int i = 0; i < 10; i++) {
            int num = (int) (cpfCnpjCliente.charAt(i) - 48);
            sm += (num * peso);
            peso -= 1;
        }

        r = 11 - (sm % 11);
        char dig11 = (r == 10 || r == 11) ? '0' : (char) (r + 48);
        if(!((dig10 == cpfCnpjCliente.charAt(9)) && (dig11 == cpfCnpjCliente.charAt(10)))) {
            throw new CampoInvalidoException("CPF Inválido.");
        }
    }

    private String tratarCpf(String cpfCnpjCliente) {
        // Remover pontuação
        cpfCnpjCliente = cpfCnpjCliente.replaceAll("\\D", "");

        // Formatar o cpfCnpjCliente
        return cpfCnpjCliente.substring(0, 3) + "." + cpfCnpjCliente.substring(3, 6) + "." + cpfCnpjCliente.substring(6, 9) + "-" + cpfCnpjCliente.substring(9, 11);
    }

    private boolean validarValorPagamento (BigDecimal valorPagamento) {
        if (valorPagamento == null || valorPagamento.compareTo(BigDecimal.ZERO) <= 0) {
            throw new CampoInvalidoException("Valor do pagamento inválido");
        }
        return true;
    }

    private BigDecimal tratarValorPagamento(BigDecimal valorPagamento) {
        valorPagamento = valorPagamento.setScale(2, RoundingMode.HALF_UP);
        return valorPagamento;
    }

    private void validarCvv(String cvv) {
        if (cvv == null || cvv.length() < 3 || cvv.length() > 4 || !(cvv.matches("\\d+"))) {
            throw new CampoInvalidoException("Formato do CVV inválido");
        }
    }

    private void validarAnoVencCartao(int anoVencCartao) {
        if (anoVencCartao < LocalDate.now().getYear()) {
            throw new NaoAutorizadoException("Cartão vencido");
        } else if (anoVencCartao > 9999) {
            throw new CampoInvalidoException("Ano de vencimento inválido");
        }
    }

    private void validarMesVencCartao(int mesVencCartao, int anoVencCartao) {
        if (mesVencCartao < 1 || mesVencCartao > 12) {
            throw new CampoInvalidoException("Mês de vencimento inválido");
        } else if (mesVencCartao < LocalDate.now().getMonthValue() && anoVencCartao == LocalDate.now().getYear()) {
            throw new NaoAutorizadoException("Cartão vencido");
        }
    }

    private void validarTipoPessoa(int tipoPessoa) {
        if (tipoPessoa < 1 || tipoPessoa > 2) {
            throw new CampoInvalidoException("Tipo de pessoa inválido");
        }
    }

    private static String validarNumCartao(String numCartao) {
        // Remover todos os caracteres não numéricos
        String numLimpo = numCartao.replaceAll("[^\\d]", "");

        // Verificar se o número tem 16 dígitos
        if (numLimpo.length() != 16) {
            throw new CampoInvalidoException("Número do cartão inválido");
        }
        return numLimpo;
    }

    private String tratarNumCartao (String numCartao){
        return numCartao.replaceAll("[^\\d]", "");
    }

}
