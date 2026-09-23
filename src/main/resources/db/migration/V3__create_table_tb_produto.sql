CREATE TABLE IF NOT EXISTS tb_produto (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255) NOT NULL,
    valor DOUBLE NOT NULL,
    categoria_id BIGINT NOT NULL,
    CONSTRAINT pk_tb_produto PRIMARY KEY (id),
    CONSTRAINT fk_produto_categoria
        FOREIGN KEY (categoria_id)
        REFERENCES tb_categoria(id)
);
