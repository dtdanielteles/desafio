package br.com.bb.t99.persistence.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Month;
import java.time.Year;

@Entity
@Table(name = "Pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "num_cartao")
    private String numCartao;

    @Column(name = "tipo_pessoa")
    private int tipoPessoa;

    @Column(name = "cpf_cnpj_cliente")
    private String cpfCnpjCliente;

    @Column(name = "mes_venc_cartao")
    private int mesVencCartao;

    @Column(name = "ano_venc_cartao")
    private int anoVencCartao;

    private String cvv;

    @Column(name = "valor_pagamento")
    private BigDecimal valorPagamento;

    public Pagamento() {
    }

    public Pagamento(int id) {
        this.id = id;
    }

    public Pagamento(int id, String numCartao, String cpfCnpjCliente, int mesVencCartao, int anoVencCartao, String cvv, BigDecimal valorPagamento, int tipoPessoa) {
        this.id = id;
        this.numCartao = numCartao;
        this.tipoPessoa = tipoPessoa;
        this.cpfCnpjCliente = cpfCnpjCliente;
        this.mesVencCartao = mesVencCartao;
        this.anoVencCartao = anoVencCartao;
        this.cvv = cvv;
        this.valorPagamento = valorPagamento;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumCartao() {
        return numCartao;
    }

    public void setNumCartao(String numCartao) {
        this.numCartao = numCartao;
    }

    public String getCpfCnpjCliente() {
        return cpfCnpjCliente;
    }

    public void setCpfCnpjCliente(String cpfCnpjCliente) {
        this.cpfCnpjCliente = cpfCnpjCliente;
    }

    public int getMesVencCartao() {
        return mesVencCartao;
    }

    public void setMesVencCartao(int mesVencCartao) {
        this.mesVencCartao = mesVencCartao;
    }

    public int getAnoVencCartao() {
        return anoVencCartao;
    }

    public void setAnoVencCartao(int anoVencCartao) {
        this.anoVencCartao = anoVencCartao;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public BigDecimal getValorPagamento() {
        return valorPagamento;
    }

    public void setValorPagamento(BigDecimal valorPagamento) {
        this.valorPagamento = valorPagamento;
    }

    public int getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(int tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }
}
