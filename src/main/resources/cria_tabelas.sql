CREATE TABLE PAGAMENTOS (
    id BIGINT NOT NULL,
    num_cartao VARCHAR(19) NOT NULL,
    tipo_pessoa INTEGER NOT NULL,
    cpf_cnpj_cliente VARCHAR(14) NOT NULL,
    mes_venc_cartao INTEGER NOT NULL,
    ano_venc_cartao INTEGER NOT NULL,
    cvv VARCHAR(4) NOT NULL,
    valor_pagamento DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id)
);
